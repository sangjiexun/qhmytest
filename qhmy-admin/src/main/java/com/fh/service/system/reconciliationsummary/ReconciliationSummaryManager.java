package com.fh.service.system.reconciliationsummary;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 对账汇总
 * <p>标题:ReconciliationSummaryManager</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2017年8月14日 下午5:42:10
 */
public interface ReconciliationSummaryManager {
	/**
	 * 
	 * <p>描述:table数据</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月14日 下午5:43:29
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> recSummarylistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述对账汇总明细table:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月14日 下午10:37:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> detaillistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:导出对账汇总</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月23日 下午10:51:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> exportSummarylist(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:导出对账明细</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月23日 下午10:51:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> exportSummaryDetail(PageData pd) throws Exception;
}



















