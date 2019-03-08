package com.fh.excel;

import java.io.File;
import java.io.InputStream;

/**
 * 
 * <p>标题:ReadExcelInterface</p>
 * <p>描述:解析excel</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月16日 下午10:41:30
 */
public interface ParseExcelInterface {
	
	/**
	 * 
	 * <p>描述:打开excel</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午11:42:18
	 * @param path
	 * @return
	 * @throws Exception
	 */
	ExcelBase openExcel(String path) throws Exception;
	
	ExcelBase openExcel(File path) throws Exception;
	
	ExcelBase openExcel(InputStream path) throws Exception;
	
	/**
	 * <p>描述:关闭资源</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月16日 下午11:41:54
	 * @throws Exception
	 */
	void closeExcel() throws Exception;
	
}
