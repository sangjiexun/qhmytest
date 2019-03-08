package com.fh.controller.system.checkIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.checkIn.CheckInManager;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;

/**
 * 
 * <p>
 * 标题:CheckInController
 * </p>
 * <p>
 * 描述:去入住登记页面
 * </p>
 * <p>
 * 组织:河北科曼信息技术有限公司
 * </p>
 * 
 * @author Administrator 任笑达
 * @date 2018年12月4日 上午9:40:08
 */
@Controller
@RequestMapping("/checkIn")
public class CheckInController extends BaseController {

	@Autowired
	private CheckInManager checkInService;

	/**
	 * 
	 * <p>
	 * 描述:去入住登记页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月4日 上午9:44:31
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkin.php")
	public ModelAndView checkin() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("system/check/check_in");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:去学生列表页面
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月4日 下午1:55:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStuInFo.json")
	public ModelAndView goStuInFo() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		ModelAndView mv = new ModelAndView();
		mv.addObject("inputValue", pd.getString("inputValue"));
		mv.setViewName("system/check/check_in_stuInfo");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * 描述:获取分班表数据
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月3日 上午9:49:03
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/stuAssignedClassTable.json")
	public ModelAndView assignedClassTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd = UtfZhuanMa.zhuanMa(pd);// 中文转码
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;
		list = this.checkInService.stulist(page);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	/**
	 * 
	 * <p>
	 * 描述:更新学生入学状态方法
	 * </p>
	 * 
	 * @author Administrator 任笑达
	 * @date 2018年12月5日 上午9:12:29
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateRz.json")
	public ModelAndView updateRz() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String PKID = pd.getString("PKID");
		if (!"null".equals(PKID) && !"".equals(PKID)) {
			this.checkInService.updateRz(pd);
			map.put("res", "SUCCESS");
		} else {
			map.put("res", "ERROR");
		}

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

}
