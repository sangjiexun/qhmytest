package com.keman.zhgd.common.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil
{
	/**
	 * 全拼
	 * @param src
	 * @return
	 */
  public static String getHanyuPingYin(String src)
  {
    char[] srcCharArray = src.toCharArray();
    String[] t2 = new String[srcCharArray.length];

    HanyuPinyinOutputFormat fmt = new HanyuPinyinOutputFormat();
    fmt.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    fmt.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    fmt.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

    String sResult = "";
    try
    {
      for (int i = 0; i < srcCharArray.length; ++i)
      {
        if (Character.toString(srcCharArray[i]).matches("[\\u4E00-\\u9FA5]+"))
        {
          t2 = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i], fmt);
          sResult = sResult + t2[0];
        }
        else {
          sResult = sResult + Character.toString(srcCharArray[i]); }
      }
      return sResult;
    }
    catch (BadHanyuPinyinOutputFormatCombination e1) {
      e1.printStackTrace();
    }
    return sResult;
  }

  /**
   * 简拼
   * @param src
   * @return
   */
  public static String getHanyuPingYinInitial(String src)
  {
    char[] srcCharArray = src.toCharArray();
    String[] t2 = new String[srcCharArray.length];

    String sResult = "";

    for (int i = 0; i < srcCharArray.length; ++i)
    {
      if (Character.toString(srcCharArray[i]).matches("[\\u4E00-\\u9FA5]+"))
      {
        t2 = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i]);
        if (t2 != null)
          sResult = sResult + t2[0].substring(0, 1);
      }
      else {
        sResult = sResult + Character.toString(srcCharArray[i]); }
    }
    return sResult.toUpperCase();
  }

  /**
   * 商品简拼
   * @param src
   * @return
   */
  public static String getSpHanyuPingYinInitial(String src)
  {
	  
//	 //1、select count(1) from t_spb 
//	  
//	 //  9+1=10  99 999 9999
//	 
//	 //方法(10,4)        if() 10.length = 2 if(2){"00"+10}
//	 
//	 //10.length  4-10.length = 2  
//	  
//	  //while(2){
//	  		string+"0"	
//  		}
//	


    char[] srcCharArray = src.toCharArray();
    String[] t2 = new String[srcCharArray.length];
    char[] cc = new char[]{'X','X','X','X'};
    String sResult = "";
    int splength=srcCharArray.length;
    if (splength>=4) {
    	for (int i = 0; i < 4; ++i) {
    		 if (Character.toString(srcCharArray[i]).matches("[\\u4E00-\\u9FA5]+"))
    		      {
    		        t2 = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i]);
    		        if (t2 != null)
    		        {
    		          sResult = sResult + t2[0].substring(0, 1);
    		        }
    		      }
    		      else 
    		      	{
    		        sResult = sResult + Character.toString(srcCharArray[i])+"_"; 
    		        }
		}
    	
    	sResult=sResult+"-";
	}else {
		for (int i = 0; i < splength; i++) {
			if (Character.toString(srcCharArray[i]).matches("[\\u4E00-\\u9FA5]+"))
		      {
		        t2 = PinyinHelper.toHanyuPinyinStringArray(srcCharArray[i]);
		        if (t2 != null)
		        {
		          sResult = sResult + t2[0].substring(0, 1);
		        }
		      }
		      else 
		      	{
		        sResult = sResult + Character.toString(srcCharArray[i])+"_"+"num"; 
		        }
		}
		for (int j = 0; j < 4-splength; j++) {
			sResult=sResult+cc[j];
			System.out.println(sResult);
		}
		sResult=sResult+"-";
	}
    return sResult.toUpperCase();
  }
  
  
  public static void main(String[] args)
  {
    String s = "朱镕基蔡赟率abcd1234567890";
    System.out.println(getHanyuPingYin(s));
    System.out.println(getHanyuPingYinInitial(s));
  }
}