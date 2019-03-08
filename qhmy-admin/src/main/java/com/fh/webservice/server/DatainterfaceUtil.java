package com.fh.webservice.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.util.JsonUtil;
import com.fh.util.Md5Util;
import com.fh.util.PageData;
import com.keman.zhgd.common.util.ImageAnd64Binary;

/**
 * 入参检查等方法
 * 构建客户端请求数据
 * @author Administrator
 *
 */
public class DatainterfaceUtil {
	
//	* XMBM:项目编码    必传字段
//	 * REQUEST_DATE:请求时间(yyyy-MM-dd HH24:mi:ss)  必传   
//	 * DATAMESSAGES:数据密文   必填字段   将XMBM属性的值  + HBKEMAN2017 这样的字符进行MD5加密后的密文
//	 * METHOD:方法:   心跳检测接口：JC_XTJC 等等  详见文档
//	 * MAXRECORD：请求记录最大行数: -1不限制行数或者为空都不限制   其他有效整数做限制
//	 * REFRESH_DATE:刷新时间:服务端会根据此时间查询数据表的数据修改时间，大于等于此时间点的数据会返回给客户端 (yyyy-MM-dd HH24:mi:ss)
	
	private static final String [] keyArray = {"XMBM","REQUEST_DATE","DATAMESSAGES","METHOD","MAXRECORD","REFRESH_DATE"};
	
	
	/**
	 * 入参必传属性检查
	 * @param pageData
	 * @return
	 */
	public static boolean checkRequestData(PageData pageData){
		boolean rst = true;
		
		for (int i = 0; i < keyArray.length; i++) {
			if (!pageData.containsKey(keyArray[i])) {
				rst = false;
				return rst;
			}
		}
		return rst;
	}
	
	/**
	 * 根据项目编码分析返回企业编码
	 * <p>描述:</p>
	 * @author Administrator 鲁震
	 * @date 2017年6月27日 上午10:53:53
	 * @param xmbm
	 * @return
	 */
	public static String getQybmByXmbm(String xmbm){
		if (xmbm == null) {
			return "";
		}
		
		try {
			int index = xmbm.indexOf("E");
			if (index == -1) {
				throw new Exception("error");
			}
			int end = index+6;
			return xmbm.substring(index,end);
		} catch (Exception e) {
			return "";
		}
//		P0009E01842
	}
	
	public static void main(String[] args) {
		String xmbm = "P0009E018421253";
		
		System.out.println(getQybmByXmbm(xmbm));
	}
	
	
	/**
	 * 构建客户端请求数据
	 * @return
	 */
	public static String clientCreateData(){
		String REQUEST_DATE = "2017-07-15 16:59:17";
		String DATAMESSAGES = "df513c606e24f04e7750957d381d4c24";//
//		String METHOD = FhadminEnum.心跳检测.getvalue();
//		String METHOD = FhadminEnum.下载基础数据.getvalue();//P0001E01681
//		String METHOD = FhadminEnum.下载劳务人员.getvalue();//P0030E01502
//		String METHOD = FhadminEnum.上传刷卡数据.getvalue();//P0604E01842
		
//		"SHUAKASHIJIAN":"2017-07-12 15:31:31","KAHAO":"FFD76828","SHUAKASHEBEIH":"223211463","MENQU_PKID":"1","LAOWURENYUAN_PKID":"805829e734594972986a893cb0e37dfd","JINHUOCHU":"1","XIANGMU_BIANMA":"P0604E01842","SHUAKAFS":"1","ZHAOPIAN":""
		
		
		String METHOD = FhadminEnum.下载黑名单.getvalue();//P0001E01842
		
		String MAXRECORD = "20";
		String REFRESH_DATE="2017-07-12 16:42:00";
		String XMBM = "P0009E0236100010001";//P0001E01842
		DATAMESSAGES = Md5Util.md5(XMBM);
		Map map = new HashMap();
		
		//刷卡数据
		List<PageData> SKLIST = new ArrayList<PageData>();
		PageData p1 = new PageData();
		PageData p2 = new PageData();
		PageData p3 = new PageData();
		
		p1.put("SHUAKASHIJIAN", "2017/6/9 9:13:44");
		p1.put("KAHAO", "FFD76828");
		p1.put("SHUAKASHEBEIH", "223211463");
		p1.put("MENQU_PKID", "1");
		p1.put("LAOWURENYUAN_PKID", "805829e734594972986a893cb0e37dfd");
		p1.put("JINHUOCHU", "1");
		p1.put("XIANGMU_BIANMA", "P0604E01842");
//		p1.put("QIYE_BIANMA", "001");
		p1.put("SHUAKAFS", "1");
		
//		String imgSrcPath = "e:\\tupian1.jpg"; // 生成64编码的图片的路径
//		String base64code = ImageAnd64Binary.getImageStr(imgSrcPath);
//		
		p1.put("ZHAOPIAN", "");
		
		
		p2.put("SHUAKASHIJIAN", "2017/6/9 9:13:44");
		p2.put("KAHAO", "FFD76828");
		p2.put("SHUAKASHEBEIH", "223211463");
		p2.put("MENQU_PKID", "1");
		p2.put("LAOWURENYUAN_PKID", "805829e734594972986a893cb0e37dfd");
		p2.put("JINHUOCHU", "1");
		p2.put("XIANGMU_BIANMA", "P0604E01842");
//		p2.put("QIYE_BIANMA", "001");
		p2.put("SHUAKAFS", "1");
		
		String imgSrcPath = "e:\\tupian1.jpg"; // 生成64编码的图片的路径
		String base64code = ImageAnd64Binary.getImageStr(imgSrcPath);
//		
		p2.put("ZHAOPIAN", base64code);
		
		SKLIST.add(p1);
		SKLIST.add(p2);
//		SKLIST.add(p3);
		map.put("SKLIST", SKLIST);
		//end 刷卡数据
		
		map.put("REQUEST_DATE", REQUEST_DATE);
		map.put("DATAMESSAGES", DATAMESSAGES);
		map.put("METHOD", METHOD);
		map.put("MAXRECORD", MAXRECORD);
		map.put("REFRESH_DATE", REFRESH_DATE);
		map.put("XMBM", XMBM);
		
		return JsonUtil.mapToJsonStr(map);
	}
	
	
	
	
}
