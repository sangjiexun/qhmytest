package com.fh.dao;

import java.util.List;

import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;


/**
 * @author 
 * 修改时间：2015、12、11
 */
public interface DAO {
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception;
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception;
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception;
	
	/**
	 * 查找对象封装成Map
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
	

	/**
	 * 查找Project对象
	 * 
	 */
	public List<Project> typeName(String typeName) throws Exception;
	
	
	
	public List<Department> findAll(String findAll) throws Exception;
	
	public List<User> findAll1(String findAll) throws Exception;
	
	/**
	 * 创建序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月13日 下午2:38:14
	 * @param str
	 * @param value
	 * @throws Exception
	 */
	public void creatSeq(String str, String value) throws Exception;
	
	/**
	 * 创建序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月13日 下午2:38:14
	 * @param str
	 * @param value
	 * @throws Exception
	 */
	public void creatSeq2(String str, String value) throws Exception;
	/**
	 * 查询序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月13日 下午3:04:07
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String getSeq(String value) throws Exception;
	
	/**
	 * 查询序列
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2018年7月13日 下午3:04:07
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String getSeq2(String value) throws Exception;

	String getSeq4(String value) throws Exception;
	
	
	
	
}
