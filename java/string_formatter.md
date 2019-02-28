# 字符串格式化

## syntax

%[argument_index\$][flags][width][.precision]conversion

以%开头，可选的参数有【参数位置】【标志】【宽度】【精度】【转换类型】

参数说明：

- argument_index$
  - 可选。 十进制整数，后置参数的格式化位置。 1& => 标识第一位 , 2& => 标识第二位
- flags
  - 可选。格式化的占位符（当后置参数不满 width 时候补充的字符），如果指定了这个值，则必须指定 width 参数。
- width
  - 可选。填充宽度。当参数位数不满 width 设置的值时候，会填充 flags 的值，默认为空格。
- precision
  - 可选。限制精度
- conversion
  - 必须。如何格式化的一个设置。formatter 根据这个值来格式化

## 参数位置

## 标志

需要和 width 配合使用，可用标志

| flag | general | char | int | float | date/time | des    |
| ---- | ------- | ---- | --- | ----- | --------- | ------ |
| '-'  | y       | y    | y   | y     | y         | 左对齐 |
| '#'  | y1      | -    | y3  | y     | -         | dsa    |
| '+'  | -       | -    | y4  | y     | -         | dsa    |
| ''   | -       | -    | y4  | y     | -         | dsa    |
| '0'  | -       | -    | y   | y     | -         | dsa    |
| ','  | -       | -    | y2  | y5    | -         | dsa    |
| '('  | -       | -    | y4  | y5    | -         | dsa    |

说明：
1. 


## 宽度

需要和 width 配合使用。__在浮点数中小数点也算一位__

## 精度

适用于浮点型，小数点后面的精度，默认为6位

## 转换类型

普通后缀：

| conversion | 类型           | 描述                     |
| ---------- | -------------- | ------------------------ |
| 'b' 'B'    | general        | valueOf获取              |
| 'h' 'H'    | general        | 调用toHexString获取      |
| 's' 'S'    | general        | 调用对象toString获取     |
| 'c' 'C'    | character      | unicode字符串            |
| 'd'        | int            | 十进制整数               |
| 'o'        | int            | 八进制整数               |
| 'x' 'X'    | int            | 十六进制整数             |
| 'e' 'E'    | float          | 科学计数法显示浮点数     |
| 'f'        | float          | 十进制显示浮点数         |
| 'g' 'G'    | float          | 和计算机相关，会计算精度 |
| 'a' 'A'    | float          | -                        |
| 't' 'T'    | date/time      | 时间和日期               |
| '%'        | percent        | 字面意思 转义            |
| 'n'        | line-separator | 换行符                   |

日期时间相关：

Locale类可以选择日期的格式化地区。

日期相关的格式化中，conversion为t，并且后缀会有特殊定义的字符，如下：

一定要选择参数位置吗？
String.format("%1$ty-%1$td-%1$tm",new Date())===2018-12-27

时间相关：

| suffix | des                                  |
| ------ | ------------------------------------ |
| H      | hour 00-23 不满两位补足              |
| K      | hour 0-23 不补位                     |
| I      | hour 01-12   补位                    |
| l      | hour 1-12  不补位                    |
| M      | minute 00-59                         |
| S      | minute 00-60                         |
| L      | millisecond 毫秒 000-999             |
| N      | nanosecond 纳秒  000000000-999999999 |
| p      | pm am 上下午                         |
| z      | -                                    |
| Z      | -                                    |
| s      | -                                    |
| Q      | -                                    |

日期相关：

| suffix | des                        |
| ------ | -------------------------- |
| B      | "January", "February".     |
| b      | "Jan", "Feb"               |
| h      | Same as 'b'.               |
| A      | "Sunday", "Monday"         |
| a      | "Sun", "Mon"               |
| C      | -                          |
| Y      | 四位年份                   |
| y      | 两位年份                   |
| j      | 一年中的第几天             |
| m      | 一年中的第几月    01 - 13? |
| d      | 一月中的第几天 补位        |
| e      | 一月中的第几天 不补位      |

系统设定的时间和日期：

| suffix | des |
| ------ | --- |
| R      | -   |
| T      | -   |
| R      | -   |
| D      | -   |
| F      | -   |
| c      | -   |
