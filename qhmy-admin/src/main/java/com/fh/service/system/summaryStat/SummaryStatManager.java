package com.fh.service.system.summaryStat;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface SummaryStatManager {
	/**
	 * 
	 * <p>
	 * 描述:获取宿舍统计表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月21日 上午9:36:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getList(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取退费明细统计表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午8:52:04
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRefundlist(Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:导出退费明细统计
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午10:26:57
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRefund(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取缴费项目
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午9:08:02
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getpayitem(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取入学年份
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午9:13:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getrxnf(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取班型
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午9:13:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getbanji_type(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取层次
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月24日 上午9:14:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getcengci(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取宿舍楼
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月21日 上午10:04:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getLou(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取宿舍树形图
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月26日 上午8:56:44
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getDormTree(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述: 退费明细合计</p>
	 * @author Administrator wzz
	 * @date 2018年1月22日 下午5:06:42
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getRefundDetailSum(PageData pd)throws Exception;

}
