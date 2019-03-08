package com.keman.zhgd.common.datetime;  
  
public class DateSeason {  
  
    
    /*  
    * 得到某个日期上一年的年份  
    * @param dateString 格式yyyy-MM-dd  
    * @return 4位年份值  
    */  
   public static String getLastYear(String dateString){  
       String currentYear = dateString.substring(0, 4);//该日期的所在年份  
       return String.valueOf(Integer.parseInt(currentYear)-1);  
   } 
    /**    
   * 得到某个日期上月的月份  
   * @param dateString 格式yyyy-MM-dd  
   * @return 2位月份值 1月份返回12  
   */  
  public static String getLastMonth(String dateString){  
      String currentMonth = dateString.substring(5, 7);//改日期所在月份  
      if("01".equals(currentMonth)){  
          currentMonth = "12";  
      }else if(Integer.parseInt(currentMonth)>10){  
          currentMonth = String.valueOf(Integer.parseInt(currentMonth)-1);  
      }else{  
          currentMonth = "0"+String.valueOf(Integer.parseInt(currentMonth)-1);  
      }  
      return currentMonth;  
  }  
   
  /**    
   * 得到某一日期的上个月的开始日期  
   * @param dateString 格式yyyy-MM-dd  
   * @return 8位日期值  
   */  
  public static String getBeginningDateOfLastMonth(String dateString) {  
      String lastYear = getLastYear(dateString);//该日期上年年份  
      String lastMonth = getLastMonth(dateString);//改日期上月月份  
      if("12".equals(lastMonth)){  
          return lastYear+"-"+lastMonth+"-01";  
      }else{  
          return dateString.substring(0,4)+"-"+lastMonth+"-01";  
      }  
  }  
   
  /**    
   * 得到某个日期上月月份的结束日期  
   * @param dateString 格式yyyy-MM-dd  
   * @return 8位日期值  
   */  
  public static String getEndingDateOfLastMonth(String dateString){  
      String lastYear_month = getBeginningDateOfLastMonth(dateString).substring(0, 8);//该月份上月月份的年+月  
      String lastYear = lastYear_month.substring(0,4);//该日期上年年份  
      String lastMonth = lastYear_month.substring(5,7);//该日期上月月份  
      String date = null;  
      switch (Integer.parseInt(lastMonth)) {  
      case 2:  
          int intValueOfYear = Integer.parseInt(lastYear);  
          if((intValueOfYear%4==0)||(intValueOfYear%100!=0&&intValueOfYear%400==0)){  
              date = lastYear_month+"29";//闰年2月29天  
          }else{  
              date = lastYear_month+"28";//非闰年2月28天  
          }  
          break;  
      case 4:  
      case 6:  
      case 9:  
      case 11:  
          date = lastYear_month+"30";//4,6,9,11月30天  
          break;  
      default:  
          date = lastYear_month+"31";//1,3,5,7,8,10,12月31天  
          break;  
      }  
      return date;  
  }  
   
  /**    
   * 得到某个日期上一季度的起始月份  
   * @param dateString  
   * @return 2位月份值  
   */  
  public static String getBeginningMonthOfLastSeason(String dateString){  
      String currentMonth = dateString.substring(5, 7);//当前月份  
      String monthOfLastSeason = null;//上一季度起始月份  
      switch(Integer.parseInt(currentMonth)){  
      case 1:  
      case 2:  
      case 3:  
          monthOfLastSeason = "10";  
          break;  
      case 4:  
      case 5:  
      case 6:  
          monthOfLastSeason = "01";  
          break;  
      case 7:  
      case 8:  
      case 9:  
          monthOfLastSeason = "04";  
          break;  
      default:  
          monthOfLastSeason = "07";  
          break;  
      }  
      return monthOfLastSeason;  
  }  
   
  /**    
   * 得到某个日期上一季度的结束月份  
   * @param dateString  
   * @return 2位月份值  
   */  
  public static String getEndingMonthOfLastSeason(String dateString){  
      String currentMonth = dateString.substring(5, 7);//当前月份  
      String monthOfLastSeason = null;//上一季度结束月份  
      switch(Integer.parseInt(currentMonth)){  
      case 1:  
      case 2:  
      case 3:  
          monthOfLastSeason = "12";  
          break;  
      case 4:  
      case 5:  
      case 6:  
          monthOfLastSeason = "03";  
          break;  
      case 7:  
      case 8:  
      case 9:  
          monthOfLastSeason = "06";  
          break;  
      default:  
          monthOfLastSeason = "09";  
          break;  
      }  
      return monthOfLastSeason;  
  }  

