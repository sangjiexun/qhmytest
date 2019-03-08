package com.fh.controller.system.project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.Project;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.getDictionnaries;
import com.fh.util.DictZiDianSelect.DicLeiXingEnum;
import com.fh.util.Jurisdiction;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.project.projectManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.seq.SeqManager;

/** 
 * 说明：组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/project")
public class ProjectController extends BaseController {
	
	String menuUrl = "project/list.do"; //菜单地址(权限用)
	@Resource(name="projectService")
	private projectManager projectService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource
	private getDictionnaries dicZiDian;
	@Resource(name="seqService")
	private SeqManager seqService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String CJR=null;
		if(user!=null){
			CJR =user.getUSER_ID();
		}
		pd.put("CJR", CJR);
		pd.put("ID", this.get32UUID());	//主键
		pd.put("FBID", this.get32UUID());	//FB主键
		projectService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
			pd = projectService.findById(pd);	//根据ID读取
			mv.addObject("pd", pd);					//放入视图容器
			pd.put("DEPARTMENT_ID",pd.get("PARENT_ID").toString());			//用作上级信息
			mv.addObject("pds",projectService.findById(pd));				//传入上级所有信息
			mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
			pd.put("DEPARTMENT_ID",DEPARTMENT_ID);							//复原本ID
			mv.setViewName("system/project/project_edit");
			mv.addObject("msg", "edit");
			return mv;
		}	
		
		
		 /**去详情页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/gomessage")
		public ModelAndView gomessage()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			PageData pdm = new PageData();
			pd = this.getPageData();
			pdm = projectService.listmessage(pd);	//根据ID读取
			mv.addObject("pd", pd);					//放入视图容器
			mv.setViewName("system/project/project_message");
			mv.addObject("pdm", pdm);
			return mv;
		}	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(DicLeiXingEnum dicLeixingEnum)throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		/*ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
		//projectService.edit(pd);		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);			//传入ID，作为子级ID用
		mv.setViewName("save_result");
		mv.addObject("msg", "success");
		return mv;
		*/
		
		
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String  aString=dicLeixingEnum.getValueByName("项目类型").toString();
		List<Dictionaries> gdcljffs=	dicZiDian.getLinkList(dicLeixingEnum.getValueByName("项目类型").toString());
		String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		String xmbm= seqService.getNextSeqBySeqName_ID(seqService.SEQ_XIANGMU, DEPARTMENT_ID);
		mv.addObject("xmbm", xmbm);	
		String  BIANMA=pd.get("BIANMA").toString();
		pd.put("DEPARTMENT_ID", DEPARTMENT_ID);					//上级ID
	//	pd = projectService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		PageData pdd = new PageData();
		pdd=projectService.findById(pd);
		mv.addObject("pds",pdd);				//传入上级所有信息
		/*mv.addObject("DEPARTMENT_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DEPARTMENT_ID",DEPARTMENT_ID);							//复原本ID
*/		mv.addObject("DEPARTMENT_ID", DEPARTMENT_ID);
		mv.addObject("BIANMA", BIANMA);
		mv.setViewName("system/project/project_edit");
		mv.addObject("msg", "save");
		return mv;
	}	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		projectService.edit(pd);
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
		//String DEPARTMENT_ID = null == pd.get("DEPARTMENT_ID")?"":pd.get("DEPARTMENT_ID").toString();
		
		String DEPARTMENT_ID="";
		String BIANMA = "";
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			BIANMA = pd.get("BIANMA").toString();
		}
		if(null == pd.get("DEPARTMENT_ID") || "".equals(pd.get("DEPARTMENT_ID").toString())){
			pd.put("DEPARTMENT_ID", "0");
		}
		
		page.setPd(pd);
		List<PageData>	varList = projectService.list(page);	//列出Dictionaries列表
		
		//定义一个集合，根据获取的DEPARTMENT_TYPE_ID，通过SYS_DEPARTMENT_TYPE表获取它的名字
					List<Project> pj=projectService.typeName();
					mv.addObject("pj", pj);
		
		
		mv.addObject("pd", projectService.findById(pd));		//传入上级所有信息
		mv.addObject("DEPARTMENT_ID", pd.getString("DEPARTMENT_ID"));			//上级ID
		mv.addObject("BIANMA", BIANMA);							//编码
		mv.setViewName("system/project/project_list");
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
		PageData pdd = new PageData();
		pd = this.getPageData();
		List<Department>  department=projectService.listAllDepartment("0");
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
		pdd.put("ROLE_ID", roleid);
		List<PageData> pd1=(List<PageData>) roleService.findByRoleId(pdd);
		List<PageData> pd2=(List<PageData>) roleService.findByRoleId(pdd);
		
		if(!"admin".equals(username)){
			
			if("0".equals(departmentid1)){
				List<Department> ld1=new ArrayList<Department>();
				if(department.size()!=0){
					for(int i=0;i<department.size();i++){
						
						String ld=department.get(i).getDEPARTMENT_ID();
						
						if(pd1.size()!=0){
							
							
							for(int j=0; j<pd1.size();j++){
								String depid=pd1.get(j).getString("DEPID");
								if(depid.equals(ld)){
									ld1.add(department.get(i));
									pd2.remove(pd1.get(j));
								}
							}
							
						}
						
						
					}
					
					if(ld1.size()!=0){
						for(int t=0;t<ld1.size();t++){
							List<Department> depidxiangmu =ld1.get(t).getSubDepartment();
							List<Department>  department2=new ArrayList();
							if(depidxiangmu.size()!=0){
								
								for(int u=0;u<depidxiangmu.size();u++){
									
									String xiangmuid1  =depidxiangmu.get(u).getDEPARTMENT_ID();
									if(pd2.size()!=0){
										for(int w=0; w<pd2.size();w++){
											
											String depid1=pd2.get(w).getString("DEPID");
											if(depid1.equals(xiangmuid1)){
												department2.add(depidxiangmu.get(u));
											}
											
										}
										
										
										
									}
									
									
								}
							}
							ld1.get(t).setSubDepartment(null);
							ld1.get(t).setSubDepartment(department2);
							
							
						}
						
						
						
					}
					
					
					
					department = null;
					department = ld1;
					
				}
				
			}
			else{
			if(department.size()!=0){
				
				for(int i=0;i<department.size();i++){
					
					String departmentid= department.get(i).getDEPARTMENT_ID();
					if(!departmentid1.equals(departmentid)){
						department.remove(i);
						
						
					}else{
						
						
						if(pd1.size()!=0){
							List<Department> ld1=new ArrayList<Department>();
							for(int j=0; j<pd1.size();j++){
								String depid=pd1.get(j).getString("DEPID");
							List<Department> ld=department.get(i).getSubDepartment();
							
							
							if(ld.size()!=0){
							
								
							for(int k=0;k<ld.size();k++){
								
								String xiangmuid=ld.get(k).getDEPARTMENT_ID();
								
										
										if(depid.equals(xiangmuid)){
											
											ld1.add(ld.get(k));
										}
										
									
									}
							}else{
								department.get(i).setSubDepartment(null);
							}
							
							
							}
							department.get(i).setSubDepartment(null);
							department.get(i).setSubDepartment(ld1);
						
						}else{
							department.get(i).setSubDepartment(null);
							
						}
					}
					
				}
			}
		}
		}
		try{
			JSONArray arr = JSONArray.fromObject(department);
			String json = arr.toString();
			json = json.replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "nodes").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DEPARTMENT_ID",DEPARTMENT_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/project/project_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
			if(projectService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
