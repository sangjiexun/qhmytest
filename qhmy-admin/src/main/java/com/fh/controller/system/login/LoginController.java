package com.fh.controller.system.login;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.buttonrights.ButtonrightsManager;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.project.projectManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.stuinfo.Impl.StuInfoService;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.userorg.UserorgManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.DateUtil;
import com.keman.zhgd.common.util.RightsHelper;
import com.keman.zhgd.common.util.Tools;

/**
 * 总入口
 * 
 * @author 修改日期：2015/11/2
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name = "fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name = "appuserService")
	private AppuserManager appuserService;
	@Resource(name = "departmentService")
	private DepartmentManager departmentService;
	@Resource(name = "projectService")
	private projectManager projectService;

	
	@Resource(name = "stuInfoService")
	private StuInfoService stuInfoService;
	
	/**
	 * 访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("STUDENT_LOGIN", Const.STUDENT_LOGIN);
		mv.addObject("pd", pd);
		return mv;
	}

	
	@RequestMapping(value = "/login.php")
	public ModelAndView goLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("STUDENT_LOGIN", Const.STUDENT_LOGIN);
		mv.setViewName("system/index/login");
		return mv;
	}
	
	
	@RequestMapping(value = "/main/gotopmain.php")
	public ModelAndView topmain() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
//		/*
//		 * luzhen  测试代码
//		 */
//		String studentPkid = "e5d0d4f9a02349c68f16abd9f375f404";
//		String itemPkid = "418aa5e60c144b878b0d7be4f58f389c";
//		TpayItemList tpayItemList = disCountCalculateImpl.disCountCalculateReturnTpayItemListOnly(studentPkid, itemPkid);
//		//end luzhen 测试代码
//		
		/*
		 * 判断当前用户是否拥有首页权限，如果没有展示一个默认页面
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String role_id = user.getROLE_ID();
		String USERNAME = user.getUSERNAME();
		Role role = user.getRole();											//获取用户角色
		String roleRights = role!=null ? role.getRIGHTS() : "";				//角色权限(菜单权限)
		PageData pda = new PageData();
		//session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); //将角色权限存入session
		//session.setAttribute(Const.SESSION_USERNAME, USERNAME);	
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){
			allmenuList = menuService.listAllMenuQx(pd);					//获取所有菜单
			if(Tools.notEmpty(roleRights)){//权限值不为空
				allmenuList = this.readMenu(allmenuList, roleRights);		//根据角色权限获取本权限的菜单列表
				menuService.buttonJurisdiction(allmenuList,user);           //设置按钮权限
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
			session.setAttribute(Const.SESSION_menuList, allmenuList);//菜单权限放入session中
		}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		allmenuList.remove(0);
		mv.addObject("menuList", allmenuList);
		//pd.put("allmenuList",allmenuList);
		pd.put("ROLE_ID", role_id);
		mv.addObject("pd", pd);
		mv.setViewName("system/index/zhigongmain");
		return mv;
	}
	
	@RequestMapping(value = "/main/gostutopmain.json")
	public ModelAndView stutopmain() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
        Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("SHENFENZHENGHAO", user.getUSER_IDCARD());
		mv.setViewName("system/index/stu_index");
		return mv;
	}
	/**
	 * 去CAS登录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cas_login")
	public void cas_login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter pWriter = response.getWriter();
		pWriter.print("<html><head><script>top.window.location.href='"+Const.LOGIN+"'</script><head></html>");
		pWriter.flush();
		pWriter.close();
	}

	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		InputStream is = LoginController.class.getClassLoader().getResourceAsStream("conf.properties");//读取配置文件
		Properties prop1 = new Properties();
		if (is != null) {
			prop1.load(is);
		} 
		String encryptString = prop1.getProperty("encryptString").trim();//秘钥
		String keyString = prop1.getProperty("keyString").trim();//key
	    String bb=java.net.URLDecoder.decode(decrypt(encryptString,keyString), "utf-8") ;   
        //System.out.println("解密后的数据:"+bb); 
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	        String time=  df.format(new Date());// new Date()为获取当前系统时间   
			InetAddress ia1 = InetAddress.getLocalHost();//获取本地IP对象  
			String mac=getMACAddress(ia1);
	        String[] aa =bb.split(","); 
	        int res=aa[1].compareTo(time);
	        if(//mac.equals(aa[0])  && res>0 
	        		true){ //mac.equals(aa[0])  && res>0 
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("kmykt", "").replaceAll("kmykt", "").split(",fh,");
		if(null != KEYDATA && KEYDATA.length == 4){
			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			String code = KEYDATA[2];
			if(null == code || "".equals(code)){//判断效验码
				errInfo = "nullcode"; 			//效验码为空
			}else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				String LX  = KEYDATA[3];      //登录过来的是学生还是教师  1：教师 2：学生
/*				String depid  = KEYDATA[4];      //登录过来学校编码
*/				pd.put("USERNAME", USERNAME);
				pd.put("LX", LX);
				/*pd.put("DEPID", depid);*/
				User user = new User();
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码
//					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();//密码加密
					String passwd = new SimpleHash("SHA-1", "", PASSWORD).toString();//密码加密
					pd.put("PASSWORD", passwd);
					//学生
