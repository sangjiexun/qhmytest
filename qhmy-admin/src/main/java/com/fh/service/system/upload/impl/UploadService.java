package com.fh.service.system.upload.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.system.upload.UploadManager;
import com.fh.util.PageData;


@Service("uploadService")
public class UploadService implements UploadManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getFenbaoshangFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getFenbaoshangFj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getQiyezizhiFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getQiyezizhiFj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getHeimingdanFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getHeimingdanFj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getLaowurenyuanFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getLaowurenyuanFj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getGongZiFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getGongZiFj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getAnQuanJyFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getAnQuanJyFj", pd);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getJcFj(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("UploadFileMapper.getJcFj", pd);
	}
	
}

