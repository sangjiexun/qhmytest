package com.fh.entity.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* 类名称：角色
* 类描述： 
* @author 
* 作者单位： 
* 联系方式：
* 创建时间：2014年3月10日
* @version 1.0
 */
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ROLE_ID;
	private String ROLE_NAME;
	private String RIGHTS;
	private String PARENT_ID;
	private String ADD_QX;
	private String DEL_QX;
	private String EDIT_QX;
	private String CHA_QX;
	private String DEPARTMENT_ID;
	private String USER_ID;
	private Boolean ROID;
	private Date CJSJ;
	private Integer ROLE_TYPE;

	public Boolean getROID() {
		return ROID;
	}
	public void setROID(Boolean rOID) {
		ROID = rOID;
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
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getADD_QX() {
		return ADD_QX;
	}
	public void setADD_QX(String aDD_QX) {
		ADD_QX = aDD_QX;
	}
	public String getDEL_QX() {
		return DEL_QX;
	}
	public void setDEL_QX(String dEL_QX) {
		DEL_QX = dEL_QX;
	}
	public String getEDIT_QX() {
		return EDIT_QX;
	}
	public void setEDIT_QX(String eDIT_QX) {
		EDIT_QX = eDIT_QX;
	}
	public String getCHA_QX() {
		return CHA_QX;
	}
	public void setCHA_QX(String cHA_QX) {
		CHA_QX = cHA_QX;
	}
	public Date getCJSJ() {
		return CJSJ;
	}
	public void setCJSJ(Date cJSJ) {
		CJSJ = cJSJ;
	}
	public Integer getROLE_TYPE() {
		return ROLE_TYPE;
	}
	public void setROLE_TYPE(Integer rOLE_TYPE) {
		ROLE_TYPE = rOLE_TYPE;
	}
	
	
}
