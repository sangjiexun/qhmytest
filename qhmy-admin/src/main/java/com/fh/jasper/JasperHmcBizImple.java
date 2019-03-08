package com.fh.jasper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fh.util.PageData;
import com.keman.zhgd.common.jasperreports.Field;
import com.keman.zhgd.common.jasperreports.JasperBizInterface;
import com.keman.zhgd.common.jasperreports.JasperStatic;
import com.keman.zhgd.common.jasperreports.Parameter;
import com.keman.zhgd.common.jasperreports.SubDataset;

/**
 * 花名册
 * @author Administrator
 *
 */
public class JasperHmcBizImple implements JasperBizInterface {
	
	/**
	 * jasper xml 模板文件路径 绝对路径
	 */
	private String jasperXmlPath;

	/**
	 * jasper xml 模板文件
	 */
	private File jasperXmlFile;

	/**
	 * jasper xml Document
	 */
	private Document document;

	private Element rootElement;
	
	/**
	 * 插入位置
	 */
	private int weizhiIndex;

	/**
	 * 业务数据
	 */
	private PageData pageData;

	public String getJasperXmlPath() {
		return jasperXmlPath;
	}

	public void setJasperXmlPath(String jasperXmlPath) {
		this.jasperXmlPath = jasperXmlPath;
	}

	public File getJasperXmlFile() {
		return jasperXmlFile;
	}

