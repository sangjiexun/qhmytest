package com.fh.service.system.menu.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.PageData;

/**
 * 类名称：MenuService 菜单处理 创建人：FH qq 3 1 3 5 9 6 7 9 0[青苔] 修改时间：2015年10月27日
 * 
 * @version v2
 */
@Service("menuService")
public class MenuService implements MenuManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 通过ID获取其子一级菜单
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(PageData pageData) throws Exception {
//		String parentId,String ZZJG
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId",
				pageData);
	}

	/**
	 * 通过菜单ID获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}

	/**
	 * 新增菜单
	 * 
	 * @param menu
	 * @throws Exception
	 */
	@Override
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
	}

	/**
	 * 取最大ID
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}

	/**
	 * 删除菜单
	 * 
	 * @param MENU_ID
	 * @throws Exception
	 */
	@Override
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}

	/**
	 * 编辑
	 * 
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@Override
	public void edit(Menu menu) throws Exception {
		dao.update("MenuMapper.updateMenu", menu);
	}

	/**
	 * 保存菜单图标
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData editicon(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.editicon", pd);
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		PageData pageData = new PageData();
		pageData.put("parentId", MENU_ID);
		List<Menu> menuList = this.listSubMenuByParentId(pageData);//根据父菜单id得到子菜单
		for (Menu menu : menuList) {
			menu.setMENU_URL("menu/toEdit.do?MENU_ID=" + menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * 
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Menu> listAllMenuQx(PageData pageData) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(pageData);
		for (Menu menu : menuList) {
			pageData.put("parentId", menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenuQx(pageData));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * 根据组织类型查询标签
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getmenulist(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.getmenulistByzzjg",pd);
	}
	
	

	

	public List<Menu> getmenulistByzzjg(PageData pd) throws Exception {
		List<Menu> menuList = this.getmenulist(pd);//取出所有顶级菜单 状态=1的
		for (Menu menu : menuList) {
			pd.put("parentId", menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenuQx(pd));
		}
		return menuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getButtonByQuxian(PageData pageData) throws Exception {
		return (List<PageData>) dao.findForList("MenuMapper.getButtonByQuxian",
				pageData);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> buttonJurisdiction(List<Menu> allmenuList, User user) throws Exception {
		PageData pageData = new PageData();
		if (allmenuList!=null) {
			List<PageData> rstList = null;
			for (Menu menu : allmenuList) {
				pageData.put("MENU_ID", menu.getMENU_ID());
				pageData.put("ROLE_ID", user.getRole().getROLE_ID());
				rstList = (List<PageData>) dao.findForList("MenuMapper.getButtonByQuxian", pageData);
				menu.setButtonList(rstList);
				
				if (menu.isHasMenu()) {
					this.buttonJurisdiction(menu.getSubMenu(), user);
				}
			}
		}
		return null;
	}

}
