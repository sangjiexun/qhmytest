package com.keman.zhgd.common.maputil;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * @ClassName: com.eversec.common.util.ReadPerpertiesUtil
 * @Description:
 * @创建人:Peng
 * @创建日期:Mar 1, 201210:31:03 AM
 * @修改人:
 * @修改日期:
 * @修改原因:
 * @version:
 */
public class ReadPerpertiesUtil {

	public static void read(Properties pro, String fileName) {
		try {
			pro.load(ReadPerpertiesUtil.class.getClassLoader().getResourceAsStream(fileName+".properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
