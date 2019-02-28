# java.util.Calendar 语法

> 月份和日期都是从0开始，并且可以为负数，表示回退一轮的事件
> -1月表示12，-1表示上个月末

## 获取实例 =>一个单例模式

```java
//  获取实例
Calendar calendar=Calendar.getInstance();
```

### 设置时间 calendar.set();

```java
    calendar.set(int year,int month); //年月
    calendar.set(int year,int month,int date);  //年月日
    calendar.set(int year,int month,int date,int hour,int min); //年月日时分
    calendar.set(int year,int month,int date,int hour,int min,int sec); //年月日时分秒
```

### 获取时间 calendar.get()

```java
    calendar.get(int type); //type识别符号

    useage  calendar.get(Calendar.YEAR);
    public static final int ERA = 0;--------------------------------------------
    public static final int YEAR = 1;   //  年
    public static final int MONTH = 2;  //  月
    public static final int WEEK_OF_YEAR = 3; //  一年的第几周
    public static final int WEEK_OF_MONTH = 4; // 一个月的第几周---正常
    public static final int DAY_OF_WEEK_IN_MONTH = 8; //  一个月的第几周---从本月1号开始算第一周，以此类推
    public static final int DATE = 5;   // 日期
    public static final int DAY_OF_MONTH = 5; //  每个月的第几天----正常的天数
    public static final int DAY_OF_YEAR = 6;  //  一年当中的第几天
    public static final int DAY_OF_WEEK = 7;  //  一周当中的第几天--------周日表示第一天 周日=1
    public static final int AM_PM = 9;  ------------------------------------------------------
    public static final int HOUR = 10;    //  12小时制
    public static final int HOUR_OF_DAY = 11; //  24小时制
    public static final int MINUTE = 12;  //  分钟
    public static final int SECOND = 13;  //  秒
    public static final int MILLISECOND = 14; //  毫秒
    public static final int ZONE_OFFSET = 15;----------------------------------------------
    public static final int DST_OFFSET = 16;----------------------------------------------
    public static final int FIELD_COUNT = 17;---------------------------------------
    public static final int SUNDAY = 1; //  周日
    public static final int MONDAY = 2; //  周一
    public static final int TUESDAY = 3; //  周二
    public static final int WEDNESDAY = 4; //  周三
    public static final int THURSDAY = 5; //  周四
    public static final int FRIDAY = 6; //  周五
    public static final int SATURDAY = 7; //  周六
    public static final int JANUARY = 0; //  一月
    public static final int FEBRUARY = 1; //  二月
    public static final int MARCH = 2; //  三月
    public static final int APRIL = 3; //  四月
    public static final int MAY = 4; //  五月
    public static final int JUNE = 5; //  六月
    public static final int JULY = 6; //  七月
    public static final int AUGUST = 7; //  八月
    public static final int SEPTEMBER = 8; //  九月
    public static final int OCTOBER = 9; //  十月
    public static final int NOVEMBER = 10; //  十一月
    public static final int DECEMBER = 11; //  十二月
    public static final int UNDECIMBER = 12; //  周日
    public static final int AM = 0; //  上午
    public static final int PM = 1; //  下午
```

## methods

```java
//  向前向后推进时间
public void add(int field,int amount)

//  such as
Calendar calendar=Calendar.getInstance(); //  当前年为2018
calendar.add(Calendar.YEAR,-3); //  当前年为2015，以此类推

//  清空时间到 1970--
public void clear()
public void clear(int field)  //  作用到具体字段

//  获取字段可取的最大最下值
public int getActualMaximum(int field)
public int getActualMinumum(int field)

//  设置哪天为每周的第一天 美国为周日i 我国为周一
public void	setFirstDayOfWeek(int value)

//  同add
public abstract void roll(int field,boolean flag) //true field + 1，false field -1;
public abstract void roll(int field,amount s) //  同add，超过上限下线不会改变其他字段的值
```