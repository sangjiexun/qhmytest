package com.fh.controller.system.authorityManage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.MenuButtonVo;
import com.fh.entity.system.User;
import com.fh.service.authoritymanage.department.DepartmentManager;
import com.fh.service.authoritymanage.department.StudentDepartmentManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.seq.SeqManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.JsonUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect;
import com.keman.zhgd.common.DataZidianSelect.MenuButtonEnum;
import com.keman.zhgd.common.maputil.UUID;
import com.keman.zhgd.common.tree.TreeJson;
import com.keman.zhgd.common.tree.VO;
import com.keman.zhgd.common.util.RightsHelper;
import com.keman.zhgd.common.util.Tools;

/**
 * 权限功能菜单
 * <p>标题:AuthorityManageController</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月6日 上午11:48:04
 */
@Controller
@RequestMapping(value="/authorityManage")
public class AuthorityManageController extends BaseController {
	
	@Resource
	private UserManager userService;
	
	@Resource
	private DepartmentManager departmentManager;
	
	@Resource
	private StudentDepartmentManager studentDepartmentManager;
	
	@Resource
	private RoleManager roleService;
	
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	
	/**
	 * 
	 * <p>描述:一级权限菜单首页面</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 上午11:57:29
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index.php")
	public ModelAndView index() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		List<Menu> allmenuList = (List<Menu>) session.getAttribute(username + Const.SESSION_allmenuList);
		List<Menu> authorityManageMenus = null;
		for (Menu menu : allmenuList) {
			if ("5".equals(menu.getMENU_ID())) {
				//权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/authoritymanage/index");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:组织机构列表 </p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 上午11:57:23
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/department-list.php")
	public ModelAndView departmentList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		/*
		 * 获取左侧树数据
		 */
		List<VO> departmentList = departmentManager.departmentList(pd);
		TreeJson treeJson = new TreeJson();
		String departmentTreeJsonData = treeJson.init(departmentList);
		mv.addObject("departmentTreeJsonData", departmentTreeJsonData);
		
