package com.fh.service.itemlist.discount;

import java.util.List;

import com.fh.entity.system.TpayItemList;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:DisCountCalculate</p>
 * <p>描述:优惠金额计算</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月23日 下午4:43:50
 */
public interface DisCountCalculate {
	
	
	PageData disCountCalculateReturnPageData(String studentPkids,String itemPkids) throws Exception;
	
	List<TpayItemList> disCountCalculateReturnList(String studentPkids,String itemPkids) throws Exception;
	
	TpayItemList disCountCalculateReturnTpayItemList(String studentPkids,String itemPkids) throws Exception;
	
	
	/**
	 * <p>描述:优惠金额计算     一条缴费名单的优惠金额和方式计算分析</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午5:01:11
	 * @param studentPkid
	 * @param itemPkid
	 * @return
	 * @throws Exception
	 */
	PageData disCountCalculateReturnPageDataOnly(String studentPkid,String itemPkid) throws Exception;
	
	/**
	 * 
	 * <p>描述:优惠金额计算     一条缴费名单的优惠金额和方式计算分析</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月23日 下午5:02:55
	 * @param studentPkid
	 * @param itemPkid
	 * @return
	 * @throws Exception
	 */
	TpayItemList disCountCalculateReturnTpayItemListOnly(String studentPkid,String itemPkid) throws Exception;
	
	/**
	 * 
	 * <p>描述:优惠金额计算     一条缴费名单的优惠金额和方式计算分析</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月27日 下午5:09:37
	 * @param studentPkid
	 * @param itemPkid
	 * @param cost
	 * @return
	 * @throws Exception
	 */
	TpayItemList disCountCalculateReturnTpayItemListOnly(String studentPkid,String itemPkid,String cost) throws Exception;
}
