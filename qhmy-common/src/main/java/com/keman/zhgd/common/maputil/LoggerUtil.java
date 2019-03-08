package com.keman.zhgd.common.maputil;

import java.sql.Connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 日志类
 * @author Administrator
 *
 */
public class LoggerUtil {
	
	private static Logger logger = Logger.getRootLogger();
	
	public static void outLog(){
		outLog(Level.INFO,"");
	}
	
	/**
	 * 日志输出
	 * @param level
	 * @param message
	 */
	public static void outLog(Level level,String message){
		if ("INFO".equals(level.toString())) {
			logger.info(message);
		}else if ("DEBUG".equals(level.toString())) {
			logger.debug(message);
		}
	}
	
	/**
	 * 日志输出并输出到数据库的某个表中
	 * @param level
	 * @param message
	 * @param connection
	 */
	public static void outLog(Level level,String message,Connection connection){
		outLog(level,message);
		/*
		 * 数据库输出日志
		 */
		
	}
	
}
