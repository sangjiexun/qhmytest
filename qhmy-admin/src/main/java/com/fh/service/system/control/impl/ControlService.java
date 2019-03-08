package com.fh.service.system.control.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.UuidUtil;
import com.fh.service.system.control.ControlManager;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明： 组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 * @version
 */
@Service("controlService")
public class ControlService implements ControlManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("ControlMapper.save", pd);
		}
		
	
	
	@Override
	public PageData getIdentify(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ControlMapper.getIdentify", pd);
	}
	@Override
	public PageData getxmbm(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ControlMapper.getxmbm", pd);
	}
	
	@Override
	public PageData findBybianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ControlMapper.findBybianma", pd);
	}
	
	
	
	
//	@Override
//	public PageData getbianma() throws Exception {
//		// TODO Auto-generated method stub
//		return (PageData)dao.findForObject("ControlMapper.getbianma",null);
//	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> cllistPage(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("ControlMapper.cllistPage", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getgzz(PageData pd) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("ControlMapper.getgzz", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getlxj(PageData pd) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("ControlMapper.getlxj", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getbianma() throws Exception {
		PageData pd = null;
		 pd = (PageData)dao.findForObject("ControlMapper.getbianma", "1");
		return String.format("%05d", Integer.parseInt(String.valueOf(pd.get("BIANMA"))));
		
	}      
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("ControlMapper.edit", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("ControlMapper.delete", pd);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findByBianma", pd);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("department/list.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@Override
	public List<Project> typeName() throws Exception {
		// TODO Auto-generated method stub
		return dao.typeName("ProjectMapper.typeName");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByTypeId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.findByTypeId", pd);
	}

	
	//user控制层用
	@Override
	public List<Department> findAll() throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>)dao.findAll("DepartmentMapper.findAll");
	}

	
	/**user控制层用
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllUserDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("user/listUsers.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllUserDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	
	/**labour控制层用
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllLabourDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("labour/listLabours.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllLabourDepartment1(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}
	
	public List<Department> listAllLabourDepartment1(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("labour/listLabours.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findBypId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>)dao.findForList("DepartmentMapper.findBypId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> departmentZtree(String department_id) throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>) dao.findForList("DepartmentMapper.departmentZtree", department_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_province(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_province", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_city(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_city", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_district(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_district", pd);
	}

	@Override
	public List<PageData> ListXM(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.listXm", pd);
	}

	@Override
	public Integer mcIsDuplicated(PageData pd) throws Exception {
		return (Integer)dao.findForObject("ControlMapper.MCIsDuplicated",pd);
	}

	@Override
	public List<PageData> ListMQ(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("ControlMapper.listMenQu", pd);
	}



	

	
}

