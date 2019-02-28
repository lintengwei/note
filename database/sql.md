# sql 语法

## 执行顺序的问题

```sql
--  mysql的执行顺序
(8)select (9)distinct <select_list>
(1)from <left_table>
(3)<join_type> join <right_table>
(2)on <join_condition>
(4)where <where_condition>
(5)group by <group_by_list>
(6)with [cube|roolup]
(7)having <havind_condition>
(10)order by <order_by_list>
(11)limit <limit_number>
```

> 下面我们来具体分析一下查询处理的每一个阶段

1.  FORM: 首先对 from 子句中的前两个表执行一个笛卡尔乘积，此时生成虚拟表 vt1（选择相对小的表做基础表）
2.  ON: 对虚表 VT1 进行 ON 筛选，只有那些符合<join-condition>的行才会被记录在虚表 VT2 中。
3.  JOIN： 如果指定了 OUTER JOIN（比如 left join、 right join），那么保留表中未匹配的行就会作为外部行添加到虚拟表 VT2 中，产生虚拟表 VT3, 如果 from 子句中包含两个以上的表的话，那么就会对上一个 join 连接产生的结果 VT3 和下一个表重复执行步骤 1~3 这三个步骤，一直到处理完所有的表为止。
4.  WHERE： 对虚拟表 VT3 进行 WHERE 条件过滤。只有符合<where-condition>的记录才会被插入到虚拟表 VT4 中。
5.  GROUP BY: 根据 group by 子句中的列，对 VT4 中的记录进行分组操作，产生 VT5.如果应用了 group by，那么后面的所有步骤都只能得到的 vt5 的列或者是聚合函数（count、sum、avg 等）。原因在于最终的结果集中只为每个组包含一行
6.  CUBE | ROLLUP: 对表 VT5 进行 cube 或者 rollup 操作，产生表 VT6.
7.  HAVING： 对虚拟表 VT6 应用 having 过滤，只有符合<having-condition>的记录才会被 插入到虚拟表 VT7 中。对分组数据捷星逻辑操作，HAVING 语法与 WHERE 语法类似，但 HAVING 可以包含聚合函数。
8.  SELECT： 执行 select 操作，选择指定的列，插入到虚拟表 VT8 中。
9.  DISTINCT： 对 VT8 中的记录进行去重。产生虚拟表 VT9.
10. ORDER BY: 将虚拟表 VT9 中的记录按照<order_by_list>进行排序操作，产生虚拟表 VT10.
11. LIMIT：取出指定行的记录，产生虚拟表 VT11, 并将结果返回。

## 驱动表的选择

## 视图

一个虚拟表，没有正式存在，不能做操作，除非是一对一的表。当查询语句过于复杂，又在多出有引用，则可以考虑使用视图来简化操作。

```java
//  创建视图
create view viewName as sql statement

//  使用视图
select * from viewName;
```

## 索引


## 锁

