package com.fh.controller.system.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.basicclass.BasicClassManager;
import com.fh.service.system.report.ReportManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;

/**
 * 
 * <p>标题:ReportController</p>
 * <p>描述:报表统计类</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author ning 王宁
 * @date 2018年10月18日 下午1:43:25
 */
@Controller
@RequestMapping(value="/report")
public class ReportController extends BaseController {
	
	@Resource(name = "reportService")
	private ReportManager reportService;
	@Resource(name="basicclassService")
	private BasicClassManager basicclassService;
	/**
	 * 
	 * <p>描述:跳转到报表统计导航页面</p>
	 * @author ning 王宁
	 * @date 2018年10月18日 下午1:50:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/reportStat.php")
	public ModelAndView reportStat() throws Exception{
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
			if ("3".equals(menu.getMENU_ID())) {//报表统计   是 3
				//权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		//end 获取当前用户权限
		mv.setViewName("system/reportStat/reportStat");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:跳转退费汇总</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日 上午9:39:46
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toRefundSumlist.php")
	public ModelAndView toRefundSumlist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//入学年份
		pd.put("PARENT_ID", "GRADE");
		List<PageData> schoolYearList = basicclassService.getFromSYS_DICT(pd);
		//班型
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> classTypeList = basicclassService.getFromSYS_DICT(pd);
		//获取缴费类型列表
		List<PageData> payStyleList = reportService.getPayStyleListAll(pd);
		mv.addObject("schoolYearList",schoolYearList);
		mv.addObject("classTypeList",classTypeList);
		mv.addObject("payStyleList",payStyleList);
		mv.addObject("pd", pd);
		mv.setViewName("system/report/refundSumlistTable");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:退费汇总表数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日 上午9:40:53
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getRefundSumlistTable.json")
	public ModelAndView getRefundSumlistTable()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_date=pd.getString("DATESTART");
		String end_date=pd.getString("DATEEND");
		if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse(start_date+" 00:00:00");
			Date end = sdf.parse(end_date+" 23:59:59");
			pd.put("DATESTART", sdf.format(start));
			pd.put("DATEEND", sdf.format(end));
			
		}
		List<PageData> list = null;
		list = reportService.getRefundSumlistTable(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 退款汇总导出
	 * <p>描述:</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日11:42:31
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/refundSumExcel.json")
	public ModelAndView refundSumExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String start_date=pd.getString("DATESTART");
		String end_date=pd.getString("DATEEND");
		if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse(start_date+" 00:00:00");
			Date end = sdf.parse(end_date+" 23:59:59");
			pd.put("DATESTART", sdf.format(start));
			pd.put("DATEEND", sdf.format(end));
			
		}
		String[] headerNames = new String[]{"入学年份","班型","项目名称","人数","收费总金额","退费","退费比例 %"};
		String[] bodyNames = new String[]{"SCHOOLYEAR","CLASSTYPE","PAY_STYLE_NAME","STUCOUNT","SHISHOUMONEY","TUIFEIMONEY","TUIMONEYPERSENT"};
		List<PageData> list = reportService.getRefundSumlistTable(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> schoolYears = new ArrayList<>();
			  List<String> classTypes = new ArrayList<>();
			  
			  //学生总数
			  BigDecimal stucountTotals = new BigDecimal(0);
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //退费总额
			  BigDecimal tuifeimoneyTotals = new BigDecimal(0);
			  
			  //学生总数
			  BigDecimal stucountTotalsXiaoji = new BigDecimal(0);
			  //实收总额
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //退费总额
			  BigDecimal tuifeimoneyTotalsXiaoji = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  //是否包含该入学年份
				  boolean isContainSchoolYear = schoolYears.contains(data.getString("SCHOOLYEAR"));
				  //是否包含该班型
				  boolean isContainClassType = classTypes.contains(data.getString("CLASSTYPE"));
				  
				  //学生总数
				  BigDecimal stucountTotal = new BigDecimal(data.getString("STUCOUNT"));
				  //实收总额
				  BigDecimal shishoumoneyTotal = new BigDecimal(data.getString("SHISHOUMONEY"));
				  //退费总额
				  BigDecimal tuifeimoneyTotal = new BigDecimal(data.getString("TUIFEIMONEY"));
				  //退费百分比总数
				  
				  //总计 start======================================================
				  stucountTotals = stucountTotals.add(stucountTotal);
				  shishoumoneyTotals = shishoumoneyTotals.add(shishoumoneyTotal);
				  tuifeimoneyTotals = tuifeimoneyTotals.add(tuifeimoneyTotal);
					  
				  //总计 end =========================================================
				  
				  data.put("TUIMONEYPERSENT", df.format(data.get("TUIMONEYPERSENT")));
				  resultList.add(data);
				  count ++;
				  
				  //添加不重复部门
				  if(!isContainSchoolYear || !isContainClassType){
					  stucountTotalsXiaoji = new BigDecimal(0);
					  shishoumoneyTotalsXiaoji = new BigDecimal(0);
					  tuifeimoneyTotalsXiaoji = new BigDecimal(0);
				  }else if(!isContainSchoolYear && !isContainClassType){
					  classTypes.clear();
				  }
				  
				  stucountTotalsXiaoji = stucountTotalsXiaoji.add(stucountTotal);
				  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(shishoumoneyTotal);
				  tuifeimoneyTotalsXiaoji = tuifeimoneyTotalsXiaoji.add(tuifeimoneyTotal);
				  //小计
				  PageData pdTemp = new PageData();
				  pdTemp.put("SCHOOLYEAR", "");
				  pdTemp.put("CLASSTYPE", "小计");
				  pdTemp.put("PAY_STYLE_NAME", "");
				  pdTemp.put("STUCOUNT", stucountTotalsXiaoji.longValue());
				  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("TUIFEIMONEY", df.format(tuifeimoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("TUIMONEYPERSENT",shishoumoneyTotalsXiaoji.doubleValue() == 0 ?"100.00%": df.format(tuifeimoneyTotalsXiaoji.divide(shishoumoneyTotalsXiaoji, 10, RoundingMode.HALF_UP).multiply(new BigDecimal(100))));
				  resultList.add(pdTemp);
				  
				  count ++;
				  //去除重复的小计
				  if(isContainSchoolYear && isContainClassType){
					  resultList.remove(count - 3);
					  count --;
				  }
				  //添加不重复入学年份
				  if(!isContainSchoolYear){
					  schoolYears.add(data.getString("SCHOOLYEAR"));
				  }	 
				  //添加不重复班型
				  if(!isContainClassType){
					  classTypes.add(data.getString("CLASSTYPE"));
				  }
			  }
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("SCHOOLYEAR", "");
			  pdTemp.put("CLASSTYPE", "总计");
			  pdTemp.put("PAY_STYLE_NAME", "");
			  pdTemp.put("STUCOUNT", stucountTotals.longValue());
			  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("TUIFEIMONEY", df.format(tuifeimoneyTotals.doubleValue()));
			  pdTemp.put("TUIMONEYPERSENT", "");
			  resultList.add(pdTemp);
		  
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,resultList);
		dataMap.put("fileName", "退费汇总");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:调整预交费明细页面</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日 下午4:33:31
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toAdvancePay.php")
	public ModelAndView toAdvancePay() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//入学年份
		pd.put("PARENT_ID", "GRADE");
		List<PageData> schoolYearList = basicclassService.getFromSYS_DICT(pd);
		//班型
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> classTypeList = basicclassService.getFromSYS_DICT(pd);
		
		PageData amountMsg = reportService.getAdvancePaySum(pd);
		mv.addObject("amountMsg", amountMsg);
		mv.addObject("schoolYearList",schoolYearList);
		mv.addObject("classTypeList",classTypeList);
		mv.setViewName("system/report/advancePay");
		return mv;
	}
	/**
	 * 
	 * <p>描述:预交明细汇总信息</p>
	 * @author  wzz
	 * @date 2019年1月22日 上午11:07:51
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getAdvancePaySum.json")
	public ModelAndView getAdvancePaySum(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData amountMsg = reportService.getAdvancePaySum(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amountMsg", amountMsg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:预交费明细表数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日16:37:17
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getAdvancePayTable.json")
	public ModelAndView getAdvancePayTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		list = reportService.getAdvancePayTable(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:预交费明细导出</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日11:42:31
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/advancePayExcel.json")
	public ModelAndView advancePayExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String[] headerNames = new String[]{"身份证号","姓名","学号","文化课学校","性别","入学年份","班型","预交费年份","预交费班型","预交费金额","缴费时间"};
		String[] bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","XUEHAO","SCHOOLNAME","XINGBIE","SCHOOLYEAR","CLASSTYPE","ADVANCEPAYYEAR","ADVANCEPAYCLASS","ADVANCEPAYMONEY","ADVANCEPAYTIME"};
		List<PageData> list = reportService.getAdvancePayList(pd);
		
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,list);
		PageData amountMsg = reportService.getAdvancePaySum(pd);
		String foots = "";
		if(list != null && list.size() > 0){
			foots = "预交费总金额："+amountMsg.getString("TOTALJYMONEY");
		}else{
			foots = "没有相关数据";
		}
		dataMap.put("totalData", foots);
		dataMap.put("column", headerNames.length);
		dataMap.put("fileName", "预交费明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
}