package com.fh.controller.system.receipt;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;




import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.login.LoginController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.paymanage.PayManageManager;
import com.fh.service.system.receipt.ReceiptManager;
import com.fh.service.system.report.ReportManager;
import com.fh.service.system.reportStat.ReportStatManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.TableColums;
import com.keman.zhgd.common.Tools;
import com.keman.zhgd.common.datetime.DateUtil;
import com.keman.zhgd.common.tree.TreeJson;
import com.keman.zhgd.common.tree.VO;

/**
 * 收据控制器
 * <p>标题:ReceiptController</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 陈超超
 * @date 2017年8月15日 上午9:30:37
 */
@Controller
@RequestMapping(value="/receipt")
public class ReceiptController extends BaseController{
	
	@Resource(name="receiptService")
	private ReceiptManager receiptManager;
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	@Resource(name = "reportService")
	private ReportManager reportService;
	
	/**
	 * 
	 * <p>描述:收据设置</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午8:37:30
	 * @return
	 */
	@RequestMapping(value="/toReceiptSetting.php")
	public ModelAndView toReceiptSetting() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> receiptList = receiptManager.getPayItemGroupList(pd);
		mv.addObject("receiptList", receiptList);
		mv.setViewName("system/receipt/receiptSetting");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:获得所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/getAllPayItemList.json")
	@ResponseBody
	public ModelAndView getAllPayItemList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		//结果集
		List<PageData> payItemListResult = new ArrayList<>();
		//所有
		List<PageData> payItemListAll = new ArrayList<>();
		
		//系统自带的缴费项目
		List<PageData> systemPayOtherItemList = receiptManager.getAllPayOtherItemList(pd);
		payItemListAll.addAll(systemPayOtherItemList);
		
		//所有自定义缴费项目
		List<PageData> payItemList = receiptManager.getAllPayItemList(pd);
		payItemListAll.addAll(payItemList);
		
