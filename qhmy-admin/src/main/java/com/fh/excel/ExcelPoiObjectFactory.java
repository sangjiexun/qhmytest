package com.fh.excel;

import com.fh.excel.util.ExcelConst;

/**
 * <p>标题:ExcelPoiObjectFactory</p>
 * <p>描述:生成excel对象，根据不同的文档类型</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午11:07:24
 */
public class ExcelPoiObjectFactory {
	
	private ExcelPoiObjectFactory(){
		
	}
	
	public static ExcelBase getInstance(String fileName){
		if (fileName.endsWith(ExcelConst.OFFICE_EXCEL_2003_POSTFIX)) {//返回2003格式的对象
			return new XlsExcel();
		}else if (fileName.endsWith(ExcelConst.OFFICE_EXCEL_2010_POSTFIX)) {//返回2007  20110格式
			return new XlsxExcel();
		}
		return null;
	}
	
}
