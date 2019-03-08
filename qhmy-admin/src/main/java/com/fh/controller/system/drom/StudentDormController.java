package com.fh.controller.system.drom;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.login.LoginController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.dorm.StudentDormManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.service.system.dorm.impl.StudentDormService;
import com.fh.service.system.dormitoryInfo.DormitoryInfoManager;
import com.fh.service.system.seq.SeqManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.DataZidianSelect.Zhiwei;
import com.keman.zhgd.common.Tools;
import com.keman.zhgd.common.tree.TreeJson;
import com.keman.zhgd.common.tree.VO;

/**
 * 学生宿舍
 * @author chencc
 *
 */
@Controller
@RequestMapping(value="/studentDorm")
public class StudentDormController extends BaseController {
	
	@Resource
	private StudentDormManager studentDormService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	@Resource
	private SeqManager seqService;
	@Resource(name="dormitoryInfoService")
	private DormitoryInfoManager  dormitoryInfoService;
	
	
	/**
	 * 
	 * <p>描述:跳转学生宿舍</p>
	 * @author chencc
	 * @return
	 */
	@RequestMapping(value="/studentDormNav.php")
	public ModelAndView studentDormNav() throws Exception{
		ModelAndView mv = this.getModelAndView();
		/*
		 * 获取当前用户
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		//end 获取当前用户
		
		/*
		 * 获取当前用户权限
		 */
		List<Menu> allmenuList = (List<Menu>) session.getAttribute(username + Const.SESSION_allmenuList);
		List<Menu> authorityManageMenus = null;
		for (Menu menu : allmenuList) {
			if ("50".equals(menu.getMENU_ID())) {//
				//权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		//end 获取当前用户权限
		mv.setViewName("system/dorm/studentDormNav");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:跳转学生监控</p>
	 * @author chencc
	 * @return
	 */
	@RequestMapping(value="/studentDormMonitor.php")
	public ModelAndView studentDormMonitor() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", "ZXZT");
		/*
		 * 获取当前用户
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		//end 获取当前用户
				List<PageData> zuzhilist=null;
				List<PageData> gradelist=null;
				try {
					pd.put("userAllDepts", this.getjkUserAllDepts());
					if(this.getjkUserAllDepts()!=null){
						zuzhilist=stuInfoService.getZuzhis(pd);
					}
					gradelist=stuInfoService.getGrades(pd);
					List alllist=new ArrayList();
					//3280
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		mv.addObject("gradelist",gradelist);
		mv.addObject("zuzhilist",zuzhilist);
		//end 获取当前用户权限
		mv.setViewName("system/dorm/studentDormMonitor");
		return mv;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getlistxy")
	public ModelAndView getlistxy() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		if("null".equals(pd.getString("NIANJI"))){
			List<PageData> gradelist=stuInfoService.getGrades(pd);
			if(gradelist != null && gradelist.size()>0){
			String gradename=gradelist.get(0).getString("NAME");
			pd.put("NIANJI", gradename);
			}
		}
		List<PageData> depList = studentDormService.getdepList(pd);
		for (PageData pdd : depList) {
			List<PageData> pds = studentDormService.getdateList(pdd);
			pdd.put("linedata", pds);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		//map.put("studentDormList", studentDormList);
		map.put("depList", depList);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:获取用户院校专业权限(所有的顶级节点及其子孙的并集)</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月13日 上午10:39:31
	 * @return
	 * @throws Exception
	 */
	
	public  List<PageData> getUserAllDepts() throws Exception{
		/*Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData userDepartments = stuInfoService.getUserDepartments(user.getUSER_ID());
		if(userDepartments!=null){
			//代表分配了权限
			String[] str = userDepartments.getString("DEPARTMENT_PKID").split(",");
			List<PageData> userAllDepts = stuInfoService.getUserAllDepts(str);
			return userAllDepts;
		}*/
		return null;
	}
	
	
	public  List<PageData> getjkUserAllDepts() throws Exception{
		/*Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData userDepartments = stuInfoService.getjkUserDepartments(user.getUSER_ID());
		if(userDepartments!=null && Tools.notEmpty(userDepartments.getString("DEPARTMENT_PKID"))){
			//代表分配了权限
			String[] str = userDepartments.getString("DEPARTMENT_PKID").split(",");
			List<PageData> userAllDepts = stuInfoService.getjkUserAllDepts(str);
			return userAllDepts;
		}*/
		return null;
	}
	
	
	/**
	 * 
	 * <p>学生宿舍</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toStudentDormList.php")
	public ModelAndView toStudentDormList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		mv.addObject("studentDormTreeJsonData", this.getStudentDormTreeData(pd));
		mv.setViewName("system/dorm/studentDormList");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:宿舍卡片List</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormList")
	public ModelAndView getStudentDormList() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		String dormLevel = "0";
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null && Tools.notEmpty(dormLevelPd.getString("SD_LEVEL"))){
			dormLevel = dormLevelPd.getString("SD_LEVEL");
		}
		List<PageData> studentDormList = studentDormService.getStudentDormList(pd);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("dormLevel", dormLevel);
		map.put("studentDormList", studentDormList);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:获得宿舍级别</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormLevel")
	public ModelAndView getStudentDormLevel() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		String dormLevel = "0";
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null && Tools.notEmpty(dormLevelPd.getString("SD_LEVEL"))){
			dormLevel = dormLevelPd.getString("SD_LEVEL");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("dormLevel", dormLevel);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	/**
	 * 
	 * <p>描述:跳转新增编辑宿舍页面</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toAddUpdateStudentDorm.json")
	public ModelAndView toAddUpdateStudentDorm() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData studentDorm = studentDormService.getStudentDorm(pd);
		if(studentDorm != null && Tools.notEmpty(studentDorm.getString("SD_LEVEL"))){
			studentDorm.put("SD_LEVEL_CLICK", studentDorm.getString("SD_LEVEL"));
			if("1".equals(studentDorm.getString("SD_LEVEL"))){
				studentDorm.put("SD_LEVEL_NAME", "宿舍楼");
				studentDorm.put("SD_LEVEL", "2");
			}else if("2".equals(studentDorm.getString("SD_LEVEL"))){
				studentDorm.put("SD_LEVEL_NAME", "楼层");
				studentDorm.put("SD_LEVEL", "3");
			}else if("3".equals(studentDorm.getString("SD_LEVEL"))){
				studentDorm.put("SD_LEVEL_NAME", "房间");
				studentDorm.put("SD_LEVEL", "4");
			}
		}else{
			studentDorm = new PageData();
			studentDorm.put("SD_LEVEL_NAME", "校区");
			studentDorm.put("SD_LEVEL_CLICK", null);
			studentDorm.put("SD_LEVEL", "1");
		}
		
		//获得宿舍类型集合
		List<PageData> studentDormTypeList = studentDormService.getStudentDormTypeListUsed(pd);
		
		if(Tools.notEmpty(pd.getString("action")) && "add".equals(pd.getString("action"))){
			studentDorm.put("PARENT_PKID", pd.getString("pkid"));
		}else if(Tools.notEmpty(pd.getString("action")) && "edit".equals(pd.getString("action"))){
			if("4".equals(studentDorm.getString("SD_LEVEL"))){
				PageData chuangCountPd = studentDormService.getStudentDormChuangCountByParentPkid(pd);
				studentDorm.put("CHUANGCOUNT", chuangCountPd.getString("CHUANGCOUNT"));
				
				//查询房间下占用的床
				List<PageData> chuangInUseList = studentDormService.getInUseChuangListByParentPkid(pd);
				if(chuangInUseList != null && chuangInUseList.size() > 0){
					studentDorm.put("CHUANG_ISUSE", true);
				}else{
					studentDorm.put("CHUANG_ISUSE", false);
				}
			}else{
				PageData sdpd = studentDormService.getStudentDorm(pd);
				studentDorm.put("PARENT_PKID", sdpd.getString("PARENT_PKID"));
			}
		}
		
		
		mv.setViewName("system/dorm/addUpdateStudentDorm");
		mv.addObject("pd", pd);
		mv.addObject("studentDorm", studentDorm);
		mv.addObject("studentDormTypeList", studentDormTypeList);
		return mv;
	}
	
	/**
	 * <p>描述:保存宿舍信息</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addUpdateStudentDorm")
	public ModelAndView addUpdateStudentDorm() throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);//结果
		
		try {
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			String username = user.getUSER_ID();//创建人
			String action = pd.getString("ACTION");
			if (Tools.notEmpty(action) && "edit".equals(action)) {//修改操作
				studentDormService.updateStudentDorm(pd);
				map.put("result", true);//结果
			}else if (Tools.notEmpty(action) && "add".equals(action)){//新增操作
				String pkid = seqService.getNextSeqBySeqName(SeqManager.SEQ_STUDENT_DORM);
				pd.put("PKID", pkid);
				studentDormService.insertStudentDorm(pd);
				map.put("result", true);//结果
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());
		} finally{
			
		}
		map.put("studentDormTreeJsonData", this.getStudentDormTreeData(pd));
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:查询节点是否存在</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormBySdName")
	public ModelAndView getStudentDormBySdName() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		//获得
		PageData dormPd = studentDormService.getStudentDormBySdName(pd);
		if(dormPd != null){
			map.put("result", true);
		}
		
		map.put("pd", pd);
		map.put("dormPd", dormPd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:删除宿舍信息</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delStudentDorm")
	public ModelAndView delStudentDorm() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		
		/*
		 * 方案2 级联删除============================================================================================================================================
		 */
		try {
			studentDormService.deleteStudentDorm(pd);
			map.put("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("errorinfo", e.getMessage());
		}
		/*
		 * 方案2 级联删除============================================================================================================================================
		 */
		
		/*
		 * 方案1  不级联删除============================================================================================================================================
		
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null){
			//查询该删除节点的上一节点
			PageData lastPd = studentDormService.getLastStudentDormByPkid(pd);
			if(lastPd != null){
				pd.put("lastPkid", lastPd.getString("PARENT_PKID"));
			}
			
			//如果删除的是房间
			if("4".equals(dormLevelPd.getString("SD_LEVEL"))){
				
				//获得所有被占用的床位
				List<PageData> chuangList = studentDormService.getInUseChuangListByParentPkid(pd);
				//如果旗下床位未被占用
				if(chuangList != null && chuangList.size() > 0){
					map.put("result", false);
					map.put("errorinfo", "删除失败，该房间下有已被占用的床位！");
					map.put("pd", pd);
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
			}
			
			try {
				pd.put("SD_LEVEL", dormLevelPd.getString("SD_LEVEL"));
				studentDormService.deleteStudentDorm(pd);
				map.put("result", true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				map.put("errorinfo", e.getMessage());
			}
			
		}
		方案1  不级联删除============================================================================================================================================
		*/
		map.put("studentDormTreeJsonData", this.getStudentDormTreeData(pd));
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>学生宿舍计划</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toStudentDormPlanList.php")
	public ModelAndView toStudentDormPlanList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		
		//获得宿舍类型集合
		List<PageData> studentDormTypeList = studentDormService.getStudentDormTypeList(pd);
		mv.addObject("studentDormTypeList", studentDormTypeList);
		
		//获得班型集合
		List<PageData> banxingList = studentDormService.getBanxingList(pd);
		
		//获得文化课学校集合
		List<PageData> wenhuakexuexiaoList = studentDormService.getWenhuakexuexiaoList(pd);
		
		mv.addObject("banxingList", banxingList);
		mv.addObject("wenhuakexuexiaoList", wenhuakexuexiaoList);
		mv.addObject("studentDormTreeJsonData", this.getStudentDormPlanTreeData(pd));
		mv.setViewName("system/dorm/studentDormPlanList");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * <p>描述:根据当前登录用户权限获得宿舍树信息</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	private String getStudentDormPlanTreeData(PageData pd) throws Exception{
		String result = "";
		TreeJson treeJson = new TreeJson();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		
		/*
		 * 获取所有学校
		 */
		List<VO> schoolsList = new ArrayList<>();
		VO vo = new VO();
		vo.setId("001");
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(in);
		String collegeName="";
		if(prop!=null){
			collegeName=prop.getProperty("collegeName");
		}
		
		vo.setName("石家庄强化美术培训学校");
		vo.setParentId(null);
		vo.setType("0");
		schoolsList.add(vo);
		
		/*
		 * 获取左侧树数据
		 */
		List<VO> studentDormList = studentDormService.getStudentDormPlanTree(pd);
		
		if(schoolsList.size() > 0){
			if(studentDormList != null && studentDormList.size() > 0)
				schoolsList.addAll(studentDormList);
			result = treeJson.init(schoolsList);
			
			//取得树第一个顶级节点
			JSONArray jsonArray = JSONArray.fromObject(result);
			JSONObject json2 = jsonArray.getJSONObject(0);
			pd.put("defaultTopPkid", json2.getString("href"));
			pd.put("treeNodeId", json2.getString("href"));
		}
		return result;
	}
	
	/**
	 * <p>描述:获得宿舍树信息</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	private String getStudentDormTreeData(PageData pd) throws Exception{
		String result = "";
		TreeJson treeJson = new TreeJson();
		/*
		 * 获取所有学校
		 */
		List<VO> schoolsList = studentDormService.getSchoolsListTree(pd);
		
		/*
		 * 获取左侧树数据
		 */
		List<VO> studentDormList = studentDormService.getStudentDormTree(pd);
		if(studentDormList != null && studentDormList.size()>0){
			/*for (VO dorm : studentDormList) {
				
				String editStr = "1".equals(SESSION_MENU_BUTTONS.get("sspz_xg"))
						? "&nbsp;&nbsp;&nbsp;<span style=text-decoration:underline;display:none; id=studentTreeNodeEdit"+dorm.getId()+" onclick=javascript:studentTreeNodeEdit(this); val="+dorm.getId()+">[修改]</span>"
						: "";
				String delStr = "1".equals(SESSION_MENU_BUTTONS.get("sspz_sc"))
						? "&nbsp;&nbsp;&nbsp;<span style=text-decoration:underline;display:none; onclick=javascript:studentTreeNodeDel(this); val="+dorm.getId()+">[删除]</span>"
						: "";
				if("1".equals(dorm.getType())){//校区
					dorm.setName(dorm.getName() + editStr + delStr);
				}else if("2".equals(dorm.getType())){//楼
					dorm.setName(dorm.getName() + editStr + delStr);
				}else if("3".equals(dorm.getType())){//楼层
					dorm.setName(dorm.getName() + delStr);
				}else if("4".equals(dorm.getType())){//房间
					dorm.setName(dorm.getName() + editStr + delStr);
				}
			}*/
			schoolsList.addAll(studentDormList);
			
		}
		if(schoolsList.size() > 0){
			//取得树第一个顶级节点
//			pd.put("defaultTopPkid", schoolsList.get(schoolsList.size() - 1).getParentId());
//			pd.put("treeNodeId", schoolsList.get(schoolsList.size() - 1).getParentId());
			result = treeJson.init(schoolsList);
			
			//取得树第一个顶级节点
			JSONArray jsonArray = JSONArray.fromObject(result);
			JSONObject json2 = jsonArray.getJSONObject(0);
			pd.put("defaultTopPkid", json2.getString("href"));
			pd.put("treeNodeId", json2.getString("href"));
		}
		return result;
	}
	
	/**
	 * 
	 * <p>描述:跳转分配宿舍页面</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toAllotStudentDorm.json")
	public ModelAndView toAllotStudentDorm() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获得班型集合
		List<PageData> banxingList = studentDormService.getBanxingList(pd);
		
		//获得文化课学校集合
		List<PageData> wenhuakexuexiaoList = studentDormService.getWenhuakexuexiaoList(pd);
		
		mv.addObject("banxingList", banxingList);
		mv.addObject("wenhuakexuexiaoList", wenhuakexuexiaoList);
				
		mv.setViewName("system/dorm/allotStudentDorm");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getSchool")
	public ModelAndView getSchool() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		/*
		String dormLevel = "0";
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null && Tools.notEmpty(dormLevelPd.getString("SD_LEVEL"))){
			dormLevel = dormLevelPd.getString("SD_LEVEL");
		}*/
		//获得文化课学校集合
		List<PageData> wenhuakexuexiaoList = studentDormService.getWenhuakexuexiaoList(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("wenhuakexuexiaoList", wenhuakexuexiaoList);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:跳转回收宿舍页面</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toRecoveryStudentDorm.json")
	public ModelAndView toRecoveryStudentDorm() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		mv.setViewName("system/dorm/recoveryStudentDorm");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:宿舍计划查询下拉树</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentPlanTreeForQuery")
	public ModelAndView getDepartmentPlanTreeForQuery() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		
		//获得当前登录用户所拥有权限的院校专业下拉框树
		List<VO> departmentList = studentDormService.getDepartmentPlanTree(pd);
		if(departmentList != null && departmentList.size() > 0){
			String jsonStr = "";
			for (int i = 0; i < departmentList.size(); i++) {
				jsonStr += "{\"id\":\""+departmentList.get(i).getId()+"\", \"pId\":\""+departmentList.get(i).getParentId()+"\", \"name\":\""+departmentList.get(i).getName()+"\"},";
			}
			jsonStr = "["+jsonStr+"]";
			
			map.put("departmentTreeJsonData", jsonStr);
		}else{
			map.put("departmentTreeJsonData", "");
		}
		
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:宿舍卡片List</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormPlanList")
	public ModelAndView getStudentDormPlanList() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		
		String dormLevel = "0";
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null && Tools.notEmpty(dormLevelPd.getString("SD_LEVEL"))){
			dormLevel = dormLevelPd.getString("SD_LEVEL");
		}
		pd.put("dormLevel", dormLevel);
		List<PageData> studentDormList = studentDormService.getStudentDormPlanList(pd);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("dormLevel", dormLevel);
		map.put("studentDormList", studentDormList);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 获得宿舍计划信息列表（表格格式）
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月6日 上午8:43:07
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormPlanlistPage")
	public ModelAndView getStudentDormPlanlistPage(Page page) throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		
		String dormLevel = "0";
		//获得宿舍级别
		PageData dormLevelPd = studentDormService.getStudentDormLevel(pd);
		if(dormLevelPd != null && Tools.notEmpty(dormLevelPd.getString("SD_LEVEL"))){
			dormLevel = dormLevelPd.getString("SD_LEVEL");
		}
		pd.put("dormLevel", dormLevel);
		List<String> DEPARTMENT_PKIDList = new ArrayList<>();
		if(Tools.notEmpty(pd.getString("DEPARTMENT_PKID"))){
			String [] DEPARTMENT_PKIDStr = pd.getString("DEPARTMENT_PKID").split(",");
			DEPARTMENT_PKIDList = Arrays.asList(DEPARTMENT_PKIDStr);
		}
		pd.put("DEPARTMENT_PKIDList", DEPARTMENT_PKIDList);
		
		page.setPd(pd);
		List<PageData> studentDormList = studentDormService.getStudentDormPlanlistPage(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", studentDormList);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:宿舍计划分配查询宿舍下拉树</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormPlanTreeForAllot")
	public ModelAndView getStudentDormPlanTreeForAllot() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		
		int chuangCount = 0;
		int chuangCount_use = 0;
		//获得当前登录用户所拥有权限的院校专业下拉框树
		pd.put("STATUS", "0");
		List<VO> studentDormList = studentDormService.getStudentDormPlanTreeForAllot(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			String jsonStr = "";
			VO college = studentDormService.getCollege(pd);
			jsonStr += "{\"id\":\""+college.getId()+"\", \"pId\":\""+college.getParentId()+"\", \"name\":\""+college.getName()+"\", \"isXM\":\""+college.getIsXM()+"\"},";
			for (int i = 0; i < studentDormList.size(); i++) {
				//如果是床
				if("5".equals(studentDormList.get(i).getType())){
					chuangCount ++;
					//如果已占用
					if("1".equals(studentDormList.get(i).getState())){
						chuangCount_use ++;
					}
				}
				jsonStr += "{\"id\":\""+studentDormList.get(i).getId()+"\", \"pId\":\""+studentDormList.get(i).getParentId()+"\", \"name\":\""+studentDormList.get(i).getName()+"\", \"isXM\":\""+studentDormList.get(i).getIsXM()+"\"},";
			}
			jsonStr = "["+jsonStr+"]";
			
			map.put("studentDormTreeJsonData", jsonStr);
		}else{
			map.put("studentDormTreeJsonData", "");
		}
		map.put("chuangCount", chuangCount);
		map.put("chuangCount_use", chuangCount_use);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:宿舍计划分配查询宿舍下拉树</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormPlanTreeForRecovery")
	public ModelAndView getStudentDormPlanTreeForRecovery() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		
		//获得当前登录用户所拥有权限的院校专业下拉框树
		pd.put("STATUS", "0");
		List<VO> studentDormList = studentDormService.getStudentDormPlanTreeForRecovery(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			String jsonStr = "";
			/*for (int i = 0; i < studentDormList.size(); i++) {
				jsonStr += "{\"id\":\""+studentDormList.get(i).getId()+"\", \"pId\":\""+studentDormList.get(i).getParentId()+"\", \"name\":\""+studentDormList.get(i).getName()+"\", \"isXM\":\""+studentDormList.get(i).getIsXM()+"\"},";
			}
			jsonStr = "["+jsonStr+"]";*/
			
			VO college = studentDormService.getCollege(pd);
			jsonStr += "{\"id\":\""+college.getId()+"\", \"pId\":\""+college.getParentId()+"\", \"name\":\""+college.getName()+"\", \"isXM\":\""+college.getIsXM()+"\"},";
			for (int i = 0; i < studentDormList.size(); i++) {
				jsonStr += "{\"id\":\""+studentDormList.get(i).getId()+"\", \"pId\":\""+studentDormList.get(i).getParentId()+"\", \"name\":\""+studentDormList.get(i).getName()+"\", \"isXM\":\""+studentDormList.get(i).getIsXM()+"\"},";
			}
			jsonStr = "["+jsonStr+"]";
			
			map.put("studentDormTreeJsonData", jsonStr);
		}else{
			map.put("studentDormTreeJsonData", "");
		}
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:获得当前登录用户USER_ID</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	private String getLoginUSER_ID() throws Exception{
		//当前登录用户
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String USER_ID = user.getUSER_ID();//创建人
		return USER_ID;
	}
	
	
	/**
	 * 
	 * <p>描述:分配宿舍</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/allotStudentDorm")
	public ModelAndView allotStudentDorm() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pd", pd);
		map.put("result", false);
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		
		String STUDENT_DORM_PKIDS = pd.getString("STUDENT_DORM_PKIDS");
		if(Tools.isEmpty(STUDENT_DORM_PKIDS)){
			map.put("errorinfo", "请先选择宿舍！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		String BANXING = pd.getString("BANXING");
		if(Tools.isEmpty(BANXING)){
			map.put("errorinfo", "请先选择班型！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		String WENHUAKEXUEXIAO = pd.getString("WENHUAKEXUEXIAO");
		if(Tools.isEmpty(WENHUAKEXUEXIAO)){
			map.put("errorinfo", "请先选择文化课学校！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		int s = WENHUAKEXUEXIAO.length();
		if(s>4000){
			map.put("errorinfo", "选择的文化课学校过多，请重新选择！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		
		
		
		pd.put("partschoolArray", pd.getString("WENHUAKEXUEXIAO").split(","));
		
		map.put("studentDormTreeJsonData", this.getStudentDormPlanTreeData(pd));
		
		try {
			studentDormService.insertStudentDormDepartment(pd,stuInfoService);
			map.put("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("errorinfo", "分配失败，请重试！");
		}
		
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:回收宿舍</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/recoveryStudentDorm")
	public ModelAndView recoveryStudentDorm() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		map.put("pd", pd);
		
		String STUDENT_DORM_PKIDS = pd.getString("STUDENT_DORM_PKIDS");
		if(Tools.isEmpty(STUDENT_DORM_PKIDS)){
			map.put("errorinfo", "请先选择宿舍！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		
		map.put("studentDormTreeJsonData", this.getStudentDormPlanTreeData(pd));
		
		try {
			studentDormService.updateStudentDormDepartment(pd,stuInfoService);
			map.put("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("errorinfo", "回收失败，请重试！");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:通过节点PKID查询其顶级节点</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormTop")
	public ModelAndView getStudentDormTop() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		map.put("pd", pd);
		
		String pkid = pd.getString("pkid");
		if(Tools.isEmpty(pkid)){
			map.put("errorinfo", "请先选择左侧一个节点！");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		
		try {
			PageData dormTop = studentDormService.getStudentDormTop(pd);
			if(dormTop == null)
				map.put("dormTopPkid", pd.getString("pkid"));
			else
				map.put("dormTopPkid", dormTop.getString("PARENT_PKID"));
			map.put("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("errorinfo", "请先选择左侧一个节点！");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:通过床PKIDS获得床数量和被占用的数量</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormCount")
	public ModelAndView getStudentDormCount() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		map.put("pd", pd);
		
		int chuangCount = 0;
		int chuangCount_use = 0;
		
		//前台勾选的所有床PKID
		String STUDENT_DORM_PKIDS = pd.getString("STUDENT_DORM_PKIDS");
		if(Tools.notEmpty(STUDENT_DORM_PKIDS)){
			List<String> STUDENT_DORM_PKIDList = new ArrayList<>();
			STUDENT_DORM_PKIDList = Arrays.asList(STUDENT_DORM_PKIDS.split(","));
			pd.put("STUDENT_DORM_PKIDList", STUDENT_DORM_PKIDList);
			
			List<PageData> dormList = studentDormService.getStudentDormByPkidList(pd);
			if(dormList != null && dormList.size() > 0){
				for(PageData dorm : dormList){
					//如果是床
					if("5".equals(dorm.getString("SD_LEVEL"))){
						chuangCount ++;
						//如果已占用
						if("1".equals(dorm.getString("STATUS"))){
							chuangCount_use ++;
						}
					}
				}
			}
		}
		
		map.put("result", true);
		map.put("chuangCount", chuangCount);
		map.put("chuangCount_use", chuangCount_use);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 获取宿舍计划信息列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月3日 上午9:48:49
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormTablelistPage")
	public ModelAndView getStudentDormTablelistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("listPage", "Y");
		//当前登录用户
		pd.put("USER_ID", this.getLoginUSER_ID());
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		
		page.setPd(pd);
		list = studentDormService.getStudentDormPlanTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 导出宿舍计划表格
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月7日 上午10:14:01
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportStudentDormPlanTable.json")
	public ModelAndView exportStudentDormPlanTable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		pd.put("USER_ID", this.getLoginUSER_ID());
		Page page = new Page();
		page.setPd(pd);
		String[] headerNames = new String[]{"宿舍","性别","宿舍归属","宿舍类型","应住人数","实住人数","房间状态"};
		String[] bodyNames = new String[]{"ROOM_NAME","SD_SEX_NAME","DEPARTMENT_NAMES","DT_NAME","CHUANGCOUNT","CHUANGCOUNT_RUZHU","FANGSTATUS"};
		List<PageData> list = studentDormService.getStudentDormPlanTablelistPage(page);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,list);
		dataMap.put("fileName", "宿舍计划表");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	/**
	 * 
	 * <p>描述:去公寓使用统计页面</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月27日 下午3:25:04
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toApartmentStatistics.php")
	public ModelAndView toApartmentStatistics() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = studentDormService.getDormList(pd);
		mv.setViewName("system/reportStat/apartmentStatistics");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:公寓使用统计列表</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月27日 下午3:23:41
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getApartmentStatisticslistPage.json")
	public ModelAndView getApartmentStatisticslistPage() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = null;
		if(pd.getString("PARENT_PKID")!="" && Tools.notEmpty(pd.getString("PARENT_PKID"))){
			String[] PARENT_PKIDS = pd.getString("PARENT_PKID").split(",");
			pd.put("PARENT_PKIDS", PARENT_PKIDS);
		}else{
			pd.put("PARENT_PKIDS", "");
		}
		list = studentDormService.getApartmentStatisticslist(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:公寓使用统计导出</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月27日 下午3:22:26
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportApartmentStatistics.json")
	public ModelAndView exportApartmentStatistics(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		List<PageData> dormpkids = null;
		if(pd.getString("PARENT_PKID")!="" && Tools.notEmpty(pd.getString("PARENT_PKID"))){
			String[] PARENT_PKIDS = pd.getString("PARENT_PKID").split(",");
			pd.put("PARENT_PKIDS", PARENT_PKIDS);
		}else{
			pd.put("PARENT_PKIDS", "");
		}
		String[] headerNames = new String[]{"校区","宿舍楼","楼层","总床位数","男生床位","女生床位","总已住数","男生已住","女生已住","总空闲床位","男生空闲","女生空闲","入住率"};
		String[] bodyNames = new String[]{"XIAOQU","SUSHELOU","LOUCENG","ZONGCHUANGWEI","TOTALNAN","TOTALNV","ZONGZHANYONG","ZHANYONGNAN","ZHANYONGNV","ZONGKONGXIAN","KONGXIANNAN","KONGXIANNV","RUZHULV"};
		List<PageData> list = studentDormService.getApartmentStatisticslist(pd);
		
		//以下为增加合计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0");
			  DecimalFormat df2 = new DecimalFormat("######0.00");
			  List<String> xiaoqu = new ArrayList<>();
			  
			  //总床位数
			  BigDecimal zongchuangwei = new BigDecimal(0);
			  //总人数(男)
			  BigDecimal totalnan = new BigDecimal(0);
			  //总人数(女)
			  BigDecimal totalnv = new BigDecimal(0);
			  //已住总人数
			  BigDecimal zongzhanyong = new BigDecimal(0);
			  //已住总人数(男)
			  BigDecimal zhanyongnan = new BigDecimal(0);
			  //已住总人数(女)
			  BigDecimal zhanyongnv = new BigDecimal(0);
			  //空闲床位数
			  BigDecimal zongkongxian = new BigDecimal(0);
			  //空闲床位数(男)
			  BigDecimal kongxiannan = new BigDecimal(0);
			  //空闲床位数(女)
			  BigDecimal kongxiannv = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  
				  //是否包含该校区
				  boolean containsXiaoQu = xiaoqu.contains(data.getString("XIAOQU"));
				  
				  //换校区时重置合计
				  if(!containsXiaoQu ){
					  zongchuangwei = new BigDecimal(0);
					  totalnan = new BigDecimal(0);
					  totalnv = new BigDecimal(0);
					  zongzhanyong = new BigDecimal(0);
					  zhanyongnan = new BigDecimal(0);
					  zhanyongnv = new BigDecimal(0);
					  zongkongxian = new BigDecimal(0);
					  kongxiannan = new BigDecimal(0);
					  kongxiannv = new BigDecimal(0);
					  xiaoqu.clear();
				  }else{
					  data.put("XIAOQU", "");
				  }
				  resultList.add(data);
				  count ++;

				  zongchuangwei = zongchuangwei.add(new BigDecimal(data.getString("ZONGCHUANGWEI")));
				  totalnan = totalnan.add(new BigDecimal(data.getString("TOTALNAN")));
				  totalnv = totalnv.add(new BigDecimal(data.getString("TOTALNV")));
				  zongzhanyong = zongzhanyong.add(new BigDecimal(data.getString("ZONGZHANYONG")));
				  zhanyongnan = zhanyongnan.add(new BigDecimal(data.getString("ZHANYONGNAN")));
				  zhanyongnv = zhanyongnv.add(new BigDecimal(data.getString("ZHANYONGNV")));
				  zongkongxian = zongkongxian.add(new BigDecimal(data.getString("ZONGKONGXIAN")));
				  kongxiannan = kongxiannan.add(new BigDecimal(data.getString("KONGXIANNAN")));
				  kongxiannv = kongxiannv.add(new BigDecimal(data.getString("KONGXIANNV")));

				  //合计
				  PageData pdTemp = new PageData();
				  pdTemp.put("XIAOQU", "");
				  pdTemp.put("SUSHELOU", "合计");
				  pdTemp.put("LOUCENG", "");
				  pdTemp.put("ZONGCHUANGWEI", df.format(zongchuangwei.doubleValue()));
				  pdTemp.put("TOTALNAN", df.format(totalnan.doubleValue()));
				  pdTemp.put("TOTALNV", df.format(totalnv.doubleValue()));
				  pdTemp.put("ZONGZHANYONG", df.format(zongzhanyong.doubleValue()));
				  pdTemp.put("ZHANYONGNAN", df.format(zhanyongnan.doubleValue()));
				  pdTemp.put("ZHANYONGNV", df.format(zhanyongnv.doubleValue()));
				  pdTemp.put("ZONGKONGXIAN", df.format(zongkongxian.doubleValue()));
				  pdTemp.put("KONGXIANNAN", df.format(kongxiannan.doubleValue()));
				  pdTemp.put("KONGXIANNV", df.format(kongxiannv.doubleValue()));
				  pdTemp.put("RUZHULV", df2.format(zongzhanyong.doubleValue()/zongchuangwei.doubleValue()*100)+"%");
				  resultList.add(pdTemp);

				  count ++;
				  //去除重复的小计
				  if(containsXiaoQu ){
					  resultList.remove(count - 3);
					  count --;
				  }
				  //添加不重复入学年份
				  if(!containsXiaoQu){
					  xiaoqu.add(data.getString("XIAOQU"));
				  }
			  }	  
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,resultList);
		dataMap.put("fileName", "公寓使用统计");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:to宿舍入住</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月17日 下午2:58:48
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDormrztj.php")
	public ModelAndView toDormrztj() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//List<PageData> list = studentDormService.getDormrztjList(pd);
		mv.setViewName("system/reportStat/dormrztj");
		mv.addObject("pd", pd);
		//mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:宿舍历史纪录</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月19日 上午10:28:21
	 * @return
	 */
	@RequestMapping(value="/toDormhistory.php")
	public ModelAndView dormitoryInfo_list() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", "ZXZT");

		List<PageData> zuzhilist=null;
		List<PageData> gradelist=null;
		List<PageData> banxinglist=null;
		List<PageData> hzxxlist=null;
		List<PageData> banjilist=null;
		/*
		 * 宿舍列表
		 */
		List<PageData> dormlist=null;
		List<PageData> treelist=null;
		/*
		 * 宿舍类别
		 */
		List<PageData> dormtypelist=null;
		try {
//			pd.put("UserAllDormDepts", this.getUserAllDormDepts());
			
//			zuzhilist=stuInfoService.getZuzhis(pd);
			zuzhilist=this.getUserAllDepts();
			gradelist=stuInfoService.getGrades(pd);
			banxinglist=stuInfoService.getBan(pd);
			hzxxlist=stuInfoService.gethzxx(pd);
			banjilist=stuInfoService.getbanji(pd);
			PageData pd_dorm = new PageData();
			pd_dorm.put("ALLOT_TYPE", "0");//未分配
//			dormlist=this.getUserAllDormDeptsBySex(pd);
			dormtypelist=dormitoryInfoService.getDormTypeList(null);
			treelist = studentDormService.getDormtree(pd);
			//mv.addObject("treelist", this.getStudentDormTreeData(pd));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("zuzhilist",zuzhilist);
		mv.addObject("gradelist",gradelist);
		mv.addObject("banxinglist",banxinglist);
		mv.addObject("hzxxlist",hzxxlist);
		mv.addObject("banjilist",banjilist);
		mv.addObject("treelist",treelist);
		
//		mv.addObject("dormlist",dormlist);
		mv.addObject("dormtypelist",dormtypelist);
		mv.setViewName("system/dormitoryInfo/dormhistory_list");
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:入住统计table</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月18日 上午9:43:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/dormitoryrztjTable.json")
	public ModelAndView dormitorytypeTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("keyWord", pd.getString("keyWord").trim());
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = studentDormService.getDormrztjlistPage(page);
		//List<PageData> list = dormitorytypeService.dormitorytype_list(page);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:入住统计打印</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月18日 上午10:12:32
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/DormrztjlistPrint.json")
	public ModelAndView DormrztjlistPrint()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		List<PageData> list = studentDormService.printDormrztj(pd);
		map.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	@RequestMapping(value="/DormrzdjExcel.json")
	public ModelAndView feeSumlistExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"日期","入住人数","男生人数","女生人数"};
		String[] bodyNames = new String[]{"RZSJ","ZS","NAN","NV"};
		//List<PageData> list = reportStatService.getFeeSumlistTable(pd);
		List<PageData> list = studentDormService.printDormrztj(pd);
		
		//以下为增加小计和总计
		//List<PageData> resultList = new ArrayList<>();
		//if(list != null && list.size() > 0){}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,list);
		dataMap.put("fileName", "宿舍入住统计");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
}
