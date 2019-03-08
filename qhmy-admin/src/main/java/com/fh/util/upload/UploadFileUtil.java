package com.fh.util.upload;

/**
 * 文件处理工具类
 * @author Administrator
 *
 */
public class UploadFileUtil {
	
	public static void deleteFile(){
		
	}
	
	public static UploadPathVo getUploadPath(String tableName){
		return Configurations.getFileRepository(tableName);
	}
	
}
