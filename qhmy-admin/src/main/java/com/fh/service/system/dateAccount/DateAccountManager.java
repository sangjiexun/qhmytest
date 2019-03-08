package com.fh.service.system.dateAccount;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:DateAccountManager</p>
 * <p>描述:日结账业务方法接口</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn 王宁
 * @date 2017年8月13日 下午3:32:16
 */
public interface DateAccountManager {
	/**
	 * 
	 * <p>描述:查询汇总信息</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:43:02
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getAmountMsg(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取日结账表单数据</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午3:57:49
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> dateAccountlistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取最后一次核账日期</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午7:03:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getlastCheckDate(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:核账明细数据</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午7:27:55
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> dateAccountlist(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:保存核账数据</p>
	 * @author wn 王宁
	 * @date 2017年8月13日 下午7:38:55
	 * @param amountMsg
	 * @param listDetail
	 */
	void saveCheck(PageData amountMsg, List<PageData> listDetail)throws Exception;
	/**
	 * 
	 * <p>描述:获取Excel导出数据</p>
	 * @author Administrator 王宁
	 * @date 2017年9月15日 下午5:15:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> dateAccountlistExcel(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:作废所选订单记录</p>
	 * @author wn 王宁
	 * @date 2018年12月12日 下午1:53:45
	 * @param pd
	 * @throws Exception
	 */
	void updateClearOrder(PageData pd)throws Exception;
	
}
