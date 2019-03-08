package com.keman.zhgd.common.jasperreports;

import java.util.ArrayList;
import java.util.List;

public class Band extends AbsObjectCreateXml{
	
//	height="125" splitType="Stretch"
	
	private String height;
	
	private String splitType;
	
	private List<ComponentElement> componentElementList = new ArrayList<ComponentElement>();

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getSplitType() {
		return splitType;
	}

	public void setSplitType(String splitType) {
		this.splitType = splitType;
	}
	
	@Override
	public String createXml() {
		StringBuffer codeBuffer = new StringBuffer();
		
		codeBuffer.append("<band height=\"");
		codeBuffer.append(this.getHeight());
		codeBuffer.append("\" ");
		codeBuffer.append(" splitType=\"");
		codeBuffer.append(this.getSplitType());
		codeBuffer.append("\" ");
		codeBuffer.append(" > ");
		
		for (ComponentElement componentElement : componentElementList) {
			//添加
		}
		
//		codeBuffer.append("<property name=\"");
//		codeBuffer.append(this.getName());
//		codeBuffer.append("\"  ");
//		codeBuffer.append(" value=\"");
//		codeBuffer.append(this.getValue());
//		codeBuffer.append("\"  ");
//		codeBuffer.append(" /> ");
		
		codeBuffer.append(" </componentElement> ");
		
		return codeBuffer.toString();
	}
	
}
