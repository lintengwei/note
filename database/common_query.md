# 查询语句

## 选取某列最大值

```sql
-- DDL
create table test(num int unsignd not null );
insert into test values(1),(2),(4)
-- 子查询
select * from test where num=(select max(num) from test)

-- left join
-- 利用左连接即使找不到符合条件的数据右边表【null】占位的原理,【效率很低】
select * from test t1 left join test t1 on t1.num<t2.num where t2.num is null;

-- limit
-- 先做排序，之后选取第一条，默认排序是降序
select * from test order by num desc limit(1)
```

## 选取分组中的最大值

```sql
create table test(companyid int unsigned not null ,age tinyint not null);
insert into test (companyid,age) values (1,22),(1,33),(1,15),(2,55),(2,26),(3,19),(3,35);

-- group by
select companyid,max(age) from test group by companyid;

-- 相关子查询
-- 外查询选出元数据，内查询过滤掉不符合条件的元数据
select * from test t1 where age=(select max(age) from test t2 where t2.companyid=t1.companyid);

-- left join 【效率很低】
select t1.companyid,t1.age from test t1 left join test t2 on t1.companyid=t2.companyid and t1.age<t2.age where t2.companyid is null;
```

## 选取分组符合条件的行

d

```sql
-- 选取分组中前2的值 使用上面的表

-- 子查询
-- 选取每个公司年龄最大的两位
select * from test t1 where 2>(select count(*) from test t2 where t1.companyid=t2.companyid and t2.age>t1.age);
```
