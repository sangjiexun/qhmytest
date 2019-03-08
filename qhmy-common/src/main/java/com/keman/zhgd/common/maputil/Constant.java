package com.keman.zhgd.common.maputil;


public class Constant {
	//是否累计分页
	public static final String IS_PAGE_SUM = "1";
	
	//临时文件存放路径
	public static final String TEMP_PATH = "c:\\bdtemp\\";
	//每个sheet写入的行数
	public static final int SHEET_SIZE = 60000;
	/**
	 * 页面参数封装的对象名称（MAP）
	 */
	public static final String PACKAGEPARAMSNAME = "FJDAKFDAFDAFDSAF";
	/**
	 * 出现错误的页面
	 */
	public static final String ERRORPAGE = "/info/error.jsp";
	/**
	 * 自动转向登录页面
	 */
	public static final String RELOGINPAGE = "/info/relogin.jsp";
	
	/**
	 * xml文件的目录
	 */
	static Constant cs = new Constant();
	public static final String XML_FILES_PATH= cs.getClass().getResource("//").getPath()+"/xmls";
	
	/**
	 * request常量
	 */
	public static final String REQUEST = "REQUESTMMCAKSJDIAJDW";
	/**
	 * response常量
	 */
	public static final String RESPONSE = "RESPONSEPLMKIIWWCDDf";
	/**
	 * 分页信息表
	 */
	public static final String PAGEINFO = "fdsalkfjdaslfd";
	
	/**
	 * 过滤条件常量
	 */
	public static final String FILTER = "T12321fdsafda";
	
	/**
	 * 存储用户Session的键值名称
	 */
	public static final String SESSIONNAME = "zxcvbnmasdfghjklqwertyuiop";
	
	/**
	 * 排序字段
	 */
	public static final String ORDERCOL="orderField";
	/**
	 * 排序方向
	 */
	public static final String ORDERDIR="orderDirection";
	
}
