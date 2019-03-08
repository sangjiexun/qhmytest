package com.fh.service.system.shezhi.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.shezhi.SheZhiManager;
import com.fh.util.PageData;


@Service("shezhiService")
public class SheZhiService implements SheZhiManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public PageData getOne(PageData pd) throws Exception {
		return (PageData) dao.findForObject("SheZhiMapper.getOne", pd);
	}
	
	@Override
	public void savetb(PageData pd) throws Exception {
		dao.save("SheZhiMapper.savetb", pd);
	}
	
	@Override   
	public void updatetb(PageData pd)throws Exception{
		dao.update("SheZhiMapper.updatetb", pd);
	}

}
