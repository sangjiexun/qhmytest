package com.fh.controller.system.reconciliationsummary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.reconciliationsummary.ReconciliationSummaryManager;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;

/**
 * 
 * <p>标题:ReconciliationSummaryController</p>
 * <p>描述:对账汇总和明细查询</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn 王宁
 * @date 2018年12月17日 上午11:12:04
 */
@Controller
@RequestMapping(value="/reconciliation")
public class ReconciliationSummaryController extends BaseController{
	@Resource(name="reconciliationSummaryService")
	private ReconciliationSummaryManager reconciliationSummaryService;
	
	/**
	 * 
	 * <p>描述:去对账汇总页面</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:12:35
	 * @return
	 */
	@RequestMapping(value="/summary.php")
	public ModelAndView toSummary() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/reconciliation/summary");
		return mv;
	}
	/**
	 * 
	 * <p>描述:汇总表格</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:12:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/table.json")
	public ModelAndView Rctablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = reconciliationSummaryService.recSummarylistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:去汇总详情页面</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:12:56
	 * @return
	 */
	@RequestMapping(value="/detail.json")
	public ModelAndView toDetail() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd",pd);
		mv.setViewName("system/reconciliation/detail");
		return mv;
	}
	/**
	 * 
	 * <p>描述:去明细页面</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:13:04
	 * @return
	 */
	@RequestMapping(value="/detail.php")
	public ModelAndView detail() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd",pd);
		mv.setViewName("system/reconciliation/detail");
		return mv;
	}
	/**
	 * 
	 * <p>描述:对账汇总明细table</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:13:15
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/detailtable.json")
	public ModelAndView detailtablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = reconciliationSummaryService.detaillistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出对账汇总</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:13:27
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel.json")
	public ModelAndView exportSummary(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"对账日期","账单总数","账单总金额","成功账单总数","失败账单总数","成功账单金额"};
		String[] bodyNames = new String[]{"RQ","ZDZS","ZDZJE","SUCCCOUNTS","FAILDCOUNTS","SUCCMONEYCOUNTS"};
		List<PageData> varOList=reconciliationSummaryService.exportSummarylist(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "对账汇总");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:导出对账明细</p>
	 * @author wn 王宁
	 * @date 2018年12月17日 上午11:13:37
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportdetailExcel.json")
	public ModelAndView exportDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[]{"订单号","交易平台流水号","交易时间","支付方式","付款方姓名","身份证号","入学年份","班型","摘要","付款方金额","对账结果"};
		String[] bodyNames = new String[]{"PAY_ORDER_ORDERNO","TRANSACTION_HOST_SN","RIQI","TRANSACTION_PAY_PLATFORM","STUNAME","SHENFENZHENGHAO","GRADE_NAME","CLASSTYPE_NAME","PAYITEM","MONEY","TRANSACTION_STATUS"};
		List<PageData> varOList=reconciliationSummaryService.exportSummaryDetail(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "对账明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
}
