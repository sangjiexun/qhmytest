package com.fh.controller.system.learningSubjects;

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
import com.fh.service.system.learningSubjects.LearningSubjectsManager;
import com.fh.util.PageData;

/**
 * 
 * <p>
 * 标题:LearningSubjectsController
 * </p>
 * <p>
 * 描述:学习科目
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年11月27日 上午9:37:04
 */
@Controller
@RequestMapping("/learningSub")
public class LearningSubjectsController extends BaseController {

	@Resource(name = "LearningSubjectsService")
	private LearningSubjectsManager LearningSubjectsService;

	/**
	 * 
	 * <p>
	 * 描述:去学习科目页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:37:32
	 * @return
	 */
	@RequestMapping("/sub_list.php")
	public ModelAndView sub_list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/lea_sub/LeaSubIndex");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:增加方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:42:13
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAndUpdate.json")
	@ResponseBody
	public ModelAndView save() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		boolean bol = false;
		bol = this.LearningSubjectsService.getSubByName(pd);
		if (pd.getString("NAME").equals(pd.getString("CFNAME"))) {// 名称没有更新
			bol = true;
		}

		if (bol) {
			if (!"".equals(PKID) && !"null".equals(PKID)) {// 执行更新方法
				this.LearningSubjectsService.update(pd);
			} else {// 执行增加方法
				pd.put("PKID", this.get32UUID());
				this.LearningSubjectsService.save(pd);
			}
			map.put("result", "SUCCESS");
		} else {
			map.put("result", "CHOGNFU");

		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:删除
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:48:26
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/del.json")
	@ResponseBody
	public ModelAndView del() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		map = this.LearningSubjectsService.del(pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:更新是否启用
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 下午2:02:10
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateIsQy.json")
	@ResponseBody
	public ModelAndView updateIsQy() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		this.LearningSubjectsService.updateForIsqy(pd);
		map.put("result", "SUCCESS");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:去更新或修改页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 上午9:53:01
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData sub = null;
		sub = this.LearningSubjectsService.getSubById(pd);
		mv.addObject("sub", sub);
		mv.setViewName("system/lea_sub/subEdit");

		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取学习科目
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年11月27日 下午1:49:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.json")
	@ResponseBody
	public ModelAndView list(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = this.LearningSubjectsService.sublist(page);
		map.put("rows", list);
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

}
