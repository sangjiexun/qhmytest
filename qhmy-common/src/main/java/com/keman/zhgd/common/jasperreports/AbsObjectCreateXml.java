package com.keman.zhgd.common.jasperreports;

import org.dom4j.Document;
import org.dom4j.Element;

public abstract class AbsObjectCreateXml implements ObjectCreateXml,BaseCollectionInterface {
	
	@Override
	public String createXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document xmlStrToDom(String xmlcode) {
		// TODO Auto-generated method stub
		return XmlJasperUtil.stringToDocument(xmlcode);
	}
	
	@Override
	public Element xmlStrToElement(String xmlcode) {
		// TODO Auto-generated method stub
		Document document = XmlJasperUtil.stringToDocument(xmlcode);
		return document.getRootElement();
	}

	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element clone(Element element) {
		// TODO Auto-generated method stub
		return (Element)element.clone();
	}

	
}
