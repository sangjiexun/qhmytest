package com.fh.controller.system.reportStat;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import com.fh.service.system.reportStat.ReportStatManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
/**
 * 
 * <p>标题:ReportFeeDetailController</p>
 * <p>描述:收费明细</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn wzz
 * @date 2018年12月21日 上午11:34:59
 */
@Controller
@RequestMapping(value="/reportFeeDetail")
public class ReportFeeDetailController  extends BaseController {
	
	@Resource(name = "reportStatService")
	private ReportStatManager reportStatService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	
	/**
	 * 
	 * <p>描述:去收费明细页面</p>
	 * @author Administrator wzz
	 * @date 2017年11月6日 下午8:02:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toFeeDetail.php")
	public ModelAndView toFeeDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//入学年份呢
		List<PageData> ruxuenianfenList = reportStatService.getRuxuenianfenList(pd);		
		//班型
		List<PageData> banxingList = reportStatService.getBanxingList(pd);
		//缴费类型
		List<PageData> jiaofeileixingList = reportStatService.getJiaofeileixingList(pd);
		//层次
		List<PageData> cengci_list = stuInfoService.getCengCi(pd);//层次;
				
		if("null".equals(pd.getString("DATESTART"))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(date);  
			calendar.add(Calendar.DAY_OF_MONTH, -1);  
			date = calendar.getTime(); //前一天的时间
			pd.put("DATESTART", sdf.format(date));
			pd.put("DATEEND", sdf.format(new Date()));//当前时间
		}
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		mv.addObject("amountMsg", amountMsg);
		mv.addObject("ruxuenianfenList", ruxuenianfenList);
		mv.addObject("banxingList", banxingList);
		mv.addObject("jiaofeileixingList", jiaofeileixingList);
		mv.addObject("cengci_list", cengci_list);
		mv.addObject("pd", pd);
		mv.setViewName("system/reportStat/toFeeDetail");
		return mv;

	}
	/**
	 * 
	 * <p>描述:获取实收汇总信息</p>
	 * @author  wzz
	 * @date 2019年1月22日 上午11:07:51
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getAmountMsgFeeDetial.json")
	public ModelAndView getAmountMsgFeeDetial(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_date=pd.getString("DATESTART");
		String end_date=pd.getString("DATEEND");

		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amountMsg", amountMsg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年12月21日 下午6:28:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("toFeeDetailTable.json")
	public ModelAndView toFeeDetailTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_date=pd.getString("DATESTART");
		String end_date=pd.getString("DATEEND");
		if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(start_date);
			Date end = sdf.parse(end_date);
			pd.put("DATESTART", sdf.format(start));
			pd.put("DATEEND", sdf.format(end));
			
		}
		
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		
		
		
		list = reportStatService.getFeeDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:导出收费明细</p>
	 * @author Administrator wzz
	 * @date 2018年12月22日 下午7:49:24
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
		String start_date=pd.getString("DATESTART");
		String end_date=pd.getString("DATEEND");
		/*if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(start_date);
			Date end = sdf.parse(end_date);
			pd.put("DATESTART", sdf.format(start));
			pd.put("DATEEND", sdf.format(end));
			
		}
		*/
		
		String[] headerNames=null;
		String[] bodyNames=null;
		headerNames = new String[]{"身份证号","姓名","学号","文化课学校","性别","学生类型","缴费类型","入学年份","班型","实收金额",
				 "收据号","缴费时间","打印次数","现金","银行卡","支付宝","微信","电汇"};
	    bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","XUEHAO","WHKXUEXIAONAME","XINGBIE","CENGCI_NAME","PAY_STYLE_NAME","NIANJI","BANJITYPENAME",
	    		"MONEY","RECEIPTNO","CJSJ","PRINTCOUNT",
	    		"CASH","CARD","ZFB","WX","TT"};

		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<PageData>	varOList = reportStatService.getFeeDetailTablelist(pd);
		dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		PageData amountMsg = reportStatService.getAmountMsgFeeDetial(pd);
		String foots = "";
		if(varOList != null && varOList.size() > 0){
			foots = "实收总金额："+amountMsg.getString("TOTALMONEY")+
					"，现金："+amountMsg.getString("CASHTOTALMONEY")+"，银行卡："+amountMsg.getString("CARDTOTALMONEY")+
					"，支付宝："+amountMsg.getString("ZFBTOTALMONEY")+"，微信："+amountMsg.getString("WXTOTALMONEY")+
					"，电汇："+amountMsg.getString("TTTOTALMONEY");
		}else{
			foots = "没有相关数据";
		}
		dataMap.put("totalData", foots);
		dataMap.put("column", headerNames.length);
		dataMap.put("fileName", "收费明细");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

}
