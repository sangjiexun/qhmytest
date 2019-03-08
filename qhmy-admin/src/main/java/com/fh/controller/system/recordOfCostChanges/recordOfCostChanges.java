package com.fh.controller.system.recordOfCostChanges;



import java.text.SimpleDateFormat;
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
import com.fh.service.system.recordOfCostChanges.RecordOfCostChangesManager;
import com.fh.service.system.reportStat.ReportStatManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
/**
 * 
 * <p>标题:recordOfCostChangesController</p>
 * <p>描述:费用变动明细</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author 刘晓
 * @date 2019年1月16日 上午11:34:59
 */
@Controller
@RequestMapping(value="/recordOfCostChanges")
public class recordOfCostChanges  extends BaseController {
	
	@Resource(name = "recordOfCostChangesService")
	private RecordOfCostChangesManager recordOfCostChangesService;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	
/**
 * 
 * <p>描述:去费用变动页面</p>
 * @author Administrator 刘晓
 * @date 2019年1月16日 上午10:01:07
 * @return
 * @throws Exception
 */
	@RequestMapping(value="/recordOfCostChanges.php")
	public ModelAndView toFeeDetail() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//入学年份呢
		List<PageData> ruxuenianfenList = recordOfCostChangesService.getRuxuenianfenList(pd);		
		//班型
		List<PageData> banxingList = recordOfCostChangesService.getBanxingList(pd);
		//缴费类型
		List<PageData> jiaofeileixingList = recordOfCostChangesService.getJiaofeileixingList(pd);			
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
		mv.addObject("ruxuenianfenList", ruxuenianfenList);
		mv.addObject("banxingList", banxingList);
		mv.addObject("jiaofeileixingList", jiaofeileixingList);
		mv.addObject("pd", pd);
		mv.setViewName("system/recordOfCostChanges/recordOfCostChanges");
		return mv;

	}
	
/**
 * 
 * <p>描述:根据条件获取数据</p>
 * @author Administrator 刘晓
 * @date 2019年1月16日 上午10:01:48
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
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		

		
		page.setPd(pd);
		list = recordOfCostChangesService.getFeeDetailTablelistPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
/**
 * 
 * <p>描述:导出费用变动明细</p>
 * @author Administrator 刘晓
 * @date 2019年1月16日 上午10:02:06
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
		
		
		String[] headerNames=null;
		String[] bodyNames=null;


		headerNames = new String[]{"身份证号","姓名","学号","文化课学校","入学年份","班型","缴费类型",
				 "调整前应收","调整后应收","差额","说明","经办人","操作人","操作时间"};
	    bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","XUEHAO","SCHOOLNAME","NIANJI","BANJITYPENAME",
	    		"PAY_STYLE_NAME","YYSJE","TZHJE","CHAE",
	    		"YHSM","JBR","CCZ","CJSJ"};

		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<PageData>	varOList = recordOfCostChangesService.getFeeDetailTablelist(pd);
		dataMap = Utils.toExcel(headerNames,bodyNames,varOList);

		dataMap.put("fileName", "费用变动记录");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

}
