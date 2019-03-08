package com.fh.util;

import java.util.Date;
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
/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* @author zhoudibo
* @version 1.0
 */
public class ObjectExcelView4 extends AbstractExcelView{

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
		List<Short> cellStyles = (List<Short>) model.get(Const.EXCEL_CELL_STYLE);
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
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(0).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		Short contentCellStyle = null;
		for(int i=0; i<varCount; i++){
			PageData vpd = varList.get(i);
			for(int j=0;j<len;j++){
				String varstr = String.valueOf(vpd.get("var"+(j+1)) != null ? vpd.get("var"+(j+1)) : "") ;
				cell = getCell(sheet, i+1, j);
				HSSFCellStyle cellcontentStyle = workbook.createCellStyle(); //内容样式
				contentCellStyle = cellStyles.get(j);//cell单独样式
				if(contentCellStyle != null){
					cellcontentStyle.setAlignment(contentCellStyle);
					cell.setCellStyle(cellcontentStyle);
				}else{
					cell.setCellStyle(contentStyle);
				}
				setText(cell,varstr);
			}
			
		}
		List<String> foots = (List<String>) model.get("foots");
		for(int i=0;i<len;i++){
			String foot = foots.get(i);
			cell = getCell(sheet, varCount+2, i);
			HSSFCellStyle cellcontentStyle = workbook.createCellStyle(); //内容样式
			contentCellStyle = cellStyles.get(i);//cell单独样式
			if(contentCellStyle != null){
				cellcontentStyle.setAlignment(contentCellStyle);
				cell.setCellStyle(cellcontentStyle);
			}else{
				cell.setCellStyle(contentStyle);
			}
			setText(cell, foot);
		}
		CellRangeAddress range = new CellRangeAddress(0, 0, 0, 7);
		range = new CellRangeAddress(varCount+2, varCount+2, 0, 6);
		sheet.addMergedRegion(range);
	}

}
