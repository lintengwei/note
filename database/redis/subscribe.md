# 发布和订阅

- psubscribe channel [...channel]
  - 订阅给定频道，支持通配符【\*】
- publish channel message
  - 发布信息
- pubsub
- punsubscribe
  - 退订所有给定模式
- subscribe
  - 订阅频道，不支持通配符，只能是确切的频道名称？
- unsubscribe
  - 退订指定的频道

```bat
rem 客户端1
rem 订阅频道
psubscribe test
rem 收到信息格式

pmessage  rem 返回值类型：信息
pattern   rem 信息匹配的模式/订阅信息时候指定的匹配模式
currentChannel  rem 信息本省的目标频道，确切的频道
message rem 收到的信息


rem 客户端2
rem 发布信息
publish test hello
```
