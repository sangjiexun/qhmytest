package com.keman.zhgd.common.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 分析字符串日期格式
 * @author Administrator
 *
 */
public class FxDateStrFormat {
	
	private static FxDateStrFormat fxDateStrFormat = new FxDateStrFormat();
	
	private FxDateStrFormat(){
		
	}
	
	public static FxDateStrFormat getInstance(){
		return fxDateStrFormat;
	}
	
	/**
	 * 将日期格式化为标准样式
	 * @param dateStr
	 * @return
	 */
	public String formateStringDate(String dateStr){
		//先去掉首尾空格
		dateStr = dateStr.trim();
		//将空格替换成一个空格
		dateStr = dateStr.replaceAll(" +", " ");
		dateStr = dateStr.replaceAll("年|月|日", "-");
		dateStr = dateStr.replaceAll("时|分|秒", ":");
		return dateStr;
	}
	
	/**
	 * 返回日期的格式
	 * @param dateStr
	 * @return
	 */
	public String getDateFormat(String dateStr){
//		String string = "2012-09-08";
//		String string2 = "2012年10月30日";
//		String string3 = "2011-01-01 10:30:22";
//		String string4 = "2012";
//		String string5 = "2012-10";
		
		String rst = "";
		
		//先去掉首尾空格
		dateStr = dateStr.trim();
		//将空格替换成一个下划线
		dateStr = dateStr.replaceAll(" +", "_");
		dateStr = dateStr.replaceAll("年|月|日|时|分|秒|:", "-");
		
		String [] arrays = dateStr.split("_");
		StringBuffer rstBuffer = new StringBuffer();
		if (arrays.length == 1) {
			//没有小时分钟秒
			String ymrStr = arrays[0];
			String [] ymrArrays = ymrStr.split("-");
			for (int i = 0; i < ymrArrays.length; i++) {
				if (i==0) {
					//年
					rstBuffer.append("yyyy-");
				}else if (i == 1) {
					rstBuffer.append("MM-");
				}else if (i == 2) {
					rstBuffer.append("dd-");
				}
			}
			String ymrString = rstBuffer.substring(0, rstBuffer.length()-1);//去掉最后的-符号
			rst = ymrString;
		}else if (arrays.length>1) {
			//有小时分钟秒
			String ymrStr = arrays[0];
			String [] ymrArrays = ymrStr.split("-");
			for (int i = 0; i < ymrArrays.length; i++) {
				if (i==0) {
					//年
					rstBuffer.append("yyyy-");
				}else if (i == 1) {
					rstBuffer.append("MM-");
				}else if (i == 2) {
					rstBuffer.append("dd-");
				}
			}
			String ymrString = rstBuffer.substring(0, rstBuffer.length()-1);//去掉最后的-符号
			rstBuffer.setLength(0);
			
			String xfmStr = arrays[1];
			String [] xfmArrays = xfmStr.split("-");
			for (int i = 0; i < xfmArrays.length; i++) {
				if (i==0) {
					rstBuffer.append("hh:");
				}else if (i == 1) {
					rstBuffer.append("mm:");
				}else if (i == 2) {
					rstBuffer.append("ss:");
				}
			}
			String xfmString = rstBuffer.substring(0, rstBuffer.length()-1);//去掉最后的-符号
			rst = ymrString +" "+ xfmString;
		}
		return rst;
	}
	
	public static void main(String[] args) throws Exception{
		String string3 = "2011-01-01 05:24:10";
		FxDateStrFormat fxDateStrFormat = FxDateStrFormat.getInstance();
		System.out.println(fxDateStrFormat.getDateFormat(string3));
		String newDate = fxDateStrFormat.formateStringDate(string3);
		System.out.println(newDate);
		String format = fxDateStrFormat.getDateFormat(newDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = simpleDateFormat.parse(string3);
		System.out.println(date);
	}
	
	
}
