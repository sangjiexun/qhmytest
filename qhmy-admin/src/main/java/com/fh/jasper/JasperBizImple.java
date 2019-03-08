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
import com.fh.util.kaoqin.KaoqinHead;
import com.keman.zhgd.common.jasperreports.Field;
import com.keman.zhgd.common.jasperreports.JasperBizInterface;
import com.keman.zhgd.common.jasperreports.JasperStatic;
import com.keman.zhgd.common.jasperreports.Parameter;
import com.keman.zhgd.common.jasperreports.SubDataset;

public class JasperBizImple implements JasperBizInterface {

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

	/**
	 * 生成报表xml模板
	 */
	@Override
	public InputStream biz() {
		InputStream inputStream = null;

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

		// 生成<parameter name="tabledata1" class="java.util.List"/>
		// pd.put("year", year);
		// pd.put("month", month);
		// pd.put("banzuList", banzuList);//各个班组的以为数据
		// pd.put("kaoqinHeads", kaoqinHeads);//动态列的头
		// pd.put("preSize", preSize);//上个月的天数
		// pd.put("bySize", bySize);//本月的天数

		try {
			List<PageData> banzuList = (List<PageData>) this.pageData
					.get("banzuList");
			List<KaoqinHead> kaoqinHeads = (List<KaoqinHead>) this.pageData
					.get("kaoqinHeads");// 动态表头

			// <parameter name="tabledata1" class="java.util.List"/>

			

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
			
			createFixParameter(fixParamList, kaoqinHeads);// 生成固定的参数值

			SubDataset subDataset = null;

			String xmlCode = null;
			
			List<Parameter> [] paraListArray = new ArrayList[banzuList.size()];//参数数组
			
			int size = 0;
			for (PageData pageData : banzuList) {
				
				paramList = new ArrayList<Parameter>();// 参数list
				
				paramList.addAll(fixParamList);
				
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

				for (int j = 0; j < kaoqinHeads.size(); j++) {
					Parameter headParameter = new Parameter();// 总合计
					headParameter.setName("day" + j);
					headParameter.setClassName(JasperStatic.STRING_TYPE);
					subDataset.add(headParameter);
				}

				Field xmField = new Field();// 姓名 与数据库字段对应
				xmField.setClassName(JasperStatic.STRING_TYPE);
				xmField.setName("XINGMING");
				subDataset.add(xmField);

				Field hjField = new Field();// 小计 出勤天数
				hjField.setClassName(JasperStatic.STRING_TYPE);
				hjField.setName("CQTS");
				subDataset.add(hjField);

				// 动态字段
				for (int j = 0; j < kaoqinHeads.size(); j++) {
					Field field = new Field();
					field.setClassName(JasperStatic.STRING_TYPE);
					field.setName("GONG" + kaoqinHeads.get(j).getDay());
					subDataset.add(field);
				}
				xmlCode = subDataset.createXml();//
				Element element = subDataset.xmlStrToElement(xmlCode);
				
				
//				List<Element> parameterList = this.rootElement.elements("parameter");
//				for (Element element2 : parameterList) {
//					element2.
////					element2.addNamespace("", "http://jasperreports.sourceforge.net/jasperreports");
//					Namespace namespace = element2.getNamespace();
//					namespace.setText("http://jasperreports.sourceforge.net/jasperreports");
////					if (namespace!=null) {
////						element2.remove(namespace);//删除此属性
////					}
//				}
				
//				element.remove(element.attribute("xmlns"));//删除此属性

				System.out.println(element.asXML());

				this.rootElement.elements().add(this.weizhiIndex,element);
				// end subDataset
				
				//判断是否自然月
				if ("true".equals(this.pageData.getString("ZIRANYUE"))) {
					//自然月
					createComponentElementZry("Dataset"+i,i,"tabledata"+i,paraListArray[i],kaoqinHeads);//生成bind,table
				}else{
					//非自然月
					createComponentElement1("Dataset"+i,i,"tabledata"+i,paraListArray[i],kaoqinHeads);//生成bind,table
				}
				//end 判断会否自然月
				// this.rootElement.element("detail").add(element2);//添加到detail
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

	/**
	 * 生成报表参数
	 */
	private void createFixParameter(List<Parameter> paramList,
			List<KaoqinHead> kaoqinHeads) {
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

		// 动态列参数
		for (int i = 0; i < kaoqinHeads.size(); i++) {
			parameter = new Parameter();
			parameter.setClassName(JasperStatic.STRING_TYPE);
			parameter.setName("day" + i);// 日
			paramList.add(parameter);
			this.rootElement.elements().add(this.weizhiIndex,parameter.xmlStrToElement(parameter
					.createXml()));// 添加到dom节点中
		}

	}

	private int y = 0;

	private int setpHeight = 60;

	/**
	 * 非自然月实现
	 * @param datasetName
	 * @param setp
	 * @param p_list_name
	 * @param paramList
	 * @param kaoqinHeads
	 * @return
	 * @throws Exception
	 */
	private Element createComponentElement1(String datasetName, int setp,
			String p_list_name, List<Parameter> paramList,
			List<KaoqinHead> kaoqinHeads) throws Exception {
		// bind 高50
		// 宽 800 高40
		Document document = null;

		int preSize = Integer.parseInt(String.valueOf(this.pageData
				.get("preSize")));// 上个月天数
		int bySize = Integer.parseInt(String.valueOf(this.pageData
				.get("bySize")));// 本月的天数
		
		int kuandu = 30;//每个cell的宽度
		int preInt = preSize * kuandu;//总宽度，列群总宽度
		int byInt = bySize * kuandu;
		
		int xmWidth = 80;//姓名  80
		
		int hejiWidth = 80;//合计 80
		
		int width = xmWidth + hejiWidth + preInt+byInt;//table总宽度
		

		StringBuffer codeBuffer = new StringBuffer();

		Element detailElement = this.rootElement.element("detail");

		// band
		Element bandElement = detailElement.addElement("band");
		bandElement.addAttribute("height", "70");
		bandElement.addAttribute("splitType", "Stretch");

		// componentElement
		Element componentElement = bandElement.addElement("componentElement");

		// reportElement
		Element reportElement = componentElement.addElement("reportElement");
		reportElement.addAttribute("uuid", UUID.randomUUID().toString());
		reportElement.addAttribute("x", "0");
		reportElement.addAttribute("y", "0");
		reportElement.addAttribute("width", "800");//整个table的宽度
		reportElement.addAttribute("height", "40");

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

		tableElement
				.addNamespace("jr",
						"http://jasperreports.sourceforge.net/jasperreports/components");
		tableElement
				.addNamespace(
						"schemaLocation",
						"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd");

		// <jr:columnGroup width="987"
		// uuid="7184f860-5baf-48b5-a00f-080b09bb946e">

		Element columnGroupElement = tableElement.addElement("jr:columnGroup");//第一组  987
		columnGroupElement.addAttribute("width", String.valueOf(width));
		columnGroupElement.addAttribute("uuid", UUID.randomUUID().toString());

		Element tableHeaderElement = columnGroupElement
				.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "47");
		tableHeaderElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"0.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"0.0\"/>");
		codeBuffer.append("</box>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append(" <textField> ");
		codeBuffer.append("   <reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" ");
		codeBuffer.append(" x=\"249\" y=\"0\" width=\"495\" height=\"33\" /> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" isStrikeThrough=\"false\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");

		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{title}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		Element tableFooterElement = columnGroupElement
				.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "55");
		tableFooterElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer
				.append("\" x=\"12\" y=\"14\" width=\"754\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[申明：此表登记劳务作业人员为我单位本月在该工程全部出勤人数，出勤情况属实；我单位已将此表向全体劳务作业人员公示，均无异议。]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"900\" y=\"18\" width=\"76\" height=\"26\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{heji" + setp
				+ "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		Element columnGroupElement2 = columnGroupElement
				.addElement("jr:columnGroup");
		columnGroupElement2.addAttribute("width", String.valueOf(width));//第二组   987
		columnGroupElement2.addAttribute("uuid", UUID.randomUUID().toString());

		tableHeaderElement = columnGroupElement2.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "55");
		tableHeaderElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"5\" y=\"17\" width=\"364\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{xmmc}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"381\" y=\"17\" width=\"453\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{bzmc" + setp
				+ "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"854\" y=\"17\" width=\"100\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{riqi}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		tableFooterElement = columnGroupElement2.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "64");
		tableFooterElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"700\" height=\"28\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[班组长签字：_______________；用工企业劳务管理员签字：_________________，]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"28\" width=\"700\" height=\"28\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[用工企业项目负责人（授权队长）签字：____________；填表时间：____________ ;用工企业盖章：]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		// 姓名
		Element columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());

		Element columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "79");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"48\" height=\"52\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[姓名]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		Element detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "48");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"5\" y=\"0\" width=\"61\" height=\"39\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{XINGMING}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		// end

		// 上月
		Element columnGroupElement3 = columnGroupElement2
				.addElement("jr:columnGroup");
		columnGroupElement3.addAttribute("width", String.valueOf(preInt));//每个单元格宽度为 30   480
		columnGroupElement3.addAttribute("uuid", UUID.randomUUID().toString());

		// codeBuffer.append("<jr:columnGroup uuid=\"");
		// // codeBuffer.append("<jr:columnGroup width=\"264\" uuid=\"");
		// codeBuffer.append(UUID.randomUUID().toString());
		// codeBuffer.append("\" >");

		columnHeader = columnGroupElement3.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "38");
		columnHeader.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"11\" y=\"0\" width=\""+String.valueOf(200)+"\"  height=\"25\"/> ");//上月的静态文本
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[上月]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		/*
		 * 此处需要循环,上个月有几天，循环几次
		 */
		for (int i = 0; i < preSize; i++) {
			columnElement = columnGroupElement3.addElement("jr:column");
			columnElement.addAttribute("width", String.valueOf(kuandu));
			columnElement.addAttribute("uuid", UUID.randomUUID().toString());

			Element columnHeaderFor = columnElement
					.addElement("jr:columnHeader");
			columnHeaderFor.addAttribute("height", "41");
			columnHeaderFor.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderFor.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			
			codeBuffer
					.append("\" x=\"0\" y=\"0\" width=\""+(kuandu-5)+"\" height=\"20\"/> ");

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$P{day" + i
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderFor.add(document.getRootElement());

			Element detailCellElement = columnElement
					.addElement("jr:detailCell");
			detailCellElement.addAttribute("height", "48");
			detailCellElement.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			codeBuffer
					.append("\" x=\"0\" y=\"0\" width=\"20\" height=\"40\"/> ");

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$F{GONG"
					+ kaoqinHeads.get(i).getDay()
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());
		}
		// end 此处需要循环,上个月有几天，循环几次

		// 本月
		// <jr:columnGroup width="559"
		// uuid="bbe69aca-e7ff-4099-8017-ddf4d3711971">
		Element columnGroupElement4 = columnGroupElement2
				.addElement("jr:columnGroup");
		columnGroupElement4.addAttribute("width",String.valueOf(byInt));
		columnGroupElement4.addAttribute("uuid", UUID.randomUUID().toString());

		// codeBuffer.append("<jr:columnGroup uuid=\"");
		// // codeBuffer.append("<jr:columnGroup width=\"264\" uuid=\"");
		// codeBuffer.append(UUID.randomUUID().toString());
		// codeBuffer.append("\" >");

		// <jr:columnHeader height="38" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// <staticText>
		// <reportElement uuid="83c717cf-fe26-41e6-aa12-edfba58904bf" x="25"
		// y="0" width="525" height="25"/>
		// <textElement textAlignment="Center" verticalAlignment="Middle">
		// <font size="14" pdfFontName="STSong-Light"
		// pdfEncoding="UniGB-UCS2-H"/>
		// </textElement>
		// <text><![CDATA[本月]]></text>
		// </staticText>
		// </jr:columnHeader>

		Element columnHeaderElement = columnGroupElement4
				.addElement("jr:columnHeader");
		
		columnHeaderElement.addAttribute("height","38");
		columnHeaderElement.addAttribute("rowSpan","1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"13\" y=\"0\" width=\""+String.valueOf(200)+"\"  height=\"25\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[本月]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderElement.add(document.getRootElement());

		// <jr:column width="25" uuid="98b1af78-a67e-4cd4-95cc-2da17584f5fd">
		// <jr:columnHeader height="41" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// </jr:columnHeader>
		// <jr:detailCell height="48" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// </jr:detailCell>
		// </jr:column>
		for (int i = preSize; i < kaoqinHeads.size(); i++) {
			Element columnForElement = columnGroupElement4
					.addElement("jr:column");
			columnForElement.addAttribute("width", String.valueOf(kuandu));
			columnForElement.addAttribute("uuid", UUID.randomUUID().toString());

			Element columnHeaderForElement = columnForElement
					.addElement("jr:columnHeader");
			columnHeaderForElement.addAttribute("height", "41");
			columnHeaderForElement.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderForElement.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			codeBuffer.
			append("\" x=\"0\" y=\"0\" width=\""+(kuandu-5)+"\" height=\"20\"/> ");
			

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$P{day" + i
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderForElement.add(document.getRootElement());

			Element detailCellElement = columnForElement
					.addElement("jr:detailCell");
			detailCellElement.addAttribute("height", "48");
			detailCellElement.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			codeBuffer
					.append("\" x=\"0\" y=\"0\" width=\"20\" height=\"40\"/> ");

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$F{GONG"
					+ kaoqinHeads.get(i).getDay()
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());
		}

		// 合计
		Element columnHjElement = columnGroupElement2.addElement("jr:column");
		columnHjElement.addAttribute("uuid", UUID.randomUUID().toString());
		columnHjElement.addAttribute("width", "80");

		Element columnHeaderHjElement = columnHjElement
				.addElement("jr:columnHeader");
		columnHeaderHjElement.addAttribute("height", "79");
		columnHeaderHjElement.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderHjElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"18\" y=\"12\" width=\"58\" height=\"40\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[合计]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderHjElement.add(document.getRootElement());

		Element columnFooterHjElement = columnHjElement
				.addElement("jr:columnFooter");
		columnFooterHjElement.addAttribute("height", "0");
		columnFooterHjElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnFooterHjElement.add(document.getRootElement());

		Element detailCellElement = columnHjElement.addElement("jr:detailCell");
		detailCellElement.addAttribute("height", "48");
		detailCellElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCellElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"58\" height=\"40\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{CQTS}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCellElement.add(document.getRootElement());

		componentElement.add(tableElement);

		// end 合计结束
		return document.getRootElement();
	}
	
	
	/**
	 * 自然月
	 * @param datasetName
	 * @param setp
	 * @param p_list_name
	 * @param paramList
	 * @param kaoqinHeads
	 * @return
	 * @throws Exception
	 */
	private Element createComponentElementZry(String datasetName, int setp,
			String p_list_name, List<Parameter> paramList,
			List<KaoqinHead> kaoqinHeads) throws Exception {
		// bind 高50
		// 宽 800 高40
		Document document = null;

		int preSize = Integer.parseInt(String.valueOf(this.pageData
				.get("preSize")));// 上个月天数
		int bySize = Integer.parseInt(String.valueOf(this.pageData
				.get("bySize")));// 本月的天数
		
		int kuandu = 30;//每个cell的宽度
		int preInt = preSize * kuandu;//总宽度，列群总宽度
		int byInt = bySize * kuandu;
		
		int xmWidth = 80;//姓名  80
		
		int hejiWidth = 80;//合计 80
		
		int width = xmWidth + hejiWidth + preInt+byInt;//table总宽度
		

		StringBuffer codeBuffer = new StringBuffer();

		Element detailElement = this.rootElement.element("detail");

		// band
		Element bandElement = detailElement.addElement("band");
		bandElement.addAttribute("height", "70");
		bandElement.addAttribute("splitType", "Stretch");

		// componentElement
		Element componentElement = bandElement.addElement("componentElement");

		// reportElement
		Element reportElement = componentElement.addElement("reportElement");
		reportElement.addAttribute("uuid", UUID.randomUUID().toString());
		reportElement.addAttribute("x", "0");
		reportElement.addAttribute("y", "0");
		reportElement.addAttribute("width", "800");//整个table的宽度
		reportElement.addAttribute("height", "40");

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

		tableElement
				.addNamespace("jr",
						"http://jasperreports.sourceforge.net/jasperreports/components");
		tableElement
				.addNamespace(
						"schemaLocation",
						"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd");

		// <jr:columnGroup width="987"
		// uuid="7184f860-5baf-48b5-a00f-080b09bb946e">

		Element columnGroupElement = tableElement.addElement("jr:columnGroup");//第一组  987
		columnGroupElement.addAttribute("width", String.valueOf(width));
		columnGroupElement.addAttribute("uuid", UUID.randomUUID().toString());

		Element tableHeaderElement = columnGroupElement
				.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "47");
		tableHeaderElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"0.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"0.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"0.0\"/>");
		codeBuffer.append("</box>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append(" <textField> ");
		codeBuffer.append("   <reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" ");
		codeBuffer.append(" x=\"249\" y=\"0\" width=\"495\" height=\"33\" /> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" isStrikeThrough=\"false\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");

		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{title}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		Element tableFooterElement = columnGroupElement
				.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "55");
		tableFooterElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer
				.append("\" x=\"12\" y=\"14\" width=\"754\" height=\"30\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[申明：此表登记劳务作业人员为我单位本月在该工程全部出勤人数，出勤情况属实；我单位已将此表向全体劳务作业人员公示，均无异议。]]></text>");
		codeBuffer.append("</staticText>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"900\" y=\"18\" width=\"76\" height=\"26\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{heji" + setp
				+ "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		Element columnGroupElement2 = columnGroupElement
				.addElement("jr:columnGroup");
		columnGroupElement2.addAttribute("width", String.valueOf(width));//第二组   987
		columnGroupElement2.addAttribute("uuid", UUID.randomUUID().toString());

		tableHeaderElement = columnGroupElement2.addElement("jr:tableHeader");
		tableHeaderElement.addAttribute("height", "55");
		tableHeaderElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");

		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"5\" y=\"17\" width=\"364\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{xmmc}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"381\" y=\"17\" width=\"453\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<textFieldExpression><![CDATA[$P{bzmc" + setp
				+ "}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer
				.append("\" x=\"854\" y=\"17\" width=\"100\" height=\"30\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$P{riqi}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableHeaderElement.add(document.getRootElement());

		tableFooterElement = columnGroupElement2.addElement("jr:tableFooter");
		tableFooterElement.addAttribute("height", "64");
		tableFooterElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"700\" height=\"28\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[班组长签字：_______________；用工企业劳务管理员签字：_________________，]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"28\" width=\"700\" height=\"28\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<text><![CDATA[用工企业项目负责人（授权队长）签字：____________；填表时间：____________ ;用工企业盖章：]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		tableFooterElement.add(document.getRootElement());

		// 姓名
		Element columnElement = columnGroupElement2.addElement("jr:column");
		columnElement.addAttribute("width", "80");
		columnElement.addAttribute("uuid", UUID.randomUUID().toString());

		Element columnHeader = columnElement.addElement("jr:columnHeader");
		columnHeader.addAttribute("height", "79");
		columnHeader.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"48\" height=\"52\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[姓名]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeader.add(document.getRootElement());

		Element detailCell = columnElement.addElement("jr:detailCell");
		detailCell.addAttribute("height", "48");
		detailCell.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"5\" y=\"0\" width=\"61\" height=\"39\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{XINGMING}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCell.add(document.getRootElement());
		// end

		// 本月
		// <jr:columnGroup width="559"
		// uuid="bbe69aca-e7ff-4099-8017-ddf4d3711971">
		Element columnGroupElement4 = columnGroupElement2
				.addElement("jr:columnGroup");
		columnGroupElement4.addAttribute("width",String.valueOf(byInt));
		columnGroupElement4.addAttribute("uuid", UUID.randomUUID().toString());

		// codeBuffer.append("<jr:columnGroup uuid=\"");
		// // codeBuffer.append("<jr:columnGroup width=\"264\" uuid=\"");
		// codeBuffer.append(UUID.randomUUID().toString());
		// codeBuffer.append("\" >");

		// <jr:columnHeader height="38" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// <staticText>
		// <reportElement uuid="83c717cf-fe26-41e6-aa12-edfba58904bf" x="25"
		// y="0" width="525" height="25"/>
		// <textElement textAlignment="Center" verticalAlignment="Middle">
		// <font size="14" pdfFontName="STSong-Light"
		// pdfEncoding="UniGB-UCS2-H"/>
		// </textElement>
		// <text><![CDATA[本月]]></text>
		// </staticText>
		// </jr:columnHeader>

		Element columnHeaderElement = columnGroupElement4
				.addElement("jr:columnHeader");
		
		columnHeaderElement.addAttribute("height","38");
		columnHeaderElement.addAttribute("rowSpan","1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
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
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\""+String.valueOf(896)+"\"  height=\"25\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[本月]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderElement.add(document.getRootElement());

		// <jr:column width="25" uuid="98b1af78-a67e-4cd4-95cc-2da17584f5fd">
		// <jr:columnHeader height="41" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// </jr:columnHeader>
		// <jr:detailCell height="48" rowSpan="1">
		// <box topPadding="2" leftPadding="2" bottomPadding="2"
		// rightPadding="2">
		// <pen lineWidth="1.0"/>
		// <topPen lineWidth="1.0"/>
		// <leftPen lineWidth="1.0"/>
		// <bottomPen lineWidth="1.0"/>
		// <rightPen lineWidth="1.0"/>
		// </box>
		// </jr:detailCell>
		// </jr:column>
		for (int i = preSize; i < kaoqinHeads.size(); i++) {
			Element columnForElement = columnGroupElement4
					.addElement("jr:column");
			columnForElement.addAttribute("width", String.valueOf(kuandu));
			columnForElement.addAttribute("uuid", UUID.randomUUID().toString());

			Element columnHeaderForElement = columnForElement
					.addElement("jr:columnHeader");
			columnHeaderForElement.addAttribute("height", "41");
			columnHeaderForElement.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderForElement.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			codeBuffer.
			append("\" x=\"0\" y=\"0\" width=\""+(kuandu-5)+"\" height=\"20\"/> ");
			

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$P{day" + i
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			columnHeaderForElement.add(document.getRootElement());

			Element detailCellElement = columnForElement
					.addElement("jr:detailCell");
			detailCellElement.addAttribute("height", "48");
			detailCellElement.addAttribute("rowSpan", "1");

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer
					.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
			codeBuffer.append("<pen lineWidth=\"1.0\"/>");
			codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
			codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
			codeBuffer.append("</box>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());

			codeBuffer.delete(0, codeBuffer.length());
			codeBuffer.append("<textField>");
			codeBuffer.append("<reportElement uuid=\"");
			codeBuffer.append(UUID.randomUUID().toString());
			codeBuffer
					.append("\" x=\"0\" y=\"0\" width=\"20\" height=\"40\"/> ");

			codeBuffer
					.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
			codeBuffer
					.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
			codeBuffer.append("</textElement>");
			codeBuffer.append("<textFieldExpression><![CDATA[$F{GONG"
					+ kaoqinHeads.get(i).getDay()
					+ "}]]></textFieldExpression>");
			codeBuffer.append("</textField>");
			document = DocumentHelper.parseText(codeBuffer.toString());
			detailCellElement.add(document.getRootElement());
		}

		// 合计
		Element columnHjElement = columnGroupElement2.addElement("jr:column");
		columnHjElement.addAttribute("uuid", UUID.randomUUID().toString());
		columnHjElement.addAttribute("width", "80");

		Element columnHeaderHjElement = columnHjElement
				.addElement("jr:columnHeader");
		columnHeaderHjElement.addAttribute("height", "79");
		columnHeaderHjElement.addAttribute("rowSpan", "2");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderHjElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<staticText>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"18\" y=\"12\" width=\"58\" height=\"40\"/> ");
		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer.append("<text><![CDATA[合计]]></text>");
		codeBuffer.append("</staticText>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnHeaderHjElement.add(document.getRootElement());

		Element columnFooterHjElement = columnHjElement
				.addElement("jr:columnFooter");
		columnFooterHjElement.addAttribute("height", "0");
		columnFooterHjElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		columnFooterHjElement.add(document.getRootElement());

		Element detailCellElement = columnHjElement.addElement("jr:detailCell");
		detailCellElement.addAttribute("height", "48");
		detailCellElement.addAttribute("rowSpan", "1");

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer
				.append("<box topPadding=\"2\" leftPadding=\"2\" bottomPadding=\"2\" rightPadding=\"2\"> ");
		codeBuffer.append("<pen lineWidth=\"1.0\"/>");
		codeBuffer.append("<topPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<leftPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<bottomPen lineWidth=\"1.0\"/>");
		codeBuffer.append("<rightPen lineWidth=\"1.0\"/>");
		codeBuffer.append("</box>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCellElement.add(document.getRootElement());

		codeBuffer.delete(0, codeBuffer.length());
		codeBuffer.append("<textField>");
		codeBuffer.append("<reportElement uuid=\"");
		codeBuffer.append(UUID.randomUUID().toString());
		codeBuffer.append("\" x=\"0\" y=\"0\" width=\"58\" height=\"40\"/> ");

		codeBuffer
				.append(" <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"> ");
		codeBuffer
				.append(" <font size=\"14\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" /> ");
		codeBuffer.append("</textElement>");
		codeBuffer
				.append("<textFieldExpression><![CDATA[$F{CQTS}]]></textFieldExpression>");
		codeBuffer.append("</textField>");
		document = DocumentHelper.parseText(codeBuffer.toString());
		detailCellElement.add(document.getRootElement());

		componentElement.add(tableElement);

		// end 合计结束
		return document.getRootElement();
	}

	public static void main(String[] args) {
		StringBuffer codeBuffer = new StringBuffer();

		String datasetRun = "<subDataset name=\"Dataset0\" uuid=\"b9b7c6bf-2ed8-4a85-ac5c-78296bc8659c\">"
				+ " 	<parameter name=\"title\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"xmmc\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"bzmc0\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"riqi\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day0\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day1\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day2\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day3\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day4\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day5\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day6\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day7\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day8\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day9\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day10\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day11\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day12\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day13\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day14\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day15\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day16\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day17\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day18\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day19\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day20\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day21\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day22\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day23\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day24\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day25\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day26\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day27\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day28\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day29\" class=\"java.lang.String\"/> 		     "
				+ " 	<parameter name=\"day30\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"XINGMING\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"CQTS\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG16\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG17\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG18\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG19\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG20\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG21\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG22\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG23\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG24\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG25\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG26\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG27\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG28\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG29\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG30\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG31\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG01\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG02\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG03\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG04\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG05\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG06\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG07\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG08\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG09\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG10\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG11\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG12\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG13\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG14\" class=\"java.lang.String\"/> 		     "
				+ "  	<field name=\"GONG15\" class=\"java.lang.String\"/>  		     "
				+ "</subDataset>								     ";

		Element tableElement = DocumentHelper.createElement("jr:table");
		tableElement
				.addNamespace("jr",
						"http://jasperreports.sourceforge.net/jasperreports/components");
		tableElement
				.addNamespace(
						"schemaLocation",
						"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd");

		Element element = null;
		try {

			Document document = DocumentHelper.parseText(datasetRun);

			element = document.getRootElement();

			List list = element.elements();

			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				System.out.println(obj);
				System.out.println(list.get(i));
			}

			Element element2 = DocumentHelper.createElement("xm");

			list.add(1, element2);

			System.out.println(document.asXML());

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableElement.add(element);

		System.out.println(tableElement.asXML());

		tableElement.addElement("jr:table");

	}

}
