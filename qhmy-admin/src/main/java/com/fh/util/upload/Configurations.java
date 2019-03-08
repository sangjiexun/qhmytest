package com.fh.util.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;


public class Configurations {
	
	static final String CONFIG_FILE = "stream-config.properties";
	private static Properties properties = null;
	private static final String REPOSITORY = System.getProperty(
			"java.io.tmpdir", File.separator + "tmp" + File.separator
					+ "upload-repository");

	static {
		new Configurations();
	}

	private Configurations() {
		init();
		System.out.println("[NOTICE] File Repository Path ≥≥≥ " + getFileRepository());
	}

	void init() {
		try {
//			uploadFiles/fbs/...
			ClassLoader loader = Configurations.class.getClassLoader();
			InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			properties = new Properties();
			properties.load(in);
			
			URL url = loader.getResource("");
			String classBasePath = url.getPath();
			String path = classBasePath.replaceAll("WEB-INF/classes/", "");
			
			//初始化文件上传的绝对路径
			Map<String, UploadPathVo> uploadPathMap = UploadConst.uploadPathMap;
			
			//分公司附件的绝对路径
			String absolutePath = path+""+uploadPathMap.get(UploadConst.T_FENBAOSHANG_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_FENBAOSHANG_FJ).setAbsolutePath(absolutePath);
			//end 分公司附件的绝对路径
			
			//企业资质的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_QIYEZIZHI_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_QIYEZIZHI_FJ).setAbsolutePath(absolutePath);
			//end 企业资质附件的绝对路径
			
			//黑名单的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_HEIMINGDAN_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_HEIMINGDAN_FJ).setAbsolutePath(absolutePath);
			//end 黑名单的绝对路径
			
			//安全教育的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_ANQUANJIAOYU_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_ANQUANJIAOYU_FJ).setAbsolutePath(absolutePath);
			//end 安全教育的绝对路径
			
			//身份证的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_LAOWURENYUAN_ZP_SFZ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_LAOWURENYUAN_ZP_SFZ).setAbsolutePath(absolutePath);
			//end 黑名单的身份证
			
			//劳务人员合同文件的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_LAOWURENYUAN_HT)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_LAOWURENYUAN_HT).setAbsolutePath(absolutePath);
			//end 劳务人员合同文件的绝对路径
			
			//证书的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_LAOWURENYUAN_ZS)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_LAOWURENYUAN_ZS).setAbsolutePath(absolutePath);
			//end 证书的绝对路径
			
			//工资附件的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_GONGZI_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_GONGZI_FJ).setAbsolutePath(absolutePath);
			//end 工资附件的绝对路径
			//奖惩的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.T_JIANGCHENG_FJ)
					.getRelativePath();
			uploadPathMap.get(UploadConst.T_JIANGCHENG_FJ).setAbsolutePath(absolutePath);
			//end 奖惩的绝对路径
			
			//缴费名单的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.ITME_LIST)
					.getRelativePath();
			uploadPathMap.get(UploadConst.ITME_LIST).setAbsolutePath(absolutePath);
			//缴费名单的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.ITME_LIST)
					.getRelativePath();
			uploadPathMap.get(UploadConst.ITME_LIST).setAbsolutePath(absolutePath);
			//导入学生信息的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.STU_INFO)
					.getRelativePath();
			uploadPathMap.get(UploadConst.STU_INFO).setAbsolutePath(absolutePath);
			//导入已缴费名单的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.ITME_PAID_LIST)
					.getRelativePath();
			uploadPathMap.get(UploadConst.ITME_PAID_LIST).setAbsolutePath(absolutePath);
			//end 缴费名单的绝对路径
			//导入发布缴费时的文件导入的绝对路径
			absolutePath = path+""+uploadPathMap.get(UploadConst.JFFB_LIST)
					.getRelativePath();
			uploadPathMap.get(UploadConst.JFFB_LIST).setAbsolutePath(absolutePath);
			
			//end 初始化文件上传的绝对路径
		} catch (IOException e) {
			System.err.println("reading `" + CONFIG_FILE + "` error!" + e);
		}
	}

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}

	public static String getFileRepository() {
		String val = getConfig("STREAM_FILE_REPOSITORY");
		if (val == null || val.isEmpty())
			val = REPOSITORY;
		return val;
	}
	
	/**
	 * 根据表名查询附件的文件目录路径
	 * 
	 * @param tableName
	 * @return
	 */
	public static UploadPathVo getFileRepository(String tableName) {
//		String val = getConfig("STREAM_FILE_REPOSITORY");
//		if (val == null || val.isEmpty())
//			val = REPOSITORY;
		return UploadConst.uploadPathMap.get(tableName);
	}

	public static String getCrossServer() {
		return getConfig("STREAM_CROSS_SERVER");
	}
	
	public static String getCrossOrigins() {
		return getConfig("STREAM_CROSS_ORIGIN");
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getConfig(key));
	}
	
	public static boolean isDeleteFinished() {
		return getBoolean("STREAM_DELETE_FINISH");
	}
	
	public static boolean isCrossed() {
		return getBoolean("STREAM_IS_CROSS");
	}
	
}
