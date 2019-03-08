package com.fh.service.system.report;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 报表统计接口
 * <p>标题:ReportManager</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 陈超超
 * @date 2017年10月31日 上午11:15:05
 */
public interface ReportManager {
	
	/**
	 * 获取招生人列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年10月31日 上午11:40:25
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getZSRSumTablelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取院校下拉菜单</p>
	 * @author Administrator 王宁
	 * @date 2017年10月30日 下午4:46:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getZuzhis(PageData pd)throws Exception;
	
	/**
	 * 招生人明细列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月1日 上午9:17:05
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getZSRSumDetailTablelistPage(Page page)throws Exception;
	
	/**
	 * 招生人汇总列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月1日 上午9:17:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> zsrSumTablelist(PageData pd)throws Exception;
	
	/**
	 * 招生人明细列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月1日 上午9:39:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> zsrSumDetailTablelist(PageData pd) throws Exception;
	
	/**
	 * 退费汇总列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月1日 下午4:25:53
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRefundSumlistTable(PageData pd)throws Exception;
	
	/**
	 * 获得退费明细列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月6日 下午4:37:29
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRefundDetailTablelistPage(Page page)throws Exception;
	
	/**
	 * 获得层次列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月6日 下午4:54:20
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getCENGCIList(PageData pd)throws Exception;
	
	/**
	 * 获得批次列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月6日 下午4:54:27
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPICIList(PageData pd)throws Exception;
	
	/**
	 * 获得所有退费明细
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月6日 下午5:13:27
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRefundDetailTableList(PageData pd) throws Exception;
	
	/**
	 * 交易汇总
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月7日 上午9:40:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getTradeSumlistPage(Page page) throws Exception;
	
	/**
	 * 交易汇总
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年11月7日 上午9:40:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getTradeSumList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取缴费类型列表</p>
	 * @author Administrator 王宁
	 * @date 2018年1月15日 下午7:15:31
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayStyleList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取学年列表</p>
	 * @author Administrator 王宁
	 * @date 2018年1月15日 下午7:15:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getSchoolYeaList(PageData pd)throws Exception;
	PageData getPayStyleRoom(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取个人欠费汇总数据</p>
	 * @author Administrator 王宁
	 * @date 2018年4月20日 下午4:26:44
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getstuArrearageSumlistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取导出数据</p>
	 * @author Administrator 王宁
	 * @date 2018年4月20日 下午6:44:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> stuArrearageSumExcel(PageData pd)throws Exception;
	
	List<PageData> getBanJiArrearageSumlistPage(Page page)throws Exception;
	
	PageData getSumRowResult(PageData pd)throws Exception;
	
	List<PageData> getstuArrearageDetaillistPage(Page page)throws Exception;
	List<PageData> getstuArrearageDetaillist(Page page)throws Exception;
	
	List<PageData> getPayItemList(PageData pd)throws Exception;
	List<PageData> stuArrearageDetailExcel(Page page)throws Exception;
	List<PageData> getPayStyleListAll(PageData pd)throws Exception;
	List<PageData> getSchoolYeaListAll(PageData pd)throws Exception;
	List<PageData> getstudepartment(PageData pd)throws Exception;
	List<PageData> getlist(PageData pd)throws Exception;
	List<PageData> getalllou(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取学生缴费明细数据</p>
	 * @author ning 王宁
	 * @date 2018年6月22日 上午10:00:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getstuPayDetaillistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:学生收费明细导出</p>
	 * @author ning 王宁
	 * @date 2018年6月22日 下午2:07:52
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> stuPayDetailExcel(PageData pd)throws Exception;
	
	/**
	 * 获得个人欠费汇总的汇总数据
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年6月29日 上午10:23:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getstuArrearageSum(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述: 退费明细合计</p>
	 * @author Administrator 康程亮
	 * @date 2018年7月2日 下午5:06:42
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getRefundDetailSum(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取个人欠费明细合计数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月2日 上午11:28:47
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getstuArrearageDetail(PageData pd)throws Exception;
	
	/**
	 * 所有班级欠费汇总数据
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月6日 下午7:01:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanJiArrearageSumlist(PageData pd)throws Exception;
	
	
	public List<PageData> getinvoicelistPage(Page page) throws Exception;
	
	PageData getinvoice(PageData pd)throws Exception;

	
	
	
	/**
	 * 
	 * <p>描述:获得物品领取汇总</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月20日 下午3:56:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getgoodsSumDetaillistPage(Page page)throws Exception;
	
	List<PageData> printgetgoodsSumDetaillist(PageData pd)throws Exception;
	
	
	
	/**
	 * 
	 * <p>描述:获取学年</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月20日 下午3:58:22
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getXueNian(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * <p>描述:获取缴费类型</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月20日 下午3:58:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getJiaoFeiLeiXing(PageData pd)throws Exception;
	
	  /**
     * 	
     * <p>描述:获取入学年份</p>
     * @author Administrator 任笑达
     * @date 2018年8月27日 下午4:18:24
     * @param pd
     * @return
     * @throws Exception
     */
	List<PageData> getRuXueNianFen(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:生源统计汇总</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月27日 下午4:18:47
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getShengYuanSum(PageData pd)throws Exception;
	

	/**
	 * 
	 * <p>描述:获取领卡明细</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月29日 下午3:34:59
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getCardDetaillistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取专业下拉</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月29日 下午3:36:04
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getZhuanYeList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取领卡明细_打印</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月29日 下午6:07:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getCardDetaillist(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取预交费明细表数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日 下午4:38:30
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getAdvancePayTable(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取预交费明细导出数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年12月17日 下午4:38:30
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getAdvancePayList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述: 预交明细合计</p>
	 * @author Administrator wzz
	 * @date 2018年1月22日 下午5:06:42
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getAdvancePaySum(PageData pd)throws Exception;
	

}
