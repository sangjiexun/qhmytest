package com.fh.controller.system.dateAccount;
import java.util.ArrayList;
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
import com.fh.entity.system.User;
import com.fh.service.system.dateAccount.DateAccountManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.TableColums;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.keman.zhgd.common.Tools;

/**
 * 
 * <p>标题:DateAccountController</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 王宁
 * @date 2017年11月6日 下午9:09:44
 */
@Controller
@RequestMapping(value="/dateAccount")
public class DateAccountController extends BaseController {
	
	@Resource(name="dateAccountService")
	private DateAccountManager dateAccountService;
	/**
	 * 
	 * <p>描述:日结账初始化进入方法</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:37:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/dateAccount.php")
	public ModelAndView checkMoney()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR",user.getUSER_ID());
		mv.addObject("pd", pd);
		mv.setViewName("system/dateAccount/dateAccount_list");
		return mv;
	}
	/**
	 * 
	 * <p>描述:获取日结账表单数据</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:52:40
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("dateAccountTable.json")
	public ModelAndView dateAccountTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR",user.getUSER_ID());
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		List<PageData> list = null;
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		list=dateAccountService.dateAccountlistPage(page);//获取作废表格数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:作废所选记录</p>
	 * @author wn 王宁
	 * @date 2018年12月12日 下午1:46:54
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("zuofei.json")
	public ModelAndView zuofei()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("INSERT_USER",user.getUSER_ID());
		Map<String, Object> map = new HashMap<String, Object>();
		dateAccountService.updateClearOrder(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:更新汇总信息</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:54:18
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getAmountMsg.json")
	public ModelAndView getAmountMsg()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR",user.getUSER_ID());
		PageData lastCheckPd = dateAccountService.getlastCheckDate(pd);//得到最后一次结账日期
		if(lastCheckPd!=null){
			pd.put("LASTCHECKDATE", lastCheckPd.getString("CHECKDAY_DATE"));//结账时间
		}
		PageData amountMsg = dateAccountService.getAmountMsg(pd);//获取汇总总金额信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amountMsg", amountMsg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:确定并结账</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:56:14
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("settleAccounts.json")
	public ModelAndView settleAccounts()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR",user.getUSER_ID());
		PageData lastCheckPd = dateAccountService.getlastCheckDate(pd);//得到最后一次结账日期
		if(lastCheckPd!=null){
			pd.put("LASTCHECKDATE", lastCheckPd.getString("CHECKDAY_DATE"));//结账时间
		}
		PageData amountMsg = dateAccountService.getAmountMsg(pd);//获取汇总总金额信息
		amountMsg.put("CJR", user.getUSER_ID());
		amountMsg.put("PKID", this.get32UUID());//核账主表主键
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> listDetail  = dateAccountService.dateAccountlist(pd);//获取日结账明细
		if(listDetail.size()==0){
			map.put("result", "error");
		}else{
			dateAccountService.saveCheck(amountMsg,listDetail);
			map.put("result", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:导出到Excel</p>
	 * @author Administrator 王宁
	 * @date 2017年9月15日 下午5:11:32
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel.json")
	public ModelAndView daoChu(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMa(pd);//中文转码
		Session session = Jurisdiction.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("CJR",user.getUSER_ID());
		PageData lastCheckPd = dateAccountService.getlastCheckDate(pd);//得到最后一次结账日期
		if(lastCheckPd!=null){
			pd.put("LASTCHECKDATE", lastCheckPd.getString("CHECKDAY_DATE"));//结账时间
		}
		String colStr="";
		colStr=TableColums.currentUserTableShowCollums("T_RIJIEZHANG");
		String col[] = colStr.split(",");
		List<String> listHeader = new ArrayList<>();
		List<String> listBody = new ArrayList<>();
		String[] headerNames={};
		String[] bodyNames={};
		if(Tools.notEmpty(colStr)){
			headerNames = new String[col.length];
			bodyNames = col;
			for(int i=0;i<col.length;i++){
				if("SHENFENZHENGHAO".equals(bodyNames[i])){
					listHeader.add("身份证号");
					listBody.add("SHENFENZHENGHAO");
				}else if("XINGMING".equals(bodyNames[i])){
					listHeader.add("姓名");
					listBody.add("XINGMING");
				}else if("NIANJI".equals(bodyNames[i])){
					listHeader.add("入学年份");
					listBody.add("NIANJI");
				}else if("ZHUANYE".equals(bodyNames[i])){
					listHeader.add("院校专业");
					listBody.add("ZHUANYE");
				}else if("PAY_STYLE_NAME".equals(bodyNames[i])){
					listHeader.add("缴费类型");
					listBody.add("PAY_STYLE_NAME");
				}else if("SCHOOL_YEAR_NAME".equals(bodyNames[i])){
					listHeader.add("学年");
					listBody.add("SCHOOL_YEAR_NAME");
				}else if("PARENTITEM".equals(bodyNames[i])){
					listHeader.add("缴费项目");
					listBody.add("PARENTITEM");
				}else if("PAYITEM".equals(bodyNames[i])){
					listHeader.add("缴费子项目");
					listBody.add("PAYITEM");
				}else if("JIAOFEILEIXING".equals(bodyNames[i])){
					listHeader.add("支付类型");
					listBody.add("JIAOFEILEIXING");
				}else if("CASHMONEY".equals(bodyNames[i])){
					listHeader.add("现金");
					listBody.add("CASHMONEY");
				}else if("CARDMONEY".equals(bodyNames[i])){
					listHeader.add("银行卡");
					listBody.add("CARDMONEY");
				}else if("TTMONEY".equals(bodyNames[i])){
					listHeader.add("电汇");
					listBody.add("TTMONEY");
				}else if("WXMONEY".equals(bodyNames[i])){
					listHeader.add("微信");
					listBody.add("WXMONEY");
				}else if("ZFBMONEY".equals(bodyNames[i])){
					listHeader.add("支付宝");
					listBody.add("ZFBMONEY");
				}else if("CJSJ".equals(bodyNames[i])){
					listHeader.add("操作时间");
					listBody.add("CJSJ");
				}
			}
			for(int i=0;i<listHeader.size();i++){
				headerNames[i]=listHeader.get(i).toString();
			}
		}else{
			headerNames = new String[]{"身份证号","姓名","入学年份","院校专业","缴费类型","学年","缴费项目","缴费子项目","支付类型","现金","银行卡","电汇","微信","支付宝","操作时间"};
			bodyNames = new String[]{"SHENFENZHENGHAO","XINGMING","NIANJI","ZHUANYE","PAY_STYLE_NAME","SCHOOL_YEAR_NAME","PARENTITEM","PAYITEM","JIAOFEILEIXING","CASHMONEY","CARDMONEY","TTMONEY","WXMONEY","ZFBMONEY","CJSJ"};
		}
		List<PageData> varOList = dateAccountService.dateAccountlistExcel(pd);
		Map<String,Object> dataMap = Utils.toExcel(headerNames,bodyNames,varOList);
		dataMap.put("fileName", "日结账");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
}