package com.fh.service.itemlist.discount;

import com.fh.entity.system.TpayItemList;
import com.fh.entity.system.TpayItemListDisCount;
import com.fh.util.ArithUtil;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>标题:DisCountCalculateByDis</p>
 * <p>描述:按名单优惠表记录计算优惠金额</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午8:14:40
 */
public class DisCountCalculateByDis extends AbsDisCountCalculate implements DisCountCalculateInterface  {

	@Override
	public void execute(TpayItemList tpayItemList,
			TpayItemListDisCount tpayItemListDisCount, PageData studentPageData,PageData rulePageData) {
		// TODO Auto-generated method stub
		this.execute(tpayItemList, tpayItemListDisCount, studentPageData, rulePageData, null);
	}

	@Override
	public void execute(TpayItemList tpayItemList,
			TpayItemListDisCount tpayItemListDisCount,
			PageData studentPageData, PageData rulePageData, String cost) {
		// TODO Auto-generated method stub
		//判断是否优惠
		if (DiscountModeEnum.不优惠.getValue().equals(tpayItemListDisCount.getDISCOUNT_MODE())) {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
			tpayItemList.setDISCOUNT_MONEY("0");//优惠金额
			tpayItemList.setDISCOUNT("");//优惠描述
		}else if (DiscountModeEnum.打折.getValue().equals(tpayItemListDisCount.getDISCOUNT_MODE())) {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.打折.getValue());
			
			if (Tools.notEmpty(cost)) {//传过来的总费用，那么需要计算折后金额
				double discount_money = ArithUtil.mul(cost, tpayItemListDisCount.getDISCOUNT());
				tpayItemList.setDISCOUNT_MONEY(String.valueOf(discount_money));//优惠金额
			}else {
				tpayItemList.setDISCOUNT_MONEY(tpayItemListDisCount.getDISCOUNT_MONEY());//优惠金额
			}
			
			tpayItemList.setDISCOUNT("打折-"+tpayItemListDisCount.getDISCOUNT());//优惠描述
		}else if (DiscountModeEnum.直减.getValue().equals(tpayItemListDisCount.getDISCOUNT_MODE())) {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.直减.getValue());
			tpayItemList.setDISCOUNT_MONEY(tpayItemListDisCount.getDISCOUNT_MONEY());//优惠金额
			tpayItemList.setDISCOUNT("直减"+tpayItemListDisCount.getDISCOUNT_MONEY());//优惠描述
		}else {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
			tpayItemList.setDISCOUNT_MONEY("0");//优惠金额
			tpayItemList.setDISCOUNT("");//优惠描述
		}
		
	}


	
	
}
