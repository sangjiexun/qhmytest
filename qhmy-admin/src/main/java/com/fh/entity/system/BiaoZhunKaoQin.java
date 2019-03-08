package com.fh.entity.system;

import java.io.Serializable;
import java.util.Date;
/**
 * 标准考勤对应的实体类
 * @author Administrator
 *
 */
public class BiaoZhunKaoQin implements Serializable{
	  
	private static final long serialVersionUID = -1336478433034243219L;
	private String PKID;
	   private String QIYE_BIANMA;
	   private String PROJECT_BIANMA;
	   private Date STARTDATE;
	   private Date ENDDATE;
	   private Integer ZAIGONGYXSJ;
	   private Integer QIANHOUSJ;
	   private Integer GONG;
	   private Date CJSJ;
	   private Date XGSJ;
	   private Integer ZHUANGTAI;
	   private Date ZUOFEISHJIAN;
	   public BiaoZhunKaoQin() {
		
	   }
	public String getPKID() {
		return PKID;
	}
	public void setPKID(String pKID) {
		PKID = pKID;
	}
	public String getQIYE_BIANMA() {
		return QIYE_BIANMA;
	}
	public void setQIYE_BIANMA(String qIYE_BIANMA) {
		QIYE_BIANMA = qIYE_BIANMA;
	}
	public String getPROJECT_BIANMA() {
		return PROJECT_BIANMA;
	}
	public void setPROJECT_BIANMA(String pROJECT_BIANMA) {
		PROJECT_BIANMA = pROJECT_BIANMA;
	}
	public Date getSTARTDATE() {
		return STARTDATE;
	}
	public void setSTARTDATE(Date sTARTDATE) {
		STARTDATE = sTARTDATE;
	}
	public Date getENDDATE() {
		return ENDDATE;
	}
	public void setENDDATE(Date eNDDATE) {
		ENDDATE = eNDDATE;
	}
	public Integer getZAIGONGYXSJ() {
		return ZAIGONGYXSJ;
	}
	public void setZAIGONGYXSJ(Integer zAIGONGYXSJ) {
		ZAIGONGYXSJ = zAIGONGYXSJ;
	}
	public Integer getQIANHOUSJ() {
		return QIANHOUSJ;
	}
	public void setQIANHOUSJ(Integer qIANHOUSJ) {
		QIANHOUSJ = qIANHOUSJ;
	}
	public Integer getGONG() {
		return GONG;
	}
	public void setGONG(Integer gONG) {
		GONG = gONG;
	}
	public Date getCJSJ() {
		return CJSJ;
	}
	public void setCJSJ(Date cJSJ) {
		CJSJ = cJSJ;
	}
	public Date getXGSJ() {
		return XGSJ;
	}
	public void setXGSJ(Date xGSJ) {
		XGSJ = xGSJ;
	}
	public Integer getZHUANGTAI() {
		return ZHUANGTAI;
	}
	public void setZHUANGTAI(Integer zHUANGTAI) {
		ZHUANGTAI = zHUANGTAI;
	}
	public Date getZUOFEISHJIAN() {
		return ZUOFEISHJIAN;
	}
	public void setZUOFEISHJIAN(Date zUOFEISHJIAN) {
		ZUOFEISHJIAN = zUOFEISHJIAN;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CJSJ == null) ? 0 : CJSJ.hashCode());
		result = prime * result + ((ENDDATE == null) ? 0 : ENDDATE.hashCode());
		result = prime * result + ((GONG == null) ? 0 : GONG.hashCode());
		result = prime * result + ((PKID == null) ? 0 : PKID.hashCode());
		result = prime * result
				+ ((PROJECT_BIANMA == null) ? 0 : PROJECT_BIANMA.hashCode());
		result = prime * result
				+ ((QIANHOUSJ == null) ? 0 : QIANHOUSJ.hashCode());
		result = prime * result
				+ ((QIYE_BIANMA == null) ? 0 : QIYE_BIANMA.hashCode());
		result = prime * result
				+ ((STARTDATE == null) ? 0 : STARTDATE.hashCode());
		result = prime * result + ((XGSJ == null) ? 0 : XGSJ.hashCode());
		result = prime * result
				+ ((ZAIGONGYXSJ == null) ? 0 : ZAIGONGYXSJ.hashCode());
		result = prime * result
				+ ((ZHUANGTAI == null) ? 0 : ZHUANGTAI.hashCode());
		result = prime * result
				+ ((ZUOFEISHJIAN == null) ? 0 : ZUOFEISHJIAN.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BiaoZhunKaoQin other = (BiaoZhunKaoQin) obj;
		if (CJSJ == null) {
			if (other.CJSJ != null)
				return false;
		} else if (!CJSJ.equals(other.CJSJ))
			return false;
		if (ENDDATE == null) {
			if (other.ENDDATE != null)
				return false;
		} else if (!ENDDATE.equals(other.ENDDATE))
			return false;
		if (GONG == null) {
			if (other.GONG != null)
				return false;
		} else if (!GONG.equals(other.GONG))
			return false;
		if (PKID == null) {
			if (other.PKID != null)
				return false;
		} else if (!PKID.equals(other.PKID))
			return false;
		if (PROJECT_BIANMA == null) {
			if (other.PROJECT_BIANMA != null)
				return false;
		} else if (!PROJECT_BIANMA.equals(other.PROJECT_BIANMA))
			return false;
		if (QIANHOUSJ == null) {
			if (other.QIANHOUSJ != null)
				return false;
		} else if (!QIANHOUSJ.equals(other.QIANHOUSJ))
			return false;
		if (QIYE_BIANMA == null) {
			if (other.QIYE_BIANMA != null)
				return false;
		} else if (!QIYE_BIANMA.equals(other.QIYE_BIANMA))
			return false;
		if (STARTDATE == null) {
			if (other.STARTDATE != null)
				return false;
		} else if (!STARTDATE.equals(other.STARTDATE))
			return false;
		if (XGSJ == null) {
			if (other.XGSJ != null)
				return false;
		} else if (!XGSJ.equals(other.XGSJ))
			return false;
		if (ZAIGONGYXSJ == null) {
			if (other.ZAIGONGYXSJ != null)
				return false;
		} else if (!ZAIGONGYXSJ.equals(other.ZAIGONGYXSJ))
			return false;
		if (ZHUANGTAI == null) {
			if (other.ZHUANGTAI != null)
				return false;
		} else if (!ZHUANGTAI.equals(other.ZHUANGTAI))
			return false;
		if (ZUOFEISHJIAN == null) {
			if (other.ZUOFEISHJIAN != null)
				return false;
		} else if (!ZUOFEISHJIAN.equals(other.ZUOFEISHJIAN))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BiaoZhunKaoQin [PKID=" + PKID + ", QIYE_BIANMA=" + QIYE_BIANMA
				+ ", PROJECT_BIANMA=" + PROJECT_BIANMA + ", STARTDATE="
				+ STARTDATE + ", ENDDATE=" + ENDDATE + ", ZAIGONGYXSJ="
				+ ZAIGONGYXSJ + ", QIANHOUSJ=" + QIANHOUSJ + ", GONG=" + GONG
				+ ", CJSJ=" + CJSJ + ", XGSJ=" + XGSJ + ", ZHUANGTAI="
				+ ZHUANGTAI + ", ZUOFEISHJIAN=" + ZUOFEISHJIAN + "]";
	}
	
	   
	   
}
