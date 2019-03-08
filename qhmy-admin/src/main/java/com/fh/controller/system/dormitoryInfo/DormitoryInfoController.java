package com.fh.controller.system.dormitoryInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.dorm.StudentDormManager;
import com.fh.service.system.dormitoryInfo.DormitoryInfoManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.ImgUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.Md5Util;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.DataZidianSelect;
import com.keman.zhgd.common.tree.VO;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>标题:DormitoryInfoController</p>
 * <p>描述:宿舍信息控制器</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2018年3月15日 上午10:54:06
 */
@Controller
@RequestMapping(value="/dormitoryInfo")
public class DormitoryInfoController extends BaseController {
	
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	@Resource
	private StudentDormManager studentDormService;
	
	@Resource(name="dormitoryInfoService")
	private DormitoryInfoManager  dormitoryInfoService;


	/**
	 * 
	 * <p>描述:宿舍列表页面</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月16日 上午9:48:57
	 * @return
	 */
	@RequestMapping(value="/dormitoryInfo_list.php")
	public ModelAndView dormitoryInfo_list() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", "ZXZT");

		List<PageData> zuzhilist=null;
		List<PageData> gradelist=null;
		List<PageData> banxinglist=null;
		List<PageData> hzxxlist=null;
		/*
		 * 宿舍列表
		 */
		List<PageData> dormlist=null;
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
			PageData pd_dorm = new PageData();
			pd_dorm.put("ALLOT_TYPE", "0");//未分配
//			dormlist=this.getUserAllDormDeptsBySex(pd);
			dormtypelist=dormitoryInfoService.getDormTypeList(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("zuzhilist",zuzhilist);
		mv.addObject("gradelist",gradelist);
		mv.addObject("banxinglist",banxinglist);
		mv.addObject("hzxxlist",hzxxlist);
//		mv.addObject("dormlist",dormlist);
		mv.addObject("dormtypelist",dormtypelist);
		mv.setViewName("system/dormitoryInfo/dormitoryInfo_list");
		return mv;
	}
	/**
	 * 
	 * <p>描述:宿舍table</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月16日 上午9:48:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getdormtable.json")
	public ModelAndView dormtablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
//		pd.dorm_pkid
		String[] dorm_array = pd.getString("dorm_pkid").split(",");
		pd.put("array", dorm_array);
		/*
		 *  根据登陆用户先查询该用户在用户管理中是否分了院校专业的权限（多选），
		 *  如果分了，根据院校专业pkids,以最高院校节点为准，可以查看所有该节点以及子节点的学生群，
		 *  如果没有分配，则判断该用户是否是班主任，如果是班主任，查询该班主任管理的班级的学生数据，如果不是班主任，则看不到任何学生数据。
		 * 
		 */
		if(Tools.isEmpty(pd.getString("dorm_type"))){
			pd.put("dorm_type", "");
		}
		List<PageData> list =null;
		//if(this.getUserAllDepts()!=null && this.getUserAllDepts().size()>0){		
			pd.put("UserAllDormDepts", this.getUserAllDepts());
			page.setPd(pd);
			list = dormitoryInfoService.getDormlistPage(page);
			
			
			PageData newpd = new PageData();
			PageData pd1 = new PageData();
			for (int i = 0; i < list.size(); i++) {
				newpd.put("schoolpkids", list.get(i).getString("SCHOOLPKIDS"));
				pd1=dormitoryInfoService.getSCHOOLNAME(newpd);
				list.get(i).put("susheguishu", pd1.getString("SCHOOLNAME"));			
				}
			
			
		//}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:对调table</p>
	 * @author Administrator 胡文浩
	 * @date 2019年2月14日 上午9:27:25
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getduidiaodormtable.json")
	public ModelAndView tiaosutablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
//		pd.dorm_pkid
		String[] dorm_array = pd.getString("dorm_pkid").split(",");
		pd.put("array", dorm_array);
		/*
		 *  根据登陆用户先查询该用户在用户管理中是否分了院校专业的权限（多选），
		 *  如果分了，根据院校专业pkids,以最高院校节点为准，可以查看所有该节点以及子节点的学生群，
		 *  如果没有分配，则判断该用户是否是班主任，如果是班主任，查询该班主任管理的班级的学生数据，如果不是班主任，则看不到任何学生数据。
		 * 
		 */
		if(Tools.isEmpty(pd.getString("dorm_type"))){
			pd.put("dorm_type", "");
		}
		List<PageData> list =null;
		List<PageData> newlist =new ArrayList();
		//if(this.getUserAllDepts()!=null && this.getUserAllDepts().size()>0){		
			pd.put("UserAllDormDepts", this.getUserAllDepts());
			page.setPd(pd);
			list = dormitoryInfoService.getDuiDiaoDormlistPage(page);
			
			
			PageData newpd = new PageData();
			PageData pd1 = new PageData();
			
