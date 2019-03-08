package com.fh.entity.system;

public class UserRoleOrgs {

	private String PKID;//主键
	private String USER_ID;//用户ID
	private String DEPARTMENT_ID;//组织ID
	private String PARENT_ID;//上级ID
	private String NAME;//组织名称
	private String BIANMA;//组织编码
	private int DEPARTMENT_TYPE_ID;//组织类型
    
	public String getPKID() {
		return PKID;
	}
	public void setPKID(String pKID) {
		PKID = pKID;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getBIANMA() {
		return BIANMA;
	}
	public void setBIANMA(String bIANMA) {
		BIANMA = bIANMA;
	}
	public int getDEPARTMENT_TYPE_ID() {
		return DEPARTMENT_TYPE_ID;
	}
	public void setDEPARTMENT_TYPE_ID(int dEPARTMENT_TYPE_ID) {
		DEPARTMENT_TYPE_ID = dEPARTMENT_TYPE_ID;
	}
	
	
	
	
}