	public void setJasperXmlFile(File jasperXmlFile) {
		this.jasperXmlFile = jasperXmlFile;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Element getRootElement() {
		return rootElement;
	}

	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	public PageData getPageData() {
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}

	public int getWeizhiIndex() {
		return weizhiIndex;
	}

	public void setWeizhiIndex(int weizhiIndex) {
		this.weizhiIndex = weizhiIndex;
	}
	
	@Override
	public InputStream biz() {
		this.jasperXmlFile = new File(this.getJasperXmlPath());

		if (!this.jasperXmlFile.exists()) {
			return null;
		}

		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(this.jasperXmlFile);

			this.document = document;

			this.rootElement = this.document.getRootElement();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			List<PageData> banzuList = (List<PageData>) this.pageData
					.get("banzuList");

			Parameter parameter = null;

			List<SubDataset> subDatasetList = new ArrayList<SubDataset>();

			// xml的节点是有顺序的，cao
			List elementList = this.rootElement.elements();
			
			Element tempElement = null;
			
			
			for (int i = 0; i < elementList.size(); i++) {
				tempElement = (Element)elementList.get(i);
				if (tempElement.getName().equals("background")) {
					//属性节点需要插入到background之前
					this.weizhiIndex = i--;
					break;
				}
			}

			// end
			
			List<Parameter> fixParamList = new ArrayList<Parameter>();// 参数list
			
			List<Parameter> paramList = null;// 参数list
			
			createFixParameter(fixParamList);// 生成固定的参数值

			SubDataset subDataset = null;

			String xmlCode = null;
			
			List<Parameter> [] paraListArray = new ArrayList[banzuList.size()];//参数数组
			
			int size = 0;
			for (PageData pageData : banzuList) {
				
				paramList = new ArrayList<Parameter>();// 参数list
				
				paramList.addAll(fixParamList);//添加固定参数
				
				/*
				 * 参数
				 */
				parameter = new Parameter();
				parameter.setClassName(JasperStatic.LIST_TYPE);
				parameter.setName("tabledata" + size);// list集合数据
				paramList.add(parameter);
				this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter
						.createXml()));// 添加到dom节点中

				parameter = new Parameter();
				parameter.setClassName(JasperStatic.STRING_TYPE);
				parameter.setName("bzmc" + size);// 班组名称
				paramList.add(parameter);
				this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter
						.createXml()));// 添加到dom节点中

				parameter = new Parameter();
				parameter.setClassName(JasperStatic.STRING_TYPE);
				parameter.setName("heji" + size);// 总计
				paramList.add(parameter);
				this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter
						.createXml()));// 添加到dom节点中

				// end 参数
				
				paraListArray[size]= paramList;
						
				size++;
			}
			
			
			//subData必须在最前面
			for (int i = 0; i < banzuList.size(); i++) {
				/*
				 * subDataset
				 */
				subDataset = new SubDataset();
				subDataset.setName("Dataset" + i);
				subDataset.setUuid(UUID.randomUUID().toString());

				Parameter titleParameter = new Parameter();// 标题
				titleParameter.setName("title");
				titleParameter.setClassName(JasperStatic.STRING_TYPE);

				Parameter xmmcParameter = new Parameter();// 项目名称
				xmmcParameter.setName("xmmc");
				xmmcParameter.setClassName(JasperStatic.STRING_TYPE);

				Parameter bzmcParameter = new Parameter();// 班组名称
				bzmcParameter.setName("bzmc" + i);
				bzmcParameter.setClassName(JasperStatic.STRING_TYPE);

				Parameter riqiParameter = new Parameter();// 日期
				riqiParameter.setName("riqi");
				riqiParameter.setClassName(JasperStatic.STRING_TYPE);
				
				Parameter hejiParameter = new Parameter();// 合计
				hejiParameter.setName("heji"+i);
				hejiParameter.setClassName(JasperStatic.STRING_TYPE);

				subDataset.add(titleParameter);
				subDataset.add(xmmcParameter);
				subDataset.add(bzmcParameter);
				subDataset.add(riqiParameter);
				subDataset.add(hejiParameter);


				Field ghField = new Field();// 编号  工号 与数据库字段对应
				ghField.setClassName(JasperStatic.STRING_TYPE);
				ghField.setName("GONGHAO");
				subDataset.add(ghField);

				Field xmField = new Field();// 姓名
				xmField.setClassName(JasperStatic.STRING_TYPE);
				xmField.setName("XINGMING");
				subDataset.add(xmField);
				
				Field xingbieField = new Field();// 性别
				xingbieField.setClassName(JasperStatic.STRING_TYPE);
				xingbieField.setName("XINGBIE");
				subDataset.add(xingbieField);
				
				Field gzField = new Field();// 工种
				gzField.setClassName(JasperStatic.STRING_TYPE);
				gzField.setName("GZ_NAME");
				subDataset.add(gzField);
				
				
				Field jgField = new Field();// 籍贯
				jgField.setClassName(JasperStatic.STRING_TYPE);
				jgField.setName("JIGUAN");
				subDataset.add(jgField);
				
				Field sfzField = new Field();// 身份证
				sfzField.setClassName(JasperStatic.STRING_TYPE);
				sfzField.setName("SHFENFENZHENG");
				subDataset.add(sfzField);
				
				Field htbhField = new Field();// 合同编号
				htbhField.setClassName(JasperStatic.STRING_TYPE);
				htbhField.setName("HETONGBH");
				subDataset.add(htbhField);
				
				xmlCode = subDataset.createXml();//
				Element element = subDataset.xmlStrToElement(xmlCode);

				this.rootElement.elements().add(this.weizhiIndex,element);
				// end subDataset
				
				createComponentElement("Dataset"+i,i,"tabledata"+i,paraListArray[i]);//生成bind,table
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteArrayInputStream byteArrayInputStream = null;

		try {
			String xml = this.document.asXML();
			
			xml = xml.replaceAll("xmlns=\"\"", "");
			

			File file = new File("c:\\daochu.xml");

			// FileOutputStream fileOutputStream = new FileOutputStream(file);

			FileWriter fileOutputStream = new FileWriter(file);
			fileOutputStream.write(xml);

			fileOutputStream.flush();
			fileOutputStream.close();
			byteArrayInputStream = new ByteArrayInputStream(
					xml.getBytes("utf-8"));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return byteArrayInputStream;
	}
	
	
	
	private void createComponentElement(String datasetName, int setp,
			String p_list_name, List<Parameter> paramList) throws Exception {
		
		Document document = null;
		
		StringBuffer codeBuffer = new StringBuffer();

		Element detailElement = this.rootElement.element("detail");
		
		// band
		Element bandElement = detailElement.addElement("band");
		bandElement.addAttribute("height", "125");
		bandElement.addAttribute("splitType", "Stretch");
		
		
		// componentElement
		Element componentElement = bandElement.addElement("componentElement");

		// reportElement
		Element reportElement = componentElement.addElement("reportElement");
		reportElement.addAttribute("uuid", UUID.randomUUID().toString());
		reportElement.addAttribute("x", "0");
		reportElement.addAttribute("y", "0");
		reportElement.addAttribute("width", "555");//整个table的宽度
		reportElement.addAttribute("height", "125");
		
		
		// <jr:table
		// xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components"
		// xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd">
		Element tableElement = DocumentHelper.createElement("jr:table");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<datasetRun subDataset=\"");
		codeBuffer.append(datasetName);
		codeBuffer.append("\"  ");
		codeBuffer.append("uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" >");

		codeBuffer.append("\n");
		codeBuffer.append("\t");
		codeBuffer.append("\t");
		codeBuffer.append("\t");
		
		
		// <datasetParameter name="title">
		// <datasetParameterExpression><![CDATA[$P{title}]]></datasetParameterExpression>
		// </datasetParameter>
		Parameter parameter = null;
		for (int i = 0; i < paramList.size(); i++) {
			parameter = paramList.get(i);
			
			if (parameter.getName().indexOf("tabledata")!=-1) {
				continue;
			}
			
			codeBuffer.append("<datasetParameter name=\"");
			codeBuffer.append(parameter.getName());
			codeBuffer.append("\" >");
			codeBuffer.append("<datasetParameterExpression><![CDATA[$P{"
							+ parameter.getName()
							+ "}]]></datasetParameterExpression>");
			codeBuffer.append(" </datasetParameter> ");
		}

		codeBuffer
				.append("<dataSourceExpression><![CDATA[new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($P{"
						+ p_list_name + "})]]></dataSourceExpression>");
		codeBuffer.append("\n");
		codeBuffer.append("\t");
		codeBuffer.append("\t");
		codeBuffer.append("</datasetRun>");
		
		document = DocumentHelper.parseText(codeBuffer.toString());

		tableElement.add(document.getRootElement());
		// end datasetRun
		
		tableElement.addNamespace("jr",
				"http://jasperreports.sourceforge.net/jasperreports/components");
		
		tableElement.addNamespace(
				"schemaLocation",
				"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd");

		
//		<jr:columnGroup width="630" uuid="0f51d0ef-dd8a-435b-8b2c-d6ebcdbc5e0e">
		
		Element columnGroupElement = tableElement.addElement("jr:columnGroup");//第一组  987
		columnGroupElement.addAttribute("width", "630");
		columnGroupElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		//<jr:tableHeader height="44" rowSpan="1">
		Element tableHeaderElement = columnGroupElement
				.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "44");
		tableHeaderElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append(" <textField isBlankWhenNull=\"true\" > ");
		codeBuffer.append("   <reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" ");
		codeBuffer.append(" x=\"15\" y=\"0\" width=\"604\" height=\"44\" /> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" isStrikeThrough=\"false\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");

		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{title}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());
		//end  //jr:tableHeader
		
		
//		<jr:tableFooter height="30" rowSpan="1">
		Element tableFooterElement = columnGroupElement
				.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "30");
		tableFooterElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"0\" y=\"0\" width=\"90\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[合计]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"450\" y=\"0\" width=\"169\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Right\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{heji" + setp
				+ "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
