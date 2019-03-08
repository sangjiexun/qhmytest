package com.fh.webservice.server.returnvo;

//* SERVER_DATE:  服务器时间  必传字段
//* RST  : 1:成功   -1失败     必传字段  
//* ERROR: 错误信息描述            必传字段
//* ERRORCODE：错误类型代码  必传字段
public class ReturnBaseVo {
	
	protected String SERVERDATE;
	
	protected String RST;
	
	protected String ERROR="";
	
	protected String ERRORCODE="";

	public String getSERVERDATE() {
		return SERVERDATE;
	}

	public void setSERVERDATE(String sERVERDATE) {
		SERVERDATE = sERVERDATE;
	}

	public String getRST() {
		return RST;
	}

	public void setRST(String rST) {
		RST = rST;
	}

	public String getERROR() {
		return ERROR;
	}

	public void setERROR(String eRROR) {
		ERROR = eRROR;
	}

	public String getERRORCODE() {
		return ERRORCODE;
	}

	public void setERRORCODE(String eRRORCODE) {
		ERRORCODE = eRRORCODE;
	}
	
	
	
}