//					if("2".equals(LX)){
//						//学生的话，根据学号和密码读取用户信息
//						pd.put("XUEHAO", KEYDATA[0]);
////						pd=stuInfoService.getStuInfo(pd);
//						
//					}else if("1".equals(LX)){
//						pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
//					}
					
					//肯定是老师
					pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
					
					if(pd != null){
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);//更新最后登录时间
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));//权限值
						user.setROLE_ID(pd.getString("ROLE_ID"));//角色ID
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						user.setDEPARTMENT_ID(pd.getString("DEPARTMENT_ID"));
						user.setUSER_IDCARD(pd.getString("USER_IDCARD"));
						Object ob = pd.get("USER_TYPE");
						user.setUserType(Integer.parseInt(ob.toString()));
						/*
						 * add by chencc 2018.06.15
						 * 获取当前登录学校名称，存入session
						 * 注：这里和云版的获取方式不同，（1.非云版的从配置文件获取，2.云版请参照云版代码，从租户表T_COLLEGES表获取）
						 * 用途：收据打印学校名称显示等等
						 */
						Properties prop = new Properties();
						InputStream in = null;
						in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
						prop.load(new InputStreamReader(in, "utf-8"));
						String collegeName="";
						String collegeNameEn="CAIJING";
						if(prop!=null){
							collegeName=prop.getProperty("collegeName");
							collegeNameEn=prop.getProperty("collegeNameEn");
						}
						user.setCOLLEGES_NAME(collegeName);
						user.setCOLLEGES_NAME_EN(collegeNameEn);
						
						
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						/*
						 * 获取用户所有表的动态列头
						 */
						List<PageData> currentUserTableShowCollumsList = stuInfoService.getCurrentUserTableShowCollumsList(pd);
						session.setAttribute("currentUserTableShowCollumsList", currentUserTableShowCollumsList);
						
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    if("null".equals(pd.getString("ROLE_ID"))||"undefined".equals(pd.getString("ROLE_ID"))){
					    	errInfo = "noquanxian";
					    }
					    if("1".equals(pd.getString("STATUS"))){//表示该用户已经停用
					    	errInfo = "userstop";
					    }
					    try { 
					        subject.login(token); 
					        
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						errInfo = "usererror"; 				//用户名或密码有误
						logBefore(logger, USERNAME+"登录系统密码或用户名错误");
					}
				}else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(Tools.isEmpty(errInfo)){
					session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
					errInfo = "success";					//验证成功
					logBefore(logger, USERNAME+"登录系统");
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	   }else{
		   ModelAndView mv = this.getModelAndView();
			//mv.setViewName("redirect:/login_toLogin");
       	mv.setViewName("redirect:" + Const.LOGOUT);
			return mv;
	   }
	    //return AppUtil.returnObject(new PageData(), map);
	}

	

	/**
	 * 访问系统首页
	 * 
	 * @param changeMenu
	 *            ：切换菜单参数
	 * @return
	 */
	@RequestMapping(value = "/main/{changeMenu}")
	@ResponseBody
	public ModelAndView login_index(
			@PathVariable("changeMenu") String changeMenu,
			HttpServletRequest request, Model model, String ZZJG) {

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("parentId", "0");
		
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);				//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);		//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());		//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);				//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();											//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";				//角色权限(菜单权限)
				PageData pda = new PageData();
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); //将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);				//放入用户名到session
				
				List<Menu> allmenuList = new ArrayList<Menu>();
				if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){
					allmenuList = menuService.listAllMenuQx(pd);					//获取所有菜单
					if(Tools.notEmpty(roleRights)){//权限值不为空
						allmenuList = this.readMenu(allmenuList, roleRights);		//根据角色权限获取本权限的菜单列表
						menuService.buttonJurisdiction(allmenuList,user);           //设置按钮权限
					}
					session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
					session.setAttribute(Const.SESSION_menuList, allmenuList);//菜单权限放入session中
				}else{
					allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
				}
				
