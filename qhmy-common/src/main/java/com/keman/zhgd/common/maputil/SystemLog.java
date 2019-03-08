package com.keman.zhgd.common.maputil;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SystemLog {
	
	private Logger log;
	
	private static Properties pro = new Properties();
	
	static {
		ReadPerpertiesUtil.read(pro, "log4j");
	}
	
	public SystemLog(Class<? extends Object> clazz){
		log = Logger.getLogger(clazz);

		//String logConfigFilePath = this.getClass().getClassLoader().getResource("logConfig.lcf").getPath();log4j.properties
		String logConfigFilePath = this.getClass().getClassLoader().getResource("log4j.properties").getPath();
		PropertyConfigurator.configure(logConfigFilePath);
	}
	
	public void error(String exceptionResource,String tipsMessage,Exception e){
		String errorMessage = "There is some exceptions in '" + exceptionResource + "' : " + tipsMessage;
		if("true".equals(pro.getProperty("debug")) && null != e) {
			e.printStackTrace();
		} else {
			System.out.println(errorMessage);
		}
		log.error(errorMessage);
		if(null != e){
			log.error(e);
		}
	}
	
	public void info(String information){
		log.info(information);
	}
	
}
