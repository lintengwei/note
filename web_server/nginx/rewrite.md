# rewrite 规则

(规则)[https://www.cnblogs.com/zhang-shijie/p/5453249.html]

> 语法:::: rewrite regex URL [flag]

- @param rewrite 关键字
- @param regex 正则表达式
- @param URL 替换的 url
- @param flag 关键字
- @param flag 可以取值[last,break,redirect,permanent]
  - last
    - 上下文 server,location
    - 表示该次 rewrite 完成
  - break
    - 上下文 server,location,if
    - 停止执行当前虚拟主机的后续 rewrite 指令集
    - 与 last 的区别是不会再把重写的 url 再次 rewrite，而 last 可能再次与后面的 rewrite 匹配？？？
  - redirect
    - 返回 302 临时重定向
  - permanent
    - 返回 301 永久重定向
  - return
    - 返回状态
    - 不能返回 301 302，因为重定向需要返回地址
  - -f ! -f
    - 判断文件是否存在

## 全局可用变量

- \$args： #这个变量等于请求行中的参数，同\$query_string
- \$content_length： 请求头中的 Content-length 字段。
- \$content_type： 请求头中的 Content-Type 字段。
- \$document_root： 当前请求在 root 指令中指定的值。
- \$host： 请求主机头字段，否则为服务器名称。
- \$http_user_agent： 客户端 agent 信息
- \$http_cookie： 客户端 cookie 信息
- \$limit_rate： 这个变量可以限制连接速率。
- \$request_method： 客户端请求的动作，通常为 GET 或 POST。
- \$remote_addr： 客户端的 IP 地址。
- \$remote_port： 客户端的端口。
- \$remote_user： 已经经过 Auth Basic Module 验证的用户名。
- \$request_filename： 当前请求的文件路径，由 root 或 alias 指令与 URI 请求生成。
- \$scheme： HTTP 协议（如 http，https）。
- \$server_protocol： 请求使用的协议，通常是 HTTP/1.0 或 HTTP/1.1。
- \$server_addr： 服务器地址，在完成一次系统调用后可以确定这个值。
- \$server_name： 服务器名称。
- \$server_port： 请求到达服务器的端口号。
- \$request_uri： 包含请求参数的原始 URI，不包含主机名，如：”/foo/bar.php?arg=baz”。
- \$uri： 不带请求参数的当前 URI，\$uri 不包含主机名，如”/foo/bar.html”。
- \$document_uri： 与\$uri 相同。

```conf
# 重定向
server {
    listen 80;
    listen 443 ssl;
    server_name www.old-name.com old-name.com;
    return 301 $scheme://www.new-name.com;
}
```
