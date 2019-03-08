package com.fh.controller.importlist;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.login.LoginController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.excel.ImportErrorEnum;
import com.fh.excel.ItemListParseExcel;
import com.fh.service.importitem.itemlist.ItemListManager;
import com.fh.util.ArithUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Md5Util;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;
import com.fh.util.upload.UploadConst;
import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;
import com.keman.zhgd.common.util.Tools;
import com.newsky.epay.sdk.HttpClient;

/**
 * <p>标题:AuthorityManageController</p>
 * <p>描述:导入缴费名单、导入已缴名单</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午5:31:16
 */
@Controller
@RequestMapping(value="/import")
public class ImportListController extends BaseController {
	
	@Resource
	private ItemListManager itemListManager;
	
	
	/**
	 * <p>描述:导入缴费名单 首页</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午5:34:27
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/import-list.json")
	public ModelAndView importList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/import-list");
		mv.addObject("uuid", this.get32UUID());
		mv.addObject("item_pkid", pd.getString("item_pkid"));
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:缴费名单解析</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午10:11:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-list-parse")
	public ModelAndView importListParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.ITME_LIST).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getList(file,pd.getString("fileName"));
			//end 解析excel
			
			String item_pkid = pd.getString("item_pkid");//选择的缴费项目,也可能没有选择
			
			//业务处理
			PageData tempPageData = null;
			PageData studentPageData = null;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			
			PageData itemListPagedata = null;
			
			String STATUS = "";
			
			List<PageData> batchSaveDatas = new ArrayList<PageData>();//批量插入的数据
			int batchSaveIndex = 0;
			for (PageData pageData : datas) {
				
				pageDatatrim(pageData);//去掉前后空格
				
				if (isAllEmpty(pageData)) {
					pageData.put("error", ImportErrorEnum.no_avail_data.getValue());
					errorList.add(pageData);
					continue;
				}
				
				if (Tools.isEmpty(pageData.getString("cost")) || Tools.isEmpty(pageData.getString("grade"))
					||	Tools.isEmpty(pageData.getString("cardNo")) ||	(Tools.isEmpty(pageData.getString("classType")))) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				//校验金额合不合法
				if(!Utils.isNumber(pageData.getString("cost"))){
					pageData.put("error", ImportErrorEnum.not_cash_error.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("item_pkid", pd.getString("item_pkid"));
				//1、根据项目PKID查询项目入学年份和班型
				tempPageData = itemListManager.queryPayItemAndList(pageData); // itemName
				if (tempPageData == null) {
					pageData.put("error", ImportErrorEnum.not_find_item.getValue());
					errorList.add(pageData);
					continue;
				}
				String GRADE_NAME = tempPageData.getString("GRADE_NAME");//入学年份名称
				String CLASSTYPE_NAME = tempPageData.getString("CLASSTYPE_NAME");//班型名称
				if (!GRADE_NAME.equals(pageData.getString("grade"))) {//比较入学年份名称是否一致
					pageData.put("error", ImportErrorEnum.no_difference_grade.getValue());
					errorList.add(pageData);
					continue;
				}
				if (!CLASSTYPE_NAME.equals(pageData.getString("classType"))) {//比较班型名称是否一致
					pageData.put("error", ImportErrorEnum.no_difference_classType.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("GRADE_PKID", tempPageData.getString("GRADE_PKID"));
				pageData.put("CLASSTYPE_PKID", tempPageData.getString("CLASSTYPE_PKID"));
				if("12345678902".equals(tempPageData.getString("PAY_TYPE_PKID"))){//表示预交
					//根据身份证号和预交班型和预交年份,判断学生是否报名
					studentPageData = itemListManager.queryStudentYj(pageData);
				}else{
					//根据身份证号和班型和入学年份,判断学生是否报名
					studentPageData = itemListManager.queryStudent(pageData);
				}
				if (studentPageData == null || Tools.isEmpty(studentPageData.getString("PKID"))) {
					pageData.put("error", ImportErrorEnum.not_bm_student.getValue());
					errorList.add(pageData);
					continue;
				}
				//判断导入名单中是否有重复数据
				boolean flag = false;
				for(PageData pdd : batchSaveDatas){
					if(pageData.getString("cardNo").equals(pdd.getString("cardNo"))){
						pageData.put("error", ImportErrorEnum.stucard_cffail.getValue());
						pdd.put("error", ImportErrorEnum.stucard_cffail.getValue());
						errorList.add(pdd);
						batchSaveDatas.remove(pdd);
						errorList.add(pageData);
						flag = true;
						break;
						
					}
				}
				if(flag){
					continue;
				}
				
				pageData.put("student_pkid", studentPageData.getString("STUDENT_PKID"));//存储学生PKID
				pageData.put("STUDENT_BM_PKID", studentPageData.getString("PKID"));//存储学生附表PKID
				/*pageData.put("item_pkid", String.valueOf(tempPageData.getString("PKID")));*/
				pageData.put("item_pkid", pd.getString("item_pkid"));
				
