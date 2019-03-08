package com.fh.entity.system;

public class Sysnotice {

	private String FID;//主键
	private String FNAME;//发布内容名称
	private String FCONTENT;//发布内容
	private String FRELEASEPSN;//发布人
	private String FRELEASEDATE;//发布时间
	private String FDEPARTMENTID;//所属企业ID
	public String getFID() {
		return FID;
	}
	public void setFID(String fID) {
		FID = fID;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getFCONTENT() {
		return FCONTENT;
	}
	public void setFCONTENT(String fCONTENT) {
		FCONTENT = fCONTENT;
	}
	public String getFRELEASEPSN() {
		return FRELEASEPSN;
	}
	public void setFRELEASEPSN(String fRELEASEPSN) {
		FRELEASEPSN = fRELEASEPSN;
	}
	public String getFRELEASEDATE() {
		return FRELEASEDATE;
	}
	public void setFRELEASEDATE(String fRELEASEDATE) {
		FRELEASEDATE = fRELEASEDATE;
	}
	public String getFDEPARTMENTID() {
		return FDEPARTMENTID;
	}
	public void setFDEPARTMENTID(String fDEPARTMENTID) {
		FDEPARTMENTID = fDEPARTMENTID;
	}
	
	
	
	
}
