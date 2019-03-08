package com.keman.zhgd.common.jasperreports;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;

public class SubDataset extends AbsObjectCreateXml{
	
	private String name;
	
	private String uuid;
	
	private List<Parameter> parameterList = new ArrayList<Parameter>();
	
	private List<Field> fieldList = new ArrayList<Field>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}
	
//	<subDataset name="Table Dataset 2" uuid="1283dfe3-13db-4499-b10e-2577a31fb238">
//		<parameter name="title" class="java.lang.String"/>
//		<parameter name="heji" class="java.lang.String"/>
//		<parameter name="xmmc" class="java.lang.String"/>
//		<parameter name="bzmc" class="java.lang.String"/>
//		<parameter name="riqi" class="java.lang.String"/>
//		<parameter name="day0" class="java.lang.String"/>
//		<field name="xm" class="java.lang.String"/>
//	</subDataset>
	
	
	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	@Override
	public String createXml() {
		StringBuffer codeBuffer = new StringBuffer();
		codeBuffer.append("<subDataset name=\"");
		codeBuffer.append(this.getName()).append("\"  ");
		codeBuffer.append(" uuid=\"");
		codeBuffer.append(getUuid()).append("\" ");
		codeBuffer.append(" > ");
		
		if (this.parameterList!=null) {
			for (Parameter parameter : parameterList) {
				codeBuffer.append("\n \t");
				codeBuffer.append(parameter.createXml());
			}
		}
		
		if (this.fieldList!=null) {
			for (Field field : fieldList) {
				codeBuffer.append("\n  \t");
				codeBuffer.append(field.createXml());
			}
		}
		codeBuffer.append(" \n ");
		codeBuffer.append("</subDataset> ");
		return codeBuffer.toString();
	}

	
	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub
		super.add(object);
		if (object instanceof Parameter) {
			this.parameterList.add((Parameter) object);
		}else if (object instanceof Field) {
			this.fieldList.add((Field) object);
		}
	}

	@Override
	public void remove(Object object) {
		// TODO Auto-generated method stub
		super.remove(object);
		if (object instanceof Parameter) {
			this.parameterList.remove((Parameter) object);
		}else if (object instanceof Field) {
			this.fieldList.remove((Field) object);
		}
	}

	@Override
	public Object remove(int index) {
		return super.remove(index);
	}

	public static void main(String[] args) {
		SubDataset subDataset = new SubDataset();
		subDataset.setName("Table Dataset 3");
		subDataset.setUuid(UUID.randomUUID().toString());
		
		Parameter parameter = new Parameter();
		parameter.setName("zhongwen");
		parameter.setClassName(JasperStatic.STRING_TYPE);
		
		Parameter parameter2 = new Parameter();
		parameter2.setName("title2");
		parameter2.setClassName(JasperStatic.STRING_TYPE);
		
		subDataset.add(parameter);
		subDataset.add(parameter2);
		
		Field field1 = new Field();
		field1.setClassName(JasperStatic.STRING_TYPE);
		field1.setName("field1");
		
		subDataset.add(field1);
		
	 	String xmlCode = subDataset.createXml();
		
		Document document = subDataset.xmlStrToDom(xmlCode);
		
		System.out.println(document.asXML());
		
		Element element = subDataset.xmlStrToElement(xmlCode);
		
		System.out.println(element.asXML());
		
	}
	
	
	
}