[为什么开发人员必须要了解数据库锁？](https://juejin.im/post/5b6c5be86fb9a04fb30a2bc7)

### 事务

#### 基本要素

ACID

- A (原子性- Atomicity)
  - 一次事务要么全部成功，要么全部失败
- C （一致性- Consistency）
  - 事务的开始和结束，数据库的结构没有被破坏
- I （隔离性- Isolation）
  - 同一时间，只允许一个失误操作一条数据，对于数据 D，当 A 事务对其操作的时候，B 事务不能对其操作。
- D （持久性- Durability）
  - 事务操作完成之后，数据保存到数据库，没有回滚操作。

### 事务并发产生的问题

可能是事务不会锁表的问题导致的，可能产生几个问题。脏读，幻读，不可重复读等问题。

- 脏读
  - 事务 A 更新了数据，然后事务 B 读取更新之后的数据，之后事务 A 回滚，导致事务 B 读取的是假的数值，不是当前数据库存在的数值，称为脏读
- 幻读
  - 在操作数据库的时候，事务 B 插入一条和事务 A 不一致的数据，当事务 A 再次读取数据的时候，出现数据不一致的问题。主要针对对数据的插入和产出操作。
- 不可重复读
  - 事务 A 多次读取数据的过程中，事务 B 对数据做出了修改，导致事务 A 多次读取数据不一致的情况出现。主要针对对数据的修改

### 事务的隔离级别

mysql 的默认隔离级别为可重复读

```java
//  查看当前数据库的隔离级别
select @@tx_isolation
//  修改当前的隔级别
set session transaction isolation level read uncommitted

//  开始事务
start transaction
//  提交事务
commit;
//  回滚事务
rollback
```

| 事务的隔离级别               | 脏读 | 幻读 | 不可重复读 |
| ---------------------------- | ---- | ---- | ---------- |
| 读未提交（read uncommitted） | 是   | 是   | 是         |
| 读已提交（read commited）    | 否   | 是   | 是         |
| 可重复读（repeatable read）  | 否   | 否   | 是         |
| 串行化（serializable）       | 否   | 否   | 否         |

#### 读未提交(RUC)

每个事务某条 sql 语句提交的数据都会在数据库中得到体现，因此当某个事务操作数据之后其他事务读取了操作过的数据，之后该事务由于某种原因发生回滚的时候，导致其他事务读取的数据的脏值。

- 事务 A 修改了数据没有提交，事务 B 查询到了数据并且在程序中缓存了数据，然后事务 A 回滚会最初值。事务 B 还在用事务 A 更新之后的值。（脏读）
- 事务 A 更新了数据没有提交，事务 B 读取了数据，之后事务 A 再次更新数据，事务 B 再次读取数据，两次读取的数据不一致。（不可重复读）
- 事务 A 读取数据没结果没有提交，事务 B 插入一条新数据，事务 A 再次读取数据，发现新数据。（幻读）

#### 读已提交(RC)

每个事务色 sql 语句操作的变化都是实时的发生在数据库里面的，丙炔最后的提交了事务。这里不会发生脏读，因为事务 A 是提交了所在事务的所有。导致数据没有失真。

- 事务 A 更新了数据没有提交，事务 B 读取了数据，做出修改，事务 A 提交事务，事务 B 提交事务。数据真实存在，不发生脏读。
- 事务 A 读取了数据没有提交，发现没有记录，事务 B 插入一条新数据提交，事务 A 再次读物数据，发现新纪录。（幻读）
- 同上

#### 可重复读(RR)

开始事务之后，是不是所在事务会保存一张开始事务时候的快照？每次读取数据都是从这个快照里面的数据中读取，事务之间的操作不会相互干扰。但是当两个事务先后都提交了的话，就会发生冲突，需要怎么去结局？如果是用数据库里面的值做迭代修改，事务之间的修改会相互影响吗？

#### 串行化

是不是锁了整张表？

## 其他

1.  mysql 中默认事务隔离级别是可重复读时并不会锁住读取到的行

2.  事务隔离级别为读提交时，写数据只会锁住相应的行

3.  事务隔离级别为可重复读时，如果有索引（包括主键索引）的时候，以索引列为条件更新数据，会存在间隙锁间隙锁、行锁、下一键锁的问题，从而锁住一些行；如果没有索引，更新数据时会锁住整张表。

4.  事务隔离级别为串行化时，读写数据都会锁住整张表

5.  隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大，鱼和熊掌不可兼得啊。对于多数应用程序，可以优先考虑把数据库系统的隔离级别设为 Read Committed，它能够避免脏读取，而且具有较好的并发性能。尽管它会导致不可重复读、幻读这些并发问题，在可能出现这类问题的个别场合，可以由应用程序采用悲观锁或乐观锁来控制。


## 排序的问题

> order by

```sql
select
  column_1
from
  table_name
order by column_1 [,column_2...] [asc|desc][nulls first|nulls last]
```

- 默认升序
  - asc 升序
  - desc 降序

* 可对函数使用排序
  - upper(column_1)

## 连接的问题

### 连个表的连接

> inner join（简写 table1,table2===talbe1 inner table2）

两个连接表都符合的才会选出

> left join

左表全部选出，右边表符合条件的选出

> right join

右表全部选出，左表符合条件的选出

### 结果集的连接

> union

连接两个表的数据，oracle 中 select 的数据列必须是一样的并且列的数据类型也必须是一样的！默认会去除重复的行，使用 union all 可以返回所有的行。（并集）

### 自连接

同一个表格的数据对比操作。自连接对比较表中的行或查询分层数据非常有用。

```sql
-- 查询同一天入职的同事
create table employees(id int unsigned auto_increment primary key,enter_date date,name varchar(16) not null)

select e1.name first,e2.name ,e1.enter_date from employees e1 left join employees e2 on e1.id<>e2.id and e1.enter_date=e2.enter_date;
```

## 分组的问题

> group by

分完组之后，选出的字段只能是 group by 后面的列，或则使用聚合函数生成的

```sql
-- 查询每个用户的订单数量和花费金额
select
-- 此处筛选的列名只能是uid 或使用聚合函数
  uid,count(*) cnt,sum(price) total_price
from
  order
group by
  uid
```

rollup,cube 是 union all 的语法糖。分段统计数据使用

> rollup
> mysql ... group by A,B with rollup;
> oracle ... group by rollup(A,B);

配合 group by 使用。如果使用 group by rollup(A,B,C)，首先会对(A、B、C)进行 GROUP BY，然后对(A、B)进行 GROUP BY，然后是(A)进行 GROUP BY，最后对全表进行 GROUP BY 操作。roll up 的意思是“卷起”，这也可以帮助我们理解 group by rollup 就是对选择的列从右到左以一次少一列的方式进行 grouping 直到所有列都去掉后的 grouping(也就是全表 grouping)，对于 n 个参数的 rollup，有 n+1 次的 grouping。以下 2 个 sql 的结果集是一样的：

```sql
Select A,B,C,sum(E) from test group by rollup(A,B,C)

-- 和下面结果一致 分组次数为3次

Select A,B,C,sum(E) from test group by A,B,C

union all

Select A,B,null,sum(E) from test group by A,B

union all

Select A,null,null,sum(E) from test group by A

union all

Select null,null,null,sum(E) from test
```

> cube

cube 的意思是立方，对 cube 的每个参数，都可以理解为取值为参与 grouping 和不参与 grouping 两个值的一个维度，然后所有维度取值组合的集合就是 grouping 的集合，对于 n 个参数的 cube，有 2^n 次的 grouping。如果使用 group by cube(A,B,C),，则首先会对(A、B、C)进行 GROUP BY，然后依次是(A、B)，(A、C)，(A)，(B、C)，(B)，(C)，最后对全表进行 GROUP BY 操作，一共是 2^3=8 次 grouping。同 rollup 一样，也可以用基本的 group by 加上结果集的 union all 写出一个与 group by cube 结果集相同的 sql：

```sql
Select A,B,C,sum(E) from test group by cube(A,B,C);

-- 同下面结果一致

Select A,B,C,sum(E) from test group by A,B,C

union all

Select A,B,null,sum(E) from test group by A,B

union all

Select A,null,C,sum(E) from test group by A,C

union all

Select A,null,null,sum(E) from test group by A

union all

Select null,B,C,sum(E) from test group by B,C

union all

Select null,B,null,sum(E) from test group by B

union all

Select null,null,C,sum(E) from test group by C

union all

Select null,null,null,sum(E) from test;
```

> grouping sets(params1,param2)

grouping sets 就是对参数中的每个参数做 grouping，也就是有几个参数做几次 grouping,例如使用 group by grouping sets(A,B,C)，则对(A),(B),(C)进行 group by，如果使用 group by grouping sets((A,B),C),则对(A,B),(C)进行 group by。甚至 grouping by grouping set(A,A)都是语法允许的，也就是对(A)进行 2 次 group by,grouping sets 的参数允许重复


## 横竖表转变

```sql
-- 竖表边横表
create table scores(
  user varchar(15) not null,
  subjects varchar(10) not null,
  score int(3) not null
);

insert into scores values('ltw','math',98),('ltw','english',80),('ltw','yw',78),
('cs','math',77),('cs','english',88),('cs','yw',73)

-- case语句选出需要的列 group by 分组，max选出可用的值
select
  r.user,
  max(case r.subjects when 'math' then r.score else 0 end) 'math',
  max(case r.subjects when 'english' then r.score else 0 end) 'english',
  max(case r.subjects when 'yw' then r.score else 0 end) 'yw'
  from scores r group by r.user;

-- 横表转竖表
create table scores(
  user varchar(16) not null,
  math int null,
  english int null,
  yw int null
);

insert into scores values('ltw',99,98,97),('cc',88,87,85);

--  选取需要的列union即可
select m.user ,'math' as 'math',m.math score from scores m
union select e.user ,'english' as 'english',e.english score from scores e
union select y.user ,'yw' as 'yw',y.yw score from scores y
order by user;
```

## 子查询

1. 外层查询条件只能嵌套第一层