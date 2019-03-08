package com.fh.service.importitem.itemlist;

import java.util.List;

import com.fh.util.PageData;

/**
 * 
 * <p>标题:ItemListManager</p>
 * <p>描述:导入缴费名单</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月17日 上午12:55:53
 */
public interface ItemListManager {

	/**
	 * 
	 * <p>描述:插入缴费名单</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午2:32:54
	 * @param pageData
	 * @throws Exception
	 */
	void saveItemList(PageData pageData) throws Exception;
	
	/**
	 * 
	 * <p>描述:根据缴费项目名称查询缴费项目</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午2:34:00
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData queryPayItemAndList(PageData pageData) throws Exception; 
	
	/**
	 * 
	 * <p>描述:根据身份证号或学号 查询 学生 档案</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午3:51:22
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData queryStudent(PageData pageData) throws Exception;
	
	
	
	/**
	 * 
	 * <p>描述:根据身份证号和卡号去匹配学员</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月24日 上午12:21:26
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData queryStu(PageData pageData) throws Exception;
	
	PageData daoruqueryStu(PageData pageData) throws Exception;

	/**
	 * 
	 * <p>描述:更新名单</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月17日 上午4:08:28
	 * @param pageData
	 * @throws Exception
	 */
	void updateItemList(PageData pageData) throws Exception;
	
	PageData stuIsExsitsInList(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:根据项目PKID和身份证号查询名单表</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月24日 上午12:04:10
	 * @param pageData
	 * @return
	 */
	PageData queryPayList(PageData pageData) throws Exception;
	/**
	 * 
	 * <p>描述:根据项目PKID查询审核状态</p>
	 * @author Administrator 王宁
	 * @date 2017年8月25日 下午3:24:21
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData getItemMsg(PageData pageData)throws Exception;

	/**
	 * 
	 * <p>描述:批量保存</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月27日 下午8:17:53
	 * @param batchSaveDatas
	 * @throws Exception
	 */
	void batchSaveDatas(List<PageData> batchSaveDatas) throws Exception;
	/**
	 * 
	 * <p>描述:查询学生名单表有没有对应名单</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午10:51:36
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData queryStudentList(PageData pageData)throws Exception;
	/**
	 * 
	 * <p>描述:获取该学生是否有缴费记录</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午11:13:25
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData getPayList(PageData pageData)throws Exception;
	/**
	 * 
	 * <p>描述:</p>
	 * @author ning 王宁
	 * @date 2018年7月12日 上午11:37:31
	 * @param successList
	 * @throws Exception
	 */
	void updatePayListDicout(List<PageData> successList)throws Exception;
	/**
	 * 
	 * <p>描述:批量贷款</p>
	 * @author Administrator wzz
	 * @date 2018年7月26日 下午2:29:34
	 * @param pd
	 * @throws Exception
	 */
	void updateStuPayItemLoan(List<PageData> list)throws Exception;
	/**
	 * 
	 * <p>描述:贷款/缓交登记</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月16日 上午11:48:14
	 * @param pd
	 * @throws Exception
	 */
	void updateStuPayLoan(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:减免登记</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月16日 下午2:12:33
	 * @param pd
	 * @throws Exception
	 */
	void updateStuPayDerate(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:查询预交年份和班型该学生有没有报名</p>
	 * @author wn 王宁
	 * @date 2019年1月3日 下午4:47:06
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	PageData queryStudentYj(PageData pageData)throws Exception;
}
