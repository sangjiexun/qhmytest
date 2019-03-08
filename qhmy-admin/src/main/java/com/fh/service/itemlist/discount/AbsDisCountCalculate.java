package com.fh.service.itemlist.discount;

import com.keman.zhgd.common.util.Tools;


/**
 * 
 * <p>标题:AbsDisCountCalculate</p>
 * <p>描述:计算优惠金额的抽象类</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午8:13:43
 */
public abstract class AbsDisCountCalculate {
	
	/**
	 * <p>描述:判断学生是否属于在规则访问内</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午9:09:24
	 * @param DISCOUNT_SCOPE   规则表中的优惠范围
	 * @param EXCEPTIONAL      学生的特殊标记
	 * @return
	 */
	public boolean isDiscountScope(String DISCOUNT_SCOPE,String EXCEPTIONAL){
		boolean rst = false;
		if (Tools.isEmpty(EXCEPTIONAL) || Tools.isEmpty(DISCOUNT_SCOPE)) {
			return rst;
		}
		String [] studentFlagArray = EXCEPTIONAL.split(",");
		String [] discountScopeArray = DISCOUNT_SCOPE.split(",");
		
		for (int i = 0; i < discountScopeArray.length; i++) {
			for (int j = 0; j < studentFlagArray.length; j++) {
				if (discountScopeArray[i].trim().equals(studentFlagArray[j].trim())) {
					rst = true;
					break;
				}
			}
			
			if (rst) {
				break;
			}
		}
		return rst;
	}
	
}
