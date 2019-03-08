package com.fh.service.authoritymanage.department.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.authoritymanage.department.DepartmentManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;

/**
 * 
 * <p>标题:DepartmentManagerImple</p>
 * <p>描述:组织机构服务类</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月9日 下午4:32:19
 */
@Service("departmentManager")
public class DepartmentManagerImple implements DepartmentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> departmentlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DepartmentManagerMapper.departmentlistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VO> departmentList(PageData pd) throws Exception {
		return (List<VO>)dao.findForList("DepartmentManagerMapper.departmentList", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("DepartmentManagerMapper.save", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(PageData pd) throws Exception {
		dao.save("DepartmentManagerMapper.update", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryDepartment(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("DepartmentManagerMapper.queryDepartment", pd));
	}

	@Override
	public void departmentDel(PageData pd) throws Exception {
		dao.delete("DepartmentManagerMapper.departmentDel", pd);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getlistbyname(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("DepartmentManagerMapper.getlistbyname", pd));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> CheckUser(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("DepartmentManagerMapper.CheckUser", pd));
	}

	@Override
	public List<PageData> getDepartmentList(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("DepartmentManagerMapper.getDepartmentList", pd));
	}

	@Override
	public String getDeptIdByName(PageData pdTemp) throws Exception {
		PageData pd = (PageData)dao.findForObject("DepartmentManagerMapper.getDeptIdByName", pdTemp);
		return pd.getString("PKID");
	}

	@Override
	public PageData getlistbyBianma(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DepartmentManagerMapper.getlistbyBianma", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getcdlistbyname(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("DepartmentManagerMapper.getcdlistbyname", pd));
	}
	
}

