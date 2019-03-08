package com.fh.util;

import java.net.URL;
import java.util.Properties;

import net.sf.ehcache.CacheManager;

import org.springframework.context.ApplicationContext;

import com.keman.zhgd.common.maputil.ReadPerpertiesUtil;
/**
 * 项目名称：
 * @author:zhoudibo 2015
 * 修改日期：2015/11/2
*/
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";//验证码
	public static final String SESSION_USER = "sessionUser";			//session用的用户
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
//	public static final String LOGIN = "/login_toLogin.do";				//登录地址
	public static String LOGIN = "/login_toLogin.do";				//登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = "(.*/((login_toLogin)|(uploadFiles)|(cas_login)|(login)|(depcheck)|(department/goActive)|(department/editActive)|(logout)|(code)|(app)|(weixin)|(static)|(plugins)|(main)|(websocket)).*)|(.*\\.json$)|(.*\\.html$)|(services)";	//不对匹配该值的访问路径拦截（正则）
	
	public static final String NO_UPLOADFILE_PATH = "(.*/((uploadFiles)).*)";	//不对匹配该值的访问路径拦截（正则）
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	public static final boolean ISDEBUG = true;
	public static final String TAB_DEPARTMENT_VALUE = "ORG";//组织查询标签类型
	public static final String TAB_XM_VALUE = "PROJECT";//项目查询标签类型

	public static final String SESSION_MENU_BUTTONS = "SESSION_MENU_BUTTONS";//某个菜单的按钮权限
	
	public static final String SESSION_MENU_BUTTONS_JSON = "SESSION_MENU_BUTTONS_JSON";//某个菜单的按钮权限
	
	
	public static String CAS_LOGIN = "/cas_login.php";				//登录地址
	
	//当前选中的组织或项目节点
	public static final String SELECTED_ZZJG="SELECTED_ZZJG";
	
	/**
	 * 当前选中的组织或项目的编码
	 */
	public static final String SELECTED_BIANMA="SELECTED_BIANMA";
	
	/**
	 * 当前选中的组织或项目的类型，确定是组织还是项目
	 */
	public static final String SELECTED_NODE_TYPE="SELECTED_NODE_TYPE";
	
	/**
	 * 导出excel时候，内容cell的位置样式
	 */
	public static final String EXCEL_CELL_STYLE = "cellStyle";
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
	
	public static CacheManager manager = null;  
	public static final String CacheName = "dicserviceCache";
	
	public static String LOGOUT = "/login_toLogin.do";
	
	public static String CLASSPATH = "";
	
	public static String STUDENT_LOGIN = "";//学生登录页面的url
	
	static{
		URL url = Const.class.getResource("/ehcache.xml");  
		manager = CacheManager.create(url); 
		//读取统一身份登录地址
		Properties properties = new Properties();
		ReadPerpertiesUtil.read(properties, "dbconfig");
//		LOGIN = properties.getProperty("loginurl");
//		LOGOUT = properties.getProperty("logouturl");
		STUDENT_LOGIN = properties.getProperty("student_login");
		String classPath = Const.class.getResource("/").getPath();
//		/E:/luzhen/src/myeclipse2014-02/zhgd/zhgd-fhadmin%20Maven%20Webapp/target/classes/
		classPath = classPath.replaceAll("classes/", "");
		classPath = classPath + "dymb/";
		CLASSPATH = classPath;
	}
	
	public static void main(String[] args) {
		String string = "/uploadFiles/uploadImgs/aaa.img";
	}
	
}
