package com.fh.controller.system.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.fh.util.Jurisdiction;
import com.fh.service.system.control.ControlManager;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.seq.SeqManager;

/** 
 * 说明：组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/control")
public class ControlController extends BaseController {
	
	String menuUrl = "control/controllist.php"; //菜单地址(权限用)
	@Resource(name="controlService")
	private ControlManager controlService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="seqService")
	private SeqManager seqService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	
	
	@RequestMapping(value="/controllist")
	public ModelAndView controllist(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		/*Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		//Map buttonMap = (Map)session.getAttribute(user.getUSERNAME()+Const.SESSION_QX);
		List buttonMap = (List) session.getAttribute(user.getUSERNAME() + Const.SESSION_QX);
		mv.addObject("buttonMap", buttonMap);*/
		
	/*	String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}*/
		/*String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		page.setPd(pd);*/
		/*List<PageData>	varList = controlService.list(page);	//列出Dictionaries列表	
		mv.addObject("pd", controlService.findById(pd));		//传入上级所有信息
*/		//mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//上级ID
		mv.setViewName("system/control/control_list");
		//mv.addObject("varList", varList);
		
		return mv;
	}
	
	
	
	
	 /**去修改页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goEdit.json")
		@ResponseBody
		public ModelAndView goEdit()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			Session session = Jurisdiction.getSession();
			String XIANGMUBM = (String) session.getAttribute(Const.SELECTED_BIANMA);//获取项目编码
			pd.put("XIANGMUBM", XIANGMUBM);
			Map<String,Object> map = new HashMap<String,Object>();
			List<PageData> listgzz=controlService.getgzz(pd);
			List<PageData> listlxj=controlService.getlxj(pd);
			List<PageData> listmq=controlService.ListMQ(pd);
			pd = controlService.findBybianma(pd);	//根据ID读取
			Object bm =pd.get("BIANMA");
			String b="";
			if(bm!=null && !"".equals(bm)){
				b=bm.toString();
			}
			String lx=(String) pd.get("LEIXING");
			String sn=(String) pd.get("SNHAO");
			String tdh=(String) pd.get("TONGDAOHAO");
			mv.setViewName("system/control/control_edit");
			mv.addObject("tdh", tdh);
			mv.addObject("lx", lx);
			mv.addObject("sn", sn);
			mv.addObject("result", "success");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("bm", b);
			mv.addObject("listgzz", listgzz);
			mv.addObject("listlxj", listlxj);
			mv.addObject("listmq", listmq);
			return mv;
		}	
	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		String XIANGMUBM = (String) session.getAttribute(Const.SELECTED_BIANMA);//获取项目编码
		pd.put("XIANGMUBM", XIANGMUBM);
		List<PageData> listgzz=controlService.getgzz(pd);
		List<PageData> listlxj=controlService.getlxj(pd);
		List<PageData> listmq=controlService.ListMQ(pd);
		PageData xmbm=controlService.getxmbm(pd);
		String bianma=controlService.getbianma();
		String bm="K"+bianma;
		mv.setViewName("system/control/control_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("bm", bm);
		mv.addObject("xmbm", xmbm);
		mv.addObject("listgzz", listgzz);
		mv.addObject("listlxj", listlxj);
		mv.addObject("listmq", listmq);
		return mv;
		
	}	
	
	
	
	
	/**
	 * 获取表格数据 json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody  
	@RequestMapping(value = "/tablejson")
	public ModelAndView tablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			pd.put("seText", keyValue);
		}
		
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
	
		page.setPd(pd);
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String roleid=user.getROLE_ID();
		List<PageData> list=null;
		
			 list = controlService.cllistPage(page);
		
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("pageNumber", page.getCurrentPage());//当前第几页
		map.put("total", page.getTotalResult());//总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	
	@ResponseBody  
	@RequestMapping(value = "/getIdentify.json")
	public ModelAndView getIdentify() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		String DTTOP_BM = (String) session.getAttribute(Const.SELECTED_BIANMA);
		String TYPE = session.getAttribute(Const.SELECTED_NODE_TYPE).toString();
		
		pd.put("DTTOP_BM", DTTOP_BM);
		PageData pdd = controlService.getIdentify(pd);
		Map<String,Object> map = new HashMap<String,Object>();
		if(pdd==null){
			 map.put("valid",true);
        }else {
        	if(pd.getString("BIANMA").equals(pdd.getString("BIANMA"))){
        		map.put("valid",true);
        	}else{
        		map.put("valid", false);
        	}
        }
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/control-add")
	public ModelAndView save() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		controlService.save(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	@ResponseBody
	@RequestMapping(value="/control-edit")
	public ModelAndView edit() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//controlService.save(pd);
		controlService.edit(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
	/**
	 * 删除
	 * @param DEPARTMENT_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/control-del.json")
	@ResponseBody
	public Object delete() throws Exception{
		
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		
		controlService.delete(pd);	//执行删除
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		controlService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	*/
	@RequestMapping(value="/editActive")
	public ModelAndView editActive() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		controlService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = controlService.list(page);	//列出Dictionaries列表	
		mv.addObject("pd", controlService.findById(pd));		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//上级ID
		mv.setViewName("system/department/department_list");
		mv.addObject("varList", varList);
		
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDepartment")
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
			pddd=controlService.findById(pddd);
			if(pddd!=null){
				parent_id =pddd.getString("PARENT_ID");
			
			}
		
			List<Department>  department1=controlService.listAllDepartment(parent_id);
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
			mv.setViewName("system/department/department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		mv.addObject("pds",controlService.findById(pd));//传入上级所有信息	
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);//传入ID，作为子级ID用
		
		//定义一个集合，根据获取的DEPARTMENT_TYPE_ID，通过SYS_DEPARTMENT_TYPE表获取它的名字
		List<Project> pj=controlService.typeName();
        mv.addObject("pj", pj);
        List<PageData> province = controlService.S_province(pd);
        mv.addObject("province", province);
    
		
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "save");
		return mv;
	}	*/
	
