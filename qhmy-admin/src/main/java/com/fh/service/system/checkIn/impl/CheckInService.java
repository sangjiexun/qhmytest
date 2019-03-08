package com.fh.service.system.checkIn.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.checkIn.CheckInManager;
import com.fh.util.PageData;

@Service("CheckInService")
public class CheckInService implements CheckInManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> stulist(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("CheckInMapper.stulistPage",
				page);
	}

	@Override
	public void updateRz(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("CheckInMapper.updateRz", pd);
	}
}
