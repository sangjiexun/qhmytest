package com.fh.service.system.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.MenuButtonVo;
import com.fh.entity.system.Role;
import com.fh.service.system.role.RoleManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.DataZidianSelect.MenuButtonEnum;
import com.keman.zhgd.common.tree.VO;
import com.keman.zhgd.common.util.RightsHelper;
import com.keman.zhgd.common.util.Tools;

/**	角色
 * @author FHadmin zhoudibo
 * 修改日期：2015.11.6
 */
@Service("roleService")
public class RoleService implements RoleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列出此组下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);
	}
	
	/**通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.findObjectById", pd);
	}
	
	/**添加
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void add(PageData pd) throws Exception {
		dao.save("RoleMapper.insert", pd);
	}
	
	/**保存修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("RoleMapper.edit", pd);
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	@Override
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);
	}
	
	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	@Override
	public void updateRoleRights(Role role) throws Exception {
		dao.update("RoleMapper.updateRoleRights", role);
	}
	
	/**通过id查找
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", ROLE_ID);
	}
	
	/**给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
	}
	
	/**权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveB4Button(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> listAllRolesBydepid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesBydepid", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByRoleId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("RoleMapper.sys_role_department", pd);
	}

	@Override
	public void addrd(PageData pd) throws Exception {
		dao.save("RoleMapper.addrd",pd);
		
	}

	@Override
	public void deleterd(String a) throws Exception {
		dao.delete("RoleMapper.deleterd",a);
		
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
	 * 
	 */
	@Override
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("role.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getzu(String a) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("RoleMapper.getzu", a);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findObjectByUserid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("RoleMapper.findObjectByUserid", pd);
	}

	@Override
	public List<Role> listAllRolesByPId1(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId1", pd);
	}

	@Override
	public List<Role> listAllRolesByPId11(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId11", pd);
	}

	@Override
	public PageData setAllRights111(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("RoleMapper.setAllRights111", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> rolelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("RoleMapper.rolelistPage", page);
	}

	@Override
	public void update(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("RoleMapper.edit", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("RoleMapper.insert", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryUserhasRoleCount(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RoleMapper.queryUserhasRoleCount", pd);
	}

	@Override
	public void deleteRoleList(PageData pd) throws Exception {
		dao.delete("RoleMapper.deleteRoleList", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuButtonVo> queryMenuAndButtonRole(PageData pd) throws Exception{
		List<MenuButtonVo> menuButtonVos = (List<MenuButtonVo>)(dao.findForList("RoleMapper.queryMenuAndButtonRole", pd));
		setMenuChecked(menuButtonVos,pd);
		return menuButtonVos;
	}
	
	
	/**
	 * <p>描述:设置菜单的checked属性</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午6:16:41
	 */
	private void setMenuChecked(List<MenuButtonVo> menuButtonVos,PageData pd){
		
		String rights = pd.getString("rights");
		/*
		 * 判断权限，设置checked属性
		 */
		for (MenuButtonVo menuButtonVo : menuButtonVos) {
			if (MenuButtonEnum.菜单.getValue().equals(menuButtonVo.getType())) {
				if(RightsHelper.testRights(rights, menuButtonVo.getId())){
					menuButtonVo.setState("true");
				}else{
					menuButtonVo.setState("false");
				}
			}
		}
		//end 判断权限，设置checked属性
	}

	@Override
	public void saveJurisdiction(PageData pd) throws Exception {
		// TODO Auto-generated method stub
//		pd.put("rights", rights);
//		pd.put("buttons", buttons);
		
		dao.update("RoleMapper.updateRoleRights", pd);//更新菜单权限值
		dao.delete("RoleMapper.deleteButtonJurisdictionByRole", pd);
		String buttons = pd.getString("buttons");
		String [] buttonArray = buttons.split(",");
		if (buttonArray.length>0) {
			for (String button : buttonArray) {
				pd.put("button_id", button);
				dao.save("RoleMapper.saveButtonJurisdiction", pd);
			}
		}
	}

	@Override
	public PageData rolenameIdentify(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.rolenameIdentify", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VO> queryqxRole(PageData pd) throws Exception{
		return (List<VO>)dao.findForList("RoleMapper.queryqxRole", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VO> queryyhqxRole(PageData pd) throws Exception{
		return (List<VO>)dao.findForList("RoleMapper.queryyhqxRole", pd);
	}
	
	
	@Override
	public void savesjqxJur(PageData pd) throws Exception {
				dao.save("RoleMapper.savesjqxJur", pd);
	}
	
	@Override
	public void saveyhsjqxJur(PageData pd) throws Exception {
				dao.save("RoleMapper.saveyhsjqxJur", pd);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getqxlist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RoleMapper.getqxlist", pd);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> getyhqxlist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RoleMapper.getyhqxlist", pd);
	}
	
	@Override
	public void updatesjqx(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("RoleMapper.updatesjqx", pd);
	}
	
	@Override
	public void updateyhsjqx(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("RoleMapper.updateyhsjqx", pd);
	}
}
