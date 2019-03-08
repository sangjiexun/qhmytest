package com.fh.service.system.family.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.family.FamilyManager;
import com.fh.util.PageData;
@Service("familyService")
public class FamilyService implements FamilyManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> familylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("FamilyMapper.getlistPage", page);
	}
	@Override
	public PageData getFamily(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FamilyMapper.getFamily", pd);
	}
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("FamilyMapper.update", pd);
	}
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("FamilyMapper.save", pd);
	}
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("FamilyMapper.updateIsUse", pd);
		
	}
	@Override
	public PageData getStuFamily(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FamilyMapper.getStuFamily", pd);
	}
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("FamilyMapper.delete", pd);
		
	}
	@Override
	public PageData getFamilybyName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("FamilyMapper.getFamilybyName", pd);
	}

}
