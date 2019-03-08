package com.fh.service.system.basicclass.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.basicclass.BasicClassManager;
import com.fh.util.PageData;

@Service("basicclassService")
public class BasicClassService implements BasicClassManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> basicclass_listPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("BasicClassMapper.basicclass_listPage", page);
	}

	@Override
	public void saveBasicClass(PageData pd) throws Exception {
		dao.save("BasicClassMapper.saveBasicClass", pd);
		
	}

	@Override
	public PageData getClassByPkid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getClassByPkid", pd);
	}

	@Override
	public void deleteClassByPkid(PageData pd) throws Exception {
		dao.delete("BasicClassMapper.deleteClassByPkid", pd);
		
	}

	@Override
	public void updateClassByPkid(PageData pd) throws Exception {
		dao.update("BasicClassMapper.updateClassByPkid", pd);
		
	}

	@Override
	public List<PageData> getFromSYS_DICT(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("BasicClassMapper.getFromSYS_DICT", pd);
	}
	
	@Override
	public List<PageData> classtype_listPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("BasicClassMapper.classtype_listPage", page);
	}

	@Override
	public void saveclasstype(PageData pd) throws Exception {
		dao.save("BasicClassMapper.saveclasstype", pd);
		
	}

	@Override
	public PageData getclasstypeByPkid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypeByPkid", pd);
	}

	@Override
	public void deleteclasstypeByPkid(PageData pd) throws Exception {
		dao.delete("BasicClassMapper.deleteclasstypeByPkid", pd);
		
	}

	@Override
	public void updateclasstypeByPkid(PageData pd) throws Exception {
		dao.update("BasicClassMapper.updateclasstypeByPkid", pd);
		
	}

	@Override
	public void updateclasstypeIS_USED(PageData pd) throws Exception {
		dao.update("BasicClassMapper.updateclasstypeIS_USED", pd);
	}

	@Override
	public void updateclasstypeSFQY(PageData pd) throws Exception {
		dao.update("BasicClassMapper.updateclasstypeSFQY", pd);
	}

	@Override
	public PageData getclasstypeBIANMA(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypeBIANMA", pd);
	}
	
	@Override
	public PageData getclasstypePkid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypePkid", pd);
	}
	
	@Override
	public PageData getclasstypeName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypeName", pd);
	}
	
	@Override
	public PageData getclasstypeBIANMAs(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypeBIANMAs", pd);
	}
	
	
	@Override
	public PageData getclasstypeNames(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getclasstypeNames", pd);
	}

	@Override
	public PageData getClassCount(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("BasicClassMapper.getClassCount", pd);
	}

	@Override
	public PageData getClassUse(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getClassUse", pd);
	}

	@Override
	public PageData getClassByGradeBxNm(PageData pd) throws Exception {
		return (PageData)dao.findForObject("BasicClassMapper.getClassByGradeBxNm", pd);
	}
	
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("BasicClassMapper.updateIsUse", pd);
		
	}

}
