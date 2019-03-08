package com.fh.controller.system.partschool;

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
import com.fh.service.system.partschool.PartSchoolManager;
import com.fh.util.ObjectExcelView2;
import com.fh.util.PageData;
import com.fh.util.UtfZhuanMa;
import com.fh.util.Utils;

@Controller
@RequestMapping(value="/partSchool")
public class PartSchoolController extends BaseController{
	
	@Resource(name="partSchoolService")
	private PartSchoolManager partSchoolService;
	
	/**
	 * 
	 * <p>描述:合作学校页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 上午11:57:23
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/partschoollist.php")
	public ModelAndView partschoollist() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("system/partschool/partschoollist");
		return mv;
	}
	
	/**
	 * 合作学校列表
	 * <p>描述:</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午4:25:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/partschoolTable.json")
	public ModelAndView partschoolTable(Page page)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		int limit = Integer.parseInt(pd.getString("limit"));
		int pageindex = Integer.parseInt(pd.getString("pageindex"));
		page.setShowCount(limit);
		page.setCurrentPage(pageindex);
		page.setPd(pd);
		List<PageData> list = null;		
		list = partSchoolService.partschool_listPage(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNumber", page.getCurrentPage());// 当前第几页
		map.put("total", page.getTotalResult());// 总记录数
		map.put("rows", list);
		map.put("pd", pd);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	
	/**
	 * 
	 * <p>描述:跳转到新增页面</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午1:48:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd.json")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdResult = partSchoolService.getPartSchoolByPkid(pd);
		mv.addObject("pdResult", pdResult);
		mv.addObject("pd", pd);
		mv.setViewName("system/partschool/partschool_add");
		return mv;
	}
	
	
	/**
	 * 
	 * <p>描述:合作学校新增</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午3:16:23
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/add.json")
	public ModelAndView add() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if(!"".equals(pd.getString("PKID"))&&pd.getString("PKID")!=null){
				if(pd.getString("SCHOOLNAME").equals(pd.getString("SchoolNameHidden"))){
					//表示修改
					partSchoolService.updatePartSchoolByPkid(pd);
					map.put("rst", "success");
				}else{
					//查询该班级信息是否已被使用
					PageData pd_classUse = partSchoolService.getPartSchoolByName(pd);
					if(pd_classUse!=null){
						map.put("rst", "exist");
					}else{
						partSchoolService.updatePartSchoolByPkid(pd);
						map.put("rst", "success");
					}
				}
			}else{
				//查询该班级信息是否已被使用
				PageData pd_classUse = partSchoolService.getPartSchoolByName(pd);
				if(pd_classUse!=null){
					map.put("rst", "exist");
				}else{
					partSchoolService.updatePartSchoolByPkid(pd);
					//表示新增
					partSchoolService.savePartSchool(pd);
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
	 * <p>描述:删除合作学校</p>
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
			List<PageData> pdIsStu = partSchoolService.getStuinfoByPkid(pd);
			if(pdIsStu!=null&&pdIsStu.size()>0){
				map.put("rst", "isExist");
			}else{
				partSchoolService.deletePartSchoolByPkid(pd);
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
	 * <p>描述:批量删除合作学校</p>
	 * @author Administrator wzz
	 * @date 2018年11月27日 下午4:01:15
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDel")
	@ResponseBody
	public ModelAndView batchdelete() throws Exception {
		Map<String, Object> jsonmap = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PKIDS = pd.getString("pkids");
		if(null != PKIDS && !"".equals(PKIDS)){
			String pkid_arr[] = PKIDS.split(",");
			List<PageData> pdResults = partSchoolService.getStuinfoByPkids(pkid_arr);
			if(pdResults!=null&&pdResults.size()>0){
				jsonmap.put("result", "isExist");
			}else{
				partSchoolService.batchDelete(pkid_arr);
				jsonmap.put("result", "success");
			}
			
		}else{
			jsonmap.put("result", "fail");
		}
		
		return new ModelAndView(new MappingJackson2JsonView(),jsonmap);
	}
	
	/**
	 * 
	 * <p>描述:更新是否合作学校状态</p>
	 * @author wzz
	 * @date 2019年1月23日 下午3:58:16
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateIsHezuo.json")
	public ModelAndView updateIsHezuo() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		partSchoolService.updateIsHezuo(pd);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rst", "success");
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}
	
	/**
	 * <p> 描述: 导出缴费名单</p>
	 * @author Administrator wzz
	 * @date 2019年1月23日 9:11:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel.json")
	public ModelAndView exportExcel(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd = UtfZhuanMa.zhuanMa(pd);
		String[] headerNames = new String[] { "文化课学校","定金", "是否合作学校"};
		String[] bodyNames = new String[] { "SCHOOLNAME", "DINGJIN","ISHEZUO"};

		List<PageData> pdList = partSchoolService.exportPartSchoolList(pd);
		Map<String, Object> dataMap = Utils.toExcel(headerNames, bodyNames,
				pdList);
		dataMap.put("fileName", "文化课学校");
		ObjectExcelView2 erv = new ObjectExcelView2();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
