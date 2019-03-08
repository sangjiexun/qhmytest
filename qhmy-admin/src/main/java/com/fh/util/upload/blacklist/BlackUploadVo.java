package com.fh.util.upload.blacklist;

/**
 * 黑名单专用的上传VO对象
 * 黑名单，先上传其中一个人员的附件列表，剩下的复制
 * @author Administrator
 *
 */
public class BlackUploadVo {
	
	/**
	 * 主表pkid
	 */
	private String zhubiaoPkid;
	
	
	private String ss; 
	
	/**
	 * 是否上传模板附件
	 */
	private String sfscmb;
	
}
