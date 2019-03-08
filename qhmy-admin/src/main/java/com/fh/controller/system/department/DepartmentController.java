package com.fh.controller.system.department;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.fh.util.Jurisdiction;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.seq.SeqManager;

/** 
 * 说明：组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/department")
public class DepartmentController extends BaseController {
	
	String menuUrl = "department/list.do"; //菜单地址(权限用)
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="seqService")
	private SeqManager seqService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增department");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String BIANMA ="";
		pd.put("DEPARTMENT_ID", this.get32UUID());	//主键
		String parent_id =pd.getString("PARENT_ID");
		 if("0".equals(parent_id)){
				 BIANMA=seqService.getNextSeqBySeqName(SeqManager.SEQ_ZONGGONGSI);
			}else {
				
				BIANMA=seqService.getNextSeqBySeqName_ID(SeqManager.SEQ_FENGONGSI, parent_id);
			}
		pd.put("BIANMA", BIANMA);
		
		
		
		
		
		departmentService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param DEPARTMENT_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String DEPARTMENT_ID) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除department");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		String errInfo = "success";
		if(departmentService.listSubDepartmentByParentId(DEPARTMENT_ID).size() > 0){//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			departmentService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改department");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		departmentService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/editActive")
	public ModelAndView editActive() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		departmentService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表department");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DEPARTMENT_ID = pd.get("id").toString();
		}
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = departmentService.list(page);	//列出Dictionaries列表	
		mv.addObject("pd", departmentService.findById(pd));		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//上级ID
		mv.setViewName("system/department/department_list");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());				//按钮权限
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDepartment")
	public ModelAndView listAllDepartment(Model model,String DEPARTMENT_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdd = new PageData();
		
		
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String roleid=null;
		String departmentid1 =null;
		String username=null;
		if(user!=null){
			
			 roleid =user.getROLE_ID();
			  departmentid1 = user.getDEPARTMENT_ID();
			  username =user.getUSERNAME();
			  
		}
		String parent_id =null;
		List<Department>  department =new ArrayList<Department>();
		if(departmentid1!=null){
			PageData pddd =new PageData();
			pddd.put("DEPARTMENT_ID", departmentid1);
			pddd=departmentService.findById(pddd);
			if(pddd!=null){
				parent_id =pddd.getString("PARENT_ID");
			
			}
		
			List<Department>  department1=departmentService.listAllDepartment(parent_id);
			if(department1.size()>0){
				for(int i =0;i<department1.size();i++){
					String depid=department1.get(i).getDEPARTMENT_ID();
					if(departmentid1.equals(depid)){
						department.add(department1.get(i));
					}
					
					
				}
			}
		
		}
		
		try{
			JSONArray arr = JSONArray.fromObject(department);
			String json = arr.toString();

			json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID",DEPARTMENT_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/department/department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		mv.addObject("pds",departmentService.findById(pd));//传入上级所有信息	
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);//传入ID，作为子级ID用
		
		//定义一个集合，根据获取的DEPARTMENT_TYPE_ID，通过SYS_DEPARTMENT_TYPE表获取它的名字
		List<Project> pj=departmentService.typeName();
        mv.addObject("pj", pj);
        List<PageData> province = departmentService.S_province(pd);
        mv.addObject("province", province);
    
		
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "save");
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = pd.getString("DEPARTMENT_ID");
		pd = departmentService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",departmentService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=departmentService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = departmentService.S_province(pd);
       mv.addObject("province", province);
       
       /*List<PageData> city = departmentService.S_city(pd);
       mv.addObject("city", city);*/
       
     /*  List<PageData> district = departmentService.S_district(pd);
       mv.addObject("district", district);*/
        
        
        
        
        
		mv.setViewName("system/department/department_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	
	@RequestMapping(value="/goActive")
	public ModelAndView goActive(HttpServletRequest req)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		HttpSession session =  req.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	
		String DEPARTMENT_ID =user.getDEPARTMENT_ID();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
		pd = departmentService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",departmentService.findById(pd));				//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);		//复原本ID
		
		List<Project> pj=departmentService.typeName();
        mv.addObject("pj", pj);
       List<PageData> province = departmentService.S_province(pd);
       mv.addObject("province", province);
       
       /*List<PageData> city = departmentService.S_city(pd);
       mv.addObject("city", city);*/
       
     /*  List<PageData> district = departmentService.S_district(pd);
       mv.addObject("district", district);*/
        
		mv.setViewName("system/department/depactive");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	
	
	
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(departmentService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	@RequestMapping(value="/getPinyin")
	@ResponseBody
	public Object getPinyin(){
		Map<String,String> map = new HashMap<String,String>();
		String name ="";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			name=pd.getString("NAME");
			name=PinyinHelper.getShortPinyin(name);//返回结果
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	
		map.put("name", name);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**获取city信息
	 * @return
	 */
	@RequestMapping(value="/getDq")
	@ResponseBody
	public Object getDq(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=departmentService.S_district(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	@RequestMapping(value="/getCity")
	@ResponseBody
	public Object getCity(){
		List<PageData> data = new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			data=departmentService.S_city(pd);
				
			
		} catch(Exception e){
			logger.error(e.toString(), e);
		}			//返回结果
		return data;
	}
	
	/**获取DEPARTMENT_TYPE_ID=1的名称和DEPARTMENT_ID，用于角色管理的添加角色页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/departmenttypeid")
	@ResponseBody
	public Object departmenttypeid() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		String pd11 = null;
		try{
			pd = this.getPageData();
			List <PageData> pd1 = departmentService.findByTypeId(pd);
			
			
			ObjectMapper mapper = new ObjectMapper();
			pd11 = mapper.writeValueAsString(pd1);
			if(pd11 != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);//返回结果
		map.put("dp", pd11);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
