package com.fh.controller.demo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/demo")
public class DemoController extends BaseController {
	
	@RequestMapping(value="/zhika.php")
	public ModelAndView zhika() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.addObject("pd",pd);
		mv.setViewName("zhika");
		return mv;
	}
	
	
	@RequestMapping(value="/print.php")
	public ModelAndView print() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.addObject("pd",pd);
		mv.setViewName("print");
		return mv;
	}
	
	@RequestMapping(value="/printexce.json")
	public void printexce(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String format = "pdf";
		
//		//准备业务数据,如：班组列表，需要循环显示几个表格的条件数据,列数不固定的话
//		String reportFileName = "E:\\luzhen\\src\\myeclipse2014-02\\zhgd\\zhgd-fhadmin Maven Webapp\\src\\main\\webapp\\WEB-INF\\dymb\\dtsc.jrxml";
//		
//		JasperBizImple jasperBizImple = new JasperBizImple();
//		jasperBizImple.setJasperXmlPath(reportFileName);
//		InputStream mobanXmlInputStream = jasperBizImple.biz();
//		
//		JasperDesign jdesign = JRXmlLoader.load(mobanXmlInputStream);//读取模板文件
//		
//		JasperReport jasperReport = JasperCompileManager.compileReport(jdesign);//编译成报表
////      String destFileName = "c:\\dtsc.jasper";
////      JasperCompileManager.compileReportToFile(jdesign, destFileName);
		
		
		
		
		//生成数据，填充数据，导出pdf
//		Map<String, Object> model = new HashMap<String,Object>();
//		JasperPrint jasperPrint = JasperFillManager.fillReport(
//				jasperReport, model,new JREmptyDataSource());
//		
//		ServletOutputStream outputStream = response.getOutputStream();
//		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//		
//		outputStream.flush();
//		outputStream.close();
		
//
//JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//
//outputStream.flush();
//outputStream.close();
		
		
		/*
		 * 使用jasper api动态生成模板
		 */
		//步骤1：获取JasperDesign
//		String reportFileName = "E:\\luzhen\\src\\myeclipse2014-02\\zhgd\\zhgd-fhadmin Maven Webapp\\src\\main\\webapp\\WEB-INF\\dymb\\dtsc.jrxml";
//        
//		
//		JasperDesign jdesign = JRXmlLoader  
//                .load(reportFileName);//读取模板文件
//        
//        
//        /*
//         * 添加报表参数
//         */
//        JRDesignParameter parameter = new JRDesignParameter();//参数对象
////        parameter.setName("title");//标题
////        parameter.setValueClass(java.lang.String.class);
////        jdesign.addParameter(parameter);
////        System.out.println("报表名称:"+jdesign.getName());//table1
//        
//        parameter = new JRDesignParameter();//参数对象
//        parameter.setName("tableData1");//存放表格数据的集合
//        parameter.setValueClass(java.util.List.class);
//        jdesign.addParameter(parameter);
//        //end 添加参数
//        
//        
//        /*
//         * 生成  DataSet,并且添加字段
//         */
//        JRDesignDataset dataset = new JRDesignDataset(false);
//        dataset.setName("dataset1");
//        JRDesignField jrDesignFieldName = new JRDesignField();//添加字段
//        jrDesignFieldName.setName("name");
//        jrDesignFieldName.setValueClass(java.lang.String.class);
//        dataset.addField(jrDesignFieldName);
//        
//        JRDesignField jrDesignFieldAge = new JRDesignField();//添加字段
//        jrDesignFieldAge.setName("age");
//        jrDesignFieldAge.setValueClass(java.lang.String.class);
//        dataset.addField(jrDesignFieldAge);
//        
//        jdesign.addDataset(dataset);//添加DataSet
//        
//        //end 生成  DataSet,并且添加字段
//        
////      JRDesignBand summary = new JRDesignBand();
//        
//        JRDesignBand summary = (JRDesignBand) jdesign.getSummary();//获取已有的summary bind
//        
//        /*
//         * 生成table组件
//         */
//        JRDesignComponentElement jrdComponentElement = new JRDesignComponentElement();//创建组件对象
//        StandardTable table = new StandardTable();//创建table对象
//        
//        List<BaseColumn> tableColumn = new ArrayList<BaseColumn>();//字段集合
//        
//        StandardColumn nameColumn = new StandardColumn();//talbe字表的，姓名字段
//        StandardColumn ageColumn = new StandardColumn();//标准列对象,年龄字段
//        
//        
//        JRDesignStaticText jrStaticText = new JRDesignStaticText();//静态文本
//        jrStaticText.setWidth(50);
//        jrStaticText.setHeight(30);
//        jrStaticText.setText("姓名");
////        jrStaticText.setX(20);
////        jrStaticText.setY(0);
//        jrStaticText.setFontSize(14);
//        
//        JRDesignFont jFont = new JRDesignFont(jrStaticText);
//        jFont.setPdfEmbedded(true);
//        jFont.setPdfEncoding("UniGB-UCS2-H");
//        jFont.setPdfFontName("STSong-Light");
//        
////        jrStaticText.setFont(jFont);//设置字体
////        jrStaticText.setfont
//        
//        
//        JRDesignStaticText jrStaticText2 = new JRDesignStaticText();//静态文本
//        jrStaticText2.setWidth(50);
//        jrStaticText2.setHeight(30);
//        jrStaticText2.setText("年龄");
////        jrStaticText.setX(20);
////        jrStaticText.setY(0);
//        jrStaticText2.setFontSize(14);
//        
//        JRDesignFont jFontAge = new JRDesignFont(jrStaticText2); 
//        jFontAge.setPdfEmbedded(true);
//        jFontAge.setPdfEncoding("UniGB-UCS2-H");
//        jFontAge.setPdfFontName("STSong-Light");
////        jrStaticText.setFont(jFont);//设置字体
////        jrStaticText.setfont
//        
//        
//        DesignCell nameCell = new DesignCell();//表格cell
//        nameCell.addElement(jrStaticText);
//        nameCell.setHeight(30);
//        nameColumn.setColumnHeader(nameCell);
//        
//        
//        DesignCell ageCell = new DesignCell();//表格cell
//        ageCell.addElement(jrStaticText2);
//        ageCell.setHeight(30);
//        ageColumn.setColumnHeader(ageCell);
//        
//        
//        
//        //表达式
//        JRDesignTextField jrDesignTextField = new JRDesignTextField();
//        JRDesignExpression expression = new JRDesignExpression();
//        expression.setText("$F{name}");
//        expression.setValueClass(String.class);
//        
//        jrDesignTextField.setExpression(expression);
//        jrDesignTextField.setFontSize(14);
//        jrDesignTextField.setHeight(30);
//        jrDesignTextField.setWidth(80);
//        jrDesignTextField.setX(20);
//        jrDesignTextField.setFont(jFont);
//        //end 表达式
//        
//        
//        DesignCell nameCellData = new DesignCell();
//        nameCellData.setHeight(30);
//        nameCellData.addElement(jrDesignTextField);
//        nameColumn.setDetailCell(nameCellData);
//        nameColumn.setWidth(126);
//        
//        tableColumn.add(nameColumn);
//        
//        
//        
//        
//      //表达式
//        JRDesignTextField jrDesignTextField2 = new JRDesignTextField();
//        JRDesignExpression expression2 = new JRDesignExpression();
//        expression2.setText("$F{age}");
//        expression2.setValueClass(String.class);
//        
//        jrDesignTextField2.setExpression(expression2);
//        jrDesignTextField2.setFontSize(14);
//        jrDesignTextField2.setHeight(30);
//        jrDesignTextField2.setWidth(80);
//        jrDesignTextField2.setX(20);
//        jrDesignTextField2.setFont(jFontAge);
//        //end 表达式
//        
//        
//        
//        DesignCell ageCellData = new DesignCell();
//        ageCellData.setHeight(30);
//        ageCellData.addElement(jrDesignTextField2);
//        ageColumn.setDetailCell(ageCellData);
//        ageColumn.setWidth(126);
//        
//        tableColumn.add(ageColumn);
//        
//        table.setColumns(tableColumn);
//        
//        //end 生成table组件
//        
//        
//        /*
//         * table 绑定使用哪个dataset
//         */
////        List<JRDataset> jrDatasets = jdesign.getDatasetsList();
////        for (JRDataset jrDataset : jrDatasets) {
////			System.out.println(jrDataset.getName());
////		}
//        JRDesignDatasetRun datasetRun = new JRDesignDatasetRun();
//        JRDesignExpression expression1 = new JRDesignExpression();
//        expression1.setText("new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($P{table1})");
//        datasetRun.setDataSourceExpression(expression1);
//        datasetRun.setDatasetName("list3");
//        //end table 绑定使用哪个dataset
//        
//        table.setDatasetRun(datasetRun);
//        
//        
//        jrdComponentElement.setComponent(table);//设置table组件
//        jrdComponentElement.setHeight(100);
//        jrdComponentElement.setWidth(504);
//        jrdComponentElement.setX(30);
//        jrdComponentElement.setY(100);
//        ComponentKey c = new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components","jr", "table");
//        jrdComponentElement.setComponentKey(c);
//        
//        summary.addElement(jrdComponentElement);
//        
//        jdesign.setSummary(summary);
//        
//        //生成xml文档
//        
//        
//        //编译，生成报表
//        JasperReport jasperReport = JasperCompileManager.compileReport(jdesign);//
//        String destFileName = "c:\\dtsc.jasper";
//        JasperCompileManager.compileReportToFile(jdesign, destFileName);
//        
//        
//        
//        
//        
//        /*
//         * 填充数据，导出pdf
//         */
//        List<PageData> list = new ArrayList<PageData>();
//		PageData pageData1 = new PageData();
//		PageData pageData2 = new PageData();
//		pageData1.put("name", "luzhen");
//		pageData1.put("age", 18);
//		
//		pageData2.put("name", "zhangsan");
//		pageData2.put("age", 19);
//		
//		list.add(pageData1);
//		list.add(pageData2);
//		
//		List<PageData> list2 = new ArrayList<PageData>();
//		PageData pageData3 = new PageData();
//		PageData pageData4 = new PageData();
//		pageData3.put("name", "lisi");
//		pageData3.put("age", "11");
//		
//		pageData4.put("name", "wang");
//		pageData4.put("age", "12");
//		
//		for (int i = 0; i < 20; i++) {
//			PageData pageData = new PageData();
//			pageData.put("name", "lulu"+i);
//			pageData.put("age", "1"+i);
//			list2.add(pageData);
//		}
//		
//		
//		for (int i = 0; i < 50; i++) {
//			PageData pageData = new PageData();
//			pageData.put("name", "list"+i);
//			pageData.put("age", 1+i);
//			list.add(pageData);
//		}
//		
//		list2.add(pageData3);
//		list2.add(pageData4);
//		
//		Map<String, Object> model = new HashMap<String,Object>();
//		model.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY, format);
//		model.put("JRExporterParameter.CHARACTER_ENCODING", "GBK");
//		model.put("list1", list);
//		model.put("list2", list2);
////		model.put("table1", list2);
//		model.put("biaoti1", "动态生成table控件");
//        
//        ServletOutputStream outputStream = response.getOutputStream();
//        
////        reportFileName = "E:\\luzhen\\src\\myeclipse2014-02\\zhgd\\zhgd-fhadmin Maven Webapp\\src\\main\\webapp\\WEB-INF\\dymb\\dtsc.jasper";
//        
//		JasperPrint jasperPrint = JasperFillManager.fillReport(
//				jasperReport, 
//			model,
//			new JREmptyDataSource()
//		);
//
//		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//
//		outputStream.flush();
//		outputStream.close();
		/*
		 * end   使用jasper api动态生成模板，失败
		 */
		
		
		
		
		
        
        
        //生成Detail
//        JRDesignBand band = new JRDesignBand();
//        band.setHeight(20);
//        
////        JRDesignBand detail = new JRDesignBand();
//        
//        JRDesignStaticText staticText = new JRDesignStaticText();//静态文本
        
//        jdesign.getDetailSection().
        
//      JRBand [] jrBands = jdesign.getDetailSection().getBands();
        
//        JRSection jrSection = jdesign.getDetailSection();//获取Detail的各个部分
//        
//        
//        for (int i = 0; i < jrBands.length; i++) {
//			System.out.println(jrBands[i]);
//		}
        
        
//        jdesign.
        
        
        
        
        
//        expression = new JRDesignExpression();
        
        
        
//        net.sf.jasperreports.engine.xml.JRXmlLoader  读报表模板
//        net.sf.jasperreports.engine.xml.JRXmlWriter  写报表模板
//        net.sf.jasperreports.engine.JasperReport 报表
//        net.sf.jasperreports.engine.JasperCompileManager 编译管理类
        
//        Map<String, Object> params = new HashMap<String, Object>();  
//        params.put("dynamiccolumn",  
//                java.util.Arrays.asList(new String[] { "Name","Age","lll"}));
        
//        JasperPrint jprint = JasperFillManager.fillReport(jreport, params,  
//                conn);  
//        JasperExportManager.exportReportToHtmlFile(jprint,  
//                "reports//dynamiccolumn.html"); 
//        JRDesignBand cHeader = (JRDesignBand) jdesign.getColumnHeader();//获得ColumnHeader  band
//        
//        //得到Detail
//        JRBand cDetailBand = jdesign.getDetailSection().getBands()[0];//得到第一个Detail
//        JRDesignBand cDetail = null;  
//        if (cDetailBand != null && cDetailBand instanceof JRDesignBand) {  
//            cDetail = (JRDesignBand) cDetailBand;  
//        }
//        //end 得到Detail
//        
//        JRElement[] es_header = cHeader.getElements();
//        JRElement[] es_detail = cDetail.getElements();
//        
//        for (int i = 0; i < es_header.length; i++) {  
//            JRDesignElement e = (JRDesignElement) es_header[i];  
//            String v = "";  
//            if (e instanceof JRStaticText) {  
//                JRStaticText text = (JRStaticText) e;  
//                v = text.getText();  
//            }
//            
//            for (int j = 0; j < es_detail.length; j++) {  
//                JRDesignElement ee = (JRDesignElement) es_detail[i];  
//                if (ee.getY() == e.getY()) {  
//                    cDetail.removeElement(ee);  
//                }  
//            }
//            cHeader.removeElement(e);  
//        }
//        
//        
//        // 步骤3：报表修改完成后，以修改后报表进行编译，并输出  
//        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
        
        
        
        
		//end 动态列
		
		
		
		
		
		
		
		/*
		 * 导出PDF
		 */
//		response.setContentType("application/pdf");
//		
//		ServletOutputStream outputStream = response.getOutputStream();
//		
//		model.put("title", "这是一个标题");
//		model.put("list1", list);//
////		model.put("list2", list2);
//		
//		String reportFileName = "E:\\luzhen\\src\\myeclipse2014-02\\zhgd\\zhgd-fhadmin Maven Webapp\\src\\main\\webapp\\WEB-INF\\dymb\\test1.jasper";
//		File reportFile = new File(reportFileName);
//		
//		if (reportFile.exists()) {
//			System.out.println("File is exists");
//		}else {
//			System.out.println("File is not exists");
//		}
//		
//		JasperPrint jasperPrint = 
//				JasperFillManager.fillReport(
//					reportFileName, 
//					model, 
//					new JREmptyDataSource()
//					);
//		
//		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//		
//		outputStream.flush();
//		outputStream.close();
		//end 导出PDF
		
		
		/*
		 * 导出excel  xls文件
		 */
		File sourceFile = new File("E:\\luzhen\\src\\myeclipse2014-01\\TestJava\\src\\baobiao\\demo1\\DataSourceReport.jrprint");
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		File destFile = new File("c:\\我的我的.xls");
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.exportReport();
		//end excel xls文件
		
//		String reportFileName = "E:\\luzhen\\src\\myeclipse2014-02\\zhgd\\zhgd-fhadmin Maven Webapp\\src\\main\\webapp\\WEB-INF\\dymb\\kaoqin.jrxml";
//		
//		JasperDesign jdesign = JRXmlLoader.load(reportFileName);//读取模板文件
//		
//		JasperReport jasperReport = JasperCompileManager.compileReport(jdesign);//编译成报表
//		
//		Map<String, Object> model = new HashMap<String,Object>();
//		
//      /*
//      * 填充数据，导出pdf
//      */
//        List<PageData> list = new ArrayList<PageData>();
//		PageData pageData1 = new PageData();
//		PageData pageData2 = new PageData();
//		pageData1.put("xm", "鲁震");
//		pageData2.put("xm", "张三");
//		
//		list.add(pageData1);
//		list.add(pageData2);
//		
//		List<PageData> list2 = new ArrayList<PageData>();
//		PageData pageData3 = new PageData();
//		PageData pageData4 = new PageData();
//		pageData3.put("xm", "lisi");
//		pageData4.put("xm", "wang");
//		
//		for (int i = 0; i < 20; i++) {
//			PageData pageData = new PageData();
//			pageData.put("xm", "枇杷露"+i);
//			list2.add(pageData);
//		}
//		
//		
//		for (int i = 0; i < 50; i++) {
//			PageData pageData = new PageData();
//			pageData.put("xm", "鲁震"+i);
//			list.add(pageData);
//		}
//		
//		list2.add(pageData3);
//		list2.add(pageData4);
//		
//		model.put("tabledata1", list2);
//		model.put("title", "宁波萌恒总部大楼项目部劳务作业人员（含队长、班组长、农民工）考勤表");
//		model.put("day0", "25");
//		model.put("xmmc", "项目名称（全称）：宁波大学梅山校区工程房建施工I标段项目");
//		model.put("bzmc", "班组名称:电工组");
//		model.put("heji", "10天");
//		model.put("riqi", "2017年04月");
//		
//		
//		ServletOutputStream outputStream = response.getOutputStream();
//		
//		JasperPrint jasperPrint = 
//				JasperFillManager.fillReport(
//					jasperReport, 
//					model, 
//					new JREmptyDataSource()
//					);
//		
//		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//	
//		outputStream.flush();
//		outputStream.close();
		
		
		
		/**
		 * 导出excel xlsx文件
		 */
//		long start = System.currentTimeMillis();
//		File sourceFile = new File("build/reports/DataSourceReport.jrprint");
//
//		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
//
//		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".xlsx");
//		
//		JRXlsxExporter exporter = new JRXlsxExporter();
//		
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
//		
//		exporter.exportReport();
//
//		System.err.println("XLSX creation time : " + (System.currentTimeMillis() - start));
		
		//end 导出excel xlsx文件
		
//		org.apache.poi.hssf.usermodel.HSSFWorkbook hssfWorkbook = null;
//		org.apache.poi.ss.usermodel.CreationHelper c = hssfWorkbook.getCreationHelper();
		
//		return new ModelAndView("report1Report",model);
//		return null;
	}
	
	
	
	
	@RequestMapping(value="/stream.php")
	public ModelAndView stream() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.addObject("pd",pd);
		mv.setViewName("stream");
		return mv;
	}
	
	/**
	 * 动态追加、插入、删除行
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sontable2.php")
	public ModelAndView sontable() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.addObject("pd",pd);
		mv.setViewName("sontable");
		return mv;
	}
	
}
