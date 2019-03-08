package com.fh.service.authoritymanage.department.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.authoritymanage.department.DepartmentManager;
import com.fh.service.authoritymanage.department.StudentDepartmentManager;
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
@Service("studentDepartmentManager")
public class StudentDepartmentManagerImple implements StudentDepartmentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> departmentlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StudentDepartmentManagerMapper.departmentlistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VO> departmentList(PageData pd) throws Exception {
		return (List<VO>)dao.findForList("StudentDepartmentManagerMapper.departmentList", pd);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("StudentDepartmentManagerMapper.save", pd);
//		//解决性能问题，存院校专业树结构
//		PageData pd_depTree = (PageData)dao.findForObject("StudentDepartmentManagerMapper.getDepTreeNodes", pd);
//		pd_depTree.put("pkid", pd.getString("pkid"));
//		dao.update("StudentDepartmentManagerMapper.updateDepTreeNodes", pd_depTree);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(PageData pd) throws Exception {
		dao.save("StudentDepartmentManagerMapper.update", pd);
		dao.update("StudentDepartmentManagerMapper.updateIsUse", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryDepartment(PageData pd) throws Exception {
		return (List<PageData>)(dao.findForList("StudentDepartmentManagerMapper.queryDepartment", pd));
	}

	@Override
	public void departmentDel(PageData pd) throws Exception {
		dao.delete("StudentDepartmentManagerMapper.departmentDel", pd);
	}

	@Override
	public PageData isHaveUser(PageData pd) throws Exception {
		return (PageData)dao.findForObject("StudentDepartmentManagerMapper.isHaveUser", pd);
	}

	@Override
	public List<PageData> getTopDept(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StudentDepartmentManagerMapper.getTopDept", pd);
	}

	@Override
	public void updatePaiXu(PageData pd) throws Exception {
		dao.update("StudentDepartmentManagerMapper.updatePaiXu", pd);
		
	}

	@Override
	public void saveDeptByBatch(List<PageData> list) throws Exception {
		dao.batchSave("StudentDepartmentManagerMapper.saveDeptByBatch", list);
	}

	@Override
	public void saveNationByBatch(List<PageData> list) throws Exception {
		dao.batchSave("StudentDepartmentManagerMapper.saveNationByBatch", list);
	}

	@Override
	public void saveStuSourceByBatch(List<PageData> list) throws Exception {
		dao.batchSave("StudentDepartmentManagerMapper.saveStuSourceByBatch", list);
	}
	
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("StudentDepartmentManagerMapper.updateIsUse", pd);
		
	}

	@Override
	public PageData getXSLXName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StudentDepartmentManagerMapper.getXSLXName", pd);
	}
	
}

