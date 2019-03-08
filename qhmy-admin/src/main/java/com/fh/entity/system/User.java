package com.fh.entity.system;

import java.io.Serializable;
import java.util.Map;

import com.fh.entity.Page;
import com.keman.zhgd.common.orgtree.OrgTree;

/**
 * 
* 类名称：用户
* 类描述： 
* @author
* 作者单位： 
* 联系方式：
* 创建时间：2014年6月28日
* @version 1.0
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String USER_ID;		//用户id
	private String USERNAME;	//用户名
	private String PASSWORD; 	//密码
	private String NAME;		//姓名
	private String RIGHTS;		//权限
	private String ROLE_ID;		//角色id
	private String LAST_LOGIN;	//最后登录时间
	private String IP;			//用户登录ip地址
	private String STATUS;		//状态
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String SKIN;		//皮肤
	private String BZ;
	private String EMAIL;
	private String NUMBER;
	private String PHONE;
	private String ISLABOUR;
	private String DEPARTMENT_ID;
	private String USER_IDCARD;
	private String COLLEGES_PKID;
	private String COLLEGES_NAME;
	private String COLLEGES_NAME_EN;//学校英文名称：CAIJING、LIGONG
	
	/**
	 * 用户类型
	 */
	private Integer userType;
	
	
	private Map<String, Object> sessionMap;
	
	private OrgTree orgTree;
	
	
	public String getUSER_IDCARD() {
		return USER_IDCARD;
	}
	public void setUSER_IDCARD(String USER_IDCARD) {
		this.USER_IDCARD = USER_IDCARD;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getNUMBER() {
		return NUMBER;
	}
	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getISLABOUR() {
		return ISLABOUR;
	}
	public void setISLABOUR(String iSLABOUR) {
		ISLABOUR = iSLABOUR;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public OrgTree getOrgTree() {
		return orgTree;
	}
	public void setOrgTree(OrgTree orgTree) {
		this.orgTree = orgTree;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getCOLLEGES_PKID() {
		return COLLEGES_PKID;
	}
	public void setCOLLEGES_PKID(String cOLLEGES_PKID) {
		COLLEGES_PKID = cOLLEGES_PKID;
	}
	public String getCOLLEGES_NAME() {
		return COLLEGES_NAME;
	}
	public void setCOLLEGES_NAME(String cOLLEGES_NAME) {
		COLLEGES_NAME = cOLLEGES_NAME;
	}
	public String getCOLLEGES_NAME_EN() {
		return COLLEGES_NAME_EN;
	}
	public void setCOLLEGES_NAME_EN(String cOLLEGES_NAME_EN) {
		COLLEGES_NAME_EN = cOLLEGES_NAME_EN;
	}
	
}
