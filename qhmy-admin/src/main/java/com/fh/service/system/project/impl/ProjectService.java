package com.fh.service.system.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.util.PageData;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.project.projectManager;

/**
 * 说明： 组织机构 创建人：zhoudibo 创建时间：2015-12-16
 * 
 * @version
 */
@Service("projectService")
public class ProjectService implements projectManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("DepartmentMapper.savexm", pd);
		dao.save("DepartmentMapper.savefb", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("DepartmentMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("DepartmentMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"DepartmentMapper.datalistPage1", page);
	}

	/**
	 * 详情
	 * 
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PageData listmessage(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DepartmentMapper.listmessage", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DepartmentMapper.findById", pd);
	}

	/**
	 * 通过编码获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByBianma(PageData pd) throws Exception {
		return (PageData) dao
				.findForObject("DepartmentMapper.findByBianma", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId)
			throws Exception {
		return (List<Department>) dao.findForList(
				"DepartmentMapper.listSubDepartmentByParentId", parentId);
	}

	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this
				.listSubDepartmentByParentId(parentId);
		for (Department depar : departmentList) {
			depar.setTreeurl("project/list.do?DEPARTMENT_ID="
					+ depar.getDEPARTMENT_ID() + "&BIANMA=" + depar.getBIANMA());
			depar.setSubDepartment(this.listAllDepartment(depar
					.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@Override
	public List<Project> typeName() throws Exception {
		// TODO Auto-generated method stub
		return dao.typeName("ProjectMapper.typeName");
	}

	@Override
	public PageData findByName(PageData pd) throws Exception {
		return (PageData) dao
				.findForObject("DepartmentMapper.findByName", pd);
	}

	@Override
	public PageData findByxmName(PageData pd) throws Exception {
		return (PageData) dao
				.findForObject("DepartmentMapper.findByxmName", pd);	}

}
