package com.fh.service.authoritymanage.department;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;


/**
 * <p>标题:DepartmentManager</p>
 * <p>描述:组织机构服务接口</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月9日 下午4:31:59
 */
public interface DepartmentManager{
	
	/**
	 * <p>描述:分页查询组织机构 T_COLLEGES_DEPARTMENT</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月9日 下午4:33:00
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> departmentlistPage(Page page)throws Exception;

	/**
	 * 
	 * <p>描述:查询组织机构T_COLLEGES_DEPARTMENT</p>
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
	 * <p>描述:验证唯一</p>
	 * @author Administrator 胡文浩
	 * @date 2017年8月30日 下午5:05:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<PageData> getlistbyname(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:删除组织时验证是否有用户</p>
	 * @author Administrator 胡文浩
	 * @date 2017年9月26日 下午5:58:43
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> CheckUser(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:查询组织数据</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午5:23:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> getDepartmentList(PageData pd)throws Exception;
	/**
	 * 
	 * <p>描述:根据组织名称查询是否合理</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午6:00:32
	 * @param pdTemp
	 * @return
	 * @throws Exception
	 */
	String getDeptIdByName(PageData pdTemp)throws Exception;

	PageData getlistbyBianma(PageData pd)throws Exception;
	
	
	List<PageData> getcdlistbyname(PageData pd)throws Exception;
	
}

