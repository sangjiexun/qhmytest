package com.fh.util.kaoqin;

import java.util.Date;

/**
 * 考勤表需要使用此类
 * 考勤表头
 * @author Administrator
 *
 */
public class KaoqinHead {
	
	/**
	 * 本月的月份值  如:  1-12
	 */
	private String byMonth;
	
	/**
	 * 上一月的月份值  如: 1-12
	 */
	private String preMonth;
	
	
	/**
	 *  本月还是上一月
	 *  monthFlag: pre是上一月  by是本月
	 */
	private String monthFlag;
	
	
	/**
	 * 日期  1-31
	 */
	private String day;
	
	
	/**
	 * 是否自然月
	 */
	private boolean sfzry;
	
	
	private Date date;


	/**
	 * 本月的月份值  如:  1-12
	 */
	public String getByMonth() {
		return byMonth;
	}


	/**
	 * 本月的月份值  如:  1-12
	 */
	public void setByMonth(String byMonth) {
		this.byMonth = byMonth;
	}

	/**
	 * 上一月的月份值  如: 1-12
	 */
	public String getPreMonth() {
		return preMonth;
	}

	/**
	 * 上一月的月份值  如: 1-12
	 */
	public void setPreMonth(String preMonth) {
		this.preMonth = preMonth;
	}

	/**
	 *  本月还是上一月
	 *  monthFlag: pre是上一月  by是本月
	 */
	public String getMonthFlag() {
		return monthFlag;
	}

	/**
	 *  本月还是上一月
	 *  monthFlag: pre是上一月  by是本月
	 */
	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag;
	}

	/**
	 * 日期  1-31
	 */
	public String getDay() {
		return day;
	}

	/**
	 * 日期  1-31
	 */
	public void setDay(String day) {
		this.day = day;
	}


	/**
	 * 是否自然月
	 */
	public boolean isSfzry() {
		return sfzry;
	}


	/**
	 * 是否自然月
	 */
	public void setSfzry(boolean sfzry) {
		this.sfzry = sfzry;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("byMonth: "+getByMonth()+"; MonthFlag: "+getMonthFlag()+"; Day: "+getDay());
		return stringBuffer.toString();
	}
	
	
	
}
