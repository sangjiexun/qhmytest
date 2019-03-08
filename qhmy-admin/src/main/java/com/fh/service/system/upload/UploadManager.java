package com.fh.service.system.upload;

import java.util.List;

import com.fh.util.PageData;


public interface UploadManager{

	public List<PageData> getFenbaoshangFj(PageData pageData) throws Exception;

	public List<PageData> getQiyezizhiFj(PageData pageData) throws Exception;

	public List<PageData> getHeimingdanFj(PageData pageData) throws Exception;

	public List<PageData> getLaowurenyuanFj(PageData pageData) throws Exception;

	public List<PageData> getGongZiFj(PageData pageData) throws Exception;
	public List<PageData> getAnQuanJyFj(PageData pageData) throws Exception;
	public List<PageData> getJcFj(PageData pageData) throws Exception;

}

