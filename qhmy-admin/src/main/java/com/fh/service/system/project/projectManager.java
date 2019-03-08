package com.fh.service.system.project;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.util.PageData;

/** 
 * 说明： 组织机构接口类
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 * @version
 */
public interface projectManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	
	
	public PageData listmessage(PageData pd)throws Exception;
	
	public PageData findById(PageData pd)throws Exception;
	
	public PageData findByName(PageData pd)throws Exception;
	public PageData findByxmName(PageData pd)throws Exception;

	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	
	public PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
	
	
	/**
	 * 获取Project表所有数据
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	
	public List<Project> typeName() throws Exception;

	
	
	
}

