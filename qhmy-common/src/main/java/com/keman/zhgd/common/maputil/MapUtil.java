package com.keman.zhgd.common.maputil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@SuppressWarnings("unchecked")
public class MapUtil {

	/**
	 * 将request.getParameterMap的Map转换为Map<String,string>
	 * @param objMap
	 * @return
	 */
	public static Map<String, String> toStringMap(Map<String, Object> reqMap) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<Map.Entry<String, Object>> keyIterator = reqMap.entrySet()
				.iterator();
		while (keyIterator.hasNext()) {
			Map.Entry<String, Object> entry = keyIterator.next();
			Object valueObj = entry.getValue();
			String[] valuesStr = new String[1];
			if (valueObj instanceof String[]) {
				valuesStr = (String[]) valueObj;
			} else {
				valuesStr[0] = valueObj.toString();
			}
			for (int i = 0; i < valuesStr.length; i++) {
				map.put(entry.getKey(), valuesStr[i]);
			}
		}
		return map;
	}
	
	/**
	 * 将request.getParameterMap的Map转换为Map<String,Object>
	 * @param objMap
	 * @return
	 */
	public static Map<String, Object> toMap(Map<String, Object> reqMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> keyIterator = reqMap.entrySet()
				.iterator();
		while (keyIterator.hasNext()) {
			Map.Entry<String, Object> entry = keyIterator.next();
			Object valueObj = entry.getValue();
			Object [] valuesStr = new Object[1];
			if (valueObj instanceof Object[]) {
				valuesStr = (Object[]) valueObj;
			} else {
				valuesStr[0] = valueObj;
			}
			for (int i = 0; i < valuesStr.length; i++) {
				map.put(entry.getKey(), valuesStr[i]);
			}
		}
		return map;
	}
	
	/**
	 * to pojo
	 * @param map
	 * @param ObjectBeanPath
	 * @return
	 */
	
	public static Object mapToObject(Map<String, Object> map,   
	        Class clazz,Object obj) {
	    Object resourcesBean = obj;
	    Field [] fields = clazz.getDeclaredFields();
	    boolean checkRst = false;
	    try {
	        for (Map.Entry<String, Object> entry : map.entrySet()) {
//	        	System.out.println(entry.getKey());
	        	checkRst = checkObjectisHaveKey(fields,entry.getKey().toLowerCase());
	        	if (checkRst) {
	        		Field filed = clazz.getDeclaredField(entry.getKey().toLowerCase());
		            filed.setAccessible(true);   
			        filed.set(resourcesBean, entry.getValue() != null ? entry.getValue() : null);  
				}
	            
	        }   
	    } catch (Exception e) {   
	        e.printStackTrace();
	    }
	    return resourcesBean;   
	}
	
	private static boolean checkObjectisHaveKey(Field[] fields, String lowerCase) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(lowerCase)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception{
//		Class class1 = TXtBm.class;
//		System.out.println(class1.getName());
//		
//		Map<String, Object> rst = new HashMap<String, Object>();
//		rst.put("bmdm", null);
//		rst.put("jdrq", null);
//		
//		mapToObject(rst, TXtBm.class, new TXtBm());
		
	}

}