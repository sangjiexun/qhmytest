package com.keman.zhgd.common.maputil;

import java.util.Properties;


public class ReadProperties {
	
	private static Properties pro;

	private static void init(String propertiesName) {
		if(pro == null) {
			pro = new Properties();
			ReadPerpertiesUtil.read(pro, propertiesName);
		}
		else
		{
			ReadPerpertiesUtil.read(pro, propertiesName);
		}
	}
	
	public static String getConfigureValue(String key,String propertiesName) {
		init(propertiesName);
		return pro.getProperty(key);
	}
}
