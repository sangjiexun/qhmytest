package com.keman.zhgd.common.web.servlet;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.keman.zhgd.common.util.FileUtil;

public class ServletContextListenerImple implements ServletContextListener{
	
	private static final Log logger = LogFactory.getLog(ServletContextListenerImple.class);

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		//初始化数据库连接池
//		logger.info("初始化数据库连接池");
//		String databaseFilePath = this.getClass().getResource("/").getPath()+"/databaseinfo.xml";
//		logger.info("读取数据库配置文件:"+databaseFilePath);
//		DataBaseConnectionPoolFactory dataBaseConnectionPoolFactory = DataBaseConnectionPoolFactory.getInstance();
//		File file = new File(databaseFilePath);
		try {
			//初始化spring容器
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:pt/spring/applicationContext-*.xml");
			
			//得到bin目录  判断是tomcat还是weblogic
			String path = getClass().getResource("/").getFile().toString();
			String basepath = path.substring(0, path.indexOf("WEB-INF"));
			//tomcat环境下
			if (path!=null && path.indexOf("webapps")!=-1) {
				path = path.substring(0, path.indexOf("webapps"));
				path = path+"/bin";
				File file = new File(path+"/dcrf32.dll");
				FileUtil.fileCopyTofile(new File(basepath+"/dll/dcrf32.dll"), file);
				
				File file2 = new File(path+"/javaRD800.dll");
				FileUtil.fileCopyTofile(new File(basepath+"/dll/javaRD800.dll"), file2);
			}else if (path!=null && path.indexOf("weblogic")!=-1) {
////				/D:/bea/weblogic92/common/lib/
////				D:\bea\weblogic92\bin;D:\bea\weblogic92\server\bin
//				path = path.substring(0, path.indexOf("common"));
//				path = path+"/bin";
//				File file = new File(path+"/dcrf32.dll");
//				FileUtil.fileCopyTofile(new File(basepath+"/dll/dcrf32.dll"), file);
//				
//				File file2 = new File(path+"/javaRD800.dll");
//				FileUtil.fileCopyTofile(new File(basepath+"/dll/javaRD800.dll"), file2);
//				
//				path = path+"/server/bin";
//				
//				File file = new File(path+"/dcrf32.dll");
//				FileUtil.fileCopyTofile(new File(basepath+"/dll/dcrf32.dll"), file);
//				
//				File file2 = new File(path+"/javaRD800.dll");
//				FileUtil.fileCopyTofile(new File(basepath+"/dll/javaRD800.dll"), file2);
//				
				
			}
			
			
			System.getProperty("java.library.path"); 
			
			
//			TestDaoImple testDaoImple = (TestDaoImple) context.getBean("testDaoImple");
//			if (testDaoImple == null) {
//				logger.error("没有该实例");
//			}
			
//			dataBaseConnectionPoolFactory.createPools(file);
//			Connection connection = ConnectionFactory.getConnection();
//			if (connection==null) {
//				logger.info("数据库初始化失败");
//			}else {
//				logger.info("数据库初始化成功");
//			}
//			ConnectionPoolC3p0Imple connectionPoolC3p0Imple = (ConnectionPoolC3p0Imple) dataBaseConnectionPoolFactory.getConnectionPool(DataBaseConnectionPoolFactory.defaultJndi);
//			DataSource dataSource = connectionPoolC3p0Imple.getDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("初始化数据库连接池发生异常:"+e);
		}
		
		
	}
	
	
	public static void main(String[] args) {
		String path = "/E:/luzhen/usr/java/apache-tomcat-6.0.35/webapps/lgyww/WEB-INF/classes/";
		String basepath = path.substring(0, path.indexOf("WEB-INF"));
		System.out.println(basepath);
		if (path!=null && path.indexOf("webapps")!=-1) {
			path = path.substring(0, path.indexOf("webapps"));
			path = path+"/bin";
			System.out.println(path);
			File file = new File(path+"/dcrf32.dll");
			FileUtil.fileCopyTofile(new File(basepath+"/dll/dcrf32.dll"), file);
			
			File file2 = new File(path+"/javaRD800.dll");
			FileUtil.fileCopyTofile(new File(basepath+"/dll/javaRD800.dll"), file2);
		}
		
		
		
		
		
	}
	
}
