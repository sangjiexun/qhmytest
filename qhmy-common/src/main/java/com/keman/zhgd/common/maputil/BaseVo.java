package com.keman.zhgd.common.maputil;

import java.util.List;

/**
 * 需要返回给客户端的变量
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class BaseVo {
	
	/**
	 * 返回结果代码
	 */
	private String returncode = "";
	
	/**
	 * 返回结果信息
	 */
	private String returnmessage = "";
	
	/**
	 * 房主税号
	 */
	private String qysh = "";
	
	/**
	 * 生成时间
	 */
	private String scsj = "";
	
	/**
	 * 起始所属期
	 */
	private String qsssq = "";
	
	/**
	 * 截止所属期
	 */
	private String jzssq = "";
	
	/**
	 * 各个表的数据
	 */
	private List tableList;

	/**
	 * 结果代码
	 * @return
	 */
	public String getReturncode() {
		return returncode;
	}

	/**
	 * 结果代码
	 * @return
	 */
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}

	/**
	 * 结果信息
	 * @return
	 */
	public String getReturnmessage() {
		return returnmessage;
	}

	/**
	 * 结果信息
	 * @return
	 */
	public void setReturnmessage(String returnmessage) {
		this.returnmessage = returnmessage;
	}

	/**
	 * 房主税号
	 * @return
	 */
	public String getQysh() {
		return qysh;
	}

	/**
	 * 房主税号
	 * @return
	 */
	public void setQysh(String qysh) {
		this.qysh = qysh;
	}

	/**
	 * 返回结果生成时间
	 * @return
	 */
	public String getScsj() {
		return scsj;
	}

	/**
	 * 返回结果生成时间
	 * @return
	 */
	public void setScsj(String scsj) {
		this.scsj = scsj;
	}

	/**
	 * 起始所属期
	 * @return
	 */
	public String getQsssq() {
		return qsssq;
	}

	/**
	 * 起始所属期
	 * @return
	 */
	public void setQsssq(String qsssq) {
		this.qsssq = qsssq;
	}

	/**
	 * 截止所属期
	 * @return
	 */
	public String getJzssq() {
		return jzssq;
	}

	/**
	 * 截止所属期
	 * @return
	 */
	public void setJzssq(String jzssq) {
		this.jzssq = jzssq;
	}

	/**
	 * 表集合
	 * @return
	 */
	public List getTableList() {
		return tableList;
	}

	/**
	 * 表集合
	 * @return
	 */
	public void setTableList(List tableList) {
		this.tableList = tableList;
	}
	
	
	
}
