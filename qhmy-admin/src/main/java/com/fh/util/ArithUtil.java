package com.fh.util;  
  
import java.math.BigDecimal;  
  
  /**
   * 
   * <p>标题:加减乘除公共方法</p>
   * <p>描述:</p>
   * <p>组织:河北科曼信息技术有限公司</p>
   * @author Administrator 胡文浩
   * @date 2017年8月8日 下午6:42:44
   */
public class ArithUtil {  
    // 默认除法运算精度  
    private static final int DEF_DIV_SCALE = 10;  
  
 
    public static double add(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);// 建议写string类型的参数，下同  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
  
  
    public static double sub(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
  
    
    public static double mul(String v1, String v2) {  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
  
    
    public static double div(String v1, String v2) {  
        return div(v1, v2, DEF_DIV_SCALE);  
    }  
    
    private static double div(String v1, String v2, int scale,int r) {  
        if (scale < 0) {  
            throw new IllegalArgumentException(" the scale must be a positive integer or zero");  
        }  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();// scale 后的四舍五入  
    }  
    
   
    public static double div(String v1, String v2, int scale) {  
        if (scale < 0) {  
            throw new IllegalArgumentException(" the scale must be a positive integer or zero");  
        }  
        BigDecimal b1 = new BigDecimal(v1);  
        BigDecimal b2 = new BigDecimal(v2);  
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();// scale 后的四舍五入  
    }  
    
    
}