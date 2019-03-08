package com.fh.service.authoritymanage.department;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;


/**
 * <p>标题:StudentDepartmentManager</p>
 * <p>描述:学生所属组织</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月14日 下午3:26:57
 */
public interface StudentDepartmentManager{
	
	/**
	 * <p>描述:分页查询学生组织机构 SYS_DEPARTMENT</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 下午4:33:00
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> departmentlistPage(Page page)throws Exception;

	/**
	 * 
	 * <p>描述:查询组织机构SYS_DEPARTMENT</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 下午6:06:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<VO> departmentList(PageData pd) throws Exception;

	/**
	 * <p>描述:保存组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午10:56:16
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:更新组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 上午10:56:28
	 * @param pd
	 * @throws Exception
	 */
	void update(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:根据条件查询组织</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午2:26:02
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> queryDepartment(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:删除 组织机构</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月10日 下午3:32:49
	 * @param pd
	 * @throws Exception
	 */
	void departmentDel(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:根据组织ID查询组织下是否有人员</p>
	 * @author Administrator 王宁
	 * @date 2017年9月27日 上午10:43:46
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	PageData isHaveUser(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:查询最顶级院校专业信息</p>
	 * @author Administrator 康程亮
	 * @date 2018年6月19日 上午11:02:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getTopDept(PageData pd)throws Exception;
	
	/**
	 * 
	 * <p>描述:修改排序</p>
	 * @author Administrator 康程亮
	 * @date 2018年6月19日 下午2:47:53
	 * @param pd
	 * @throws Exception
	 */
	void updatePaiXu(PageData pd) throws Exception;
	
	void saveDeptByBatch(List<PageData> list) throws Exception;
	/**
	 * 
	 * <p>描述:批量插入民族</p>
	 * @author ning 王宁
	 * @date 2018年6月26日 下午4:21:54
	 * @param batchList
	 * @throws Exception
	 */
	void saveNationByBatch(List<PageData> batchList)throws Exception;
	/**
	 * 
	 * <p>描述:批量插入学生来源</p>
	 * @author Administrator 柴飞
	 * @date 2018年7月9日 下午3:20:53
	 * @param batchList
	 * @throws Exception
	 */
	void saveStuSourceByBatch(List<PageData> batchList)throws Exception;
	/**
	 * 
	 * <p>描述:修改院校专业启用未启用</p>
	 * @author Administrator wzz
	 * @date 2018年7月19日 下午3:20:53
	 * @param batchList
	 * @throws Exception
	 */
	void updateIsUse(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:查询专业对应的学生类型名称</p>
	 * @author Administrator wzz
	 * @date 2018年7月19日 下午3:20:53
	 * @param batchList
	 * @throws Exception
	 */
	PageData getXSLXName(PageData pd) throws Exception;
}

