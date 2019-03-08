package com.fh.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.keman.zhgd.common.util.PathUtil;

public class GenerateExcel {
	/** 
     * 创建2003文件的方法 
     *  
     * @param filePath 
     */  
    @Autowired
	public static void generateExcel2003(String filePath,Map<String,Object> dataMap) {
    	@SuppressWarnings("unchecked")
		List<PageData> list = (List<PageData>)dataMap.get("varList");
    	@SuppressWarnings("unchecked")
		List<String> titles =  (List<String>)dataMap.get("titles");
        // 先创建工作簿对象  
        @SuppressWarnings("resource")
		HSSFWorkbook workbook2003 = new HSSFWorkbook();  
        // 创建工作表对象并命名  
        HSSFSheet sheet = workbook2003.createSheet("工人信息");
        short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);
        HSSFRow headerRow = sheet.createRow(0); 
        headerRow.setHeightInPoints(25f);// 设置行高度  
        HSSFCellStyle headerStyle = workbook2003.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = workbook2003.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		int len = titles.size();
		for(int i=0;i<titles.size();i++){
			HSSFCell nameHeader = headerRow.createCell(i);  
			nameHeader.setCellValue(titles.get(i));  
			nameHeader.setCellStyle(headerStyle);  
		}
        // 遍历集合对象创建行和单元格  
        for (int i = 0; i < list.size(); i++) {
        	HSSFRow row = sheet.createRow(i+1);  
        	PageData vpd = list.get(i);
			for(int j=0;j<len;j++){
				String varstr = String.valueOf(vpd.get("var"+(j+1)) != null ? vpd.get("var"+(j+1)) : "") ;
				HSSFCell nameCell = row.createCell(j); 
				nameCell.setCellValue(varstr);
			}
        }  
        // 生成文件  
        File file = new File(PathUtil.getClasspath() + filePath);
        
		if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
			file.getParentFile().mkdirs();				//不存在就全部创建
		}
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(file);  
            workbook2003.write(fos);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}
