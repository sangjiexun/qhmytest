package com.fh.service.system.menu;

import java.util.List;

import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.util.PageData;


/**说明：MenuService 菜单处理接口
 * @author zhoudibo
 */
public interface MenuManager {

	/**
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listSubMenuByParentId(PageData pData)throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception;
	
	/**
	 * @param menu
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception;
	
	/**
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception;
	
	/**
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(PageData pd) throws Exception;
	/**
	 * 根据组织类别加载首页标签
	 * @param ZZJG
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getmenulistByzzjg(PageData pd)throws Exception;
	public List<Menu> getmenulist(PageData pd) throws Exception;

	public List<PageData>getButtonByQuxian(PageData pageData) throws Exception;

	/**
	 * 获取按钮权限
	 * <p>描述:</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月5日 下午5:10:59
	 * @param allmenuList
	 * @param user
	 * @return
	 */
	public List<Menu> buttonJurisdiction(List<Menu> allmenuList, User user) throws Exception;
	
}
