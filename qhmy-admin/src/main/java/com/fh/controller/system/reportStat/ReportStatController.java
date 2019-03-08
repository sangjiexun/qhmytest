package com.fh.controller.system.reportStat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.service.system.report.ReportManager;
import com.fh.service.system.reportStat.ReportStatManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.DictZiDianSelect;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.ObjectExcelView3;
import com.fh.util.ObjectExcelView4;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.Tools;
import com.keman.zhgd.common.tree.TreeJson;
import com.keman.zhgd.common.tree.VO;

/**
 * 
 * <p>标题:ReportStatController</p>
 * <p>描述:报表统计总页面</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn 王宁
 * @date 2017年8月14日 上午11:34:59
 */
@Controller
@RequestMapping(value="/reportStat")
public class ReportStatController extends BaseController {
	@Resource(name = "reportStatService")
	private ReportStatManager reportStatService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	@Resource(name = "reportService")
	private ReportManager reportService;
	@Resource(name="payManageService")
	private PayManageManager  payManageService;
	
	
	/**
	 * 
	 * <p>描述:跳转到报表统计导航页面</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 上午11:37:06
	 * @return
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
	@RequestMapping(value="/paySum.php")
	public ModelAndView paySum() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        Date dateEnd = new Date();
		pd.put("DATESTART", sdf.format(date));
		pd.put("DATEEND", sdf.format(dateEnd));
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/paySum");
		return mv;
	}
	/**
	 * 
	 * <p>描述:获取缴费汇总页面数据</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午4:01:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("paySumTable.json")
	public ModelAndView paySumTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list = reportStatService.paySumTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:去缴费明细页面</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午9:55:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goDetail.php")
	public ModelAndView goDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        Date dateEnd = new Date();
		pd.put("DATESTART", sdf.format(date));
		pd.put("DATEEND", sdf.format(dateEnd));
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/paySumDetail");
		return mv;
	}
	/**
	 * 
	 * <p>描述:导出缴费明细列表</p>
	 * @author Administrator 王宁
	 * @date 2017年8月17日 下午5:30:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel.json")
	public ModelAndView daoChu(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"项目名称","姓名","院系","缴费金额","优惠方式","应收金额","缴费方式——缴费金额","缴费时间"};
		String[] bodyNames = new String[]{"ITEMNAME","XINGMING","DEPTNAME","COST","DISCOUNT","AMOUNTRECEIVABLE","MSG","XGSJ"};
		List<PageData> varOList = reportStatService.paySumDetailTablelist(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "缴费明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 
	 * <p>描述:去缴费明细页面</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午9:55:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goDetailJson.json")
	public ModelAndView goDetailJson() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        Date dateEnd = new Date();
		pd.put("DATESTART", sdf.format(date));
		pd.put("DATEEND", sdf.format(dateEnd));
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/paySumDetail");
		return mv;
	}
	/**
	 * 
	 * <p>描述:获取缴费汇总页面数据</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午4:01:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("goDetailTable.json")
	public ModelAndView goDetailTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list = reportStatService.paySumDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:缴费比例页面</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 上午11:40:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payScale.php")
	public ModelAndView payScale() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> listItem = reportStatService.getListItemBL(pd);//获取名单所有的缴费项目
		if(listItem.size()>0){
			mv.addObject("listItemTreeJsonData", listItem);
		}
		/*
		 * 获取院校下拉列表
		 */
		List<PageData> departmentList = stuInfoService.getZuzhis(pd);
		/*
		 * 获取年级数据
		 */
		List<PageData> grades = reportStatService.getGrades(pd);
		mv.addObject("departmentTreeJsonData", departmentList);
		mv.addObject("grades", grades);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/payScale");
		return mv;
	}
	/**
	 * <p>描述:获得费用统计信息</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 下午9:02:09
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getPayMsg.json")
	public ModelAndView getPayMsg(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String PARENT_ITEM_PKID = "";
		if(!"null".equals(pd.getString("PARENT_ITEM_PKID"))&&!"".equals(pd.getString("PARENT_ITEM_PKID"))){
			PARENT_ITEM_PKID = pd.getString("PARENT_ITEM_PKID").substring(0, pd.getString("PARENT_ITEM_PKID").length()-1);
		}
		PageData pd_msg = new PageData();
		List<PageData> msgList = new ArrayList<>();
		List<PageData> msgList1 = new ArrayList<>();
		List<PageData> msgList2 = new ArrayList<>();
		//统计欠费金额和实收金额
		msgList1 = reportStatService.getPd_msg(pd);
		//统计欠费人数和非欠费人数
		msgList2 = reportStatService.getStu_msg(pd);
		for(int i=0;i<msgList1.size();i++){
			pd_msg = new PageData();
			pd_msg.putAll(msgList1.get(i));
			pd_msg.putAll(msgList2.get(i));
			msgList.add(pd_msg);
		}
		//msgList = reportStatService.getPayMsg(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pd", pd);
		map.put("msgList", msgList);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:首页折线图项目缴费详情页数据</p>
	 * @author Administrator 康程亮
	 * @date 2017年9月2日 上午11:11:50
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("goPayDetailTable.json")
	public ModelAndView goPayDetailTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list = reportStatService.payDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:介绍人汇总页面</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 上午9:36:30
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jieshaorenSum.php")
	public ModelAndView jieshaorenSum() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		/*
		 * 获取院校下拉列表
		 */
		List<PageData> departmentList = reportStatService.getZuzhis(pd);
		mv.addObject("departmentTreeJsonData", departmentList);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/jieshaorenSum");
		return mv;
	}
	/**
	 * 
	 * <p>描述:介绍人汇总表格数据</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 上午9:36:50
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("jieshaorenSumTable.json")
	public ModelAndView jieshaorenSumTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list = reportStatService.jieshaorenSumTablelistPage(page);
		//求合计
		if(list != null && list.size() > 0){
			PageData totalPd = new PageData();
			BigDecimal totalSTUNUM = new BigDecimal(0);
			BigDecimal totalNONUM = new BigDecimal(0);
			BigDecimal totalSUMMONEY = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				totalSTUNUM = totalSTUNUM.add(new BigDecimal(list.get(i).getString("STUNUM")));
				totalNONUM = totalNONUM.add(new BigDecimal(list.get(i).getString("NONUM")));
				totalSUMMONEY = totalSUMMONEY.add(new BigDecimal(list.get(i).getString("SUMMONEY")));
			}
			totalPd.put("SHENFENZHENGHAO", "合计");
			totalPd.put("STUNUM", totalSTUNUM);
			totalPd.put("NONUM", totalNONUM);
			totalPd.put("SUMMONEY", totalSUMMONEY);
			totalPd.put("XINGMING", "");
			totalPd.put("NAME", "");
			totalPd.put("PICI_NAME", "");
			totalPd.put("opt", "");
			list.add(totalPd);
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
	 * <p>描述:介绍人明细页面</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 上午9:38:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jieshaorenDetail")
	public ModelAndView jieshaorenDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		/*
		 * 获取院校下拉列表
		 */
		List<PageData> departmentList = stuInfoService.getZuzhis(pd);
		Map<String, String> statusMap = new HashMap<>();
		// 获得缴费状态集合
		for (DictZiDianSelect.PayItemListStatusEnum status : DictZiDianSelect.PayItemListStatusEnum
				.values()) {
			statusMap.put(status.name(), status.getValue());
		}
		if("null".equals(pd.getString("DATESTART"))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(date);  
			calendar.add(Calendar.DAY_OF_MONTH, -1);  
			date = calendar.getTime(); //前一天的时间
			pd.put("DATESTART", sdf.format(date));
			pd.put("DATEEND", sdf.format(new Date()));//当前时间
		}
		mv.addObject("departmentTreeJsonData", departmentList);
		mv.addObject("statusMap", statusMap);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/jieshaorenDetail");
		return mv;
	}
	/**
	 * 
	 * <p>描述:介绍人明细表格数据</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 上午9:36:50
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("jieshaorenDetailTable.json")
	public ModelAndView jieshaorenDetailTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list = reportStatService.jieshaorenDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:介绍人汇总导出</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 下午3:10:24
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jieshaorenSumExcel.json")
	public ModelAndView jieshaorenSumExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"身份证号","介绍人","院校名称","批次","招生人数","欠费人数","实收总金额"};
		String[] bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","NAME","PICI_NAME","STUNUM","NONUM","SUMMONEY"};
		List<PageData> list = reportStatService.jieshaorenSumTablelist(pd);
		//求合计
		if(list != null && list.size() > 0){
			PageData totalPd = new PageData();
			BigDecimal totalSTUNUM = new BigDecimal(0);
			BigDecimal totalNONUM = new BigDecimal(0);
			BigDecimal totalSUMMONEY = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				totalSTUNUM = totalSTUNUM.add(new BigDecimal(list.get(i).getString("STUNUM")));
				totalNONUM = totalNONUM.add(new BigDecimal(list.get(i).getString("NONUM")));
				totalSUMMONEY = totalSUMMONEY.add(new BigDecimal(list.get(i).getString("SUMMONEY")));
			}
			totalPd.put("SHENFENZHENGHAO", "合计");
			totalPd.put("STUNUM", totalSTUNUM);
			totalPd.put("NONUM", totalNONUM);
			totalPd.put("SUMMONEY", totalSUMMONEY);
			totalPd.put("XINGMING", "");
			totalPd.put("NAME", "");
			totalPd.put("PICI_NAME", "");
			totalPd.put("opt", "");
			list.add(totalPd);
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,list);
		dataMap.put("fileName", "介绍人汇总");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 介绍人汇总打印
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月23日 下午2:06:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("jieshaorenSumPrint.json")
	public ModelAndView jieshaorenSumPrint()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMas(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
		List<PageData> list = reportStatService.jieshaorenSumTablelist(pd);
		//求合计
		if(list != null && list.size() > 0){
			PageData totalPd = new PageData();
			BigDecimal totalSTUNUM = new BigDecimal(0);
			BigDecimal totalNONUM = new BigDecimal(0);
			BigDecimal totalSUMMONEY = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				totalSTUNUM = totalSTUNUM.add(new BigDecimal(list.get(i).getString("STUNUM")));
				totalNONUM = totalNONUM.add(new BigDecimal(list.get(i).getString("NONUM")));
				totalSUMMONEY = totalSUMMONEY.add(new BigDecimal(list.get(i).getString("SUMMONEY")));
			}
			totalPd.put("SHENFENZHENGHAO", "合计");
			totalPd.put("STUNUM", totalSTUNUM);
			totalPd.put("NONUM", totalNONUM);
			totalPd.put("SUMMONEY", totalSUMMONEY);
			totalPd.put("XINGMING", "");
			totalPd.put("NAME", "");
			totalPd.put("PICI_NAME", "");
			totalPd.put("opt", "");
			list.add(totalPd);
		}
		map.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出介绍人明细</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 下午2:26:54
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/jieshaorenDetailExcel.json")
	public ModelAndView jieshaorenDetailExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"身份证号","姓名","院校专业","介绍人","应收总金额","实收总金额","报名时间"};
		String[] bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","NAME","JIESHAOREN_NAME","AMOUNTRECEIVABLE","AMOUNTCOLLECTION","CJSJ"};
		List<PageData> varOList = reportStatService.jieshaorenDetailTablelist(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "介绍人明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 上午10:18:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toFeeSumlist.php")
	public ModelAndView toRefundSumlist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		date = calendar.getTime(); //前一天的时间
		pd.put("DATESTARTS", sdf.format(date));
		pd.put("DATEENDS", sdf.format(new Date()));//当前时间
		/*List<PageData> listItem = reportStatService.getListItem(pd);//获取名单所有的缴费项目
		if(listItem.size()>0){
			mv.addObject("listItemTreeJsonData", listItem);
		}
		
		 * 获取院校下拉列表
		 
		List<PageData> departmentList = stuInfoService.getZuzhis(pd);
		for(PageData p:departmentList){
			System.out.println(p.getString(""));
		}
		mv.addObject("departmentTreeJsonData", departmentList);*/
		
		List<PageData> ruxuenianfenList = reportStatService.getRuxuenianfenList(pd);
		mv.addObject("ruxuenianfenList", ruxuenianfenList);
		
		List<PageData> banxingList = reportStatService.getBanxingList(pd);
		mv.addObject("banxingList", banxingList);
		
		List<PageData> jiaofeileixingList = reportStatService.getJiaofeileixingList(pd);
		mv.addObject("jiaofeileixingList", jiaofeileixingList);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/toFeeSumlist");
		return mv;
	}
	/**
	 * 
	 * <p>描述:收费汇总列表</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 上午10:35:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getFeeSumlistTable.json")
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
		
		if(pd.getString("DATESTART").equals("null")&&pd.getString("DATEEND").equals("null")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		if(pd.getString("DATESTART").equals("")&&pd.getString("DATEEND").equals("")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		
		
		List<PageData> list = null;
		list = reportStatService.getFeeSumlistTable(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出收费汇总</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午8:04:08
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/feeSumlistExcel.json")
	public ModelAndView feeSumlistExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
		if(pd.getString("DATESTART").equals("null")&&pd.getString("DATEEND").equals("null")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		if(pd.getString("DATESTART").equals("")&&pd.getString("DATEEND").equals("")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		if(Tools.notEmpty(pd.getString("DPKID"))){
			String [] array = pd.getString("DPKID").split(",");
			pd.put("DPKID", Arrays.asList(array));
		}
		
		String[] headerNames = new String[]{"入学年份","班型","缴费类型","人数","实收总金额","现金总金额","银行卡总金额","电汇总金额","支付宝总金额","微信总金额"};
		String[] bodyNames = new String[]{"RUXUENIANFEN","BANXING","JIAOFEILEIXING","STU_COUNT","SHISHOUMONEY","CASH_MONEY","CARD_MONEY","TT_MONEY",
				"ZFB_MONEY","WX_MONEY"};
		List<PageData> list = reportStatService.getFeeSumlistTable(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> departments = new ArrayList<>();
			  
			  //学生总数
			  BigDecimal stucountTotals = new BigDecimal(0);
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //现金总额
			  BigDecimal cashmoneyTotals = new BigDecimal(0);
			  //银行卡总额
			  BigDecimal cardmoneyTotals = new BigDecimal(0);
			  //电汇总额
			  BigDecimal ttmoneyTotals = new BigDecimal(0);
			  //微信总额
			  BigDecimal wxmoneyTotals = new BigDecimal(0);
			  //支付宝总额
			  BigDecimal zfbmoneyTotals = new BigDecimal(0);
			  
			//学生总数小计
			  BigDecimal stucountTotalsXiaoji = new BigDecimal(0);
			  //实收总额小计
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //现金总额小计
			  BigDecimal cashmoneyTotalXiaoji = new BigDecimal(0);
			  //银行卡总额小计
			  BigDecimal cardmoneyTotalXiaoji = new BigDecimal(0);
			  //电汇总额小计
			  BigDecimal ttmoneyTotalXiaoji = new BigDecimal(0);
			  //微信总额小计
			  BigDecimal wxmoneyTotalXiaoji = new BigDecimal(0);
			  //支付宝总额小计
			  BigDecimal zfbmoneyTotalXiaoji = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  //收费学生数量
				  String shoustudent = data.getString("STU_COUNT");
				  if("0".equals(shoustudent)){
					  continue;
				  }
				  if(Tools.notEmpty(shoustudent)){
					  //是否包含该部门
					  boolean isContainDepartment = departments.contains(data.getString("RUXUENIANFEN")+data.getString("BANXING"));
					  
					  if(Tools.isEmpty(data.getString("CASH_MONEY"))){
						  data.put("CASH_MONEY", "0.00");
					  }else if(data.getString("CASH_MONEY").startsWith(".")){
						  data.put("CASH_MONEY", "0"+data.getString("CASH_MONEY"));
					  }
					  if(Tools.isEmpty(data.getString("CARD_MONEY"))){
						  data.put("CARD_MONEY", "0.00");
					  }else if(data.getString("CARD_MONEY").startsWith(".")){
						  data.put("CARD_MONEY", "0"+data.getString("CARD_MONEY"));
					  }
					  if(Tools.isEmpty(data.getString("TT_MONEY"))){
						  data.put("TT_MONEY", "0.00");
					  }else if(data.getString("TT_MONEY").startsWith(".")){
						  data.put("TT_MONEY", "0"+data.getString("TT_MONEY"));
					  }
					  if(Tools.isEmpty(data.getString("WX_MONEY"))){
						  data.put("WX_MONEY", "0.00");
					  }else if(data.getString("WX_MONEY").startsWith(".")){
						  data.put("WX_MONEY", "0"+data.getString("WX_MONEY"));
					  }
					  if(Tools.isEmpty(data.getString("ZFB_MONEY"))){
						  data.put("ZFB_MONEY", "0.00");
					  }else if(data.getString("ZFB_MONEY").startsWith(".")){
						  data.put("ZFB_MONEY", "0"+data.getString("ZFB_MONEY"));
					  }
					  
					  //学生总数
					  BigDecimal stucountTotal = new BigDecimal(data.getString("STU_COUNT"));
					  //实收总额
					  BigDecimal shishoumoneyTotal = new BigDecimal(0/*data.getString("SHISHOUMONEY")*/);
					//现金总额
					  BigDecimal cashmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("CASH_MONEY")) ? data.getString("CASH_MONEY") : "0.00");
					  //银行卡总额
					  BigDecimal cardmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("CARD_MONEY")) ? data.getString("CARD_MONEY") : "0.00");
					  //电汇总额
					  BigDecimal ttmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("TT_MONEY")) ? data.getString("TT_MONEY") : "0.00");
					  //微信总额
					  BigDecimal wxmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("WX_MONEY")) ? data.getString("WX_MONEY") : "0.00");
					  //支付宝总额
					  BigDecimal zfbmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("ZFB_MONEY")) ? data.getString("ZFB_MONEY") : "0.00");
					  
					  shishoumoneyTotal = shishoumoneyTotal.add(cashmoneyTotal).add(cardmoneyTotal).add(ttmoneyTotal).add(wxmoneyTotal).add(zfbmoneyTotal);
					  data.put("SHISHOUMONEY", shishoumoneyTotal);
					  //总计 start=========================================================
					  stucountTotals = stucountTotals.add(stucountTotal);
					  shishoumoneyTotals = shishoumoneyTotals.add(shishoumoneyTotal);
					  cashmoneyTotals = cashmoneyTotals.add(cashmoneyTotal);
					  cardmoneyTotals = cardmoneyTotals.add(cardmoneyTotal);
					  ttmoneyTotals = ttmoneyTotals.add(ttmoneyTotal);
					  wxmoneyTotals = wxmoneyTotals.add(wxmoneyTotal);
					  zfbmoneyTotals = zfbmoneyTotals.add(zfbmoneyTotal);
					  //总计 end =========================================================
					  if(!isContainDepartment){
						  stucountTotalsXiaoji = new BigDecimal(0);
						  shishoumoneyTotalsXiaoji = new BigDecimal(0);
						  cashmoneyTotalXiaoji = new BigDecimal(0);
						  cardmoneyTotalXiaoji = new BigDecimal(0);
						  ttmoneyTotalXiaoji = new BigDecimal(0);
						  wxmoneyTotalXiaoji = new BigDecimal(0);
						  zfbmoneyTotalXiaoji = new BigDecimal(0);
					  }else{
						  data.put("RUXUENIANFEN", "");
					  }
					  resultList.add(data);
					  count ++;
					  /*if(isContainDepartment){
						  stucountTotal = new BigDecimal(list.get(i - 1).getString("STU_COUNT")).add(new BigDecimal(data.getString("STU_COUNT")));
						  shishoumoneyTotal = new BigDecimal(list.get(i - 1).getString("SHISHOUMONEY")).add(new BigDecimal(data.getString("SHISHOUMONEY")));
						  cashmoneyTotal = new BigDecimal(list.get(i - 1).getString("CASH_MONEY")).add(new BigDecimal(data.getString("CASH_MONEY")));
						  cardmoneyTotal = new BigDecimal(list.get(i - 1).getString("CARD_MONEY")).add(new BigDecimal(data.getString("CARD_MONEY")));
						  wxmoneyTotal = new BigDecimal(list.get(i - 1).getString("WX_MONEY")).add(new BigDecimal(data.getString("WX_MONEY")));
						  zfbmoneyTotal = new BigDecimal(list.get(i - 1).getString("ZFB_MONEY")).add(new BigDecimal(data.getString("ZFB_MONEY")));
					  }*/
					  stucountTotalsXiaoji = stucountTotalsXiaoji.add(stucountTotal);
					  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(shishoumoneyTotal);
					  cashmoneyTotalXiaoji = cashmoneyTotalXiaoji.add(cashmoneyTotal);
					  cardmoneyTotalXiaoji = cardmoneyTotalXiaoji.add(cardmoneyTotal);
					  ttmoneyTotalXiaoji = ttmoneyTotalXiaoji.add(ttmoneyTotal);
					  wxmoneyTotalXiaoji = wxmoneyTotalXiaoji.add(wxmoneyTotal);
					  zfbmoneyTotalXiaoji = zfbmoneyTotalXiaoji.add(zfbmoneyTotal); 
					  //小计
					  PageData pdTemp = new PageData();
					  pdTemp.put("RUXUENIANFEN", "");
					  pdTemp.put("BANXING", "小计");
					  pdTemp.put("STU_COUNT", stucountTotalsXiaoji.longValue());
					  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotalsXiaoji.doubleValue()));
					  pdTemp.put("CASH_MONEY", df.format(cashmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("CARD_MONEY", df.format(cardmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("TT_MONEY", df.format(ttmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("WX_MONEY", df.format(wxmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("ZFB_MONEY", df.format(zfbmoneyTotalXiaoji.doubleValue()));
					  resultList.add(pdTemp);
					  
					  count ++;
					  //去除重复的小计
					  if(isContainDepartment){
						  resultList.remove(count - 3);
						  count --;
					  }
					  //添加不重复部门
					  if(!isContainDepartment){
						  departments.add(data.getString("RUXUENIANFEN")+data.getString("BANXING"));
					  }
				  }
				  
				  
				  
			  }
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("RUXUENIANFEN", "");
			  pdTemp.put("BANXING", "总计");
			  pdTemp.put("STU_COUNT", stucountTotals.longValue());
			  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("CASH_MONEY", df.format(cashmoneyTotals.doubleValue()));
			  pdTemp.put("CARD_MONEY", df.format(cardmoneyTotals.doubleValue()));
			  pdTemp.put("TT_MONEY", df.format(ttmoneyTotals.doubleValue()));
			  pdTemp.put("WX_MONEY", df.format(wxmoneyTotals.doubleValue()));
			  pdTemp.put("ZFB_MONEY", df.format(zfbmoneyTotals.doubleValue()));
			  resultList.add(pdTemp);
		  
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,resultList);
		dataMap.put("fileName", "收费汇总");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 学院收费汇总打印
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月23日 下午2:55:39
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/feeSumlistPrint.json")
	public ModelAndView feeSumlistPrint()throws Exception{
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
		
		if(pd.getString("DATESTART").equals("null")&&pd.getString("DATEEND").equals("null")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		if(pd.getString("DATESTART").equals("")&&pd.getString("DATEEND").equals("")) {
			pd.put("DATESTART", "1900-01-01");
			pd.put("DATEEND", "2999-01-01");//当前时间
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
		if(Tools.notEmpty(pd.getString("DPKID"))){
			String [] array = pd.getString("DPKID").split(",");
			pd.put("DPKID", Arrays.asList(array));
		}
		
		
		List<PageData> list = reportStatService.getFeeSumlistTable(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> departments = new ArrayList<>();
			  
			  //学生总数
			  BigDecimal stucountTotals = new BigDecimal(0);
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //现金总额
			  BigDecimal cashmoneyTotals = new BigDecimal(0);
			  //银行卡总额
			  BigDecimal cardmoneyTotals = new BigDecimal(0);
			  //电汇总额
			  BigDecimal ttmoneyTotals = new BigDecimal(0);
			  //微信总额
			  BigDecimal wxmoneyTotals = new BigDecimal(0);
			  //支付宝总额
			  BigDecimal zfbmoneyTotals = new BigDecimal(0);
			  
			  //学生总数小计
			  BigDecimal stucountTotalsXiaoji = new BigDecimal(0);
			  //实收总额小计
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //现金总额小计
			  BigDecimal cashmoneyTotalXiaoji = new BigDecimal(0);
			  //银行卡总额小计
			  BigDecimal cardmoneyTotalXiaoji = new BigDecimal(0);
			  //电汇总额小计
			  BigDecimal ttmoneyTotalXiaoji = new BigDecimal(0);
			  //微信总额小计
			  BigDecimal wxmoneyTotalXiaoji = new BigDecimal(0);
			  //支付宝总额小计
			  BigDecimal zfbmoneyTotalXiaoji = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  //收费学生数量
				  String shoustudent = data.getString("STU_COUNT");
				  if("0".equals(shoustudent)){
					  continue;
				  }
				  if(Tools.notEmpty(shoustudent)){
					  //是否包含该部门
					  boolean isContainDepartment = departments.contains(data.getString("RUXUENIANFEN")+data.getString("BANXING"));
					  //学生总数
					  BigDecimal stucountTotal = new BigDecimal(data.getString("STU_COUNT"));
					  //实收总额
					  BigDecimal shishoumoneyTotal = new BigDecimal(0/*data.getString("SHISHOUMONEY")*/);
					  //现金总额
					  BigDecimal cashmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("CASH_MONEY")) ? data.getString("CASH_MONEY") : "0");
					  //银行卡总额
					  BigDecimal cardmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("CARD_MONEY")) ? data.getString("CARD_MONEY") : "0");
					  //电汇总额
					  BigDecimal ttmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("TT_MONEY")) ? data.getString("TT_MONEY") : "0");
					  //微信总额
					  BigDecimal wxmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("WX_MONEY")) ? data.getString("WX_MONEY") : "0");
					  //支付宝总额
					  BigDecimal zfbmoneyTotal = new BigDecimal(Tools.notEmpty(data.getString("ZFB_MONEY")) ? data.getString("ZFB_MONEY") : "0");
					  
					  shishoumoneyTotal = shishoumoneyTotal.add(cashmoneyTotal).add(cardmoneyTotal).add(ttmoneyTotal).add(wxmoneyTotal).add(zfbmoneyTotal);
					  
					  if(Tools.isEmpty(data.getString("CASH_MONEY"))){
						  data.put("CASH_MONEY", "0");
					  }
					  if(Tools.isEmpty(data.getString("CARD_MONEY"))){
						  data.put("CARD_MONEY", "0");
					  }
					  if(Tools.isEmpty(data.getString("TT_MONEY"))){
						  data.put("TT_MONEY", "0");
					  }
					  if(Tools.isEmpty(data.getString("WX_MONEY"))){
						  data.put("WX_MONEY", "0");
					  }
					  if(Tools.isEmpty(data.getString("ZFB_MONEY"))){
						  data.put("ZFB_MONEY", "0");
					  }
						  
					  //总计 start=========================================================
					  stucountTotals = stucountTotals.add(stucountTotal);
					  shishoumoneyTotals = shishoumoneyTotals.add(shishoumoneyTotal);
					  cashmoneyTotals = cashmoneyTotals.add(cashmoneyTotal);
					  cardmoneyTotals = cardmoneyTotals.add(cardmoneyTotal);
					  ttmoneyTotals = ttmoneyTotals.add(ttmoneyTotal);
					  wxmoneyTotals = wxmoneyTotals.add(wxmoneyTotal);
					  zfbmoneyTotals = zfbmoneyTotals.add(zfbmoneyTotal);
					  //总计 end =========================================================
					  if(!isContainDepartment){
						  stucountTotalsXiaoji = new BigDecimal(0);
						  shishoumoneyTotalsXiaoji = new BigDecimal(0);
						  cashmoneyTotalXiaoji = new BigDecimal(0);
						  cardmoneyTotalXiaoji = new BigDecimal(0);
						  ttmoneyTotalXiaoji = new BigDecimal(0);
						  wxmoneyTotalXiaoji = new BigDecimal(0);
						  zfbmoneyTotalXiaoji = new BigDecimal(0);
					  }else{
						  data.put("RUXUENIANFEN", "");
					  }
					  resultList.add(data);
					  count ++;
					  stucountTotalsXiaoji = stucountTotalsXiaoji.add(stucountTotal);
					  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(shishoumoneyTotal);
					  cashmoneyTotalXiaoji = cashmoneyTotalXiaoji.add(cashmoneyTotal);
					  cardmoneyTotalXiaoji = cardmoneyTotalXiaoji.add(cardmoneyTotal);
					  ttmoneyTotalXiaoji = ttmoneyTotalXiaoji.add(ttmoneyTotal);
					  wxmoneyTotalXiaoji = wxmoneyTotalXiaoji.add(wxmoneyTotal);
					  zfbmoneyTotalXiaoji = zfbmoneyTotalXiaoji.add(zfbmoneyTotal); 
					  //小计
					  PageData pdTemp = new PageData();
//					  {"DNAMES","PAYITEM","STUCOUNT","SHISHOUMONEY","TUIFEIMONEY","TUIMONEYPERSENT"};
					  pdTemp.put("RUXUENIANFEN", "");
					  pdTemp.put("BANXING", "小计");
					  pdTemp.put("JIAOFEILEIXING", "");
					  pdTemp.put("STU_COUNT", stucountTotalsXiaoji.longValue());
					  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotalsXiaoji.doubleValue()));
					  pdTemp.put("CASH_MONEY", df.format(cashmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("CARD_MONEY", df.format(cardmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("TT_MONEY", df.format(ttmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("WX_MONEY", df.format(wxmoneyTotalXiaoji.doubleValue()));
					  pdTemp.put("ZFB_MONEY", df.format(zfbmoneyTotalXiaoji.doubleValue()));
					  resultList.add(pdTemp);
					  
					  count ++;
					  //去除重复的小计
					  if(isContainDepartment){
						  resultList.remove(count - 3);
						  count --;
					  }
					  //添加不重复部门
					  if(!isContainDepartment){
						  departments.add(data.getString("RUXUENIANFEN")+data.getString("BANXING"));
					  }
				  }
				  
				  
				  
			  }
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("RUXUENIANFEN", "");
			  pdTemp.put("BANXING", "总计");
			  pdTemp.put("JIAOFEILEIXING", "");
			  pdTemp.put("STU_COUNT", stucountTotals.longValue());
			  pdTemp.put("SHISHOUMONEY", df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("CASH_MONEY", df.format(cashmoneyTotals.doubleValue()));
			  pdTemp.put("CARD_MONEY", df.format(cardmoneyTotals.doubleValue()));
			  pdTemp.put("TT_MONEY", df.format(ttmoneyTotals.doubleValue()));
			  pdTemp.put("WX_MONEY", df.format(wxmoneyTotals.doubleValue()));
			  pdTemp.put("ZFB_MONEY", df.format(zfbmoneyTotals.doubleValue()));
			  resultList.add(pdTemp);
		  
		}
		map.put("list", resultList);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:去实收明细页面</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午8:02:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toFeeDetail.php")
	public ModelAndView toFeeDetail() throws Exception{/*
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		 * 缴费项目下拉列表
		 
		List<PageData> listItem = reportStatService.getListItem(pd);//获取名单所有的缴费项目
		if(listItem.size()>0){
			mv.addObject("listItemTreeJsonData", listItem);
		}
		
		 * 获取院校下拉列表
		 
		List<PageData> departmentList = stuInfoService.getZuzhis(pd);
		
		 * 获取年级数据
		 
		List<PageData> grades = reportStatService.getGrades(pd);
		//层次
		List<PageData> cengci_list = null;
		//批次
		List<PageData> pici_list = null;
		//操作人
		List<PageData> user_list = null;
		user_list = stuInfoService.getUserList(pd);
		PageData pd_user = new PageData();
		pd_user.put("USER_ID", "院方");
		pd_user.put("NAME", "院方");
		user_list.add(pd_user);
		cengci_list=stuInfoService.getCengCi(pd);
		pici_list=stuInfoService.getPiCi(pd);
		if("null".equals(pd.getString("DATESTART"))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(date);  
			calendar.add(Calendar.DAY_OF_MONTH, -1);  
			date = calendar.getTime(); //前一天的时间
			pd.put("DATESTART", sdf.format(date));
			pd.put("DATEEND", sdf.format(new Date()));//当前时间
		}
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		mv.addObject("collegeNameEn",collegeNameEn);
		mv.addObject("amountMsg", amountMsg);
		mv.addObject("departmentTreeJsonData", departmentList);
		mv.addObject("pd", pd);
		mv.addObject("user_list", user_list);
		mv.addObject("cengci_list", cengci_list);
		mv.addObject("pici_list", pici_list);
		mv.addObject("grades", grades);
		mv.setViewName("system/reportStat/toFeeDetail");
		return mv;
	*/
		return null;
	}
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午6:28:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("toFeeDetailTable.json")
	public ModelAndView toFeeDetailTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeName="";
		String collegeNameEn="";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeName=prop.getProperty("collegeName");
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		pd.put("collegeNameEn",collegeNameEn);
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		
		if(Tools.notEmpty(pd.getString("DPKID"))){
			String [] array = pd.getString("DPKID").split(",");
			pd.put("DPKID", Arrays.asList(array));
		}
		
		page.setPd(pd);
		list = reportStatService.getFeeDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		
		
		
		
		
		
		map.put("collegeNameEn",collegeNameEn);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	@ResponseBody
	@RequestMapping("toFcollegeNameEn.json")
	public ModelAndView tocollegeNameEn(Page page)throws Exception{
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collegeNameEn",collegeNameEn);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:实收明细导出</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午7:49:24
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/feeDetailExcel.json")
	public ModelAndView feeDetailExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		
		
		String[] headerNames=null;
		String[] bodyNames=null;
		if("GUANGDIAN".equals(collegeNameEn)){
			headerNames = new String[]{"身份证号","学号","姓名","入学年份","院校专业","学生类型","批次",/*"进校年份",*/
					 "收费项目","子项目","实收金额","操作人","票据号","票据类型","缴费时间","打印次数","现金","银行卡","电汇","微信","支付宝"};
		    bodyNames = new String[]{"SHENFENZHENGHAO","XUEHAO","XINGMING","NIANJI","DEP_NAME","CENGCI_NAME","PICI_NAME"/*,"JINXIAONIANFEN"*/
							,"ITEM_NAME","ITEM_NAME_CHILD","MONEY","USER_SF","RECEIPTNO","PJTYPE","CJSJ","PRINTCOUNT","CASH","CARD","TT","WX","ZFB"};
		}else{
			headerNames = new String[]{"身份证号","学号","姓名","入学年份","院校专业","学生类型","批次",/*"进校年份",*/
					 "收费项目","子项目","实收金额","操作人","收据号","缴费时间","打印次数","现金","银行卡","电汇","微信","支付宝"};
		    bodyNames = new String[]{"SHENFENZHENGHAO","XUEHAO","XINGMING","NIANJI","DEP_NAME","CENGCI_NAME","PICI_NAME"/*,"JINXIAONIANFEN"*/
							,"ITEM_NAME","ITEM_NAME_CHILD","MONEY","USER_SF","RECEIPTNO","CJSJ","PRINTCOUNT","CASH","CARD","TT","WX","ZFB"};
		}
		

		/*List<PageData> varOList = reportStatService.getFeeDetailTablelist(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "实收明细");
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;*/
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<PageData>	varOList = reportStatService.getFeeDetailTablelist(pd);
		dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		List<Short> cellStyle = new ArrayList<>();
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		String foots = "";
		if(varOList != null && varOList.size() > 0){
			foots = "收费人数："+amountMsg.getString("TOTALPEOPLE")+"，实收总金额："+amountMsg.getString("TOTALMONEY")+
					"，现金："+amountMsg.getString("CASHTOTALMONEY")+"，银行卡："+amountMsg.getString("CARDTOTALMONEY")+
					"，电汇："+amountMsg.getString("TTTOTALMONEY")+
					"，支付宝："+amountMsg.getString("ZFBTOTALMONEY")+"，微信："+amountMsg.getString("WXTOTALMONEY");
		}else{
			foots = "没有相关数据";
		}
		dataMap.put("totalData", foots);
		dataMap.put("column", headerNames.length);
		dataMap.put("fileName", "实收明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 
	 * <p>描述:获取实收汇总信息</p>
	 * @author ning 王宁
	 * @date 2018年6月29日 上午11:07:51
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getAmountMsgFeeDetial.json")
	public ModelAndView getAmountMsgFeeDetial(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Tools.notEmpty(pd.getString("DPKID"))){
			String [] array = pd.getString("DPKID").split(",");
			pd.put("DPKID", Arrays.asList(array));
		}
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amountMsg", amountMsg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:去学院汇总统计页面</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月4日 上午11:50:48
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toCollegeSummary.php")
	public ModelAndView toCollegeSummary() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		/*
		 * 获取院校下拉列表
		 */
		List<PageData> departmentList = reportStatService.getYuanXiao(pd);
		/*
		 * 获取年级下拉列表
		 */
		List<PageData> grades = reportStatService.getGrades(pd);
		/*
		 * 缴费类型下拉列表
		 */
		List<PageData> pay_style_list = reportService.getPayStyleListAll(pd);
		/*
		 * 获取学年下拉列表
		 */
		List<PageData> xuenianList = reportService.getSchoolYeaListAll(pd);
		mv.addObject("pay_style_list", pay_style_list);
		mv.addObject("nianjiList", grades);
		mv.addObject("xuenianList", xuenianList);
		mv.addObject("departmentList", departmentList);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/collegeSummary");
		return mv;
	}
	/**
	 * 
	 * <p>描述:学院汇总统计列表信息</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月4日 上午11:37:33
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/collegeSummary_list.json")
	public ModelAndView stuArrearageDeatilTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMas(pd);
		List<PageData> list = null;
		list = reportStatService.getCollegeSummaryTableListPage(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:学院汇总统计导出Excel</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月5日 上午10:22:04
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/collegeSummaryExcel.json")
	public ModelAndView collegeSummaryExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		if(Tools.notEmpty(pd.getString("DPKID"))){
			String [] array = pd.getString("DPKID").split(",");
			pd.put("DPKID", Arrays.asList(array));
		}

		String[] headerNames = new String[]{"入学年份","院校专业","学年","缴费类型","应收总金额","实收总金额","欠费总金额"};
		String[] bodyNames = new String[]{"NIANJI","NAME","SCHOOL_YEAR_NAME","PTP","TOTALAMOUNTRECEIVABLE","TOTALAMOUNTCOLLECTION","QIANFEIHEJI"};
		List<PageData> list = reportStatService.getCollegeSummaryTableListPage(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> years = new ArrayList<>();
			  List<String> orgtrees = new ArrayList<>();
			  
			  //应收总额
			  BigDecimal yingshoumoneyTotals = new BigDecimal(0);
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //欠费总额
			  BigDecimal qianfeimoneyTotals = new BigDecimal(0);
			  //应收总额小计
			  BigDecimal yingshoumoneyTotalsXiaoji = new BigDecimal(0);
			  //实收总额小计
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //欠费总额小计
			  BigDecimal qianfeimoneyTotalsXiaoji = new BigDecimal(0);			  
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  
				  //是否包含该年份
				  boolean isContainYear = years.contains(data.getString("NIANJI"));
				  //是否包含该院校专业
				  boolean isContainProfessional = orgtrees.contains(data.getString("NAME"));
				  //应收总数
				  BigDecimal TOTALAMOUNTRECEIVABLE = new BigDecimal(data.getString("TOTALAMOUNTRECEIVABLE"));
				  //实收总额
				  BigDecimal TOTALAMOUNTCOLLECTION = new BigDecimal(data.getString("TOTALAMOUNTCOLLECTION"));
				  //欠费总额
				  BigDecimal QIANFEIHEJI = new BigDecimal(data.getString("QIANFEIHEJI"));	
				  
				  //总计 start=========================================================
				  yingshoumoneyTotals = yingshoumoneyTotals.add(TOTALAMOUNTRECEIVABLE);
				  shishoumoneyTotals = shishoumoneyTotals.add(TOTALAMOUNTCOLLECTION);
				  qianfeimoneyTotals = qianfeimoneyTotals.add(QIANFEIHEJI);
				  //总计 end =========================================================
				  
				  //换年份且换院校专业时重置小计，否则去重入学年份和院校专业
				  if(!isContainYear || !isContainProfessional){
					  yingshoumoneyTotalsXiaoji = new BigDecimal(0);
					  shishoumoneyTotalsXiaoji = new BigDecimal(0);
					  qianfeimoneyTotalsXiaoji = new BigDecimal(0);
					  orgtrees.clear();
				  }else{
					  data.put("NIANJI", "");
					  data.put("NAME", "");
				  }
				  resultList.add(data);
				  count ++;

				  yingshoumoneyTotalsXiaoji = yingshoumoneyTotalsXiaoji.add(TOTALAMOUNTRECEIVABLE);
				  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(TOTALAMOUNTCOLLECTION);
				  qianfeimoneyTotalsXiaoji = qianfeimoneyTotalsXiaoji.add(QIANFEIHEJI);

				  //小计
				  PageData pdTemp = new PageData();
				  pdTemp.put("NIANJI", "");
				  pdTemp.put("NAME", "");
				  pdTemp.put("SCHOOL_YEAR_NAME", "小计");
				  pdTemp.put("PTP", "");
				  pdTemp.put("TOTALAMOUNTRECEIVABLE", df.format(yingshoumoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("TOTALAMOUNTCOLLECTION", df.format(shishoumoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("QIANFEIHEJI", df.format(qianfeimoneyTotalsXiaoji.doubleValue()));
				  resultList.add(pdTemp);
				  
				  count ++;
				  //去除重复的小计
				  if(isContainYear && isContainProfessional){
					  resultList.remove(count - 3);
					  count --;
				  }
				  //添加不重复入学年份
				  if(!isContainYear){
					  years.add(data.getString("NIANJI"));
				  }
				  //添加不重复院校专业
				  if(!isContainProfessional){
					  orgtrees.add(data.getString("NAME"));
				  }
				  if("0".equals(data.getString("TOTALAMOUNTRECEIVABLE"))){
					  data.put("TOTALAMOUNTRECEIVABLE", "");
				  }
				  if("0".equals(data.getString("TOTALAMOUNTCOLLECTION"))){
					  data.put("TOTALAMOUNTCOLLECTION", "");
				  }
				  if("0".equals(data.getString("QIANFEIHEJI"))){
					  data.put("QIANFEIHEJI", "");
				  }
			  }	  
			  
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("NIANJI", "");
			  pdTemp.put("NAME", "");
			  pdTemp.put("SCHOOL_YEAR_NAME", "总计");
			  pdTemp.put("PTP", "");
			  pdTemp.put("TOTALAMOUNTRECEIVABLE", df.format(yingshoumoneyTotals.doubleValue()));
			  pdTemp.put("TOTALAMOUNTCOLLECTION", df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("QIANFEIHEJI", df.format(qianfeimoneyTotals.doubleValue()));
			  resultList.add(pdTemp);
		}
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,resultList);
		dataMap.put("fileName", "学院汇总统计");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:去学院人数统计界面</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 上午8:17:47
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toPeopleSum.php")
	public ModelAndView toPeopleSum(HttpServletRequest request,HttpServletResponse response) throws Exception {/*
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		
		
		
	
		
		
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeName="";
		String collegeNameEn="CAIJING";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeName=prop.getProperty("collegeName");
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		
		
		if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
			pd.put("leibie", "2");
		}else{
			pd.put("leibie", "4");
		}
		//批次
		List<PageData> pici_list = stuInfoService.getPiCi(pd);
		//得到年级
		List<PageData> nianjiList = this.reportStatService.getGrades(pd);
		mv.addObject("nianjiList", nianjiList);
		//得到院校
		List<PageData> yuanxiaoList=this.reportStatService.getYuanXiaoLists(pd);
		mv.addObject("yuanxiaoList", yuanxiaoList);
		//得到宿舍
		List<PageData> susheList=this.reportStatService.getSuShersList(pd);
		mv.addObject("susheList", susheList);
		mv.addObject("collegeNameEn", collegeNameEn);
		mv.addObject("pici_list", pici_list);
		
		if("CAIJING".equals(collegeNameEn)){
			mv.setViewName("system/reportStat/toPeopleSum_Cj");
		}else{
			mv.setViewName("system/reportStat/toPeopleSum");
		}
		
		mv.setViewName("system/reportStat/toPeopleSum_Cj");
		

		return mv;
	*/
		return null;
	}
	
	
	/**
	 * 获得宿舍计划统计表格
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月14日 下午4:14:04
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getStudentDormPlanSumTable.json")
	public @ResponseBody ModelAndView getStudentDormPlanSumTable() throws Exception {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		//读取conf配置文件  判断是否是财经
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="CAIJING";
		if(prop!=null){
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		pd.put("collegeNameEn", collegeNameEn);
		//获得统计表格数据
		List<PageData> studentDormPlanSumTable = reportStatService.getStudentDormPlanSumTable(pd);
		map.put("pd", pd);
		map.put("studentDormPlanSumTable", studentDormPlanSumTable);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 导出宿舍计划汇总表格
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月15日 下午3:01:35
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportStudentDormPlanSumTable.json")
	public ModelAndView exportStudentDormPlanSumTable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		//读取conf配置文件  判断是否是财经
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="CAIJING";
		if(prop!=null){
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		pd.put("collegeNameEn", collegeNameEn);
		
		String[] headerNames = new String[]{"院校专业","性别","总人数",("CAIJING".equals(collegeNameEn)?"确认专业人数":"确认个人信息人数"),"宿舍类型","已缴费人数","床位总数","剩余床位数"};
		String[] bodyNames = new String[]{"DEPARTMENTNAME","SD_SEX_NAME","STUDENTCOUNT_TOTAL",("CAIJING".equals(collegeNameEn)?"STUDENTCOUNT_TOTAL_SUREDEP":"STUDENTCOUNT_TOTAL_SUREINF"),"DT_NAME","STUDENTCOUNT_YIJIAO","DORMCOUNT_TOTAL","DORMCOUNT_SHENGYU"};
		//获得统计表格数据
		List<PageData> studentDormPlanSumTable = reportStatService.getStudentDormPlanSumTable(pd);
		
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,studentDormPlanSumTable);
		dataMap.put("fileName", "宿舍计划统计");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 宿舍计划统计打印
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年11月2日 下午3:39:13
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/printStudentDormPlanSumTable.json")
	public ModelAndView printStudentDormPlanSumTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		page.setPd(pd);
		//读取conf配置文件  判断是否是财经
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeNameEn="CAIJING";
		if(prop!=null){
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		pd.put("collegeNameEn", collegeNameEn);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//获得统计表格数据
		List<PageData> studentDormPlanSumTable = reportStatService.getStudentDormPlanSumTable(pd);
		
		map.put("list", studentDormPlanSumTable);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:统计表写入</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 下午2:21:12
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/todepart.json")
	public@ResponseBody ModelAndView todepart(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		
		
        List<PageData> sushe=null;//包含男女两种情况
		PageData disp=null;
		
		
		
		//读取conf配置文件  判断是否是财经
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(new InputStreamReader(in, "utf-8"));
		String collegeName="";
		String collegeNameEn="CAIJING";
		if(prop!=null){   //需要修改的地方 学校名称。。。。
			collegeName=prop.getProperty("collegeName");
			collegeNameEn=prop.getProperty("collegeNameEn");
		}
		
		
	
		
		
		
		List<String> sexlist=null;
		sexlist=new ArrayList<String>();
		String sex=pd.getString("sex");
		System.out.println(sex);
		if("1".equals(sex)){  //页面判断性别男女
			System.out.println("1");
			sexlist.add("男");	
		}else if("2".equals(sex)){
			System.out.println("0");
			sexlist.add("女");
		}else{
			System.out.println("10");
			sexlist.add("男");	
			sexlist.add("女");
		}
		
		if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
			pd.put("leibie", "2");
		}else{
			pd.put("leibie", "4");
		}
		sushe=this.reportStatService.sushelist(pd);
		
		
		
		for(int i=0;i<sushe.size();i++){//循环宿舍\专业
			pd.put("bed", sushe.get(i).get("DT_NAME"));
			pd.put("zhuanye", sushe.get(i).get("YXNAME"));
			for(int j=0;j<sexlist.size();j++){//循环男女性别
				pd.put("xingbie", sexlist.get(j));
				if("男".equals(sexlist.get(j))){
					pd.put("enxingbie", "1");
				}else if("女".equals(sexlist.get(j))){
					pd.put("enxingbie", "0");
				}
				
                 PageData disps=null;
	        	
	    		if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
	    			disps=this.reportStatService.getdepartListCj(pd);
	    		}else{
	    			disps=this.reportStatService.getdepartList(pd);
	    		}
				map.put("isok"+i+"_"+j, disps);//传参 动态参数名 
			}
			
		}
		
		map.put("sexlist", sexlist);
		
		
		
		
		map.put("sushe", sushe);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	//toPeopleExcel.json
	@RequestMapping(value="/toPeopleExcel.json")
	public ModelAndView toPeopleExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		//读取conf配置文件  判断是否是财经
				Properties prop = new Properties();
				InputStream in = null;
				in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
				prop.load(new InputStreamReader(in, "utf-8"));
				String collegeName="";
				String collegeNameEn="CAIJING";
				if(prop!=null){   //需要修改的地方 学校名称。。。。
					collegeName=prop.getProperty("collegeName");
					collegeNameEn=prop.getProperty("collegeNameEn");
				}
		
				String[] headerNames=null;
				String[] bodyNames =null;
				String filename=null;
				if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
					headerNames = new String[]{"宿舍类型","院校专业","性别","总人数","确认专业信息","已缴费人数","床位总数","剩余床位数"};
					bodyNames= new String[]{"DT_NAME","YXNAME","SEX","MAXPEOPLE","INFO","ISPAY","MAXBED","LASTBED"};
					filename="财经学院宿舍计划统计";
	    		}else{
	    			headerNames = new String[]{"宿舍类型","院校专业","性别","总人数","确认个人信息","已缴费人数","床位总数","剩余床位数"};
	    			bodyNames= new String[]{"DT_NAME","YXNAME","SEX","MAXPEOPLE","INFO","ISPAY","MAXBED","LASTBED"};
	    			filename="宿舍计划统计";
	    		}			
				
				
		
		
		
		List<String> sexlist=null;
		sexlist=new ArrayList<String>();
		String sex=pd.getString("sex");
		System.out.println(sex);
		if("1".equals(sex)){  //页面判断性别男女
			System.out.println("1");
			sexlist.add("男");	
		}else if("2".equals(sex)){
			System.out.println("0");
			sexlist.add("女");
		}else{
			System.out.println("10");
			sexlist.add("男");	
			sexlist.add("女");
		}
		
		

		List<PageData> resultList = new ArrayList<>();
		
		if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
			pd.put("leibie", "2");
		}else{
			pd.put("leibie", "4");
		}
		
		
		
		
		List<PageData> suSheList=this.reportStatService.sushelist(pd);
		
		
		
		
		
		for(int i=0;i<suSheList.size();i++){
			PageData suShe=suSheList.get(i);
			pd.put("bed", suShe.get("DT_NAME"));
			pd.put("zhuanye", suShe.get("YXNAME"));
			pd.put("xingbie", sexlist.get(0));
			if("男".equals(sexlist.get(0))){
				pd.put("enxingbie", "1");
			}else if("女".equals(sexlist.get(0))){
				pd.put("enxingbie", "0");
			}
			PageData disp=this.reportStatService.getdepartList(pd);
			suShe.put("SEX", sexlist.get(0));
			suShe.put("MAXPEOPLE", disp.get("MAXPEOPLE"));
			suShe.put("INFO", disp.get("INFO"));
			suShe.put("ISPAY", disp.get("ISPAY"));
			suShe.put("MAXBED", disp.get("MAXBED"));
			suShe.put("LASTBED", disp.get("LASTBED"));
			resultList.add(suShe);
		
	        if(sexlist.size()==2){
	        	
	        	pd.put("xingbie", sexlist.get(1));
	        	if("男".equals(sexlist.get(1))){
					pd.put("enxingbie", "1");
				}else if("女".equals(sexlist.get(1))){
					pd.put("enxingbie", "0");
				}
	        	
	        	
	        	PageData disps=null;
	        	
	        	
	    		if("CAIJING".equals(collegeNameEn)){//判断是否是财经 决定用那一套数据库脚本
	    			disps=this.reportStatService.getdepartListCj(pd);
	    		}else{
	    			disps=this.reportStatService.getdepartList(pd);
	    		}
	        	
	        	
	        	
	        	
	        	disps.put("SEX", sexlist.get(1));
	        	resultList.add(disps);
	        }
			
			
		}
		
		PageData heji=new PageData();
		heji.put("YXNAME", "合计");
		heji.put("MAXPEOPLE", pd.get("maxPeoSum"));
		heji.put("INFO", pd.get("infoSum"));
		heji.put("ISPAY", pd.get("paySum"));
		heji.put("MAXBED", pd.get("bedSum"));
		heji.put("LASTBED", pd.get("lastBedSum"));
		
		resultList.add(heji);
		
		
		
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,resultList);
		dataMap.put("fileName", filename);
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:去结账汇总统计页面</p>
	 * @author Administrator 柴飞
	 * @date 2017年11月6日 上午10:18:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/theInvoicing_list.php")
	public ModelAndView theInvoicing_list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//获取学年下拉列表
		List<PageData> xuenianList = reportService.getSchoolYeaListAll(pd);
		//获取收费员下拉列表
		List<PageData> operatorList = reportStatService.getOperatorList(pd);
		mv.addObject("xuenianList", xuenianList);
		mv.addObject("operatorList", operatorList);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/theInvoicing_list");
		return mv;
	}
	/**
	 * 
	 * <p>描述:结账汇总统计列表</p>
	 * @author Administrator 柴飞
	 * @date 2017年11月6日 上午10:35:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getTheInvoicingTable.json")
	public ModelAndView getTheInvoicingTable()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = null;
		list = reportStatService.getTheInvoicingList(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出结账汇总统计</p>
	 * @author Administrator 柴飞
	 * @date 2017年11月6日 下午8:04:08
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/theInvoicingExcel.json")
	public ModelAndView theInvoicingExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		String[] headerNames = new String[]{"收费员","缴费类型","实收总金额","现金总金额","银行卡总金额","电汇总金额","支付宝总金额","微信总金额"};
		String[] bodyNames = new String[]{"NAME","PAY_STYLE_NAME","SHISHOUMONEY","CASH_MONEY","CARD_MONEY","TT_MONEY",
				"ZFB_MONEY","WX_MONEY"};
		List<PageData> list = reportStatService.getTheInvoicingList(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> cjrs = new ArrayList<>();
			  
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //现金总额
			  BigDecimal cashmoneyTotals = new BigDecimal(0);
			  //银行卡总额
			  BigDecimal cardmoneyTotals = new BigDecimal(0);
			  //电汇总额
			  BigDecimal ttmoneyTotals = new BigDecimal(0);
			  //微信总额
			  BigDecimal wxmoneyTotals = new BigDecimal(0);
			  //支付宝总额
			  BigDecimal zfbmoneyTotals = new BigDecimal(0);
			  
			  //实收总额小计
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //现金总额小计
			  BigDecimal cashmoneyTotalXiaoji = new BigDecimal(0);
			  //银行卡总额小计
			  BigDecimal cardmoneyTotalXiaoji = new BigDecimal(0);
			  //电汇总额小计
			  BigDecimal ttmoneyTotalXiaoji = new BigDecimal(0);
			  //微信总额小计
			  BigDecimal wxmoneyTotalXiaoji = new BigDecimal(0);
			  //支付宝总额小计
			  BigDecimal zfbmoneyTotalXiaoji = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  
				  //是否包含该操作员
				  boolean isContainCJR = cjrs.contains(data.getString("USER_ID"));

				  //实收总额
				  BigDecimal shishoumoneyTotal = new BigDecimal(data.getString("SHISHOUMONEY"));
				  //现金总额
				  BigDecimal cashmoneyTotal = new BigDecimal(data.getString("CASH_MONEY"));
				  //银行卡总额
				  BigDecimal cardmoneyTotal = new BigDecimal(data.getString("CARD_MONEY"));
				  //电汇总额
				  BigDecimal ttmoneyTotal = new BigDecimal(data.getString("TT_MONEY"));
				  //微信总额
				  BigDecimal wxmoneyTotal = new BigDecimal(data.getString("WX_MONEY"));
				  //支付宝总额
				  BigDecimal zfbmoneyTotal = new BigDecimal(data.getString("ZFB_MONEY"));
				  
				  //总计 start=========================================================
				  shishoumoneyTotals = shishoumoneyTotals.add(shishoumoneyTotal);
				  cashmoneyTotals = cashmoneyTotals.add(cashmoneyTotal);
				  cardmoneyTotals = cardmoneyTotals.add(cardmoneyTotal);
				  ttmoneyTotals = ttmoneyTotals.add(ttmoneyTotal);
				  wxmoneyTotals = wxmoneyTotals.add(wxmoneyTotal);
				  zfbmoneyTotals = zfbmoneyTotals.add(zfbmoneyTotal);
				  //总计 end =========================================================
				  if(!isContainCJR){
					  shishoumoneyTotalsXiaoji = new BigDecimal(0);
					  cashmoneyTotalXiaoji = new BigDecimal(0);
					  cardmoneyTotalXiaoji = new BigDecimal(0);
					  ttmoneyTotalXiaoji = new BigDecimal(0);
					  wxmoneyTotalXiaoji = new BigDecimal(0);
					  zfbmoneyTotalXiaoji = new BigDecimal(0);
				  }else{
					  data.put("NAME", "");
				  }
				  //将0置为""
				  if("0".equals(data.getString("SHISHOUMONEY"))){
					  data.put("SHISHOUMONEY", "");
				  }
				  if("0".equals(data.getString("CASH_MONEY"))){
					  data.put("CASH_MONEY", "");
				  }
				  if("0".equals(data.getString("CARD_MONEY"))){
					  data.put("CARD_MONEY", "");
				  }
				  if("0".equals(data.getString("TT_MONEY"))){
					  data.put("TT_MONEY", "");
				  }
				  if("0".equals(data.getString("WX_MONEY"))){
					  data.put("WX_MONEY", "");
				  }
				  if("0".equals(data.getString("ZFB_MONEY"))){
					  data.put("ZFB_MONEY", "");
				  }
				  resultList.add(data);
				  count ++;
				  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(shishoumoneyTotal);
				  cashmoneyTotalXiaoji = cashmoneyTotalXiaoji.add(cashmoneyTotal);
				  cardmoneyTotalXiaoji = cardmoneyTotalXiaoji.add(cardmoneyTotal);
				  ttmoneyTotalXiaoji = ttmoneyTotalXiaoji.add(ttmoneyTotal);
				  wxmoneyTotalXiaoji = wxmoneyTotalXiaoji.add(wxmoneyTotal);
				  zfbmoneyTotalXiaoji = zfbmoneyTotalXiaoji.add(zfbmoneyTotal); 
				  //小计
				  PageData pdTemp = new PageData();
				  pdTemp.put("NAME", "");
				  /*pdTemp.put("SCHOOL_YEAR_NAME", "小计");*/
				  pdTemp.put("PAY_STYLE_NAME", "小计");
				  pdTemp.put("SHISHOUMONEY", shishoumoneyTotalsXiaoji.doubleValue() == 0 ? "": df.format(shishoumoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("CASH_MONEY", cashmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(cashmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("CARD_MONEY", cardmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(cardmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("TT_MONEY", ttmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(ttmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("WX_MONEY", wxmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(wxmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("ZFB_MONEY", zfbmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(zfbmoneyTotalXiaoji.doubleValue()));
				  resultList.add(pdTemp);
				  
				  count ++;
				  //去除重复的小计
				  if(isContainCJR){
					  resultList.remove(count - 3);
					  count --;
				  }
				  //添加不重复操作员
				  if(!isContainCJR){
					  cjrs.add(data.getString("USER_ID"));
				  }
				    
			  }
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("NAME", "");
			  /*pdTemp.put("SCHOOL_YEAR_NAME", "总计");*/
			  pdTemp.put("PAY_STYLE_NAME", "总计");
			  pdTemp.put("SHISHOUMONEY", shishoumoneyTotals.doubleValue() == 0 ? "": df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("CASH_MONEY", cashmoneyTotals.doubleValue() == 0 ? "": df.format(cashmoneyTotals.doubleValue()));
			  pdTemp.put("CARD_MONEY", cardmoneyTotals.doubleValue() == 0 ? "": df.format(cardmoneyTotals.doubleValue()));
			  pdTemp.put("TT_MONEY", ttmoneyTotals.doubleValue() == 0 ? "": df.format(ttmoneyTotals.doubleValue()));
			  pdTemp.put("WX_MONEY", wxmoneyTotals.doubleValue() == 0 ? "": df.format(wxmoneyTotals.doubleValue()));
			  pdTemp.put("ZFB_MONEY", zfbmoneyTotals.doubleValue() == 0 ? "": df.format(zfbmoneyTotals.doubleValue()));
			  resultList.add(pdTemp);
		  
		}
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("totalData", "缴费时间："+pd.getString("DATESTART")+" 至  "+pd.getString("DATEEND")+"  收费员："+pd.getString("shoufeiname"));
		dataMap.put("column", 8);
		dataMap.putAll(Utils.toExcel(headerNames,bodyNames,resultList));
		dataMap.put("fileName", "结账汇总统计");
		ObjectExcelView3 erv = new ObjectExcelView3();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**
	 * 
	 * <p>描述:结账汇总统计打印</p>
	 * @author Administrator 柴飞
	 * @date 2018年9月14日 上午10:03:04
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/theInvoicingPrint.json")
	public ModelAndView theInvoicingPrint()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		List<PageData> list = reportStatService.getTheInvoicingList(pd);
		
		//以下为增加小计和总计
		List<PageData> resultList = new ArrayList<>();
		if(list != null && list.size() > 0){

			  DecimalFormat df = new DecimalFormat("######0.00");
			  List<String> cjrs = new ArrayList<>();
			  
			  //实收总额
			  BigDecimal shishoumoneyTotals = new BigDecimal(0);
			  //现金总额
			  BigDecimal cashmoneyTotals = new BigDecimal(0);
			  //银行卡总额
			  BigDecimal cardmoneyTotals = new BigDecimal(0);
			  //电汇总额
			  BigDecimal ttmoneyTotals = new BigDecimal(0);
			  //微信总额
			  BigDecimal wxmoneyTotals = new BigDecimal(0);
			  //支付宝总额
			  BigDecimal zfbmoneyTotals = new BigDecimal(0);
			  
			  //实收总额小计
			  BigDecimal shishoumoneyTotalsXiaoji = new BigDecimal(0);
			  //现金总额小计
			  BigDecimal cashmoneyTotalXiaoji = new BigDecimal(0);
			  //银行卡总额小计
			  BigDecimal cardmoneyTotalXiaoji = new BigDecimal(0);
			  //电汇总额小计
			  BigDecimal ttmoneyTotalXiaoji = new BigDecimal(0);
			  //微信总额小计
			  BigDecimal wxmoneyTotalXiaoji = new BigDecimal(0);
			  //支付宝总额小计
			  BigDecimal zfbmoneyTotalXiaoji = new BigDecimal(0);
			  
			  //行数
			  int count = 0;
			  for (int i = 0; i < list.size(); i++) {
				  PageData data = list.get(i);
				  
				  //是否包含该操作员
				  boolean isContainCJR = cjrs.contains(data.getString("USER_ID"));

				  //实收总额
				  BigDecimal shishoumoneyTotal = new BigDecimal(data.getString("SHISHOUMONEY"));
				  //现金总额
				  BigDecimal cashmoneyTotal = new BigDecimal(data.getString("CASH_MONEY"));
				  //银行卡总额
				  BigDecimal cardmoneyTotal = new BigDecimal(data.getString("CARD_MONEY"));
				  //电汇总额
				  BigDecimal ttmoneyTotal = new BigDecimal(data.getString("TT_MONEY"));
				  //微信总额
				  BigDecimal wxmoneyTotal = new BigDecimal(data.getString("WX_MONEY"));
				  //支付宝总额
				  BigDecimal zfbmoneyTotal = new BigDecimal(data.getString("ZFB_MONEY"));
				  
				  //总计 start=========================================================
				  shishoumoneyTotals = shishoumoneyTotals.add(shishoumoneyTotal);
				  cashmoneyTotals = cashmoneyTotals.add(cashmoneyTotal);
				  cardmoneyTotals = cardmoneyTotals.add(cardmoneyTotal);
				  ttmoneyTotals = ttmoneyTotals.add(ttmoneyTotal);
				  wxmoneyTotals = wxmoneyTotals.add(wxmoneyTotal);
				  zfbmoneyTotals = zfbmoneyTotals.add(zfbmoneyTotal);
				  //总计 end =========================================================
				  if(!isContainCJR){
					  shishoumoneyTotalsXiaoji = new BigDecimal(0);
					  cashmoneyTotalXiaoji = new BigDecimal(0);
					  cardmoneyTotalXiaoji = new BigDecimal(0);
					  ttmoneyTotalXiaoji = new BigDecimal(0);
					  wxmoneyTotalXiaoji = new BigDecimal(0);
					  zfbmoneyTotalXiaoji = new BigDecimal(0);
				  }else{
					  data.put("NAME", "");
				  }
				  //将0置为""
				  if("0".equals(data.getString("SHISHOUMONEY"))){
					  data.put("SHISHOUMONEY", "");
				  }
				  if("0".equals(data.getString("CASH_MONEY"))){
					  data.put("CASH_MONEY", "");
				  }
				  if("0".equals(data.getString("CARD_MONEY"))){
					  data.put("CARD_MONEY", "");
				  }
				  if("0".equals(data.getString("TT_MONEY"))){
					  data.put("TT_MONEY", "");
				  }
				  if("0".equals(data.getString("WX_MONEY"))){
					  data.put("WX_MONEY", "");
				  }
				  if("0".equals(data.getString("ZFB_MONEY"))){
					  data.put("ZFB_MONEY", "");
				  }
				  resultList.add(data);
				  count ++;
				  shishoumoneyTotalsXiaoji = shishoumoneyTotalsXiaoji.add(shishoumoneyTotal);
				  cashmoneyTotalXiaoji = cashmoneyTotalXiaoji.add(cashmoneyTotal);
				  cardmoneyTotalXiaoji = cardmoneyTotalXiaoji.add(cardmoneyTotal);
				  ttmoneyTotalXiaoji = ttmoneyTotalXiaoji.add(ttmoneyTotal);
				  wxmoneyTotalXiaoji = wxmoneyTotalXiaoji.add(wxmoneyTotal);
				  zfbmoneyTotalXiaoji = zfbmoneyTotalXiaoji.add(zfbmoneyTotal); 
				  //小计
				  PageData pdTemp = new PageData();
				  pdTemp.put("NAME", "");
				  /*pdTemp.put("SCHOOL_YEAR_NAME", "小计");*/
				  pdTemp.put("PAY_STYLE_NAME", "小计");
				  pdTemp.put("SHISHOUMONEY", shishoumoneyTotalsXiaoji.doubleValue() == 0 ? "": df.format(shishoumoneyTotalsXiaoji.doubleValue()));
				  pdTemp.put("CASH_MONEY", cashmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(cashmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("CARD_MONEY", cardmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(cardmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("TT_MONEY", ttmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(ttmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("WX_MONEY", wxmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(wxmoneyTotalXiaoji.doubleValue()));
				  pdTemp.put("ZFB_MONEY", zfbmoneyTotalXiaoji.doubleValue() == 0 ? "": df.format(zfbmoneyTotalXiaoji.doubleValue()));
				  resultList.add(pdTemp);
				  
				  count ++;
				  //去除重复的小计
				  if(isContainCJR){
					  resultList.remove(count - 3);
					  count --;
				  }
				  //添加不重复操作员
				  if(!isContainCJR){
					  cjrs.add(data.getString("USER_ID"));
				  }
				    
			  }
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("NAME", "");
			  /*pdTemp.put("SCHOOL_YEAR_NAME", "总计");*/
			  pdTemp.put("PAY_STYLE_NAME", "总计");
			  pdTemp.put("SHISHOUMONEY", shishoumoneyTotals.doubleValue() == 0 ? "": df.format(shishoumoneyTotals.doubleValue()));
			  pdTemp.put("CASH_MONEY", cashmoneyTotals.doubleValue() == 0 ? "": df.format(cashmoneyTotals.doubleValue()));
			  pdTemp.put("CARD_MONEY", cardmoneyTotals.doubleValue() == 0 ? "": df.format(cardmoneyTotals.doubleValue()));
			  pdTemp.put("TT_MONEY", ttmoneyTotals.doubleValue() == 0 ? "": df.format(ttmoneyTotals.doubleValue()));
			  pdTemp.put("WX_MONEY", wxmoneyTotals.doubleValue() == 0 ? "": df.format(wxmoneyTotals.doubleValue()));
			  pdTemp.put("ZFB_MONEY", zfbmoneyTotals.doubleValue() == 0 ? "": df.format(zfbmoneyTotals.doubleValue()));
			  resultList.add(pdTemp);
		  
		}
		map.put("list", resultList);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:实收明细打印</p>
	 * @author Administrator 柴飞
	 * @date 2018-10-11 09:18:14
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/feeDetailPrint.json")
	public ModelAndView feeDetailPrint()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData>	list = reportStatService.getFeeDetailTablelist(pd);
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		
		map.put("list", list);
		map.put("amountMsg", amountMsg);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	public static void main(String[] args) {
		double money=1270;
		double isyue=money/12;
		System.out.println(isyue);
	}
}