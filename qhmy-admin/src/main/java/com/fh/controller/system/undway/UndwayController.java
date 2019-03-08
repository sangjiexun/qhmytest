package com.fh.controller.system.undway;

import java.util.HashMap;
import java.util.List;
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
import com.fh.service.system.shezhi.impl.SheZhiService;
import com.fh.service.system.undway.UndwayManager;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:UndwayController</p>
 * <p>描述:了解途径管理</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author wn 王宁
 * @date 2018年11月27日 下午2:30:50
 */
@Controller
@RequestMapping(value="/undway")
public class UndwayController extends BaseController {
	
	@Resource(name="undwayService")
	private UndwayManager undwayService;
	
	/**
	 * 
	 * <p>描述:跳转到了解途径页面</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:14:54
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toUndway.php")
	public ModelAndView controllist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/undway/undwaylist");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 
	 * <p>描述:了解途径列表</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:15:17
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/undwayTable.json")
	public ModelAndView cengciTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = undwayService.undwaylist(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:去新增页面</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:32:49
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit.json")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		PageData pd_undway = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_undway = undwayService.getUndway(pd);
		}
		mv.addObject("pd_undway", pd_undway);
		mv.addObject("pd", pd);
		mv.setViewName("system/undway/undway_edit");
		return mv;
	}
	/**
	 * 
	 * <p>描述:新增编辑保存</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:44:15
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit.json")
	public ModelAndView edit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		//判断家庭关系名称是否重复
		PageData pd_undway = undwayService.getUndwaybyName(pd);
		String PKID = pd.getString("PKID");
		if(pd_undway!=null && !pd_undway.getString("DICTIONARIES_ID").equals(PKID)){
			map.put("rst", "error");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			undwayService.update(pd);
		}else{//表示新增
			undwayService.save(pd);
		}
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午3:58:16
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		undwayService.updateIsUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:删除操作</p>
	 * @author wn 王宁
	 * @date 2018年11月27日 下午4:04:33
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询是否已被学生使用
		PageData pd_stu = undwayService.getStuUndway(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_stu!=null){//已被使用
			map.put("rst", "error");
		}else{
			undwayService.delete(pd);
			map.put("rst", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
}
