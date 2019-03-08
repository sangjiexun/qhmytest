package com.fh.service.system.rule;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.Project;
import com.fh.util.PageData;

/** 
 * 说明： 组织机构接口类
 * 创建人：zhoudibo
 * 创建时间：2015-12-16
 * @version
 */
public interface RuleManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	public void savemz(PageData pd)throws Exception;
	public void savekq(PageData pd)throws Exception;
	public void saveyj(PageData pd)throws Exception;
	public void savemq(PageData pd)throws Exception;
	public void savediyu(PageData pd)throws Exception;
	public List<PageData> listformc(PageData pd)throws Exception;
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	public void deletedy(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public String getbianma()throws Exception;
	public PageData getxmbm(PageData pd)throws Exception;
	public PageData findBybianma(PageData pd)throws Exception;
	public PageData gettypeid(PageData pd)throws Exception;
	public PageData getqygz(PageData pd)throws Exception;
	public PageData getxxbybianma(PageData pd)throws Exception;
	public PageData getyujing(PageData pd)throws Exception;
	
	public void edit(PageData pd)throws Exception;
	public void saveqy(PageData pd)throws Exception;
	public void savefgs(PageData pd)throws Exception;
	public void savexm(PageData pd)throws Exception;
	public void updatecf(PageData pd)throws Exception;
	public void updateyxdj(PageData pd)throws Exception;
	public void updateagetg(PageData pd)throws Exception;
	public void updatechangemqqy(PageData pd)throws Exception;
	public void updatechangemqty(PageData pd)throws Exception;
	public void updatechangemzwu(PageData pd)throws Exception;
	
	public void updatemzwuall(List<PageData> list)throws Exception;
	public void updatemzdjtxall(List<PageData> list)throws Exception;
	public void updatemzjzdjall(List<PageData> list)throws Exception;
	
	public void updatechangedjtx(PageData pd)throws Exception;
	public void updatechangejzdj(PageData pd)throws Exception;
	public void updatejypx(PageData pd)throws Exception;
	public void updateyjbtn(PageData pd)throws Exception;
	
	public void updaterysx(PageData pd)throws Exception;
	public void updatejyts(PageData pd)throws Exception;
	public void updateryts(PageData pd)throws Exception;
	public void updatebzcql(PageData pd)throws Exception;
	public void updateyjwcc(PageData pd)throws Exception;
	
	public void updateewm(PageData pd)throws Exception;
	public void updatedrkq(PageData pd)throws Exception;
	
	
	public void updateagenx(PageData pd)throws Exception;
	public void updatetyagenx(PageData pd)throws Exception;
	public PageData findbybm(PageData pd)throws Exception;
	public void updateagenv(PageData pd)throws Exception;
	public void updatetyagenv(PageData pd)throws Exception;
	public void updatezhengsqy(PageData pd)throws Exception;
	public void updatezhengsty(PageData pd)throws Exception;
	
	public void updatehmdqy(PageData pd)throws Exception;
	public void updatehmdty(PageData pd)throws Exception;
	
	
	public void updategrjzqy(PageData pd)throws Exception;
	public void updategrjzty(PageData pd)throws Exception;
	
	public void updatenxnl(PageData pd)throws Exception;
	public void updatenvnl(PageData pd)throws Exception;
	public void updatetgnl(PageData pd)throws Exception;
	
	public void updatetyagetg(PageData pd)throws Exception;
	public void updatecdjb(PageData pd)throws Exception;
	public void updatebyxdj(PageData pd)throws Exception;
	public void updatecfgb(PageData pd)throws Exception;
	public void updatenlqy(PageData pd)throws Exception;
	public void updatenlgb(PageData pd)throws Exception;
	public void updatezsqy(PageData pd)throws Exception;
	public void updatezsgb(PageData pd)throws Exception;
	public List<PageData> cllistPage(Page page)throws Exception;
	public List<PageData> mqlistPage(Page page)throws Exception;
	public List<PageData> dylistPage(Page page)throws Exception;
	public List<PageData> mzlistPage(Page page)throws Exception;
	public List<PageData> mzlist(Page page)throws Exception;
	
	public List<PageData> getgzz(PageData pd)throws Exception;
	public List<PageData> getshengfen(PageData pd)throws Exception;
	public List<PageData> getlxj(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	public List<Department> findBypId(PageData pd)throws Exception;
	
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	
	public PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception;
	
	
	/*
	 * 获取组织机构Ztree数据
	 * 
	 * 
	 * */
	public List<Department> departmentZtree(String department_id) throws Exception;
	
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
	
	
	/**
	 * 获取Project表所有数据
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	
	public List<Project> typeName() throws Exception;
	
	/**通过部门类型获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByTypeId(PageData pd)throws Exception;
	
	
	/**通过parentid获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<Department> findAll()throws Exception;
	
	
	public List<Department> listAllUserDepartment(String parentId) throws Exception;
	
	
	public List<Department> listAllLabourDepartment(String parentId) throws Exception;
	public List<Department> listAllLabourDepartment1(String parentId) throws Exception;
	public List<PageData> S_province(PageData pd)throws Exception;
	public List<PageData> S_city(PageData pd)throws Exception;
	public List<PageData> S_district(PageData pd)throws Exception;

	public List<PageData> ListXM(PageData pd)throws Exception;

	public PageData shangji(PageData sjpd)throws Exception;
	public PageData shangjinl(PageData sjpd)throws Exception;
	public PageData shangjizs(PageData sjpd)throws Exception;
	public PageData xmshangji(PageData sjpd)throws Exception;
	public PageData xmshangjinl(PageData sjpd)throws Exception;
	public PageData xmshangjizs(PageData sjpd)throws Exception;

	public PageData getxm(PageData pd) throws Exception;

	

	
	
}

