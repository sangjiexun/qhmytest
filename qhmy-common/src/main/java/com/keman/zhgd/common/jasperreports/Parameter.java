package com.keman.zhgd.common.jasperreports;

public class Parameter extends AbsObjectCreateXml{
	
	private String name;
	
	private String className;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

//	<parameter name="title" class="java.lang.String"/>
	
	@Override
	public String createXml() {
		StringBuffer codeBuffer = new StringBuffer();
		codeBuffer.append("<parameter name=\"");
		codeBuffer.append(this.getName());
		codeBuffer.append("\"  ");
		codeBuffer.append(" class=\"");
		codeBuffer.append(this.getClassName());
		codeBuffer.append("\"  ");
		codeBuffer.append(" /> ");
		return codeBuffer.toString();
	}

	
	
}
