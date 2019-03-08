package com.keman.zhgd.common.jasperreports;

public class Property extends AbsObjectCreateXml {
	
	private String name;
	
	private String value;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String createXml() {
		StringBuffer codeBuffer = new StringBuffer();
		codeBuffer.append("<property name=\"");
		codeBuffer.append(this.getName());
		codeBuffer.append("\"  ");
		codeBuffer.append(" value=\"");
		codeBuffer.append(this.getValue());
		codeBuffer.append("\"  ");
		codeBuffer.append(" /> ");
		return codeBuffer.toString();
	}
	
}
