package com.fh.service.system.dorm;

import java.util.List;

import com.keman.zhgd.common.tree.VO;
import com.fh.entity.Page;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.PageData;


public interface StudentDormManager {

	/**
	 * <p>描述:获得宿舍信息树</p>
	 * @author chencc
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<VO> getStudentDormTree(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询宿舍列表表格</p>
	 * @author chencc
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormList(PageData pd)throws Exception;
	
	List<PageData> getdepList(PageData pd)throws Exception;
	List<PageData> getdateList(PageData pd)throws Exception;
	
	
	/**
	 * <p>描述:获得所有的学校</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getSchoolsListTree(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得宿舍级别</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDormLevel(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDorm(PageData pd) throws Exception;
	
	/**
	 * <p>描述:插入宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void insertStudentDorm(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDormBySdName(PageData pd) throws Exception;
	
	
	/**
	 * <p>描述:查询宿舍类型</p>
	 * @author chencc
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormTypeList(PageData pd)throws Exception;
	
	/**
	 * <p>描述:更新宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateStudentDorm(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得被占用的床位</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getInUseChuangListByParentPkid(PageData pd) throws Exception;
	
	/**
	 * <p>描述:删除宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void deleteStudentDorm(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询该节点的上一节点</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getLastStudentDormByPkid(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得学生宿舍计划左侧树</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getStudentDormPlanTree(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得所有的学校</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getSchoolsListPlanTree(PageData pd) throws Exception;
	
	/**
	 * <p>描述:获得入学年份集合</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRuxuenianfenList(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询当前登录用户所拥有权限的院校专业下拉框树 </p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getDepartmentPlanTree(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询宿舍卡片List</p>
	 * @author chencc
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormPlanList(PageData pd)throws Exception;
	
	/**
	 * 宿舍计划列表
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年8月6日 上午8:47:01
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormPlanlistPage(Page page)throws Exception;
	
	/**
	 * <p>描述:查询床数量</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDormChuangCountByParentPkid(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询当前登录用户所拥有权限的宿舍下拉框树 </p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getStudentDormPlanTreeForAllot(PageData pd) throws Exception;
	
	public List<VO> gettiaosuStudentDormTree(PageData pd) throws Exception;
	
	public List<VO> getStudentDormPlanTreeForAllotBySex(PageData pd) throws Exception;
	public List<VO> getStudentDorminfotree(PageData pd) throws Exception;
	
	public List<VO> getStudentDorminfotreestu(PageData pd) throws Exception;
	
	/**
	 * <p>描述:查询当前登录用户所拥有权限的宿舍下拉框树 </p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> getStudentDormPlanTreeForRecovery(PageData pd) throws Exception;
	
	/**
	 * <p>描述:分配宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void insertStudentDormDepartment(PageData pd, StuInfoManager stuInfoService) throws Exception;

	/**
	 * <p>描述:回收宿舍</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateStudentDormDepartment(PageData pd, StuInfoManager stuInfoService) throws Exception;
	
	/**
	 * <p>描述:</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDormTop(PageData pd) throws Exception;
	
	/**
	 * <p>描述:通过PKID获得床集合</p>
	 * @author chencc
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormByPkidList(PageData pd) throws Exception;
	
	/**
	 * <p>描述:</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStudentDormChildCount(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:获取已启用宿舍类型</p>
	 * @author Administrator 王宁
	 * @date 2018年4月23日 下午5:10:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormTypeListUsed(PageData pd)throws Exception;
	
	/**
	 * <p>描述:</p>
	 * @author chencc
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public VO getCollege(PageData pd) throws Exception;
	
	/**
	 * 获取宿舍计划信息列表
	 * @author chencc
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getStudentDormPlanTablelistPage(Page page)throws Exception;
	
	/**
	 * 
	 * <p>描述:公寓使用统计列表导出</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月27日 下午3:17:30
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getApartmentStatisticslist(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:宿舍下拉框</p>
	 * @author Administrator 柴飞
	 * @date 2018年8月27日 下午10:45:32
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getDormList(PageData pd)throws Exception;
	
	/**
	 * 获得班型集合
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年11月30日 上午11:21:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getBanxingList(PageData pd) throws Exception;
	
	/**
	 * 获得文化课学校集合
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年11月30日 上午11:21:37
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getWenhuakexuexiaoList(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:入住统计</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月18日 上午8:24:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getDormrztjlistPage(Page page)throws Exception;
	/**
	 * 
	 * <p>描述:打印入住统计</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月18日 上午10:14:12
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> printDormrztj(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:</p>
	 * @author Administrator 胡文浩
	 * @date 2018年12月19日 上午11:20:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getDormtree(PageData pd)throws Exception;
	
}
