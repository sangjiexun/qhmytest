package com.keman.zhgd.common.maputil;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.keman.zhgd.common.datetime.DateUtil;


@SuppressWarnings("unchecked")
public class XmlCreateRst {
	
	/**
	 * 返回给客户端下载的结果数据:txml文档的head部分
	 * @param rstArrays
	 * @return
	 */
	public static Document createdownloadReturnXml_head(BaseVo baseVo,Map paramMap,Document document) {
		Element root = DocumentHelper.createElement("BUSINESS");
		document.setRootElement(root);
		root.addAttribute("ID", "SDS10");
		root.addAttribute("COMMENT", "房主下载信息");
		Element headElement = root.addElement("HEAD");
		headElement.addElement("RETURNCODE").setText(String.valueOf(paramMap.get("RETURNCODE")));//返回代码
		headElement.addElement("RETURNMESSAGE").setText(String.valueOf(paramMap.get("RETURNMESSAGE")));//返回信息
		headElement.addElement("QYSH").setText(baseVo.getQysh());//房主税号		
		headElement.addElement("SCSJ").setText(DateUtil.getSysdate("yyyy-MM-dd HH:mm:ss"));//生成时间
		headElement.addElement("QSSSQ").setText(baseVo.getQsssq());
		headElement.addElement("JZSSQ").setText(baseVo.getJzssq());
		return document;
	}
	
	/**
	 * 根据某个表及结果集生成一个tablename节点
	 * @param resultSet
	 * @param document
	 * @return
	 */
	public static Document createTableNodeInfo(List fieldNameList,String opType,String tableName,ResultSet resultSet,Document document) throws Exception{
		Element element = document.getRootElement();
		Element tableElement = element.addElement(tableName);
		Element rowElement = null;
		int fieldNameSzie = fieldNameList.size();
		while(resultSet.next()){
			rowElement = tableElement.addElement("ROW");//得到行节点
			//rowElement.addAttribute("keyname", String.valueOf(fieldNameList.get(0)));//主键名称
			for (int i = 0; i < fieldNameSzie; i++) {
				rowElement.addAttribute(String.valueOf(fieldNameList.get(i)), StringUtils.toGBK(String.valueOf(resultSet.getObject(String.valueOf(fieldNameList.get(i))))));
			}
		}
		return document;
	}
	

	/**
	 * 返回给客户端下载失败的结果数据文档
	 */
	public static Document downloadError(BaseVo baseVo) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = DocumentHelper.createElement("BUSINESS");
		document.setRootElement(root);
		root.addAttribute("ID", "SDS10");
		root.addAttribute("COMMENT", "房主下载信息");
		Element headElement = root.addElement("HEAD");
		headElement.addElement("RETURNCODE").setText(baseVo.getReturncode());//返回代码
		headElement.addElement("RETURNMESSAGE").setText(baseVo.getReturnmessage());//返回信息
		headElement.addElement("QYSH").setText(baseVo.getQysh());//房主税号
		headElement.addElement("SCSJ").setText(DateUtil.getSysdate("yyyy-MM-dd HH:mm:ss"));//生成时间
		headElement.addElement("QSSSQ").setText(baseVo.getQsssq());
		headElement.addElement("JZSSQ").setText(baseVo.getJzssq());
		System.out.println(document.asXML());
		return document;
	}

	/**
	 * 返回给客户端上传失败的结果数据文档
	 * @param baseVo
	 * @return
	 */
	public static Document uploadError(BaseVo baseVo) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = DocumentHelper.createElement("root");
		document.setRootElement(root);
		root.addElement("RETURNCODE").setText(baseVo.getReturncode());//返回代码
		root.addElement("RETURNMESSAGE").setText(baseVo.getReturnmessage());//返回信息
		System.out.println(document.asXML());
		return document;
	}
	
}
