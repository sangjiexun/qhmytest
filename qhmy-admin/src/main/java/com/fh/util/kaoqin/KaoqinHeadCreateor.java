package com.fh.util.kaoqin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 考勤表头生成类
 * @author Administrator
 *
 */
public class KaoqinHeadCreateor {
	
	private static KaoqinHeadCreateor kaoqinHeadCreateor = new KaoqinHeadCreateor();

	public static final String PRE_MONTH = "pre";
	
	public static final String BY_MONTH = "by";
	
	private KaoqinHeadCreateor(){
		
	}
	
	public static KaoqinHeadCreateor getInstance(){
		return kaoqinHeadCreateor;
	}
	
	/**
	 * 生成考勤头
	 * @return
	 */
	public List<KaoqinHead> createKaoqinHead(String start,String end){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		List<KaoqinHead> list = new ArrayList<KaoqinHead>();
		
		KaoqinHead kaoqinHead = null;
		
		try {
			startCalendar.setTime(simpleDateFormat.parse(start));
			endCalendar.setTime(simpleDateFormat.parse(end));
			
			int maxDay;
			int curDay;
		
			
			if (startCalendar.get(Calendar.MONTH)!=endCalendar.get(Calendar.MONTH)) {
				//跨月了
				
				maxDay = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);//一个月的天数
				curDay = startCalendar.get(Calendar.DATE);//当前几号
				
				//生成开始日期
				for (int i = curDay; i <= maxDay; i++) {
					kaoqinHead = new KaoqinHead();
					kaoqinHead.setDay(String.format("%02d", i));
					kaoqinHead.setByMonth(String.valueOf(startCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setMonthFlag(PRE_MONTH);//上个月
					kaoqinHead.setPreMonth(String.valueOf(startCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setSfzry(false);//非自然月
					list.add(kaoqinHead);
				}
				
				
				maxDay = endCalendar.get(Calendar.DATE);//下个月的截止日期
				curDay = 1;//当前几号
				for (int i = curDay; i <= maxDay; i++) {
					kaoqinHead = new KaoqinHead();
					kaoqinHead.setDay(String.format("%02d", i));
					kaoqinHead.setByMonth(String.valueOf(endCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setMonthFlag(BY_MONTH);//是本月
					kaoqinHead.setPreMonth(String.valueOf(startCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setSfzry(false);//非自然月
					list.add(kaoqinHead);
				}
				
			}else {
				//没有跨月
				maxDay = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);//一个月的天数
				curDay = startCalendar.get(Calendar.DATE);//当前几号
				
				for (int i = curDay; i <= maxDay; i++) {
					kaoqinHead = new KaoqinHead();
					kaoqinHead.setDay(String.format("%02d", i));
					kaoqinHead.setByMonth(String.valueOf(startCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setMonthFlag(BY_MONTH);//上个月
					kaoqinHead.setPreMonth(String.valueOf(startCalendar.get(Calendar.MONTH)+1));
					kaoqinHead.setSfzry(true);//非自然月
					list.add(kaoqinHead);
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public static void main(String[] args) {
		String riQiQi = "2017-2-24";
		String riQiZhi = "2017-3-23";
		KaoqinHeadCreateor kaoqinHeadCreateor = KaoqinHeadCreateor.getInstance();
		List<KaoqinHead> kaoqinHeads = kaoqinHeadCreateor.createKaoqinHead(riQiQi,riQiZhi);
		
		for (KaoqinHead kaoqinHead : kaoqinHeads) {
			System.out.println(kaoqinHead.toString());
		}
		
		
		System.out.println(String.format("%02d", 1));
		
//		String.format("%02d",String.valueOf(startCalendar.get(Calendar.MONTH)+1))
		
	}
	
	
	
	
	
}
