package com.fh.util.upload;

/**
 * 文件上传路径
 * @author Administrator
 *
 */
public class UploadPathVo {
	
	/**
	 * 绝对路径
	 */
	private String absolutePath;
	
	/**
	 * 相对路径
	 */
	private String relativePath;

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	
}
