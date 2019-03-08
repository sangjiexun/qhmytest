package com.fh.service.system.rule.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.rule.RuleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;

/** 
 * 说明： 组织机构
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 * @version
 */
@Service("ruleService")
public class RuleService implements RuleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	
	
	
	@Override
	public PageData shangji(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		String djbm=pd.getString("BIANMA");
		String mw=djbm.substring(0,djbm.length()-4);
		String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
		PageData pdd =  new PageData();
		pdd.put("BIANMA", jq);
		//PageData pdd=(PageData)dao.findForObject("RuleMapper.getbm", pd);
		PageData pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
		if(pda!=null){
			if("Y".equals(pda.get("CHENGFA_JZXJXG").toString()) && !pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
				 return pda;
			}else if(mw.equals(jq) || pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
				return null;
			}else{
				PageData paa =  new PageData();
				paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paa.put("BIANMA", djbm);
				pda=shangji(paa);
			}
		}else{
			PageData paaa =  new PageData();
			paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
			paaa.put("BIANMA", djbm);
			pda=shangji(paaa);
		}
		return pda;
	}
	@Override
	public PageData shangjinl(PageData pd) throws Exception {
		String djbm=pd.getString("BIANMA");
		String mw=djbm.substring(0,djbm.length()-4);
		String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
		PageData pdd =  new PageData();
		pdd.put("BIANMA", jq);
		//PageData pdd=(PageData)dao.findForObject("RuleMapper.getbm", pd);
		PageData pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
		if(pda!=null){
			if("Y".equals(pda.get("NIANLING_JZXJXG").toString()) && !pda.get("QIYE_BIANMA").equals(pd.get("BIANMA")) ){
				 return pda;
			}else if(mw.equals(jq) || pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
				return null;
				
			}else{
				PageData paa =  new PageData();
				paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paa.put("BIANMA", djbm);
				pda=shangjinl(paa);
			}
		}else{
			PageData paaa =  new PageData();
			paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
			paaa.put("BIANMA", djbm);
			pda=shangjinl(paaa);
		}
		return pda;
	}
	@Override
	public PageData shangjizs(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		String djbm=pd.getString("BIANMA");
		String mw=djbm.substring(0,djbm.length()-4);
		String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
		PageData pdd =  new PageData();
		pdd.put("BIANMA", jq);
		PageData pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
		if(pda!=null){
			if("Y".equals(pda.get("ZHENGSHU_JZXJXG").toString()) && !pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
				 return pda;
			}else if(mw.equals(jq) || pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
				return null;
				
			}else{
				PageData paa =  new PageData();
				paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paa.put("BIANMA", djbm);
				pda=shangjizs(paa);
			}
		}else{
			PageData paaa =  new PageData();
			paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
			paaa.put("BIANMA", djbm);
			pda=shangjizs(paaa);
		}
		return pda;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public PageData xmshangji(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		String djbm=pd.getString("BIANMA");
		String mw=djbm.substring(0,djbm.length()-4);
		PageData pda=new PageData();
		if(djbm.length()>=Integer.parseInt(pd.getString("LENGTH"))){
			String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
			PageData pdd =  new PageData();
			pdd.put("BIANMA", jq);
			//PageData pdd=(PageData)dao.findForObject("RuleMapper.getbm", pd);
			 pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
			if(pda!=null){
				if("Y".equals(pda.get("CHENGFA_JZXJXG").toString())){
					 return pda;
				}else if(djbm.equals(jq)){
					return null;
				}else if(pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
					 return pda;
				}
				else{
					PageData paa =  new PageData();
					paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
					paa.put("BIANMA", djbm);
					pda=xmshangji(paa);
				}
			}else{
				PageData paaa =  new PageData();
				paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paaa.put("BIANMA", djbm);
				pda=xmshangji(paaa);
			}
		}else{
			return null;
		}
		
		return pda;
	}
	@Override
	public PageData xmshangjinl(PageData pd) throws Exception {
		String djbm=pd.getString("BIANMA");
	//	String mw=djbm.substring(0,djbm.length()-4);
		PageData pda=new PageData();
		if(djbm.length()>=Integer.parseInt(pd.getString("LENGTH"))){
			String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
			PageData pdd =  new PageData();
			pdd.put("BIANMA", jq);
			//PageData pdd=(PageData)dao.findForObject("RuleMapper.getbm", pd);
			 pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
			if(pda!=null){
				if("Y".equals(pda.get("NIANLING_JZXJXG").toString())){
					 return pda;
				}else if(djbm.equals(jq)){
					return null;
					
				}else if(pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
					 return pda;
				}else{
					PageData paa =  new PageData();
					paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
					paa.put("BIANMA", djbm);
					pda=xmshangjinl(paa);
				}
			}else{
				PageData paaa =  new PageData();
				paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paaa.put("BIANMA", djbm);
				pda=xmshangjinl(paaa);
			}
		}else{
			return null;
		}
		
		return pda;
	}
	@Override
	public PageData xmshangjizs(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		String djbm=pd.getString("BIANMA");
	//	String mw=djbm.substring(0,djbm.length()-4);
		PageData pda=new PageData();
		if(djbm.length()>=Integer.parseInt(pd.getString("LENGTH"))){
			String jq=djbm.substring(0,Integer.parseInt(pd.getString("LENGTH")));
			PageData pdd =  new PageData();
			pdd.put("BIANMA", jq);
			 pda=(PageData)dao.findForObject("RuleMapper.shangji", pdd);
			if(pda!=null){
				if("Y".equals(pda.get("ZHENGSHU_JZXJXG").toString())){
					 return pda;
				}else if(djbm.equals(jq)){
					return null;
					
				}else if(pda.get("QIYE_BIANMA").equals(pd.get("BIANMA"))){
					 return pda;
				}else{
					PageData paa =  new PageData();
					paa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
					paa.put("BIANMA", djbm);
					pda=xmshangjizs(paa);
				}
			}else{
				PageData paaa =  new PageData();
				paaa.put("LENGTH", Integer.parseInt(pd.getString("LENGTH"))+4);
				paaa.put("BIANMA", djbm);
				pda=xmshangjizs(paaa);
			}
		}else{
			return null;
		}
		
		
		return pda;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Override
	public PageData xmshangji(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData pdd=(PageData)dao.findForObject("RuleMapper.getsjbm", pd);
		PageData pda=(PageData)dao.findForObject("RuleMapper.xmshangji", pdd);
		
		if(pda==null &&  !"0".equals(pdd.getString("BIANMA"))){
			xmshangji(pdd);
		}else if("0".equals(pdd.getString("BIANMA"))){
			return null;
		}
		 return pda;
	}*/
	
	
	/**通过bianma获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findbybm(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.findbybm", pd);
	}
	/**惩罚开启
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updatecf(PageData pd)throws Exception{
		dao.update("RuleMapper.updatecf", pd);
		
		/*List<PageData> list=(List<PageData>)dao.findForList("RuleMapper.getdepid", pd);
		
		for(int i=0;i<list.size();i++){
			PageData pda=new PageData();
			if("FALSE".equals(list.get(i).get("XM"))){
				pda.put("BM", list.get(i).get("BIANMA"));
				dao.update("RuleMapper.updatecf", pda);
			}else if("TRUE".equals(list.get(i).get("XM"))){
				pda.put("XMBM", list.get(i).get("BIANMA"));
				dao.update("RuleMapper.updatecf", pda);
			}
		}*/
	
	}
	/**惩罚关闭
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updatecfgb(PageData pd)throws Exception{
		dao.update("RuleMapper.updatecfgb", pd);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("RuleMapper.save", pd);
		}
	@Override
	public void savemz(PageData pd)throws Exception{
		dao.save("RuleMapper.savemz", pd);
		}
	@Override
	public void savekq(PageData pd)throws Exception{
		dao.save("RuleMapper.savekq", pd);
		}
	@Override
	public void saveyj(PageData pd)throws Exception{
		dao.save("RuleMapper.saveyj", pd);
		}
	
	@Override
	public void savemq(PageData pd)throws Exception{
		dao.save("RuleMapper.savemq", pd);
		}
	
	@Override
	public void savediyu(PageData pd)throws Exception{
		dao.save("RuleMapper.savediyu", pd);
		}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listformc(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RuleMapper.findlistformc", pd);
	}
	
	@Override
	public void saveqy(PageData pd)throws Exception{
		dao.save("RuleMapper.saveqy", pd);
		}
	
	@Override
	public void savexm(PageData pd)throws Exception{
		dao.save("RuleMapper.savexm", pd);
		}
		
	@Override
	public void savefgs(PageData pd)throws Exception{
		dao.save("RuleMapper.savefgs", pd);
		}
	

	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateyxdj(PageData pd)throws Exception{
		dao.update("RuleMapper.updateyxdj", pd);
	}
	
	
	@Override
	public void updatetgnl(PageData pd)throws Exception{
		dao.update("RuleMapper.updatetgnl", pd);
	}
	@Override
	public void updatenxnl(PageData pd)throws Exception{
		dao.update("RuleMapper.updatenxnl", pd);
	}
	@Override
	public void updatenvnl(PageData pd)throws Exception{
		dao.update("RuleMapper.updatenvnl", pd);
	}  
	
	
	
	
	
	
	@Override
	public void updateagenv(PageData pd)throws Exception{
		dao.update("RuleMapper.updateagenv", pd);
	}
	@Override
	public void updatetyagenv(PageData pd)throws Exception{
		dao.update("RuleMapper.updatetyagenv", pd);
	}
	
	
	@Override
	public void updatezhengsqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updatezhengsqy", pd);
	}
	
	@Override
	public void updatehmdqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updatehmdqy", pd);
	}
	
	@Override
	public void updategrjzqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updategrjzqy", pd);
	}
	@Override
	public void updategrjzty(PageData pd)throws Exception{
		dao.update("RuleMapper.updategrjzty", pd);
	}
	
	
	@Override
	public void updatehmdty(PageData pd)throws Exception{
		dao.update("RuleMapper.updatehmdty", pd);
	}
	@Override
	public void updatezhengsty(PageData pd)throws Exception{
		dao.update("RuleMapper.updatezhengsty", pd);
	}
	
	
	@Override
	public void updateagenx(PageData pd)throws Exception{
		dao.update("RuleMapper.updateagenx", pd);
	}
	@Override
	public void updatetyagenx(PageData pd)throws Exception{
		dao.update("RuleMapper.updatetyagenx", pd);
	}
	
	@Override   
	public void updatejypx(PageData pd)throws Exception{
		dao.update("RuleMapper.updatejypx", pd);
	}
	
	@Override   
	public void updateyjbtn(PageData pd)throws Exception{
		dao.update("RuleMapper.updateyjbtn", pd);
	}
	
	@Override   
	public void updatechangemqty(PageData pd)throws Exception{
		dao.update("RuleMapper.updatechangemqty", pd);
	}
	
	@Override   
	public void updatechangemzwu(PageData pd)throws Exception{
		dao.update("RuleMapper.updatechangemzwu", pd);
	}
	
	@Override   
	public void updatemzwuall(List<PageData> list)throws Exception{
		for (PageData pd : list) {
			dao.update("RuleMapper.updatechangemzwu", pd);
		}
		//dao.update("RuleMapper.updatemzwuall", pd);
	}
	@Override   
	public void updatemzdjtxall(List<PageData> list)throws Exception{
		for (PageData pd : list) {
			dao.update("RuleMapper.updatechangedjtx", pd);
		}
	}
	@Override   
	public void updatemzjzdjall(List<PageData> list)throws Exception{
		for (PageData pd : list) {
			dao.update("RuleMapper.updatechangejzdj", pd);
		}
	}
	
	
	
	@Override   
	public void updatechangedjtx(PageData pd)throws Exception{
		dao.update("RuleMapper.updatechangedjtx", pd);
	}
	@Override   
	public void updatechangejzdj(PageData pd)throws Exception{
		dao.update("RuleMapper.updatechangejzdj", pd);
	}
	
	@Override   
	public void updatechangemqqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updatechangemqqy", pd);
	}
	
	
	@Override   
	public void updaterysx(PageData pd)throws Exception{
		dao.update("RuleMapper.updaterysx", pd);
	}
	
	@Override   
	public void updatejyts(PageData pd)throws Exception{
		dao.update("RuleMapper.updatejyts", pd);
	}
	@Override   
	public void updateryts(PageData pd)throws Exception{
		dao.update("RuleMapper.updateryts", pd);
	}
	
	@Override   
	public void updatebzcql(PageData pd)throws Exception{
		dao.update("RuleMapper.updatebzcql", pd);
	}
	@Override   
	public void updateyjwcc(PageData pd)throws Exception{
		dao.update("RuleMapper.updateyjwcc", pd);
	}
	
	
	
	@Override   
	public void updateewm(PageData pd)throws Exception{
		dao.update("RuleMapper.updateewm", pd);
	}
	
	@Override   
	public void updatedrkq(PageData pd)throws Exception{
		dao.update("RuleMapper.updatedrkq", pd);
	}
	
	@Override
	public void updateagetg(PageData pd)throws Exception{
		dao.update("RuleMapper.updateagetg", pd);
	}
	
	
	@Override
	public void updatetyagetg(PageData pd)throws Exception{
		dao.update("RuleMapper.updatetyagetg", pd);
	}
	
	@Override
	public void updatecdjb(PageData pd)throws Exception{
		dao.update("RuleMapper.updatecdjb", pd);
	}
	@Override
	public void updatebyxdj(PageData pd)throws Exception{
		dao.update("RuleMapper.updatebyxdj", pd);
	}
	
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updatezsgb(PageData pd)throws Exception{
		dao.update("RuleMapper.updatezsgb", pd);
	}
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updatezsqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updatezsqy", pd);
	}
	
	
	@Override
	public void updatenlqy(PageData pd)throws Exception{
		dao.update("RuleMapper.updatenlqy", pd);
	}
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updatenlgb(PageData pd)throws Exception{
		dao.update("RuleMapper.updatenlgb", pd);
	}
	
	
	@Override
	public PageData getxm(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.getxmbm", pd);
	}
	
	@Override
	public PageData findBybianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.findBybianma", pd);
	}
	
	@Override
	public PageData gettypeid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.gettypeid", pd);
	}
	
	@Override
	public PageData getqygz(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.getqygz", pd);
	}
	
	@Override
	public PageData getxxbybianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.getxxbybianma", pd);
	}
	
	@Override
	public PageData getyujing(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuleMapper.getyujing", pd);
	}
	
//	@Override
//	public PageData getbianma() throws Exception {
//		// TODO Auto-generated method stub
//		return (PageData)dao.findForObject("RuleMapper.getbianma",null);
//	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> cllistPage(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.cllistPage", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> mqlistPage(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.mqlistPage", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> dylistPage(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.dylistPage", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> mzlistPage(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.mzlistP", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> mzlist(Page page) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.mzlist", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getgzz(PageData pd) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.getgzz", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getshengfen(PageData pd) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.getgshengfen", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getlxj(PageData pd) throws Exception {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList("RuleMapper.getlxj", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getbianma() throws Exception {
		PageData pd = null;
		 pd = (PageData)dao.findForObject("RuleMapper.getbianma", "1");
		return String.format("%05d", Integer.parseInt(String.valueOf(pd.get("BIANMA"))));
		
	}      
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("RuleMapper.edit", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("RuleMapper.delete", pd);
	}
	
	@Override
	public void deletedy(PageData pd)throws Exception{
		dao.delete("RuleMapper.deletedy", pd);
	}
	
	
	@Override
	public PageData getxmbm(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findByBianma", pd);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("department/list.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@Override
	public List<Project> typeName() throws Exception {
		// TODO Auto-generated method stub
		return dao.typeName("ProjectMapper.typeName");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByTypeId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.findByTypeId", pd);
	}

	
	//user控制层用
	@Override
	public List<Department> findAll() throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>)dao.findAll("DepartmentMapper.findAll");
	}

	
	/**user控制层用
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllUserDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("user/listUsers.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllUserDepartment(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	
	/**labour控制层用
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Department> listAllLabourDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("labour/listLabours.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setSubDepartment(this.listAllLabourDepartment1(depar.getDEPARTMENT_ID()));
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}
	
	public List<Department> listAllLabourDepartment1(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setTreeurl("labour/listLabours.do?DEPARTMENT_ID="+depar.getDEPARTMENT_ID());
			depar.setTarget("treeFrame");
		}
		return departmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> findBypId(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>)dao.findForList("DepartmentMapper.findBypId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> departmentZtree(String department_id) throws Exception {
		// TODO Auto-generated method stub
		return (List<Department>) dao.findForList("DepartmentMapper.departmentZtree", department_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_province(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_province", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_city(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_city", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> S_district(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.S_district", pd);
	}

	@Override
	public List<PageData> ListXM(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("DepartmentMapper.listXm", pd);
	}





	



	

	
}

