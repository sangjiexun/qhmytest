package com.keman.zhgd.common.stringutil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理的工具类
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class StringUtil {
	
	/**
	 * 判断字符串是否为空,null,"","null"
	 * @param param
	 * @return
	 */
	public static boolean isNull(String param) {
		if (param == null || "".equals(param.trim())
				|| "null".equals(param.trim().toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 去除字符串中的回车、换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * 全角转半角
	 * @param QJstr
	 * @return
	 * @throws Exception
	 */
	public static String fullToHalfChange(String QJstr) throws Exception {
		if (QJstr == null) {
			return null;
		}
		
		if ("".equals(QJstr.trim())) {
			return "";
		}
		
		QJstr = QJstr.replaceAll("【","[");
		QJstr = QJstr.replaceAll("　", "");
		QJstr = QJstr.replaceAll("】", "]");
		
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;

				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr.trim();
	}

	/**
	 * 半角转全角
	 * @param QJstr
	 * @return
	 * @throws Exception
	 */
	// 全角转半角
	public static final String QBchange(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}
	
	/**
	 * 得到一个字符串的数字和非数字字符串部分
	 * @param string [0]:是数字部分
	 * @return
	 */
	public static String [] getNumAndChars(String string){
		String [] rst = new String[2];
		char [] charArrays = string.toCharArray();
		int size = charArrays.length - 1;
		char temp;
		while(size>=0){
			temp = charArrays[size];
			size--;
			if (String.valueOf(temp).matches("[0-9]+")) {
				continue;
			}else {
				break;
			}
		}
		size = size+2;
		rst[0] = string.substring(size);
		rst[1] = string.substring(0, size);
		return rst;
	}
	
	/**
	 * 判断一个字符串，如果最前面是0，计算开头有多少个0
	 * @param string
	 * @return
	 */
	private static int getLingshuliang(String string){
		if (string.startsWith("0")) {
			char [] charArrays = string.toCharArray();
			int size = charArrays.length;
			char temp;
			int lingshuliang = 0;
			for (int i = 0; i < size; i++) {
				temp = charArrays[i];
				if (String.valueOf(temp).equals("0")) {
					lingshuliang++;
				}
			}
			return lingshuliang;
		}else {
			return 0;
		}
	}
	
	/**
	 * 指定一个字符串，将这个字符串加上sl，返回结果
	 * 自动补零
	 * 字符串格式 :100  ,0001  , 10001
	 * @param target
	 * @param sl
	 * @return
	 */
	public static String getBulingjieguo(String target,int sl){
		//得到开头零的个数
		int linggeshu = getLingshuliang(target);//零的个数
		//int shuziLength = target.length();//这个数字字符串的长度
		int shuzhi = Integer.parseInt(target);//数值
//		int shuzhiLength = String.valueOf(shuzhi).length();//数值的长度
		int xiangjiarst = shuzhi + sl;//相加后的结果
		
		if (target.length() <= String.valueOf(xiangjiarst).length() || linggeshu == 0) {
			return String.valueOf(xiangjiarst);
		}
		
		//计算需要补几个零
		int bulinggeshu = linggeshu - (String.valueOf(xiangjiarst).length() - String.valueOf(shuzhi).length());
		String xiangjiaStr = String.valueOf(xiangjiarst);
		while(bulinggeshu > 0){
			bulinggeshu--;
			xiangjiaStr = "0" + xiangjiaStr;
		}
		
		return xiangjiaStr;
	}
	
	/**
	 * 递归截取上级部门代码,将编码存放到一个List
	 * 2 表示2位一级
	 * @return
	 */
	
	
	public static void createParentBmdmsByBmdm(String bmdm, List parentBmdmList) {
		if (bmdm.length() < 5) {
			return;
		}
		String parentBmdm = bmdm.substring(0, bmdm.length()-2);
		parentBmdmList.add(parentBmdm);
		createParentBmdmsByBmdm(parentBmdm,parentBmdmList);
	}
	
	
	
	
	public static void main(String[] args) {
		String string = "00010";
		System.out.println(getBulingjieguo(string, 99999));
	}
	
}