//		合计  底部结束
		
		
//		<jr:columnGroup width="630" uuid="a77ca905-c1b1-4e0b-98a4-5bbe7f0de181">
		Element columnGroupElement2 = columnGroupElement
				.addElement("jr:columnGroup");
		columnGroupElement2.addAttribute("width", "630");//第二组   987
		columnGroupElement2.addAttribute("uuid", UUID.randomUUID().toString());
		
		tableHeaderElement = columnGroupElement2.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "46");
		tableHeaderElement.addAttribute("rowSpan", "1");
		
		
		//xmmc
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"258\" height=\"46\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{xmmc}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());
		
		//riqi
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"540\" y=\"0\" width=\"79\" height=\"46\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{riqi}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());
		
		
		//banzu
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"270\" y=\"0\" width=\"259\" height=\"46\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{bzmc" + setp + "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());
		
		
		//tableFooter
		tableFooterElement = columnGroupElement2.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "90");
		tableFooterElement.addAttribute("rowSpan", "1");
		
		
		//1
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"138\" height=\"90\"/> ");
		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[申明：此表登记劳务作业人员为我单位在此该工程全部人数，情况属实。]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		//2
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"138\" y=\"0\" width=\"404\" height=\"44\"/> ");
		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[班组长签字：_______________用工企业劳务管理员签字：_________________]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		
		//
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"138\" y=\"44\" width=\"402\" height=\"46\"/> ");
		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[用工企业项目负责人（授权队长）签字：____________填表时间：____________]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		
		//
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"542\" y=\"0\" width=\"88\" height=\"90\"/> ");
		codeBuffer
				.append(" <textElement verticalAlignment=\"Top\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[用工企业盖章：]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		//column  编号
		Element columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		Element columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[编号]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		Element columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		Element detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" > ");//有的字段如果是null，那么不在导出的界面中不显示null，只显示''
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{GONGHAO}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		//column  姓名
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[姓名]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$F{XINGMING}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		//column  性别
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[性别]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{XINGBIE}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		//column  编号
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[工种(岗位)]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{GZ_NAME}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		
		//column  籍贯
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[籍贯]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{JIGUAN}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		
		//column 身份证号
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[身份证号]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$F{SHFENFENZHENG}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		
		//column  合同编号
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "39");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[劳务合同编号]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		columnFooter = columnElement.addElement("jr:columnFooter");
		columnFooter.addAttribute("height", "0");
		columnFooter.addAttribute("rowSpan", "1");
		
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "36");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"1\" leftPadding=\"1\" bottomPadding=\"1\" rightPadding=\"1\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"85\" height=\"20\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{HETONGBH}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		componentElement.add(tableElement);
	}

	/**
	 * 生成报表参数
	 */
	private void createFixParameter(List<Parameter> paramList) {
		// <parameter name="tabledata1" class="java.util.List"/>
		// <parameter name="title" class="java.lang.String"/>
		// <parameter name="day0" class="java.lang.String"/>
		// <parameter name="riqi" class="java.lang.String"/>
		// <parameter name="heji" class="java.lang.String"/>
		// <parameter name="xmmc" class="java.lang.String"/>
		// <parameter name="bzmc" class="java.lang.String"/>

		// 标题 title ，每个子表都是一样的
		// 项目全程xmmc，每个子表一样
		// 日期一样,riqi
		/*
		 * 标题
		 */
		Parameter parameter = new Parameter();
		parameter.setClassName(JasperStatic.STRING_TYPE);
		parameter.setName("title");
		paramList.add(parameter);// 添加到参数容器
		this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter.createXml()));// 添加到dom节点中

		/*
		 * 项目名称
		 */
		parameter = new Parameter();
		parameter.setClassName(JasperStatic.STRING_TYPE);
		parameter.setName("xmmc");
		paramList.add(parameter);// 添加到参数容器
		this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter.createXml()));// 添加到dom节点中

		/*
		 * 日期
		 */
		parameter = new Parameter();
		parameter.setClassName(JasperStatic.STRING_TYPE);
		parameter.setName("riqi");
		paramList.add(parameter);// 添加到参数容器
		this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter.createXml()));// 添加到dom节点中

	}
	
	
	
	

}
