package com.keman.zhgd.common.databaseutil.pool;

public class ConnectionPoolType {
	
	public final static String C3P0 = "c3p0";
	
	public final static String PROXOOL = "proxool";
	
	protected static String useingPool = "c3p0";
	
	private ConnectionPoolType(){
		
	}
	
	
	public static void setUseingPool(){
		useingPool = ConnectionPoolType.C3P0;
	}
	
}
