package com.keman.zhgd.common.xml;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("unchecked")
public class XmlUtil {

	private String fileName;

	private Document document;

	private Element rootElement;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public XmlUtil() {
			
	}

	public XmlUtil(String fileName) {
		this.fileName = fileName;
	}

	public Document read() throws MalformedURLException, DocumentException {
		return read(fileName);
	}
	
	

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document read(String fileName) throws MalformedURLException,
			DocumentException {
		SAXReader reader = new SAXReader();
		document = reader.read(new File(fileName));
		return document;
	}
	
	public Document read(File fileName) throws MalformedURLException,
		DocumentException {
		SAXReader reader = new SAXReader();
		this.fileName = fileName.getName();
		document = reader.read(fileName);
		return document;
	}
		

	public Element getRootElement() {
		return document.getRootElement();
	}

	public Element getRootElement(Document doc) {
		return doc.getRootElement();
	}

	
	public void checkXml(String[] pathNameStrings) throws Exception{
		rootElement = document.getRootElement();
		int pathNameStringsLength = pathNameStrings.length;
		Iterator iterator;
		for (iterator = rootElement.elementIterator(); iterator.hasNext();) {
			Element module = (Element) iterator.next();
			checkModule(pathNameStringsLength,iterator,module,pathNameStrings);
		}
	}

	private void checkModule(int pathNameStringsLength,Iterator iterator,Element module,String [] pathNameStrings)throws Exception{
		boolean isNull = true;
		for (int i = 0; i < pathNameStringsLength; i++) {
			for (iterator = module.elementIterator(); iterator.hasNext();) {
				Element moduleChild = (Element) iterator.next();
				if (moduleChild.getName().equals(pathNameStrings[i])) {
					isNull = false;
					break;
				}
			}
			if (isNull) {
				throw new Exception(fileName+"error"+pathNameStrings[i]);
			}else {
				isNull = true;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String path = "baseConfig.xml";
		XmlUtil xmlUtil = new XmlUtil();
		xmlUtil.setFileName(path);
		xmlUtil.read();
		String[] pathNameStrings = {"basePackagePath","webPath","targetPath"
				,"actionPackagePath","servicePackagePath","daoPackagePath"};
		xmlUtil.checkXml(pathNameStrings);
	}

	public int getNodeCount(String nodeName) {
		int nodeCount = 0;
		Iterator iterator;
		for (iterator = rootElement.elementIterator(); iterator.hasNext();) {
			Element moduleChild = (Element) iterator.next();
			if (moduleChild.getName().equals("module")) {
				nodeCount++;
			}
		}
		return nodeCount;
	}


	public void checkXmlValue(Element element) throws Exception{
		if ("".equals(element.getTextTrim())) {
			throw new Exception(fileName+"erroe"+element.getName()+"error");
		}
	}

}
