package com.fh.controller.system.stuinfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.fh.service.system.basicclass.BasicClassManager;
import com.fh.service.system.partschool.PartSchoolManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.ImgUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.TableColums;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.util.Tools;
/**
 * 
 * <p>标题:StuInfoController</p>
 * <p>描述:后台-学生信息控制器</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator wzz
 * @date 2018年11月27日 上午8:25:41
 */
@Controller
@RequestMapping(value="/stuinfo")
public class StuInfoController extends BaseController{
	
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	
	@Resource(name="basicclassService")
	private BasicClassManager basicclassService;
	
	@Resource(name="partSchoolService")
	private PartSchoolManager partSchoolService;
	/**
	 * 
	 * <p>描述:去学生列表导航页面</p>
	 * @author Administrator wzz
	 * @date 2017年8月2日 上午8:32:09
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/stu_frame.php")
	public ModelAndView toStuframe() {
		ModelAndView mv = this.getModelAndView();
		//PageData pd = new PageData();
		
		/*
		 * 获取当前用户
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		//获取当前登录用户学校英文名称
		String COLLEGES_NAME_EN = user.getCOLLEGES_NAME_EN();
		//end 获取当前用户
		
		/*
		 * 获取当前用户权限
		 */
		List<Menu> allmenuList = (List<Menu>) session.getAttribute(username + Const.SESSION_allmenuList);
		List<Menu> authorityManageMenus = null;
		for (Menu menu : allmenuList) {
			if ("4".equals(menu.getMENU_ID())) {//学生信息   是 4
				//权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		mv.addObject("COLLEGES_NAME_EN", COLLEGES_NAME_EN);
		//end 获取当前用户权限
		
		mv.setViewName("system/stuinfo/stu_frame");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:去学生列表页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 上午8:32:09
	 * @return
	 */
	@RequestMapping(value="/stuinfo_list.php")
	public ModelAndView toStuinfo_list() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String colStr="";	
		List<PageData> gradelist=null;//入学年份
		List<PageData> banXinglist=null;//班型
		List<PageData> partSchoollist=null;//合作学校
		List<PageData> zxztlist=null;//在学状态
		List<PageData> cengci_list = null;//层次
		List<PageData> meiyuanlist = null;//美院

		
		try {
			pd.put("PARENT_ID", "GRADE");
			gradelist = basicclassService.getFromSYS_DICT(pd);//入学年份
			pd.put("PARENT_ID", "CLASSTYPE");
			banXinglist = basicclassService.getFromSYS_DICT(pd);//班型
			pd.put("ZHUANGTAI", "1");
			partSchoollist = stuInfoService.getPartSchoolList(pd);//合作学校
			pd.put("PARENT_ID", "ZXZT");
			zxztlist = basicclassService.getFromSYS_DICT(pd);//在学状态
			pd.put("PARENT_ID", "ZXZT");
			zxztlist = basicclassService.getFromSYS_DICT(pd);//在学状态
			cengci_list = stuInfoService.getCengCi(pd);//层次
			meiyuanlist = stuInfoService.getMeiYuan(pd);//美院
			pd.put("table_name", "T_STUDENT");
			colStr = TableColums.currentUserTableShowCollums("T_STUDENT");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("colStr",colStr);
		mv.addObject("gradelist",gradelist);
		mv.addObject("banXinglist",banXinglist);
		mv.addObject("partSchoollist",partSchoollist);
		mv.addObject("zxztlist",zxztlist);
		mv.addObject("cengci_list",cengci_list);
		mv.addObject("meiyuanlist",meiyuanlist);
		mv.addObject("pd", pd);
		mv.setViewName("system/stuinfo/stuinfo_list");	
		return mv;
	}
	/**
	 * 
	 * <p>描述:学生列表表格数据</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午1:41:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getstutable.json")
	public ModelAndView stutablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//年纪
		pd.put("gradeArray", pd.getString("grade_str").split(","));
		//班型
		pd.put("banxingArray", pd.getString("banxing_str").split(","));
		//班级
		pd.put("banjiArray", pd.getString("banji_str").split(","));
		//合作学校
		pd.put("partschoolArray", pd.getString("partschool_str").split(","));
		//层次
		pd.put("cengciArray", pd.getString("cengci_str").split(","));
		//预交年份
		pd.put("yjgradeArray", pd.getString("yjgrade_str").split(","));
		//校考成绩
		pd.put("meiyuanArray", pd.getString("meiyuan_str").split(","));
		//将学号变成大写
		String XUEHAOQ = pd.getString("XUEHAOQ").toUpperCase();;//学号起
		String XUEHAOZ = pd.getString("XUEHAOZ").toUpperCase();;//学号止
		pd.put("XUEHAOQ", XUEHAOQ);
		pd.put("XUEHAOZ", XUEHAOZ);
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list =null;
		this.logger.error("开始进行列表查询！！！！！！！！！！");
		System.out.println("排序列：" + pd.getString("sortName") + " 排序方式："+pd.getString("sortOrder"));
		list = stuInfoService.stuinfo_list(page);
		this.logger.error("列表查询已经走完！！！！！！！！！！！！！");
		if(list!=null&&list.size()>0){
			String xkMarks = "";
			String kemus = "";
			for(int i = 0;i<list.size();i++){
				//循环遍历处理校考成绩为中文名称
				String xkMarkNames = "";
				xkMarks = list.get(i).getString("XK_MARK");
				if(xkMarks!=null&&!"".equals(xkMarks)){
					String [] xkMarkArr = xkMarks.split(",");
					PageData pdMy = null;
					for(String xkMark : xkMarkArr){
						if(!Tools.isEmpty(xkMark)){
							pdMy = stuInfoService.getDepListById(xkMark);
							this.logger.error("联考分数查询已经走完~~~~~~~~~");
							if(pdMy!=null&&!"".equals(pdMy.getString("NAME"))&&pdMy.getString("NAME")!=null){
								xkMarkNames+=pdMy.getString("NAME")+",";
							}
						}
					}
				}
				if(!Tools.isEmpty(xkMarkNames)){
					xkMarkNames = xkMarkNames.substring(0, xkMarkNames.length()-1);
				}
				list.get(i).put("XK_MARK", xkMarkNames);
				//循环遍历处理科目为中文名称
				String kemuNames = "";
				kemus = list.get(i).getString("KEMU");
				if(!Tools.isEmpty(kemus)){
					String [] kemuArr = kemus.split(",");
					PageData pdMy = null;
					PageData pdParam = new PageData();
					for(String kemu : kemuArr){
						if(!Tools.isEmpty(kemu)){
							pdParam.put("PKID", kemu);
							pdMy = stuInfoService.getSubById(pdParam);
							this.logger.error("科目查询已经走完~~~~~~~~~~~");
							if(pdMy!=null&&!Tools.isEmpty(pdMy.getString("NAME"))){
								kemuNames+=pdMy.getString("NAME")+",";
							}
						}
					}
				}
				if(!Tools.isEmpty(kemuNames)){
					kemuNames = kemuNames.substring(0, kemuNames.length()-1);
				}
				list.get(i).put("KEMU", kemuNames);
			}
			
		}
		this.logger.error("整个查询已经走完了~~~~~~~~~~~");
		Map<String, Object> map = new HashMap<String, Object>();        
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	@RequestMapping(value = "/updateShowCols")
	@ResponseBody
	public ModelAndView updateShowCols() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		/*
		 * 存在，更新；不存在，新增
		 */
		/*
		 * 获取当前用户
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", user.getUSER_ID());
//		pd.put("table_name", "T_STUDENT");
		PageData currentUserTableShowCollums = stuInfoService.getCurrentUserTableShowCollums(pd);
		if (currentUserTableShowCollums!=null) {
			//更新
			stuInfoService.updateShowCols(pd);
		}else{
			//新增
			stuInfoService.saveShowCols(pd);
		}
		/*
		 * 更新session
		 */
		pd.put("USER_ID", user.getUSER_ID());
		List<PageData> currentUserTableShowCollumsList = stuInfoService.getCurrentUserTableShowCollumsList(pd);
		session.setAttribute("currentUserTableShowCollumsList", currentUserTableShowCollumsList);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:查询班级列表</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午1:41:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBanJi.json")
	@ResponseBody
	public ModelAndView getBanJi() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> banJiList = stuInfoService.getBanJi(pd);
		jsonmap.put("banJiList", banJiList);
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:去查看详情页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日14:53:34
	 * @return
	 */
	@RequestMapping(value="/goDetail.json")
	public ModelAndView goDetail() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> xkmarkList = new ArrayList<PageData>();
		List<PageData> kemuList = new ArrayList<PageData>();

		try {
			PageData stuPd=stuInfoService.getStuInfoByPkid(pd);
			//校考成绩
			String xkmarks = stuPd.getString("XK_MARK");
			if(xkmarks!=null&&!"".equals(xkmarks)){
				String [] xkmarksArr = xkmarks.split(",");				
				PageData pdArr = null;
				for(int i = 0; i < xkmarksArr.length; i++){
					pdArr = stuInfoService.getDepListById(xkmarksArr[i]);
					xkmarkList.add(pdArr);
				}
			}
			
			//已学过的科目
			String kemus = stuPd.getString("KEMU");
			if(kemus!=null&&!"".equals(kemus)){
				String [] kemusArr = kemus.split(",");
				
				for(int i = 0; i < kemusArr.length; i++){
					PageData pdArr1 = new PageData();
					pdArr1.put("BIANMA", kemusArr[i]);
					kemuList.add(pdArr1);
				}
			}
			
			//校考成绩
			pd.put("IS_QY", "Y");
			List<PageData> xkcjList = stuInfoService.getDepList(pd);
			mv.addObject("pd",stuPd);
			mv.addObject("xkmarkList",xkmarkList);
			mv.addObject("kemuList",kemuList);
			mv.addObject("xkcjList",xkcjList);
			mv.setViewName("system/stuinfo/detailStu");
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * 去编辑页面
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午8:50:08
	 * @return
	 */
	@RequestMapping(value="/goEdit.json")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> gradelist=null;//入学年份
		List<PageData> gradelist2=null;//预交入学年份
		List<PageData> banXinglist=null;//班型
		List<PageData> banXinglist2=null;//预交班型
		List<PageData> partSchoollist=null;//合作学校
		List<PageData> zxztlist=null;//在学状态
		List<PageData> cengci_list = null;//层次
		List<PageData> meiyuanlist = null;//美院
		List<PageData> jtgxList = null;//家庭关系
		List<PageData> jtgxList2 = null;//家庭关系
		List<PageData> ljqhtjList = null;//了解强化途径
		List<PageData> kemuArrList = null;//科目
		List<PageData> kemuList = new ArrayList<PageData>();
		try {
			//校考成绩
			pd.put("IS_QY", "Y");
			List<PageData> xkcjList = stuInfoService.getDepList(pd);
			kemuArrList = stuInfoService.getLearnSubList(pd);
			meiyuanlist = stuInfoService.getMeiYuan(pd);//美院
			PageData stuPd=stuInfoService.getStuInfoByPkid(pd);	//学生信息详情
			String xkmarks = stuPd.getString("XK_MARK");
			String [] xkmarksArr = xkmarks.split(",");
			List<PageData> xkmarkList = new ArrayList<PageData>();
			PageData pdArr = null;
			for(int i = 0; i < xkmarksArr.length; i++){
				pdArr = stuInfoService.getDepListById(xkmarksArr[i]);
				xkmarkList.add(pdArr);
				meiyuanlist = returnResultList(meiyuanlist,"PKID",xkmarksArr[i],"4");
			}
			mv.addObject("pd",stuPd);
			pd.put("is_use_d", "1");
			pd.put("PARENT_ID", "GRADE");
			gradelist = basicclassService.getFromSYS_DICT(pd);//入学年份
			gradelist = returnResultList(gradelist,"DICTIONARIES_ID",stuPd.getString("RXNIANFEN_PKID"),"1");
			gradelist2 = returnResultList(gradelist,"DICTIONARIES_ID",stuPd.getString("YJ_NIANFEN"),"1");
			pd.put("PARENT_ID", "CLASSTYPE");
			banXinglist = basicclassService.getFromSYS_DICT(pd);//班型
			banXinglist = returnResultList(banXinglist,"DICTIONARIES_ID",stuPd.getString("BANJI_TYPE_PKID"),"1");
			
			pd.put("ZHUANGTAI", "1");
			partSchoollist = stuInfoService.getPartSchoolList(pd);//合作学校
			partSchoollist = returnResultList(partSchoollist,"PKID",stuPd.getString("WHKXUEXIAO"),"2");
			pd.put("PARENT_ID", "ZXZT");
			zxztlist = basicclassService.getFromSYS_DICT(pd);//在学状态
			pd.put("PARENT_ID", "FAMILY");
			jtgxList = basicclassService.getFromSYS_DICT(pd);//家庭关系
			jtgxList = returnResultList(jtgxList,"DICTIONARIES_ID",stuPd.getString("ONE_JHRGX_PKID"),"1");
			jtgxList2 = returnResultList(jtgxList,"DICTIONARIES_ID",stuPd.getString("TWO_JHRGX_PKID"),"1");
			pd.put("PARENT_ID", "UNDWAY");
			ljqhtjList = basicclassService.getFromSYS_DICT(pd);//了解强化途径
			ljqhtjList = returnResultList(ljqhtjList,"DICTIONARIES_ID",stuPd.getString("LJQHTJ_PKID"),"1");
			cengci_list = stuInfoService.getCengCi(pd);//层次
			pd.put("SFQY", "1");
			pd.put("PARENT_ID", "CLASSTYPE");
			banXinglist2 = basicclassService.getFromSYS_DICT(pd);//班型
			banXinglist2 = returnResultList(banXinglist2,"DICTIONARIES_ID",stuPd.getString("YJ_BANJI_TYPE_PKID"),"1");
			
			
			//已学过的科目
			String kemus = stuPd.getString("KEMU");
			if(kemus!=null&&!"".equals(kemus)){
				String [] kemusArr = kemus.split(",");
				
				for(int i = 0; i < kemusArr.length; i++){
					PageData pdArr1 = new PageData();
					pdArr1.put("BIANMA", kemusArr[i]);
					kemuList.add(pdArr1);
					kemuArrList = returnResultList(kemuArrList,"PKID",kemusArr[i],"3");
				}
			}
			mv.addObject("xkmarkList",xkmarkList);
			mv.addObject("xkcjList",xkcjList);
			mv.addObject("kemuList",kemuList);
			mv.addObject("kemuArrList",kemuArrList);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		mv.addObject("gradelist",gradelist);
		mv.addObject("gradelist2",gradelist2);
		mv.addObject("banXinglist",banXinglist);
		mv.addObject("banXinglist2",banXinglist2);
		mv.addObject("partSchoollist",partSchoollist);
		mv.addObject("zxztlist",zxztlist);
		mv.addObject("cengci_list",cengci_list);
		mv.addObject("meiyuanlist",meiyuanlist);		
		mv.addObject("jtgxList",jtgxList);		
		mv.addObject("jtgxList2",jtgxList2);		
		mv.addObject("ljqhtjList",ljqhtjList);		

		mv.setViewName("system/stuinfo/editstu");
		return mv;
	}
	/**
	 * 
	 * <p>描述:学生信息导出</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 上午11:04:22
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
		//入学年份
		pd.put("gradeArray", pd.getString("grade_str").split(","));
		//班型
		pd.put("banxingArray", pd.getString("banxing_str").split(","));
		//班级
		pd.put("banjiArray", pd.getString("banji_str").split(","));
		//合作学校
		pd.put("partschoolArray", pd.getString("partschool_str").split(","));
		//层次
		pd.put("cengciArray", pd.getString("cengci_str").split(","));
		//预交年份
		pd.put("yjgradeArray", pd.getString("yjgrade_str").split(","));
		//校考成绩
		pd.put("meiyuanArray", pd.getString("meiyuan_str").split(","));
		//页面传过来需要展示的字段判断有的从map里取出来字段和属性值一一对应
		Map<String, String> resultMap = returnMapList();
		String[] bodyNames = ((String) pd.get("resultColumns")).split(",");
		String[] headerNames = new String[bodyNames.length];
		for (int i = 0; i < bodyNames.length; i++) {
			headerNames[i] = resultMap.get(bodyNames[i]);
		}
		List<PageData> varOList =null;
		//将学号变成大写
		String XUEHAOQ = pd.getString("XUEHAOQ").toUpperCase();;//学号起
		String XUEHAOZ = pd.getString("XUEHAOZ").toUpperCase();;//学号止
		pd.put("XUEHAOQ", XUEHAOQ);
		pd.put("XUEHAOZ", XUEHAOZ);
		varOList=stuInfoService.exportStuInfoToExcel(pd);
		if(varOList!=null&&varOList.size()>0){
			String xkMarks = "";
			String kemus = "";
			for(int i = 0;i<varOList.size();i++){
				//循环遍历处理校考成绩为中文名称
				String xkMarkNames = "";
				xkMarks = varOList.get(i).getString("XK_MARK");
				if(xkMarks!=null&&!"".equals(xkMarks)){
					String [] xkMarkArr = xkMarks.split(",");
					PageData pdMy = null;
					for(String xkMark : xkMarkArr){
						if(xkMark!=null&&!"".equals(xkMark)){
							pdMy = stuInfoService.getDepListById(xkMark);
							if(pdMy!=null&&!"".equals(pdMy.getString("NAME"))&&pdMy.getString("NAME")!=null){
								xkMarkNames+=pdMy.getString("NAME")+",";
							}
						}
					}
				}
				if(!"".equals(xkMarkNames)){
					xkMarkNames = xkMarkNames.substring(0, xkMarkNames.length()-1);
				}
				varOList.get(i).put("XK_MARK", xkMarkNames);
				//循环遍历处理科目为中文名称
				String kemuNames = "";
				kemus = varOList.get(i).getString("KEMU");
				if(kemus!=null&&!"".equals(kemus)){
					String [] kemuArr = kemus.split(",");
					PageData pdMy = null;
					PageData pdParam = new PageData();
					for(String kemu : kemuArr){
						if(kemu!=null&&!"".equals(kemu)){
							pdParam.put("PKID", kemu);
							pdMy = stuInfoService.getSubById(pdParam);
							if(pdMy!=null&&!"".equals(pdMy.getString("NAME"))&&pdMy.getString("NAME")!=null){
								kemuNames+=pdMy.getString("NAME")+",";
							}
						}
					}
				}
				if(!"".equals(kemuNames)){
					kemuNames = kemuNames.substring(0, kemuNames.length()-1);
				}
				varOList.get(i).put("KEMU", kemuNames);
			}
			
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "学生信息");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	//将页面到所有字段一一对应放到map里
	public Map<String, String> returnMapList(){
		String[] headerNames = new String[]{"身份证","姓名","学号","性别","手机","家庭住址",//1
											"邮政编码","第一监护人","家庭关系","监护人电话","监护人单位","监护人职务","第二监护人",//2
											"家庭关系","监护人电话","监护人单位","监护人职务","病史","了解强化的途径","推荐人",//3
											"文化课学校","高一入学时间", "班级","文理科","学校班主任",//4
											"班主任电话","学生类型","自何时学习美术","已学过的科目","入学年份",//5
											"班型","预交班型","批次","预交年份","原班级",//6
											"联考分数","考生号","校考成绩", "是否报到","报到时间",//7
											"在学状态","审核状态","备注1","备注2","备注3","入住状态",
											"缴费总额","报名时间","高中班级","操作人","审核通过时间"};//8
		String[] bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","XUEHAO","XINGBIE","SHOUJI","JIATINGZHUZHI",//1
										  "YOUZHENGBIANMA","ONE_JHR","ONE_JHRGX","ONE_JHRDH","ONE_JHRDW","ONE_JHRZW","TWO_JHR",//2
										  "TWO_JHRGX","TWO_JHRDH","TWO_JHRDW","TWO_JHRZW","BINGSHI","LJQHTJ","TUIJIANREN",//3
										  "WHKXUEXIAONAME","GYRXSJ","BANJINAME","WENLIKE","BZRSCHOOL",//4
										  "BZRPHONE","CENGCI","STARTMEISHU","KEMU","RXNIANFEN",//5
										  "BANJI_TYPE","YJ_BANJI_TYPE","PICI","YJ_NIANFEN","OLD_BANJINAME",//6
										  "LKFS","KSNUMBER","XK_MARK","IS_BD","BD_DATE",//7
										  "ZXZT","IS_TONGGUO","REMARKS1","REMARKS2","REMARKS3","RZZT",
										  "TOTALMONEY","CJSJTM","GZ_BANJI","CAOZUOREN","SHTGSJ"};//8
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0, n=bodyNames.length; i < n; i++) {
			result.put(bodyNames[i], headerNames[i]);
		}
		return result;
	}
	
	/**
	 * 
	 * <p>描述:批量通过</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午5:27:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchTongGuo")
	@ResponseBody
	public ModelAndView batchTongGuo() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		if("1".equals(pd.getString("status"))){
			pd.put("SHTGSJ", "isNotNull");
		}
		stuInfoService.batchTongGuo(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:批量毕业或退学</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午5:27:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchByOrTuiXue")
	@ResponseBody
	public ModelAndView batchByOrTuiXue() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			stuInfoService.batchByOrTuiXue(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:批量复学</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午5:27:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchByFuXue")
	@ResponseBody
	public ModelAndView batchByFuXue() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			stuInfoService.batchByOrFuXue(pd);
			jsonmap.put("result", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:批量删除</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午2:01:15
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDel")
	@ResponseBody
	public ModelAndView batchdelete() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd_pkid=new PageData();
		String PKIDS = pd.getString("pkids");
		if(null != PKIDS && !"".equals(PKIDS)){
			String pkid_arr[] = PKIDS.split(",");
			boolean flag = false;
			for (String pkid : pkid_arr) {
				pd_pkid.put("PKID", pkid);
				if(stuInfoService.stuIsPaid(pd_pkid)==true){
					flag = true;
				}
				if(flag == true){
					jsonmap.put("result", "paid");
					return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
				}

			}
			pd.put("pkids", pkid_arr);
			stuInfoService.batchDelete(pd,pkid_arr);
			jsonmap.put("result", "success");
		}else{
			jsonmap.put("result", "fail");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	
	/**
	 * 
	 * <p>描述:新增保存功能</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月2日 下午3:18:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveedu.json")
	public ModelAndView save(Page page) throws Exception  {
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		
		// 编辑
		try {
			if(!pd.getString("SHENFENZHENGHAO").equals(pd.getString("sfzh"))){
				PageData pdRes = stuInfoService.getStuBySfzRnBx(pd);
				if(pdRes!=null){
					jsonmap.put("rst", "isExSfz");
				}else{
					stuInfoService.updateStu(pd);
					jsonmap.put("rst", "success");
				}
			}
		    else if(pd.getString("RXNIANFEN_PKID").equals(pd.getString("RXNIANFEN"))&&
					pd.getString("BANJI_TYPE_PKID").equals(pd.getString("BANJI_TYPE"))&&
					pd.getString("SHENFENZHENGHAO").equals(pd.getString("sfzh"))){
				stuInfoService.updateStu(pd);
				jsonmap.put("rst", "success");
			}else{
				PageData pdRe = stuInfoService.getStuBySfzRnBx(pd);
				if(pdRe!=null){
					jsonmap.put("rst", "isExist");
				}else{
					stuInfoService.updateStu(pd);
					jsonmap.put("rst", "success");
				}
			}
			
		} catch (Exception e) {
			jsonmap.put("rst", "error");
			e.printStackTrace();
		}


		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	/**
	 * 
	 * <p>描述:上传头像页面</p>
	 * @author Administrator wzz
	 * @date 2018年12月11日 上午8:33:21
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadtouxiang.json")
	public ModelAndView uploadTouXiang() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("text", "tx");
		mv.addObject("pd", pd);
		mv.setViewName("system/stuinfo/uploadtouxiang");
		return mv;
	}
	/**
	 * 
	 * <p>描述:保存头像</p>
	 * @author Administrator wzz
	 * @date 2018年12月11日 上午8:48:11
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/imgCrop")  
    @ResponseBody  
    public ModelAndView imgCrop(HttpServletRequest request) throws Exception{ 
    	PageData pd = new PageData();
		pd = this.getPageData();
        double x = Double.valueOf(pd.get("x").toString());  
        double y = Double.valueOf(pd.getString("y"));  
        double width = Double.valueOf(pd.getString("width"));  
        double height = Double.valueOf(pd.getString("height"));  
        String path = pd.getString("path");
        String realPath = request.getSession().getServletContext().getRealPath("/"); 
        String imgPath = realPath+path;
        String readImageFormat = path.substring(path.length()-3);
        String dirpath = realPath+"uploadFiles/tx/";
        File dir = new File(dirpath);
        if (!dir.exists()){  
            dir.mkdirs();  
        } 
        String empPath = dirpath+pd.getString("sfz")+"."+readImageFormat;
        String pathjz = "uploadFiles/tx/"+pd.getString("sfz")+"."+readImageFormat;
        pd.put("TOUXIANG", pathjz);
        ImgUtils.cropImage(imgPath, empPath, (int)x, (int)y,(int) width,(int) height, readImageFormat, readImageFormat);
        //保存照片到数据库
        stuInfoService.saveTouxiangUrlToDB(pd);
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("path", pathjz);
        data.put("pd", pd);
        return new  ModelAndView(new MappingJackson2JsonView(),data);  
    }
	
	


	 /**
	  * 
	  * <p>描述:保存头像到本地</p>
	  * @author Administrator wzz
	  * @date 2018年12月3日 上午9:43:33
	  * @param myfiles
	  * @param request
	  * @param response
	  * @throws Exception
	  */
	@RequestMapping(value = "/upload.json")
	public void upload(@RequestParam("upload-file") MultipartFile myfiles,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		PageData pd = this.getPageData();
		MultipartFile img = myfiles;
		String uPath = request.getSession().getServletContext()
				.getRealPath("/");
		String tempImg_path = "uploadFiles/tx/";
		File dir = new File(uPath + tempImg_path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String pic_path = null;
		String result = "0"; // --标识是否成功--
		int srcWidth = 0;
		int srcHeight = 0;
		if (img != null && !img.isEmpty()) {
			int index = img.getOriginalFilename().lastIndexOf('.');
			String picName = pd.getString("sfz") 
					+ img.getOriginalFilename().substring(index);
			pic_path = tempImg_path + picName;
			img.transferTo(new File(uPath + tempImg_path + picName));

			BufferedImage bi = ImageIO.read(new File(uPath + tempImg_path
					+ picName));
			srcWidth = bi.getWidth();
			srcHeight = bi.getHeight();

			result = "1";
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		data.put("path", pic_path);
		data.put("width", String.valueOf(srcWidth));
		data.put("height", String.valueOf(srcHeight));
		if(srcWidth<srcHeight){
			data.put("scale", ImgUtils.imgScale(srcWidth, srcHeight, 200, 300));
		}else{
			data.put("scale", ImgUtils.imgScale(srcWidth, srcHeight, 460, 400));
		}
		data.put("style", srcWidth >= srcHeight ? "width" : "height");
		PrintWriter printWriter = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		printWriter
				.write("<html><head><meta charset=\"utf-8\"/></head><body><pre>"
						+ json + "</pre></body></html>");
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 重置登录密码
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年12月11日 下午9:38:49
	 */
	@RequestMapping(value = "/resetpwd")
	@ResponseBody
	public ModelAndView resetPwd() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PWD = new SimpleHash("SHA-1", "","123456").toString();//密码加密
		pd.put("PWD", PWD);
		stuInfoService.resetPwd(pd);
		jsonmap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	
	/**
	 * 
	 * <p>描述:编辑为了加上未启用却被学生使用的配置项</p>
	 * @author Administrator wzz
	 * @date 2018年6月28日 上午10:11:47
	 * @param
	 * list 配置项查询的启用结果集
	 * flag 结果集中取哪个字段值
	 * value 学生信息查询出来的单个配置项结果
	 * type 查询配置项的值根据哪个字段查询查哪个表
	 * pkid 查询条件值
	 */
	public List<PageData>  returnResultList(List<PageData> list, String flag,String value,String type){
			List<String> reList = new ArrayList<String>();
			PageData pdResult = null;
			PageData pd = null;
			try {
				//将list结果集中的值取出来放到stringlist中
				for(PageData pdList : list){
					reList.add(pdList.getString(flag));
				}
				//判断stringlist中是否存在查询的结果如果不存在说明查询出来的结果是未启用状态
				if(!reList.contains(value)){
					PageData pdParam = new PageData();					
					//查字典表
					if(type == "1"){
						pdParam.put("dictionaries_id", value);
						//根据条件查询某个配置项的单个详细信息
						pdResult = stuInfoService.getDictionariesById(pdParam);						
						if(pdResult!=null){
							pd = new PageData();
							//将结果到list中
							pd.put("DICTIONARIES_ID", pdResult.get("DICTIONARIES_ID"));
							pd.put("NAME", pdResult.get("NAME"));
							list.add(pd);
						}
					}else if(type == "2"){//合作学校
						pdParam.put("PKID", value);
						pdResult = partSchoolService.getPartSchoolByPkid(pdParam);
						if(pdResult!=null){
							pd = new PageData();
							pd.put("PKID", pdResult.get("PKID"));
							pd.put("SCHOOLNAME", pdResult.get("SCHOOLNAME"));
							list.add(pd);
						}
					}else if(type == "3"){//科目
						pdParam.put("PKID", value);
						pdResult = stuInfoService.getSubById(pdParam);
						if(pdResult!=null){
							pd = new PageData();
							pd.put("PKID", pdResult.get("PKID"));
							pd.put("NAME", pdResult.get("NAME"));
							list.add(pd);
						}
					}else if(type == "4"){//校考成绩
						pdParam.put("PKID", value);
						pdResult = stuInfoService.getDepListById(value);
						if(pdResult!=null){
							pd = new PageData();
							pd.put("PKID", pdResult.get("PKID"));
							pd.put("NAME", pdResult.get("NAME"));
							list.add(pd);
						}
					}					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return list;	
	}
}
