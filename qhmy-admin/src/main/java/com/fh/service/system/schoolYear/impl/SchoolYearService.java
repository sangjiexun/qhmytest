package com.fh.service.system.schoolYear.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.schoolYear.SchoolYearManager;
import com.fh.util.PageData;

@Service("schoolYearService")
public class SchoolYearService implements SchoolYearManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> schoolYearlist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SchoolYearMapper.getlistPage", page);
	}
	@Override
	public PageData getSchoolYearById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SchoolYearMapper.getSchoolYearById", pd);
	}
	@Override
	public PageData getSchoolYearByName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SchoolYearMapper.getSchoolYearByName", pd);
	}
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("SchoolYearMapper.update", pd);
	}
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("SchoolYearMapper.save", pd);
	}
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("SchoolYearMapper.updateIsUse", pd);
		
	}
	@Override
	public PageData getStuSchoolYear(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SchoolYearMapper.getStuSchoolYear", pd);
	}
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("SchoolYearMapper.delete", pd);
		
	}

}
