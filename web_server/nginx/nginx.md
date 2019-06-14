# nginx

(zh-cn-doc)[http://shouce.jb51.net/nginx/left.html];
(zh-cn-doc2)[http://www.nginx.cn/doc/];
(en-doc)[http://www.nginx.org]

## 命令行操作 nginx

> 为啥要导航到 nginx 的安装目录才能生效？

nginx -s[signal] command

- nginx -s stop
  - 快速关闭服务
- nginx -s quit
  - 优雅的关闭服务？
- nginx -s reload
  - 重启服务
- nginx -s reopen
  - ？？？

## 反向代理

## nginx 配置文件 nginx.conf

(doc)[http://nginx.org/en/docs/http/ngx_http_core_module.html#directives]

## events

## upstream

## http

### server

#### location 属性

> 匹配规则

- Syntax: location [ = | ~ | ~* | ^~ ] uri { ... }
- loation = /uri
  - 【=】开头表示精确匹配，需要完全匹配才会生效
- location ^~ /uri
  - 【^~】开头表示对 URL 进行前缀匹配
- location ~ pattern
  - 【~】正则匹配。区分大小写，正则表达式的规则是？
- location ~\* pattern
  - 【~\*】正则匹配。不区分大小写
- location /uri
  - 没有修饰符。也表示前缀匹配，当前缀匹配时会采用这个 location
- location /
  - 通配符。当没有搜索到相关的 location 会导航到这里

优先级 绝对匹配>带修饰符的前缀匹配>顺序的正则匹配>不带修饰的前缀>匹配通配符，一旦找到符合的location，会停止往下匹配，返回结果

> 内置配置
> 注意绝对路径的相对路径。/开头为绝对路径，会以当前的盘为根目录。
> 路径以/结尾会替换掉 location 匹配的 uri，结尾没有/，会把 location 匹配的 uri 链接到路径

- root
  - 默认的root为html目录
  - root + uri 目录下搜索资源
  - location /food ;;; root = /static/images/
    - http://localhost/food
    - 上面的地址会导航到 http://localhost/static/images/food 目录下去搜索资源
- alias
  - 会把 alias 字段替换为 uri 去搜索资源
  - location /food ;;; alias = /static/images
    - http://localhost/food
    - 上面的地址会导航到 http://localhost/static/images 目录下去搜索资源
- try_files
  - 如果未找到匹配的文件，会尝试加载指定的file
  - try_files $uri $uri/ webapps/index.html

## 变量

[全局可用变量](http://nginx.org/en/docs/varindex.html)

> 常用变量

- $content_length
  - 文档长度
- $request_uri
  - 带域名的路径
- $uri
  - 路径
- $host
  - 主机
- $hostname
  - 带端口主机
- $request_method
  - 请求方法
- $server_port
- $server_protocol
- $status

## 配置文件的语法

```conf
# 语句以分号结束
set $flag "0";  # 设置变量和值

# 各个关键字要空格分开，没有else语法，比较支持= > <
if ( $flag = "1" ) {

}
```

## NOTE

> nginx 如何处理 request

(nginx 处理请求过程)[http://nginx.org/en/docs/http/request_processing.html]
