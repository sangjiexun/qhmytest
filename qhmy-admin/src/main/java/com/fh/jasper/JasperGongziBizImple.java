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

public class JasperGongziBizImple implements JasperBizInterface {

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

//				parameter = new Parameter();
//				parameter.setClassName(JasperStatic.STRING_TYPE);
//				parameter.setName("heji" + size);// 总计
//				paramList.add(parameter);
//				this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter
//						.createXml()));// 添加到dom节点中

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
				
//				Parameter hejiParameter = new Parameter();// 合计
//				hejiParameter.setName("heji"+i);
//				hejiParameter.setClassName(JasperStatic.STRING_TYPE);

				subDataset.add(titleParameter);
				subDataset.add(xmmcParameter);
				subDataset.add(bzmcParameter);
				subDataset.add(riqiParameter);
//				subDataset.add(hejiParameter);


				Field ghField = new Field();// 序号 与数据库字段对应
				ghField.setClassName(JasperStatic.STRING_TYPE);
				ghField.setName("XUHAO");
				subDataset.add(ghField);

				Field xmField = new Field();// 工人姓名
				xmField.setClassName(JasperStatic.STRING_TYPE);
				xmField.setName("XINGMING");
				subDataset.add(xmField);
				
				Field xingbieField = new Field();// 工号
				xingbieField.setClassName(JasperStatic.STRING_TYPE);
				xingbieField.setName("GONGHAO");
				subDataset.add(xingbieField);
				
				Field gzField = new Field();// 门禁考勤(天)
				gzField.setClassName(JasperStatic.STRING_TYPE);
				gzField.setName("MENJINKAOQIN");
				subDataset.add(gzField);
				
				
				Field jgField = new Field();// 实际考勤(天)
				jgField.setClassName(JasperStatic.STRING_TYPE);
				jgField.setName("CHUQINTIANSHU");
				subDataset.add(jgField);
				
				Field sfzField = new Field();// 工资标准
				sfzField.setClassName(JasperStatic.STRING_TYPE);
				sfzField.setName("GONGZIBIAOZHUN");
				subDataset.add(sfzField);
				
				Field htbhField = new Field();// 当月工资结算
				htbhField.setClassName(JasperStatic.STRING_TYPE);
				htbhField.setName("DANGYUEGZJS");
				subDataset.add(htbhField);
				
				
				
				Field htbhField1 = new Field();// 生活费
				htbhField1.setClassName(JasperStatic.STRING_TYPE);
				htbhField1.setName("SHENGHUOFEI");
				subDataset.add(htbhField1);

				
				Field htbhField2 = new Field();// 预支费
				htbhField2.setClassName(JasperStatic.STRING_TYPE);
				htbhField2.setName("YUZHIFEI");
				subDataset.add(htbhField2);
				
				Field htbhField3 = new Field();// 罚款
				htbhField3.setClassName(JasperStatic.STRING_TYPE);
				htbhField3.setName("FAKUAN");
				subDataset.add(htbhField3);
				
				Field htbhField4 = new Field();// 其他
				htbhField4.setClassName(JasperStatic.STRING_TYPE);
				htbhField4.setName("QITA");
				subDataset.add(htbhField4);
				
				Field htbhField5 = new Field();// 本月实际支付
				htbhField5.setClassName(JasperStatic.STRING_TYPE);
				htbhField5.setName("BENYUESJZF");
				subDataset.add(htbhField5);
				
				Field htbhField6 = new Field();// 未支付数
				htbhField6.setClassName(JasperStatic.STRING_TYPE);
				htbhField6.setName("WEIZHIFUS");
				subDataset.add(htbhField6);
				
//				Field htbhField = new Field();// 领款人签字
//				htbhField.setClassName(JasperStatic.STRING_TYPE);
//				htbhField.setName("DANGYUEGZJS");
//				subDataset.add(htbhField);
				
