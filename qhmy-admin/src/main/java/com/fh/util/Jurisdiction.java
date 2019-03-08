package com.fh.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.keman.zhgd.common.util.RightsHelper;

/**
 * 权限处理
 * @author:zhoudibo
 * 修改日期：2015/11/19
*/
public class Jurisdiction {
	
	public static String URL_PRIX = ".php"; //请求地址的后缀
	

	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
	 * @param menuUrl  菜单路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasJurisdiction(String menuUrl){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String USERNAME = getUsername();	//获取当前登录者loginname
		Session session = getSession();
		
		List<Menu> menuList = (List<Menu>)session.getAttribute(Const.SESSION_menuList); //获取菜单列表
		
		if (menuList == null || menuList.size()==0) {
			return false;
		}
		Map<String,String> rstMap = new HashMap<String,String>();
		rstMap.put("rst", "false");
		readMenu(rstMap,menuList,menuUrl,session,USERNAME);
		
		if (rstMap.get("rst").equals("true")) {
			return true;
		}else {
			return false;
		}
	}
	
	/**校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
	 * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param menuUrl:访问地址
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static boolean readMenu(Map<String,String> rstMap,List<Menu> menuList,String menuUrl,Session session,String USERNAME){
		List<PageData> buttons = (List<PageData>)session.getAttribute(USERNAME+Const.SESSION_QX);
		
		boolean jieguo = false;
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMENU_URL().split(URL_PRIX)[0].equals(menuUrl.split(URL_PRIX)[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)										//按钮判断
				Map<String, String> buttonQxMap = new HashMap<String,String>();
				
				buttons = menuList.get(i).getButtonList();//luzhen //高校缴费系统，将权限按钮存储在了Menu对象中
				
				for (PageData pageData : buttons) {
					if (menuList.get(i).getMENU_ID().equals(pageData.getString("MENU_ID"))) {
						buttonQxMap.put(pageData.getString("QX_NAME"), "1");
					}
				}
				session.removeAttribute(USERNAME + Const.SESSION_MENU_BUTTONS);
				session.setAttribute(USERNAME + Const.SESSION_MENU_BUTTONS, buttonQxMap);	//重新分配按钮权限
				
//				session.removeAttribute(Const.SESSION_MENU_BUTTONS_JSON);
//				session.setAttribute(Const.SESSION_MENU_BUTTONS_JSON,JsonUtil.mapToJsonStr(buttonQxMap));
				session.removeAttribute(Const.SESSION_MENU_BUTTONS);
				session.setAttribute(Const.SESSION_MENU_BUTTONS, buttonQxMap);	//重新分配按钮权限
				
				rstMap.put("rst", "true");
				return true;
			}else{
				readMenu(rstMap,menuList.get(i).getSubMenu(),menuUrl,session,USERNAME);//继续排查其子菜单
				
			}
		}
		return true;
	}
	
	/**
	 * 按钮权限(方法中校验)   验证有没有按钮权限  
	 * @param menuUrl  菜单路径
	 * @param type  类型(add、del、edit、cha)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean buttonJurisdiction(String menuUrl, String type){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		String USERNAME = getUsername();	//获取当前登录者loginname
		Session session = getSession();
		List<Menu> menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList); //获取菜单列表
		readMenuButton(menuList,menuUrl,session,USERNAME,type);
		return true;
	}
	
	/**校验按钮权限(递归处理)
	 * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
	 * @param menuUrl:访问地址
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean readMenuButton(List<Menu> menuList,String menuUrl,Session session,String USERNAME, String type){
		for(int i=0;i<menuList.size();i++){
			if(menuList.get(i).getMENU_URL().split(URL_PRIX)[0].equals(menuUrl.split(URL_PRIX)[0])){ //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
				if(!menuList.get(i).isHasMenu()){				//判断有无此菜单权限
					return false;
				}else{											//按钮判断
					Map<String, String> map = (Map<String, String>)session.getAttribute(USERNAME + Const.SESSION_QX);//按钮权限(增删改查)
					String MENU_ID =  menuList.get(i).getMENU_ID();
					Boolean isAdmin = "admin".equals(USERNAME);
					if("add".equals(type)){
						return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
					}else if("del".equals(type)){
						return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
					}else if("edit".equals(type)){
						return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
					}else if("cha".equals(type)){
						return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
					}
				}
			}else{
				readMenuButton(menuList.get(i).getSubMenu(),menuUrl,session,USERNAME,type);//继续排查其子菜单
			}
		}
		return true;
	}
	
	/**获取当前登录的用户名
	 * @return
	 */
	public static String getUsername(){
		User user = (User)getSession().getAttribute(Const.SESSION_USER);
		return user.getUSERNAME();
	}
	
	/**获取当前按钮权限(增删改查)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getHC(){
		return (Map<String, String>)getSession().getAttribute(getUsername() + Const.SESSION_QX);
	}
	
	/**shiro管理的session
	 * @return
	 */
	public static Session getSession(){
		//Subject currentUser = SecurityUtils.getSubject();  
		return SecurityUtils.getSubject().getSession();
	}
}