			if(list.size()>0){
				String[] strOne= pd.getString("whkxx").split(",");
			   // String[] strTwo= {"a","b","d","g"};
				for (int i = 0; i < list.size(); i++) {
					String[] strTwo= list.get(i).getString("WHKXUEXIAOPKID").split(",");
					for (int k = 0; k < strOne.length; k++) {
					            if (ArrayUtils.contains(strTwo, strOne[k])) {
					            	newlist.add(list.get(i));
					             }
								}
					          }
				}
			
			
			
			/*for (int i = 0; i < list.size(); i++) {
				newpd.put("schoolpkids", list.get(i).getString("SCHOOLPKIDS"));
				pd1=dormitoryInfoService.getSCHOOLNAME(newpd);
				list.get(i).put("susheguishu", pd1.getString("SCHOOLNAME"));			
				}*/
			
			
		//}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", newlist);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}


	
	
		/**
		 * 
		 * <p>描述:宿舍入住历史</p>
		 * @author Administrator 胡文浩
		 * @date 2018年12月19日 下午4:14:57
		 * @param page
		 * @return
		 * @throws Exception
		 */
	@ResponseBody
	@RequestMapping(value = "/getdormhistorytable.json")
	public ModelAndView dormhistorytable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
//		pd.dorm_pkid
		String[] dorm_array = pd.getString("dorm_pkid").split(",");
		pd.put("array", dorm_array);
		/*
		 *  根据登陆用户先查询该用户在用户管理中是否分了院校专业的权限（多选），
		 *  如果分了，根据院校专业pkids,以最高院校节点为准，可以查看所有该节点以及子节点的学生群，
		 *  如果没有分配，则判断该用户是否是班主任，如果是班主任，查询该班主任管理的班级的学生数据，如果不是班主任，则看不到任何学生数据。
		 * 
		 */
		if(Tools.isEmpty(pd.getString("dorm_type"))){
			pd.put("dorm_type", "");
		}
		if("1".equals(pd.getString("xingbie"))){
			pd.put("xingbie", "M");
		}else if("0".equals(pd.getString("xingbie"))){
			pd.put("xingbie", "W");
		}
		List<PageData> list =null;
		//if(this.getUserAllDepts()!=null && this.getUserAllDepts().size()>0){		
			/*pd.put("UserAllDormDepts", this.getUserAllDepts());
			page.setPd(pd);*/
		page.setPd(pd);
			list = dormitoryInfoService.getDormhistorylistPage(page);
		//}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:宿舍历史导出</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月20日 上午10:51:06
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dormhistoryexportExcel.json")
	public ModelAndView dormhistory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		if("1".equals(pd.getString("xingbie"))){
			pd.put("xingbie", "M");
		}else if("0".equals(pd.getString("xingbie"))){
			pd.put("xingbie", "W");
		}
		String[] dorm_array = pd.getString("dorm_pkid").split(",");
		pd.put("array", dorm_array);
		String[] headerNames = new String[]{"身份证号","学号","姓名","性别","文化课学校","入学年份","班型","班级","宿舍类型","宿舍","入住日期"};
		String[] bodyNames = new String[]{"SHENFENZHENGHAO","XUEHAO","XINGMING","XINGBIE","WHKXUEXIAO","RXNF","BANXING","BANJI","DORM_TYPE_NAME","ROOM_NAME","RZSJ"};
		List<PageData> varOList=dormitoryInfoService.ExportDormhistoryInfo(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "历史住宿明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
		
	@RequestMapping(value="/toduidiao.json")
	public ModelAndView to_duidiao() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pkid", pd.getString("pkid"));
		mv.addObject("tiaosu", pd.getString("tiaosu"));
		mv.addObject("sd_sex", pd.getString("SD_SEX"));
		mv.addObject("student_pkid",pd.getString("student_pkid"));
		mv.addObject("dorm_type_pkid",pd.getString("T_STUDENT_DORM_TYPE_PKID"));
		mv.addObject("WHKXUEXIAOPKID",pd.getString("WHKXUEXIAO"));
		mv.addObject("BANXING",pd.getString("BANXING"));
		//pd.put("UserAllDormDepts", this.getUserAllDormDepts());
		List<PageData> dormList = dormitoryInfoService.getDormTypeList(null);
		mv.addObject("dormList", dormList);
		mv.setViewName("system/dormitoryInfo/dormitoryInfo_exchange");
		return mv;
	}
	
