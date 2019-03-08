package com.fh.service.system.department.impl;

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
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明： 组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 * @version
 */
@Service("departmentService")
public class DepartmentService implements DepartmentManager{

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
		dao.save("DepartmentMapper.save", pd);
		
		String parent_id =pd.getString("PARENT_ID");
		if("0".equals(parent_id)){
			
			String USERNAME=pd.getString("BIANMA");//总公司编码作为默认用户登录名
			String DEPARTMENT_ID=pd.getString("DEPARTMENT_ID");
			String RIGHTS="";
			String USER_ID= UuidUtil.get32UUID();
			String PASSWORD =new SimpleHash("SHA-1", USERNAME,"888").toString();
			String ISLABOUR ="n";
			String STATUS="0";
			String ROLE_ID="00";//系统默认总公司角色
			PageData pdData =new PageData();
			PageData pdRole =new PageData();
			pdRole.put("ROLE_ID", ROLE_ID);
			pdRole=	roleService.findObjectById(pdRole);
			RIGHTS=pdRole.getString("RIGHTS");
			pdData.put("USERNAME", USERNAME);
			pdData.put("DEPARTMENT_ID", DEPARTMENT_ID);
			pdData.put("RIGHTS", RIGHTS);
			pdData.put("USER_ID", USER_ID);
			pdData.put("PASSWORD", PASSWORD);
			pdData.put("ISLABOUR", ISLABOUR);
			pdData.put("STATUS", STATUS);
			pdData.put("ROLE_ID", ROLE_ID);
			userService.saveU(pdData); 	
			
		}
		
		
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("DepartmentMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("DepartmentMapper.edit", pd);
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
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listxx(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.xxlist", pd);
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
	
}

