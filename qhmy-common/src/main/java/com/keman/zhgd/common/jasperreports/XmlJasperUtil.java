package com.keman.zhgd.common.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * infoElm.addElement("content");
 * contentElm.addCDATA(“cdata区域”);
 * XMLWriter writer = new XMLWriter(new  FileWriter("ot.xml"));
   writer.write(document);
   writer.close();  
   2.文档中含有中文,设置编码格式写入的形式
   OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式                    
   format.setEncoding("UTF-8");//设置编码  
   XMLWriter writer = new XMLWriter(new FileWriter("output.xml"),format);
   writer.write(document);  
   writer.close();
   
   // 写入  
   writer.write(document);  
   // 立即写入  
   writer.flush();  
   // 关闭操作  
   writer.close();  
   
   XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File("src//a.xml")), "UTF-8"), format); 
   
   1.将字符串转化为XML  
      String text = "<csdn> <java>Java班</java></csdn>";  
      Document document = DocumentHelper.parseText(text);  
    2.将文档或节点的XML转化为字符串.  
     SAXReader reader = new SAXReader();  
     Document   document = reader.read(new File("csdn.xml"));              
     Element root=document.getRootElement();      
     String docXmlText=document.asXML(); 
     String rootXmlText=root.asXML();
     Element memberElm=root.element("csdn");
     String memberXmlText=memberElm.asXML();  
   
 * @author Administrator
 *
 */

public class XmlJasperUtil {

	private String fileName;

	private Document document;

	private Element rootElement;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public XmlJasperUtil(){
		
	}

	public XmlJasperUtil(String fileName) {
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

	/**
	 * 读一个xml文件
	 * @param fileName
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public Document read(String fileName) throws MalformedURLException,
			DocumentException {
		SAXReader reader = new SAXReader();
		document = reader.read(new File(fileName));
		return document;
	}
	
	public static Document saxReadInputStream(InputStream inputStream) throws MalformedURLException,
		DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		return document;
	}
	
	/**
	 * 读取xml文件
	 * @param fileName
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public Document read(File fileName) throws MalformedURLException,
		DocumentException {
		SAXReader reader = new SAXReader();
		this.fileName = fileName.getName();
		document = reader.read(fileName);
		return document;
	}
		

	/**
	 * 返回文档的root节点
	 * @return
	 */
	public Element getRootElement() {
		return document.getRootElement();
	}

	/**
	 * 根据文档返回该文档的root节点
	 * @param doc
	 * @return
	 */
	public Element getRootElement(Document doc) {
		return doc.getRootElement();
	}

	
	/**
	 * 检查xml文档的节点合法性
	 * @param pathNameStrings
	 * @throws Exception
	 */
	public void checkXml(String[] pathNameStrings) throws Exception{
		rootElement = document.getRootElement();
		int pathNameStringsLength = pathNameStrings.length;
		Iterator iterator;
		for (iterator = rootElement.elementIterator(); iterator.hasNext();) {
			Element module = (Element) iterator.next();
			checkModule(pathNameStringsLength,iterator,module,pathNameStrings);
		}
	}

	/**
	 * 检查xml文档的合法性，根据规则
	 * @param pathNameStringsLength
	 * @param iterator
	 * @param module
	 * @param pathNameStrings
	 * @throws Exception
	 */
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
		XmlJasperUtil xmlUtil = new XmlJasperUtil();
		xmlUtil.setFileName(path);
		xmlUtil.read();
		
		String[] pathNameStrings = {"basePackagePath","webPath","targetPath"
				,"actionPackagePath","servicePackagePath","daoPackagePath"};
		xmlUtil.checkXml(pathNameStrings);
	}

	/**
	 * 获得节点总数
	 * @param nodeName
	 * @return
	 */
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

    
    public static Document stringToDocument(String text){
    	Document document = null;
		try {
			document = DocumentHelper.parseText(text);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return document;
    }
    
	/**
	 * 检查文档中某个元素是否为空
	 * @param element
	 * @throws Exception
	 */
	public  void checkXmlValue(Element element) throws Exception{
		if ("".equals(element.getTextTrim())) {
			throw new Exception(fileName+"erroe"+element.getName()+"error");
		}
	}
	
	public static void addElement(){
		
	}
	
}
