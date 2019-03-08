package com.fh.service.system.stuinfo;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface StuInfoManager {

	/**
	 * <p>描述:学生信息列表</p>
	 * @author Administrator 康程亮
	 * @date 2018年11月28日 下午4:39:30
	 */
	List<PageData> stuinfo_list(Page page) throws Exception;
	/**
	 * <p>描述:学生列表表头</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getCurrentUserTableShowCollums(PageData pd) throws Exception;
	/**
	 * <p>描述:学生列表表头修改</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	void updateShowCols(PageData pd) throws Exception;
	/**
	 * <p>描述:学生列表表头新增</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	void saveShowCols(PageData pd) throws Exception;
	/**
	 * <p>描述:学生列表表头用户权限下的集合</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getCurrentUserTableShowCollumsList(PageData pd) throws Exception;
	/**
	 * <p>描述:查询合作学校集合</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getPartSchoolList(PageData pd) throws Exception;
	/**
	 * <p>描述:查询层次列表</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getCengCi(PageData pd) throws Exception;
	/**
	 * <p>描述:美院列表 校考成绩</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getMeiYuan(PageData pd) throws Exception;
	/**
	 * <p>描述:根据入学年份和班型查询班级列表</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getBanJi(PageData pd) throws Exception;
	/**
	 * <p>描述:导出学生信息</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> exportStuInfoToExcel(PageData pd) throws Exception;
	/**
	 * <p>描述:查询校考成绩列表</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getDepList(PageData pd) throws Exception;
	/**
	 * <p>描述:获取学习科目</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	List<PageData> getLearnSubList(PageData pd) throws Exception;
	/**
	 * <p>描述:根据T_STUDENT_BM的pkid查询一条数据详情</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getStuInfoByPkid(PageData pd) throws Exception;
	/**
	 * <p>描述:根据pkid获取学习科目</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getSubById(PageData pd) throws Exception;
	/**
	 * <p>描述:根据name获取学习科目</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getSubByName(PageData pd) throws Exception;
	/**
	 * <p>描述:根据SYS_MEI_DEPARTMENT的pkid查询一条数据详情</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getDepListById(String PKID) throws Exception;
	/**
	 * <p>描述:根据中文名称匹配校考成绩（美院）</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getMeiByName(String XKMARK) throws Exception;
	/**
	 * <p>根据身份证号 入学年份 班型查询学生数据</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getStuBySfzRnBx(PageData pd) throws Exception;
	/**
	 * <p>根据身份证号查询学生信息 </p>
	 * @author Administrator wzz
	 * @date  2019年1月24日 下午4:39:30
	 */
	PageData getStuBySfzNum(PageData pd) throws Exception;
	/**
	 * <p>根据学号查询学生信息</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getStuinfoByXuehao(PageData pd) throws Exception;
	/**
	 * <p>根据身份证号查询学生信息</p>
	 * @author Administrator wzz
	 * @date  2018年11月28日 下午4:39:30
	 */
	PageData getStuinfoBySfz(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:批量(不)通过</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午4:39:30
	 */
	void batchTongGuo(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:批量毕业或退学</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午4:39:30
	 */
	void batchByOrTuiXue(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:批量毕业或退学</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午4:39:30
	 */
	void batchByOrFuXue(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:批量毕业或退学</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午4:39:30
	 */
	void updateStu(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据中文名称匹配层次</p>
	 * @author Administrator wzz
	 * @date 2018年11月28日 下午4:39:30
	 */
	PageData getCengCiByName(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:批量保存学生</p>
	 * @author Administrator wzz
	 * @date 2018年12月4日 上午10:33:45
	 */
	void batchSaveStu(List<PageData> list,String methodurl) throws Exception;
	/**
	 * 
	 * <p>描述:学生是否缴过费</p>
	 * @author Administrator wzz
	 * @date 2018年12月5日 下午7:55:55
	 */
	Boolean stuIsPaid(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:学生是否缴过费</p>
	 * @author Administrator wzz
	 * @date 2018年12月5日 下午7:55:55
	 */
	void saveTouxiangUrlToDB(PageData pd) throws Exception;
	/**
	 * 
	 <p>描述:学生是否缴过费</p>
	 * @author Administrator wzz
	 * @date 2018年12月11日 下午3:55:55
	 */
	void resetPwd(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:获取年级字典表数据</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月8日 上午11:34:53
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getGrades (PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:批量删除学生信息</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午3:15:55
	 * @param pkids
	 * @throws Exception
	 */
	void batchDelete(PageData pd,String [] pkids) throws Exception;
	/**
	 * 
	 * <p>描述:根据中文匹配字典项</p>
	 * @author Administrator wzz
	 * @date 2018年11月30日 下午3:15:55
	 * @param pkids
	 * @throws Exception
	 */
	PageData getSysDictyionaries(PageData pd) throws Exception;
	
	List<PageData> getBan (PageData pd) throws Exception;
	
	List<PageData> gethzxx (PageData pd) throws Exception;
	List<PageData> getbanji (PageData pd) throws Exception;
	
	//宿舍信息-分配成功后发送消息
	void sendMessage(String STUDENT_PKID, String DORM_PKID) throws Exception;
	
	/**
	 * 得到组织树结构
	 * <p>描述:</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月5日 下午2:36:03
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getZuzhis(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:取字典表中数据</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月3日 上午11:40:16
	 * @return
	 * @throws Exception
	 */
	List<PageData> getFromSYS_DICT(PageData pd) throws Exception;
	PageData getDictionariesById(PageData pageData)throws Exception;
	
	/**
	 * 
	 * <p>描述:除支付以外涉及宿舍资源维护</p>
	 * @author Administrator 王宁
	 * @date 2018年3月20日 下午3:59:30
	 * @param STUDENT_PKID：学生PKID  新增床位时可为空
	 * 、opt_dorm：要进行的操作，1表示要绑定床位，0表示要解绑床位 不涉及绑定关系时可为空
	 * 、DORM_PKID：宿舍PKID 有预定记录但没有绑定关系时可为空
	 * 、opt_des ： 要进行的操作不为空时，需要描述操作的动作，例如：分配床位，退宿，学籍异动等
	 * T_STUDENT_DORM_TYPE_PKID:宿舍类型pkid 必传字段
	 * 调用时需要确定这三个参数能否获取
	 */
	void updateDorm(String STUDENT_PKID,String opt_dorm,String DORM_PKID,String opt_des,String T_STUDENT_DORM_TYPE_PKID) throws Exception;
	void updateDorm(String STUDENT_PKID,String opt_dorm,String DORM_PKID,String opt_des,String T_STUDENT_DORM_TYPE_PKID,String OLD_DEPARTMENT_PKID, String NEW_DEPARTMENT_PKID) throws Exception;
}



















