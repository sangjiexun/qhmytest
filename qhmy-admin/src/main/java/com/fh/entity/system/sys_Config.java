package com.fh.entity.system;

import java.util.List;

public class sys_Config {
	private sys_Config sys_Config;
	public sys_Config getSys_Config() {
		return sys_Config;
	}
	public void setSys_Config(sys_Config sys_Config) {
		this.sys_Config = sys_Config;
	}
	private int PARAM_ID;
	private	String PARAM_NAME;	
	private	String PARAM_VALUE;	
	private	String PARAM_COMMENT;	
	private	String DEPARTMENT_ID;	
	private	String IS_USED;	
	private int PARENT_ID;
	private int CONFIG_TYPE_ID;
	private String treeurl;
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	private List<sys_Config> subDepartment;
	public List<sys_Config> getSubDepartment() {
		return subDepartment;
	}
	public void setSubDepartment(List<sys_Config> subDepartment) {
		this.subDepartment = subDepartment;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	private String target;
	
	public int getPARAM_ID() {
		return PARAM_ID;
	}
	public void setPARAM_ID(int pARAM_ID) {
		PARAM_ID = pARAM_ID;
	}
	public String getPARAM_NAME() {
		return PARAM_NAME;
	}
	public void setPARAM_NAME(String pARAM_NAME) {
		PARAM_NAME = pARAM_NAME;
	}
	public String getPARAM_VALUE() {
		return PARAM_VALUE;
	}
	public void setPARAM_VALUE(String pARAM_VALUE) {
		PARAM_VALUE = pARAM_VALUE;
	}
	public String getPARAM_COMMENT() {
		return PARAM_COMMENT;
	}
	public void setPARAM_COMMENT(String pARAM_COMMENT) {
		PARAM_COMMENT = pARAM_COMMENT;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getIS_USED() {
		return IS_USED;
	}
	public void setIS_USED(String iS_USED) {
		IS_USED = iS_USED;
	}
	public int getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(int pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public int getCONFIG_TYPE_ID() {
		return CONFIG_TYPE_ID;
	}
	public void setCONFIG_TYPE_ID(int cONFIG_TYPE_ID) {
		CONFIG_TYPE_ID = cONFIG_TYPE_ID;
	}
	@Override
	public String toString() {
		return "sys_Config [sys_Config=" + sys_Config + ", PARAM_ID="
				+ PARAM_ID + ", PARAM_NAME=" + PARAM_NAME + ", PARAM_VALUE="
				+ PARAM_VALUE + ", PARAM_COMMENT=" + PARAM_COMMENT
				+ ", DEPARTMENT_ID=" + DEPARTMENT_ID + ", IS_USED=" + IS_USED
				+ ", PARENT_ID=" + PARENT_ID + ", CONFIG_TYPE_ID="
				+ CONFIG_TYPE_ID + ", treeurl=" + treeurl + ", subDepartment="
				+ subDepartment + ", target=" + target + "]";
	}
	
	
	
}
