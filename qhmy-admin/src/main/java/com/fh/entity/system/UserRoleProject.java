package com.fh.entity.system;

public class UserRoleProject {

	private String PROJECT_ID;// 主键
	private String XIANGMUBM;// 项目编码
	private String DEPARTMENT_ID;// 组织ID
	private String XIANGMUMC;// 项目名称

	public String getPROJECT_ID() {
		return PROJECT_ID;
	}

	public void setPROJECT_ID(String pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}

	public String getXIANGMUBM() {
		return XIANGMUBM;
	}

	public void setXIANGMUBM(String xIANGMUBM) {
		XIANGMUBM = xIANGMUBM;
	}

	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}

	public String getXIANGMUMC() {
		return XIANGMUMC;
	}

	public void setXIANGMUMC(String xIANGMUMC) {
		XIANGMUMC = xIANGMUMC;
	}

}
