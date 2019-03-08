package com.keman.zhgd.common.databaseutil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetUtil{
	
	public static void print(ResultSet rs)throws SQLException{
		
		ResultSetMetaData rsmd=rs.getMetaData();
		int count=rsmd.getColumnCount();
		
		while(rs.next()){
			
			for(int i=1;i<=count;i++){
				
				if(i!=1){
					System.out.print(",");
					}
				
				String colName=rsmd.getColumnName(i);
				String value=rs.getString(colName);
	//			String value=rs.getString(colName);
				System.out.print(colName+"="+value);
				
				}
				
				System.out.println();
			
			}
		
		}
	}