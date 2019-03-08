package com.fh.service.system.partschool.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.partschool.PartSchoolManager;
import com.fh.util.PageData;

@Service("partSchoolService")
public class PartSchoolService implements PartSchoolManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> partschool_listPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("PartSchoolMapper.partschool_listPage", page);
	}

	@Override
	public void savePartSchool(PageData pd) throws Exception {
		dao.save("PartSchoolMapper.savePartSchool", pd);
		
	}

	@Override
	public void deletePartSchoolByPkid(PageData pd) throws Exception {
		dao.update("PartSchoolMapper.deletePartSchoolByPkid", pd);
		
	}

	@Override
	public void batchDelete(String[] pkids) throws Exception {
		dao.update("PartSchoolMapper.deleteAllPartSchool", pkids);
		
	}

	@Override
	public void batchSavePart(List<PageData> list) throws Exception {
		if(list!=null&&list.size()>0){
			dao.batchSave("PartSchoolMapper.batchSavePart", list);
		}
	}

	@Override
	public PageData getPartSchoolByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("PartSchoolMapper.getPartSchoolByName", pd);
	}

	@Override
	public PageData getPartSchoolByPkid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("PartSchoolMapper.getPartSchoolByPkid", pd);
	}

	@Override
	public void updatePartSchoolByPkid(PageData pd) throws Exception {
		dao.update("PartSchoolMapper.updatePartSchoolByPkid", pd);
		
	}

	@Override
	public List<PageData> getStuinfoByPkid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PartSchoolMapper.getStuinfoByPkid", pd);
	}

	@Override
	public List<PageData> getStuinfoByPkids(String[] pkids) throws Exception {
		return (List<PageData>) dao.findForList("PartSchoolMapper.getStuinfoByPkidArr", pkids);
	}
	
	@Override
	public void updateIsHezuo(PageData pd) throws Exception {
		dao.update("PartSchoolMapper.updateIsHezuo", pd);
		
	}

	@Override
	public List<PageData> exportPartSchoolList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PartSchoolMapper.exportPartSchoolList", pd);
	}

}
