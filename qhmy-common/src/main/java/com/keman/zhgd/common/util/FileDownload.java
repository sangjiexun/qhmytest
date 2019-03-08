package com.keman.zhgd.common.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载文件
 * 创建人：zhoudibo 2015
 * 创建时间：2014年12月23日
 * @version
 */
public class FileDownload {

	/**
	 * @param response 
	 * @param filePath		//文件完整路径(包括文件名和扩展名)
	 * @param fileName		//下载后看到的文件名
	 * @return  文件名
	 */
	public static void fileDownload(final HttpServletResponse response,HttpServletRequest request, String filePath, String fileName) throws Exception{  
		     
		    byte[] data = FileUtil.toByteArray2(filePath);  
//		    URLEncoder.encode(fileName, "utf-8");
//		    fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		    String userAgent = request.getHeader("user-agent");
		    if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0) {
                fileName = "=?UTF-8?B?" + (new String(org.apache.commons.net.util.Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";//该行代码也可以使用下面的代码来代替：
                //fileName = "=?UTF-8?B?" + (new String(com.ghj.packageoftool.Base64.encode(fileName.getBytes("UTF-8")))) + "?=";
            } else {
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }
		    response.reset();  
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		    response.addHeader("Content-Length", "" + data.length);  
		    response.setContentType("application/octet-stream;charset=UTF-8");  
		    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
		    outputStream.write(data);  
		    outputStream.flush();  
		    outputStream.close();
		    response.flushBuffer();
		    
		   

		    
		} 
	public static void fileDownloadAjax(final HttpServletResponse response, String filePath, String fileName) throws Exception{  
	     
	    byte[] data = FileUtil.toByteArray2(filePath);  
	    fileName = URLEncoder.encode(fileName, "UTF-8");  
	    response.reset();  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	    response.setContentType("application/json; charset=utf-8");  
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	    response.flushBuffer();
	    
	}
	public static void main(String[] args) throws Exception {
		String fileName = "你好";
		fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		System.out.println(fileName);
		fileName = URLEncoder.encode(fileName, "iso-8859-1");
		System.out.println(fileName);
		fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
		System.out.println(fileName);
	}
}
