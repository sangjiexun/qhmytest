package com.fh.interceptor.commonabs;



import java.util.HashMap;
import java.util.Map;

public class  SysLogAbsTract implements SysLoggc{

	public static Map<String, Object> rongqi=new HashMap<String, Object>();

	public static void createSonClass() {
		new LogWn().init();
	}
	@Override
	public void init() {
		
	}

}
