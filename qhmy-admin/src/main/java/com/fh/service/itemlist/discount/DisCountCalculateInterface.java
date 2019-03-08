package com.fh.service.itemlist.discount;

import com.fh.entity.system.TpayItemList;
import com.fh.entity.system.TpayItemListDisCount;
import com.fh.util.PageData;


/**
 * 
 * <p>标题:DisCountCalculateInterface</p>
 * <p>描述:计算优惠金额</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午8:16:29
 */
public interface DisCountCalculateInterface {

	/**
	 * 
	 * <p>描述:执行计算</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午8:16:44
	 * @param tpayItemListDisCount
	 * @param studentPageData
	 */
	void execute(TpayItemList tpayItemList,TpayItemListDisCount tpayItemListDisCount,PageData studentPageData,PageData rulePageData);
	
	void execute(TpayItemList tpayItemList,TpayItemListDisCount tpayItemListDisCount,PageData studentPageData,PageData rulePageData,String cost);
	
}
