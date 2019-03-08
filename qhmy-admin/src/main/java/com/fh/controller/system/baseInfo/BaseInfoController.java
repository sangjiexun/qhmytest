package com.fh.controller.system.baseInfo;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
import com.fh.entity.system.User;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;

/**
 * 
 * <p>标题:BaseInfoController</p>
 * <p>描述:基本配置信息</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 王宁
 * @date 2017年10月19日 上午11:03:07
 */
@Controller
@RequestMapping(value="/baseInfo")
public class BaseInfoController extends BaseController {
	@Resource(name="stuInfoService")
	private StuInfoManager  stuInfoService;
	/**
	 * 
	 * <p>描述:跳转到报表统计导航页面</p>
	 * @author wn 王宁
	 * @date 2017年8月14日 上午11:37:06
	 * @return
	 */
	@RequestMapping(value="/baseCon.php")
	public ModelAndView reportStat() throws Exception{
		ModelAndView mv = this.getModelAndView();
		/*
		 * 获取当前用户
		 */
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String username = user.getUSERNAME();
		//end 获取当前用户
		
		/*
		 * 获取当前用户权限
		 */
		List<Menu> allmenuList = (List<Menu>) session.getAttribute(username + Const.SESSION_allmenuList);
		List<Menu> authorityManageMenus = null;
		for (Menu menu : allmenuList) {
			if ("27".equals(menu.getMENU_ID())) {//基础配置   是 27
				//权限菜单
				authorityManageMenus = menu.getSubMenu();
			}
		}
		mv.addObject("menuList", authorityManageMenus);
		//end 获取当前用户权限
		mv.setViewName("system/baseInfo/baseInfo");
		return mv;
	}
}