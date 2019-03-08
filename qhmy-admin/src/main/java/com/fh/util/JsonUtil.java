package com.fh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.keman.zhgd.common.util.PageData;

//下面是FastJson的简介：常用的方法！  
//  Fastjson API入口类是com.alibaba.fastjson.JSON，常用的序列化操作都可以在JSON类上的静态方法直接完成。  
//  public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray   
//  public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject      
//  public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean   
//  public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray   
//  public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合   
//  public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本   
//  public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本   
//  public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray（和上面方法的区别是返回值是不一样的）
public class JsonUtil {
	
	/**
	 * Map转换为JSON字符串
	 * @return
	 */
	public static String mapToJsonStr(Map map){
		String rst = null;
		rst = JSON.toJSONString(map,SerializerFeature.WriteMapNullValue);
		return rst;
	}
	
	public static String beanToJsonStr(Object obj){
		String rst = null;
		rst = JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue);
		return rst;
	}
	
	/**
	 * 字符串转JSONObject
	 * @param jsonText
	 * @return
	 */
	public static JSONObject stringParseObject(String jsonText){
		return JSON.parseObject(jsonText);
	}
	
	
	public static Object stringParseObject(String jsonText,Class cla){
		return JSON.parseObject(jsonText, cla);
	}
	
	
	/**
	 * 字符串转JSONArray
	 * @param jsonText
	 * @return
	 */
	public static JSONArray stringParseArray(String jsonText){
		return JSON.parseArray(jsonText);
	}
	
	public static void main(String[] args) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "luzhen");
		map.put("age", 34);
		
		List<Map> list = new ArrayList<Map>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		
		map2.put("a", "luzhen");
		map2.put("b", 34);
		
		list.add(map2);
		list.add(map);
		
		map.put("list", list);
		
		String text = mapToJsonStr(map);
		
		System.out.println(text);
		
	 	Map map3 = (Map)stringParseObject(text,Map.class);
	 	
	 	System.out.println(map3);
	 	
//	 	Integer age = jsonObject.getInteger("age");
//	 	
//	 	list = jsonObject.getObject("list", java.util.List.class);
//	 	
//		System.out.println(list);
//		
//		System.out.println(list.get(0).get("a"));
		
		
	}
	
	
}