				Field htbhField7 = new Field();// 备注
				htbhField7.setClassName(JasperStatic.STRING_TYPE);
				htbhField7.setName("BEIZHU");
				subDataset.add(htbhField7);
				
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
		reportElement.addAttribute("width", "1160");//整个table的宽度
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
		columnGroupElement.addAttribute("width", "1150");
		columnGroupElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		//<jr:tableHeader height="44" rowSpan="1">
		Element tableHeaderElement = columnGroupElement.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "43");
		tableHeaderElement.addAttribute("rowSpan", "1");
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append(" <textField isBlankWhenNull=\"true\" > ");
		codeBuffer.append("   <reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" ");
		codeBuffer.append(" x=\"121\" y=\"0\" width=\"800\" height=\"30\" /> ");
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
		tableFooterElement.addAttribute("height", "69");
		tableFooterElement.addAttribute("rowSpan", "1");
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"318\" height=\"69\"/> ");

		codeBuffer.append(" <textElement textAlignment=\"Left\" verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[申明：此表登记劳务作业人员为我单位本月在该工程全部人数；工资结算、支付、领取情况属实，均系本人签字。]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"318\" y=\"0\" width=\"500\" height=\"34\"/> ");

		codeBuffer.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[班组长签字：_______________；用工企业劳务管理员签字：_________________，]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"318\" y=\"34\" width=\"500\" height=\"35\"/> ");

		codeBuffer.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[用工企业项目负责人（授权队长）签字：____________；填表时间：____________]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"862\" y=\"0\" width=\"288\" height=\"69\"/> ");
		codeBuffer.append(" <textElement textAlignment=\"Left\" verticalAlignment=\"Top\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[用工企业盖章：]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		
//		 底部结束
		
		
//		<jr:columnGroup width="630" uuid="a77ca905-c1b1-4e0b-98a4-5bbe7f0de181">
		Element columnGroupElement2 = columnGroupElement
				.addElement("jr:columnGroup");
		columnGroupElement2.addAttribute("width", "1150");//第二组   987
		columnGroupElement2.addAttribute("uuid", UUID.randomUUID().toString());
		
		tableHeaderElement = columnGroupElement2.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "36");
		tableHeaderElement.addAttribute("rowSpan", "1");
		
		
		//xmmc
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"48\" y=\"0\" width=\"360\" height=\"30\"/> ");
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
		
		//banzu
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"408\" y=\"0\" width=\"360\" height=\"30\"/> ");
		codeBuffer.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{bzmc" + setp + "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());
		
		//riqi
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField isBlankWhenNull=\"true\" >");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"970\" y=\"6\" width=\"180\" height=\"30\"/> ");
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
		
		
		//tableFooter
		tableFooterElement = columnGroupElement2.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "59");
		tableFooterElement.addAttribute("rowSpan", "1");
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());
		//end 
		
		
		//column1  序号
		Element columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "62");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		Element columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"60\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[序号]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		
		
		Element detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"60\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{XUHAO}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 序号
		
		
		//column1  工人姓名
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[工人姓名]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{XINGMING}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		//end 工人姓名
		
		
		//column1  工号
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[工号]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

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
		//end 工号
		
		
		//column1  门禁考勤(天)
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[门禁考勤(天)]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{MENJINKAOQIN}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 门禁考勤（天）
		
		
		//column1 实际考勤(天)
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[实际考勤(天)]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{CHUQINTIANSHU}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 实际考勤（天）
		
		//column1 工资标准
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[工资标准]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{GONGZIBIAOZHUN}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 工资标准
		

		//当月工资结算
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[当月工资结算]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{DANGYUEGZJS}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 当月工资结算
		
		
		
		Element columnGroupElement3 = columnGroupElement2.addElement("jr:columnGroup");
		columnGroupElement3.addAttribute("width", "370");//第三组
		columnGroupElement3.addAttribute("uuid", UUID.randomUUID().toString());
		
		Element columnHeaderElement = columnGroupElement3.addElement("jr:columnHeader");
		columnHeaderElement.addAttribute("height", "30");
		columnHeaderElement.addAttribute("rowSpan", "1");
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderElement.add(document.getRootElement());
		
		
		
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"370\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[支出部分]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderElement.add(document.getRootElement());
		
		
		//生活费
		columnElement = columnGroupElement3.addElement("jr:column");
		columnElement.addAttribute("width", "70");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "30");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[生活费]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{SHENGHUOFEI}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 生活费
		
		
		//预支费
		columnElement = columnGroupElement3.addElement("jr:column");
		columnElement.addAttribute("width", "70");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "30");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[预支费]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{YUZHIFEI}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 预支费
		
		
		//罚款
		columnElement = columnGroupElement3.addElement("jr:column");
		columnElement.addAttribute("width", "70");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "30");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[罚款]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{FAKUAN}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 罚款
		
		
		//其他
		columnElement = columnGroupElement3.addElement("jr:column");
		columnElement.addAttribute("width", "70");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "30");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[其他]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{QITA}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 其他
		
		
		//本月实际支付
		columnElement = columnGroupElement3.addElement("jr:column");
		columnElement.addAttribute("width", "90");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "30");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"90\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[本月实际支付]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"70\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{BENYUESJZF}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 本月实际支付
		//第三组结束
		
		
		
		//未支付数
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[未支付数]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{WEIZHIFUS}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 未支付数
		
		
		
		
		//领款人签字
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"80\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[领款人签字]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");
		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		
		//end 领款人签字
		
		
		//备注
		columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "78");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());
		
		
		columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "60");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<box>");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"78\" height=\"60\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[备注]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());
		
		detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "34");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"78\" height=\"34\"/> ");

		codeBuffer
				.append(" <textElement verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{BEIZHU}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		
		//end 备注
		
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
