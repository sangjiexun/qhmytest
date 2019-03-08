package com.fh.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * <p>标题:AbsReadExcel</p>
 * <p>描述:读Excel 基类</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午10:31:51
 */
public abstract class AbsParseExcel implements ParseExcelInterface {
	
	private InputStream inputStream;
	
	private String officeVersion;
	
	private String fileName;
	
	private ExcelBase excelBase;
	
	/**
	 * <p>描述:打开excel</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午10:32:30
	 */
	public ExcelBase openExcel(String path) throws Exception{
		File file = new File(path);
		return openExcel(file);
	}
	
	public ExcelBase openExcel(File file) throws Exception{
		InputStream inputStream = new FileInputStream(file);
        return openExcel(inputStream);
	}
	
	public void closeExcel() throws Exception {
		this.inputStream.close();
	}

	public ExcelBase openExcel(InputStream inputStream) throws Exception{
		this.inputStream = inputStream;
		ExcelBase excelBase = ExcelPoiObjectFactory.getInstance(this.fileName);
		if (excelBase instanceof XlsExcel) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(this.inputStream);
			excelBase = (XlsExcel)excelBase;
			((XlsExcel) excelBase).setBook(hssfWorkbook);
		}else if (excelBase instanceof XlsxExcel) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(this.inputStream);
			excelBase = (XlsxExcel)excelBase;
			((XlsxExcel) excelBase).setBook(xssfWorkbook);
		}
		this.excelBase = excelBase;
        return excelBase;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getOfficeVersion() {
		return officeVersion;
	}

	public void setOfficeVersion(String officeVersion) {
		this.officeVersion = officeVersion;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ExcelBase getExcelBase() {
		return excelBase;
	}

	public void setExcelBase(ExcelBase excelBase) {
		this.excelBase = excelBase;
	}
	
//	xssfWorkbook.getNumberOfSheets()    获得sheets
//	XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
//	xssfSheet.getLastRowNum();  获得行
//	SSFRow xssfRow = xssfSheet.getRow(rowNum); //某一行
	
	//获取单元格
//	XSSFCell no = xssfRow.getCell(0);
//    XSSFCell name = xssfRow.getCell(1);
//    XSSFCell age = xssfRow.getCell(2);
//    XSSFCell score = xssfRow.getCell(3);
	
	
}
