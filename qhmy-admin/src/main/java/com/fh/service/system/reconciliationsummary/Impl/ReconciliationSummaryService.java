package com.fh.service.system.reconciliationsummary.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.reconciliationsummary.ReconciliationSummaryManager;
import com.fh.util.PageData;
@Service
public class ReconciliationSummaryService implements ReconciliationSummaryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> recSummarylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ReconciliationSummaryMapper.recSummarylistPage", page);
	}
	@Override
	public List<PageData> detaillistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ReconciliationSummaryMapper.detaillistPage", page);
	}
	@Override
	public List<PageData> exportSummarylist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReconciliationSummaryMapper.exportSummarylist", pd);
	}
	@Override
	public List<PageData> exportSummaryDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReconciliationSummaryMapper.exportSummaryDetail", pd);
	}

}