//				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
//					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));	//按钮权限放到session中
//				}
				
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/topmain");
				mv.addObject("user", user);
				mv.addObject("menuList", allmenuList);
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		
		
//		try {
//			mv.addObject("username",user.getUSERNAME());
//			// -------------------------------------加载首页tab标签开始-----------------------------------------//
//			List<Menu> lMenus = new ArrayList<Menu>();
//			PageData pageData = new PageData();
//			pageData.put("roleid", user.getROLE_ID());
//			lMenus = menuService.getmenulistByzzjg(pageData);//获得所有的菜单
//			mv.addObject("lMenus", lMenus);
//			// -------------------------------------加载首页tab标签结束-----------------------------------------//
//			
//			/*
//			 * 根据用户权限，获取菜单
//			 */
//			
//			//end 根据用户权限，获取菜单
//		} catch (Exception e) {
//			e.printStackTrace();
//			mv.setViewName("system/index/login");
//			logger.error(e.getMessage(), e);
//		}
		
		
//		int  a = user.getUserType();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		pd.put("SYS_COLLEGENAME", "职工首页"); // 读取系统名称
		mv.addObject("pd", pd);
		mv.setViewName("system/index/topmain");
//		if(1 == a){
//			mv.setViewName("system/index/topmain");
//		}else if(2 == a){
//			try {
////				pd.put("SHENFENZHENGHAO", user.getUSER_IDCARD());
////				PageData stuInfoPd=stuInfoService.getStuInfo(pd);
////				
////				mv.addObject("stuInfoPd", stuInfoPd);
//				mv.setViewName("system/index/stu_topmain");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
		return mv;
	}

	
	private List<PageData> haveNode = new ArrayList<PageData>();

	
	
	private PageData getFristNode(List<PageData> orgList){
		if (orgList != null && orgList.size() > 0) {
			// 获取顶层节点
			List<PageData> topList = queryTopNodeOrg(orgList);

			boolean rst = false;
			boolean isHaveParentNode = false;

			for (PageData pageData : topList) {
				isHaveParentNode = isHaveParnetNode(pageData, orgList);

				if (isHaveParentNode) {
					continue;
				} else {
					return pageData;
				}
			}
		}
		return null;
	}
	


	public int treeSetp = 20;// 每次加20

	private boolean isHaveNode(PageData sonData) {
		for (PageData node : haveNode) {
			if (sonData.getString("ID").equals(node.getString("ID"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据父节点查找所有的子节点
	 * 
	 * @param pageData
	 * @param orgList
	 * @return
	 */
	private List<PageData> querySonNodeByParentNode(PageData pageData,
			List<PageData> orgList) {

		List<PageData> sonLists = new ArrayList<PageData>();

		for (PageData pageData2 : orgList) {
			if (pageData2.getString("PID").equals(pageData.getString("ID"))) {
				if (Integer.parseInt(pageData2.getString("LEVEL"))>Integer.parseInt(pageData.getString("LEVEL"))) {
					sonLists.add(pageData2);
				}
			}
		}
		return sonLists;
	}

	/**
	 * 是否有父节点
	 * 
	 * @param pageData
	 * @param orgList
	 * @return
	 */
	private boolean isHaveParnetNode(PageData pageData, List<PageData> orgList) {
		for (PageData pageData2 : orgList) {
			if (pageData.getString("PID").equals(pageData2.getString("ID"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询TOP节点
	 * 
	 * @param orgList
	 * @return
	 */
	private List<PageData> queryTopNodeOrg(List<PageData> orgList) {
		List<PageData> topList = new ArrayList<PageData>();
		for (PageData pageData : orgList) {
			if ("1".equals(String.valueOf(pageData.get("LEVEL")))) {
				topList.add(pageData);
			}
		}
		return topList;
	}

	/**
	 * 查询是否有子节点
	 * 
	 * @param pageData
	 * @param orgList
	 * @return
	 */
	private boolean checkisHaveSon(PageData pageData, List<PageData> orgList) {
		for (PageData pageData2 : orgList) {
			if (pageData2.getString("PID").equals(pageData.getString("ID"))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList
	 *            ：传入的总菜单
	 * @param roleRights
	 *            ：加密的权限字符串 =======
	 * 
	 *            /** 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList
	 *            ：传入的总菜单
	 * @param roleRights
	 *            ：加密的权限字符串 >>>>>>> .r2322
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(
					RightsHelper.testRights(roleRights, menuList.get(i)
							.getMENU_ID()));
			if (menuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);// 是：继续排查其子菜单
			} else {
				menuList.remove(i);
				i--;
			}
		}
		return menuList;
	}



	/**
	 * 进入tab标签
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/index/tab";
	}

	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		String USERNAME = Jurisdiction.getUsername(); // 当前登录的用户名
		logBefore(logger, USERNAME + "退出系统");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession(); // 以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称

		mv.setViewName("redirect:" + Const.LOGOUT);
		return mv;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME) {
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID")
					.toString());// 获取角色ID
			pd = roleService.findObjectById(pd); // 获取角色信息
			map.put("adds", pd.getString("ADD_QX")); // 增
			map.put("dels", pd.getString("DEL_QX")); // 删
			map.put("edits", pd.getString("EDIT_QX")); // 改
			map.put("chas", pd.getString("CHA_QX")); // 查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if ("admin".equals(USERNAME)) {
				buttonQXnamelist = fhbuttonService.listAll(pd); // admin用户拥有所有按钮权限
			} else {
//				updatepw
//				toExcel
//				email
//				FromExcel
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd); // 此角色拥有的按钮权限标识列表
			}
			for (int i = 0; i < buttonQXnamelist.size(); i++) {
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"), "1"); // 按钮权限  存储按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}

	/**
	 * 更新登录用户的IP
	 * 
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}
	
	/**
	 * 
	 * <p>描述:修改密码界面</p>
	 * @author Administrator 康程亮
	 * @date 2017年8月21日 下午6:54:17
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/goEditPwd.json")
	public ModelAndView editPwd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.setViewName("system/index/editpwd");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveNewPwd.json")
	public ModelAndView savePwd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		
		String newpwd = new SimpleHash("SHA-1", "", pd.getString("newpwd")).toString();
		String oldpwd = new SimpleHash("SHA-1", "", pd.getString("oldpwd")).toString();
		if(!user.getPASSWORD().equals(oldpwd)){
			map.put("result", "faild");
		}else{
			//保存新密码
			pd.put("pwd",newpwd);
			pd.put("userid", user.getUSER_ID());
			userService.saveNewPwd(pd);
			map.put("result", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	public static void main(String[] args) {
		String passwd = new SimpleHash("SHA-1", "", "2").toString();//密码加密
		System.out.println(passwd);
	}
	
	
	 //解密数据   
	   public static String decrypt(String message,String key) throws Exception {   
	           
		  
	           
	           byte[] bytesrc =convertHexString(message);      
	           Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");       
	           DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));      
	           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");      
	           SecretKey secretKey = keyFactory.generateSecret(desKeySpec);      
	           IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));   
	                  
	           cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);         
	            
	           byte[] retByte = cipher.doFinal(bytesrc);        
	           return new String(retByte);    
	           
	   }  
		
	   
	   public static byte[] convertHexString(String ss)    
	   {    
	   byte digest[] = new byte[ss.length() / 2];    
	   for(int i = 0; i < digest.length; i++)    
	   {    
	   String byteString = ss.substring(2 * i, 2 * i + 2);    
	   int byteValue = Integer.parseInt(byteString, 16);    
	   digest[i] = (byte)byteValue;    
	   }    
	 
	   return digest;    
	   }  
		
		   public static String toHexString(byte b[]) {   
		        StringBuffer hexString = new StringBuffer();   
		        for (int i = 0; i < b.length; i++) {   
		            String plainText = Integer.toHexString(0xff & b[i]);   
		            if (plainText.length() < 2)   
		                plainText = "0" + plainText;   
		            hexString.append(plainText);   
		        }   
		           
		        return hexString.toString();   
		    }  
		
		   
		   //加密数据
		    public static byte[] encrypt(String message, String key)   
		            throws Exception {   
		        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
		  
		        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));   
		  
		        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");   
		        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);   
		        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));   
		        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);   
		  
		        return cipher.doFinal(message.getBytes("UTF-8"));   
		    } 
		
		
	   //获取MAC地址的方法  
	   private static String getMACAddress(InetAddress ia)throws Exception{  
	       //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
	       byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();  
	         
	       //下面代码是把mac地址拼装成String  
	       StringBuffer sb = new StringBuffer();  
	         
	       for(int i=0;i<mac.length;i++){  
	           if(i!=0){  
	               sb.append(":");  
	           }  
	           //mac[i] & 0xFF 是为了把byte转化为正整数  
	           String s = Integer.toHexString(mac[i] & 0xFF);  
	          // System.out.println("--------------");
	          // System.err.println(s);
	           
	           sb.append(s.length()==1?0+s:s);  
	       }  
	         
	       //把字符串所有小写字母改为大写成为正规的mac地址并返回  
	       return sb.toString().toUpperCase();  
	   } 

}
