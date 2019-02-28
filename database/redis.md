# redis

## 数据结构

### String

- APPEND
- BITCOUNT
- BITOP
- DECR
- DECRBY
- GET
- GETBIT
- GETRANGE
- GETSET
- INCR
- INCRBY
- INCRBYFLOAT
- MGET
- MSET
- MSETNX
- PSETEX
- SET key value [EX seconds] [PX milliseconds] [NX|XX]
- SETBIT key offset value
  - 位数统计，签到应用
- SETEX
- SETNX
- SETRANGE key offset value
- STRLEN

### Hash

- HDEL
- HEXISTS
- HGET key field 
  - 一次获取单个值
- HGETALL
- HINCRBY
- HINCRBYFLOAT
- HKEYS
- HLEN
- HMGET  key field [field ...]
  - 获取多个值
- HMSET  key field value [field value ...]
  - 设置多个值
- HSET  key field value
  - 一次设置单个值
- HSETNX
  - 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
  - NX NOT EXIST
- HVALS
- HSCAN

### Set

- SADD
- SCARD
- SDIFF key1 key2 [key3]
  - 返回一个集合的全部成员，该集合是所有给定集合之间的差集。不存在的 key 被视为空集。
  - 返回在其他集合不存在的元素数组
- SDIFFSTORE destination key1 key2 [key3]
  - 类似【SDIFF】,但是会把结果i存入【destionation】key中
  - note
    - 如果 destination 集合已经存在，则将其覆盖。
    - destination 可以是 key 本身。
- SINTER
  - 【SDIFF】反向操作。返回在几个集合中都存在的元素
- SINTERSTORE
  - 同上
- SISMEMBER
- SMEMBERS
- SMOVE source destination member
  - 移动集合元素到其他集合
- SPOP  
  - 移出一个随机元素
- SRANDMEMBER key [count]
  - 获取一个随机元素
  - note
    - 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数：
    - 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
    - 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
- SREM key member [member ...] 
- SUNION
- SUNIONSTORE
- SSCAN

### List

> 关于区间的操作都是闭区间？

- BLPOP key [key ...] timeout
  - LPOP的阻塞原语
    - 阻塞
      - 当有一个非空列表
    - 非阻塞
      - 当key都不存在或者都为空，则会阻塞，直到有新元素插入或者超时
- BRPOP
- BRPOPLPUSH
- LINDEX
- LINSERT key BEFORE|AFTER pivot value
  - 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
  - 当 pivot 不存在于列表 key 时，不执行任何操作。
  - 当 key 不存在时， key 被视为空列表，不执行任何操作。
- LLEN
- LPOP
- LPUSH 
  - 的给定key不存在时候创建
- LPUSHX
  - 当给定key不存在不做操作
- LRANGE key start stop
  - 标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素
  - 闭区间 start stop 同时包含
- LREM  key count value
  - 根据参数 count 的值，移除列表中与参数 value 相等的元素。
  - note
    - count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
    - count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
    - count = 0 : 移除表中所有与 value 相等的值。
- LSET
- LTRIM
- RPOP
- RPOPLPUSH source destination
  - 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
    - 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
    - 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
- RPUSH
- RPUSHX

### SortedSet（有序集合）

- ZADD
  - ZADD key score member [[score member] [score member] ...]
    - 每个成员可以附加一个分数值
    - score 值可以是整数值或双精度浮点数。
    - 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
  - note
    - 在 Redis 2.4 版本以前， ZADD 每次只能添加一个元素。
- ZCARD 
  - 返回基数
- ZCOUNT
  - ZCOUNT key min max
    - 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
- ZINCRBY
- ZRANGE
- ZRANGEBYSCORE
- ZRANK
- ZREM
- ZREMRANGEBYRANK
- ZREMRANGEBYSCORE
- ZREVRANGE
- ZREVRANGEBYSCORE
- ZREVRANK
- ZSCORE
- ZUNIONSTORE
- ZINTERSTORE
- ZSCAN

## 事务

不支持回滚。

> WATCH 命令可以为 Redis 事务提供 check-and-set （CAS）行为（乐观锁）
> WATCH 使得 EXEC 命令需要有条件地执行： 事务只能在所有被监视键都没有被修改的前提下执行， 如果这个前提不能满足的话，事务就不会被执行。
> MULTI之前先watch需要操作的key，如果在exec之前发现key的值被修改了，则放弃此次事务
> 事务不管陈功与否，最后都会取消对key的watch

```cli
redis> MULTI            # 标记事务开始
OK

redis> INCR user_id     # 多条命令按顺序入队
QUEUED

redis> INCR user_id
QUEUED

redis> INCR user_id
QUEUED

redis> PING
QUEUED

redis> EXEC             # 执行
1) (integer) 1
2) (integer) 2
3) (integer) 3
4) PONG
```

## 发布订阅

- PSUBSCRIBE pattern [pattern ...]
  - 订阅一个或多个符合给定模式的频道。SUBSCRIBE的通配版本
- PUBLISH channel message
  - 将信息 message 发送到指定的频道 channel 。
- PUBSUB
- PUNSUBSCRIBE [pattern [pattern ...]]
  - 指示客户端退订所有给定模式。
  - 如果没有模式被指定，也即是，一个无参数的 PUNSUBSCRIBE 调用被执行，那么客户端使用 PSUBSCRIBE 命令订阅的所有模式都会被退订。在这种情况下，命令会返回一个信息，告知客户端所有被退订的模式。
- SUBSCRIBE channel [channel ...]
  - 订阅给定的一个或多个频道的信息
- UNSUBSCRIBE [channel [channel ...]]
  - 指示客户端退订给定的频道。
  - 如果没有频道被指定，也即是，一个无参数的 UNSUBSCRIBE 调用被执行，那么客户端使用 SUBSCRIBE 命令订阅的所有频道都会被退订。在这种情况下，命令会返回一个信息，告知客户端所有被退订的频道。