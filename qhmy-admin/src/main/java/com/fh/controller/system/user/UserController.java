package com.fh.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.keman.zhgd.common.util.FileDownload;
import com.keman.zhgd.common.util.FileUpload;
import com.keman.zhgd.common.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.keman.zhgd.common.util.ObjectExcelRead;
import com.keman.zhgd.common.util.ObjectExcelView;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.PathUtil;
import com.keman.zhgd.common.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 类名称：UserController
 * 创建人：FH zhoudibo
 * 更新时间：2015年11月3日
 * @version
 */

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	String menuUrl = "user/listUsers.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	/**显示用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		String departmentid1 = null;
		String DEPARTMENT_ID = null;
		String username = null;
		
		if(user!=null){
			
			departmentid1= user.getDEPARTMENT_ID();
			 username =user.getUSERNAME();
			  
		}
		if(!"admin".equals(username)){
			if(!"0".equals(departmentid1)){
				
				
					DEPARTMENT_ID =departmentid1;
				
				
			}else{
				
				DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();//部门的检索
			}
		}else{
			
			DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();//部门的检索
		}
			
		
		
		
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);//上级ID
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart);
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd);
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsers(page);	//列出用户列表
		pd.put("ROLE_ID", "0");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出系统角色
		
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		//mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**删除用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.deleteU(pd);
		out.write("success");
		out.close();
	}
	
	/**去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		List<Role> deproleList = roleService.listAllRolesBydepid(pd);//列出此部门下的所有角色
		
		page.setPd(pd);
		List<PageData>	users = userService.listUsers(page);	//列出用户列表
		List<Department> pd1 = departmentService.findAll();	//列出Dictionaries列表
		mv.addObject("users", users);
		mv.addObject("pd1", pd1);
		
		
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", deproleList);
		return mv;
	}
	
	/**保存用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增user");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd1 = new PageData();
		pd1 = roleService.findObjectById(pd);
		String rights = pd1.getString("RIGHTS");
		if(rights==null){
			pd.put("RIGHTS", "");
		}else{
			pd.put("RIGHTS", rights);
		}
		pd.put("USER_ID", this.get32UUID());	//ID 主键
		pd.put("LAST_LOGIN", "");//最后登录时间
		pd.put("IP", "");						//IP
		pd.put("SKIN", "");
				
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
		if(null == userService.findByUsername(pd)){	//判断用户名是否存在
			userService.saveU(pd); 					//执行保存
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUsername(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断邮箱是否存在
	 * @return
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUE(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	/**判断身份证号是否存在
	 * @return
	 */
	@RequestMapping(value="/LW_IDCARD")
	@ResponseBody
	public Object USER_IDCARD(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUSER_IDCARD(pd) != null){
				errInfo = "error";
				
				
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**判断劳务人员身份证号是否存在
	 * @return
	 */
	@RequestMapping(value="/USER_IDCARD")
	@ResponseBody
	public Object LW_IDCARD(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String d=pd.getString("USER_IDCARD");
			PageData pd1 = new PageData();
			pd1.put("USER_IDCARD", d);
			List<PageData> pdList = new ArrayList<PageData>();
			pdList = userService.findByUSER_IDCARD(pd1);
			if(pdList != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	
	
	
	
	/**去修改用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String fx = pd.getString("fx");//顶部修改个人资料
		if("head".equals(fx)){
			mv.addObject("fx", "head");
		}else{
			mv.addObject("fx", "user");
		}
		List<Role> roleList = roleService.listAllRolesBydepid(pd);//列出所有部门下的角色
		pd = userService.findById(pd);								//根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**查看用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		mv.setViewName("system/user/user_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去修改用户页面(在线管理页面打开)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改ser");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd1 = new PageData();
		pd1 = roleService.findObjectById(pd);
		String rights = pd1.getString("RIGHTS");
		pd.put("RIGHTS", rights);
		pd.put("SKIN", "");
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editU(pd);	//执行修改
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
				String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
				if(lastLoginStart != null && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
				}
				if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
				} 
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("编号");  		//2
				titles.add("姓名");			//3
				titles.add("所属部门");		//13
				titles.add("手机");			//5
				titles.add("邮箱");			//6
				titles.add("用户名"); 		//1
				titles.add("最近登录");		//7
				titles.add("上次登录IP");	//8
				titles.add("皮肤");	//9
				titles.add("身份证号");	//10
				titles.add("状态");	//11
				titles.add("是否为劳务人员");	//11
				titles.add("备注信息");	//12
				
				dataMap.put("titles", titles);
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("NUMBER"));		//2
					vpd.put("var2", userList.get(i).getString("NAME"));			//3
					vpd.put("var4", userList.get(i).getString("PHONE"));		//5
					vpd.put("var5", userList.get(i).getString("EMAIL"));		//6
					vpd.put("var6", userList.get(i).getString("USERNAME"));		//1
					vpd.put("var11", userList.get(i).getString("STATUS"));	//4
					vpd.put("var7", userList.get(i).getString("LAST_LOGIN"));	//7
					vpd.put("var8", userList.get(i).getString("IP"));			//8
					vpd.put("var9", userList.get(i).getString("SKIN"));			//9
					vpd.put("var10", userList.get(i).getString("USER_IDCARD"));			//10
					vpd.put("var12", userList.get(i).getString("ISLABOUR"));			//11
					vpd.put("var13", userList.get(i).getString("BZ"));			//12
					PageData pd1=new PageData();
					pd1.put("DEPARTMENT_ID",userList.get(i).getString("DEPARTMENT_ID"));
					
					pd1=departmentService.findById(pd1);
					List <PageData> listpagedata=departmentService.findByTypeId(pd1);
					String departmentname ="";
					for(i=0;i<listpagedata.size();i++){
						departmentname = listpagedata.get(i).getString("NAME");
					}
					vpd.put("var3", departmentname);			//13
					
					
					
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Role> roleList = roleService.listAllRolesBydepid(pd);//列出所有部门下的角色
	
		ModelAndView mv = this.getModelAndView();
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response,HttpServletRequest request)throws Exception{
		FileDownload.fileDownload(response,request, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			PageData pd1 = new PageData();
			
			pd1 = roleService.findObjectById(pd);
			String rights = pd1.getString("RIGHTS");
			pd.put("RIGHTS",rights);//权限
			
			
			
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("USER_ID", this.get32UUID());										//ID
				pd.put("NAME", listPd.get(i).getString("var1"));							//姓名
				
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var4"));	//根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);	
				if(userService.findByUsername(pd) != null){									//判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
			
				if(Tools.checkEmail(listPd.get(i).getString("var3"))){						//邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var3"));						
					if(userService.findByUE(pd) != null){									//邮箱已存在就跳过
						continue;
					}
				}else{
					continue;
				}
				pd.put("NUMBER", listPd.get(i).getString("var0"));							//编号已存在就跳过
				pd.put("PHONE", listPd.get(i).getString("var2"));							//手机号
				String psw = listPd.get(i).getString("var5");
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME,psw).toString());	//默认密码123
				if(userService.findByUN(pd) != null){
					continue;
				}
				
				pd.put("LAST_LOGIN", listPd.get(i).getString("var6"));				//最后登录时间
				pd.put("IP",listPd.get(i).getString("var7"));						//IP
				pd.put("STATUS", listPd.get(i).getString("var10"));					//状态
				pd.put("SKIN", listPd.get(i).getString("var8"));				//默认皮肤	
				
				pd.put("USER_IDCARD", listPd.get(i).getString("var9"));
				pd.put("ISLABOUR", listPd.get(i).getString("var11"));
				pd.put("BZ", (listPd.get(i).getString("var12") == null)?"":listPd.get(i).getString("var12"));								//备注
				
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllUserDepartment")
	public ModelAndView listAllDepartment(Model model,String DEPARTMENT_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = new PageData();
		
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String roleid=null;
		String departmentid1 =null;
		String username=null;
		if(user!=null){
			
			 roleid =user.getROLE_ID();
			  departmentid1 = user.getDEPARTMENT_ID();
			  username =user.getUSERNAME();
			  
		}
		String parent_id =null;
		List<Department>  department =new ArrayList<Department>();
		if(departmentid1!=null){
			PageData pddd =new PageData();
			pddd.put("DEPARTMENT_ID", departmentid1);
			pddd=departmentService.findById(pddd);
			if(pddd!=null){
				parent_id =pddd.getString("PARENT_ID");
			
			}
		
			List<Department>  department1=departmentService.listAllUserDepartment(parent_id);
			if(department1.size()>0){
				for(int i =0;i<department1.size();i++){
					String depid=department1.get(i).getDEPARTMENT_ID();
					if(departmentid1.equals(depid)){
						department.add(department1.get(i));
					}
					
					
				}
			}
		
		
		
		}
		
		
		
		try{
			JSONArray arr = JSONArray.fromObject(department);
			String json = arr.toString();
			json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID",DEPARTMENT_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/user/user_department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	/**获取DEPARTMENT_TYPE_ID=1的名称和DEPARTMENT_ID，用于角色管理的添加角色页面
	 * @return
	 * @throws Exception 
	 *//*	 
	@RequestMapping(value="/getrights")
	@ResponseBody
	public Object departmenttypeid() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String pd11 = null;
		try{
			pd = this.getPageData();
			List<Role> pd1 = roleService.listAllRolesBydepid(pd);
			ObjectMapper mapper = new ObjectMapper();
			pd11 = mapper.writeValueAsString(pd1);
			System.out.println(pd11+"##################################################################");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("pd11",pd11);//返回结果
		map.put("result", "false");
		return AppUtil.returnObject(new PageData(), map);
	}
	
	*/
	
	
	
	
	  
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

}
