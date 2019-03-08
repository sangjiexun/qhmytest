package com.fh.controller.system.role;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Department;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.department.DepartmentManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.RightsHelper;
import com.keman.zhgd.common.util.Tools;
/** 
 * 类名称：RoleController 角色权限管理
 * 创建人：zhoudibo
 * 修改时间：2015年11月6日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	
	/** 进入权限首页
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
 			pd = this.getPageData();
		
			PageData fpd = new PageData();
			
			PageData pdro = new PageData();
			pdro.put("ROID", false);
			List<Role> roleList =new ArrayList<Role>();//列出角色组
			List<Role> roleList_z =new ArrayList<Role>();//列出角色组下的角色
			
			String rid=pd.getString("ROLE_ID") == null?"":pd.getString("ROLE_ID");//点击获取的角色id
			String depid=pd.getString("DEPARTMENT_ID") == null?"":pd.getString("DEPARTMENT_ID");//点击角色获取的部门id
			String department_id=pd.getString("id") == null?"":pd.getString("id");//点击树结构节点时获取的部门id
			
			//获取的登录用户信息
				Session session = Jurisdiction.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USER);
				PageData pdrole =new PageData();
				String roleid=null;
				String departmentid1 =null;
				String username=null;
				String user_id=null;
				if(user!=null){
					
					 roleid =user.getROLE_ID();
					  departmentid1 = user.getDEPARTMENT_ID();
					  username =user.getUSERNAME();
					  user_id =user.getUSER_ID();
				}
				
				
				pdrole.put("ROLE_ID", roleid);
				pdrole=roleService.findObjectById(pdrole);
				String rolepid =pdrole.getString("PARENT_ID");//获取登录用户的角色id的parentid
				
			
				
				PageData pduser =new PageData();
				pduser.put("USER_ID", user_id);
				List<PageData> users=roleService.findObjectByUserid(pduser);
				
				
				if("1".equals(roleid) || "1".equals(rolepid)){
					
					if("".equals(rid)){
						if("".equals(department_id)){
							
							fpd.put("ROLE_ID", "0");
							fpd.put("DEPARTMENT_ID", departmentid1);
							roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
							
							
							
							if(roleList.size()>0){
								
								String roleidd=roleList.get(0).getROLE_ID();
								pd.put("DEPARTMENT_ID", departmentid1);
								pd.put("ROLE_ID", roleidd);
								if("1".equals(roleidd)){
									pdro.put("ROID", true);
								}
								  roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
								  
								  
									
									if(roleList_z.size()>0){
										
										for(int j=0;j<roleList_z.size();j++){
											String roid=roleList_z.get(j).getROLE_ID();
									if(users!=null){
										
										for(int i=0;i<users.size();i++){
											String uid=users.get(i).getString("PARENT_ID");
											if(!"0".equals(uid)){
												String rrid =users.get(i).getString("ROLE_ID");
													if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
														roleList_z.get(j).setROID(true);
														
													}
												}
											}
										}
									  }
									}
								  
								  
								  
								  
								  
								  
								  
								  
							     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
							     	
							     	
							     	
							}
						}else{
							
							
							fpd.put("ROLE_ID", "0");
							fpd.put("DEPARTMENT_ID", department_id);
							roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
							
							
							
							pd.put("DEPARTMENT_ID", department_id);
							if(roleList.size()>0){
								
								String roleidd=roleList.get(0).getROLE_ID();
								if("1".equals(roleidd)){
									pdro.put("ROID", true);
								}
								pd.put("ROLE_ID", roleidd);
							
								  roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
								  
	if(roleList_z.size()>0){
										
										for(int j=0;j<roleList_z.size();j++){
											String roid=roleList_z.get(j).getROLE_ID();
									if(users!=null){
										
										for(int i=0;i<users.size();i++){
											String uid=users.get(i).getString("PARENT_ID");
											if(!"0".equals(uid)){
												String rrid =users.get(i).getString("ROLE_ID");
													if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
														roleList_z.get(j).setROID(true);
													
													}
												}
											}
										}
									  }
									}
								  
								  
								  
								  
								  
							     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
							
						}
						
						
						}
						
					}else{
						
						fpd.put("ROLE_ID", "0");
						fpd.put("DEPARTMENT_ID", depid);
						roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
					

					
							String roleidd=roleList.get(0).getROLE_ID();
							pd.put("DEPARTMENT_ID", depid);
							pd.put("ROLE_ID", rid);
							if("1".equals(rid) || "2".equals(rid)){
								pdro.put("ROID", true);
							}
							  roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
							  
								if(roleList_z.size()>0){
									
									for(int j=0;j<roleList_z.size();j++){
										String roid=roleList_z.get(j).getROLE_ID();
								if(users!=null){
									
									for(int i=0;i<users.size();i++){
										String uid=users.get(i).getString("PARENT_ID");
										if(!"0".equals(uid)){
											String rrid =users.get(i).getString("ROLE_ID");
												if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
													roleList_z.get(j).setROID(true);
													
												}
											}
										}
									}
								  }
								}
							  
							  
							  
							  
							  
							  
						     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
						
					
						
						
						
						
						
					}
					
					
					
					
				}else{
					//没有点击角色组
					if("".equals(rid)){
						//没有点击树结构
						if("".equals(department_id)){
							
							fpd.put("ROLE_ID", "0");
							fpd.put("DEPARTMENT_ID", departmentid1);
							roleList = roleService.listAllRolesByPId1(fpd);		//列出组(页面横向排列的一级组)

							
							if(roleList.size()>0){
								
								String roleidd=roleList.get(0).getROLE_ID();
								pd.put("DEPARTMENT_ID", departmentid1);
								pd.put("ROLE_ID", roleidd);
								
								if("2".equals(roleidd)){
									pdro.put("ROID", true);
									pd.put("DEPARTMENT_ID", "0");
								}
								  roleList_z = roleService.listAllRolesByPId11(pd);		//列出此组下架角色
								  
									if(roleList_z.size()>0){
										
										for(int j=0;j<roleList_z.size();j++){
											String roid=roleList_z.get(j).getROLE_ID();
									if(users!=null){
										
										for(int i=0;i<users.size();i++){
											String uid=users.get(i).getString("PARENT_ID");
											if(!"0".equals(uid)){
												String rrid =users.get(i).getString("ROLE_ID");
													if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
														roleList_z.get(j).setROID(true);
													}
												}
											}
										}
									  }
									}
								  
								  
								  
								  
								  
							     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
							     	
							     	pd.put("DEPARTMENT_ID", departmentid1);
							}
						}else{
							
							
							fpd.put("ROLE_ID", "0");
							fpd.put("DEPARTMENT_ID", department_id);
							roleList = roleService.listAllRolesByPId1(fpd);		//列出组(页面横向排列的一级组)
							
							
							pd.put("DEPARTMENT_ID", department_id);
							if(roleList.size()>0){
								
								String roleidd=roleList.get(0).getROLE_ID();
								
								pd.put("ROLE_ID", roleidd);
								if("2".equals(roleidd)){
									pdro.put("ROID", true);
									pd.put("DEPARTMENT_ID", "0");
								}
								  roleList_z = roleService.listAllRolesByPId11(pd);		//列出此组下架角色
								  
									if(roleList_z.size()>0){
										
										for(int j=0;j<roleList_z.size();j++){
											String roid=roleList_z.get(j).getROLE_ID();
									if(users!=null){
										
										for(int i=0;i<users.size();i++){
											String uid=users.get(i).getString("PARENT_ID");
											if(!"0".equals(uid)){
												String rrid =users.get(i).getString("ROLE_ID");
													if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
														roleList_z.get(j).setROID(true);
														
													}
												}
											}
										}
									  }
									}
								  
								  
								  
								  
							     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
							     	pd.put("DEPARTMENT_ID", department_id);
							
						}
						
						
						}
						
					}else{
						
						fpd.put("ROLE_ID", "0");
						if(departmentid1!=null && !"".equals(departmentid1)){
							if(departmentid1.equals(depid)){
								fpd.put("DEPARTMENT_ID", depid);
							}else{
								fpd.put("DEPARTMENT_ID", departmentid1);
							}
						}
						
						roleList = roleService.listAllRolesByPId1(fpd);		//列出组(页面横向排列的一级组)
				
							pd.put("DEPARTMENT_ID", depid);
							  if("2".equals(rid)){
								  pdro.put("ROID", true);
                            	  pd.put("DEPARTMENT_ID", "0");
                              }
							
							pd.put("ROLE_ID", rid);
							
							  roleList_z = roleService.listAllRolesByPId11(pd);		//列出此组下架角色
							  
							  if(roleList_z.size()>0){
									
									for(int j=0;j<roleList_z.size();j++){
										String roid=roleList_z.get(j).getROLE_ID();
								if(users!=null){
									
									for(int i=0;i<users.size();i++){
										String uid=users.get(i).getString("PARENT_ID");
										if(!"0".equals(uid)){
											String rrid =users.get(i).getString("ROLE_ID");
												if(roid.equals(rrid) && !"00".equals(roid) && !"53714c688e5d438e8959b5d879d6ece6".equals(roid)){
													roleList_z.get(j).setROID(true);
												}
											}
										}
									}
								  }
								}
							  
							  
							  
						     	pd = roleService.findObjectById(pd);				//取得点击的角色组(横排的)
						
						     	pd.put("DEPARTMENT_ID", departmentid1);
						
						
						
						
						
					}
					
					
				}
				
				
					
		mv.addObject("pdro", pdro);
		mv.addObject("DEPARTMENT_ID", department_id);
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/role/role_list"); 
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String role_id=pd.getString("ROLE_ID");
			if("2".equals(role_id)){
				pd.put("DEPARTMENT_ID", "0");
			}
			
			mv.addObject("msg", "add");
			pd.put("NAME", departmentService.findById(pd).getString("NAME"));
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存新增角色
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String role_id = pd.getString("ROLE_ID");		//父类角色id
			String department_id=pd.getString("suoshuzagxjg");
			if(!"".equals(department_id) && null!=department_id){
				String depid=pd.getString("suoshuzagxjg");
				pd.put("DEPARTMENT_ID", depid);
			}
			String rights="";
			if("0".equals(role_id)){
				Session session = Jurisdiction.getSession();
				User user = (User)session.getAttribute(Const.SESSION_USER);
				String roleid=null;
				if(user!=null){
					 roleid =user.getROLE_ID(); 
				}
				PageData pd1 = new PageData();
				pd1.put("ROLE_ID", roleid);
				 rights = roleService.findObjectById(pd1).getString("RIGHTS");
					pd.put("RIGHTS", (null == rights)?"":rights);	//组菜单权限					
			
			}else{
				 rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);	//组菜单权限
			}
			pd.put("PARENT_ID", role_id);
			pd.put("ROLE_ID", this.get32UUID());				//主键
			pd.put("ADD_QX", (null == rights)?"":rights);	//初始新增权限为否
			pd.put("DEL_QX", (null == rights)?"":rights);	//删除权限
			pd.put("EDIT_QX", (null == rights)?"":rights);	//修改权限
			pd.put("CHA_QX", (null == rights)?"":rights);	//查看权限
			String roleid=null;
			String departmentid1 =null;
			String userid=null;
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			
			if(user!=null){
				
				 roleid =user.getROLE_ID();
				  departmentid1 = user.getDEPARTMENT_ID();
				  userid =user.getUSER_ID();
				  
			}
			
			pd.put("USERID", userid);
			roleService.add(pd);
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**请求编辑
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID,String DEPARTMENT_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd1 = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd1.put("DEPARTMENT_ID", DEPARTMENT_ID);
		
			pd = roleService.findObjectById(pd);
			pd.put("NAME", departmentService.findById(pd1).getString("NAME"));
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("system/role/role_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			roleService.edit(pd);
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID,@RequestParam String DEPARTMENT_ID)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除角色");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			pd.put("ROLE_ID", ROLE_ID);
			pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
			if(roleList_z.size() > 0){
				errInfo = "false";											//下级有数据时，删除失败
			}else{
				List<PageData> userlist = userService.listAllUserByRoldId(pd);			//此角色下的用户
				List<PageData> appuserlist = appuserService.listAllAppuserByRorlid(pd);	//此角色下的会员
				if(userlist.size() > 0 || appuserlist.size() > 0){						//此角色已被使用就不能删除
					errInfo = "false2";
				}else{
				roleService.deleteRoleById(ROLE_ID);	//执行删除
				errInfo = "success";
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 显示菜单列表ztree(菜单授权菜单)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuqx")
	public ModelAndView listAllMenu(Model model,String ROLE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
//			Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
//			String roleRights = role.getRIGHTS();//取出本角色菜单权限
//			String parent_id =role.getPARENT_ID();
//			String userdianjiid =role.getUSER_ID();//点击角色组或者角色所获得的userid
//			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
//			
//			Boolean a=false;
//			String roleid=null;
//			String departmentid1 =null;
//			String username=null;
//			String userxitongid=null;
//				Session session = Jurisdiction.getSession();
//				User user = (User)session.getAttribute(Const.SESSION_USER);
//				
//				if(user!=null){
//					
//					 roleid =user.getROLE_ID();
//					  departmentid1 = user.getDEPARTMENT_ID();
//					  username =user.getUSERNAME();
//					  userxitongid=user.getUSER_ID();//登录用户的userid
//					  
//				}
//				PageData pduser =new PageData();
//				pduser.put("USER_ID", userxitongid);
//				List<PageData> users=roleService.findObjectByUserid(pduser);
//				
//				if(users!=null){
//					
//					for(int i=0;i<users.size();i++){
//						if(userdianjiid!=null && !"".equals(userdianjiid)){
//							if(userdianjiid.equals(users.get(i).getString("USERID"))){
//								a=true;
//								break;
//							}
//						}
//						
//						
//					}
//					
//					
//				}
//				
//				
//				
//				
//				Role role1 = roleService.getRoleById(roleid);
//				
//				String pid=role1.getPARENT_ID();
//			//系统用户
//			if("1".equals(roleid) || "1".equals(pid)){
//				
//				//判断点击的菜单角色所对应的用户是否为本用户创建
//				if(a){
//					//判断点击的是否为组角色
//					if("0".equals(parent_id)){
//						//判断是否为系统管理组
//						if("1".equals(ROLE_ID)){
//							
//							menuList = this.readMenu2(menuList, roleRights);
//							//判断是否为总公司管理组
//						}else{
//							menuList = this.readMenu(menuList, roleRights);	
//					}
//					
//					
//					
//				}else{
//					if("53714c688e5d438e8959b5d879d6ece6".equals(ROLE_ID)){
//						menuList = this.readMenu2(menuList, roleRights);
//						}else{
//							menuList = this.readMenu(menuList, roleRights);	
//						}
//					}
//				}else{
//					menuList = this.readMenu3(menuList, roleRights);	
//				}
//				
//				
//				//企业用户
//			}else if("2".equals(roleid) || "2".equals(pid)){
//				
//				
//				//判断点击的菜单角色所对应的用户是否为本用户创建
//				if(a){
//					//判断点击的是否为组角色
//					if("0".equals(parent_id)){
//						//判断是否为系统管理组
//						if("2".equals(ROLE_ID)){
//							
//							menuList = this.readMenu3(menuList, roleRights);
//							//判断是否为总公司管理组
//						}else{
//							menuList = this.readMenu(menuList, roleRights);	
//					}
//					
//					
//					
//				}else{
//					if("00".equals(ROLE_ID)){
//						menuList = this.readMenu3(menuList, roleRights);
//						}else{
//							menuList = this.readMenu(menuList, roleRights);	
//						}
//					}
//				}else{
//					menuList = this.readMenu3(menuList, roleRights);	
//				}
//				
//				//分公司用户
//			}else{
//				
//				
//				//判断点击的菜单角色所对应的用户是否为本用户创建
//				if(a){
//					//判断点击的是否为组角色
//					if("0".equals(parent_id)){
//						
//					menuList = this.readMenu(menuList, roleRights);	
//					
//				}else{
//					
//						menuList = this.readMenu1(menuList, roleRights);	
//						
//					}
//				}else{
//					menuList = this.readMenu3(menuList, roleRights);	
//				}
//				
//			}
//			
//			mv.addObject("roleid", roleid);
//			mv.addObject("parent_id", parent_id);
//			mv.addObject("pid", pid);
//			mv.addObject("a", a);
//			mv.addObject("ROLE_ID",ROLE_ID);
//			
//			mv.addObject("departmentid1", departmentid1);
//			
//			
//	
//			JSONArray arr = JSONArray.fromObject(menuList);
//			String json = arr.toString();
//			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "children").replaceAll("hasMenu", "checked");
//			model.addAttribute("zTreeNodes", json);
//			
//			mv.setViewName("system/role/menuqx");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 显示菜单列表ztree(菜单授权菜单)
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuqx1")
	public ModelAndView listAllMenu1(Model model,String ROLE_ID,String DEPARTMENT_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
			if( "0".equals(DEPARTMENT_ID)){
				try{
					Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
					PageData pd =new PageData();
					pd.put("ROLE_ID", ROLE_ID);
					pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
				
				List<Department> department=departmentService.listAllDepartment("0");
				
				List<PageData> pd1=(List<PageData>) roleService.findByRoleId(pd);
				
				if(department.size()!=0){
					
					for(int i=0;i<department.size();i++){
						String depid=department.get(i).getDEPARTMENT_ID();
						List<Department> depidson =department.get(i).getSubDepartment();
						if(pd1!= null){
							for(int j=0;j<pd1.size();j++){
								String pddepid=pd1.get(j).getString("DEPID");
								if(depid.equals(pddepid)){
									department.get(i).setHasDepartment(true);
								}
							}
					if(depidson!=null &&  depidson.size()!=0){
						
						for(int k=0;k<depidson.size();k++){
							String depidxiangmu =depidson.get(k).getDEPARTMENT_ID();
							department.get(i).getSubDepartment().get(k).setSubDepartment(null);
							for(int x=0;x<pd1.size();x++){
								String pddepid=pd1.get(x).getString("DEPID");
								if(depidxiangmu.equals(pddepid)){
									department.get(i).getSubDepartment().get(k).setHasDepartment(true);
									
								}
							}
							
							
							
						}
					}
						
						}
						
					
					
					}		
				}
				
				
				JSONArray arr = JSONArray.fromObject(department);
				String json = arr.toString();
				json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "children").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
				
				model.addAttribute("zTreeNodes", json);
				mv.addObject("ROLE_ID",ROLE_ID);
				mv.setViewName("system/role/menuqx1");
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
				
				
			}else{
				try{
					Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
					PageData pd =new PageData();
					pd.put("ROLE_ID", ROLE_ID);
					pd.put("DEPARTMENT_ID", DEPARTMENT_ID);
				
				List<Department> department=departmentService.findBypId(pd);
				
				List<PageData> pd1=(List<PageData>) roleService.findByRoleId(pd);
				
				if(department.size()!=0){
					
					for(int i=0;i<department.size();i++){
						String depid=department.get(i).getDEPARTMENT_ID();
						if(pd1!= null){
							for(int j=0;j<pd1.size();j++){
								String pddepid=pd1.get(j).getString("DEPID");
								if(depid.equals(pddepid)){
									department.get(i).setHasDepartment(true);
								}
							}
						}
					}
				}
				
				
				JSONArray arr = JSONArray.fromObject(department);
				String json = arr.toString();
				json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "children").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
				
				model.addAttribute("zTreeNodes", json);
				mv.addObject("ROLE_ID",ROLE_ID);
				mv.setViewName("system/role/menuqx1");
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
				
			}
		return mv;
	}
	
	
	@RequestMapping(value="/queryBumen",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object queryBumen(HttpServletResponse res){
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json;charset=UTF-8");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String json="";
		List<Department>  list =new ArrayList<Department>();
		try {
			
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
			if(departmentid1!=null){
				PageData pddd =new PageData();
				pddd.put("DEPARTMENT_ID", departmentid1);
				pddd=departmentService.findById(pddd);
				if(pddd!=null){
					parent_id =pddd.getString("PARENT_ID");
				
				}
			
				List<Department>  department1=roleService.listAllDepartment(parent_id);
				if(department1.size()>0){
					for(int i =0;i<department1.size();i++){
						String depid=department1.get(i).getDEPARTMENT_ID();
						if(departmentid1.equals(depid)){
							list.add(department1.get(i));
						}
						
						
					}
				}
			
			
			
			}
			
//			List<Map> rstList = new ArrayList<Map>();
			
			JSONArray arr = JSONArray.fromObject(list);
			 json = arr.toString();

			json = json.replaceAll("DEPARTMENT_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDepartment", "children").replaceAll("hasDepartment", "checked").replaceAll("treeurl", "url");
			
			
//			JSONArray jsonArray = JSONArray.fromObject(json);
//			
//			for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
//				JSONObject object = (JSONObject) iterator.next();
//				System.out.println(object.get("name"));
//				System.out.println(object.get("children"));
//				System.out.println(object.get("id"));
//				System.out.println(object.get("pId"));
//			}
//			
////			JsonConfig.
////			
////			JSONArray.fromObject(arg0, arg1);
//			
//			
////			return JSONArray.fromObject(json);
//			
//			
//			
////			rstList=(List<Map>)JSONArray.toList(JSONArray.fromObject(json));
//			System.out.println("sdfsdfsdf");
//			
//			Map<String,Object> map1 = new HashMap<String,Object>();
//			Map<String,Object> map2 = new HashMap<String,Object>();
//			
//			map1.put("id", "0");
//			map1.put("name", "云平台");
//			map1.put("pId", "00");
//			
//			map2.put("id", "1");
//			map2.put("name", "xxx总公司");
//			map2.put("pId", "0");
//			
//			map1.put("children", new ArrayList().add(map2));
//			
//			System.out.println(map2.get("pid"));
//			System.out.println(map2.get("pId"));
//			
//			rstList.add(map1);
////			rstList.add(map2);
			
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
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
		
			List<Department>  department1=roleService.listAllDepartment(parent_id);
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
			mv.setViewName("system/role/department_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	@RequestMapping(value="/saveMenuqx1")
	public void saveMenuqx1(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单权限");
		PageData pd = new PageData();
		pd.put("ROLE_ID", ROLE_ID);
		roleService.deleterd(ROLE_ID);
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				String[] nodes=Tools.str2StrArray(menuIds);
				for(int j=0;j<nodes.length;j++){
				PageData pd1 =new PageData();
				pd1.put("ROLE_ID", ROLE_ID);
				pd1.put("DEPARTMENT_ID", nodes[j]);
				pd1.put("ID", this.get32UUID());
					roleService.addrd(pd1);
					}
				}
				
			}
		 catch(Exception e){
			logger.error(e.toString(), e);
		}


		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**保存角色菜单权限
	 * @param ROLE_ID 角色ID
	 * @param menuIds 菜单ID集合
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMenuqx")
	public void saveMenuqx(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改菜单权限");
		PageData pd = new PageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
				
				Role role = roleService.getRoleById(ROLE_ID);	//通过id获取角色对象
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);				//更新当前角色菜单权限
				pd.put("rights",rights.toString());
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
				pd.put("ROLE_ID", ROLE_ID);
				roleService.setAllRights(pd);					//更新此角色所有子角色的菜单权限
				
				roleService.setAllRights111(pd);  //更新增删改成权限
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}


		
	}

	/**请求角色按钮授权页面(增删改查)
	 * @param ROLE_ID： 角色ID
	 * @param msg： 区分增删改查
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/b4Button")
	public ModelAndView b4Button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
//			List<Menu> menuList = menuService.listAllMenuQx("0"); //获取所有菜单
//			Role role = roleService.getRoleById(ROLE_ID);		  //根据角色ID获取角色对象
//			String parent_id =role.getPARENT_ID();
//			String userdianjiid =role.getUSER_ID();//点击角色组或者角色所获得的userid
//			String roleRights = "";
//			if("add_qx".equals(msg)){
//				roleRights = role.getADD_QX();	//新增权限
//			}else if("del_qx".equals(msg)){
//				roleRights = role.getDEL_QX();	//删除权限
//			}else if("edit_qx".equals(msg)){
//				roleRights = role.getEDIT_QX();	//修改权限
//			}else if("cha_qx".equals(msg)){
//				roleRights = role.getCHA_QX();	//查看权限
//			}
//			
//			
//			
//			Boolean a=false;
//			String roleid=null;
//			String departmentid1 =null;
//			String username=null;
//			String userxitongid=null;
//				Session session = Jurisdiction.getSession();
//				User user = (User)session.getAttribute(Const.SESSION_USER);
//				
//				if(user!=null){
//					
//					 roleid =user.getROLE_ID();
//					  departmentid1 = user.getDEPARTMENT_ID();
//					  username =user.getUSERNAME();
//					  userxitongid=user.getUSER_ID();//登录用户的userid
//					  
//				}
//				PageData pduser =new PageData();
//				pduser.put("USER_ID", userxitongid);
//				List<PageData> users=roleService.findObjectByUserid(pduser);
//				
//				if(user!=null){
//					
//					for(int i=0;i<users.size();i++){
//						if(userdianjiid!=null && !"".equals(userdianjiid)){
//							if(userdianjiid.equals(users.get(i).getString("USERID"))){
//								a=true;
//							}
//						}
//						
//						
//					}
//					
//					
//				}
//				
//				
//				Role role1 = roleService.getRoleById(roleid);
//				
//				String pid=role1.getPARENT_ID();
//			
//			
//				//系统用户
//				if("1".equals(roleid) || "1".equals(pid)){
//					
//					
//					if(a){
//					
//						if("1".equals(parent_id)){
//							
//							if("53714c688e5d438e8959b5d879d6ece6".equals(ROLE_ID)){
//								menuList = this.readMenu2(menuList, roleRights);
//								}else{
//									menuList = this.readMenu(menuList, roleRights);	
//								}
//						
//						
//					}else{
//						menuList = this.readMenu(menuList, roleRights);	
//						
//					}
//					}else{
//						menuList = this.readMenu3(menuList, roleRights);	
//					}
//					
//					
//					//企业用户
//				}else if("2".equals(roleid) || "2".equals(pid)){
//					
//					
//					//判断点击的菜单角色所对应的用户是否为本用户创建
//					if(a){
//					if("2".equals(parent_id)){
//						
//						if("00".equals(ROLE_ID)){
//							menuList = this.readMenu3(menuList, roleRights);
//							}else{
//								menuList = this.readMenu(menuList, roleRights);	
//							}
//						
//					}else{
//						menuList = this.readMenu(menuList, roleRights);	
//					}
//						
//						
//					}else{
//						menuList = this.readMenu3(menuList, roleRights);	
//					}
//					
//					//分公司用户
//				}else{
//					
//					
//					if(a){
//						if("2".equals(parent_id)){
//							
//								menuList = this.readMenu3(menuList, roleRights);
//								
//						}else{
//							menuList = this.readMenu(menuList, roleRights);	
//						}
//							
//							
//						}else{
//							menuList = this.readMenu3(menuList, roleRights);	
//						}
//					
//				}
//			
//			JSONArray arr = JSONArray.fromObject(menuList);
//			String json = arr.toString();
//			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "children").replaceAll("hasMenu", "checked");
//			model.addAttribute("zTreeNodes", json);
//			mv.addObject("ROLE_ID",ROLE_ID);
//			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/b4Button");
		return mv;
	}
	
	/**根据角色权限处理权限状态(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	//如果有权限就勾选复选框
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			
			
			this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
		}
		return menuList;
	}
	//如果有权限就勾选复选框，只要勾选复选框就无法修改
	public List<Menu> readMenu2(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID())){
					menuList.get(i).setChkDisabled(true);
			}
			
			this.readMenu2(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
		}
		return menuList;
	}
	
	//如果有权限就勾选复选框，并且所有节点都无法修改
	public List<Menu> readMenu3(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
				menuList.get(i).setChkDisabled(true);
			this.readMenu3(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
		}
		return menuList;
	}
	
	
	
	//如果有权限就勾选复选框，没有权限就禁止选中
	public List<Menu> readMenu1(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(!RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID())){
				menuList.get(i).setChkDisabled(true);
				
			}
			this.readMenu1(menuList.get(i).getSubMenu(), roleRights);					//是：继续排查其子菜单
		}
		return menuList;
	}
	/**
	 * 保存角色按钮权限
	 */
	/**
	 * @param ROLE_ID
	 * @param menuIds
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveB4Button")
	public void saveB4Button(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"修改"+msg+"权限");
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value",rights.toString());
			}else{
				pd.put("value","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.saveB4Button(msg,pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
//	
//	/**获取DEPARTMENT_TYPE_ID=1的名称和DEPARTMENT_ID，用于角色管理的添加角色页面
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping(value="/getrights")
//	@ResponseBody
//	public Object departmenttypeid() throws Exception{
//		Map<String,String> map = new HashMap<String,String>();
//		PageData pd = new PageData();
//		String pd11 = null;
//		try{
//			pd = this.getPageData();
//			pd = roleService.findObjectById(pd);
//			ObjectMapper mapper = new ObjectMapper();
//			pd11 = mapper.writeValueAsString(pd);
//			System.out.println(pd11+"#################################");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		map.put("pd",pd11);//返回结果
//		return AppUtil.returnObject(new PageData(), map);
//	}
	
	
	public static void main(String[] args) {
		
		BigInteger i = RightsHelper.sumRights(Tools.str2StrArray("1,2,3,4,5,6,7"));//用菜单ID做权处理
		System.out.println(i);
		
		
		
	}
	
	
}