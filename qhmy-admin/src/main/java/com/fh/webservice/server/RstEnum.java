package com.fh.webservice.server;

public enum RstEnum {
	
	异常("-1"),
	正常("1")
	;
	private String value;
	
	RstEnum(String value){
		this.value = value;
	}

	public String getvalue() {
		return value;
	}

	public void setvalue(String value) {
		this.value = value;
	}
	
	public String getName(){
		return this.name();
	}
	
	/**
	 * 根据value找name
	 * @param value
	 * @return
	 */
	public static String getNameByValue(String value){
		RstEnum [] fhadminEnums = values();
		for (RstEnum fhadminEnum : fhadminEnums) {
			if (fhadminEnum.getvalue().equals(value)) {
				return fhadminEnum.getName();
			}
		}
		return "";
	}
	
}
