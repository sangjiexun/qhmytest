package com.keman.zhgd.common.maputil;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
    /**
     * 去除字符串中的回车、换行符
     * 
     * @param str
     * @return
     */
    public static String replaceBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    /**
     * 
     * <li>功能描述：去除字符串中所有空格。
     * @param str
     * @return
     * String 
     * @创建人:liu huan huan
     * @创建日期:Mar 14, 2014
     * @修改人:xu ming yi 
     * @修改日期:Mar 14, 2014
     * @修改原因:
     * @version $Id: $
     */
    public static String replaceTrim(String str)
    {
        String dest = "";
        if (str != null)
        {
            dest = str.replaceAll("\\s", "");
        }
        return dest;
    }

    /**
     * 判断字符串是否为空,null,"","null"
     * 
     * @param param
     * @return
     */
    public static boolean isNull(String param)
    {
        if (param == null || "".equals(param.trim())
                || "null".equals(param.trim().toLowerCase()))
        {
            return true;
        }
        return false;
    }

    public static String toGBK(String string)
    {
        try
        {
            if (StringUtils.isNull(string))
            {
                return "";
            }
            return new String(string.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static String iso8859_1ToGBK(String string)
    {
        try
        {
            if (StringUtils.isNull(string))
            {
                return "";
            }
            return new String(string.getBytes("ISO8859_1"), "GBK");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static String toBianma(String string, String srcBianma,
            String tobianma) throws Exception
    {
        try
        {
            if (srcBianma != null && !"".equals(srcBianma))
            {
                return new String(string.getBytes(srcBianma), tobianma);
            } else
            {
                return new String(string.getBytes(), tobianma);
            }
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public static String toUS7(String string) throws Exception
    {
        if ((string == null) || (string.trim().equals("")))
            return string;

        try
        {
            return new String(string.getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public static String toUS7(String bianma, String string) throws Exception
    {
        if ((string == null) || (string.trim().equals("")))
            return string;
        try
        {
            return new String(string.getBytes(bianma), "ISO8859_1");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 全角转半角
     * 
     * @param QJstr
     * @return
     * @throws Exception
     */
    public static String fullToHalfChange(String QJstr) throws Exception
    {
        if (QJstr == null)
        {
            return null;
        }

        if ("".equals(QJstr.trim()))
        {
            return "";
        }

        QJstr = QJstr.replaceAll("【", "[");
        QJstr = QJstr.replaceAll("　", "");
        QJstr = QJstr.replaceAll("】", "]");

        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++)
        {
            try
            {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }

            if (b[3] == -1)
            {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;

                try
                {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr.trim();
    }

    /**
     * 半角转全角
     * 
     * @param QJstr
     * @return
     * @throws Exception
     */
    // 全角转半角
    public static final String QBchange(String QJstr)
    {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++)
        {
            try
            {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if (b[3] == -1)
            {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try
                {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr;
    }

    public static void main(String[] args) throws Exception
    {
        String QJstr = "测试-５()（）";
        String result = fullToHalfChange(QJstr);
        System.out.println(QJstr);
        System.out.println(result);
        System.out.println("------------------------------------");

        String qysh = "1301\0    aaaaaa";
        System.out.println("aaaa="
                + org.springframework.util.StringUtils.trimWhitespace(qysh));
        System.out.println(qysh);
        System.out.println(delTsChars(qysh));
        org.springframework.util.StringUtils.trimAllWhitespace(qysh);
    }

    /**
     * 删除特殊字符
     * 
     * @param qysh
     * @return
     */
    public static String delTsChars(String string)
    {
        if (string == null)
        {
            return null;
        }
        char[] chars = string.toCharArray();
        int rst = -1;
        for (int i = 0; i < chars.length; i++)
        {
            rst = (int) chars[i];
            if (rst == 0)
            {
                string = string.replaceAll(String.valueOf(chars[i]), "");
            }
        }
        return string;
    }

}
