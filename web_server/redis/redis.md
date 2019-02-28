# redis

## 安装

+ 下载地址：https://github.com/MSOpenTech/redis/releases。
+ 配置系统环境变量
+ 开服服务端 - redis-service -conf
+ 开启客户端 - redis-cli

## 操作

### string操作

> 当对应的key不存在会返生什么，返回的是什么

```java {.line-numbers}

//  保存值
set key value 
setnx key value   //  不存在设置
mset key1 value1 key2 value2 //设置多个值
setex key seconds value  //赋值 并且设置过期时间 秒单位
psetex key milliseconds value  //赋值 并且设置过期时间 毫秒单位

//  获取
get key
mget key1 key2    //  获取多个值

//  截取部分值 闭区间 包含start end
getrange key start end 
setrange key offset value //在给key对应的值覆盖 从offset开始 包含offset 返回字符串长度 
msetnx key1 value1 key2 value2 //多个值 不存在设置

//  长度
strlen key

//  自操作
incr key // 数值加1
incrby key increment //增量 
incrbyfloat key increment //增量 浮点 不精确 去除需要在做变化
decr key  //减1
decrby key descrement //减量

//  追加
append key value //字符串
```

### hashmap

```java {.line-numbers}

//  长度
hlen hashmap

//  赋值
hset hashmap key value
hmget hashmap key1 key2 //获取多个值
hmset hashmap key1 value1  key2 value2 //设置多个值
hsetnx hashmap key value // 不存在赋值
hvals key //获取所有的值 不包含key

//  获取
hget hashmap key 
hgetall hashmap //获取所有的字段的值
hkeys hashmap //获取所有字段

//  增量操作
hincrby hashmap key increment //字段增加i
hincrbyfloat hashmap key increment //字段增加i

//  查看
hexits hashmap key //是否存在某个字段

```

### list

```java {.line-numbers}

//  长度
llen list

//  插入
lpush list value1 value2 // 插入列表头部
linsert list before|after exitValue newValue //从后往前遍历 直到找到第一个符合要求的

//  移除
lpop list  //移除第一个元素并返回
rpop list  //移除第后一个元素并返回
blpop list1 list2 timeout    //移除并获取列表的第一个元素 只要有一个列表有返回就结束 等待时常timeout s
brpop list1 list2 timeout   // 移除最后一个元素
lrem list count value //移除给定值的在列表中的数量

//  获取
lindex list index // 通过索引获取指定元素
lrange list start end // 获取全部 start=0 end=-1

```

### set

### sort-set

## 发布订阅模式

```java {.line-numbers}

//  注册一个客户端
subscribe channel1 
psubscribe channel1 channel2 //注册多个频道

//  发布消息
publish channel1 message 

//  查看注册的频道
pubsub channels

```