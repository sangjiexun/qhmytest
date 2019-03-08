package com.fh.entity.system;

import java.util.List;

/**
 * 
* 类名称：组织机构
* 类描述： 
* @author
* 作者单位： 
* 联系方式：
* 修改时间：2015年12月16日
* @version 2.0
 */
public class Department {

	private String NAME;			//名称
	private String NAME_EN;			//英文名称
	private String BIANMA;			//编码
	private String PARENT_ID;		//上级ID
	private String HEADMAN;			//负责人
	private String TEL;				//电话
	private String FUNCTIONS;		//部门职能
	private String BZ;				//备注
	private	String ADDRESS;			//地址
	private String DEPARTMENT_ID;	//主键
	private int COUNT_IN;
	private int COUNT_OUT;
	public int getCOUNT_IN() {
		return COUNT_IN;
	}
	public void setCOUNT_IN(int cOUNT_IN) {
		COUNT_IN = cOUNT_IN;
	}
	public int getCOUNT_OUT() {
		return COUNT_OUT;
	}
	public void setCOUNT_OUT(int cOUNT_OUT) {
		COUNT_OUT = cOUNT_OUT;
	}
	private String target;
	private String PHONE;
	private String DEPARTMENT_TYPE_ID;
	private String CREWSIZE;
	private String REGISTADDRESS;
	private String BUSINESSLICCODE;
	private String PROJECTCODE;
	private Department department;
	private List<Department> subDepartment;
	private boolean hasDepartment = false;
	private String treeurl;
	private String SYNCHRONPK;
	private String DATASOURCE;
	private String PROJECTSTATUS;
	
	
	
	
	
	
	public String getSYNCHRONPK() {
		return SYNCHRONPK;
	}
	public void setSYNCHRONPK(String sYNCHRONPK) {
		SYNCHRONPK = sYNCHRONPK;
	}
	public String getDATASOURCE() {
		return DATASOURCE;
	}
	public void setDATASOURCE(String dATASOURCE) {
		DATASOURCE = dATASOURCE;
	}
	public String getPROJECTSTATUS() {
		return PROJECTSTATUS;
	}
	public void setPROJECTSTATUS(String pROJECTSTATUS) {
		PROJECTSTATUS = pROJECTSTATUS;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNAME_EN() {
		return NAME_EN;
	}
	public void setNAME_EN(String nAME_EN) {
		NAME_EN = nAME_EN;
	}
	public String getBIANMA() {
		return BIANMA;
	}
	public void setBIANMA(String bIANMA) {
		BIANMA = bIANMA;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getHEADMAN() {
		return HEADMAN;
	}
	public void setHEADMAN(String hEADMAN) {
		HEADMAN = hEADMAN;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getFUNCTIONS() {
		return FUNCTIONS;
	}
	public void setFUNCTIONS(String fUNCTIONS) {
		FUNCTIONS = fUNCTIONS;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Department> getSubDepartment() {
		return subDepartment;
	}
	public void setSubDepartment(List<Department> subDepartment) {
		this.subDepartment = subDepartment;
	}
	public boolean isHasDepartment() {
		return hasDepartment;
	}
	public void setHasDepartment(boolean hasDepartment) {
		this.hasDepartment = hasDepartment;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	public String getDEPARTMENT_TYPE_ID() {
		return DEPARTMENT_TYPE_ID;
	}
	public void setDEPARTMENT_TYPE_ID(String dEPARTMENT_TYPE_ID) {
		DEPARTMENT_TYPE_ID = dEPARTMENT_TYPE_ID;
	}
	public String getCREWSIZE() {
		return CREWSIZE;
	}
	public void setCREWSIZE(String cREWSIZE) {
		CREWSIZE = cREWSIZE;
	}
	public String getREGISTADDRESS() {
		return REGISTADDRESS;
	}
	public void setREGISTADDRESS(String rEGISTADDRESS) {
		REGISTADDRESS = rEGISTADDRESS;
	}
	public String getBUSINESSLICCODE() {
		return BUSINESSLICCODE;
	}
	public void setBUSINESSLICCODE(String bUSINESSLICCODE) {
		BUSINESSLICCODE = bUSINESSLICCODE;
	}
	public String getPROJECTCODE() {
		return PROJECTCODE;
	}
	public void setPROJECTCODE(String pROJECTCODE) {
		PROJECTCODE = pROJECTCODE;
	}
	
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getPHONE() {
		return PHONE;
	}
	
	
}