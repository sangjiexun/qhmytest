package com.keman.zhgd.common.spring;

public class PageVariable {
	/**
	 * js 页面唯一变量名
	 */
	public final static String JS_PAGEID = "Jspageid";
	
	/**
	 * 页面唯一DIV变量名
	 */
	public final static String DIV_PAGEID = "Divpageid";
	
	/**
	 * 页面唯一表单变量名
	 */
	public final static String FORM_PAGEID = "Formpageid";
	
	/**
	 * 开关调试信息
	 */
	protected static boolean pagevariableKg = false;
	
	/**
	 * 是否sql输出
	 */
	protected static boolean sqlshuchu = false;
	
	/**
	 * 请求路径是否输出
	 */
	protected static boolean requesturl = false;
	
}
