package com.fh.service.itemlist.discount.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.TpayItemList;
import com.fh.entity.system.TpayItemListDisCount;
import com.fh.service.itemlist.discount.DisCountCalculate;
import com.fh.service.itemlist.discount.DisCountCalculateFactory;
import com.fh.service.itemlist.discount.DisCountCalculateInterface;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;
import com.keman.zhgd.common.util.Tools;

/**
 * <p>标题:DisCountCalculateImpl</p>
 * <p>描述:计算优惠金额</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午5:00:05
 */
@Service
public class DisCountCalculateImpl implements DisCountCalculate {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;

//	DisCountCalculateMapper
//	tpayItemList
	
	
	@Override
	public PageData disCountCalculateReturnPageData(String studentPkids,
			String itemPkids) throws Exception {
		
		return null;
	}

	@Override
	public List<TpayItemList> disCountCalculateReturnList(String studentPkids,
			String itemPkids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TpayItemList disCountCalculateReturnTpayItemList(
			String studentPkids, String itemPkids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageData disCountCalculateReturnPageDataOnly(String studentPkid,
			String itemPkid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TpayItemList disCountCalculateReturnTpayItemListOnly(
			String studentPkid, String itemPkid) throws Exception {
		return this.disCountCalculateReturnTpayItemListOnly(studentPkid, itemPkid, null);
	}
	

	/**
	 * <p>描述:获得学生信息</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午6:11:30
	 * @param studentPkid
	 * @return
	 */
	private PageData getStudentPageData(String studentPkid) {
		
		return null;
	}

	/**
	 * 
	 * <p>描述:根据学生ID和项目ID查询t_pay_item_list_discount</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午5:08:23
	 * @return
	 * @throws Exception
	 */
	private PageData getListDiscountPd(String studentPkid,String itemPkid) throws Exception{
		return null;
	}
	
	
	/**
	 * 
	 * <p>描述:根据学生ID和项目ID查询t_pay_item_list_discount</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午5:08:23
	 * @return
	 * @throws Exception
	 */
	private TpayItemListDisCount getListDiscountTpayItemList(String studentPkid,String itemPkid) throws Exception{
		PageData pageData = new PageData();
		pageData.put("student_pkid", studentPkid);
		pageData.put("pay_item_pkid", itemPkid);
		TpayItemListDisCount tpayItemListDisCount = (TpayItemListDisCount) dao.findForObject("DisCountCalculateMapper.getListDiscountTpayItemList", pageData);
		return tpayItemListDisCount;
	}
	
	
	/**
	 * <p>描述:获取学生信息  包括特殊标记</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午7:41:35
	 * @param studentPkid
	 * @return
	 * @throws Exception
	 */
	private PageData getStudentAndFlagsByPkid(String studentPkid) throws Exception{
		PageData pageData = new PageData();
		pageData.put("student_pkid", studentPkid);
		return (PageData) dao.findForObject("DisCountCalculateMapper.getStudentAndFlagsByPkid", pageData);
	}
	
	
	/**
	 * 
	 * <p>描述:查询规则表</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午8:47:47
	 * @param itemPkid
	 * @return
	 * @throws Exception
	 */
	private PageData getItemRule(String itemPkid) throws Exception{
		PageData pageData = new PageData();
		pageData.put("pay_item_pkid", itemPkid);
		return (PageData) dao.findForObject("DisCountCalculateMapper.getItemRule", pageData); 
	}
	
	/**
	 * 查询规则表规则数量
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月27日 下午11:10:29
	 * @param itemPkid
	 * @return
	 * @throws Exception
	 */
	private PageData getItemRuleCount(String itemPkid) throws Exception{
		PageData pageData = new PageData();
		pageData.put("pay_item_pkid", itemPkid);
		return (PageData) dao.findForObject("DisCountCalculateMapper.getItemRuleCount", pageData); 
	}

	@Override
	public TpayItemList disCountCalculateReturnTpayItemListOnly(
			String studentPkid, String itemPkid, String cost) throws Exception {
		TpayItemList tpayItemList = new TpayItemList();
		
		//查询学生信息
		PageData pageData = new PageData();
		pageData.put("PKID", studentPkid);
//		PageData sutdentPagedata = stuInfoService.getStuInfoByPkid(pageData);
		PageData studentPageData = this.getStudentAndFlagsByPkid(studentPkid);//获得学生pagedata
		//end 
		
		//查询list_discount表    
		TpayItemListDisCount tpayItemListDisCount = this.getListDiscountTpayItemList(studentPkid, itemPkid);//计算优惠金额
		if (tpayItemListDisCount!=null) {//list_discount表有记录
			DisCountCalculateInterface disCountCalculateInterface = DisCountCalculateFactory.getInstance(DisCountCalculateFactory.Type.按名单表优惠子表计算);
			disCountCalculateInterface.execute(tpayItemList,tpayItemListDisCount,studentPageData,null,cost);//执行计算
		}else {//需要查询规则记录
			PageData pdCount = this.getItemRuleCount(itemPkid);
			//规则数量大于1个,直接不优惠
			if(pdCount!=null && Tools.notEmpty(pdCount.getString("CCOUNT")) && Integer.valueOf(pdCount.getString("CCOUNT")) > 1){
				tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
				tpayItemList.setDISCOUNT_MONEY("0");//优惠金额
				tpayItemList.setDISCOUNT("");//优惠描述
				return tpayItemList;
			}
			PageData rulePageData = this.getItemRule(itemPkid);//查询项目的优惠规则
			if (rulePageData!=null && Tools.notEmpty(String.valueOf(rulePageData.get("PKID")))) {
				DisCountCalculateInterface disCountCalculateInterface = DisCountCalculateFactory.getInstance(DisCountCalculateFactory.Type.按项目规则表计算);
				disCountCalculateInterface.execute(tpayItemList,null,studentPageData,rulePageData,cost);//执行计算
			}
		}
		return tpayItemList;
	}
	
	
}
