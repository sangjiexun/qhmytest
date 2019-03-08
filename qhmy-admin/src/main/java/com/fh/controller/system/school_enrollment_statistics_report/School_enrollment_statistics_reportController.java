package com.fh.controller.system.school_enrollment_statistics_report;



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
import com.fh.service.system.school_enrollment_statistics_report.School_enrollment_statistics_reportMapper;
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
@RequestMapping(value="/school_enrollment_statistics_report")
public class School_enrollment_statistics_reportController extends BaseController {
	@Resource(name = "school_enrollment_statistics_reportService")
	private School_enrollment_statistics_reportMapper school_enrollment_statistics_reportService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	@Resource(name = "reportService")
	private ReportManager reportService;
	@Resource(name="payManageService")
	private PayManageManager  payManageService;
	
	
	/**
	 * 
	 * <p>描述:获取分页信息</p>
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
		list = school_enrollment_statistics_reportService.paySumDetailTablelistPage(page);
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
	 * @date 2017年11月6日 上午10:18:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toFeeSumlist.php")
	public ModelAndView toRefundSumlist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> ruxuenianfenList = school_enrollment_statistics_reportService.getRuxuenianfenList(pd);
		mv.addObject("ruxuenianfenList", ruxuenianfenList);
		
		List<PageData> banxingList = school_enrollment_statistics_reportService.getBanxingList(pd);
		mv.addObject("banxingList", banxingList);
		
		List<PageData> jiaofeileixingList = stuInfoService.gethzxx(pd);
		mv.addObject("jiaofeileixingList", jiaofeileixingList);
		PageData amountMsg = school_enrollment_statistics_reportService.getAmountMsgFeeDetial(pd);
		mv.addObject("amountMsg", amountMsg);
		mv.addObject("pd", pd);
		mv.setViewName("system/school_enrollment_statistics_report/toFeeSumlist");
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
		List<PageData> list = null;
		list = school_enrollment_statistics_reportService.getFeeSumlistTable(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pd", pd);
		map.put("result","success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出</p>
	 * @author Administrator 刘晓
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
		String[] headerNames = new String[]{"入学年份","班型","文化课学校","人数"};
		String[] bodyNames = new String[]{"RUXUENIANFEN","BANXING","SCHOOLNAME","STU_COUNT"};
		List<PageData> list = school_enrollment_statistics_reportService.getFeeSumlistTable(pd);
		
			  //总计
			  PageData pdTemp = new PageData();
			  pdTemp.put("RUXUENIANFEN", "合计");
			  pdTemp.put("BANXING", "");
				PageData amountMsg = school_enrollment_statistics_reportService.getAmountMsgFeeDetial(pd);
			  pdTemp.put("STU_COUNT", amountMsg.getString("TOTALSUM"));
			  list.add(pdTemp);
		  
	
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,list);
		dataMap.put("fileName", "学校招生统计");
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
		PageData amountMsg = school_enrollment_statistics_reportService.getAmountMsgFeeDetial(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amountMsg", amountMsg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}



	
	

	
	


	





	

}