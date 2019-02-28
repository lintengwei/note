# mysql 数据类型

## 数字

### bit

- bit(M)
  - M 表示位长度
  - insert----insert into table(bitColumn) values(b'value'/B'value'/0b[binary])
  - select----select bin(key)[二进制]/key+0[十进制]/hex(key)[十六进制] from table

### 精确数字

- dec(M,D)
  - 适用于需要精确值的存储。商品价格，薪水等。
  - M 精确值
  - D 小数位数
  - 范围 -999.99~999.99
  - 整数位超过 M 位会报错，小数位超过 D 位会四舍五入

## 字符串

### enum 枚举值

- enum(var1,var2,...)
  - 开启严格模式会导致插入只不在可选项中的时候报错。否则如果值不在可选范围，插入 0
  - 如果 ENUM 声明一列允许 NULL，则该 NULL 值是该列的有效值，默认值为 NULL。如果 ENUM 声明了列 NOT NULL，则其默认值是允许值列表的第一个元素。
  - 适用于确定的几个值之间选择
  - 可以使用索引或者定义的值来操作，从 1 开始定位
    - insert into tablename (colname) values(var1/1)
  - order by
    - 默认按照索引进行排序
    - 如果要定义排序规则
      - 定义字段时候就按照 assci 顺序定义

### set 集合

- set(var1,var2,...)
  - 如果启用了严格的 SQL 模式，则尝试插入无效 SET 值会导致错误。
  - 可以插入空字符串
  - find_in_set(var,col_name) || col_name like '%%'
  - create table test(tags set('html','css','js'))
  - insert into test values('html,css')
  - 如何搜索包含多个值的 set 列

## JSON 类型（5.7.8+开始支持）

- JSON_TYPE(str)
  - 判断 json 类型
- JSON_ARRAY(str1,str2,str3)
  - 生成 json_array,[str1,str2,str3]
- JSON_OBJECT(key1,value2,ke2,value2[,key3,value3])
  - 生成 json_object,{key1:value1,key2:value2}
- JSON_MERGE(...args)
  - 根据参数生成新的 json_array
- JSON_EXTRACT(jsonObj,key);
  - JSON_EXTRACT('{"id": 14, "name": "Aztalan"}', '\$.name')
- JSON_KEY(jsonObj,path,key)
  - [3, {"a": [5, 6], "b": 10}, [99, 100]]
  - path='\$[0]',key='a',value=[5,6]

## 时间

> DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
> 默认为当前时间，更新刷新时间

- date
  - yyyy-MM-dd
- datetime
  - yyyy-MM-dd hh:mm:ss
- timestamp
  - yyyyMMddhhmmssS

### 时间函数

- curdate()
  - yyyy-MM-dd
- curtime()
  - hh:mm:ss
- current_timestamp
  - yyyy-MM-dd hh:mm:ss
- now()
  - yyyy-MM-dd hh:mm:ss
