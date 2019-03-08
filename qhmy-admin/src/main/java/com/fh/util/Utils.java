package com.fh.util;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import jxl.Workbook;

/**
 * 
 * @author zxs
 *
 */
@SuppressWarnings("unchecked")
public class Utils {

	public static String createID() {
		return UUID.randomUUID().toString();
	}

	public static Boolean isNotEmpty(Object obj){
		if(obj == null){
			return false;
		}else{
			return true;
		}
	}
	 /** 
		 * 获取年份列表--连续10年
		 * */
	public static List<PageData> YearList(int index){
		List<PageData> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			PageData pd = new PageData();
			pd.put(index+i+"", index+i+"年");
			list.add(pd);
		}
		return list;
	}
	 /** 
	 * 根据年 月 获取对应的月份 天数 
	 * */ 
	public static int getDaysByYearMonth(int year, int month){  
         
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.YEAR, year);  
	        a.set(Calendar.MONTH, month - 1);  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	}
	/**
	 * 根据出生日期获取年龄
	 * 
	 * @param 
	 * @return 
	 */
	public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException("年龄不能超过当前日期");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
            if (nowDayOfYear < bornDayOfYear) {
                age -= 1;
            }
        }
        return age;
    }
	/**
	 * 判断字符串是否为空值
	 * 
	 * @param str
	 * @return 不为空返回真 ，否则为假
	 */
	public static Boolean isNotEmpty(String str) {

		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 转成utf-8
	 * 
	 * @param str
	 * @return
	 */
	public static String toUTF8(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 导出excel文件
	 * @param request
	 * @param response
	 * @param fileName	文件名
	 * @param headerNames 表头
	 * @param bodyNames list每条记录的key值
	 * @param list 其中放map对象
	 * @return
	 * @throws Exception
	 */
	public static Boolean exportExcel(HttpServletRequest request,
			HttpServletResponse response,String fileName, String[] headerNames,String[] bodyNames, List list) {
		
		if (fileName == null) {
			fileName = "temp";
		}
		
		if(fileName==null||headerNames.length<1){
			return false;
		}
		
//		if (list == null || list.size()<=0) {
//			PrintWriter out;
//			try {
//				out = response.getWriter();
//				out.print("<script language='javascript'>alert('未找到符合条件的记录行！')");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return false;
//		}
		
		
		fileName = fileName+".xls";	//2003-excel格式
		response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");
        try {
			response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return false;
		} 
		
		jxl.write.WritableWorkbook wwb = null;
        
        try {
			wwb = Workbook.createWorkbook(response.getOutputStream());
			jxl.write.WritableSheet ws = wwb.createSheet("TestSheet1", 0);
			
			//头内容
			jxl.write.Label label00 = new jxl.write.Label(0, 0, "序号");
			ws.addCell(label00);
			for (int i = 0; i < headerNames.length; i++) {
				jxl.write.Label label01 = new jxl.write.Label(i+1, 0, headerNames[i]);
				ws.addCell(label01);
			}
			
			//数据内容
			for (int i = 0; i < list.size(); i++) {
				//序号
				jxl.write.Label label0 = new jxl.write.Label(0, i+1, i+1+"");
				ws.addCell(label0);
				
				Map map =  (Map)list.get(i);
				for (int j = 0,k=1; j < bodyNames.length; j++) {
				 	Object object = map.get(bodyNames[j]);
				 	String val = "";
				 	if (object instanceof Timestamp) {
				 		Timestamp timestamp = (Timestamp)object;
				 		Date date = new Date();
				 		date.setTime(timestamp.getTime());
						java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						val = simpleDateFormat.format(date);
					}else if (object instanceof Date) {
						java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						val = simpleDateFormat.format(object);
					}else {
						val = String.valueOf(map.get(bodyNames[j])); //仓库代码
					}
				 	if (val == null || "null".equals(val)) {
						val = "";
					}
					jxl.write.Label label1 = new jxl.write.Label(k++, i+1, val);
					ws.addCell(label1);
				}
			}
			// 写入Excel工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
//			try {
//				//wwb.close();
//			} catch (WriteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		return true;
	}
	public static void readFile(String readPath,String writePath){
        /* 创建输入和输出流 */
        FileInputStream fis = null;
        FileOutputStream fos = null;
 
        try {
            /* 将io流和文件关联 */
            fis = new FileInputStream(readPath);
            fos = new FileOutputStream(writePath);
            byte[] buf = new byte[1024 * 1024];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
 
            }
        } catch (Exception e) {
        	System.out.println(e.toString());
        }
         finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
            	System.out.println(e.toString());
            }
        }
    }
	public static String toUtf8(String target){
		String rst = "";
		try {
			rst = new String(target.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rst;
	}
	public static Map<String,Object> toExcel(String[] headerNames,String[] bodyNames,List<PageData> varOList){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		for(int i=0;i<headerNames.length;i++){
			titles.add(headerNames[i]);
		}
		dataMap.put("titles", titles);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			for(int j=0;j<bodyNames.length;j++){
				Object var = varOList.get(i).get(bodyNames[j]);
				if(var==null){
					var = "";
				}else if("Y".equals(var)){
					var = "是";
				}else if("N".equals(var)){
					var = "否";
				}/*else if("HG".equals(var)){
					var = DictZiDianSelect.PingJiaZTEnum.getNameByValue(var.toString());
				}else if("BHG".equals(var)){
					var = DictZiDianSelect.PingJiaZTEnum.getNameByValue(var.toString());
				}else if("001".equals(var)){
					var = DictZiDianSelect.QIYELEIBIE.getNameByValue(var.toString());
				}else if("002".equals(var)){
					var = DictZiDianSelect.QIYELEIBIE.getNameByValue(var.toString());
				}*/
				vpd.put("var"+(j+1), var);
			}
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		return dataMap;
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
	public static int getLingshuliang(String string){
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
	 * 相反数
	 * @param value
	 * @return
	 */
	public static Double xiangfanshu(Double value){
		if (value > 0 ) {
			return -value;
		}
		return Math.abs(value);
	}

	/**
	 * 递归截取上级部门代码
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

	/**
	 * 根据sql查询语句，获得查询总记录数的sql语句
	 * 比如：
	 * select o from Organization o where o.parent is null
	 * 经过转换，可以得到：
	 * select count(*) from Organization o where o.parent is null
	 * @param sql
	 * @return
	 */
	public static String getCountQuery(String sql){
		int index = sql.toUpperCase().indexOf("FROM");
		if(index != -1){
			/**
			 * 包头不包尾：
			 * 字符串：abcdefghijklmn
			 * substring(5,8) == fgh
			 */
			return "select count(*) "+sql.substring(index);
		}
		throw new RuntimeException("无效的SQL查询语句【"+sql+"】");
	}
	//图片转化成base64字符串  
    public static String GetImageStr()  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "d://Koala.jpg";//待处理的图片  
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
      
    //base64字符串转化成图片  
    public static boolean GenerateImage(String imgStr,String path)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成jpeg图片  
            String imgFilePath = path;//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  
    public static void main(String[] args) {
    	/*String strImg = GetImageStr();  
        System.out.println(strImg);  
        GenerateImage(strImg,"d://wangning.jpeg");*/ 
    	stringToPd("");
	}
    public static PageData stringToPd(String str){
    	//net.sf.ezmorph.bean.MorphDynaBean@617feeab[{21212121=200, 2121=100}]
    	PageData pd = new PageData();
    	String newStr = str.substring(str.indexOf("{")+1,str.indexOf("}"));
    	String[] mapStr = newStr.split(",");
    	for(String s:mapStr){
    		String[] ss = s.split("=");
    		pd.put(ss[0].trim(), ss[1].trim());
    	}
    	System.out.println(pd.toString());
    	return pd;
    }
    //金额验证  
    public static boolean isNumber(String str){   
         Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式  
         Matcher match=pattern.matcher(str);   
         if(match.matches()==false){   
            return false;   
         }else{   
            return true;   
         }   
     }  
}
