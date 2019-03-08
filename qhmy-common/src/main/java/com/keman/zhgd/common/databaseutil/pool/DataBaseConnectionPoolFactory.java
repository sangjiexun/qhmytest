package com.keman.zhgd.common.databaseutil.pool;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.keman.zhgd.common.xml.XmlUtil;

@SuppressWarnings("unchecked")
public class DataBaseConnectionPoolFactory {
	
	public static String defaultJndi;
	
	private static  DataBaseConnectionPoolFactory dataBaseConnectionPoolFactory;
	
	private  Map poolMap;


	private DataBaseConnectionPoolFactory(){
		this.poolMap = new HashMap();
	}
	

	public DataBaseConnectionPoolFactory(Map poolMap){
		
	}
	
	public static DataBaseConnectionPoolFactory getInstance(){
		if (dataBaseConnectionPoolFactory == null) {
			dataBaseConnectionPoolFactory = new DataBaseConnectionPoolFactory();
			return dataBaseConnectionPoolFactory;
		}else {
			return dataBaseConnectionPoolFactory;
		}
	}
	
	public ConnectionPool getConnectionPool(String jndiName) throws Exception{
		return (ConnectionPool) this.poolMap.get(jndiName);
	}
	
	public void createPools(String xml) throws Exception{
		
		createPools(new File(xml));
	}
	
	public void createPools(File file) throws Exception{
		
		XmlUtil xmlUtil = new XmlUtil();
		Document document = xmlUtil.read(file);
		String [] checkfield = {"driverclass","url","username","password","isdefault","jndiName"};
		xmlUtil.checkXml(checkfield);
		Element rootElement = document.getRootElement();
		Iterator iterator;
		DataBaseInfo dataBaseInfo = null;
		for (iterator = rootElement.elementIterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			ConnectionPool connectionPool = null;
			if (ConnectionPoolType.C3P0.equals(ConnectionPoolType.useingPool)) {
				connectionPool = new ConnectionPoolC3p0Imple();
				dataBaseInfo = elementToDatabaseInfo(element);
				connectionPool.setDatabaseInfo(dataBaseInfo);
			}else if (ConnectionPoolType.PROXOOL.equals(ConnectionPoolType.useingPool)) {
				
			}
			if (dataBaseInfo.getIsdefault().equals("true")) {
				defaultJndi = dataBaseInfo.getJndiName();
			}
			this.poolMap.put(dataBaseInfo.getJndiName(), connectionPool);
		}
	}

	private DataBaseInfo elementToDatabaseInfo(Element element) throws Exception{
		DataBaseInfo dataBaseInfo = new DataBaseInfo();
		if (element == null) {
			throw new Exception(this.getClass().getName()+":error");
		}
		
		String driverclass = element.element("driverclass").getTextTrim();
		if (driverclass == null || "".equals(driverclass.trim())) {
			throw new Exception(this.getClass().getName()+":error");
		}
		
		String url = element.element("url").getTextTrim();
		String username = element.element("username").getTextTrim();
		String password = element.element("password").getTextTrim();
		String isdefault = element.element("isdefault").getTextTrim();
		String jndiName = element.element("jndiName").getTextTrim();
		
		if (jndiName == null || "".equals(jndiName) || url == null || "".equals(url) || username == null || "".equals(username) || password == null || "".equals(password)) {
			throw new Exception(this.getClass().getName()+":error");
		}
		dataBaseInfo.setDriverclass(driverclass);
		dataBaseInfo.setUrl(url);
		dataBaseInfo.setUsername(username);
		dataBaseInfo.setPassword(password);
		dataBaseInfo.setIsdefault(isdefault);
		dataBaseInfo.setDriverclass(driverclass);
		dataBaseInfo.setJndiName(jndiName);
		return dataBaseInfo;
	}
	
	public static void main(String[] args) throws Exception{
	}
	
}
