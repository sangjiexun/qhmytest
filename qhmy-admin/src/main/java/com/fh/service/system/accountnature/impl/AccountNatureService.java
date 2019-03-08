package com.fh.service.system.accountnature.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.accountnature.AccoutNatureManager;
import com.fh.util.PageData;

@Service
public class AccountNatureService implements AccoutNatureManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> poor_list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("AccountNatureMapper.accountNaturelistPage", page);
	}

	@Override
	public PageData getpoor(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AccountNatureMapper.getpoor", pd);
	}

	@Override
	public PageData getBianMa(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AccountNatureMapper.getBianMa", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("AccountNatureMapper.update", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		PageData pd_bianma = this.getBianMa(pd);
	
		pd.put("BIANMA", pd_bianma.getString("BIANMA").trim());
		dao.save("AccountNatureMapper.save", pd);
	}

	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("AccountNatureMapper.updateIsUse", pd);
		
	}

	@Override
	public PageData getpoor_NAME(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AccountNatureMapper.getpoor_NAME", pd);
	}

	@Override
	public void del(PageData pd) throws Exception {
		dao.delete("AccountNatureMapper.del", pd);
		
	}

	@Override
	public PageData getIsUsed(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AccountNatureMapper.getIsUsed", pd);
	}


}
