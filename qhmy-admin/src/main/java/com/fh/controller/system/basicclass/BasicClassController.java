package com.fh.controller.system.basicclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.entity.Page;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.basicclass.BasicClassManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.UuidUtil;

@Controller
@RequestMapping(value="/basicClass")
public class BasicClassController extends BaseController{
	
	@Resource(name="basicclassService")
	private BasicClassManager basicclassService;
	
	/**
	 * 
	 * <p>描述:班级列表页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月26日 上午11:57:23
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classlist.php")
	public ModelAndView classlist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//入学年份list
		pd.put("PARENT_ID", "GRADE");
		List<PageData> gradeList = basicclassService.getFromSYS_DICT(pd);
		//班型list
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> banXingList = basicclassService.getFromSYS_DICT(pd);
		mv.addObject("gradeList", gradeList);
		mv.addObject("banXingList", banXingList);
		mv.addObject("pd", pd);
		mv.setViewName("system/basicclass/basicclass");
		return mv;
	}
	
	/**
	 * 班级列表
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年11月26日 下午4:25:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/basicClassTable.json")
	public ModelAndView basicClassTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;		
		list = basicclassService.basicclass_listPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:跳转到班级编辑新增页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午1:48:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit.json")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		PageData pd_class = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_class = basicclassService.getClassByPkid(pd);
		}
		//入学年份list
		pd.put("PARENT_ID", "GRADE");
		List<PageData> gradeList = basicclassService.getFromSYS_DICT(pd);
		//班型list
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> banXingList = basicclassService.getFromSYS_DICT(pd);
		mv.addObject("pd_class", pd_class);
		mv.addObject("gradeList", gradeList);
		mv.addObject("banXingList", banXingList);
		mv.addObject("pd", pd);
		mv.setViewName("system/basicclass/class_edit");
		return mv;
	}
	/**
	 * 
	 * <p>描述:班级新增和编辑</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit.json")
	public ModelAndView edit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		try {
			if(!"".equals(pd.getString("PKID"))&&pd.getString("PKID")!=null){
				if(pd.getString("CLASS_NAME").equals(pd.getString("CLASS_NAME_HIDDEN"))&&
						pd.getString("SYS_DICTIONARIES_PKID").equals(pd.getString("SYS_DICTIONARIES_PKID_HIDDEN"))&&
						pd.getString("BANXING_PKID").equals(pd.getString("BANXING_PKID_HIDDEN"))){
					//表示修改
					basicclassService.updateClassByPkid(pd);
					map.put("rst", "success");
				}else{
					//查询该班级信息是否已被使用
					PageData pd_classUse = basicclassService.getClassByGradeBxNm(pd);
					if(pd_classUse!=null){
						map.put("rst", "isExist");
					}else{
						basicclassService.updateClassByPkid(pd);
						map.put("rst", "success");
					}
				}
			}else{
				//查询该班级信息是否已被使用
				PageData pd_classUse = basicclassService.getClassByGradeBxNm(pd);
				//表示新增
				if(pd_classUse!=null){
					map.put("rst", "isExist");
				}else{
					basicclassService.saveBasicClass(pd);
					map.put("rst", "success");
				}
			}
		} catch (Exception e) {
			map.put("rst", "fail");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:删除班级</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:49:20
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete.json")
	public ModelAndView delete() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();		
		try {
			//查询该班级信息是否已被使用
			PageData pd_classUse = basicclassService.getClassUse(pd);
			if(pd_classUse!=null){//已被使用
				map.put("rst", "error");
			}else{
				basicclassService.deleteClassByPkid(pd);
				map.put("rst", "success");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:班型列表页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月26日 上午11:57:23
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/classtypelist.php")
	public ModelAndView classtypelist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//班型list
		pd.put("PARENT_ID", "CLASSTYPE");
		List<PageData> banXingList = basicclassService.getFromSYS_DICT(pd);
		mv.addObject("banXingList", banXingList);
		mv.addObject("pd", pd);
		mv.setViewName("system/basicclass/classtype_list");
		return mv;
	}
	
	/**
	 * 班型列表
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年11月26日 下午4:25:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/classtypeTable.json")
	public ModelAndView classtypeTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;		
		list = basicclassService.classtype_listPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("rows", list);
		map.put("total", page.getTotalResult());
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	/**
	 * 
	 * <p>描述:跳转到班型编辑新增页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午1:48:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goClassTypeEdit.json")
	public ModelAndView goClassTypeEdit() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		PageData pd_class = null;
		if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
			pd_class = basicclassService.getclasstypeByPkid(pd);
		}
		mv.addObject("pd_class", pd_class);
		mv.addObject("pd", pd);
		mv.setViewName("system/basicclass/classtype_edit");
		return mv;
	}
	/**
	 * 
	 * <p>描述:班型新增和编辑</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/editClassType.json")
	public ModelAndView editClassType() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		pd.put("BIANMA", pd.getString("BIANMA").trim().toUpperCase());
		try {
			if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
				PageData pddname = basicclassService.getclasstypeNames(pd);
				if(pddname != null){
					map.put("rst", "relname");
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				
				PageData pdd = basicclassService.getclasstypeBIANMAs(pd);
				if(pdd != null){
					map.put("rst", "relbianma");
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				
				basicclassService.updateclasstypeByPkid(pd);
				map.put("rst", "success");
				
			}else{//表示新增
				PageData pddname = basicclassService.getclasstypeName(pd);
				if(pddname != null){
					map.put("rst", "relname");
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				
				PageData pdd = basicclassService.getclasstypeBIANMA(pd);
				if(pdd != null){
					map.put("rst", "relbianma");
					return new ModelAndView(new MappingJackson2JsonView(), map);
				}
				pd.put("PKID", UuidUtil.get32UUID());
				basicclassService.saveclasstype(pd);
				map.put("rst", "success");
				
				
			}
			
		} catch (Exception e) {
			map.put("rst", "fail");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:删除班型</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:49:20
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteClassType.json")
	public ModelAndView deleteClassType() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();		
		try {
			PageData classPd = basicclassService.getClassCount(pd);
			if("0".equals(classPd.getString("CCOUNT"))){
				basicclassService.deleteclasstypeByPkid(pd);
				map.put("rst", "success");
			}else{
				map.put("rst", "rel");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	
	/**
	 * 
	 * <p>描述:班型编辑</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateclasstypeIS_USED.json")
	public ModelAndView updateclasstypeIS_USED() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		try {
			if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
				basicclassService.updateclasstypeIS_USED(pd);
			}
			map.put("rst", "success");
		} catch (Exception e) {
			map.put("rst", "fail");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:班型编辑</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateclasstypeSFQY.json")
	public ModelAndView updateclasstypeSFQY() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKID = pd.getString("PKID");
		try {
			if(!"".equals(PKID) && !"null".equals(PKID)){//表示是编辑页面
				basicclassService.updateclasstypeSFQY(pd);
			}
			map.put("rst", "success");
		} catch (Exception e) {
			map.put("rst", "fail");
			e.printStackTrace();
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * 
	 * <p>描述:更新启用状态</p>
	 * @author Administrator wzz
	 * @date 2017年10月20日 下午3:41:34
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsUse.json")
	public ModelAndView updateIsUse() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		basicclassService.updateIsUse(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
}
