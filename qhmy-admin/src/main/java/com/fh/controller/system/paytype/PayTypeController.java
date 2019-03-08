package com.fh.controller.system.paytype;

import java.util.Arrays;
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
import com.fh.service.system.paytype.PayTypeManager;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.keman.zhgd.common.Tools;

/**
 * 
 * <p>标题:PayTypeController</p>
 * <p>描述:缴费类型</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 康程亮
 * @date 2018年1月16日 上午8:58:32
 */
@Controller
@RequestMapping(value="/paytype")
public class PayTypeController extends BaseController {
	
	@Resource(name="payTypeService")
	private PayTypeManager  payTypeService;
	
	
	/**
	 * 
	 * <p>描述:去列表页面</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:37:58
	 * @return
	 */
	@RequestMapping(value="/paytype_list.php")
	public ModelAndView paytype_list() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();	
		mv.setViewName("system/paytype/paytype_list");
		return mv;
	}
	
	/**
	 * 
	 * <p>描述:列表table</p>
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:38:28
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/table.json")
	public ModelAndView stutablejson(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = payTypeService.payTypelistPage(page);
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
	 * @author Administrator 康程亮
	 * @date 2018年1月16日 上午9:37:58
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/paytype_add.json")
	public ModelAndView paytype_add() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();	
		if(!Tools.isEmpty(pd.getString("pkid"))){
			pd = payTypeService.getPayTypeByPkid(pd);
		}
		mv.addObject("pd", pd);
		mv.setViewName("system/paytype/paytype_add");
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/save.json")
	public ModelAndView save() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = UtfZhuanMa.zhuanMas(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(!pd.getString("pay_style_name").equals(pd.getString("oldname"))){
			/* 名称不能重复  */
			List<PageData> payItemsList = payTypeService.getPayItemsByName(pd);
			if(payItemsList!=null && payItemsList.size()>0){
				for(PageData pdd : payItemsList){
					if(!pdd.getString("PKID").equals(pd.getString("pkid"))){
						map.put("result", "nameExisted");
						return new ModelAndView(new MappingJackson2JsonView(), map);
					}
				}
			}
		}
		if("edit".equals(pd.getString("edit"))){
			/* 编辑  */
			payTypeService.editPayType(pd);
		}else{
			/* 新增  */
			pd.put("pkid", this.get32UUID());
			payTypeService.addPayType(pd);
		}
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:修改登记领取字段</p>
	 * @author ning 王宁
	 * @date 2018年7月13日 上午11:42:13
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDJLQ.json")
	public ModelAndView updateDJLQ() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		payTypeService.updateDJLQ(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> payItemsList = payTypeService.getPayItems(pd);
		if(payItemsList!=null && payItemsList.size()>=1){
			map.put("result", "is_used");
			return new ModelAndView(new MappingJackson2JsonView(), map);
		}
		payTypeService.delPayType(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatepaixu.json")
	public ModelAndView updatepaixu() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		payTypeService.updatePaixu(pd);
		map.put("result", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
}





































