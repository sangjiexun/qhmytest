package com.fh.service.system.undway.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.undway.UndwayManager;
import com.fh.util.PageData;
@Service("undwayService")
public class UndwayService implements UndwayManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> undwaylist(Page page) throws Exception {
		return (List<PageData>)dao.findForList("UndwayMapper.getlistPage", page);
	}
	@Override
	public PageData getUndway(PageData pd) throws Exception {
		return (PageData)dao.findForObject("UndwayMapper.getUndway", pd);
	}
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("UndwayMapper.update", pd);
	}
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("UndwayMapper.save", pd);
	}
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("UndwayMapper.updateIsUse", pd);
		
	}
	@Override
	public PageData getStuUndway(PageData pd) throws Exception {
		return (PageData)dao.findForObject("UndwayMapper.getStuUndway", pd);
	}
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("UndwayMapper.delete", pd);
		
	}
	@Override
	public PageData getUndwaybyName(PageData pd) throws Exception {
		return (PageData)dao.findForObject("UndwayMapper.getUndwaybyName", pd);
	}

}
