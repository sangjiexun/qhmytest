package com.keman.zhgd.common.databaseutil.pool;

import java.sql.Connection;


public class ConnectionFactory {

	private ConnectionFactory(){
		
	}
	

	public synchronized static Connection getConnection(){
		try {
			DataBaseConnectionPoolFactory poolFactory = DataBaseConnectionPoolFactory.getInstance();
			ConnectionPool connectionPool = poolFactory.getConnectionPool(DataBaseConnectionPoolFactory.defaultJndi);
			return connectionPool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public synchronized static Connection getConnection(String jndiName){
		try {
			DataBaseConnectionPoolFactory poolFactory = DataBaseConnectionPoolFactory.getInstance();
			ConnectionPool connectionPool = poolFactory.getConnectionPool(jndiName);
			return connectionPool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
