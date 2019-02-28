# nginx 模块

[document](http://shouce.jb51.net/nginx/left.html)

## gzip 压缩模块

- gzip
  - on/off
  - 是否开启 gzip 压缩
- gzip-min-length
  - 最小压缩阀值，当值超过该值，会使用 gzip 压缩，单位为 byte
  - 默认是 0

## http-proxy 代理模块

> http 模块内使用

```conf
# proxy_cache_path path [levels=number] keys_zone=zone_name:zone_size [inactive=time] [max_size=size];
# path  缓存文件的位置
# levels 文件存储的层级，已文件名生成hash，从右往左取值，最多三层。
# 假设hash=...faed4，path=/cache/4/ed/file
proxy_cache_path /cache levels=1:2;
```

## upstream 负载均衡模块

> http 模块内使用

```conf
# server name [parameters]
# name 可以是 ip:port host:port
# weight 服务器权重
upstream name{
  server 198.168.1.116:3000 weight=1;
  server 192.168.1.116:6000 weight=2;
  server www.diet.com:6896 weight=6;
}
```

## log 日志模块

- log_format
  - 定义日志的格式，可以使用变量
- access_log
  - 是否开启访问日志
  - access_log log/access.log useFormatType(使用哪种格式存储)
- error_log
  - 是否开启错误日志
  - 同上

```conf
# 定义格式化 格式名称为main，error_log/access_log 可以使用
log_format  main  '$upstream_addr - $remote_addr - $remote_user [$time_local] "$request" '
                  '$status $body_bytes_sent "$http_referer" '
                  '"$http_user_agent" "$http_x_forwarded_for"';
```

## http_auth_basic http 基本认证模块

- http_basic
  - Authorization realm=该字段。同于提示用户该使用哪个密码
- http_basic_user_file
  - 存储的用户名和密码的文件的路径。
