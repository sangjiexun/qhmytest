package com.keman.zhgd.common.page;

public class PageConfig {
	
	/**
	 * 要查询的页面数
	 */
	private Integer pageNum = 1;//要查询的页码数
	
	/**
	 * 每页显示多少条
	 */
	private Integer numPerPage = 20;//每页显示多少条记录
	
	/**
	 * 排序字段名称
	 */
	private String orderField;
	
	/**
	 * 排序描述，升序还是降序
	 */
	private String orderDirection;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	
	
	
}
