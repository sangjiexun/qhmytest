/**
 * 
 */
package com.keman.zhgd.common.maputil;

import java.math.BigDecimal;

/**
 * @author LHG
 * 
 */
public class NumberFormat {
	public static int DEF_DIV_SCALE = 2;// 小数位两位四舍五入
	public static final int MULTIPLE = 5;// 计算价格时的倍数

	public static int toInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int toInt(String str, int defaultInt) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultInt;
		}
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		return mul(Double.toString(v1), Double.toString(v2));
	}

	public static double mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		double result = 0;
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		if (v2 != 0) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			result = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		}
		return result;
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(String v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(

			"The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(v);

		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * double转换成string，防止变成科学计数法 陈磊 2010年5月15日9:28:08 异常返回“0”
	 */
	public static String doubleToString(double value) {
		String res_value = "0";
		try {
			// 不让Double变成科学计数法
			java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			res_value = nf.format(value);
		} catch (Exception ex) {
			res_value = "0";
			ex.printStackTrace();
		}
		return res_value;
	}
	/**
	 * @author zhangmeijuan
	 * 如果传入的double数字型字符串小数点之后全是0，把小数点和之后的零都去掉：如"0.0"变为"0""12.000000"变为"0"
	 * @param doubleString
	 * @return
	 */
	public static String deleteString0(String doubleString){
		if(doubleString.indexOf(".")!=-1){
			String strW=doubleString.substring(doubleString.indexOf(".")+1);
			if(Double.parseDouble(strW)==0){
				return doubleString.substring(0,doubleString.indexOf("."));
			}else{
				return doubleString;
			}
		}else{
			return doubleString;
		}
	}
	/**
	 * @author zhangmeijuan
	 * 如果传入的double数字小数点之后全是0，把小数点和之后的零都去掉：如"0.0"变为"0""12.000000"变为"0"
	 * @param doublenum
	 * @return
	 */
	public static String deleteString0(double doublenum){
		String str=doublenum+"";
		return deleteString0(str);
	}
	
	/**
	 * 保留两位小数
	 * @param data
	 * @return
	 */
	public static String roundDouble(String data){

		String result = "0.00";
		BigDecimal dsz = new BigDecimal(data);
		try {
			result = dsz.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception e) {
		}
		return result;
	}
}
