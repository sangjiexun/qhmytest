package com.fh.controller.system.shezhi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.shezhi.SheZhiManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明：组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/shezhi")
public class SheZhiController extends BaseController {
	
	@Resource(name="shezhiService")
	private SheZhiManager SheZhiService;
	
	
	
	
	@RequestMapping(value="/shezhi.json")
	public ModelAndView controllist(Page page) throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String depid=user.getDEPARTMENT_ID();
		pd.put("GAOXIAO_PKID", depid);
		PageData qygz = SheZhiService.getOne(pd);
		mv.setViewName("system/shezhi/shezhi");
		mv.addObject("listjw", qygz);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/setjwtbkq.json")
	public ModelAndView setdrkq() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String depid=user.getDEPARTMENT_ID();
		pd.put("GAOXIAO_PKID", depid);
		pd.put("PKID", this.get32UUID());
		PageData qygz = SheZhiService.getOne(pd);
		if(qygz==null){
			SheZhiService.savetb(pd);
		}else{
			SheZhiService.updatetb(pd);
		}
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	@ResponseBody
	@RequestMapping(value="/setipdz.json")
	public ModelAndView setipdz() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String depid=user.getDEPARTMENT_ID();
		pd.put("GAOXIAO_PKID", depid);
		pd.put("PKID", this.get32UUID());
		PageData qygz = SheZhiService.getOne(pd);
		if(qygz==null){
			SheZhiService.savetb(pd);
		}else{
			SheZhiService.updatetb(pd);
		}
		return new ModelAndView(new MappingJackson2JsonView(),map);

	}
	
	
	
}
