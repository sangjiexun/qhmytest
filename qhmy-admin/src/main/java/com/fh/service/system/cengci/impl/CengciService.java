package com.fh.service.system.cengci.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.cengci.CengciManager;
import com.fh.util.PageData;
@Service("cengciService")
public class CengciService implements CengciManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> cengci_list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("CengciMapper.cengci_listPage", page);
	}
	@Override
	public PageData getCengci(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CengciMapper.getCengci", pd);
	}
	@Override
	public void update(PageData pd) throws Exception {
		dao.update("CengciMapper.update", pd);
	}
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("CengciMapper.save", pd);
	}
	@Override
	public void updateIsUse(PageData pd) throws Exception {
		dao.update("CengciMapper.updateIsUse", pd);
	}
	@Override
	public PageData getStuJieShao(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CengciMapper.getStuJieShao", pd);
	}
	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("CengciMapper.delete", pd);
	}
	@Override
	public PageData getCENGCI_NAME(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CengciMapper.getCENGCI_NAME", pd);
	}
	@Override
	public PageData getBianMa(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CengciMapper.getBianMa", pd);
	}
	@Override
	public PageData getCCDAIMA(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("CengciMapper.getCCDAIMA", pd);
	}

}
