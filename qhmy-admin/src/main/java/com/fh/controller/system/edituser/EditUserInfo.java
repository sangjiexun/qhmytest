package com.fh.controller.system.edituser;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.login.LoginController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.edituser.impl.EditUserService;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.keman.zhgd.common.MD5;
import com.keman.zhgd.common.datetime.DateUtil;

@Controller
@RequestMapping(value="/edit")
public class EditUserInfo extends BaseController{

	@Resource(name = "editUserService")
	private EditUserService editUserService;
	
	/**
	 * 去修改密码页面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditPwd.json")
	public ModelAndView goEditPwd(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd=gongzuozhanService.getbianma();
		mv.setViewName("system/user/pwd_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**保存新密码
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveNewPwd.json")
	@ResponseBody
	public Object saveNewPwd() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		//读取session中的密码跟用户输入的旧密码相比较
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER); 
		String userPwd=user.getPASSWORD();
		String inputOldPwd = new SimpleHash("SHA-1", user.getUSERNAME(), pd.getString("oldpwd")).toString();//密码加密
		//如果不一样的话，返回错误提示！
		if(!userPwd.equals(inputOldPwd)){
			map.put("result", "faild");
			return AppUtil.returnObject(pd, map);
		}
		//更新 密码
		 // 读取平台的数据库用户名
		Properties prop = new Properties();
		prop.load(LoginController.class.getClassLoader().getResourceAsStream(
				"conf.properties"));
		String tablename = prop.getProperty("bizusername");
		pd.put("tablename", tablename+".sys_user");
		String PASSWORD = new SimpleHash("SHA-1", user.getUSERNAME(), pd.getString("confirmpwd")).toString();//密码加密
		pd.put("USER_ID","'"+user.getUSER_ID()+"'");
		pd.put("PASSWORD","'"+PASSWORD+"'");
		editUserService.updatePwd(pd);
		map.put("result", "success");
		return AppUtil.returnObject(pd, map);
	}
}