		//筛选
		if(payItemListAll.size() > 0){
			pd.put("groupPkid", "NOTNULL");
			List<PageData> payItemGroupList = receiptManager.getPayItemGroupList(pd);
			
			for(PageData payItem : payItemListAll){
				boolean flag = false;
				for(PageData payItemGroup : payItemGroupList){
					if(payItemGroup.get("PAY_ITEM_PKID") == null){
						continue;
					}
					if(payItemGroup.get("PAY_ITEM_PKID").toString().equals(payItem.get("PKID").toString())){
						flag = true;
					}
					
				}
				if(!flag){
					payItemListResult.add(payItem);
				}
				
			}
		}
		//打印时调用///////////////////////////////////////////////////////////////////////////////////
		//结果集
		List<PageData> payItemListResultForPrint = new ArrayList<>();
		if(pd.get("way") != null && "forPrint".equals(pd.get("way").toString())){
			if(pd.get("payItemPkids") != null){
				String [] payItemPkidsArray = pd.get("payItemPkids").toString().split(",");
				for (int i = 0; i < payItemPkidsArray.length; i++) {
					if(!StringUtils.isEmpty(payItemPkidsArray[i])){
						for (int j = 0; j < payItemListResult.size(); j++) {
							if(payItemListResult.get(j).get("PKID").toString().equals(payItemPkidsArray[i])){
								pd.put("payItemPkid", payItemPkidsArray[i]);
								PageData pdd = receiptManager.getPayOrderDetailCount(pd);
								if(pdd != null && pdd.getString("CCOUNT") != null && !"0".equals(pdd.getString("CCOUNT"))){
									payItemListResultForPrint.add(payItemListResult.get(j));
								}
								break;
							}
						}
					}
					
				}
			}
			resultMap.put("payItemListResult", payItemListResultForPrint);
		}
		//打印时调用///////////////////////////////////////////////////////////////////////////////////
		else{
			resultMap.put("payItemListResult", payItemListResult);
		}
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	
	/**
	 * 
	 * <p>描述:获得未分组缴费项目分组详情</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/getPayItemGroupsList.json")
	@ResponseBody
	public ModelAndView getPayItemGroupsList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		
		//父
		pd.put("groupPkid", "");
		List<PageData> payItemGroupParentList = receiptManager.getPayItemGroupList(pd);
		//子
		List<PageData> payItemGroupsList = receiptManager.getPayItemGroupsList(pd);
		
		if(payItemGroupParentList != null && payItemGroupParentList.size() > 0){
			for(PageData payItemGroupParent : payItemGroupParentList){
				List<PageData> pdList = new ArrayList<>();
				if(payItemGroupsList != null && payItemGroupsList.size() > 0){
					for(PageData payItemGroup : payItemGroupsList){
						if(payItemGroup.get("GROUP_PKID") != null 
								&& payItemGroup.get("GROUP_PKID").toString().equals(payItemGroupParent.get("PKID").toString())){
							
							//打印时调用///////////////////////////////////////////////////////////////////////////////////
							//结果集
							List<PageData> payItemListResultForPrint = new ArrayList<>();
							if(pd.get("way") != null && "forPrint".equals(pd.get("way").toString())){
								if(pd.get("payItemPkids") != null){
									String [] payItemPkidsArray = pd.get("payItemPkids").toString().split(",");
									for (int i = 0; i < payItemPkidsArray.length; i++) {
										if(!StringUtils.isEmpty(payItemPkidsArray[i])
												&& payItemGroup.get("PAY_ITEM_PKID") != null 
												&& payItemGroup.get("PAY_ITEM_PKID").toString().equals(payItemPkidsArray[i])){
											pd.put("payItemPkid", payItemPkidsArray[i]);
											PageData pdd = receiptManager.getPayOrderDetailCount(pd);
											if(pdd != null && pdd.getString("CCOUNT") != null && !"0".equals(pdd.getString("CCOUNT"))){
												pdList.add(payItemGroup);
											}
											break;
										}
//										if(!StringUtils.isEmpty(payItemPkidsArray[i])
//												&& payItemGroup.get("PAY_ITEM_PKID") != null 
//												&& payItemGroup.get("PAY_ITEM_PKID").toString().equals(payItemPkidsArray[i])){
//											pdList.add(payItemGroup);
//											continue;
//										}
									}
								}
								resultMap.put("payItemListResult", payItemListResultForPrint);
							}
							//打印时调用///////////////////////////////////////////////////////////////////////////////////
							else{
								pdList.add(payItemGroup);
							}
						}
					}
				}
				
				resultMap.put(payItemGroupParent.get("PKID").toString() + "@|@" + payItemGroupParent.get("GROUP_NAME").toString(), pdList);
			}
			
		}
		
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 
	 * <p>描述:添加分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/insertPayItemGroup.json")
	@ResponseBody
	public ModelAndView insertPayItemGroup() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		pd.put("pkid",this.get32UUID());
		receiptManager.insertPayItemGroup(pd);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 
	 * <p>描述:修改组名</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/updatePayItemGroup.json")
	@ResponseBody
	public ModelAndView updatePayItemGroup() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		receiptManager.updatePayItemGroup(pd);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 
	 * <p>描述:移动缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/moveGroup.json")
	@ResponseBody
	public ModelAndView moveGroup() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("XGR", user.getUSER_ID());
		
		if("1".equals(pd.get("type").toString())){//移动未分组的缴费项目
			pd.put("pkid",this.get32UUID());
			receiptManager.insertPayItemGroup(pd);
			resultMap.put("result", "success");
		}else if("2".equals(pd.get("type").toString())){//移动已分组的缴费项目
			if(!StringUtils.isEmpty(pd.getString("groupPkid"))){
				receiptManager.updatePayItemGroup(pd);
			}else{//移动到未分组
				receiptManager.deleteGroupByPayItemPkid(pd);
			}
			resultMap.put("result", "success");
		}else{
			resultMap.put("result", "fail");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	
	/**
	 * 
	 * <p>描述:获得所有一级分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/getAllGroups.json")
	@ResponseBody
	public ModelAndView getAllGroups() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		
		List<PageData> pds = receiptManager.getPayItemGroupList(pd);
			
		resultMap.put("result", "success");
		resultMap.put("pds", pds);
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 
	 * <p>描述:删除分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/deleteGroup.json")
	@ResponseBody
	public ModelAndView deleteGroup() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		try {
			receiptManager.deleteGroup(pd);
			resultMap.put("result", "success");
		} catch (Exception e) {
			resultMap.put("result", "fail");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}

	/**
	 * 
	 * <p>描述:跳转收据打印</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午7:37:30
	 * @return
	 */
	@RequestMapping(value="/toPrintReceipt.php")
	public ModelAndView toPrintReceipt() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(date);  
			calendar.add(Calendar.DAY_OF_MONTH, 0);  
			date = calendar.getTime(); //前一天的时间
			pd.put("DATESTART", sdf.format(new Date()));
			pd.put("DATEEND", sdfs.format(new Date()));//当前时间
			Properties prop = new Properties();
			InputStream in = null;
			in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
			prop.load(new InputStreamReader(in, "utf-8"));
			String collegeNameEn="";
			if(prop!=null){   //需要修改的地方 学校名称。。。。
				collegeNameEn=prop.getProperty("collegeNameEn");
			}
		//获得用户
		List<PageData> user_list = receiptManager.getUserList(pd);
		//获取缴费类型列表
//		List<PageData> pay_style_list = reportService.getPayStyleListAll(pd);
		//获取学年列表
//		List<PageData> school_year_list = reportService.getSchoolYeaListAll(pd);
		String colStr="";
		colStr=TableColums.currentUserTableShowCollums("SJDY_LIST");		
		mv.addObject("colStr",colStr);
//		mv.addObject("pay_style_list", pay_style_list);
		mv.addObject("user_list", user_list);
		mv.addObject("DATESTART", sdf.format(new Date()));
		mv.addObject("DATEEND",  sdfs.format(new Date()));
		mv.addObject("collegeNameEn", collegeNameEn);
		mv.setViewName("system/receipt/receiptPrint");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:查询收据table列表</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午16:57:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getReceiptlistPage.json")
	public ModelAndView getReceiptlistPage(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		List<PageData> list = null;
		if(!StringUtils.isEmpty(pd.getString("conditions"))){
			String conditions = new String(pd.getString("conditions").getBytes("ISO8859-1"), "utf-8");
			pd.put("conditions",conditions);
		}
		List<String> departmentIdList = new ArrayList<>();
		if(!StringUtils.isEmpty(pd.getString("departmentIdList"))){
			String [] departmentIdArray = pd.getString("departmentIdList").split(",");
			for(String departmentId : departmentIdArray){
				departmentIdList.add(departmentId);
			}
		}
		pd.put("departmentIdList",departmentIdList);
		
		List<String> payItemList = new ArrayList<>();
		if(Tools.notEmpty(pd.getString("payItem"))){
			String [] departmentIdArray = pd.getString("payItem").split(",");
			payItemList = Arrays.asList(departmentIdArray);
		}
		pd.put("payItemPkid",payItemList);
		
		page.setPd(pd);
		list=receiptManager.getPayOrderDetaillistPage(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:获取所有的缴费项目</p>
	 * @author ning 王宁
	 * @date 2018年7月6日 下午8:15:43
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllPayItemListListAll.json")
	@ResponseBody
	public ModelAndView getAllPayItemListListAll() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		//结果集
		List<PageData> payItemListResult = new ArrayList<>();
		//所有
		List<PageData> payItemListAll = new ArrayList<>();
		
		//系统自带的缴费项目
//		List<PageData> systemPayOtherItemList = receiptManager.getAllPayOtherItemList(pd);
//		payItemListAll.addAll(systemPayOtherItemList);
		
		//所有自定义缴费项目
		List<PageData> payItemList = receiptManager.getAllPayItemListAll(pd);
		payItemListAll.addAll(payItemList);
		
		//筛选
		if(payItemListAll.size() > 0){
			pd.put("groupPkid", "NOTNULL");
			List<PageData> payItemGroupList = receiptManager.getPayItemGroupList(pd);
			
			for(PageData payItem : payItemListAll){
				boolean flag = false;
				for(PageData payItemGroup : payItemGroupList){
					if(payItemGroup.get("PAY_ITEM_PKID") == null){
						continue;
					}
					if(payItemGroup.get("PAY_ITEM_PKID").toString().equals(payItem.get("PKID").toString())){
						flag = true;
					}
					
				}
				if(!flag){
					payItemListResult.add(payItem);
				}
				
			}
		}
		String jsonStr = "{\"id\":\"@@@\", \"pId\":\"0\", \"name\":\"全部\"},";
		for (int i = 0; i < payItemListResult.size(); i++) {
			jsonStr += "{\"id\":\""+payItemListResult.get(i).getString("PKID")+"\", \"pId\":\"@@@\", \"name\":\""+payItemListResult.get(i).getString("PAYITEM")+"\"},";
		}
		jsonStr = "["+jsonStr+"]";
		resultMap.put("payItemListResult", jsonStr);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	/**
	 * 
	 * <p>描述:获得所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月30日 下午8:36:45
	 * @return
	 */
	@RequestMapping(value="/getAllPayItemListList.json")
	@ResponseBody
	public ModelAndView getAllPayItemListList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		//结果集
		List<PageData> payItemListResult = new ArrayList<>();
		//所有
		List<PageData> payItemListAll = new ArrayList<>();
		
		//系统自带的缴费项目
//		List<PageData> systemPayOtherItemList = receiptManager.getAllPayOtherItemList(pd);
//		payItemListAll.addAll(systemPayOtherItemList);
		
		//所有自定义缴费项目
		List<PageData> payItemList = receiptManager.getAllPayItemList(pd);
		payItemListAll.addAll(payItemList);
		
		//筛选
		if(payItemListAll.size() > 0){
			pd.put("groupPkid", "NOTNULL");
			List<PageData> payItemGroupList = receiptManager.getPayItemGroupList(pd);
			
			for(PageData payItem : payItemListAll){
				boolean flag = false;
				for(PageData payItemGroup : payItemGroupList){
					if(payItemGroup.get("PAY_ITEM_PKID") == null){
						continue;
					}
					if(payItemGroup.get("PAY_ITEM_PKID").toString().equals(payItem.get("PKID").toString())){
						flag = true;
					}
					
				}
				if(!flag){
					payItemListResult.add(payItem);
				}
				
			}
		}
		String jsonStr = "{\"id\":\"@@@\", \"pId\":\"0\", \"name\":\"全部\"},";
		for (int i = 0; i < payItemListResult.size(); i++) {
			jsonStr += "{\"id\":\""+payItemListResult.get(i).getString("PKID")+"\", \"pId\":\"@@@\", \"name\":\""+payItemListResult.get(i).getString("PAYITEM")+"\"},";
		}
		jsonStr = "["+jsonStr+"]";
		resultMap.put("payItemListResult", jsonStr);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	
	/**
	 * 
	 * <p>描述:查询缴费记录明细</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月30日 下午2:36:45
	 * @return
	 */
	@RequestMapping(value="/getPayOrderDetail.json")
	@ResponseBody
	public ModelAndView getPayOrderDetail() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//当前登录用户
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        
		Map<String,Object> resultMap = new HashMap<>();
		
		List<String> detailPkidList = new ArrayList<>();
		if(!StringUtils.isEmpty(pd.getString("detailPkids")) && !"null".equals(pd.getString("detailPkids"))){
			String [] detailPkidsArray = pd.getString("detailPkids").split(",");
			detailPkidList = Arrays.asList(detailPkidsArray);
		}
		pd.put("detailPkidList", detailPkidList);
		
		//线下缴费打印
		List<String> payItemPkidList = new ArrayList<>();
		if(!StringUtils.isEmpty(pd.getString("isOffline")) && "true".equals(pd.getString("isOffline"))){
			if(!StringUtils.isEmpty(pd.getString("payItemPkids")) && !"null".equals(pd.getString("payItemPkids"))){
				String [] payItemPkidsArray = pd.getString("payItemPkids").split(",");
				payItemPkidList = Arrays.asList(payItemPkidsArray);
			}
			pd.put("payItemPkidList", payItemPkidList);
		}
		
		//获取数据
		Map<String, List<PageData>> dataMap = new HashMap<>();
		
		/*
		 * 如果没有序列则按年创建序列
		 */
		pd.put("SEQNAME", "SEQ_RECEIPTNO_"+com.keman.zhgd.common.util.DateUtil.getYear().substring(2, 4));
		PageData seqPd = receiptManager.getSeqCount(pd);
		if("0".equals(seqPd.getString("CCOUNT"))){
			receiptManager.createSeq(pd);
		}
		
		try {
			//获得所有缴费记录
			pd.put("detailPkidList", detailPkidList);
			List<PageData> payOrderDetailList = receiptManager.getPayOrderDetailList(pd);
			
			//组装数据
			if(payOrderDetailList != null && payOrderDetailList.size() > 0){
				
				//取KEY
				for (int i = 0; i < payOrderDetailList.size(); i++) {
					PageData payOrderDetail = payOrderDetailList.get(i);
					String studentPkid = payOrderDetail.getString("STUDENT_PKID");
					String payMode = payOrderDetail.getString("PAY_MODE");
					if(!StringUtils.isEmpty(studentPkid) && !StringUtils.isEmpty(payMode)){
						String key = payOrderDetail.getString("STUDENT_PKID")+payOrderDetail.getString("RUXUENIANFEN")+payOrderDetail.getString("BIANMA");
						if(!dataMap.containsKey(key)){
							dataMap.put(key, null);
						}
					}
				}
				//取value
				for (Map.Entry<String, List<PageData>> entry : dataMap.entrySet()) {
					//收据号集合  理工
//					Map<String, String> payItemPkidMap = new HashMap<>();
					//理工
					StringBuffer PRINTNOSB = new StringBuffer();
					
					List<PageData> pdList = new ArrayList<>();
					for (int i = 0; i < payOrderDetailList.size(); i++) {
						PageData payOrderDetail = payOrderDetailList.get(i);
						String studentPkid = payOrderDetail.getString("STUDENT_PKID");
						String payMode = payOrderDetail.getString("PAY_MODE");
						if(!StringUtils.isEmpty(studentPkid) && !StringUtils.isEmpty(payMode)){
							String key = payOrderDetail.getString("STUDENT_PKID")+payOrderDetail.getString("RUXUENIANFEN")+payOrderDetail.getString("BIANMA");
							if(entry.getKey().equals(key)){
								
								/*
								 * 财经学院打印时生成收据号
								 */
								if(Tools.notEmpty(pd.getString("fromSchool")) && ("CAIJING".equals(pd.getString("fromSchool")) || "GUANGDIAN".equals(pd.getString("fromSchool")) || "QHMY".equals(pd.getString("fromSchool")))){
									
									//收据号处理  每个缴费项目一个收据号
									String RECEIPTNO = payOrderDetail.getString("RECEIPTNO");
									if(Tools.isEmpty(RECEIPTNO)){
										//生成收据号
										RECEIPTNO = receiptManager.getSeqReceiptNO(pd);
									}
									
									PRINTNOSB.append(RECEIPTNO);
									PRINTNOSB.append(" ");
									PRINTNOSB.append("|");
									
									//修改打印状态、打印数量、是否打印、打印人  start.....
									List<String> detailPkidListTemp = new ArrayList<>();
									detailPkidListTemp.add(payOrderDetail.getString("PKID"));
									//修改打印状态
									pd.put("XGR", user.getUSER_ID());
									pd.put("printFlag", "Y");
									pd.put("RECEIPTNO", RECEIPTNO);
									pd.put("detailPkidList", detailPkidListTemp);
									pd.put("PRINTCOUNT", "+1");
									payOrderDetail.put("RECEIPTNO", RECEIPTNO);
									
									receiptManager.updatePayOrderDetails(pd);
									//修改打印状态、打印数量、是否打印、打印人  end.....
									
								}else if(Tools.notEmpty(pd.getString("fromSchool")) && "LIGONG".equals(pd.getString("fromSchool"))){//理工学院打印时暂时没特殊需求
									/*
									 * 理工订单生成的时候生成收据号
									 */
									
								}
								
								//XGR这里其实是收款人的数据，即订单创建人
								String xgr = payOrderDetail.getString("XGR");
								if(Tools.isEmpty(payOrderDetail.getString("XGR"))){
							        payOrderDetail.put("XGR", "");
							        /*if("GUANGDIAN".equals(pd.getString("fromSchool")) && "线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("XGR", "修莹莹");
									}else if("GUANGDIAN".equals(pd.getString("fromSchool")) && !"线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("XGR", Tools.notEmpty(xgr)?xgr:"");
									}*/
								}else{
									payOrderDetail.put("XGR", payOrderDetail.getString("XGR"));
									/*if("GUANGDIAN".equals(pd.getString("fromSchool")) && "线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("XGR", "修莹莹");
									}else if("GUANGDIAN".equals(pd.getString("fromSchool")) && !"线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("XGR", Tools.notEmpty(xgr)?xgr:"");
									}*/
								}
								if("GUANGDIAN".equals(pd.getString("fromSchool"))){
									payOrderDetail.put("XGR", "修莹莹");
								}
								 //开票人
								payOrderDetail.put("KAIPIAOREN", user.getNAME());
								if("CAIJING".equals(pd.getString("fromSchool")) && !"线上".equals(payOrderDetail.getString("ORDERCREATE_MODE"))){
									payOrderDetail.put("KAIPIAOREN", payOrderDetail.getString("XGR"));
								}else if("CAIJING".equals(pd.getString("fromSchool")) && "线上".equals(payOrderDetail.getString("ORDERCREATE_MODE"))){
									payOrderDetail.put("KAIPIAOREN", "");
								}
								/*if("GUANGDIAN".equals(pd.getString("fromSchool"))){
									if("线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("KAIPIAOREN", "修莹莹");
									}else if(!"线上".equals(payOrderDetail.getString("ORDERCREATE_MODE")) && "2".equals(pd.getString("BILL_TYPE"))){
										payOrderDetail.put("KAIPIAOREN", Tools.notEmpty(user.getNAME())?user.getNAME():"修莹莹");
									}
								}*/
								
								/*
								 * 支付方式
								 */
								String PAY_MODE = payOrderDetail.getString("PAY_MODE");
								if(Tools.notEmpty(PAY_MODE)){
									//sys_dictionaries表  支付方式:  CASH:现金  CARD:银行卡  WX:微信  ZFB:支付宝  字典类型 PayModeEnum
									if("CASH".equals(PAY_MODE)){
										payOrderDetail.put("PAY_MODE", "现金");
									}else if("CARD".equals(PAY_MODE)){
										payOrderDetail.put("PAY_MODE", "银行卡");
									}else if("WX".equals(PAY_MODE)){
										payOrderDetail.put("PAY_MODE", "微信");
									}else if("ZFB".equals(PAY_MODE)){
										payOrderDetail.put("PAY_MODE", "支付宝");
									}
								}else{
									payOrderDetail.put("PAY_MODE", "其它");
								}
								
								/*
								 * 宿舍
								 * 43代表住宿费
								 */
								String DORMNAME = payOrderDetail.getString("DORMNAME");
								payOrderDetail.put("DORMNAME", "");
								
								if(Tools.notEmpty(payOrderDetail.getString("PAY_STYLE_BIANMA")) && "43".equals(payOrderDetail.getString("PAY_STYLE_BIANMA"))){
									if(Tools.notEmpty(DORMNAME)){
										String [] DORMNAMEarray = DORMNAME.split("-");
										if(DORMNAMEarray.length > 3)
											payOrderDetail.put("DORMNAME", DORMNAMEarray[1]+"-"+DORMNAMEarray[3]);
											
									}
								}
								
								pdList.add(payOrderDetail);
							}
						}
					}
					
					PageData pdTemp = new PageData();
					String PRINTNOStr = PRINTNOSB.toString();
					if(Tools.notEmpty(PRINTNOStr) && "|".equals(PRINTNOStr.substring(PRINTNOStr.length()-1, PRINTNOStr.length()))){
						PRINTNOStr = PRINTNOStr.substring(0, PRINTNOStr.length()-1);
					}
					pdTemp.put("RECEIPTNO", PRINTNOStr);
					pdList.add(pdTemp);
					dataMap.put(entry.getKey(), pdList);
				} 
				
			}
			//修改打印状态
//			Session session = Jurisdiction.getSession();
//	        User user = (User) session.getAttribute(Const.SESSION_USER);
//			pd.put("XGR", user.getUSER_ID());
//			pd.put("printFlag", "Y");
//			receiptManager.updatePayOrderDetails(pd);
			resultMap.put("result", "success");
			resultMap.put("sysDay", DateUtil.getDay());
			resultMap.put("dataMap", dataMap);
			
		} catch (Exception e) {
			resultMap.put("result", "fail");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);}
	
	
	/**
	 * 
	 * <p>描述:获得所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午5:36:45
	 * @return
	 */
	@RequestMapping(value="/getAllPayItemListList2.json")
	@ResponseBody
	public ModelAndView getAllPayItemListList2() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		//所有
		List<PageData> payItemListAll = new ArrayList<>();
		
		//系统自带的缴费项目
		/*List<PageData> systemPayOtherItemList = receiptManager.getAllPayOtherItemList(pd);
		payItemListAll.addAll(systemPayOtherItemList);*/
		
		//所有自定义缴费项目
		List<PageData> payItemList = receiptManager.getAllPayItemList2(pd);
		payItemListAll.addAll(payItemList);
		
//		List<PageData> payItemListResult = new ArrayList<>();
		//筛选
		if(payItemListAll.size() > 0){
			List<PageData> payItemListYet = receiptManager.getPayItemListGroupByPayItemPkid(pd);
			for (int i = 0; i < payItemListAll.size(); i++) {
				PageData payItem = payItemListAll.get(i);
				for (PageData item : payItemListYet) {
					if(payItem.getString("PKID").equals(item.getString("PAY_ITEM_PKID"))){
						payItemListAll.remove(payItem);
						i --;
						break;
					}
				}
			}
		}
		
		String jsonStr = "{\"id\":\"@@@\", \"pId\":\"0\", \"name\":\"全部\"},";
		for (int i = 0; i < payItemListAll.size(); i++) {
			jsonStr += "{\"id\":\""+payItemListAll.get(i).getString("PKID")+"\", \"pId\":\"@@@\", \"name\":\""+payItemListAll.get(i).getString("PAYITEM")+"\"},";
		}
		jsonStr = "["+jsonStr+"]";
		resultMap.put("payItemListResult", jsonStr);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 获得所有缴费项目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllPayItemListList3.json")
	@ResponseBody
	public ModelAndView getAllPayItemListList3() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();
		
		//所有父缴费项
		List<PageData> payItemListAllParent = new ArrayList<>();
		//所有子缴费项
		List<PageData> payItemListAllChild = new ArrayList<>();
		
		//查询该学生已缴过的缴费项目
		List<PageData> payItemListYet = receiptManager.getPayItemListGroupByPayItemPkid(pd);
		List<String> payitemPKIDs = new ArrayList<>();
		if(payItemListYet != null && payItemListYet.size() > 0){
			for (int i = 0; i < payItemListYet.size(); i++) {
				payitemPKIDs.add(payItemListYet.get(i).getString("PAY_ITEM_PKID"));
			}
		}
		pd.put("payitemPKIDs", payitemPKIDs);
		//所有自定义缴费项目
		List<PageData> payItemList = receiptManager.getAllPayItemList3(pd);
		
		//筛选
		if(payItemList.size() > 0){
			for (int i = 0; i < payItemList.size(); i++) {
				PageData payItem = payItemList.get(i);
				if(Tools.isEmpty(payItem.getString("PARENT_ID"))){
					payItemListAllParent.add(payItem);
				}else{
					payItemListAllChild.add(payItem);
				}
			}
		}
		resultMap.put("payItemListAllParent", payItemListAllParent);
		resultMap.put("payItemListAllChild", payItemListAllChild);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 通过父缴费项目PKID获得旗下所有子缴费项目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllChildPayItemsByParentPkid.json")
	@ResponseBody
	public ModelAndView getAllChildPayItemsByParentPkid() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> resultMap = new HashMap<>();

		//通过父缴费项目PKID获得旗下所有子缴费项目
		List<PageData> payItemListAllChild = receiptManager.getAllChildPayItemsByParentPkid(pd);
		resultMap.put("payItemListAllChild", payItemListAllChild);
		resultMap.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 获得单据号
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月7日 下午5:31:09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getSeqReceiptNO.json")
	@ResponseBody
	public ModelAndView getSeqReceiptNO() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		try{
			if(Tools.notEmpty(pd.getString("PKID"))){
				String RECEIPTNO = receiptManager.getSeqReceiptNO(pd);
				
				resultMap.put("result", true);
				resultMap.put("RECEIPTNO", RECEIPTNO);
			}else{
				resultMap.put("result", false);
				resultMap.put("RECEIPTNO", "");
			}
			
			
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("RECEIPTNO", "");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	
	/**
	 * 修改打印状态、单据号、打印次数
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月8日 下午3:14:20
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePayOrderDetailStatus.json")
	@ResponseBody
	public ModelAndView updatePayOrderDetailStatus() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> resultMap = new HashMap<>();
		try{
			if(Tools.notEmpty(pd.getString("PKID"))){
				List<String> detailPkidList = new ArrayList<>();
				detailPkidList.add(pd.getString("PKID"));
				//修改打印状态
				Session session = Jurisdiction.getSession();
		        User user = (User) session.getAttribute(Const.SESSION_USER);
				pd.put("XGR", user.getUSER_ID());
				pd.put("printFlag", "Y");
				pd.put("detailPkidList", detailPkidList);
				pd.put("PRINTCOUNT", "+1");
				
				receiptManager.updatePayOrderDetails(pd);
				
				resultMap.put("result", true);
			}else{
				resultMap.put("result", false);
			}
			
			
		} catch (Exception e) {
			resultMap.put("result", false);
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	/**
	 * 发票号获取
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年8月28日 下午3:14:20
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getinvoicestatus.json")
	@ResponseBody
	public ModelAndView getinvoicestatus() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("LEASER_USER", user.getUSER_ID());
		//查询发票号
		PageData invoiceNum = receiptManager.getInvoiceInfo(pd);
		Map<String,Object> resultMap = new HashMap<>();
		//如果不为空说明还有可使用的发票号 2代表有可使用的  1代表没有可使用的
		if(invoiceNum!=null){
			resultMap.put("isHasNum", "2");
			resultMap.put("pd", invoiceNum);
		}else{
			resultMap.put("isHasNum", "1");
		}
		return new ModelAndView(new MappingJackson2JsonView(),resultMap);
	}
	/**
	 * 修改发票使用状态
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年8月28日 下午3:14:20
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateinvoicestatus.json")
	@ResponseBody
	public void updateinvoicestatus() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//前台拼接的所使用的发票号
		String invoiceNum = pd.getString("invoiceNumList");
		String [] invoiceNumArr = invoiceNum.split(",");
		//分割后循环将怕票号对应的发票状态改成已使用
		for(int i = 0;i<invoiceNumArr.length;i++){
			if(invoiceNumArr[i]!=null&&!"".equals(invoiceNumArr[i])){
				pd.put("INVOICE_NO", invoiceNumArr[i]);
				receiptManager.updateInvoiceStatus(pd);
			}
		}
		//前台拼接的pkid+发票号分割成数组
		String tempParamList = pd.getString("tempParam");
		String [] tempParamListArr = tempParamList.split(",");
		//分割后将数组循环拿到'pkid+fapiaohao'形式
		for(int i = 0;i<tempParamListArr.length;i++){
			//判空
			if(tempParamListArr[i]!=null&&!"".equals(tempParamListArr[i])){
				if(tempParamListArr[i]!=null&&!"".equals(tempParamListArr[i])){
					//+正则中是关键字所以用\\转义
					String [] tempParamArr = tempParamListArr[i].split("\\+");
					if(tempParamArr[0]!=null&&!"".equals(tempParamArr[0])){
						//将pkid放到list中
						List<String> detailPkidListTemp = new ArrayList<>();
						detailPkidListTemp.add(tempParamArr[0]);
						//修改打印状态
						pd.put("XGR", user.getUSER_ID());
						pd.put("printFlag", "Y");
						pd.put("RECEIPTNO", tempParamArr[1]);//每一个pkid对应的发票号
						pd.put("detailPkidList", detailPkidListTemp);
						pd.put("PRINTCOUNT", "+1");					
						receiptManager.updatePayOrderDetails(pd);
					}
					
				}
				
			}
		}

		
	}
	
	
	
}
