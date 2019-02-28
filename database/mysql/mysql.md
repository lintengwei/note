# msyql 基于 5.7 版本

## msyql 可选的安装程序

> mysql .客户端
> msyqld .服务端
> mysqld_safe
> mysql.server
> mysqld_multi

## 账号密码

[文档](https://dev.mysql.com/doc/refman/8.0/en/account-management-sql.html).修改账号密码，默认密码是否为空？对于新安装的 mysql，需要删除系统默认的空账户，保证数据库的安全，删除之后需要重启 mysql 的服务，才能完成。添加新账号也需要重启服务才能生效。而且生辰密码需要用到 password('password');用常规的 mysql 语法去更新用户表，需要重启服务才能生效，而用下面的语句则不需要--。应该是内部缓存的问题。

```sql
--  本地登录不需要host参数
mysql [-h hostname] [-u user] [-p password]
-- 创建用户 localhost 表示本机 %表示任何域名
create user 'test'@'localhost' identified by '123456';
-- 给用户创建数据库
create database test;
-- 给用户授权制定数据库 全部数据库 *.*
grant all privileges on database.* to 'username'@'localhost' identified by 'password';
-- 部分授权
grant select,update privileges on database.* to 'username'@localhost identified;
-- 收回授权
revoke select ,update on database.* from 'username'@'localhost' identified by 'password';

-- 删除用户
drop user [if exist] 'username'@'localhost'

-- 刷新权限表
flush privileges;
-- 删除用户的数据库
drop database testDB;
-- 删除权限
drop user 用户名@'%';
drop user 用户名@ localhost;
--  更新用户密码
set password for 'root'@'localhost'=password('password')

--  删除账号
show databases;
use mysql;
select * from user ; --  查看系统中的账户
delete from user where password=''; -- 删除系统账户中密码为空的用户
```

如果是忘记了 root 的密码

1.  关闭正在运行的 MySQL 服务。
2.  打开 DOS 窗口，转到 mysql\bin 目录。
3.  输入 mysqld --skip-grant-tables 回车。--skip-grant-tables 的意思是启动 MySQL 服务的时候跳过权限表认证。
    1.  mysqld --defaults-file="C:\ProgramData\MySQL\MySQL Server 5.7\my.ini" --skip-grant-tables
    2.  配置文件可能和安装目录不在同一个文件夹内，需要全局查找
4.  再开一个 DOS 窗口（因为刚才那个 DOS 窗口已经不能动了），转到 mysql\bin 目录。
5.  输入 mysql 回车，如果成功，将出现 MySQL 提示符 >。
6.  连接权限数据库： use mysql; 。
7.  改密码：update user set password=password("password") where user="root";| update user set authentication_string=password('newpassword') where user='root';
8.  刷新权限（必须步骤）：flush privileges;　。
9.  退出 quit。
10. 注销系统，再进入，使用用户名 root 和刚才设置的新密码登录。

## 日志系统

mysql 所有日志默认都是不开启的，需要用户手动开启。

> 错误日志

记录服务器启动，运行，停止中发生的错误

> 普通操作日志

记录客户端连接过程中执行的操作记录

> 二进制日志

记录服务器修改数据的行为过程。
系统参数：

- sql_log_bin：[0|1] 是否开启二进制日志记录
- log_bin：记录文件名

> 复制日志

适用于主从复制

> 慢查询日志

记录执行时间超过配置文件中设置的最大值的语句。
系统参数：

- slow_query_log：[0|1]是否开启
- long_query_time：int 阀值，超过该值会记录。
- log_output：[file|table|none] 日志记录形式
- slow_query_log_file：日志输出文件名
- log_queries_not_using_indexes：[0|1] 默认不记录不使用索引的查询。
- log_slow_admin_statements[0|1] 默认不记录管理语句，例如 ALTER TABLE, ANALYZE TABLE, CHECK TABLE, CREATE INDEX, DROP INDEX, OPTIMIZE TABLE, and REPAIR TABLE.

> DDL 日志

记录数据定义语句。

[mysql 日志系统](https://segmentfault.com/a/1190000016642320)

## 系统配置查询

```sql
-- 查看引擎
show engines
-- 查看默认引擎
show variables like '%storage_engine%'
show variables like '%general%'
show variables like '%log'
-- 设置配置值
set global variablesName=value
```

## 定义语句

```sql
-- 查看表结构
desc table_name
show create table tableName
```

## 操作语句

```xml
<!-- 数据库操作 -->
<!-- 显示数据库 -->
<sql>show databases</sql>
<!-- 新增数据表 -->
<sql>create database name</sql>
<!-- 切换数据库 -->
<sql>use database name</sql>
<!-- 删除数据库 -->
<sql>drop database name</sql>

<!-- 数据表 -->
<!-- 显示表结构 -->
<sql>desc table_name</sql>
<!-- 判断数据表存在与否 -->
<sql>drop table if exits tableName</sql>
<!-- 新增数据表 -->
<sql>create table tableName (columnName dataType (auto_increment [primary key]),,[primary key(columnname)])</sql>
<!-- 删除数据表 -->
<sql>drop table tableName</sql>
<!-- 插入数据 -->
<!-- 如果省略行，则按照数据表定义的顺序，如果是数据格式不对，则插入失败 -->
<sql>insert into tableName (column1,column2...)values(value1,value2),(value1,value2)</sql>
<!-- 查询数据-->
<sql>select * from tableName where columnName='condition'</sql>
<!--  模糊查询 -->
<sql>select * from tableName where  columnName like'%keywords'</sql>
<!-- 更新数据 -->
<sql>update tableName set columnName='value' where columnName='condition'</sql>
<!-- 删除数据 如果条件为空 删除整个数据表内容 -->
<sql>delete from tableName where columnName='condition'</sql>

<!-- 设置自增键的起始值 -->
<sql>auto_increment=1001</sql>
<!-- 表结构定义 设置表引擎和编码方式 -->
<sql>create table test(name type ext...) engine=innodb charest=otf8</sql>
```

## 优化

[MySQL 优化的几点总结](https://segmentfault.com/a/1190000016486789)

[SQL 语句执行过程详解](https://juejin.im/post/5b7036de6fb9a009c40997eb)

## 分表

[分表分库](https://segmentfault.com/a/1190000016475827)

### 水平分表

> 将大表分成多个小表，每个表存放一定的数据，在根据算法计算出需要访问的数据表

### 垂直分表

> 单行数据字段过多，且数据类型过大，把大字段的数据分到子表只中，之后用那个关联查询