	@RequestMapping(value="/duidiao.json")
	public ModelAndView duidiao() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		dormitoryInfoService.updateStuDorm(pd);
		dormitoryInfoService.saveStuDorm(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:退宿</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月30日 上午9:10:03
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tuisu.json")
	public ModelAndView tuisu() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		dormitoryInfoService.updateTuisu(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:批量退宿</p>
	 * @author Administrator 康程亮
	 * @date 2018年4月02日 上午9:10:03
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/batchTuiSu.json")
	public ModelAndView batchTuiSu() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String [] pkid = pd.getString("pkids").split(",");
		String [] bmpkid = pd.getString("bmpkids").split(",");
		String [] stupkids = pd.getString("stupkids").split(",");
		String [] types = pd.getString("types").split(",");
		//String [] t_studep_pkid = pd.getString("t_studep_pkid").split(",");
		System.out.println(pkid.length + stupkids.length + types.length);
		if(pkid.length>0 && stupkids.length>0 && types.length>0 && bmpkid.length>0){
			for (int i = 0; i < pkid.length; i++) {
				pd.put("pkid", pkid[i]);
				pd.put("T_STUDENT_BM_PKID", bmpkid[i]);
				pd.put("t_student_pkid", stupkids[i]);
				pd.put("T_STUDENT_DORM_TYPE_PKID", types[i]);
				//pd.put("t_studep_pkid", t_studep_pkid[i]);
				dormitoryInfoService.updateTuisu(pd);
			}
			map.put("result", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:去分配页面</p>
	 * @author Administrator 康程亮
	 * @date 2018年4月2日 上午9:13:07
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tofenpei.json")
	public ModelAndView tofenpei() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pkid", pd.getString("pkid"));
		mv.addObject("t_student_dorm_type_pkid", pd.getString("t_student_dorm_type_pkid"));
		mv.addObject("sd_sex", pd.getString("SD_SEX"));
		mv.addObject("dept_id",pd.getString("dept_id"));
		mv.addObject("WHKXUEXIAO",pd.getString("WHKXUEXIAO"));
		mv.addObject("BANXING",pd.getString("BANXING"));
		mv.setViewName("system/dormitoryInfo/dormitoryInfo_fenpei");
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:fenpeitaoble</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月20日 上午11:38:39
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getfenpeistutable.json")
	public ModelAndView getfenpeistutable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//array用于存放院校专业的id

		
		
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		
		List<PageData> stuList =null;
		List<PageData> stuList_new =null;
			page.setPd(pd);
			stuList = dormitoryInfoService.getfenpeiStuInfoList(page);
			//stuList_new = dormitoryInfoService.getStuInfoList(pd);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", stuList);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:宿舍table</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月16日 上午9:48:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getstutable.json")
	public ModelAndView getstutable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//array用于存放院校专业的id

		
		
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		
		List<PageData> stuList =null;
		List<PageData> stuList_new =null;
			page.setPd(pd);
			stuList = dormitoryInfoService.getStuInfoList(page);
			//stuList_new = dormitoryInfoService.getStuInfoList(pd);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", stuList);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:分配</p>
	 * @author Administrator 康程亮
	 * @date 2018年4月02日 上午10:00:03
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fenpei.json")
	public ModelAndView fenpei() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		dormitoryInfoService.updatefenpei(pd);
		//stuInfoService.sendMessage(pd.getString("stuId"), pd.getString("dormId"));
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:tiaosu</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月5日 上午8:45:06
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tiaosu.json")
	public ModelAndView tiaosu() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData newpd = new PageData();
		PageData logpd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		dormitoryInfoService.updatetiaosu(pd);
		 newpd = dormitoryInfoService.getdorminfo(pd);
		 
		 
		 if(newpd!=null){
			 logpd.put("T_STUDENT_PKID", newpd.getString("T_STUDENT_PKID"));
			 logpd.put("ROOM_PKID", newpd.getString("PKID"));
			 logpd.put("USED_TYPE", "1");
			 logpd.put("HANDLE_RESOURCE", "调宿新宿舍信息由宿舍"+pd.getString("dormId")+"调换到当前宿舍");
			 logpd.put("BANXING", newpd.getString("CLASSTYPE_ID"));
			 logpd.put("HZXX", newpd.getString("PARTSCHOOL_ID"));
			}
		 logpd.put("T_STUDENT_BM_PKID", pd.getString("T_STUDENT_BM_PKID"));
		 
		 
		dormitoryInfoService.savelog(logpd);
		//dormitoryInfoService.updatetiaosuold(pd);
		//stuInfoService.sendMessage(pd.getString("stuId"), pd.getString("dormId"));
		map.put("result", "success");
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
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData userDormDepartments = dormitoryInfoService.getUserDormDepartments(user.getUSER_ID());
		if(userDormDepartments!=null){
			//代表分配了权限
			String[] str = userDormDepartments.getString("DEPARTMENT_PKID").split(",");
			List<PageData> userDormInfoDepts = dormitoryInfoService.getUserDormInfoDepts(str);
			return userDormInfoDepts;
		}
		return null;
	}
	
	public  List<PageData> getUserAllDormDepts() throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData userDormDepartments = dormitoryInfoService.getUserDormDepartments(user.getUSER_ID());
		if(userDormDepartments!=null){
			//查看当前分配的院校专业下有哪些宿舍
			userDormDepartments.getString("DEPARTMENT_PKID").split(",");
			List<PageData> userAllDepts = dormitoryInfoService.getDormDepts(userDormDepartments.getString("DEPARTMENT_PKID"));
			if(userAllDepts!=null && userAllDepts.size()>0){
//				return dormitoryInfoService.getUserAllDeptsBeds(userAllDepts);
				return userAllDepts;
			}
		}
		return null;
	}
	

	/**
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2018年4月2日 下午1:46:06
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel.json")
	public ModelAndView daoChu(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		if(this.getUserAllDepts()!=null){		
			pd.put("UserAllDormDepts", this.getUserAllDepts());	
		}	
		String[] dorm_array = pd.getString("dorm_pkid").split(",");
		pd.put("array", dorm_array);
		String[] headerNames = new String[]{"宿舍","性别","宿舍类型","身份证","学号","姓名","班型","班级","入学年份","文化课学校","是否办理入住"};
		String[] bodyNames = new String[]{"ROOM_NAME","SD_SEX","DORM_TYPE_NAME","SHENFENZHENGHAO","XUEHAO","XINGMING","BANXING","BANJI","RXNF","WHKXX","IS_RZ"};
		List<PageData> varOList=dormitoryInfoService.ExportDormInfo(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "宿舍信息");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 
	 * <p>描述:去预定分配页面</p>
	 * @author Administrator 王宁
	 * @date 2018年4月3日 上午8:27:05
	 * @return
	 */
	@RequestMapping(value="/dormOrder_list.php")
	public ModelAndView dormOrder_list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//文化课学校列表
		List<PageData> list_wenhuake = dormitoryInfoService.getWhkList(pd);
		//班型列表
		List<PageData> list_classType = dormitoryInfoService.getClassTypeList(pd);
		//入学年份呢
		List<PageData> ruxuenianfenList = dormitoryInfoService.getRuxuenianfenList(pd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date  = new Date();
		String totalYear = sdf.format(date);
		pd.put("totalYear", totalYear);
		mv.addObject("pd", pd);
		mv.addObject("list_wenhuake", list_wenhuake);
		mv.addObject("list_classType", list_classType);
		mv.addObject("ruxuenianfenList", ruxuenianfenList);
		mv.setViewName("system/dormitoryInfo/dormOrder_list");
		return mv;
	}
	@ResponseBody
	@RequestMapping("dormOrderTable.json")
	public ModelAndView dormOrderTable(Page page)throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list =null;
		list = dormitoryInfoService.dormOrderlistPage(page);//预定分配表格数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 王宁
	 * @date 2018年4月3日 下午2:18:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/godiviDorm.json")
	public ModelAndView godiviDorm() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> dormList = new ArrayList<>();
		dormList = this.getUserAllDormDeptsBySex(pd);
		mv.addObject("pd", pd);
		mv.addObject("dormlist", dormList);
		mv.setViewName("system/dormitoryInfo/diviDorm");
		return mv;
	}
	
	
	@RequestMapping(value="/gotiaosuDorm.json")
	public ModelAndView gotiaosuDorm() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> dormList = new ArrayList<>();
		pd.put("ALLOT_TYPE", "0");
		pd.put("STATUS", "0");
		//dormList = this.gettiaosuUserAllDormDeptsBySex(pd);
		mv.addObject("pd", pd);
		//mv.addObject("dormlist", dormList);
		mv.setViewName("system/dormitoryInfo/tiaosuDorm");
		return mv;
	}
	/**
	 * 
	 * <p>描述:异步加载树</p>
	 * @author ning 王宁
	 * @date 2018年6月21日 上午8:54:14
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("diviDormTree.json")
	public ModelAndView diviDormTree()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ALLOT_TYPE", "0");
		pd.put("STATUS", "0");
		List<PageData> dormList = new ArrayList<>();
		dormList = this.getUserAllDormDeptsBySex(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("dormList", dormList);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStudentDormPlanTreeForAllot")
	public ModelAndView getStudentDormPlanTreeForAllot() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户
		//pd.put("USER_ID", this.getLoginUSER_ID());
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		
		int chuangCount = 0;
		int chuangCount_use = 0;
		//获得当前登录用户所拥有权限的院校专业下拉框树
		pd.put("STATUS", "0");
		
		List<VO> studentDormList = studentDormService.gettiaosuStudentDormTree(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			String jsonStr = "";
			VO college = studentDormService.getCollege(pd);
			jsonStr += "{\"id\":\""+college.getId()+"\", \"pId\":\""+college.getParentId()+"\", \"name\":\""+college.getName()+"\", \"isXM\":\""+college.getIsXM()+"\" ,\"nocheck\":\"true\"},";
			for (int i = 0; i < studentDormList.size(); i++) {
				//如果是床
				if("5".equals(studentDormList.get(i).getType())){
					/*chuangCount ++;
					//如果已占用
					if("1".equals(studentDormList.get(i).getState())){
						chuangCount_use ++;
					}*/
					jsonStr += "{\"id\":\""+studentDormList.get(i).getId()+"\", \"pId\":\""+studentDormList.get(i).getParentId()+"\", \"name\":\""+studentDormList.get(i).getName()+"\", \"isXM\":\""+studentDormList.get(i).getIsXM()+"\", \"nocheck\":\"false\"},";
				}else{
					jsonStr += "{\"id\":\""+studentDormList.get(i).getId()+"\", \"pId\":\""+studentDormList.get(i).getParentId()+"\", \"name\":\""+studentDormList.get(i).getName()+"\", \"isXM\":\""+studentDormList.get(i).getIsXM()+"\", \"nocheck\":\"true\"},";

				}
				
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
	public  List<PageData> getUserAllDormDeptsBySex(PageData pd) throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData pd_dorm = null;
		List<PageData> userAllDepts = new ArrayList<>(); 
		pd.put("USER_ID", user.getUSER_ID());
		List<VO> studentDormList = studentDormService.getStudentDorminfotree(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			for (int i = 0; i < studentDormList.size(); i++) {
				pd_dorm = new PageData();
				pd_dorm.put("ID", studentDormList.get(i).getId());
				pd_dorm.put("PARENT_ID", studentDormList.get(i).getParentId());
				pd_dorm.put("NAME", studentDormList.get(i).getName());
				pd_dorm.put("type", studentDormList.get(i).getType());
				userAllDepts.add(pd_dorm);
			}
			return userAllDepts;
		}else{
			return null;
		}
	}
	
	public  List<PageData> getUserAllDormDeptsstuinfo(PageData pd) throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData pd_dorm = null;
		List<PageData> userAllDepts = new ArrayList<>(); 
		pd.put("USER_ID", user.getUSER_ID());
		List<VO> studentDormList = studentDormService.getStudentDorminfotreestu(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			for (int i = 0; i < studentDormList.size(); i++) {
				pd_dorm = new PageData();
				pd_dorm.put("ID", studentDormList.get(i).getId());
				pd_dorm.put("PARENT_ID", studentDormList.get(i).getParentId());
				pd_dorm.put("NAME", studentDormList.get(i).getName());
				pd_dorm.put("type", studentDormList.get(i).getType());
				userAllDepts.add(pd_dorm);
			}
			return userAllDepts;
		}else{
			return null;
		}
	}
	
	public  List<PageData> gettiaosuUserAllDormDeptsBySex(PageData pd) throws Exception{
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		PageData pd_dorm = null;
		List<PageData> userAllDepts = new ArrayList<>(); 
		pd.put("USER_ID", user.getUSER_ID());
//		pd.put("ALLOT_TYPE", "0");
//		pd.put("STATUS", "0");
		List<VO> studentDormList = studentDormService.getStudentDormPlanTreeForAllotBySex(pd);
		List<VO> schoolsList = studentDormService.getSchoolsListPlanTree(pd);
		if(studentDormList != null && studentDormList.size() > 0){
			schoolsList.addAll(studentDormList);
			for (int i = 0; i < schoolsList.size(); i++) {
				pd_dorm = new PageData();
				pd_dorm.put("ID", schoolsList.get(i).getId());
				pd_dorm.put("PARENT_ID", schoolsList.get(i).getParentId());
				pd_dorm.put("NAME", schoolsList.get(i).getName());
				pd_dorm.put("type", schoolsList.get(i).getType());
				userAllDepts.add(pd_dorm);
			}
			return userAllDepts;
		}else{
			return null;
		}
	}
	@ResponseBody
	@RequestMapping("diviDorm.json")
	public ModelAndView diviDorm()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String[] stu_pkids = pd.getString("pkids").split(",");
		int stu_length = Integer.parseInt(pd.getString("stu_lenght"));
		int dorm_length = Integer.parseInt(pd.getString("dorm_length"));
		//将宿舍按照宿舍名字排序
		List<PageData> list_dorm = dormitoryInfoService.getDormOrder(pd);
		PageData pd_stuDorm = null;
