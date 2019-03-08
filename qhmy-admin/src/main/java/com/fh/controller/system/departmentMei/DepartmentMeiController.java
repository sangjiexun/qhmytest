package com.fh.controller.system.departmentMei;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.departmentMei.DepartmentMeiManager;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:DepartmentMeiController
 * </p>
 * <p>
 * 描述:基础配置-美院
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月26日 上午10:17:12
 */
@RequestMapping("/departmentMei")
@Controller
public class DepartmentMeiController extends BaseController {

	@Resource(name = "DepartmentMeiService")
	private DepartmentMeiManager departmentMeiService;

	/**
	 * 
	 * <p>
	 * 描述:去 基础配置-美院 页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午10:18:15
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dep_list.php")
	public ModelAndView dep_list() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/dep_mei/meiDepIndex");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:去新增或修改页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 下午1:37:19
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit.json")
	public ModelAndView goEdit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView mv = new ModelAndView();
		PageData dep = null;
		String PKID = pd.getString("PKID");
		if (!"".equals(PKID) && !"null".equals(PKID)) {
			dep = this.departmentMeiService.depListById(pd);
		}

		mv.addObject("dep", dep);
		mv.setViewName("system/dep_mei/meiDepEdit");

		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:增加/修改 通用方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午11:35:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insert.json")
	public ModelAndView insert() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		boolean bol = false;
		bol = this.departmentMeiService.getMeiDepByName(pd);

		if (pd.getString("CFNAME").equals(pd.getString("NAME"))) {
			bol = true;
		}
		if (bol) {
			if (!"".equals(PKID) && !"null".equals(PKID)) {
				this.departmentMeiService.update(pd);
				map.put("result", "SUCCESS");

			} else {
				this.departmentMeiService.save(pd);
				map.put("result", "SUCCESS");
			}
		} else {
			map.put("result", "CHOGNFU");
		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:删除方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午11:42:41
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		map = this.departmentMeiService.delete(pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:更改是否启用
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午11:42:41
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/update_isqy.json")
	public ModelAndView update_isqy() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		this.departmentMeiService.update_isqy(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "SUCCESS");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:获取美院
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月26日 上午11:31:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list.json")
	public ModelAndView list(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> pdlist = this.departmentMeiService.depList(page);
		map.put("rows", pdlist);
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

}
