package com.keman.zhgd.common.maputil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * @Description: 公共方法类
 * @author liuqingsong
 * @date Jan 12, 2012 3:41:00 PM
 */
public class CommonUtil {

	/**
	 * 是否为空，空-true，非空-false
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		if(obj==null || obj.equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否不为空，空-false，非空-true
	 * @return
	 */
	public static boolean isNotEmpty(Object obj){
		if((obj!=null) && (!obj.equals("")) && (!obj.equals("null"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 读取properties配置文件
	 * @param c
	 * @param fileName 全路径名
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Properties getProperties(String fileName) 
			throws FileNotFoundException, IOException{
    	Properties pp=new Properties();
    	pp.load(new FileInputStream(fileName));
    	return pp;
	}
	
	/**
	 * Object转换成String类型
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj){

		if(obj instanceof BigDecimal || obj instanceof BigInteger ){
			BigDecimal bd = null;
			bd = (BigDecimal)obj;
//			Integer temp=(Integer)obj;
			return bd+"";
		}else if(isNotEmpty(obj)){
			return (String)obj;
		}else{
			return null;
		}
	}

	/**
	 * 去除Object数组的空值,并把US7转码为UTF-8
	 * @param obj
	 * @return
	 */
	public static Object[] getAllParamas(Object[] obj) {
//		Object[] obj = CommonUtil.changeChara(params);
		List list = new ArrayList();
		for(int i=0;i<obj.length;i++){
			if(isNotEmpty(obj[i])){
				list.add(obj[i]);
			};
		}
		return list.toArray();
	}

	/**
	 * 表头+数据
	 * @param map
	 * @param items
	 * @return
	 */
	public static List initList(Map map, List items) {
		List list = new ArrayList();
		list.add(map);
		for(int i=0;i<items.size();i++){
			list.add(items.get(i));
		}
		return list;
	}
	
	/**
	 * 全部替换
	 * jdk1.4中的replaceAll()对一些特殊字符（如：“\” “(”）的处理存在一定的缺陷
	 * 使用如下方法替换replaceAll()方法
	 * 使用如下：把右括号替换成空字符串--replaceForAll(") and ","）","")，结果为"and "
	 * @param aInput
	 * @param aOldPattern
	 * @param aNewPattern
	 * @return
	 */
	public static String replaceForAll(final String aInput,final String aOldPattern,final String aNewPattern){
	       if (aOldPattern.equals("")){
	          throw new IllegalArgumentException("Old pattern must have content.");
	       }
	       final StringBuffer result = new StringBuffer();
	       int startIdx = 0;
	       int idxOld = 0;
	       while((idxOld = aInput.indexOf(aOldPattern, startIdx))>= 0){
		      result.append(aInput.substring(startIdx, idxOld));
		      result.append(aNewPattern );
		      startIdx = idxOld + aOldPattern.length();
	       }
	       result.append(aInput.substring(startIdx));
	       return result.toString();
	    }
	
	/**
	 * 替换第一个
	 * jdk1.4中的replaceAll()对一些特殊字符（如：“\” “(”）的处理存在一定的缺陷
	 * @param aInput
	 * @param aOldPattern
	 * @param aNewPattern
	 * @return
	 */
	public static String replaceForFirst(final String aInput,final String aOldPattern,final String aNewPattern){
	       if (aOldPattern.equals("")){
	          throw new IllegalArgumentException("Old pattern must have content.");
	       }
	       final StringBuffer result = new StringBuffer();
	       int startIdx = 0;
	       int idxOld = 0;
	       while((idxOld = aInput.indexOf(aOldPattern, startIdx))>= 0){
		      result.append(aInput.substring(startIdx, idxOld));
		      result.append(aNewPattern );
		      startIdx = idxOld + aOldPattern.length();
		      break;
	       }
	       result.append(aInput.trim().substring(startIdx));
	       return result.toString();
	    }


	/**
	 * List转为json
	 * @param list
	 * @return
	 */
	public static String listToJson(List list){
		JSONArray json = new JSONArray(list) ;
		return json.toString();
	}
	
	/**
	 * Map转为json
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map map){
		try {
			JSONArray json = new JSONArray(map) ;
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}   
		
	}
	
	/**
	 * 初始化map的null值，变为""
	 */
	public static String initMapString(Map map, String key){
		String temp = (String)map.get(key);
		return temp==null?"":temp;
	}
	
	/**
	 * 处理字符串，空值返回""
	 * @param map
	 * @param key
	 * @return
	 */
	public static String initStr(Object obj){
		if(CommonUtil.isNotEmpty(obj)){
			return String.valueOf(obj);
		}else{
			return "";
		}
	}
	
	/**
	 * @Description: 对象初始化，空值为0
	 * @param obj
	 * @return
	 */
	public static String initZeroStr(Object obj){
		if(CommonUtil.isNotEmpty(obj)){
			return String.valueOf(obj);
		}else{
			return "0";
		}
	}
	
	/**
	 * 
	 * @Description: 
	 * @param obj
	 * @return
	 */
	public static String initArrStr(String str){
		if(isNotEmpty(str)){
			return replaceForAll(str,"，",",");
		}else{
			return str;
		}
		
	}

	/**
	 * like两端加%，如果为空则返回""
	 * @param str
	 * @return
	 */
	public static String betweenLike(Object obj) {
		if (CommonUtil.isNotEmpty(obj)) {
			return "%" + obj.toString() + "%";
		} else {
			return "";
		}
	}
	
	/**
	 * like后端加%，如果为空则返回""
	 * @param str
	 * @return
	 */
	public static String endLike(Object obj) {
		if (CommonUtil.isNotEmpty(obj)) {
			return obj.toString() + "%";
		} else {
			return "";
		}
	}
	
	/**
	 *  右边补0
	 * */
	public static String rightpush(int k) {
		String str="";
		for(int i=0;i<k;i++){
			str+="0";
		}
		return str;
	}
	
	 /**
	  * 根据多个ids字符串，获取sql可用的ids字符串，以“，”分隔
	  * 如：1,2,3——>'1','2','3'
	  * @param userids
	  * @return
	  */
	public static String getIds(String ids){
		String[] arr = initArrStr(ids).split(",");
		StringBuffer buf = new StringBuffer("'0'");
		for(int i=0;i<arr.length;i++){
			buf.append(",'").append(arr[i]).append("'");
		}
		return buf.toString();
	}
	
	/**
	 * 根据List集合和关键字，返回供sql in 使用的字符串，以“，”分隔
	 * 如：List ——>'1','2','3'
	 * @Description: 
	 * @param list
	 * @param paramName
	 * @return
	 */
	public static String getIds(List list,String paramName){
		StringBuffer buf = new StringBuffer("'0'");
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			buf.append(",'").append(initStr(map.get(paramName))).append("'");
		}
		return buf.toString();
	}
	
	
	/**
	* 两个Double数相加 *
	* 已修改为double类型
	* @param v1 *
	* @param v2 *
	* @return Double
	*/
	public static double add(double v1, double v2) {
	   BigDecimal b1 = new BigDecimal(String.valueOf(v1));
	   BigDecimal b2 = new BigDecimal(String.valueOf(v2));
	   return b1.add(b2).doubleValue();
	}

	/**
	* 两个Double数相减 *
	* @param v1 *
	* @param v2 *
	* @return Double
	*/
	public static double sub(double v1, double v2) {
	   BigDecimal b1 = new BigDecimal(String.valueOf(v1));
	   BigDecimal b2 = new BigDecimal(String.valueOf(v2));
	   return  b1.subtract(b2).doubleValue();
	}

	/**
	* 两个Double数相乘 *
	* @param v1 *
	* @param v2 *
	* @return Double
	*/
	public static double mul(double v1, double v2) {
	   BigDecimal b1 = new BigDecimal(String.valueOf(v1));
	   BigDecimal b2 = new BigDecimal(String.valueOf(v2));
	   return b1.multiply(b2).doubleValue();
	}

	/**
	* 两个Double数相除,保留两位小数
	* @param v1 *
	* @param v2 *
	* @return Double
	*/
	public static Double div(Double v1, Double v2) {
	   BigDecimal b1 = new BigDecimal(v1.toString());
	   BigDecimal b2 = new BigDecimal(v2.toString());
	   return new Double(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	* 两个Double数相除，并保留scale位小数 *
	* @param v1 *
	* @param v2 *
	* @param scale *
	* @return Double
	*/
	public static double div(double v1, double v2, int scale) {
	   if (scale < 0) {
	    throw new IllegalArgumentException(
	      "The scale must be a positive integer or zero");
	   }
	   BigDecimal b1 = new BigDecimal(String.valueOf(v1));
	   BigDecimal b2 = new BigDecimal(String.valueOf(v2));
	   if(v2==0){
		   return 0;
	   }
	   return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * @Description: 数值格式化，保留几位小数
	 * @param obj 数据
	 * @param index 保留小数的位数
	 * @return
	 */
	public static String decimalFormat(double d,int index){

		DecimalFormat df = null;
		StringBuffer buf = new StringBuffer("###0.");
		if(isNotEmpty(d+"")){
			if(index==0){
				 df = new DecimalFormat("###0");
			}else{
				for(int i=0;i<index;i++){
					buf.append("0");
				}
				 df = new DecimalFormat(buf.toString());
			}
			
			String str = df.format(d);
//			处理保留小数后.000的情况
//			if ((int)Double.parseDouble(str) == Double.parseDouble(str) ) {
//				str = String.valueOf((int)Double.parseDouble(str));
//			}
			return str;
		}else{
			return "0";
		}
	}

	/**
	 * 根据计划期返回计划期名称
	 * @return
	 */
	public static String getJhq(String jhq){
		String year = jhq.substring(0, 4);
		String month = jhq.substring(4,5);
		if(Integer.parseInt(month)==1){
			return year+"上半年";
		}else{
			return year+"下半年";
		}
	}
	
	/**
	 * @Description: 获得日期的下一天
	 * @param date
	 * @param format
	 * @return
	 */
	  public static String getNextDay(String date,String format) {
	    if (date == null || date.trim().length() == 0) {
	      return "";
	    }
	    SimpleDateFormat f = new SimpleDateFormat(format);
	    Calendar calendar = Calendar.getInstance();
	    try {
	      calendar.setTime(f.parse(date));
	    }
	    catch (Exception ex) {
	      return date;
	    }
	    calendar.add(5, 1);
	    return f.format(calendar.getTime());
	  }

}
