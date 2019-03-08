package com.fh.service.itemlist.discount;

import java.math.BigDecimal;

import com.fh.entity.system.TpayItemList;
import com.fh.entity.system.TpayItemListDisCount;
import com.fh.util.ArithUtil;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect.DiscountModeEnum;
import com.keman.zhgd.common.util.Tools;

/**
 * 
 * <p>标题:DisCountCalculateByRule</p>
 * <p>描述:按rule表计算优惠金额</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午8:15:15
 */
public class DisCountCalculateByRule extends AbsDisCountCalculate implements DisCountCalculateInterface {

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
		
		//判断是否优惠
		if (DiscountModeEnum.不优惠.getValue().equals(String.valueOf(rulePageData.get("DISCOUNT_MODE")))) {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
			tpayItemList.setDISCOUNT_MONEY("0");//优惠金额
			tpayItemList.setDISCOUNT("");//优惠描述
		}else if (DiscountModeEnum.打折.getValue().equals(String.valueOf(rulePageData.get("DISCOUNT_MODE")))) {
			//是否在优惠范围
			boolean rst = this.isDiscountScope(String.valueOf(rulePageData.get("DISCOUNT_SCOPE")), String.valueOf(studentPageData.get("EXCEPTIONAL")));
			if (rst) {
				tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.打折.getValue());
				
				if (Tools.notEmpty(cost)) {//传过来的总费用，那么需要计算折后金额
					double discount_money = ArithUtil.mul(cost, String.valueOf(rulePageData.get("DISCOUNT")));
					discount_money = ArithUtil.div(String.valueOf(discount_money),"10",2);
					discount_money = new BigDecimal(cost).subtract(new BigDecimal(discount_money)).doubleValue();
					tpayItemList.setDISCOUNT_MONEY(String.valueOf(discount_money));//优惠金额
				}else {
					tpayItemList.setDISCOUNT_MONEY(String.valueOf(rulePageData.get("DISCOUNT_MONEY")));//优惠金额
				}
				
				tpayItemList.setDISCOUNT("打折-"+String.valueOf(rulePageData.get("DISCOUNT")) );//优惠描述
			}
		}else if (DiscountModeEnum.直减.getValue().equals(String.valueOf(rulePageData.get("DISCOUNT_MODE")))) {
			//是否在优惠范围
			boolean rst = this.isDiscountScope(String.valueOf(rulePageData.get("DISCOUNT_SCOPE")), String.valueOf(studentPageData.get("EXCEPTIONAL")));
			if (rst) {
				tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.直减.getValue());
				tpayItemList.setDISCOUNT_MONEY(String.valueOf(rulePageData.get("DISCOUNT_MONEY")));//优惠金额
				tpayItemList.setDISCOUNT("直减"+String.valueOf(rulePageData.get("DISCOUNT_MONEY")));//优惠描述
			}
		}else {
			tpayItemList.setDISCOUNT_MODE(DiscountModeEnum.不优惠.getValue());//是否优惠
			tpayItemList.setDISCOUNT_MONEY("0");//优惠金额
			tpayItemList.setDISCOUNT("");//优惠描述
		}	
		
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(ArithUtil.div("555.56", "100",2));
		
		
	}
	
}
