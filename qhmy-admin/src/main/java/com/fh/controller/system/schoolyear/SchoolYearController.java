package com.fh.controller.system.schoolyear;

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
import com.fh.service.system.schoolYear.SchoolYearManager;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/schoolYear")
public class SchoolYearController extends BaseController{
	
	@Resource(name="schoolYearService")
	private SchoolYearManager schoolYearService;
	
	/**
	 * 
	 * <p>描述:跳转到入学年份页面</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:13:12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toSchoolYear.php")
	public ModelAndView controllist() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/schoolYear/schoolYearList");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 
	 * <p>描述:入学年份列表</p>
	 * @author 柴飞
	 * @date 2018年11月28日15:13:51
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/schoolYearTable.json")
	public ModelAndView cengciTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = schoolYearService.schoolYearlist(page);
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
	 * @author 柴飞
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
		PageData pd_schoolYear = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_schoolYear = schoolYearService.getSchoolYearById(pd);
		}
		mv.addObject("pd_schoolYear", pd_schoolYear);
		mv.addObject("pd", pd);
		mv.setViewName("system/schoolYear/schoolYear_edit");
		return mv;
	}
	/**
	 * 
	 * <p>描述:新增编辑保存</p>
	 * @author 柴飞
	 * @date 2018年11月27日 下午3:44:15
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit.json")
	public ModelAndView edit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData sy = schoolYearService.getSchoolYearByName(pd);
		if(sy == null){//名称可用
			if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
				schoolYearService.update(pd);
			}else{//表示新增
				schoolYearService.save(pd);
			}
			map.put("rst", "success");
		}else{
			map.put("rst", "fail");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author 柴飞
	 * @date 2018年11月27日 下午3:58:16
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		schoolYearService.updateIsUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:删除操作</p>
	 * @author 柴飞
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
		PageData pd_stu = schoolYearService.getStuSchoolYear(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_stu!=null){//已被使用
			map.put("rst", "error");
		}else{
			schoolYearService.delete(pd);
			map.put("rst", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
}