//		PageData pd_type = null;
		if(stu_length>=dorm_length){//学生数大于等于宿舍数
			for(int i=0;i<list_dorm.size();i++){
				pd_stuDorm = new PageData();
				pd_stuDorm.put("DORM_PKID", list_dorm.get(i).getString("PKID"));
//				pd_type = dormitoryInfoService.getDormType(pd_stuDorm);
				pd_stuDorm.put("STUDENT_BM_PKID", stu_pkids[i]);
//				pd_stuDorm.put("T_STUDENT_DORM_TYPE_PKID", pd_type.getString("T_STUDENT_DORM_TYPE_PKID"));
				dormitoryInfoService.updateDormStu(pd_stuDorm);
			}
		}else if(stu_length<dorm_length){//学生数小于宿舍数
			for(int i=0;i<stu_pkids.length;i++){
				pd_stuDorm = new PageData();
				pd_stuDorm.put("DORM_PKID", list_dorm.get(i).getString("PKID"));
//				pd_type = dormitoryInfoService.getDormType(pd_stuDorm);
				pd_stuDorm.put("STUDENT_BM_PKID", stu_pkids[i]);
//				pd_stuDorm.put("T_STUDENT_DORM_TYPE_PKID", pd_type.getString("T_STUDENT_DORM_TYPE_PKID"));
				dormitoryInfoService.updateDormStu(pd_stuDorm);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}


	
	
	/**
	 * 
	 * <p>描述:宿舍信息下拉树</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentDormTreeList")
	public ModelAndView getStudentDormTreeList() throws Exception{
		PageData pd = new PageData();
		pd = super.getPageData();
		
		//当前登录用户				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		
		//获得当前登录用户所拥有权限的院校专业下拉框树
//		pd.put("STATUS", "0");
			
		
		/*
		 * 宿舍列表
		 */
		List<PageData> dormlist=null;
		pd.put("UserAllDormDepts", this.getUserAllDormDepts());
		PageData pd_dorm = new PageData();
	//	pd_dorm.put("ALLOT_TYPE", "0");//未分配
		dormlist=this.getUserAllDormDeptsstuinfo(pd);
		
//		List<VO> studentDormList = studentDormService.getStudentDormPlanTreeForRecovery(pd);
		if(dormlist != null && dormlist.size() > 0){
			String jsonStr = "";			
//			VO college = studentDormService.getCollege(pd);
//			jsonStr += "{\"id\":\""+college.getId()+"\", \"pId\":\""+college.getParentId()+"\", \"name\":\""+college.getName()+"\", \"isXM\":\""+college.getIsXM()+"\"},";
			for (int i = 0; i < dormlist.size(); i++) {
				jsonStr += "{\"id\":\""+dormlist.get(i).getString("ID")+"\", \"pId\":\""+dormlist.get(i).getString("PARENT_ID")+"\", \"name\":\""+dormlist.get(i).getString("NAME")+"\", \"type\":\""+dormlist.get(i).getString("type")+"\"},";
			}
			jsonStr = "["+jsonStr+"]";
			
			map.put("studentDormTreeJsonData", jsonStr);
		}else{
			map.put("studentDormTreeJsonData", "");
		}
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
}





































