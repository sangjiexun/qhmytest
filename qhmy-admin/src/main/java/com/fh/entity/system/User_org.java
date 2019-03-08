package com.fh.entity.system;

public class User_org {

	private String PKID;//主键
	private String USER_ID;//用户id
	private String DEPARTMENT_ID;//组织id
	private String SHIFOUKCZ;//是否树结构
	private String CJSJ;//创建时间
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
	public String getSHIFOUKCZ() {
		return SHIFOUKCZ;
	}
	public void setSHIFOUKCZ(String sHIFOUKCZ) {
		SHIFOUKCZ = sHIFOUKCZ;
	}
	public String getCJSJ() {
		return CJSJ;
	}
	public void setCJSJ(String cJSJ) {
		CJSJ = cJSJ;
	}
	
	
	
	
}
