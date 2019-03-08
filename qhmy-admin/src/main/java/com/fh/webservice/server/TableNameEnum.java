package com.fh.webservice.server;

public enum TableNameEnum {
	
	T_PROJECT_XT("心跳检测表"),
	T_TONGBU_DELETE("物理删除数据记录表")
	;
	private String value;
	
	TableNameEnum(String value){
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
		TableNameEnum [] fhadminEnums = values();
		for (TableNameEnum fhadminEnum : fhadminEnums) {
			if (fhadminEnum.getvalue().equals(value)) {
				return fhadminEnum.getName();
			}
		}
		return "";
	}
	
}
