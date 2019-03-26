

- [moment](#moment)
  - [基本用法](#%E5%9F%BA%E6%9C%AC%E7%94%A8%E6%B3%95)
  - [用法](#%E7%94%A8%E6%B3%95)
    - [解析](#%E8%A7%A3%E6%9E%90)
    - [格式化](#%E6%A0%BC%E5%BC%8F%E5%8C%96)
    - [calendar Time](#calendar-time)
    - [是否为有效的时间](#%E6%98%AF%E5%90%A6%E4%B8%BA%E6%9C%89%E6%95%88%E7%9A%84%E6%97%B6%E9%97%B4)
    - [比较时间](#%E6%AF%94%E8%BE%83%E6%97%B6%E9%97%B4)
    - [获取和设置时间](#%E8%8E%B7%E5%8F%96%E5%92%8C%E8%AE%BE%E7%BD%AE%E6%97%B6%E9%97%B4)
    - [操作时间](#%E6%93%8D%E4%BD%9C%E6%97%B6%E9%97%B4)
    - [utils](#utils)
    - [Year, month, and day 占位符格式](#year-month-and-day-%E5%8D%A0%E4%BD%8D%E7%AC%A6%E6%A0%BC%E5%BC%8F)
    - [时分秒毫秒的占位符](#%E6%97%B6%E5%88%86%E7%A7%92%E6%AF%AB%E7%A7%92%E7%9A%84%E5%8D%A0%E4%BD%8D%E7%AC%A6)
  - [自定义](#%E8%87%AA%E5%AE%9A%E4%B9%89)
  - [Duration](#duration)
  - [注意点](#%E6%B3%A8%E6%84%8F%E7%82%B9)

# moment

moment的实现不是修改了原生的Date，而是在原生的Date外面包裹了一层。moment的原型方法通过moment.fn暴露出来，如果需要自定义方法，可以在【moment.fn】上面添加新方法来实现。

==使用moment可以做哪些事情==

1. 格式化时间
2. 比较时间
3. 求相对时间
4. 可以操作时间，加上多少减去多少
5. 可以设置时间语种

## 基本用法

```javascript
const moment=require('moment')
//  调用moment方法生成一个moment对象 ，可以传参数来指定时间
//  格式化时间 2014-01-01 12:22:22
moment().format('YYYY-MM-DD HH:ss:mm')
//  设置语种
moment.locale('zh_CN',{
  //  可以设置全局的配置声明
  //  格式化别名 ?
  longDateFormat:{
    L:'HH:mm',  //  format('HH:mm')===format('L')
  }
})
//  比对时间
moment('2018-02-24','YYYY-MM-DD').fromNow();
moment('2018-02-24','YYYY-MM-DD').from(< Moment | Date | string | number | (number | string)[] | MomentInputObject | void>);
```

## 用法

### 解析

- moment
  - moment(date:string|number,formatMode?:string|string[],localeStr?:string,strictMode?:boolean)
    - formatMode
      - 支持传入字符串和字符串数组表示支持多种解析格式
    - localeStr
      - 2.0.0开始支持设置时间域
    - strictMode
      - 是否严格的遵守解析格式

### 格式化

- format(formatStr?:string)

### calendar Time

日历时间显示相对于给定参考时间的时间（默认为现在），但与现在之后的时间稍有不同。
将使用不同字符串设置日期格式，具体取决于日期与引用时间的日期（默认情况下为今天）的接近程度。
当距离参考时间超过一周时，使用【sameelse】作为格式。

- calendar(moment?:Moment|string,config:Object)
  - config
    - lastDay
    - sameDay
    - nextDay
    - lastWeek
    - nextWeek
    - sameElse

```javascript
const comment=require('moment')

//  output Tomorrow
moment()
    .add(1, 'days')
    .calendar(moment(), {
      sameDay: '[Today]',
      nextDay: '[Tomorrow]',
      nextWeek: 'dddd',
      lastDay: '[Yesterday]',
      lastWeek: '[Last] dddd',
      sameElse: 'YYYY-DD-MM'
    })
```

### 是否为有效的时间

- isValid()

### 比较时间

- diff(s:Moment|String|Number|Date|Array,field?:string,strict?:boolean):number
  - 比较时间
  - 返回相差毫秒数
    - 如果前者的时间较大，返回正直，否则返回负值
  - 参数
    - field
      - 比较字段
        - year
        - month
        - date
- isBefore(s:Moment|String|Number|Date|Array,field?:string):boolean
  - 参数
    - field
      - 表示按照某个字段来比较
- isAfter(s:Moment|String|Number|Date|Array,field?:string):boolean
- isSame(s:Moment|String|Number|Date|Array)
- isSameOrBefore(s:Moment|String|Number|Date|Array)
- isSameOrAfter(s:Moment|String|Number|Date|Array)
- isBetween(s:Moment|String|Number|Date|Array,s1:Moment|String|Number|Date|Array)

### 获取和设置时间

如果带参数，表示setter，不带参数表示getter

- year(amount?:number)
- month(amount?:number)
- date(amount?:number)
- hour(amount?:number)
- minute(amount?:number)
- second(amount?:number)
- millisecond(amount?:number)

获取在某个月的第几天

- dayInMonth()

返回javascriot原生的Date

- toDate():Date
- toArray():number[]
  - return
    - year
    - month
    - date
    - hout
    - minute
    - second
    - millisecond

设置开始和结束时间

- startOf(s?:string)
  - 表示到什么时间，其后时间清零等
  - s
    - year
    - month
    - date
    - hour
    - minute
    - second
    - millisecond
- endOf(s?:string)
  - 表示截止时间

```javascript
const moment=require('moment')
const date=moment()

//  current 2019-03-24 12:12:12
//  after startOf 2019-01-01 00:00:00
date.startOf('year')

//  after endOf 2019-12-31 23:59:59
date.endOf('year')
//  2019-03-24 23:59:59
date.endOf('date')
```

### 操作时间

- add(amount?: DurationInputArg1, unit?: DurationInputArg2): Moment;
  - 在基本时间的基础上加上多少，支持链式调用
  - DurationInputArg1
    - Duration | number | string | FromTo | DurationInputObject | void;
- subtract(amount?: DurationInputArg1, unit?: DurationInputArg2): Moment;
  - 在基本时间的基础上减去多少

| key          | short-key | desc |
| ------------ | --------- | ---- |
| years        | Y         | 年   |
| quarters     | Q         | 季度 |
| months       | M         | 月   |
| weeks        | w         | 周   |
| days         | d         | 日   |
| hours        | h         | 时   |
| minutes      | m         | 分   |
| seconds      | s         | 秒   |
| milliseconds | ms        | 毫秒 |

```javascript
const moment=require('moment')

//  在当前的时间往前推3个月6天
moment().add(6,'days').add(3,'M')
//  两年前
moment().subtract(2,'Y')
```

### utils

- isMoment(obj)
  - 判断是否是Moment对象
- isDate(obj)
  - 获取原生的Date

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

| Input    | Example        | Description                                 |
| -------- | -------------- | ------------------------------------------- |
| YYYY     | 2014           | 四位数年份                                  |
| YY       | 14             | 两位数年份                                  |
| Y        | -25            | Year with any number of digits and sign     |
| Q        | 1..4           | 季度                                        |
| M MM     | 1..12          | 月份                                        |
| MMM MMMM | Jan..December  | Month name in locale set by moment.locale() |
| D DD     | 1..31          | 月份中的天数                                |
| Do       | 1st..31st      |                                             |
| DDD DDDD | 1..365         | 年份中的天数                                |
| X        | 1410715640.579 | Unix timestamp                              |
| x        | 1410715640579  | Unix ms timestamp                           |

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

## 自定义

[http://momentjs.com/docs/#/customization/](http://momentjs.com/docs/#/customization/)

```javascript
const moment=require('moment')
```

## Duration

moment定义的是单个时间点，duration定义的是一个时间段.

- duration(Number,String)
  - duration(2,'days')
- duration(Number)
  - duration(2,'second')
  - 如果没指定field，默认是second
- duration(String)
- duration(Object)
  - Object
    - years
    - months
    - days
    - hours
    - minutes
    - seconds

```javascript
const moment=require('moment')

//  创建Duration对象
//  只会计算最大单位的，其余的会被忽略
const duration=moment.duration({
  years:5,
  months:3
})

//  output 5年
console.log(duration.humanize())
```

## 注意点

1. 使用webpack打包，默认会打包所有语种，可以使用【moment-locales-webpack-plugin】来去掉多余的locale

```javascript
//  yarn add --dev moment-locales-webpack-plugin
const MomentLocalesPlugin=require('moment-locales-webpack-plugin')

module.exports={
  //  ...
  plugins:[
    //  默认会打包en
    new MomentLocalesPlugin()
    new MomentLocalesPlugin({
      localesToKeep:['zh-CN']
    })
  ]
}
```