				//实缴不为0
				itemListPagedata = itemListManager.queryPayList(pageData); // itemName
				
				
				if(itemListPagedata != null){
					if ((Tools.notEmpty(itemListPagedata.getString("AMOUNTCOLLECTION"))
							&& !"0".equals(itemListPagedata.getString("AMOUNTCOLLECTION")) )
							) {
						pageData.put("error", ImportErrorEnum.has_trans_record.getValue());
						errorList.add(pageData);
						continue;
					}
					if (Tools.notEmpty(itemListPagedata.getString("PKID"))) {//存在名单表  update
						pageData.put("error", ImportErrorEnum.has_item_list.getValue());
						errorList.add(pageData);
						continue;
						/*try {
							updateItemList(pageData,itemListPagedata);//更新名单
						} catch (Exception e) {
							e.printStackTrace();
							pageData.put("error", ImportErrorEnum.update_fail.getValue());
							errorList.add(pageData);
							continue;
						}*/
					}
				}else {//名单表不存在  insert
					try {
						batchSaveIndex++;
						saveItemList(pageData,tempPageData);//保存名单记录
						batchSaveDatas.add(pageData);
						/*if (batchSaveDatas.size()%500 == 0) {//到500条了
							//批量保存
							itemListManager.batchSaveDatas(batchSaveDatas);
							batchSaveDatas.clear();//清空
						}*/
						
					} catch (Exception e) {
						e.printStackTrace();
						if ("批量异常".equals(e.getMessage())) {
							for (PageData pageData2 : batchSaveDatas) {
								pageData2.put("error", ImportErrorEnum.insert_fail.getValue());
								errorList.add(pageData2);
							}
							batchSaveDatas.clear();//清空
							continue;
						}else {
							pageData.put("error", ImportErrorEnum.insert_fail.getValue());
							errorList.add(pageData);
							continue;
						}
					}
				}
				
			}
			
			
			//插入，执行剩余的
			try {
				if(batchSaveDatas.size()>0){
					//批量保存
					itemListManager.batchSaveDatas(batchSaveDatas);
					batchSaveDatas.clear();//清空
				}
			} catch (Exception e) {
				batchSaveDatas.clear();//清空
				for (PageData pageData2 : batchSaveDatas) {
					pageData2.put("error", ImportErrorEnum.insert_fail.getValue());
					errorList.add(pageData2);
				}
				e.printStackTrace();
			}
			//end 插入，执行剩余的
			
			
			
			
			//end 业务处理
			
 			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writeExcel(errorList);//pkid
			}
			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:插入名单</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午3:24:17
	 * @param pageData
	 * @param tempPageData
	 */
	private void saveItemList(PageData pageData, PageData tempPageData) throws Exception {
		// TODO Auto-generated method stub
		pageData.put("pkid", this.get32UUID());
		
		pageData.put("pay_item_pkid", pageData.getString("item_pkid"));//项目PKID
		
		pageData.put("student_pkid", pageData.getString("student_pkid"));//学生pkid
		
		pageData.put("pay_item_parent_pkid", tempPageData.getString("PAY_ITEM_PARENT_PKID"));
		
		pageData.put("cost", pageData.getString("cost"));//费用
		pageData.put("is_default", "Y");
		pageData.put("amountreceivable", pageData.getString("cost"));//应收金额
		pageData.put("discount_mode", DiscountModeEnum.不优惠.getValue());// 优惠方式
		pageData.put("discount", "不优惠");// 直减 - xx
		pageData.put("discount_money", Tools.toDouble("0"));//优惠金额
//		itemListManager.saveItemList(pageData);//保存
	}

	/**
	 * 
	 * <p>描述:更新名单</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午3:24:24
	 * @param pageData 新的名单
	 * @param tempPageData 旧的名单
	 */
	private void updateItemList(PageData pageData, PageData tempPageData) throws Exception {
		// TODO Auto-generated method stub
		
		pageData.put("pay_item_list_pkid", tempPageData.getString("PKID"));//存储名单pkid
		pageData.put("pay_item_pkid", tempPageData.getString("PAY_ITEM_PKID"));//项目PKID
		pageData.put("student_pkid", pageData.getString("student_pkid"));//学生pkid
		pageData.put("cost", pageData.getString("cost"));
		pageData.put("amountreceivable", Tools.toDouble(pageData.getString("cost")));//应收金额
		pageData.put("discount_mode", DiscountModeEnum.不优惠.getValue());// 优惠方式
		pageData.put("discount", "不优惠");// 直减 - xx
		pageData.put("discount_money", Tools.toDouble("0"));//优惠金额
		itemListManager.updateItemList(pageData);//保存	
	}

	/**
	 * 
	 * <p>描述:去掉前后空格</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午3:03:43
	 * @param pageData
	 */
	private void pageDatatrim(PageData pageData) {
		// TODO Auto-generated method stub
//		pageData.put("item", pageData.getString("itemName").trim());
		pageData.put("stuName", pageData.getString("stuName").trim());
		pageData.put("stuNumber", pageData.getString("stuNumber").trim());
		pageData.put("cardNo", pageData.getString("cardNo").trim());
		pageData.put("cost", pageData.getString("cost").trim());
		pageData.put("discountMoney", pageData.getString("discountMoney").trim());
	}
	
	private void pageDatatrim2(PageData pageData) {
		// TODO Auto-generated method stub
//		pageData.put("item", pageData.getString("itemName").trim());
		pageData.put("stuName", pageData.getString("stuName").trim());
		pageData.put("stuNumber", pageData.getString("stuNumber").trim());
		pageData.put("cardNo", pageData.getString("cardNo").trim());
		pageData.put("TOTALMONEY", pageData.getString("TOTALMONEY").trim());
	}

	/**
	 * 
	 * <p>描述:是否都为空</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午2:55:12
	 * @return
	 */
	private boolean isAllEmpty(PageData pageData){
		if(/*Tools.isEmpty(pageData.getString("itemName"))
			&& */Tools.isEmpty(pageData.getString("stuName"))
			&& Tools.isEmpty(pageData.getString("stuNumber"))
			&& Tools.isEmpty(pageData.getString("cardNo"))
			&& Tools.isEmpty(pageData.getString("discountMoney")) && Tools.isEmpty(pageData.getString("cost")) ){
			return true;
		}
		return false;
	}
	
	private boolean isAllEmpty2(PageData pageData){
		if(/*Tools.isEmpty(pageData.getString("itemName"))
			&& */Tools.isEmpty(pageData.getString("stuName"))
			&& Tools.isEmpty(pageData.getString("stuNumber"))
			&& Tools.isEmpty(pageData.getString("cardNo"))
			&& Tools.isEmpty(pageData.getString("TOTALMONEY"))){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * <p>描述:导入缴费名单</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月17日 下午5:50:58
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importpaidlist.json")
	public ModelAndView importPaidList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		pd.put("payitem", pd.getString("item_pkid"));
		mv.setViewName("system/import/importpaidlist");
		mv.addObject("uuid", this.get32UUID());
		mv.addObject("payitem", pd.getString("item_pkid"));
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 
	 * <p>描述:导入批量减免</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午9:05:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importJianMian.json")
	public ModelAndView importJianMian() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/import/importJianMian");
		mv.addObject("uuid", this.get32UUID());
		mv.addObject("payitem", pd.getString("item_pkid"));
		pd.put("payitem", pd.getString("item_pkid"));
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:已缴名单解析</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月17日 下午5:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-paidlist-parse")
	public ModelAndView imporPaidtListParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMas(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.ITME_PAID_LIST).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getPaidList(file,pd.getString("fileName"));
			//end 解析excel
			
			String payitem = pd.getString("item_pkid");//选择的缴费项目,也可能没有选择
			
			//业务处理
			PageData tempPageData = null;
			PageData studentPageData = null;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList = new ArrayList<PageData>();//记录错误的导入信息
			for (PageData pageData : datas) {
				
				pageDatatrim2(pageData);//去掉前后空格
				
				if (isAllEmpty2(pageData)) {
					pageData.put("error", ImportErrorEnum.no_avail_data.getValue());
					errorList.add(pageData);
					continue;
				}
				
				if (Tools.isEmpty(pageData.getString("TOTALMONEY")) ) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				if((/*Tools.isEmpty(pageData.getString("stuNumber")) && */Tools.isEmpty(pageData.getString("cardNo")) ) ){
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); 
				Matcher matcher = pattern.matcher(pageData.getString("TOTALMONEY"));
				if(!matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_total_error.getValue());
					errorList.add(pageData);
					continue;
				}
				matcher = pattern.matcher(pageData.getString("CASH"));
				if(!Tools.isEmpty(pageData.getString("CASH")) && !matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_cash_error.getValue());
					errorList.add(pageData);
					continue;
				}
				matcher = pattern.matcher(pageData.getString("CARD"));
				if(!Tools.isEmpty(pageData.getString("CARD")) && !matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_card_error.getValue());
					errorList.add(pageData);
					continue;
				}
				matcher = pattern.matcher(pageData.getString("WX"));
				if(!Tools.isEmpty(pageData.getString("WX")) && !matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_wx_error.getValue());
					errorList.add(pageData);
					continue;
				}
				matcher = pattern.matcher(pageData.getString("ZFB"));
				if(!Tools.isEmpty(pageData.getString("ZFB")) && !matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_zfb_error.getValue());
					errorList.add(pageData);
					continue;
				}
				matcher = pattern.matcher(pageData.getString("TT"));
				if(!Tools.isEmpty(pageData.getString("TT")) && !matcher.matches()){
					pageData.put("error", ImportErrorEnum.not_tt_error.getValue());
					errorList.add(pageData);
					continue;
				}
				String card_regix = "^[0-9]*$";
				if(!Tools.isEmpty(pageData.getString("BANKCARDNO"))){
					if(!pageData.getString("BANKCARDNO").matches(card_regix)){
						pageData.put("error", ImportErrorEnum.card_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				Double wx="".equals(pageData.getString("WX"))?0:Double.valueOf(pageData.getString("WX"));
				Double zfb="".equals(pageData.getString("ZFB"))?0:Double.valueOf(pageData.getString("ZFB"));
				Double cash="".equals(pageData.getString("CASH"))?0:Double.valueOf(pageData.getString("CASH"));
				Double card="".equals(pageData.getString("CARD"))?0:Double.valueOf(pageData.getString("CARD"));
				Double tt="".equals(pageData.getString("TT"))?0:Double.valueOf(pageData.getString("TT"));
			    
				if((wx+zfb+cash+card+tt) !=(Double.valueOf(pageData.getString("TOTALMONEY")))){
					pageData.put("error", ImportErrorEnum.paymoneywrong.getValue());
					errorList.add(pageData);
					continue;
				}
				
				//如果是单个项目
				/*if (Tools.notEmpty(payitem)) {
					if(!payitem.equals(pageData.getString("itemName"))){
						pageData.put("error", ImportErrorEnum.payitemnameerror.getValue());
						errorList.add(pageData);
						continue;
					}
				}*/
				pageData.put("item_pkid", pd.getString("item_pkid"));
				//1、根据项目PKID查询
				tempPageData = itemListManager.queryPayItemAndList(pageData); // itemName
				if (tempPageData == null) {
					pageData.put("error", ImportErrorEnum.not_find_item.getValue());
					errorList.add(pageData);
					continue;
				}
				if (Tools.isEmpty(tempPageData.getString("PKID"))) {//item_pkid
					pageData.put("error", ImportErrorEnum.not_find_item.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("GRADE_PKID", tempPageData.getString("GRADE_PKID"));
				pageData.put("CLASSTYPE_PKID", tempPageData.getString("CLASSTYPE_PKID"));
				pageData.put("PAY_ITEM_PKID",pd.getString("item_pkid"));
				if("12345678902".equals(tempPageData.getString("PAY_TYPE_PKID"))){//表示该项目是预交项目
					//根据身份证号和预交年份和班型 查询学生档案表,判断学生是否报名
					studentPageData = itemListManager.queryStudentYj(pageData);
				}else{
					//根据身份证号和入学年份和班型 查询学生档案表,判断学生是否报名
					studentPageData = itemListManager.queryStudent(pageData);
				}
				
				if (studentPageData == null || Tools.isEmpty(studentPageData.getString("PKID"))) {
					pageData.put("error", ImportErrorEnum.not_bm_student.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("STUDENT_PKID", studentPageData.getString("STUDENT_PKID"));//存储学生PKID
				pageData.put("STUDENT_BM_PKID", studentPageData.getString("PKID"));//存储学生附表PKID
				PageData stuIsExsitsInList = itemListManager.stuIsExsitsInList(pageData);
				if(stuIsExsitsInList==null){
					pageData.put("error", ImportErrorEnum.stunotexistinlist.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("PAY_ITEM_LIST_PKIDS",stuIsExsitsInList.getString("PKID"));
				//实缴不为0
				pageData.put("CJR", user.getUSER_ID());
				pageData.put("WX_OPENID", studentPageData.getString("WX_OPENID"));
				successList.add(pageData);
			}
			JSONArray json = new JSONArray();
			for(PageData a : successList){
	            JSONObject jo = new JSONObject();
	            jo.put("STUDENT_PKID", a.get("STUDENT_PKID"));
	            jo.put("PAY_ITEM_LIST_PKIDS", a.get("PAY_ITEM_LIST_PKIDS"));
	            jo.put("CASH", a.get("CASH"));
	            jo.put("CARD", a.get("CARD"));
	            jo.put("WX", a.get("WX"));
	            jo.put("ZFB", a.get("ZFB"));
	            jo.put("TT", a.get("TT"));
	            jo.put("TOTALMONEY", a.get("TOTALMONEY"));
	            jo.put("CARDNO", a.get("BANKCARDNO"));
	            jo.put("CJR", a.get("CJR"));
	            jo.put("stuName", a.get("stuName"));
	            jo.put("stuNumber", a.get("stuNumber"));
	            jo.put("IDCARDNO", a.get("cardNo"));
	            jo.put("WX_OPENID", a.get("WX_OPENID"));
	            jo.put("cardNo", a.get("cardNo"));
	            jo.put("BANKCARDNO", a.get("BANKCARDNO"));
	            json.add(jo);
	        }
			
			//end 业务处理
			String resultString = "";
			Map<String,String> rep=new HashMap<String,String>();
			String uuid = this.get32UUID();
			rep.put("UUID",uuid);
			rep.put("PASSMSG", Md5Util.md5(uuid));
			rep.put("list", json.toString());
			Properties prop = new Properties();
			InputStream in = null;
			in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
			prop.load(in);
			String methodurl="";
			if(prop!=null){
				methodurl=prop.getProperty("neiUrl");
			}
				HttpClient httpClient = new HttpClient(methodurl+"/qhmy-pay/dingDanPayController/importOrder", 30000, 30000);
				int resultCode = httpClient.send(rep, "UTF-8");
				if (resultCode == 200) {
					resultString = httpClient.getResult();
					JSONObject jbo = JSONObject.fromObject(resultString);
					if("false".equals(jbo.get("result"))){
						if("false".equals(jbo.get("isAllErro"))){
							if(jbo.get("errorList")!=null){
								JSONArray array=JSONArray.fromObject(jbo.get("errorList"));
								List<PageData>list = (List<PageData>)JSONArray.toCollection(array, PageData.class);
								errorList.addAll(list);
							}
							
						}else{
							for (PageData data : successList) {
								data.put("error", "服务端上传失败");
							}
							errorList=datas;
						}
						
					}
					/*if("true".equals(jbo.get("result"))){
						rep.put("MUDI", "importPaidList");
						rep.put("template_title", "缴费成功通知");
						HttpClient httpClient2 = new HttpClient(methodurl+"/colleges-pay/dingDanPayController/sendMubanMessage", 30000, 30000);
						int resultCode2 = httpClient2.send(rep, "UTF-8");
					}*/
					//JSONArray array=JSONArray.fromObject(resultString);
					//List<PageData>list = (List<PageData>)JSONArray.toCollection(array, PageData.class);
//					List<PageData> list2=(List<PageData>)JSONArray.toList(JSONArray.fromObject(resultString), PageData.class);
					//errorList.addAll(list);
				} else {
//					throw new runt("HTTP请求失败, 返回码为:" + resultCode);
					for (PageData data : successList) {
						data.put("error", "服务端上传失败");
					}
					errorList=datas;
				}
				
				
			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writeExcel(errorList,null);//pkid
			}
			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:批量减免</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午9:17:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/import-jianmian-parse")
	public ModelAndView importJianmianParse(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.ITME_LIST_JIANMIAN).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getJianMianList(file,pd.getString("fileName"));
			//end 解析excel
			
			String item_pkid = pd.getString("item_pkid");//选择的缴费项目,也可能没有选择
			
			//业务处理
			PageData studentPageData = null;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList = new ArrayList<PageData>();//记录错误的导入信息
			for (PageData pageData : datas) {
				pageData.put("PAY_ITEM_PARENT_PKID", item_pkid);
				if (Tools.isEmpty(pageData.getString("shenFenZhengHao")) 
						|| Tools.isEmpty(pageData.getString("jianMianJine"))) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				//判断减免金额是否为非负数，如果不是提示减免金额不合法
				Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); 
			    Matcher matcher = pattern.matcher(pageData.getString("jianMianJine")); 
				if(!matcher.matches()||Double.parseDouble(pageData.getString("jianMianJine"))<=0){
					pageData.put("error", ImportErrorEnum.not_jianmian_error.getValue());
					errorList.add(pageData);
					continue;
				}
				//根据身份证号和缴费项目 查询学生名单表，看是否有符合的名单
				studentPageData = itemListManager.queryStudentList(pageData);
				if (studentPageData == null || Tools.isEmpty(studentPageData.getString("PKID"))) {
					pageData.put("error", ImportErrorEnum.not_find_stuList.getValue());
					errorList.add(pageData);
					continue;
				}else if(Double.parseDouble(studentPageData.getString("COST"))<Double.parseDouble(pageData.getString("jianMianJine"))){//优惠金额大于费用
					pageData.put("error", ImportErrorEnum.not_jianmian_over.getValue());
					errorList.add(pageData);
					continue;
				}
				pageData.put("ITEM_PKID", studentPageData.getString("PAY_ITEM_PKID"));
				//判断学生的对应名单是否有过缴费记录，有过则不允许调整应收
				PageData pd_pay_list_log = itemListManager.getPayList(pageData);
				if(Double.parseDouble(pd_pay_list_log.getString("CCOUNT"))>0){//表示已有缴费记录，不允许修改
					pageData.put("error", ImportErrorEnum.exists_find_stuPayList.getValue());
					errorList.add(pageData);
					continue;
				}
				
				successList.add(pageData);
			}
			itemListManager.updatePayListDicout(successList);
			//end 业务处理
			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writeJianMianExcel(errorList);//pkid
			}
			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:减免登记</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月16日 下午3:05:32
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStuPayDerate.json")
	public ModelAndView updateStuPayDerate() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String, Object>();// json结果map
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//判断学生的对应名单是否有过缴费记录，有过则不允许调整应收
			PageData pd_pay_list_log = itemListManager.getPayList(pd);
			if(Double.parseDouble(pd_pay_list_log.getString("CCOUNT"))>0){//表示已有缴费记录，不允许修改
				jsonmap.put("result", "error");
			}else{
				itemListManager.updateStuPayDerate(pd);
				jsonmap.put("result", "success");
			}
		} catch (Exception e) {
			jsonmap.put("result", "fail");
		}

		return new ModelAndView(new MappingJackson2JsonView(), jsonmap);
	}
	
	/**
	 * 
	 * <p>描述:发布缴费导入</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月18日 下午4:53:14
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/import-list-fbjf")
	public ModelAndView importListfbjf(Page page) throws Exception{
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		File file = null;
		
		try {
			//得到上传的文件
			String basePath = UploadConst.uploadPathMap.get(UploadConst.JFFB_LIST).getAbsolutePath() + "\\"+pd.getString("mulu")+"\\";
			basePath = basePath + pd.getString("fileName");
			file = new File(basePath);
			
			//解析excel
			ItemListParseExcel itemListParseExcel = new ItemListParseExcel();
			List<PageData> datas = itemListParseExcel.getjffbList(file,pd.getString("fileName"));
			//end 解析excel
			
			//业务处理
			PageData studentPageData = null;
			List<PageData> errorList = new ArrayList<PageData>();//记录错误的导入信息
			List<PageData> successList = new ArrayList<PageData>();//记录正确的导入信息
			for (PageData pageData : datas) {
				
				boolean  flag=false;
				
				pageDatatrim(pageData);//去掉前后空格
				
				if (isAllEmpty(pageData)) {
					pageData.put("error", ImportErrorEnum.no_avail_data.getValue());
					errorList.add(pageData);
					continue;
				}
				
				if ((Tools.isEmpty(pageData.getString("stuNumber")) && Tools.isEmpty(pageData.getString("cardNo")) ) ) {
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}
				if(Tools.isEmpty(pageData.getString("cost"))){//判断费用字段是否为空
					pageData.put("error", ImportErrorEnum.core_data_empty.getValue());
					errorList.add(pageData);
					continue;
				}else{//表示费用字段不为空
					BigDecimal bd=new BigDecimal(pageData.getString("cost"));
					int a =bd.scale();
					if(a>2){
						pageData.put("error", ImportErrorEnum.money_input_error.getValue());
						errorList.add(pageData);
						continue;
					}
				}
			/*	
				if (!Tools.isEmpty(pageData.getString("discountMoney")) &&  !Tools.isEmpty(pageData.getString("cost"))) {//优惠金额不能大于发布金额
					double discountMoney = 0;
					try{
						discountMoney=Double.valueOf(pageData.getString("discountMoney"));
						
					}catch (Exception e){
						pageData.put("error", ImportErrorEnum.jffbje_fail_number.getValue());
						errorList.add(pageData);
						continue;
					}
					double fbjed=Double.valueOf(pageData.getString("cost"));
					if(discountMoney>fbjed){
						pageData.put("error", ImportErrorEnum.jffbje_fail.getValue());
						errorList.add(pageData);
						continue;
					}
				}*/
				
				pageData.put("GRADE_PKID", pd.getString("GRADE_PKID"));
				pageData.put("CLASSTYPE_PKID", pd.getString("CLASSTYPE_PKID"));
				//根据身份证号 查询学生档案表,判断学生是否存在
				studentPageData = itemListManager.queryStu(pageData);
				if (studentPageData == null || Tools.isEmpty(studentPageData.getString("PKID"))) {
					pageData.put("error", ImportErrorEnum.not_find_stu.getValue());
					errorList.add(pageData);
					continue;
				}
				
				
				
				
				
				for(PageData successpd:successList){
					//判断Excel的数据是否存在身份证号码重复的
					if(successpd.get("cardNo").equals(pageData.getString("cardNo"))){
						//把重复的数据放到errorlist 并从正确数据集合中删除
						flag=true;
						pageData.put("error", ImportErrorEnum.stucard_cffail.getValue());
						errorList.add(pageData);
						continue;
					}
				}
				
				if(flag){
					continue;
				}
				successList.add(pageData);
				map.put("totalCount", datas.size());
			}
			//end 业务处理
			map.put("successList",JSONArray.fromObject(successList).toString());
 			map.put("totalCount", datas.size());//总记录数
			map.put("successCount", datas.size() - errorList.size());//成功数
			map.put("failCount", errorList.size());//失败记录数
			
			String mulu = null;
			if (errorList.size()>0) {
				//生成下载文件,提供下载
				mulu = itemListParseExcel.writeExcelfb(errorList);//pkid
			}
			
			map.put("mulu", mulu);
			map.put("result", true);//结果
			map.put("errorinfo", "");//错误信息
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);//结果
			map.put("errorinfo", e.getMessage());//错误信息
		} finally{
			
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	public static void main(String[] args) {
//		double a=0.1;
//		double b=0.2;
//		System.out.println(a+b);
//		BigDecimal bigDecimal1 = new BigDecimal(Double.toString(a));
//        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(b));
//        System.out.println(bigDecimal1.subtract(bigDecimal2));
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher matcher = pattern.matcher("0.01"); 
        System.out.println(matcher.matches());
        
        
//        String card_regix = "^[0-9]*$";
//		System.out.println("00121245812200".matches(card_regix));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
