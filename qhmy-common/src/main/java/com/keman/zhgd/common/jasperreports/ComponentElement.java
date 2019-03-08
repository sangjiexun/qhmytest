package com.keman.zhgd.common.jasperreports;

import org.dom4j.Element;

public class ComponentElement extends AbsObjectCreateXml {
	
	private Element element;
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	@Override
	public Element clone(Element element) {
		String srcXmlCode = element.asXML();
		this.element = xmlStrToElement(srcXmlCode);
		return this.element;
	}

	@Override
	public String createXml() {
		return element.asXML();
//		StringBuffer codeBuffer = new StringBuffer();
////		codeBuffer.append("<property name=\"");
////		codeBuffer.append(this.getName());
////		codeBuffer.append("\"  ");
////		codeBuffer.append(" value=\"");
////		codeBuffer.append(this.getValue());
////		codeBuffer.append("\"  ");
////		codeBuffer.append(" /> ");
//		return codeBuffer.toString();
	}
	
}
