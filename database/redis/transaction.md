# redis 事务

## 基本使用

```bat
rem   开启一个事务
multi
rem   指令放入队列
set a 1
rem   指令放入队列
set b 2
rem   执行事务
exec
rem   放弃事务
discard
```

## 分布式锁

## 乐观锁

使用【watch】和【unwatch】实现乐观锁。在事务开启之前，使用 watch 对某个键进行监控，在 exec 命令执行前，如果其他客户端修改了被监控的键的值，那么事务会失败，返回 【nil】，如果事务成功，那么 exec 命令会按照事务队列的顺序返回对应的结果

```bat
rem 客户端1
rem 监视a
watch a
rem 开启事务
multi
rem 对a操作
incr a
rem 执行，因为客户端2对被监控的键进行了修改，所以返回【nil】
exec
rem 取消当前事务
rem 【exec】和【discard】两个命令对自动【unwatch】被监控的键
discard


rem 客户端2
rem 在客户端exec之前对被监控的键执行动作
set a 222
```

## 注意点

1. redis 事务为什么不存在回滚？
