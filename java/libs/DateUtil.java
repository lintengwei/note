package com.di.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.di.exception.AppException;

public class DateUtil
{

   public static void main(String[] args) throws Exception
   {

      String clocktime = DateUtil.afterDate("minute", DateUtil.StringToDate("14:00", "HH:mm"), -3, "HH:mm");
      System.out.println(clocktime);
      String begin = "2017-01";
      System.out.println(DateUtil.afterDate("day", DateUtil.StringToDate(begin, "yyyy-MM"), 33, "yyyy-MM"));
      System.out.println(isValidDate("255:255", "HH:mm"));
      System.out.println(getMonday("2016-02-14"));
      System.out.println(getMonday("2016-02-15"));
      System.out.println(getMonday("2016-02-16"));
      System.out.println(getMonday("2016-02-17"));
      System.out.println(getMonday("2016-02-18"));
      System.out.println(getMonday("2016-02-19"));
      System.out.println(getMonday("2016-02-20"));
      System.out.println(getMonday("2016-02-21"));
      System.out.println(getMonday("2016-02-22"));
      System.out.println(getSunday("2016-02-14"));
      System.out.println(getSunday("2016-02-15"));
      System.out.println(getSunday("2016-02-16"));
      System.out.println(getSunday("2016-02-17"));
      System.out.println(getSunday("2016-02-18"));
      System.out.println(getSunday("2016-02-19"));
      System.out.println(getSunday("2016-02-20"));
      System.out.println(getSunday("2016-02-21"));
      System.out.println(getSunday("2016-02-22"));
      //System.out.println(DateToString(null, "yyyy-MM-01"));
      //System.out.println(DateToString(null, "yyyy-MM-31"));
      //System.out.println(DateToString(null, "yyyyMMddHHmmssSSS"));
      //long l = new Date().getTime();
      //System.out.println(l);

      //System.out.println(formatLongTime(l, null));
      long l2 = 1429689781404L;
      System.out.println(formatLongTime(l2, "yyyy-MM-dd HH:mm:ss.SSS"));

      System.out.println(getWeekOfDate(new Date()));
      System.out.println(getWeekOfDate("2016-02-14", "yyyy-MM-dd"));

      String srcdate = "20150805110939";
      String srcFmt = "yyyyMMddHHmmss";
      String rstFmt = "yyyy-MM-dd HH:mm:ss";
      System.out.println("日期格式转换,原格式：" + srcdate + "[" + srcFmt + "]");
      System.out.println("转换后的结果,新格式：" + changeDateStringFormat(srcdate,
         srcFmt,
         rstFmt)
         + "["
         + rstFmt
         + "]");

      rstFmt = "MM月dd日";
      System.out.println("转换后的结果,新格式：" + changeDateStringFormat(srcdate,
         srcFmt,
         rstFmt)
         + "["
         + rstFmt
         + "]");

      System.out.println("2015-10-12是第" + WeekOfYear("2015-10-12", "yyyy-MM-dd")
         + "周");

      System.out.println("本周日：" + getCurSunday());

      int interval = getIntervalDay("2015-12-26", "2015-12-25");
      System.out.println("间隔：" + interval + "天");

//      String a = afterDate("mon", StringToDate("201511", "yyyyMM"), 1, "yyyyMM");
//      System.err.println(a);
      
      System.out.println(getWeekNoOfDate(new Date()));

   }

   /**
    * 得到当前日期(java.sql.Date类型)，注意：没有时间，只有日期
    * 
    * @return 当前日期
    */
   public static java.sql.Date getDate()
   {
      Calendar oneCalendar = Calendar.getInstance();
      return getDate(oneCalendar.get(Calendar.YEAR),
         oneCalendar.get(Calendar.MONTH) + 1,
         oneCalendar.get(Calendar.DATE));
   }

   /**
    * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
    * 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
    * 
    * @param yyyy
    *            4位年
    * @param MM
    *            月
    * @param dd
    *            日
    * @return 日期
    */
   public static java.sql.Date getDate(int yyyy, int MM, int dd)
   {
      if (!verityDate(yyyy, MM, dd))
         throw new IllegalArgumentException("This is illegimate date!");

      Calendar oneCalendar = Calendar.getInstance();
      oneCalendar.clear();
      oneCalendar.set(yyyy, MM - 1, dd);
      return new java.sql.Date(oneCalendar.getTime().getTime());
   }

