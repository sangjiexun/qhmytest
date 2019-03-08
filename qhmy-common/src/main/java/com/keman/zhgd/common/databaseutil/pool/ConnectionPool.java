package com.keman.zhgd.common.databaseutil.pool;

import java.sql.Connection;

public interface ConnectionPool {
	Connection getConnection() throws Exception;

	void setDatabaseInfo(DataBaseInfo dataBaseInfo) throws Exception;
}
