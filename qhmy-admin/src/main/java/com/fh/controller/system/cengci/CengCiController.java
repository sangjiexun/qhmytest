package com.fh.controller.system.cengci;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.cengci.CengciManager;
import com.fh.util.PageData;

/**
 * 
 * <p>标题:cengciController</p>
 * <p>描述:层次信息维护</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 王宁
 * @date 2017年10月19日 上午11:22:44
 */
@Controller
@RequestMapping(value="/cengci")
public class CengCiController extends BaseController {
	@Resource(name="cengciService")
	private CengciManager  cengciService;
	/**
	 * 
	 * <p>描述:层次列表页面</p>
	 * @author Administrator 王宁
	 * @date 2017年10月19日 上午11:23:48
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cengci_list.php")
	public ModelAndView jieshaoren_list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/cengci/cengci_list");
		return mv;
	}
	/**
	 * 
	 * <p>描述:层次表格数据列表</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 上午11:30:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cengciTable.json")
	public ModelAndView cengciTable(Page page) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("keyWord", pd.getString("keyWord").trim());
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);		
		page.setPd(pd);
		List<PageData> list = cengciService.cengci_list(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:跳转到编辑新增页面</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午1:48:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit.json")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		PageData pd_cengci = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_cengci = cengciService.getCengci(pd);
		}else{//表示新增页面，获取层次编码
			PageData pd_bianma = cengciService.getBianMa(pd);
			pd_cengci = new PageData();
			pd_cengci.put("CENGCI_BIANMA", pd_bianma.getString("CENGCI_BIANMA").trim());
		}
		mv.addObject("pd_cengci", pd_cengci);
		mv.addObject("pd", pd);
		mv.setViewName("system/cengci/cengci_edit");
		return mv;
	}
	/**
	 * 
	 * <p>描述:操作层次信息</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit.json")
	public ModelAndView edit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			cengciService.update(pd);
		}else{//表示新增
			cengciService.save(pd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:41:34
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		cengciService.updateIsUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:删除层次</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午3:49:20
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询该层次是否已被学生使用
		PageData pd_stuJie = cengciService.getStuJieShao(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_stuJie!=null){//已被使用
			map.put("rst", "error");
		}else{
			cengciService.delete(pd);
			map.put("rst", "success");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:查询身份证是否重复</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午4:10:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getCENGCI_NAME.json")
	public ModelAndView getCENGCI_NAME() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询该身份证是否已存在
		PageData pd_stuJie = cengciService.getCENGCI_NAME(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pd_stuJie!=null){
			if("".equals(pd.getString("PKID"))){//表示新增
				map.put("rst", "success");
			}else if(!pd_stuJie.getString("PKID").equals(pd.getString("PKID"))){//表示编辑
				map.put("rst", "success");
			}
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	
	
	/**
	 * 
	 * <p>描述:查询身份证是否重复</p>
	 * @author Administrator 王宁
	 * @date 2017年10月20日 下午4:10:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getCCDAIMA.json")
	public ModelAndView getCCDAIMA() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询该身份证是否已存在
		PageData pd_stuJie = cengciService.getCCDAIMA(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(pd_stuJie!=null){
			System.out.println("进来了 他不是null");
			if("".equals(pd.getString("PKID"))){
				map.put("rst","success");
			}else if(!pd_stuJie.get("PKID").equals(pd.getString("PKID"))){
				map.put("rst","success");
			}
		}
		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	
	
	
	
	
	
	
	
}