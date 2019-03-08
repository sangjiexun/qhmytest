package com.fh.controller.system.summaryStat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.dorm.StudentDormManager;
import com.fh.service.system.stuinfo.StuSignSupManager;
import com.fh.service.system.summaryStat.SummaryStatManager;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;

/**
 * 
 * <p>
 * 标题:SummaryStatController
 * </p>
 * <p>
 * 描述:统计报表页面
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年12月21日 上午10:22:54
 */
@Controller
@RequestMapping("/summarystat")
public class SummaryStatController extends BaseController {

	@Autowired
	private SummaryStatManager SummaryStatService;

	@Autowired
	private StuSignSupManager stusingsupService;

	@Autowired
	private StudentDormManager studentDormService;

	/**
	 * 
	 * <p>
	 * 描述:去宿舍统计页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月21日 上午10:20:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toDormitory.php")
	public ModelAndView toDormitory() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		List<PageData> loulist = this.SummaryStatService.getLou(pd);
		List<PageData> treelist = null;
		treelist = this.SummaryStatService.getDormTree(pd);

		mv.addObject("loulist", loulist);
		mv.addObject("treelist", treelist);
		mv.setViewName("system/summaryStat/summaryStatDor");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取宿舍统计表信息
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月21日 上午10:22:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/table.json")
	public ModelAndView tablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = null;
		String name = pd.getString("NAME");

		if (!"null".equals(name) && !"".equals(name))
			pd.put("namelist", name.split(","));

		list = this.SummaryStatService.getList(pd);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:宿舍统计信息导出
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:28:51
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel.json")
	public ModelAndView daoChu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);// 中文转码
		String name = pd.getString("NAME");

		if (!"null".equals(name) && !"".equals(name))
			pd.put("namelist", name.split(","));

		String[] headerNames = new String[] { "校区", "宿舍楼", "楼层", "总床位数",
				"男生人数", "女生人数", "已住总人数", "已住男生人数", "已住女生人数", "空闲总床位数",
				"空闲男生人数", "空闲女生人数", "入住率" };
		String[] bodyNames = new String[] { "XIAOQU", "LOU", "CENG",
				"Z_MAXCHUANG", "Z_NANSHENG", "Z_NVSHENG", "Y_MAXCHUANG",
				"Y_NANSHENG", "Y_NVSHENG", "K_MAXCHUANG", "K_NANSHENG",
				"K_NVSHENG", "RZL" };

		List<PageData> varOList = this.SummaryStatService.getList(pd);

		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				varOList);
		dataMap.put("fileName", "宿舍统计信息");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:去退费明细页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午8:49:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toRefund.php")
	public ModelAndView toRefund() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		List<PageData> rxnf = null;// 入学年份
		List<PageData> banji_type = null;// 班型
		List<PageData> cengci = null;// 层次
		List<PageData> payitem = null;// 缴费项目

		rxnf = this.SummaryStatService.getrxnf(pd);
		banji_type = this.SummaryStatService.getbanji_type(pd);
		cengci = this.SummaryStatService.getcengci(pd);
		payitem = this.SummaryStatService.getpayitem(pd);

		Date date = new Date();// 取时间
		Date dates = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStringstart = formatter.format(date);
		String dateStringend = formatter.format(dates);
		pd.put("STARTTIME", dateStringstart);
		pd.put("ENDTIME", dateStringend);//当前时间
		PageData amountMsg = SummaryStatService.getRefundDetailSum(pd);
		mv.addObject("amountMsg", amountMsg);
		mv.addObject("rxnf", rxnf);
		mv.addObject("banji_type", banji_type);
		mv.addObject("cengci", cengci);
		mv.addObject("payitem", payitem);
		mv.addObject("dateStringstart", dateStringstart);
		mv.addObject("dateStringend", dateStringend);// treelist

		mv.setViewName("system/summaryStat/summaryRefund");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取退费明细表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:49:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/RefundTable.json")
	public ModelAndView stuFenBan(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_date=pd.getString("STARTTIME");
		String end_date=pd.getString("ENDTIME");
		if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(start_date);
			Date end = sdf.parse(end_date);
			pd.put("STARTTIME", sdf.format(start));
			pd.put("ENDTIME", sdf.format(end));
			
		}
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;
		list = this.SummaryStatService.getRefundlist(page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:退费明细合计</p>
	 * @author Administrator wzz
	 * @date 2019年1月22日 下午4:14:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getRefundDetailSum.json")
	public ModelAndView getRefundDetailSum(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String start_date=pd.getString("STARTTIME");
		String end_date=pd.getString("ENDTIME");
		if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(start_date);
			Date end = sdf.parse(end_date);
			pd.put("STARTTIME", sdf.format(start));
			pd.put("ENDTIME", sdf.format(end));
			
		}
		PageData sumPd = SummaryStatService.getRefundDetailSum(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("sumPd", sumPd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>
	 * 描述:退费明细统计导出
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 下午2:28:51
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tuifeiExcel.json")
	public ModelAndView tuifeiExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);// 中文转码
		String start_date=pd.getString("STARTTIME");
		String end_date=pd.getString("ENDTIME");
		/*if(!"".equals(start_date)&&!"null".equals(start_date)&&!"".equals(end_date)&&!"null".equals(end_date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse(start_date+" 00:00:00");
			Date end = sdf.parse(end_date+" 23:59:59");
			pd.put("STARTTIME", sdf.format(start));
			pd.put("ENDTIME", sdf.format(end));
			
		}*/
		String[] headerNames = new String[] { "身份证号", "姓名", "学号","文化课学校","性别","学生类型", "缴费类型",
				"入学年份", "班型", "退费金额", "退费时间", "现金", "银行卡", "电汇", "退费原因"};
		String[] bodyNames = new String[] { "SHENFENZHENGHAO", "XINGMING",
				"XUEHAO","WHKXUEXIAONAME","XINGBIE",
				"CCNAME", "PAY_STYLE_NAME", "RXNF", "BANJI_TYPE", "MONEY",
				"XGSJ", "CASH", "CARD", "TT", "REMARK"};

		List<PageData> varOList = this.SummaryStatService.getRefund(pd);
		PageData refundDetailSum = SummaryStatService.getRefundDetailSum(pd);
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				varOList);
		dataMap.put("fileName", "退费明细统计信息");
		dataMap.put("totalData", "退费总金额："+refundDetailSum.getString("TOALREFUNDMONEY")+"，现金："+refundDetailSum.getString("TOTALCASH")+
				"，银行卡："+refundDetailSum.getString("TOTALCARDMONEY")+"，电汇："+refundDetailSum.getString("TOTALTTMONEY"));
		dataMap.put("column", headerNames.length);
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
