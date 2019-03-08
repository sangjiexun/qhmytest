package com.fh.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.util.Const;
import com.fh.util.upload.Configurations;
/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者：zhoudibo 2015
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
		//初始化
		Configurations.getCrossServer();
		
		//System.out.println("========获取Spring WebApplicationContext");
	}

}
