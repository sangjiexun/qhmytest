package com.fh.service.system.dormitoryInfo;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface DormitoryInfoManager {
	/**
	 * 
	 * <p>描述: table</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月20日 上午10:42:38
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getDormlistPage (Page page) throws Exception;
	
	List<PageData> getDuiDiaoDormlistPage (Page page) throws Exception;
	
	List<PageData> getDormhistorylistPage (Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:获取用户宿舍院校专业权限信息</p>
	 * @author Administrator 康程亮
	 * @date 2018年3月20日 上午10:42:46
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	PageData getUserDormDepartments(String user_id) throws Exception;
	
	List<PageData> getDormDepts (String str) throws Exception;
	
	List<PageData> getUserAllDeptsBeds (List<PageData> list) throws Exception;
	
	List<PageData> getDormList (PageData pd) throws Exception;
	
	List<PageData> getDormTypeList (PageData pd) throws Exception;
	
	List<PageData> getUserDormInfoDepts (String[] array) throws Exception;
	
	void updateStuDorm(PageData pd) throws Exception;
	void saveStuDorm(PageData pd) throws Exception;
	
	void updateTuisu(PageData pd) throws Exception;
	
	List<PageData> getStuInfoList (Page page) throws Exception;
	List<PageData> getfenpeiStuInfoList (Page page) throws Exception;
	
	List<PageData> ExportDormInfo (PageData pd) throws Exception;
	List<PageData> ExportDormhistoryInfo (PageData pd) throws Exception;
	
	PageData getStuDrom(PageData pd) throws Exception;
	
	PageData getdaoruStuDrom(PageData pd) throws Exception;
	
	List<PageData> getDromList (PageData pd) throws Exception;
	
	List<PageData> getdaoruDromList (PageData pd) throws Exception;
	
	List<PageData> getinfoList (PageData pd) throws Exception;
	
	PageData getDormName(PageData pd) throws Exception;
	void updatebatchBindStuAndDorm(List<PageData> list)throws Exception;
	/**
	 * 
	 * <p>描述:获取预定分配数据</p>
	 * @author Administrator 王宁
	 * @date 2018年4月3日 上午9:56:19
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> dormOrderlistPage(Page page)throws Exception;

	List<PageData> getDormDeptsBykey(PageData pd)throws Exception;

	List<PageData> getDormOrder(PageData pd)throws Exception;

	void updateDormStu(PageData pd_stuDorm)throws Exception;

	PageData getStuDorm(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:分配宿舍并更新宿舍资源</p>
	 * @author ning 王宁
	 * @date 2018年7月11日 下午3:23:28
	 * @param pd
	 * @throws Exception
	 */
	void updatefenpei(PageData pd)throws Exception;
	void updatetiaosu(PageData pd)throws Exception;
	void updatetiaosuold(PageData pd)throws Exception;
	
	PageData getItemList(PageData pd_stu)throws Exception;

	PageData getDormType(PageData pd_stuDorm)throws Exception;

	List<PageData> getStuInfoList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取文化课学校列表</p>
	 * @author wn 王宁
	 * @date 2018年12月6日 上午11:28:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getWhkList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:获取班型类表</p>
	 * @author wn 王宁
	 * @date 2018年12月6日 上午11:36:43
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getClassTypeList(PageData pd)throws Exception;
	public void savelog(PageData pd) throws Exception;
	PageData getdorminfo(PageData pd) throws Exception;
	
	PageData getSCHOOLNAME(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:获取入学年份下拉列表</p>
	 * @author wn 王宁
	 * @date 2019年2月13日 上午10:43:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getRuxuenianfenList(PageData pd)throws Exception;
}



















