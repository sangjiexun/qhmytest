package com.keman.zhgd.common.databaseutil.pool;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class ConnectionPoolC3p0Imple implements ConnectionPool{
	private ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
	
	public Connection getConnection() throws Exception{	
		return comboPooledDataSource.getConnection();
	}
	
	/**
	 * 为spring提供支持
	 * @return
	 */
	public ComboPooledDataSource getDataSource(){
		return this.comboPooledDataSource;
	}

	public void setDatabaseInfo(DataBaseInfo dataBaseInfo) throws Exception{
        comboPooledDataSource.setDriverClass(dataBaseInfo.getDriverclass());
        comboPooledDataSource.setJdbcUrl(dataBaseInfo.getUrl());
        comboPooledDataSource.setUser(dataBaseInfo.getUsername());
        comboPooledDataSource.setPassword(dataBaseInfo.getPassword());
        
        
        comboPooledDataSource.setInitialPoolSize(5);
       
        comboPooledDataSource.setMaxPoolSize(15);
        
        comboPooledDataSource.setMinPoolSize(3);

        comboPooledDataSource.setAcquireIncrement(5);


        comboPooledDataSource.setIdleConnectionTestPeriod(60);

        comboPooledDataSource.setMaxIdleTime(25000);

        comboPooledDataSource.setAutoCommitOnClose(true);

        comboPooledDataSource.setAcquireRetryAttempts(30);

        comboPooledDataSource.setAcquireRetryDelay(1000);
        comboPooledDataSource.setBreakAfterAcquireFailure(true);
	}
}
