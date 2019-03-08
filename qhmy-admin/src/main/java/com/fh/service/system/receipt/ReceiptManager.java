package com.fh.service.system.receipt;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 收据相关
 * <p>标题:ReceiptManager</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 陈超超
 * @date 2017年8月15日 下午8:49:26
 */
public interface ReceiptManager {
	
	/**
	 * 
	 * <p>描述:获取所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllPayItemList(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * <p>描述:获取所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllPayItemList2(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * <p>描述:获取所有缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllPayItemList3(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取所有其他缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllPayOtherItemList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取所有缴费项目分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPayItemGroupList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:分组获取所有缴费项目分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPayItemGroupsList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:修改缴费项目分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updatePayItemGroup(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:添加分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void insertPayItemGroup(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:删除分组</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteGroup(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费条数</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getPayOrderDetailCount(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:删除缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月15日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteGroupByPayItemPkid(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:查询订单记录</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetaillistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费记录</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月30日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPayOrderDetailList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:修改缴费记录</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月30日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updatePayOrderDetails(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得学生缴费过的项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午5:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getPayItemListGroupByPayItemPkid(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:通过父缴费项目PKID获得旗下所有子缴费项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午5:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllChildPayItemsByParentPkid(PageData pd)throws Exception;
	
	
	/**
	 * 获得收据单号
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月7日 下午5:26:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getSeqReceiptNO(PageData pd)throws Exception;

	/**
	 * 
	 * <p>描述:获取所有缴费项目</p>
	 * @author ning 王宁
	 * @date 2018年7月6日 下午8:16:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllPayItemListAll(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取发票号</p>
	 * @author Administrator WZZ
	 * @date 2018年8月21日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getInvoiceInfo(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:修改发票状态为已使用</p>
	 * @author Administrator wzz
	 * @date 2018年8月21日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateInvoiceStatus(PageData pd)throws Exception;
	
	/**
	 * 获得用户
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月7日 上午8:56:22
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getUserList(PageData pd)throws Exception;
	
	/**
	 * 查询序列数量
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月10日 上午11:38:22
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getSeqCount(PageData pd)throws Exception;
	
	/**
	 * 创建序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月10日 上午11:43:17
	 * @throws Exception
	 */
	public void createSeq(PageData pd) throws Exception;
	

}
