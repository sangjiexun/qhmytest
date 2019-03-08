package com.fh.service.departmentMei;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:DepartmentMeiManager
 * </p>
 * <p>
 * 描述:美院
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月26日 上午10:21:02
 */
public interface DepartmentMeiManager {

	/**
	 * 
	 * <p>
	 * 描述:增加美院院系
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:28:33
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:删除美院院系
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:29:17
	 * @param pd
	 * @throws Exception
	 */
	public Map<String, Object> delete(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:更改是否启用
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 下午3:43:31
	 * @param pd
	 * @throws Exception
	 */
	public void update_isqy(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:查询单个美院
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:31:02
	 * @param pd
	 * @throws Exception
	 */
	public PageData depListById(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:修改美院
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:31:38
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:获取美院列表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:26:39
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> depList(Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:唯一性验证
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 下午1:54:54
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	boolean getMeiDepByName(PageData pd) throws Exception;

	/**
	 * 
	 * <p>
	 * 描述:根据id查询名称 与页面传过来的名称 比较
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月20日 上午10:24:45
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	String getname(PageData pd) throws Exception;

}
