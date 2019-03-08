package com.keman.zhgd.common.datetime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Level;

import com.keman.zhgd.common.maputil.LoggerUtil;

public class DateUtil {
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}
	
	/**
	 * 获得系统当前时间，返回Date类型
	 * @return
	 */
	public static Date nowDate(){
		return new Date();
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author fh
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }
	
	
	public static Date now() {
		return Calendar.getInstance().getTime();
	}
	
	public static String getCurrentTimeMillis(){
		return String.valueOf(System.currentTimeMillis());
	}

	public static String getSysdate() throws Exception{
		return DateUtil.getSysdate();
	}
	
	/**
	 * 得到千年
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 */
	
	
	/**
	 * 得到当前日期
	 * @param dateFormat
	 * @return
	 * @throws Exception
	 */
	public static String getSysdate(String dateFormat){
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
			return simpleDateFormat.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.outLog(Level.DEBUG, "getSysdate方法出错");
		}
		return "";
	}
	public static String toString(Date d) {
		return toString(d, "yyyy-MM-dd HH:mm:ss");
	}
    /**
     * 判断字符串是否为日期格式
     * @param dateStr 字符串
     * @param format 格式字符串,如：yyyy-MM-dd,yyyy-MM-dd HH:mm:ss
     * @return 
     */
    public static boolean isDate(String dateStr,String format){
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        format1.setLenient(false);
        try {
            format1.parse(dateStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	public static String toString(Date d, String fmt) {

		if (null == d)
			return "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			return sdf.format(d);
		} catch (Exception e) {

		}
		return d.toString();
	}

	/**
	 * 将字符串格式的时间进行转换
	 * 
	 * @param d
	 * @param fmt
	 * @return
	 */
	public static Date toDate(String d, String fmt) {
		if (null == d)
			return new Date();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			System.out.println(sdf.parse(d));
			return sdf.parse(d);
		} catch (Exception e) {

		}
		return new Date();
	}

	/**
	 * 将java.util.Date类型转换为java.sql.Date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date toJavaSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Date类型转换为java.sql.Time
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Time toJavaSqlTime(Date date) {
		return new java.sql.Time(date.getTime());
	}

	public static java.sql.Timestamp toJavaSqlTimestamp(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}
    public static java.util.Date toJavaUtilDate(Timestamp timet)
    {
        Date date = new Date();
        date = timet;
        return date;
    }

	/**
	 * 得到二个日期间的间隔分钟数
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 开始时间和结束时间之间相差的分钟数
	 */
	public static int getMinutes(Date begin, Date end) {
		long m = 0;

		try {
			m = (end.getTime() - begin.getTime()) / (60 * 1000);
		} catch (Exception e) {
			return 0;
		}

		return (int) m;
	}

	/**
	 * 获取两个日期的间隔天数
	 * 
	 * @param ksrq
	 * @param jsrq
	 * @return
	 */
	public static int getQuot(String ksrq, String jsrq) {
		int days = 0;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c_b = Calendar.getInstance();
		Calendar c_e = Calendar.getInstance();

		try {
			c_b.setTime(df.parse(ksrq));
			c_e.setTime(df.parse(jsrq));

			while (c_b.before(c_e)) {
				days++;
				c_b.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException pe) {
			System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
		}

		return days;
	}

	public static String lastYear() {
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)-1);
		Date date=curr.getTime();
		String lastYear = toString(date, "yyyy");
		return lastYear ;
	}
	
	public static String addDay(int days) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return myFormatter.format(calendar.getTime());
	}

	public static String addDay(String date, int days) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		try {
			trialTime = myFormatter.parse(date);
		} catch (ParseException e) {
		}
		calendar.setTime(trialTime);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return myFormatter.format(calendar.getTime());
	}

	public static String subDay(int days) {
		// SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// Calendar calendar = new GregorianCalendar();
		// Date trialTime = new Date();
		// calendar.setTime(trialTime);
		// calendar.add(Calendar.DAY_OF_MONTH, -days);
		return addDay(-days);
	}

	public static String subDay(String date, int days) {
		return addDay(date, -days);
	}

	public static String addMonth(int month) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		calendar.add(Calendar.MONTH, month);
		return myFormatter.format(calendar.getTime());
	}

	public static String subMonth(int month) {
		// SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// Calendar calendar = new GregorianCalendar();
		// Date trialTime = new Date();
		// calendar.setTime(trialTime);
		// calendar.add(Calendar.MONTH, -month);
		return addMonth(-month);// myFormatter.format(calendar.getTime());
	}

	// 将日期转换成yyyy年MM月dd日
	public static String transDate(String date) {
		Date reDate = toDate(date, "yyyy-MM-dd");

		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy年MM月dd日");
		return myFormatter.format(reDate);

	}

	public static void main(String[] args) throws Exception {
		// System.out.print(DateUtil.subDay("2010-3-1", 1));

		System.out.println(DateUtil.getQuot("2013-06-24", "2013-06-25"));
		System.out.println(DateUtil.getCurrentTimeMillis());
		// System.out.println(DateUtil.transDate(DateUtil.now().toString()));

	}

	/**
	 * 日期格式化
	 * 
	 * @param htrq
	 * @param i
	 * @return
	 */
	public static String formatDate(String date, int i) {
		Date reDate = toDate(date, "yyyy-MM-dd");

		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return myFormatter.format(reDate);
	}
	//本月第一天
	public static String monthFristDay() {
		Calendar cal_czcs = Calendar.getInstance(); 
		cal_czcs.setTime(new Date()); 
		cal_czcs.set(Calendar.DAY_OF_MONTH, 1); 
		return new SimpleDateFormat("yyyy-MM-dd").format(cal_czcs.getTime()); 
	}
	
	//本月最后一天
	public static String monthLastDay() {
		Calendar cal_czcs = Calendar.getInstance(); 
		cal_czcs.setTime(new Date()); 
		cal_czcs.set(Calendar.DAY_OF_MONTH, cal_czcs.getActualMaximum

				(Calendar.DAY_OF_MONTH));     
		return new SimpleDateFormat("yyyy-MM-dd").format(cal_czcs.getTime()); 
		}
	/**
	 * 日期格式化
	 * 
	 * @param htrq
	 * @param i
	 *            0:yyyy-MM-dd 1:yyyy-MM
	 * @return
	 */
	public static String formatDate(Date date, int i) {
		String fmt = "yyyy-MM-dd";
		if (i == 1) {
			fmt = "yyyy-MM";
		}
		if (i == 2) {
			fmt = "yyyy";
		}
		SimpleDateFormat myFormatter = new SimpleDateFormat(fmt);
		return myFormatter.format(date);
	}
	/**
	 * 自定义格式化日期
	 * @param date
	 * @param f
	 * @return
	 */
	public static String fomatDate(Date date,String f) {
		SimpleDateFormat fmt = new SimpleDateFormat(f);
		try {
			return fmt.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
