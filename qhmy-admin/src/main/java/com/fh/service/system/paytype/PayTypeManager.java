package com.fh.service.system.paytype;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface PayTypeManager {

	/**
	 * 
	 * <p>描述: 列表</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:33:14
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> payTypelistPage(Page page) throws Exception;

	/**
	 * 
	 * <p>描述: 新增</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:33:29
	 * @param pd
	 * @throws Exception
	 */
	void addPayType(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述: 修改</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:33:38
	 * @param pd
	 * @throws Exception
	 */
	void editPayType(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:删除</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:33:45
	 * @param pd
	 * @throws Exception
	 */
	void delPayType(PageData pd) throws Exception;
	
	PageData getPayTypeByPkid(PageData pd) throws Exception;

	List<PageData> getPayItems(PageData pd) throws Exception;
	
	List<PageData> getPayItemsByName(PageData pd) throws Exception;
	
	void updatePaixu(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:修改登记领取字段</p>
	 * @author ning 王宁
	 * @date 2018年7月13日 上午11:43:18
	 * @param pd
	 * @throws Exception
	 */
	void updateDJLQ(PageData pd)throws Exception;
}



