/*	 *//**去修改页面
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID");
		pd = controlService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",controlService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=controlService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = controlService.S_province(pd);
       mv.addObject("province", province);
       
       List<PageData> city = controlService.S_city(pd);
       mv.addObject("city", city);
       
       List<PageData> district = controlService.S_district(pd);
       mv.addObject("district", district);
        
        
        
        
        
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	*/

	
	@RequestMapping(value="/goActive")
	public ModelAndView goActive(HttpServletRequest req)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		HttpSession session =  req.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		String DEPARTMENT_ID =user.getDEPARTMENT_ID();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		pd = controlService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",controlService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=controlService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = controlService.S_province(pd);
       mv.addObject("province", province);
       
       /*List<PageData> city = controlService.S_city(pd);
       mv.addObject("city", city);*/
       
     /*  List<PageData> district = controlService.S_district(pd);
       mv.addObject("district", district);*/
        
		mv.setViewName("system/department/depactive");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	
	
	
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(controlService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	@RequestMapping(value="/getPinyin")
	@ResponseBody
	public Object getPinyin(){
		Map<String,String> map = new HashMap<String,String>();
		String name ="";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			name=pd.getString("NAME");
			name=PinyinHelper.getShortPinyin(name);//返回结果
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	
		map.put("name", name);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**获取city信息
	 * @return
	 */
	@RequestMapping(value="/getDq")
	@ResponseBody
	public Object getDq(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=controlService.S_district(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	@RequestMapping(value="/getCity")
	@ResponseBody
	public Object getCity(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=controlService.S_city(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	/**获取DEPARTMENT_TYPE_ID=1的名称和DEPARTMENT_ID，用于角色管理的添加角色页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/departmenttypeid")
	@ResponseBody
	public Object departmenttypeid() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		String pd11 = null;
		try{
			pd = this.getPageData();
			List <PageData> pd1 = controlService.findByTypeId(pd);
			
			
			ObjectMapper mapper = new ObjectMapper();
			pd11 = mapper.writeValueAsString(pd1);
			if(pd11 != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);//返回结果
		map.put("dp", pd11);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/**
	 * 检查工作站名称是否已存在	
	 */
	@RequestMapping(value="/checkmc.json")
	@ResponseBody
	public Object checkmc() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
	//项目编码
		Session session = Jurisdiction.getSession();
		String PROJECT_BIANMA = (String) session.getAttribute(Const.SELECTED_BIANMA);//获取项目编码
		pd.put("PROJECT_BIANMA", PROJECT_BIANMA);
		//判断工作站名称是否重复
		String MINGCHENG=pd.getString("MINGCHENG");
		MINGCHENG=new String(MINGCHENG.getBytes("ISO8859-1"),"utf-8");
		pd.put("MINGCHENG", MINGCHENG);
		Integer count=controlService.mcIsDuplicated(pd);
		if(count>0){
			map.put("result", "mcIsDuplicated");
			return AppUtil.returnObject(pd, map);
		}
		map.put("result", "success");
		return AppUtil.returnObject(pd, map);
	}
}