		mv.setViewName("system/authoritymanage/department-list");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:学生专业</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月14日 下午3:25:50
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/student_department_list.php")
	public ModelAndView studentDepartmentList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//获取当前登录用户学校英文名称
		String COLLEGES_NAME_EN = user.getCOLLEGES_NAME_EN();
		/*
		 * 获取左侧树数据
		 */
		List<VO> departmentList = studentDepartmentManager.departmentList(pd);
		if(departmentList.size()>0){
			TreeJson treeJson = new TreeJson();
			treeJson.setPaixuField(true);
			String departmentTreeJsonData = treeJson.init(departmentList);
			mv.addObject("departmentTreeJsonData", departmentTreeJsonData);
		}
		mv.setViewName("system/authoritymanage/student-department-list");
		mv.addObject("pd", pd);
		mv.addObject("COLLEGES_NAME_EN", COLLEGES_NAME_EN);
		return mv;
	}
	
	
	
	
	/**
	 * 
	 * <p>描述:用户</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 上午12:38:35
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user-list.php")
	public ModelAndView userList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		/*
		 * 获取左侧树数据
		 */
		List<VO> departmentList = departmentManager.departmentList(pd);
		TreeJson treeJson = new TreeJson();
		String departmentTreeJsonData = treeJson.init(departmentList);
		mv.addObject("departmentTreeJsonData", departmentTreeJsonData);
		
		mv.setViewName("system/authoritymanage/user-list");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * <p>描述:用户 分页查询</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 上午1:37:14
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user-table")
	public ModelAndView userTable(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = (String)keyValue;
			if("用户名.真实名.手机号查询".equals(keyValue)){
				keyValue="";
			}
			pd.put("seText", keyValue);
		}
		
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		
		list = userService.userlistPage(page);
		
		/*
		 * 是否admin登录 add by chencc
		 */
		List<PageData> listResult = new ArrayList<>();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if(!"1".equals(user.getUSER_ID())){
			for(PageData pdd : list){
				if(!"1".equals(pdd.getString("USER_ID"))){//过滤掉admin
					listResult.add(pdd);
				}
			}
		}else{
			listResult = list;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", listResult);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * <p>描述:查询院系组织树</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月27日 下午6:51:41
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/student-department-tree")
	public ModelAndView studentDepartmentTree(Page page) throws Exception{
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String,Object>();
		
		//重新获取树的json数据
		List<VO> departmentList = departmentManager.departmentList(pd);
		TreeJson treeJson = new TreeJson();
		String departmentTreeJsonData = treeJson.init(departmentList);
		map.put("departmentTreeJsonData", departmentTreeJsonData);
		//end 重新获取树的json数据
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	
	/**
	 * 
	 * <p>描述:组织机构列表数据查询</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 下午4:27:21
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/department-table")
	public ModelAndView departmentTable(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			if("请输入组织名称查询".equals(keyValue)){
				keyValue="";
			}
			pd.put("seText", keyValue);
		}
		
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		list = departmentManager.departmentlistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:组织机构列表数据查询</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 下午4:27:21
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/student-department-table")
	public ModelAndView studentDepartmentTable(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = new String(keyValue.toString().getBytes("ISO8859-1"), "utf-8");
			if("请输入组织名称查询".equals(keyValue)){
				keyValue="";
			}
			pd.put("seText", keyValue);
		}
		if(Tools.isEmpty(pd.getString("PKID"))){
			/*
			 * 代表没有勾选，展示最顶级以及子集
			 */
			List<PageData> topDeptList = studentDepartmentManager.getTopDept(pd);
			pd.put("topDeptList", topDeptList);
		}
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		list = studentDepartmentManager.departmentlistPage(page);
		PageData pdXslx = null;
		//学生类型页面展示处理
		for(PageData value : list){
			pdXslx = studentDepartmentManager.getXSLXName(value);
    		if(pdXslx!=null){
    			value.put("XUESHENGLEIXING", pdXslx.get("XUESHENGLEIXING"));
    		} 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:组织机构新增页面</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午9:36:10
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDepartment.json")
	public ModelAndView addDepartment() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String edit = pd.getString("edit");
		
		PageData department = new PageData();
		
		if ("true".equals(edit)) {//是编辑页面
			List<PageData> departmentList = departmentManager.queryDepartment(pd);
			if (departmentList!=null && departmentList.size()>0) {
				department = departmentList.get(0);
				pd.put("pkid", department.getString("PARENT_PKID"));//存储父节点id
			}
		}
		mv.setViewName("system/authoritymanage/department-add");
		mv.addObject("pd", pd);
		mv.addObject("department", department);
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:组织机构新增页面</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午9:36:10
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addStudentDepartment.json")
	public ModelAndView addStudentDepartment() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String edit = pd.getString("edit");
		pd.put("is_use_d", "Y");
		pd.put("PARENT_ID", "XUEJI");
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//获取当前登录用户学校英文名称
		String COLLEGES_NAME_EN = user.getCOLLEGES_NAME_EN();
		List<PageData> schoolrollList=stuInfoService.getFromSYS_DICT(pd);
		PageData department = new PageData();
		List<String> xslxList = new ArrayList<String>();
		if ("true".equals(edit)) {//是编辑页面
			List<PageData> departmentList = studentDepartmentManager.queryDepartment(pd);
			if (departmentList!=null && departmentList.size()>0) {
				department = departmentList.get(0);
				pd.put("pkid", department.getString("PARENT_PKID"));//存储父节点id
			}
			//财经版本才有学生类型
			if("CAIJING".equals(COLLEGES_NAME_EN)){
				//将专业所属的学生类型查询出来放入list为了编辑页面展示所选的学生类型
				String xslx = (String) department.get("XUESHENGLEIXING_PKID");
				if(xslx!=null&&!"".equals(xslx)){
					String [] xslxarry = xslx.split(",");
					for(String result : xslxarry){
						xslxList.add(result);
					}
				}
				//将配置项启用的学生类型放到tempList中，为了判断编辑时一个专业所拥有的学生类型是未启用状态时将这个学生类型放入前面tempList中
				List<String> tempList = new ArrayList<String>();
				//先将学籍类型的DICTIONARIES_ID放入tempList中
				for(PageData list : schoolrollList){
					tempList.add(list.getString("DICTIONARIES_ID"));
				}
				//判断如果一个专业所拥有的学生类型在tempList中没有就将此学生类型加入到schoolrollList
				PageData pdParam = null;
				PageData pdList = null;
				PageData pdResult = null;
				for(String tempList2 : xslxList){
					if(!tempList.contains(tempList2)){
						pdParam = new PageData();
						pdParam.put("dictionaries_id", tempList2);
						//根据条件查询某个配置项的单个详细信息
						pdResult = stuInfoService.getDictionariesById(pdParam);						
						if(pdResult!=null){
							pdList = new PageData();
							//将结果到list中
							pdList.put("DICTIONARIES_ID", pdResult.get("DICTIONARIES_ID"));
							pdList.put("NAME", pdResult.get("NAME"));
							schoolrollList.add(pdList);
						}
					}
				}
			}
			
		}
		mv.setViewName("system/authoritymanage/student-department-add");
		mv.addObject("pd", pd);
		mv.addObject("department", department);
		mv.addObject("xslxList", xslxList);
		mv.addObject("schoolrollList", schoolrollList);
		mv.addObject("COLLEGES_NAME_EN", COLLEGES_NAME_EN);
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:新增 用户</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午3:53:29
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addUser.json")
	public ModelAndView addUser() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String edit = pd.getString("edit");
		
		PageData department = new PageData();
		String department_name = null;
		String department_id = null;
		PageData userPageData = null;
		
		if ("true".equals(edit)) {//是编辑页面
			userPageData = userService.findUserById(pd);
			pd.put("department_id", userPageData.getString("DEPARTMENT_ID"));
			pd.put("department_name", userPageData.getString("DEPARTMENT_NAME"));
			//查询组织机构
			List<PageData> departmentList = departmentManager.getDepartmentList(pd);
			mv.addObject("departmentList", departmentList);
			mv.setViewName("system/authoritymanage/user-edit");
		}else{
			//根据左侧组织点击的department_id查询 组织
			PageData departmentSchoolPageData = userService.getDepartmentSchool(pd);
			if (Tools.notEmpty(departmentSchoolPageData.getString("DEPARTMENT_NAME"))) {
				pd.put("department_name", departmentSchoolPageData.getString("DEPARTMENT_NAME"));
			}else{
				pd.put("department_name", "");
			}
			mv.setViewName("system/authoritymanage/user-add");
		}
		//获取状态字典类数据
		mv.addObject("userStatusList", DataZidianSelect.UserStats.values());
		//end 获取状态字典类数据
		mv.addObject("pd", pd);
		mv.addObject("userPageData", userPageData);
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:更新 用户 状态</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午7:23:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user-status")
	public ModelAndView userStatus() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pkids = pd.getString("pkids");
			String [] pkidsArray = pkids.split(",");
			pd.put("pkids", pkidsArray);
			PageData pageData = new PageData();
			pageData.put("pkids", pkidsArray);
			pageData.put("status", pd.getString("status"));
			userService.updateUserStatus(pageData);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * <p>描述:用户 查询 角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午7:59:32
	 * @param model
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user-showRole.json")
	public ModelAndView userShowRole(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		Session session = Jurisdiction.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("showFlag", true);//只是显示 查询
		mv.addObject("pd", pd);
		mv.setViewName("system/authoritymanage/role-list");
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:更新用户角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午8:40:55
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserRole")
	public ModelAndView saveUserRole() throws Exception{
		//pkids   role_id
		
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pkids = pd.getString("pkids");
			String [] pkidsArray = pkids.split(",");
			pd.put("pkids", pkidsArray);
			PageData pageData = new PageData();
			pageData.put("pkids", pkidsArray);
			pageData.put("role_id", pd.getString("role_id"));
			userService.saveUserRole(pageData);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:用户 重置 密码</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午7:37:00
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user-updatePwd")
	public ModelAndView userUpdatePwd() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pkids = pd.getString("pkids");
			String [] pkidsArray = pkids.split(",");
			pd.put("pkids", pkidsArray);
			PageData pageData = new PageData();
			pageData.put("pkids", pkidsArray);
			//密码
			String passwd = new SimpleHash("SHA-1", "", "123456").toString();//密码加密
			pageData.put("pwd", passwd);
			//end
			
			userService.updatePwd(pageData);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	/**
	 * 
	 * <p>描述:角色新增页面</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午2:26:46
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addRole.json")
	public ModelAndView addRole() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String edit = pd.getString("edit");
		
		PageData rolePd = new PageData();
		if ("true".equals(edit)) {//是编辑页面
			List<PageData> roleList = roleService.findByRoleId(pd);
			if (roleList!=null && roleList.size()>0) {
				rolePd = roleList.get(0);
			}
		}
		mv.setViewName("system/authoritymanage/role-add");
		mv.addObject("pd", pd);
		mv.addObject("role", rolePd);
		return mv;
	}
	
	
	/**
	 * <p>描述:保存组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午10:39:53
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/department-save")
	public ModelAndView departmentSave(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String pkid = pd.getString("pkid");
			
			//查询该组织节点下名称是否重名
			List<PageData> depList = departmentManager.getcdlistbyname(pd);
			
			if (Tools.notEmpty(pkid)) {//pkid不是空，是修改操作
				if(depList != null && depList.size() > 0 && !pkid.equals(depList.get(0).getString("PKID"))){
					map.put("result", false);//结果
					map.put("errorinfo", " 该名称已存在！");//错误信息
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				
				departmentManager.update(pd);
			}else {//pkid为空，新增操作
				
				if(depList != null && depList.size() > 0){
					map.put("result", false);//结果
					map.put("errorinfo", " 该名称已存在！");//错误信息
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				pkid = UUID.randomUUID().toString();
				pd.put("pkid", pkid);
				String username = user.getUSER_ID();//创建人
				pd.put("creater", username);
				departmentManager.save(pd);
			}
			
			//重新获取树的json数据
			List<VO> departmentList = departmentManager.departmentList(pd);
			TreeJson treeJson = new TreeJson();
			String departmentTreeJsonData = treeJson.init(departmentList);
			map.put("departmentTreeJsonData", departmentTreeJsonData);
			//end 重新获取树的json数据
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	@Resource
	private SeqManager seqService;
	
	/**
	 * <p>描述:保存组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午10:39:53
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/student-department-save")
	public ModelAndView studentDepartmentSave(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//判断父级节点有没有开启，如果没有，将新建节点设置为未启用状态
			PageData pd_parent = new PageData();
			pd_parent.put("pkid", pd.getString("parent_pkid"));
		    List<PageData> list_parent = studentDepartmentManager.queryDepartment(pd_parent);
		    if(list_parent!=null&&list_parent.size()>0){
		    	if("N".equals(list_parent.get(0).getString("JIHUOZT"))){
			    	pd.put("JIHUOZT", "N");
			    }
		    }
			String pkid = pd.getString("pkid");
			if (Tools.notEmpty(pkid)) {//pkid不是空，是修改操作
				studentDepartmentManager.update(pd);
			}else {//pkid为空，新增操作
//				pkid = UUID.randomUUID().toString();
				pkid = seqService.getNextSeqBySeqName(SeqManager.SEQ_SYS_DEPARTMENT);
				pd.put("pkid", pkid);
				String username = user.getUSER_ID();//创建人
				pd.put("creater", username);
				studentDepartmentManager.save(pd);
			}
			
			//重新获取树的json数据
			List<VO> departmentList = studentDepartmentManager.departmentList(pd);
			TreeJson treeJson = new TreeJson();
			String departmentTreeJsonData = treeJson.init(departmentList);
			map.put("departmentTreeJsonData", departmentTreeJsonData);
			//end 重新获取树的json数据
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:保存 用户</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午5:14:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user-save")
	public ModelAndView userSave(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			//获取当前登录用户学校英文名称
			String COLLEGES_NAME_EN = user.getCOLLEGES_NAME_EN();
			String user_id = pd.getString("user_id");
			if (Tools.notEmpty(user_id)) {//user_id不是空，是修改操作
				userService.updateU(pd);
			}else {//user_id为空，新增操作
				user_id = UUID.randomUUID().toString();
				pd.put("user_id", user_id);
				//密码
				String passwd = new SimpleHash("SHA-1", "", "123456").toString();//密码加密
				pd.put("pwd", passwd);
				//end 
				userService.saveU(pd);
				//如果是财经用户默认学生信息展示列
				if("CAIJING".equals(COLLEGES_NAME_EN)){
					pd.put("table_name", "T_STUDENT_CJ");
					String table_show_cols = "SHENFENZHENGHAO,XINGMING,XINGBIE,SHOUJI,XUEHAO,NIANJI,ZUZHINAME,CLASS_NAME,IS_RECEIVED,ZXZT,opt,";
					pd.put("table_show_cols", table_show_cols);
					stuInfoService.saveShowCols(pd);	
				}else{
					pd.put("table_name", "T_STUDENT");
					String table_show_cols = "SHENFENZHENGHAO,XINGMING,XUEHAO,XINGBIE,SHOUJI,BANJINAME,RXNIANFEN,BANJI_TYPE,ZXZT,IS_TONGGUO,opt,";
					pd.put("table_show_cols", table_show_cols);
					stuInfoService.saveShowCols(pd);
				}
			}
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:保存角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午2:27:42
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/role-save")
	public ModelAndView roleSave(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String pkid = pd.getString("pkid");
			if (Tools.notEmpty(pkid)) {//pkid不是空，是修改操作
				roleService.update(pd);
			}else {//pkid为空，新增操作
				pkid = UUID.randomUUID().toString();
				pd.put("pkid", pkid);
				String username = user.getUSER_ID();//创建人
				pd.put("userid", username);
				roleService.save(pd);
			}
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:删除 组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午3:30:19
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/department-del")
	public ModelAndView departmentDel(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			departmentManager.departmentDel(pd);
			//重新获取树的json数据
			List<VO> departmentList = departmentManager.departmentList(pd);
			TreeJson treeJson = new TreeJson();
			String departmentTreeJsonData = treeJson.init(departmentList);
			map.put("departmentTreeJsonData", departmentTreeJsonData);
			//end 重新获取树的json数据
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:删除 组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午3:30:19
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/student-department-del")
	public ModelAndView studentDepartmentDel(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			studentDepartmentManager.departmentDel(pd);
			//重新获取树的json数据
			List<VO> departmentList = studentDepartmentManager.departmentList(pd);
			TreeJson treeJson = new TreeJson();
			String departmentTreeJsonData = treeJson.init(departmentList);
			map.put("departmentTreeJsonData", departmentTreeJsonData);
			//end 重新获取树的json数据
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:判断组织节点下是否有人员</p>
	 * @author Administrator 王宁
	 * @date 2017年9月27日 上午10:41:02
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/isHaveUser.json")
	public ModelAndView isHaveUser() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pdd = studentDepartmentManager.isHaveUser(pd);
			if(pdd!=null){
				map.put("result", "success");//结果
				map.put("errorinfo", "");//错误信息
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:删除 角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午3:30:46
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/role-del")
	public ModelAndView roleDel(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			//先检查是否可以删除
			String pkids = pd.getString("pkids");
			boolean isCanDelRst = isCanDel(pkids);
			
			if (isCanDelRst) {
				String [] pkidsArray = pkids.split(",");
				pd.put("pkids", pkidsArray);
				PageData pageData = new PageData();
				pageData.put("pkids", pkidsArray);
				roleService.queryUserhasRoleCount(pageData);
				roleService.deleteRoleList(pageData);
				map.put("result", true);//结果
				map.put("errorinfo", "");//错误信息
			}else {
				map.put("result", false);//结果
				map.put("errorinfo", "删除失败,有角色已经被用户使用!");//错误信息
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:检查角色是否可以被删除</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午4:08:41
	 * @param pkids
	 * @return
	 * @throws Exception
	 */
	private boolean isCanDel(String pkids) throws Exception{
		boolean rst = true;
		String [] pkidsArray = pkids.split(",");
		PageData pageData = new PageData();
		pageData.put("pkids", pkidsArray);
	 	List<PageData> rstList = roleService.queryUserhasRoleCount(pageData);
	 	
	 	if (rstList!=null) {
	 		pageData = rstList.get(0);
	 		int count = Integer.parseInt(pageData.getString("RS"));
	 		if (count>0) {
				rst = false;
			}
		}
		return rst;
	}
	
	
	
	/**
	 * 
	 * <p>描述:角色管理 首页</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午7:24:10
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/role-list.php")
	public ModelAndView roleList(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		Session session = Jurisdiction.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		mv.addObject("pd", pd);
		mv.setViewName("system/authoritymanage/role-list");
		return mv;
	}
	
	
	/**
	 * <p>描述:显示分配权限页面</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午5:35:01
	 * @param model
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showJurisdiction.json")
	public ModelAndView showJurisdiction(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询角色
		pd.put("pkid", pd.getString("role_id"));
		List<PageData> roleList = roleService.findByRoleId(pd);
		PageData role = roleList.get(0);
		
		pd.put("rights", role.getString("RIGHTS"));
		
		/*
		 * 权限树
		 */
		List<MenuButtonVo> menuAndButton = roleService.queryMenuAndButtonRole(pd);
		TreeJson treeJson = new TreeJson();
		treeJson.setPaixuField(true);
		String menuAndButtonTreeData = treeJson.init(menuAndButton);
		mv.addObject("menuAndButtonTreeData", menuAndButtonTreeData);
		
		mv.addObject("pd", pd);
		mv.setViewName("system/authoritymanage/role-jurisdiction");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:保存菜单和按钮权限</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午9:43:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/jurisdiction-save")
	public ModelAndView jurisdictionSave() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String jurisdictions = pd.getString("datas");
			JSONArray jsonArray = JsonUtil.stringParseArray(jurisdictions);
			JSONObject data = null;
			String  menus = "";
			String  buttons = "";
			String type = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				data = jsonArray.getJSONObject(i);
				type = data.getString("type");
				if (MenuButtonEnum.菜单.getValue().equals(type)) {
					menus = menus + data.getString("href")+",";
				}else if (MenuButtonEnum.按钮.getValue().equals(type)) {
					buttons = buttons + data.getString("href")+",";
				}
			}
			
			if (Tools.notEmpty(menus) && menus.length()>0) {
				menus = menus.substring(0, menus.length()-1);
			}
			
			if (Tools.notEmpty(buttons) && buttons.length()>0) {
				buttons = buttons.substring(0, buttons.length()-1);
			}
			
			if (Tools.notEmpty(menus)) {
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menus));//用菜单ID做权处理
				pd.put("rights", rights.toString());
			}else {
				pd.put("rights", null);
			}
			pd.put("buttons", buttons);
			roleService.saveJurisdiction(pd);
//			
//			for (Object object : jsonArray) {
//				data = (JSONObject)object;
//			}
			
//			BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
//			roleService.updateRoleRights(null);
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:保存数据权限</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月14日 上午10:13:17
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/sjqxjur-save")
	public ModelAndView sjqxjur() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String username = user.getUSER_ID();//创建人
			pd.put("CZR_ID", username);
			String pkid = UUID.randomUUID().toString();
			pd.put("PKID", pkid);
			String jurisdictions = pd.getString("datas");
			JSONArray jsonArray = JsonUtil.stringParseArray(jurisdictions);
			JSONObject data = null;
			//String  menus = "";
			String  buttons = "";
			String type = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				data = jsonArray.getJSONObject(i);
				buttons = buttons + data.getString("href")+",";
			}
			if (Tools.notEmpty(buttons) && buttons.length()>0) {
				buttons = buttons.substring(0, buttons.length()-1);
			}
			pd.put("DEPARTMENT_PKID", buttons);
			
			List<PageData> list = roleService.getqxlist(pd);
			if(list!=null && list.size()>0){  //更新 
				pd.put("YUSER_ID", list.get(0).getString("USER_ID"));
				roleService.updatesjqx(pd);
			}else{ //插入
				roleService.savesjqxJur(pd);
			}
			
