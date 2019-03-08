package com.fh.excel.util;


/**
 * 
 * <p>标题:ExcelConst</p>
 * <p>描述:excel常量</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午10:28:56
 */
public class ExcelConst {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	public static final String EMPTY = "";
	
	public static final String POINT = ".";
	
	public static final String LIB_PATH = "lib";
	
	public static final String STUDENT_INFO_XLS_PATH = LIB_PATH
			+ "/student_info" + POINT + OFFICE_EXCEL_2003_POSTFIX;
	
	public static final String STUDENT_INFO_XLSX_PATH = LIB_PATH
			+ "/student_info" + POINT + OFFICE_EXCEL_2010_POSTFIX;
	
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	
	public static final String PROCESSING = "Processing...";

}