   /**
    * 根据所给年、月、日，检验是否为合法日期。
    * 
    * @param yyyy
    *            4位年
    * @param MM
    *            月
    * @param dd
    *            日
    * @return
    */
   public static boolean verityDate(int yyyy, int MM, int dd)
   {
      boolean flag = false;

      if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31)
      {
         if (MM == 4 || MM == 6 || MM == 9 || MM == 11)
         {
            if (dd <= 30) flag = true;
         }
         else if (MM == 2)
         {
            if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0)
            {
               if (dd <= 29) flag = true;
            }
            else if (dd <= 28) flag = true;

         }
         else
            flag = true;

      }
      return flag;
   }

   /**
    * 检查日期字符串是否正确。格式：yyyy-MM-dd
    * 
    * @param dateString
    * @return
    */
   public static boolean checkDateString(String dateString) throws AppException
   {
      boolean check = false;

      try
      {
         Date oneDay = stringToDate(dateString, "", true);
         Calendar ca = Calendar.getInstance();
         ca.clear();
         ca.setTime(oneDay);
         int yyyy = ca.get(Calendar.YEAR);
         if (yyyy >= 1000 && yyyy <= 9999) check = true;
      }
      catch (AppException e)
      {
         throw new AppException("日期格式错误[" + dateString + "]");
      }

      return check;
   }

   /**
    * 得到日期字符串的年，必须是4位数字。
    * 
    * @param dateStr
    * @return
    * @throws ActionException
    */
   public static String getYear(String dateStr) throws AppException
   {

      Date oneDay = stringToDate(dateStr, "", true);
      Calendar ca = Calendar.getInstance();
      ca.clear();
      ca.setTime(oneDay);
      int yyyy = ca.get(Calendar.YEAR);
      if (yyyy < 1000 || yyyy > 9999) throw new AppException("| 错误的年！ |");

      return "" + yyyy;

   }

   /**
    * 根据所给的起始,终止时间来计算间隔天数
    * 
    * @param startDate
    * @param endDate
    * @return 间隔天数
    */
   public static int getIntervalDay(java.sql.Date startDate,
      java.sql.Date endDate)
   {
      long startdate = startDate.getTime();
      long enddate = endDate.getTime();
      long interval = enddate - startdate;
      int intervalday = (int) interval / (1000 * 60 * 60 * 24);
      return intervalday;
   }

   public static int getIntervalDay(String startDate1, String endDate1)
   {
      int intervalday = 0;
      try
      {
         Date startDate = stringToDate(startDate1, "", false);
         Date endDate = stringToDate(endDate1, "", false);
         long startdate = startDate.getTime();
         long enddate = endDate.getTime();
         long interval = enddate - startdate;
         intervalday = (int) interval / (1000 * 60 * 60 * 24);
         intervalday = Math.abs(intervalday);
      }
      catch (Exception e)
      {
         // TODO: handle exception
      }

      return intervalday;
   }

   /**
    * 获取输入时间后n个月的YYYYMM
    * 
    * @param inputDate
    * @param n
    * @return
    */
   public static String getYearAndMonth(String dateStr, int n) throws AppException
   {
      Date oneDay = stringToDate(dateStr, "", true);
      Calendar ca = Calendar.getInstance();
      ca.clear();
      ca.setTime(oneDay);

      ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + n);

      int yyyy = ca.get(Calendar.YEAR);
      if (yyyy < 1000 || yyyy > 9999) throw new AppException("| 错误的年！ |");
      int MM = ca.get(Calendar.MONTH) + 1;
      String month = "" + MM;

      if (MM < 10) month = "0" + MM;
      return "" + yyyy + month;
   }

   /**
    * 根据所给的起始,终止时间来计算间隔月数
    * 
    * @param startDate
    *            YYYYMM
    * @param endDate
    *            YYYYMM
    * @return 间隔月数
    */
   public static int getIntervalMonth(String startDate, String endDate)
   {
      int startYear = Integer.parseInt(startDate.substring(0, 4));
      int startMonth = 0;
      if (startDate.substring(4, 5).equals("0"))
         startMonth = Integer.parseInt(startDate.substring(5));
      startMonth = Integer.parseInt(startDate.substring(4, 6));

      int endYear = Integer.parseInt(endDate.substring(0, 4));
      int endMonth = 0;
      if (endDate.substring(4, 5).equals("0"))
         endMonth = Integer.parseInt(endDate.substring(5));
      endMonth = Integer.parseInt(endDate.substring(4, 6));

      int intervalMonth = (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
      return intervalMonth;
   }

   /**
    * 得到年月，yyyyMM格式
    * 
    * @param dateStr
    * @return
    * @throws ActionException
    */
   public static String getYearAndMonth(String dateStr) throws AppException
   {
      Date oneDay = stringToDate(dateStr, "", true);
      Calendar ca = Calendar.getInstance();
      ca.clear();
      ca.setTime(oneDay);
      int yyyy = ca.get(Calendar.YEAR);
      if (yyyy < 1000 || yyyy > 9999) throw new AppException("错误的年份");
      int MM = ca.get(Calendar.MONTH) + 1;
      String month = "" + MM;
      if (MM < 10) month = "0" + MM;
      return "" + yyyy + month;
   }

   public static Date stringToDate(String strDate,
      String paraName,
      boolean isCanNull) throws AppException
   {
      Date targetDate = null;
      if (strDate == null || strDate.equals(""))
      {
         if (isCanNull)
            return null;
         else
            throw new AppException("| 传入的参数：" + paraName + "为空，无法转换成 Date 型 |");
      }
      if (strDate.indexOf("-") == -1)
         throw new AppException("| 传入的参数：" + paraName + "格式不对，无法转换成 Date 型 |");
      int yyyy, MM, dd;
      try
      {
         yyyy = Integer.parseInt(strDate.substring(0, strDate.indexOf("-")));
         MM = Integer.parseInt(strDate.substring(strDate.indexOf("-") + 1,
            strDate.lastIndexOf("-")));
         dd = Integer.parseInt(strDate.substring(strDate.lastIndexOf("-") + 1,
            strDate.length()));

         targetDate = DateUtil.getDate(yyyy, MM, dd);
      }
      catch (NumberFormatException nfe)
      {
         throw new AppException("| 传入的参数：" + paraName + "错误，无法转换成 Date 型 |");
      }
      catch (IllegalArgumentException e)
      {
         throw new AppException("| 传入的参数：" + paraName + "错误，无法转换成 Date 型 |");
      }
      return targetDate;
   }

   /**
    * 描述：格式化日期 说明：fmt格式注意大小写 yyyy 年 MM 月 dd 日 HH 24小时制 hh 12小时制 mm 分钟 ss 秒
    */
   public static String formatDate(Date date, String fmt)
   {
      if (date == null)
      {
         date = new java.util.Date();
      }
      SimpleDateFormat s1 = new SimpleDateFormat(fmt);
      return s1.format(date);
   }

   /**
    * 若干单位后的日期时间
    * 
    * @param type
    *            类型 day日 hour时 minute分 second秒
    * @param num
    *            数量 正数：后 负数：前
    * @param format
    *            返回的日期格式 当天:beforecDate("day", 0, null)); 昨天:beforecDate("day",
    *            -1, null)); 明天:beforecDate("day", 1, null));
    *            一小时前:beforecDate("hour", -1, null)); 一小时后:beforecDate("hour",
    *            1, null)); 一分钟前:beforecDate("minute", -1, null));
    *            一分钟后:beforecDate("minute", 1, null));
    *            一秒钟前:beforecDate("second", -1, null));
    *            一秒钟后:beforecDate("second", 1, null));
    * @return
    */
   public static String afterDate(String type, Date date, int num, String format)
   {
      long count = 1;
      if (GU.isNull(type))
      {
         return "";
      }
      if ("day".equals(type))
      {
         count = 24 * 60 * 60;
      }
      if ("hour".equals(type))
      {
         count = 60 * 60;
      }
      if ("minute".equals(type))
      {
         count = 60;
      }
      if ("second".equals(type))
      {
         count = 1;
      }
      if (date == null) date = new Date();
      long s3 = (date.getTime() / 1000) + count * (num);
      date.setTime(s3 * 1000);
      return DateToString(date, format);
   }

   /**
    * 格式化日期,任意格式,返回String型,即可获取固定日期进行格式化后的日期,也可获取系统当前日期格式化后的日期 日期格式yyyy-MM-dd
    * HH:mm:ss
    * 
    * @param date
    * @param format
    * @return
    * @throws FrameException
    * @throws ParseException
    */
   public static String DateToString(Date date, String format)
   {
      if (format == null || format == "") format = "yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      if (date == null)
         return sdf.format(new java.util.Date());
      else
         return sdf.format(date);
   }

   public static String formatLongTime(long time, String format)
   {
      if (format == null) format = "yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf = new SimpleDateFormat(format);

      java.util.Date date1 = new Date();
      date1.setTime(time);
      return sdf.format(date1);
   }

   public static Date StringToDate(String strDate, String format)
   {
      if (format == null || format == "") format = "yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      try
      {
         Date date = sdf.parse(strDate);
         return date;
      }
      catch (ParseException e)
      {
         return null;
      }
   }

   public static boolean isValidDate(String str, String format)
   {
      boolean convertSuccess = true;
      if (format == null || format == "") format = "yyyy-MM-dd HH:mm:ss";

      // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      try
      {
         // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
         sdf.setLenient(false);
         sdf.parse(str);
      }
      catch (ParseException e)
      {
         // e.printStackTrace();
         // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
         convertSuccess = false;
      }
      return convertSuccess;
   }

   //---------------------与星期有关的函数--------------------------
   /**
    * 获得日期所在星期的周一的日期
    * @param date
    * @return
    */
   public static String getMonday(String strdate)
   {
      try
      {
         Date date = stringToDate(strdate, "", false);
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
         int[] c = { 6, 0, 1, 2, 3, 4, 5 };
         String monday = afterDate("day", date, -c[intWeek], "yyyy-MM-dd");
         return monday;
      }
      catch (Exception e)
      {
         // TODO: handle exception
      }
      return "";
   }

   /**
    * 获得日期所在星期的周天的日期
    * @param date
    * @return
    */
   public static String getSunday(String strdate)
   {
      try
      {
         Date date = stringToDate(strdate, "", false);
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
         int[] c = { 0, 6, 5, 4, 3, 2, 1 };
         String monday = afterDate("day", date, c[intWeek], "yyyy-MM-dd");
         return monday;
      }
      catch (Exception e)
      {
         // TODO: handle exception
      }
      return "";
   }

   /**
    * 根据日期获得星期编号 0周日 6周六
    * @param date
    * @return
    */
  public static String getWeekNoOfDate(Date date)
  {
     //      String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
     String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
     String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
     Calendar calendar = Calendar.getInstance();
     calendar.setTime(date);
     int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
     return weekDaysCode[intWeek];
  }
  
  public static String getWeekNoOfDate(String date, String format)
  {
     if (format == null || format == "") format = "yyyy-MM-dd HH:mm:ss";
     DateFormat df = new SimpleDateFormat(format);
     Date d;
     try
     {

        if (date == null || "".equals(date))
           d = new Date();
        else
           d = df.parse(date);
     }
     catch (Exception e)
     {
        return "";
     }

     return getWeekNoOfDate(d);
  }
  
   /**
     * 根据日期获得星期
     * @param date
     * @return
     */
   public static String getWeekOfDate(Date date)
   {
      //      String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
      String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
      String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      return weekDaysName[intWeek];
   }

   public static String getWeekOfDate(String date, String format)
   {
      if (format == null || format == "") format = "yyyy-MM-dd HH:mm:ss";
      DateFormat df = new SimpleDateFormat(format);
      Date d;
      try
      {

         if (date == null || "".equals(date))
            d = new Date();
         else
            d = df.parse(date);
      }
      catch (Exception e)
      {
         return "";
      }

      return getWeekOfDate(d);
   }

   public static int WeekOfYear(String dateStr, String fmt)
   {
      try
      {
         SimpleDateFormat format = new SimpleDateFormat(fmt);
         Date date = format.parse(dateStr);
         Calendar calendar = Calendar.getInstance();
         calendar.setFirstDayOfWeek(Calendar.MONDAY);
         calendar.setTime(date);
         return calendar.get(Calendar.WEEK_OF_YEAR);
      }
      catch (Exception e)
      {
      }
      return 0;
   }

   public static String getCurSunday()
   {
      SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
      //      Calendar c =Calendar.getInstance(Locale.CHINA); //中国时区
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(System.currentTimeMillis());
      //      System.out.println("当前时间:"+fmt.format(c.getTime()));
      c.setFirstDayOfWeek(Calendar.SUNDAY);
      c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
      //      System.out.println("周日时间:"+fmt.format(c.getTime()));
      return fmt.format(c.getTime());
   }

   /**
    * 得到本周周一
    *
    * @return yyyy-MM-dd
    */
   public static String getMondayOfThisWeek()
   {
      SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
      if (day_of_week == 0) day_of_week = 7;
      c.add(Calendar.DATE, -day_of_week + 1);
      return df2.format(c.getTime());
   }

   /**
    * 得到本周周日
    *
    * @return yyyy-MM-dd
    */
   public static String getSundayOfThisWeek()
   {
      SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
      if (day_of_week == 0) day_of_week = 7;
      c.add(Calendar.DATE, -day_of_week + 7);
      return df2.format(c.getTime());
   }

   /**
    * 当前季度的开始时间，即2012-01-1
    * @return
    */
   public static String getCurrentQuarterStartDate()
   {

      SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      int currentMonth = c.get(Calendar.MONTH) + 1;
      Date now = null;
      try
      {
         if (currentMonth >= 1 && currentMonth <= 3)
            c.set(Calendar.MONTH, 0);
         else if (currentMonth >= 4 && currentMonth <= 6)
            c.set(Calendar.MONTH, 3);
         else if (currentMonth >= 7 && currentMonth <= 9)
            c.set(Calendar.MONTH, 6);
         else if (currentMonth >= 10 && currentMonth <= 12)
            c.set(Calendar.MONTH, 9);
         c.set(Calendar.DATE, 1);
         return shortSdf.format(c.getTime());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return "";
   }

   /**
    * 当前季度的结束时间，即2012-03-31
    * @return
    */
   public static String getCurrentQuarterEndDate()
   {
      SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      int currentMonth = c.get(Calendar.MONTH) + 1;
      try
      {
         if (currentMonth >= 1 && currentMonth <= 3)
         {
            c.set(Calendar.MONTH, 2);
            c.set(Calendar.DATE, 31);
         }
         else if (currentMonth >= 4 && currentMonth <= 6)
         {
            c.set(Calendar.MONTH, 5);
            c.set(Calendar.DATE, 30);
         }
         else if (currentMonth >= 7 && currentMonth <= 9)
         {
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DATE, 30);
         }
         else if (currentMonth >= 10 && currentMonth <= 12)
         {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
         }
         return shortSdf.format(c.getTime());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return "";
   }

   public static String changeDateStringFormat(String date,
      String srcFmt,
      String rstFmt)
   {
      if (srcFmt == null || srcFmt == "") srcFmt = "yyyy-MM-dd HH:mm:ss";
      DateFormat df = new SimpleDateFormat(srcFmt);
      Date d;
      String result = "";
      try
      {

         if (date == null || "".equals(date))
            d = new Date();
         else
            d = df.parse(date);
         result = DateToString(d, rstFmt);
      }
      catch (Exception e)
      {
         return "";
      }
      return result;
   }

   /**
    * @param date1      服务器日期
    * @param dateFmt    日期格式
    * @param timestamp1 服务器时间戳
    * @param timestamp2 本地时间戳
    * @param stampFmt   时间戳格式
    * @return
    */
   public static boolean isAcrossDay(String date1,
      String dateFmt,
      String timestamp1,
      String timestamp2,
      String stampFmt)
   {
      try
      {
         SimpleDateFormat sdf = new SimpleDateFormat(stampFmt);
         Date dtt1 = sdf.parse(timestamp1);
         Date dtt2 = sdf.parse(timestamp2);
         String date2 = formatLongTime(dtt1.getTime() + new Date().getTime()
            - dtt2.getTime(), dateFmt);
         if (!date1.equals(date2)) return true;
      }
      catch (Exception e)
      {
      }
      return false;
   }

   /**
    * 获取指定月份的下一个月
    * @param month 指定月份 yyyyMM
    * @return
    */
   public static String getNextMonth(String month)
   {
      String nMonth = "";
      month += "01";//指定那个月的一号
      nMonth = afterDate("day", StringToDate(month, "yyyyMMdd"), 35, "yyyyMMdd");
      nMonth = nMonth.substring(0, 6);
      return nMonth;
   }
}