//			
//			for (Object object : jsonArray) {
//				data = (JSONObject)object;
//			}
			
//			BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
//			roleService.updateRoleRights(null);
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:用户权限</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月14日 下午4:29:35
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/yhsjqxjur-save")
	public ModelAndView yhsjqxjur() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String username = user.getUSER_ID();//创建人
			pd.put("CZR_ID", username);
			String pkid = UUID.randomUUID().toString();
			pd.put("PKID", pkid);
			String jurisdictions = pd.getString("datas");
			JSONArray jsonArray = JsonUtil.stringParseArray(jurisdictions);
			JSONObject data = null;
			//String  menus = "";
			String  buttons = "";
			String type = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				data = jsonArray.getJSONObject(i);
				buttons = buttons + data.getString("href")+",";
			}
			if (Tools.notEmpty(buttons) && buttons.length()>0) {
				buttons = buttons.substring(0, buttons.length()-1);
			}
			pd.put("DEPARTMENT_PKID", buttons);
			
			List<PageData> list = roleService.getyhqxlist(pd);
			if(list!=null && list.size()>0){  //更新 
				pd.put("YUSER_ID", list.get(0).getString("USER_ID"));
				roleService.updateyhsjqx(pd);
			}else{ //插入
				roleService.saveyhsjqxJur(pd);
			}
			
