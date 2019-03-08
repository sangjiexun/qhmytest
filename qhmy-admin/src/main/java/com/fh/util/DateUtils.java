package com.fh.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author kangchl
 *
 */
public class DateUtils {
	/**
	 * 校验时间格式
	 * @param strDateTime
	 * @return
	 */
	public static int checkDateFormatAndValite(String strDateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ndate = format.parse(strDateTime);
			String str = format.format(ndate);
			if (str.equals(strDateTime))
				return 1;
			else
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
