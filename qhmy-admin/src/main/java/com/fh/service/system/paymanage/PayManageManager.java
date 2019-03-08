package com.fh.service.system.paymanage;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 缴费管理
 * <p>标题:PayManageManager</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2017年8月3日 下午8:49:26
 */
public interface PayManageManager {
	
	
	
	
	/**
	 * 
	 * <p>描述:获取学生pkid</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月18日 下午9:25:19
	 * @param SELECTED_ZZJG
	 * @return
	 * @throws Exception
	 */
	PageData findpkbyid(PageData pd_stu) throws Exception;
	/**
	 * <p>描述:根据入学年份和班型pkid查询缴费类型</p>
	 * @author Administrator wzz
	 * @date 2018年12月7日 9:25:19
	 */
	List<PageData>  getPayTypePkid(PageData pd) throws Exception;
	/**
	 * <p>描述:根据入学年份和班型pkid查询缴费类型</p>
	 * @author Administrator wzz
	 * @date 2018年12月7日 9:25:19
	 */
	PageData getPayTypeYjPkid(PageData pd) throws Exception;
	/**
	 * <p>描述:根据pkid查询缴费类型</p>
	 * @author Administrator wzz
	 * @date 2018年12月7日 9:25:19
	 */
	PageData getPayTypeName(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:保存其它缴费项设置</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午9:49:54
	 * @param pd
	 * @throws Exception
	 */
	void saveOtherPayItem(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> otherPayItemlistPage(Page page)throws Exception;
	/**
	 * 修改其它缴费项
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月6日 下午4:02:06
	 * @param pd
	 * @throws Exception
	 */
	void updateotherpayitemset(PageData pd)throws Exception;
	
	void updateitemlist(PageData pd)throws Exception;
	/**
	 * 删除其它缴费项
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月6日 下午4:02:16
	 * @param pd
	 * @throws Exception
	 */
	void deleteotherpayitemset(PageData pd)throws Exception;

	
	
	
	/**
	 * 
	 * <p>描述:获取年级list</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月7日 下午2:21:07
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getnianji(PageData pd)throws Exception;
	
	public List<PageData> getbanxing(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取优惠范围list</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月7日 下午2:21:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getyouhuifw(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获取组织树</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月7日 下午2:21:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getZuzhis(PageData pd)throws Exception;

	
	/**
	 * 
	 * <p>描述:获取学生信息</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月6日 下午4:15:15
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuInfo(PageData pd) throws Exception;
	
	/**
	 * 获取其它缴费项目
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月6日 下午4:15:26
	 * @return
	 * @throws Exception
	 */
	List <PageData> getOtherPayItems() throws Exception;
	
	
	/**
	 * 
	 * <p>描述:保存发布</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月8日 上午11:21:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	void savefabu(PageData pd,List<PageData> list_gz) throws Exception;
	
	/**
	 * 
	 * <p>描述:保存发布 名单导入</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月8日 上午11:21:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	void savefabumd(PageData pd,List<PageData> list) throws Exception;
	/**
	 * 
	 * <p>描述:导入名单为空时只插入item表</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月25日 下午8:20:54
	 * @param pd
	 * @param list
	 * @throws Exception
	 */
	void saveitem(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:缴费审核table</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 上午9:27:38
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> jfshenhelistPage(Page page)throws Exception;
	
	/**
	 * 
	 * <p>描述:批量通过审核项目</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午5:38:23
	 * @param pkids
	 * @throws Exception
	 */
	void batchGree(String[] pkids) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:通过 详情的</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午9:59:41
	 * @param pd
	 * @throws Exception
	 */
	void batchGreeOne(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:批量不通过审核项目</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午6:48:43
	 * @param pkids
	 * @throws Exception
	 */
	void batchNo(String[] pkids) throws Exception;
	/**
	 * 
	 * <p>描述:单个不通过审核项目</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午10:03:34
	 * @param pkids
	 * @throws Exception
	 */
	void batchNoOne(PageData pd) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:得到项目信息</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午9:11:01
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getItem(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:rule 表 数据回显详情</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月10日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> rulelist(PageData pd)throws Exception;
	
	List<PageData> getsclist(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:得到导入的学生list</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月26日 下午2:35:54
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData>getlist(PageData pd)throws Exception;

/**
 * 
 * <p>描述:得到rule</p>
 * @author Administrator 胡文浩
 * @date 2017年8月22日 下午9:34:02
 * @param pd
 * @return
 * @throws Exception
 */
	
	List<PageData> getlistrule(PageData pd)throws Exception;
	
	/**
	 * 获得缴费项目列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayItemlistPage(Page page)throws Exception;
	
	/**
	 * 修改缴费项
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午4:02:06
	 * @param pd
	 * @throws Exception
	 */
	void updatePayItem(PageData pd)throws Exception;
	/**
	 * 获得缴费名单列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayItemListPage(Page page)throws Exception;
	
	/**
	 * 修改缴费名单
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午4:02:06
	 * @param pd
	 * @throws Exception
	 */
	void updatePayItemList(PageData pd)throws Exception;
	
	/**
	 * 获得缴费名单
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午4:02:06
	 * @param pd
	 * @throws Exception
	 */
	PageData getPayItemListInfo(PageData pd)throws Exception;
	
	/**
	 * 删除缴费项
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午4:02:16
	 * @param pd
	 * @throws Exception
	 */
	void deletePayItem(PageData pd)throws Exception;
	/**
	 * 通过缴费项PKID查询缴费清单
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月9日 下午4:02:16
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> getPayItemListByPayItemPkid(PageData pd) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:取所有缴费项目</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月7日 下午9:23:40
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List <PageData> getPayItems(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:获取学生某项目的实际缴纳金额</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月7日 下午9:25:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuOrderMoney(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:某个学生某个项目是否有优惠，有的话取出优惠方式和结果</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月8日 上午10:28:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStuItemDiscountMoney(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询某学生可以转出的缴费项目</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月9日 上午9:43:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List <PageData> getStuCanOutPayitems(PageData pd,List<String> list) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询某学生可以转入的缴费项目</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月9日 上午10:07:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List <PageData> getStuCanInPayitems(PageData pd) throws Exception;
	
	/**
	 * 通过学生Pkid获得缴费名单列表(分页)
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayItemlistPageByStudentPkidAndStatus(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 胡文浩
	 * @date 2019年1月16日 上午10:01:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getallrulebypkid(PageData pd)throws Exception;
	/**
	 * 通过学生Pkid获得缴费名单列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月8日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayItemlistByStudentPkidAndStatus(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:list列表数据展示</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月10日 下午7:08:30
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> datalistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:通过人员名单表查各种金额</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月13日 上午1:13:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getpayItemMoneyByList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:通过学生id和标记查询优惠</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月13日 上午1:13:45
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getPayItemMoneyByRule(PageData pd) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:获得线下缴费记录列表</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetailList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费记录金额和支付方式列表</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetailMoneyList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费名单</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageData getPayItemList(PageData pd)throws Exception;
	
	PageData getStuYJInfo(PageData pd)throws Exception;
	PageData getorderInfo(String  pkid)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费记录详情</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetail(PageData pd)throws Exception;
	
	/**
	 * 修改订单明细
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月16日 下午4:02:06
	 * @param pd
	 * @throws Exception
	 */
	void updatePayOrderDetail(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费记录</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月12日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetailGroup(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:获得缴费记录</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月16日 下午10:13:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayOrderDetailListForPrint(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:取所有项目</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月16日 下午10:13:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getAllPayItems(PageData pd)throws Exception;

	/**
	 * 
	 * <p>描述:更新缴费项目的，已完成，已过期</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月19日 上午9:30:10
	 * @param pd
	 */
	void updatePayItemStatus(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:获得未缴学生名单</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 下午9:51:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getStudentPaylistPage(Page page)throws Exception;
	
	/**
	 * 
	 * <p>描述:保存缴费名单</p>
	 * @author Administrator 陈超超
	 * @date 2018年12月7日 9:49:54
	 * @param pd
	 * @throws Exception
	 */
	void savePayItemList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:删除缴费项</p>
	 * @author Administrator wzz
	 * @date 2018年12月7日 9:49:54
	 */
	void delitemlist(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:激活缴费名单</p>
	 * @author wzz
	 * @date 2018年12月10日 下午5:09:51
	 */
	void updateItemList(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:导出缴费名单</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月18日 下午9:49:54
	 * @param pd
	 * @throws Exception
	 */
	List<PageData> exportStudentPayList(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:验证项目名称</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月18日 下午10:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> yanzmc(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:批量  更改缴费名单表状态  已关闭</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月22日 上午8:51:23
	 * @param pageData
	 */
	void updatePayItemListStatusClose(PageData pageData) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:批量  更改缴费名单表状态 已完成 </p>
	 * @author Administrator 鲁震
	 * @date 2017年8月22日 下午7:12:05
	 * @param pageData
	 * @throws Exception
	 */
	void updatePayItemListStatusOk(PageData pageData) throws Exception;
	
	/**
	 * 
	 * <p>描述:缴费项目记录条数</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月21日 下午17:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getPayOrderDetailCount(PageData pd) throws Exception;
	
	
	List<PageData> exportStuPayList (PageData pd) throws Exception;
	
	/**
	 * 删除缴费名单
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月23日 下午4:02:16
	 * @param pd
	 * @throws Exception
	 */
	void deletePayItemListObject(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * <p>描述:删除导入名单</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月26日 下午1:17:03
	 * @param pd
	 * @throws Exception
	 */
	void deletedr(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:查询缴费名单</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月28日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayItemListForPrint(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询学生名单</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getStudentQuerylistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询学生信息</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStudentInfo(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询学生信息</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月29日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getStudentInfo2(PageData pd) throws Exception;
	
	PageData getitemlistmax(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询学生</p>
	 * @author Administrator 陈超超
	 * @date 2017年8月31日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getStudentQuerylist2(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:关闭项目</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午02:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	void updatePayItemListStatusIsClose(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:添加缴费项目名单</p>
	 * @author Administrator 陈超超
	 * @date 2017年9月8日 下午05:39:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void insertPayItemList(PageData pd) throws Exception;
	
	PageData getDetailByOtherPayItem(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取缴费类型列表</p>
	 * @author wn 王宁
	 * @date 2018年11月28日 下午5:20:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getPayStyleList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取班型列表</p>
	 * @author wn 王宁
	 * @date 2018年11月29日 上午10:43:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getBanXing(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取合作学校下拉列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 上午10:15:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> gethzxxList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取合作学校下拉列表包含回显的</p>
	 * @author wn 王宁
	 * @date 2019年1月23日 上午11:30:20
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> gethzxxListAdd(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取班级列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 下午2:34:35
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getClassList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取美院列表</p>
	 * @author wn 王宁
	 * @date 2018年12月3日 下午2:49:18
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getMeiYuanList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:保存按规则发布的缴费</p>
	 * @author wn 王宁
	 * @date 2018年12月4日 上午9:45:24
	 * @param pd
	 * @param list_gz
	 * @param list_gz_fb
	 * @throws Exception
	 */
	void savefabu(PageData pd, List<PageData> list_gz, List<PageData> list_gz_fb) throws Exception;
	/**
	 * 
	 * <p>描述:验证项目是否已经发布</p>
	 * @author wn 王宁
	 * @date 2018年12月4日 下午2:09:03
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData indefyItem(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据规则表PKID获取附表信息</p>
	 * @author wn 王宁
	 * @date 2018年12月10日 上午9:41:54
	 * @param a
	 * @throws Exception
	 */
	PageData getRuleFB(PageData a) throws Exception;
	/**
	 * 
	 * <p>描述:根据规则表PKID缴费详情</p>
	 * @author wzz
	 * @date 2018年12月10日 上午9:41:54
	 * @param a
	 * @throws Exception
	 */
	PageData getPayItemListByPkid(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取合作学校名称</p>
	 * @author wn 王宁
	 * @date 2018年12月10日 下午2:46:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getHzxyName(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月10日 上午9:39:56
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	List<PageData>  getlistbyPkid(String  pkid) throws Exception;
	void deletePayItemListbypkid(String  pkid)throws Exception;
	
	void updatePayItemListTZ(PageData pd)throws Exception;
	void updatePayItemListJM(PageData pd)throws Exception;
	
	List<PageData> getManeyChangelistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:获取要添加的学生</p>
	 * @author wn 王宁
	 * @date 2018年12月11日 下午3:26:17
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getaddStutablelistPage(Page page) throws Exception;
	/**
	 * 
	 * <p>描述:保存新增名单</p>
	 * @author wn 王宁
	 * @date 2018年12月11日 下午4:39:05
	 * @param pd
	 * @throws Exception
	 */
	void saveAddItemList(PageData pd) throws Exception;
	List<PageData> getManeyLOGlistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:根据入学年份获取上一年的入学年份</p>
	 * @author wn 王宁
	 * @date 2018年12月19日 下午3:31:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData getLastGrade(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:预交是否绑定</p>
	 * @author wzz
	 * @date 2018年12月19日 下午3:31:19
	 */
	PageData getYjIsBd(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:</p>
	 * @author wn 王宁
	 * @date 2018年12月18日 下午3:49:19
	 * @param bgCost  优惠前金额
	 * @param bgZJJinE 优惠金额
	 * @param pdRule  查询出来的规则
	 * @param pd 最后要插入的名单PD
	 * @throws Exception
	 */
	 double getItemCost(PageData pd) throws Exception;
	 
	 PageData getSendMsgCounts (PageData pd) throws Exception;
	 /**
	  * 
	  * <p>描述:更新学生为退学</p>
	  * @author wn 王宁
	  * @date 2019年2月13日 下午3:11:51
	  * @param pd
	  * @throws Exception
	  */
	void updateStuTuiXue(PageData pd)throws Exception;
	
}



















