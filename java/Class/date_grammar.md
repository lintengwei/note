# java.util.Date 记录

## constructor

```java

//  无参数构造函数 获取当前系统时间
Date()  

//  long参数  解析成从 1970-01-01 00 00 00返回的具体时间
Date(long date)

//  以下构造函数废弃
Date(String s)
Date(int year,int month,int date)   // 使用 Calendar.set(year+1900,month,date)代替
Date(int year,int month,int date,int hour,int minute);  //同上
Date(int year,int month,int date,int hour,int minute,int second); //同上
```

## methods

```java
Date date=new Date(); //以当前运行的jvm时间；
date.getTime(); //获取从1970-1-1 00:00:00  开始的毫秒数
System.currentTimeMillis(); //  同上


public String toString()
Converts this Date object to a String of the form:
 dow mon dd hh:mm:ss zzz yyyy
where:
dow is the day of the week (Sun, Mon, Tue, Wed, Thu, Fri, Sat).
mon is the month (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec).
dd is the day of the month (01 through 31), as two decimal digits.
hh is the hour of the day (00 through 23), as two decimal digits.
mm is the minute within the hour (00 through 59), as two decimal digits.
ss is the second within the minute (00 through 61, as two decimal digits.
zzz is the time zone (and may reflect daylight saving time). Standard time zone abbreviations include those recognized by the method parse. If time zone information is not available, then zzz is empty - that is, it consists of no characters at all.
yyyy is the year, as four decimal digits.

```