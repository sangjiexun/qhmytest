package com.keman.zhgd.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	/**
	 * 删除目录及子文件
	 * @param file
	 */
	public static void deleteFiles(File file){
		if(file.isDirectory()){
			File file2 = null;
		 	File [] listFiles = file.listFiles();
		 	if (listFiles == null || listFiles.length == 0) {
				file.delete();
			}else{
				for (int i = 0; i < listFiles.length; i++) {
			 		file2 = listFiles[i];
			 		deleteFiles(file2);
				}
				file.delete();
			}
		}else if (file.isFile()) {
//			file.getAbsoluteFile().delete();
			file.delete();
			return;
		}
	}
	
	/**
	 * 文件拷贝
	 * @author luzhen
	 * 2015年5月11日
	 * @param src
	 * @param des
	 */
	public static void fileCopyTofile(File src,File des){
		try {
			FileUtils.copyFile(src, des);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
