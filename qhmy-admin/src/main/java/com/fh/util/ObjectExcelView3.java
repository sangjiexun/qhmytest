package com.fh.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fh.util.PageData;
import com.keman.zhgd.common.util.Tools;
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @author zhoudibo
* @version 1.0
 */
public class ObjectExcelView3 extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		Object name = model.get("fileName");
		if(name!=null){
			filename = name.toString();
			String userAgent = request.getHeader("user-agent");
		    if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0) {
		    	filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
//		    	filename = "=?UTF-8?B?" + (new String(org.apache.commons.net.util.Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";//该行代码也可以使用下面的代码来代替：
                //fileName = "=?UTF-8?B?" + (new String(com.ghj.packageoftool.Base64.encode(fileName.getBytes("UTF-8")))) + "?=";
            } else {
            	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
            }
		}
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		sheet = workbook.createSheet("sheet1");
		
		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 1, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(1).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			PageData vpd = varList.get(i);
			for(int j=0;j<len;j++){
				
				
				String varstr = String.valueOf(vpd.get("var"+(j+1)) != null ? vpd.get("var"+(j+1)) : "") ;
				cell = getCell(sheet, i+2, j);
				cell.setCellStyle(contentStyle);
				setText(cell,varstr);
			}
			
		}
		
		/*
		 * 导出到excel汇总数据
		 * add by ccc 
		 * totalData 汇总的数据(String)
		 * column excel中中共多少列
		 */
		String totalData = (String)model.get("totalData");
		if(Tools.notEmpty(totalData)){
			Integer column = (Integer)model.get("column");
			CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,column.intValue() - 1);
	        sheet.addMergedRegion(callRangeAddress);
			cell = getCell(sheet, 0, 0);
			HSSFCellStyle contentStyle3 = workbook.createCellStyle(); //内容样式
			contentStyle3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cell.setCellStyle(contentStyle3);
			setText(cell,totalData);
			sheet.getRow(0).setHeight(height);
		}
		
	}

}
