# nginx

(zh-cn-doc)[http://shouce.jb51.net/nginx/left.html];
(zh-cn-doc2)[http://tengine.taobao.org/book/index.html];
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

> 内置配置
> 注意绝对路径的相对路径。/开头为绝对路径，会以当前的盘为根目录。
> 路径以/结尾会替换掉 location 匹配的 uri，结尾没有/，会把 location 匹配的 uri 链接到路径

- root
  - root + uri 目录下搜索资源
  - location /food ;;; root = /static/images/
    - http://localhost/food
    - 上面的地址会导航到 http://localhost/static/images/food 目录下去搜索资源
- alias
  - 会把 alias 字段替换为 uri 去搜索资源
  - location /food ;;; alias = /static/images
    - http://localhost/food
    - 上面的地址会导航到 http://localhost/static/images 目录下去搜索资源

## 变量

[全局可用变量](http://nginx.org/en/docs/varindex.html)

> 常用变量

- \$content_length
  - 文档长度

## NOTE

> nginx 如何处理 request

(nginx 处理请求过程)[http://nginx.org/en/docs/http/request_processing.html]
