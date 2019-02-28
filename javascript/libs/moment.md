# moment

## 用法

+ 生成moment对象
  + 可以接收一个字符串参数作为需要解析的日期
    + moment()
      + 解析当前的时间
    + moment(String)
      + 解析指定的日期时间
  + 可接收一个对象作为解析参数
    + moment({unit: value, ...})
  + 接收一个数组作为参数
    + moment(Array)
      + [year, month, day, hour, minute, second, millisecond]

> moment(String) 包含日期的 String支持的ISO 8601 strings

| 格式       | 注释                            |
| ---------- | ------------------------------- |
| 2013-02-08 | 年份-月份-日期                  |
| 2013-W06-5 | 年份-周数-周几（周一为1）       |
| 2013-039   | 年份-总天数 （必须三位数365天） |
| 20130208   | 同上简写                        |
| 2013W065   | 同上简写                        |
| 2013W06    | 同上简写                        |
| 2013050    | 同上简写                        |

> 包含时间的支持格式（空格或者T作为时间的分割线）

| 格式                    | 注释              |
| ----------------------- | ----------------- |
| 2013-02-08T09           | 年份-月份-日期T时 |
| 2013-02-08 09           |                   |
| 2013-02-08 09:30        |                   |
| 2013-02-08 09:30:26     |                   |
| 2013-02-08 09:30:26.123 | 毫秒              |
| 20130208T080910,123     |                   |
| 20130208T080910.123     |                   |
| 20130208T080910         |                   |
| 20130208T0809           |                   |
| 20130208T08             |                   |

### Year, month, and day 占位符格式

| Input    | Example        | Description                                            |
| -------- | -------------- | ------------------------------------------------------ |
| YYYY     | 2014           | 4 or 2 digit year                                      |
| YY       | 14             | 2 digit year                                           |
| Y        | -25            | Year with any number of digits and sign                |
| Q        | 1..4           | Quarter of year. Sets month to first month in quarter. |
| M MM     | 1..12          | Month number                                           |
| MMM MMMM | Jan..December  | Month name in locale set by moment.locale()            |
| D DD     | 1..31          | Day of month                                           |
| Do       | 1st..31st      | Day of month with ordinal                              |
| DDD DDDD | 1..365         | Day of year                                            |
| X        | 1410715640.579 | Unix timestamp                                         |
| x        | 1410715640579  | Unix ms timestamp                                      |

### 时分秒毫秒的占位符

| Input    | Example | Description                                                                  |
| -------- | ------- | ---------------------------------------------------------------------------- |
| H HH     | 0..23   | 24小时制                                                                     |
| h hh     | 1..12   | 12小时制 (12 hour time used with a A.)                                       |
| k kk     | 1..24   | 24小时制  (24 hour time from 1 to 24)                                        |
| a A      | am pm   | Post or ante meridiem (Note the one character a p are also considered valid) |
| m mm     | 0..59   | Minutes                                                                      |
| s ss     | 0..59   | Seconds                                                                      |
| S SS SSS | 0..999  | Fractional seconds                                                           |
| Z ZZ     | +12:00  | Offset from UTC as +-HH:mm, +-HHmm, or Z                                     |