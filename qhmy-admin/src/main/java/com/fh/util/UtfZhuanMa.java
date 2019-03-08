package com.fh.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.keman.zhgd.common.Tools;

public class UtfZhuanMa {

	
	public static PageData zhuanMa(PageData pd){
		Iterator entries = pd.entrySet().iterator();
		while(entries.hasNext()){
			  Map.Entry<Object, Object> entry = (Entry<Object, Object>) entries.next();
			  try {
				entry.setValue(new String(entry.getValue().toString().getBytes("ISO8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pd;
	}
	
	/**
	 * 转码
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年10月18日 下午3:52:01
	 * @param pd
	 * @return
	 */
	public static PageData zhuanMas(PageData pd){
		Iterator entries = pd.entrySet().iterator();
		while(entries.hasNext()){
			  Map.Entry<Object, Object> entry = (Entry<Object, Object>) entries.next();
			  try {
				  String value = entry.getValue().toString();
				  if(!StringUtils.isEmpty(value) && isMessyCode(value)){
					  value = new String(entry.getValue().toString().getBytes("ISO8859-1"), "utf-8");
				  }
				  entry.setValue(value);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pd;
	}
	
	/**
	 * 判断字符串是否是乱码
	 * <p>描述:</p>
	 * @author Administrator 陈超超
	 * @date 2017年10月18日 下午3:48:26
	 * @param strName
	 * @return
	 */
    private static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
    /**
     * 判断字符是否中文
     * <p>描述:</p>
     * @author Administrator 陈超超
     * @date 2017年10月18日 下午3:49:44
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
