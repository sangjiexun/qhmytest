package com.keman.zhgd.common.databaseutil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 数据库持久化操作类
 * @author liuqingsong
 * @date Jan 12, 2012 3:42:12 PM
 */
public class DataTool {

	private static String db_CharSet = "ISO8859_1";
	private static String user_CharSet = "GBK";

	/**
	 * 对于需要执行的字符串信息，存入数据库时，汉字转码
	 * 
	 * @param o
	 * @return
	 */
	public static Object format2Db(Object o) {
		if (String.class.isInstance(o)) {
			try {
//				return new String(o.toString().getBytes(), db_CharSet);
				return o;
			} catch (Exception localException) {
				return o;
			}
		} else {
			return o;
		}
	}

	private static Object stringToUtf8(Object o) {
		if (String.class.isInstance(o)) {
			try {
//				return new String(o.toString().getBytes(db_CharSet),
//						user_CharSet);
				return o;
			} catch (Exception localException) {
				return o;
			}
		} else {
			return o;
		}
	}

	/**
	 * 将List<Map<String, Object>>中的数据进行转码
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> listToUtf8(
			List<Map<String, Object>> list) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (list != null && list.size() > 0) {
			Map<String, Object> resultData = null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {

				resultData = new HashMap<String, Object>();

				Map<String, Object> map = (Map<String, Object>) iterator.next();
				Set<String> keys = map.keySet();
				for (Iterator iterator2 = keys.iterator(); iterator2.hasNext();) {
					String key = (String) iterator2.next();
					resultData.put(key.toLowerCase(),
							stringToUtf8(map.get(key)));
				}
				result.add(resultData);
			}
		}

		return result;
	}

	/**
	 * 将List<Map<String, Object>>中的数据进行转码
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, Object> mapToUtf8(Map<String, Object> map) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (map != null) {
			Set<String> keys = map.keySet();
			for (Iterator iterator2 = keys.iterator(); iterator2.hasNext();) {
				String key = (String) iterator2.next();
				result.put(key.toLowerCase(), stringToUtf8(map.get(key)));
			}
		}
		return result;
	}
	
	public static Map<String, Object> mapToCharset(Map<String, Object> map,String charset) throws Exception{

		Map<String, Object> result = new HashMap<String, Object>();
		if (map != null) {
			Set<String> keys = map.keySet();
			for (Iterator iterator2 = keys.iterator(); iterator2.hasNext();) {
				String key = (String) iterator2.next();
				result.put(key.toLowerCase(), new String(map.get(key).toString().getBytes("ISO8859_1"),charset));
			}
		}
		return result;
	}
	
	

	public static String getJSONfromResultSetFirst(ResultSet resultSet)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int count = metaData.getColumnCount();
		if ((resultSet == null) || (count < 0)) {
			return "-100";
		}
		if (resultSet.next()) {
			sb.append("[{");
			for (int x = 1; x <= count - 1; x++) {
				sb.append("\""
						+ metaData.getColumnName(x).toLowerCase()
						+ "\":"
						+ (resultSet.getObject(x) == null ? "\"\""
								: new StringBuffer("\"").append(
										resultSet.getObject(x)).append("\"")
										.toString()) + ",");
			}
			sb.append("\""
					+ metaData.getColumnName(count).toLowerCase()
					+ "\":"
					+ (resultSet.getObject(count) == null ? "\"\""
							: new StringBuffer("\"").append(
									resultSet.getObject(count)).append("\"")
									.toString()));
			sb.append("}]");
		}
		return sb.toString();
	}

	
	 public static void main(String[] args) {
		 long currentTimeMillis = System.currentTimeMillis();
		 
		 double random = Math.random();
		 System.out.println(random);
    }
	 
	/**
	 * 获取当前时间毫秒数
	 * @return
	 */
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
}