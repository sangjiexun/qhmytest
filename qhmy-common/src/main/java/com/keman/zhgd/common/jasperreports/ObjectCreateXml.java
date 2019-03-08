package com.keman.zhgd.common.jasperreports;

import org.dom4j.Document;
import org.dom4j.Element;

public interface ObjectCreateXml {
	/**
	 * 生成xml格式的字符串
	 * @return
	 */
	String createXml();
	
	/**
	 * 根据xmlcode生成org.dom4j.Document
	 * @param xmlcode
	 * @return
	 */
	Document xmlStrToDom(String xmlcode);
	
	/**
	 * 根据xmlcode生成org.dom4j.Element
	 * @param xmlcode
	 * @return
	 */
	Element xmlStrToElement(String xmlcode);
	
	/**
	 * 根据一个element克隆一个Element
	 * @param element
	 * @return
	 */
	Element clone(Element element);
	
	
}
