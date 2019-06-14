# redis 配置文件

[https://www.cnblogs.com/zhang-ke/p/5981108.html](https://www.cnblogs.com/zhang-ke/p/5981108.html)

```conf
# 密码配置
# 配置文件修改之后，需要重启服务器
requirepass password
```

```bat
rem 开启服务器，后台挂起，提示选择配置文件
redis-server --service-start redis.windows.conf
rem 关闭
redis-server  --service-stop
rem 安装服务
redis-server  --service-install
```