  /**    
   * 得到某个日期上一季度的开始日期  
   * @param dateString  
   * @return 8位日期  
   */  
  public static String getBeginingDateOfLastSeason(String dateString){  
      String currentYear = dateString.substring(0, 4);//该日期所在年份  
      String lastYear = getLastYear(dateString);//该日期上一年年份  
      String currentMonth = dateString.substring(5, 7);//该日期所在月份  
      String beginningMonthOfLastSeason = getBeginningMonthOfLastSeason(dateString);//该日期上一季度起始月份  
      String date = null;  
      if("01".equals(currentMonth)||"02".equals(currentMonth)||"03".equals(currentMonth)){  
          date = lastYear+"-01-01";  
      }else{  
          date = currentYear+"-"+beginningMonthOfLastSeason+"-01";  
      }  
      return date;  
  }  
   
  /**    
   * 得到某个日期上一季度的结束日期  
   * @param dateString  
   * @return 8位日期  
   */  
  public static String getEndingDateOfLastSeason(String dateString){  
      String currentYear = dateString.substring(0, 4);//该日期所在年份  
      String lastYear = getLastYear(dateString);//该日期上一年年份  
      String currentMonth = dateString.substring(5, 7);//该日期所在月份  
      String endingMonthOfLastSeason = getEndingMonthOfLastSeason(dateString);//该日期上季度结束月份  
      String date = null;  
      if("01".equals(currentMonth)||"02".equals(currentMonth)||"03".equals(currentMonth)){  
          switch (Integer.parseInt(endingMonthOfLastSeason)) {  
          case 2:  
              int intValueOfYear = Integer.parseInt(lastYear);  
              if((intValueOfYear%4==0)||(intValueOfYear%100!=0&&intValueOfYear%400==0)){  
                  date = lastYear+"-"+endingMonthOfLastSeason+"-29";//闰年2月29天  
              }else{  
                  date = lastYear+"-"+endingMonthOfLastSeason+"-28";//非闰年2月28天  
              }  
              break;  
          case 4:  
          case 6:  
          case 9:  
          case 11:  
              date = lastYear+"-"+endingMonthOfLastSeason+"-30";//4,6,9,11月30天  
              break;  
          default:  
              date = lastYear+"-"+endingMonthOfLastSeason+"-31";//1,3,5,7,8,10,12月31天  
              break;  
          }  
      }else{  
          switch (Integer.parseInt(endingMonthOfLastSeason)) {  
          case 2:  
              int intValueOfYear = Integer.parseInt(currentYear);  
              if((intValueOfYear%4==0)||(intValueOfYear%100!=0&&intValueOfYear%400==0)){  
                  date = currentYear+"-"+endingMonthOfLastSeason+"-29";//闰年2月29天  
              }else{  
                  date = currentYear+"-"+endingMonthOfLastSeason+"-28";//非闰年2月28天  
              }  
              break;  
          case 4:  
          case 6:  
          case 9:  
          case 11:  
              date = currentYear+"-"+endingMonthOfLastSeason+"-30";//4,6,9,11月30天  
              break;  
          default:  
              date = currentYear+"-"+endingMonthOfLastSeason+"-31";//1,3,5,7,8,10,12月31天  
              break;  
          }  
      }  
      return date;  
  }  
  /**    
   * 得到某一日期的本月的开始日期  
   * @param dateString 格式yyyy-MM-dd  
   * @return 8位日期值  
   */  
  public static String getBeginningDateOfThisMonth(String dateString) {  
       
          return dateString.substring(0,7)+"-01";  
      
  }  
   
  /**    
   * 得到某个日期本月的结束日期  
   * @param dateString 格式yyyy-MM-dd  
   * @return 8位日期值  
   */  
  public static String getEndingDateOfThistMonth(String dateString){  
      String lastYear_month = dateString.substring(0, 8);//该月份上月月份的年+月  
      String lastYear = dateString.substring(0,4);//该日期上年年份  
      String lastMonth = dateString.substring(5,7);//该日期上月月份  
      String date = null;  
      switch (Integer.parseInt(lastMonth)) {  
      case 2:  
          int intValueOfYear = Integer.parseInt(lastYear);  
          if((intValueOfYear%4==0)||(intValueOfYear%100!=0&&intValueOfYear%400==0)){  
              date = lastYear_month+"29";//闰年2月29天  
          }else{  
              date = lastYear_month+"28";//非闰年2月28天  
          }  
          break;  
      case 4:  
      case 6:  
      case 9:  
      case 11:  
          date = lastYear_month+"30";//4,6,9,11月30天  
          break;  
      default:  
          date = lastYear_month+"31";//1,3,5,7,8,10,12月31天  
          break;  
      }  
      return date;  
  } 
   
 
}  