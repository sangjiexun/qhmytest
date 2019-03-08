package com.fh.util.upload;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UploadConst {
	
	public static final int BUFFER_LENGTH = 1024 * 1024 * 10;//10MB
	
	public static final String CONTENT_RANGE_HEADER = "content-range";
	
	
	/**
	 * 上传文件路径  相对路径
	 */
	public static Map<String, UploadPathVo> uploadPathMap = new HashMap<String, UploadPathVo>();
	//end 上传文件路径
	
	/**
	 * 缴费名单
	 */
	public final static String ITME_LIST = "ITME_LIST";
	/**
	 * 合作学校
	 */
	public final static String PART_SCHOOL = "PART_SCHOOL";
	/**
	 * 发布缴费上传名单
	 */
	public final static String JFFB_LIST = "JFFB_LIST";
	/**
	 * 已经缴费名单
	 */
	public final static String ITME_PAID_LIST = "ITME_PAID_LIST";
	
	/**
	 * 奖惩附件表
	 */
	public final static String T_JIANGCHENG_FJ = "T_JIANGCHENG_FJ";
	
	/**
	 * 分包公司附件表
	 */
	public final static String T_FENBAOSHANG_FJ = "T_FENBAOSHANG_FJ";
	
	/**
	 * 企业资质附件表
	 */
	public final static String T_QIYEZIZHI_FJ = "T_QIYEZIZHI_FJ";
	
	
	/**
	 * 黑名单  T_HEIMINGDAN_FJ
	 */
	public final static String T_HEIMINGDAN_FJ = "T_HEIMINGDAN_FJ";
	
	/**
	 * 安全教育 T_ANQUANJIAOYU_FJ
	 */
	public final static String T_ANQUANJIAOYU_FJ="T_ANQUANJIAOYU_FJ";
	
	/**
	 * 劳务人员照片  身份证
	 */
	public final static String T_LAOWURENYUAN_ZP_SFZ = "T_LAOWURENYUAN_ZP_SFZ";
	
	
	/**
	 * 劳务人员照片 近照
	 */
	public final static String T_LAOWURENYUAN_ZP_JZ = "T_LAOWURENYUAN_ZP_JZ";
	
	/**
	 * 合同文件
	 */
	public final static String T_LAOWURENYUAN_HT = "T_LAOWURENYUAN_HT";
	
	
	/**
	 * 证书
	 */
	public final static String T_LAOWURENYUAN_ZS = "T_LAOWURENYUAN_ZS";

	/**
	 * 工资附件
	 */
	public final static String T_GONGZI_FJ = "T_GONGZI_FJ";
	
	/**
	 * 学生信息
	 */
	public final static String STU_INFO = "STU_INFO";
	/**
	 * 电子卡模板
	 */
	public final static String TEMPLATE_IMAGE = "TEMPLATE_IMAGE";
	
	/**
	 * 老师信息
	 */
	public final static String TEACHER_INFO = "TEACHER_INFO";
	/**
	 * 交易记录
	 */
	public final static String REC_INFO = "REC_INFO";
	
	/**
	 * 宿舍信息
	 */
	public final static String DORM_INFO = "DORM_INFO";
	/**
	 * 班级信息
	 */
	public final static String CLASS_INFO = "CLASS_INFO";
	
	/**
	 * 专业
	 */
	public final static String DEPT = "DEPT";
	/**
	 * 民族
	 */
	public final static String NATION = "NATION";
	/**
	 * 学生来源
	 */
	public final static String STUSOURCE = "STUSOURCE";
	/**
	 * 批量减免
	 */
	public final static String ITME_LIST_JIANMIAN = "ITME_LIST_JIANMIAN";
	/**
	 * 批量贷款
	 */
	public final static String STU_LOAN = "STU_LOAN";
	static{
		/*
		 * 奖惩附件表  
		 */
		UploadPathVo uploadPathVo9 = new UploadPathVo();
		uploadPathVo9.setRelativePath("uploadFiles/jcfj/");//相对路径
		uploadPathMap.put(T_JIANGCHENG_FJ, uploadPathVo9);
		//end 奖惩附件表
		
		/*
		 * 分包公司附件表
		 */
		UploadPathVo uploadPathVo = new UploadPathVo();
		uploadPathVo.setRelativePath("uploadFiles/fbs/");//相对路径
		uploadPathMap.put(T_FENBAOSHANG_FJ, uploadPathVo);//分包商附件表的附件存储路径
		//end 分包公司附件表
		
		/*
		 * 企业资质附件表
		 */
		UploadPathVo uploadPathVo2 = new UploadPathVo();
		uploadPathVo2.setRelativePath("uploadFiles/qyzz/");//相对路径
		uploadPathMap.put(T_QIYEZIZHI_FJ, uploadPathVo2);
		//end 企业资质附件表
		
		
		/*
		 * 黑名单附件表  T_JIANGCHENG_FJ
		 */
		UploadPathVo uploadPathVo3 = new UploadPathVo();
		uploadPathVo3.setRelativePath("uploadFiles/hmd/");//相对路径
		uploadPathMap.put(T_HEIMINGDAN_FJ, uploadPathVo3);
		//end 黑名单附件表
		
		/*
		 * 身份证
		 */
		UploadPathVo uploadPathVo4 = new UploadPathVo();
		uploadPathVo4.setRelativePath("uploadFiles/lwry/sfz/");//相对路径
		uploadPathMap.put(T_LAOWURENYUAN_ZP_SFZ, uploadPathVo4);
		//end 身份证
		
		/*
		 * 合同文件
		 */
		UploadPathVo uploadPathVo5 = new UploadPathVo();
		uploadPathVo5.setRelativePath("uploadFiles/lwry/ht/");//相对路径
		uploadPathMap.put(T_LAOWURENYUAN_HT, uploadPathVo5);
		//end 合同文件
		
		/*
		 * 证书
		 */
		UploadPathVo uploadPathVo6 = new UploadPathVo();
		uploadPathVo6.setRelativePath("uploadFiles/lwry/zs/");//相对路径
		uploadPathMap.put(T_LAOWURENYUAN_ZS, uploadPathVo6);
		//end 证书
		/*
		 * 安全教育附件表
		 */
		UploadPathVo uploadPathVo7 = new UploadPathVo();
		uploadPathVo7.setRelativePath("uploadFiles/aqjy/");//相对路径
		uploadPathMap.put(T_ANQUANJIAOYU_FJ, uploadPathVo7);
		/*
		 * 工资附件
		 */
		UploadPathVo uploadPathVo8 = new UploadPathVo();
		uploadPathVo8.setRelativePath("uploadFiles/gongzi/");//相对路径
		uploadPathMap.put(T_GONGZI_FJ, uploadPathVo8);
		//end 工资
		
		/**
		 * 缴费名单 
		 */
		UploadPathVo uploadPathVo10 = new UploadPathVo();
		uploadPathVo10.setRelativePath("uploadFiles/itemlist/");//相对路径
		uploadPathMap.put(ITME_LIST, uploadPathVo10);
		
		/**
		 * 已经缴费名单 ITME_PAID_LIST
		 */
		UploadPathVo uploadPathVo11 = new UploadPathVo();
		uploadPathVo11.setRelativePath("uploadFiles/itempaidlist/");//相对路径
		uploadPathMap.put(ITME_PAID_LIST, uploadPathVo11);
		
		/**
		 * 发布缴费名单
		 */
		UploadPathVo uploadPathVo15 = new UploadPathVo();
		uploadPathVo15.setRelativePath("uploadFiles/itemjffblist/");//相对路径
		uploadPathMap.put(JFFB_LIST, uploadPathVo15);
		

		UploadPathVo uploadPathVo18 = new UploadPathVo();
		uploadPathVo18.setRelativePath("uploadFiles/stuinfo/");//相对路径
		uploadPathMap.put(STU_INFO, uploadPathVo18);
		
		
		UploadPathVo uploadPathVo19 = new UploadPathVo();
		uploadPathVo19.setRelativePath("uploadFiles/template_image/");//相对路径
		uploadPathMap.put(TEMPLATE_IMAGE, uploadPathVo19);
		
		UploadPathVo uploadPathVo20 = new UploadPathVo();
		uploadPathVo18.setRelativePath("uploadFiles/teacherinfo/");//相对路径
		uploadPathMap.put(TEACHER_INFO, uploadPathVo20);
		
		/**
		 * 交易记录
		 */
		UploadPathVo uploadPathVo21 = new UploadPathVo();
		uploadPathVo21.setRelativePath("uploadFiles/payRecord/");//相对路径
		uploadPathMap.put(REC_INFO, uploadPathVo21);
		
		UploadPathVo uploadPathVo22 = new UploadPathVo();
		uploadPathVo22.setRelativePath("uploadFiles/dorminfo/");//相对路径
		uploadPathMap.put(DORM_INFO, uploadPathVo22);
		
		UploadPathVo uploadPathVo23 = new UploadPathVo();
		uploadPathVo22.setRelativePath("uploadFiles/classinfo/");//相对路径
		uploadPathMap.put(CLASS_INFO, uploadPathVo23);
		
		UploadPathVo uploadPathVo24 = new UploadPathVo();
		uploadPathVo24.setRelativePath("uploadFiles/dept/");//相对路径
		uploadPathMap.put(DEPT, uploadPathVo24);
		
		UploadPathVo uploadPathVo25 = new UploadPathVo();
		uploadPathVo25.setRelativePath("uploadFiles/nation/");//相对路径
		uploadPathMap.put(NATION, uploadPathVo25);
		
		UploadPathVo uploadPathVo26 = new UploadPathVo();
		uploadPathVo26.setRelativePath("uploadFiles/stu_source/");//相对路径
		uploadPathMap.put(STUSOURCE, uploadPathVo26);
		
		UploadPathVo uploadPathVo27 = new UploadPathVo();
		uploadPathVo27.setRelativePath("uploadFiles/jianmian/");//相对路径
		uploadPathMap.put(ITME_LIST_JIANMIAN, uploadPathVo27);
		
		
		UploadPathVo uploadPathVo28 = new UploadPathVo();
		uploadPathVo18.setRelativePath("uploadFiles/stuloan/");//相对路径
		uploadPathMap.put(STU_LOAN, uploadPathVo28);
		
		/**
		 * 合作学校
		 */
		UploadPathVo uploadPathVo29 = new UploadPathVo();
		uploadPathVo29.setRelativePath("uploadFiles/partschool/");//相对路径
		uploadPathMap.put(PART_SCHOOL, uploadPathVo10);
	}
	
	public static void main(String[] args) {
		ClassLoader loader = UploadConst.class.getClassLoader();
		URL url = loader.getResource("");
		String classBasePath = url.getPath();
		
		String path = classBasePath.replaceAll("target/classes/", "");
		
		System.out.println(path);
		
		String absolutePath = path+""+uploadPathMap.get(TEMPLATE_IMAGE).getRelativePath();
		
		
		
		System.out.println(absolutePath);
		
//		classBasePath.replaceAll("WEB-INF/classes", "");
		
	}
	
}
