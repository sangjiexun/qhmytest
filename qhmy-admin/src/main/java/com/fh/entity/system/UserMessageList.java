package com.fh.entity.system;

public class UserMessageList {
	private String XCBB_ID;
	private String NAME;
	private String xmname;
	private String fbgsname;
	private String bzname;
	private String jobid;
	private String gz;
	private String uname;
	private String idcard;
	private String jcrq;
	private String lcrq;
	private String sd;
	private String gs;
	private String BZ;
	public String getXmname() {
		return xmname;
	}
	public void setXmname(String xmname) {
		this.xmname = xmname;
	}
	public String getFbgsname() {
		return fbgsname;
	}
	public void setFbgsname(String fbgsname) {
		this.fbgsname = fbgsname;
	}
	public String getBzname() {
		return bzname;
	}
	public void setBzname(String bzname) {
		this.bzname = bzname;
	}
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getGz() {
		return gz;
	}
	public void setGz(String gz) {
		this.gz = gz;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getJcrq() {
		return jcrq;
	}
	public void setJcrq(String jcrq) {
		this.jcrq = jcrq;
	}
	public String getLcrq() {
		return lcrq;
	}
	public void setLcrq(String lcrq) {
		this.lcrq = lcrq;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public void setXCBB_ID(String xCBB_ID) {
		XCBB_ID = xCBB_ID;
	}
	public String getXCBB_ID() {
		return XCBB_ID;
	}
	
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNAME() {
		return NAME;
	}
	public void setBz(String bz) {
		this.BZ = bz;
	}
	public String getBz() {
		return BZ;
	}
	@Override
	public String toString() {
		return "UserMessageList [XCBB_ID=" + XCBB_ID + ", NAME=" + NAME
				+ ", xmname=" + xmname + ", fbgsname=" + fbgsname + ", bzname="
				+ bzname + ", jobid=" + jobid + ", gz=" + gz + ", uname="
				+ uname + ", idcard=" + idcard + ", jcrq=" + jcrq + ", lcrq="
				+ lcrq + ", sd=" + sd + ", gs=" + gs + ", BZ=" + BZ
				+ ", getXmname()=" + getXmname() + ", getFbgsname()="
				+ getFbgsname() + ", getBzname()=" + getBzname()
				+ ", getJobid()=" + getJobid() + ", getGz()=" + getGz()
				+ ", getUname()=" + getUname() + ", getIdcard()=" + getIdcard()
				+ ", getJcrq()=" + getJcrq() + ", getLcrq()=" + getLcrq()
				+ ", getSd()=" + getSd() + ", getGs()=" + getGs()
				+ ", getXCBB_ID()=" + getXCBB_ID() + ", getNAME()=" + getNAME()
				+ ", getBz()=" + getBz() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	

}
