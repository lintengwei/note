# oracle

## 常用数据操作语句

```sql
-- 定义自增字段
-- Create sequence
-- 调用 APP_SECKILL_ID.nextval,会返回下一个自增值
--  每次调用都会消耗掉一个id值，多条数据插入values(nextval),(nextval)不合法，插入失败
create sequence APP_SECKILL_ID
minvalue 1
maxvalue 9999999
start with 1000114
increment by 1
nocache;

```

## 函数

[引用地址](https://www.cnblogs.com/bbliutao/archive/2017/11/08/7804263.html)

### 字符串函数

```sql
-- 字符串
-- 连接字符串
concat(str1,str2) == str1||str2
-- 填充 用str2填充str1，指导str1长度满足len
lpad(str1,len,str2) -- rpad
```

### 其他函数

```sql
-- 如果str1为空，返回str2，否则返回str1.str1和str2同类型
nvl(str1,str2)
-- 如果str1为空返回str3，否则返回str2
nvl2(str1,str2,str3)
```

### 判断 null

```sql
-- 如果var1 isnull 返回 var2，否则返回var1
nvl(var1,var2)

-- 如果var1 isnull返回var3，否则返回var2
nvl2(var1,var2,var3)
```

### 聚合函数

==聚合函数不能嵌套使用==

```sql
--  可以配合group by分组语法使用
--  如果不配合group by使用，只会返回一行！
--  返回记录的行数，行值为null的不会统计在内
count(colnumName)
--  返回指定行的总值
sum(colnumName)
--  返回指定行的平均值
avg(colnumName)
min(colnumName)
max(colnumName)
```

## 条件判断

### case when then else end

```sql
--简单Case函数

CASE sex
WHEN '1' THEN '男'
WHEN '2' THEN '女'
ELSE '其他' END

--Case搜索函数

CASE
WHEN sex = '1' THEN '男'
WHEN sex = '2' THEN '女'
ELSE '其他' END
```

### if else

```sql
-- 配合聚合函数
-- 如果返回不是空则集合函数加1否则不做处理
select sum(case sex where '1' then 1 else null end) 男

-- 如果value=if1 则值为then1 ...
DECODE(VALUE, IF1, THEN1, IF2, THEN2, IF3, THEN3, ......, OTHER);

--
if a=... then
...
end if;

if a=... then
...
elsif a=... then    --这里是elsif，不是else if.
...
end if;
```

```sql
create table test(month int ,day int ,cnt int);
insert into test(month,day,cnt) values(1,1,1),(1,1,4),(2,5,2),(2,3,4),(2,3,1);
select ifnull(month,'总数'),ifnull(day,''),sum(month) from test group by month,day with rollup;
```
