package com.fh.service.system.reportStat;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;


/**
 * 
 * <p>标题:ReportStatManager</p>
 * <p>描述:报表统计接口</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn 王宁
 * @date 2017年8月14日 下午4:38:04
 */
public interface ReportStatManager {
	/**
	 * 
	 * <p>描述:缴费汇总表格数据</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午10:30:58
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> paySumTablelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:缴费明细表格数据</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 下午10:31:22
	 * @param page
	 * @return
	 */
	List<PageData> paySumDetailTablelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取缴费名单list</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 上午11:43:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getListItem(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:院校树列表</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 下午2:59:00
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<VO> departmentList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取年级</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 下午9:11:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getGrades(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取费用合计信息</p>
	 * @author wn 王宁
	 * @date 2017年8月15日 下午9:11:53
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayMsg(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据条件获取导出数据</p>
	 * @author Administrator 王宁
	 * @date 2017年8月17日 下午5:34:47
	 * @param pd
	 * @return
	 */
	List<PageData> paySumDetailTablelist(PageData pd)throws Exception;
	
	List<PageData> payDetailTablelistPage(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:介绍人</p>
	 * @author Administrator 王宁
	 * @date 2017年10月30日 下午4:40:41
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> jieshaorenSumTablelistPage(Page page)throws Exception;
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
	 * 
	 * <p>描述:介绍人明细表格数据</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 上午10:23:02
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> jieshaorenDetailTablelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:介绍人明细</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 下午2:34:42
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> jieshaorenDetailTablelist(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:介绍人汇总导出</p>
	 * @author Administrator 王宁
	 * @date 2017年10月31日 下午3:28:05
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> jieshaorenSumTablelist(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:学院收费汇总</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 上午10:48:12
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getFeeSumlistTable(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取有缴费记录的专业</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 上午11:36:29
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getlistDep(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取实收明细表格数据</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午6:29:51
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getFeeDetailTablelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取实收明细导出数据</p>
	 * @author Administrator 王宁
	 * @date 2017年11月6日 下午7:51:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getFeeDetailTablelist(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取缴费比例的缴费项目欠费或者是已完成</p>
	 * @author Administrator 王宁
	 * @date 2017年12月27日 下午3:22:18
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getListItemBL(PageData pd)throws Exception;
	
	List<PageData> getPd_msg(PageData pd)throws Exception;
	List<PageData> getStu_msg(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:更新实收明细汇总信息</p>
	 * @author ning 王宁
	 * @date 2018年6月29日 上午11:10:04
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getAmountMsgFeeDetial(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:学院汇总统计</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月4日 上午10:48:04
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getCollegeSummaryTableListPage(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取院校下拉列表</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月6日 下午12:13:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getYuanXiao(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取宿舍下拉列表</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 上午9:20:15
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getSuShersList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取非财经人数统计表</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 上午9:35:40
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getdepartList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取财经人数统计表</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 上午9:35:40
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getdepartListCj(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取宿舍专业关系表</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 下午7:07:08
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> sushelist(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取院校列表</p>
	 * @author Administrator 任笑达
	 * @date 2018年8月6日 下午7:07:08
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getYuanXiaoLists(PageData pd)throws Exception;
	
	/**
	 * 获得宿舍计划汇总表格
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月14日 下午5:01:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getStudentDormPlanSumTable(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:结账汇总统计列表</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月10日 上午8:47:59
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getTheInvoicingList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:收费员下拉列表数据</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月10日 上午8:48:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getOperatorList(PageData pd)throws Exception;
	
	/**
	 * 获得入学年份
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月17日 下午2:12:29
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRuxuenianfenList(PageData pd)throws Exception;
	
	/**
	 * 获得班型
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月17日 下午2:12:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanxingList(PageData pd)throws Exception;
	
	/**
	 * 获得缴费类型
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月17日 下午2:12:48
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getJiaofeileixingList(PageData pd)throws Exception;
}