//			
//			for (Object object : jsonArray) {
//				data = (JSONObject)object;
//			}
			
//			BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
//			roleService.updateRoleRights(null);
			
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:角色列表数据</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午11:35:33
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/role-table")
	public ModelAndView roleTable(Page page) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		
		Object keyValue = pd.get("seText");
		if(keyValue != null){
			keyValue = String.valueOf(keyValue);
			if("请输入角色名称查询".equals(keyValue)){
				keyValue="";
			}
			pd.put("seText", keyValue);
		}
		
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		page.setPd(pd);
		
		list = roleService.rolelistPage(page);
		/*
		 * 是否admin登录 add by chencc
		 * 如果非admin登录则过滤掉系统管理组角色
		 */
		List<PageData> listResult = new ArrayList<>();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if(!"1".equals(user.getUSER_ID())){
			for(PageData pdd : list){
				if(!"0".equals(pdd.getString("ROLE_ID"))){//过滤掉系统管理组角色
					listResult.add(pdd);
				}
			}
		}else{
			listResult = list;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", listResult);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:验证名称唯一</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月30日 下午4:59:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getlistbyname")
	public ModelAndView getlistbyname(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			List<PageData> list = null;
			list = departmentManager.getlistbyname(pd);
			if(list != null && list.size() > 0){
				for(PageData pdd : list){
					if(!pd.getString("PKID").equals(pdd.getString("DEPARTMENT_ID"))){
						map.put("result", false);//结果
					}else{
						map.put("result", true);//结果
					}
				}
			}else{
				map.put("result", true);//结果
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:判断部门编码是否重复</p>
	 * @author Administrator 王宁
	 * @date 2017年11月27日 上午9:11:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getlistbyBianma")
	public ModelAndView getlistbyBianma(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);//结果
		try {
			PageData pd_bianMa = null;
			pd_bianMa = departmentManager.getlistbyBianma(pd);
			if(pd_bianMa != null){
				if(!pd.getString("PKID").equals(pd_bianMa.getString("DEPARTMENT_ID"))){
					map.put("result", false);//结果
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:删除时验证是否有用户</p>
	 * @author Administrator 胡文浩
	 * @date 2017年9月26日 下午5:56:44
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/CheckUser")
	public ModelAndView CheckUser() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			List<PageData> list = null;
			list = departmentManager.CheckUser(pd);
			if(list != null && list.size() > 0){
				map.put("result", "false");//结果
			}else{
				map.put("result", "true");//结果
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:验证用户名称唯一性</p>
	 * @author Administrator 王宁
	 * @date 2018年1月8日 下午7:10:10
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/usernameIdentify")
	public ModelAndView usernameIdentify() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd_user = null;
		pd_user = userService.usernameIdentify(pd);
		if(pd_user != null && !pd_user.getString("USER_ID").equals(pd.getString("USER_ID"))){
			map.put("result", "true");//表示用户名重复了
		}else{
			map.put("result", "false");//表示用户名没重复
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:验证角色名称唯一性</p>
	 * @author Administrator 王宁
	 * @date 2018年1月8日 下午7:10:32
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/rolenameIdentify")
	public ModelAndView rolenameIdentify() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd_user = null;
		pd_user = roleService.rolenameIdentify(pd);
		if(pd_user != null && !pd_user.getString("ROLE_ID").equals(pd.getString("PKID"))){
			map.put("result", "true");//表示用户名重复了
		}else{
			map.put("result", "false");//表示用户名没重复
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:分配数据权限</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月13日 上午9:30:12
	 * @param model
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/qxshowJur.json")
	public ModelAndView showJur(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询角色
		/*pd.put("pkid", pd.getString("role_id"));
		List<PageData> roleList = roleService.findByRoleId(pd);
		PageData role = roleList.get(0);
		
		pd.put("rights", role.getString("RIGHTS"));*/
		
		/*
		 * 权限树
		 */
		List<VO> menuAndButton = roleService.queryqxRole(pd);
		TreeJson treeJson = new TreeJson();
		treeJson.setPaixuField(true);
		String TreeData = treeJson.init(menuAndButton);
		mv.addObject("qxTreeData", TreeData);
		
		mv.addObject("pd", pd);
		mv.setViewName("system/authoritymanage/sjqxrole-jurisdiction");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:分配用户数据权限</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月14日 下午3:43:40
	 * @param model
	 * @param DEPARTMENT_ID
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/yhqxshowJur.json")
	public ModelAndView showyhJur(Model model, String DEPARTMENT_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询角色
		/*pd.put("pkid", pd.getString("role_id"));
		List<PageData> roleList = roleService.findByRoleId(pd);
		PageData role = roleList.get(0);
		
		pd.put("rights", role.getString("RIGHTS"));*/
		
		/*
		 * 权限树
		 */
		List<VO> menuAndButton = roleService.queryyhqxRole(pd);
		TreeJson treeJson = new TreeJson();
		treeJson.setPaixuField(true);
		String TreeData = treeJson.init(menuAndButton);
		mv.addObject("yhqxTreeData", TreeData);
		
		mv.addObject("pd", pd);
		mv.setViewName("system/authoritymanage/yhsjqxrole-jurisdiction");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatepaixu.json")
	public ModelAndView updatepaixu() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		studentDepartmentManager.updatePaiXu(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	@ResponseBody
	@RequestMapping(value = "/user-del")
	public ModelAndView userdel() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pkids = pd.getString("pkids");
			String [] pkidsArray = pkids.split(",");
			pd.put("pkids", pkidsArray);
			PageData pageData = new PageData();
			pageData.put("pkids", pkidsArray);
			//pageData.put("status", pd.getString("status"));
			userService.delUser(pageData);
			map.put("result", true);//缁撴灉
			map.put("errorinfo", "");//閿欒淇℃伅
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//缁撴灉
			map.put("errorinfo", e.getMessage());//閿欒淇℃伅
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:更新启用状态(院校专业)</p>
	 * @author Administrator w'zz
	 * @date 2018年07月19日 下午3:41:34
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			studentDepartmentManager.updateIsUse(pd);			
			map.put("rst", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
}
