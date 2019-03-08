package com.fh.util;

import java.util.List;

import org.apache.shiro.session.Session;

public class TableColums {
	
	public static String currentUserTableShowCollums(String tableName){
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();		
		pd.put("table_name", tableName);
		List<PageData> currentUserTableShowCollumsList = (List<PageData>) session.getAttribute("currentUserTableShowCollumsList");
		if(currentUserTableShowCollumsList!=null){
			for (PageData pageData : currentUserTableShowCollumsList) {
				if(pageData.getString("TABLE_NAME").equals(tableName)){
					return pageData.getString("TABLE_SHOW_COLS");
				}
			}
		}
		return "";
	}
	
	

}
