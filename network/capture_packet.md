# 抓包

## wireshark抓包工具

### 过滤条件

- 过滤ip
  - ip.src eq 127.0.0.0
    - 过滤发出请求的ip
  - ip.dst eq 127.0.0.1
    - 过滤接收请求的ip
  - ip.addr eq 127.0.0.1
    - 过滤作为发出和请求一方的ip
- 过滤端口
  - tcp.port eq 88
    - 过滤所有端口为88的包
  - tcp.dstport eq 80
    - 过滤所有目标端口为80的包
  - tcp.srcport eq 999
    - 过滤所有发送端端口为999的包
  - tcp.port eq 80 or udp.port 99
- 过滤协议
  - tcp
  - udp
  - http
- http模式过滤
  - http.request.method == "GET"
  - http.request.method == "POST"
  - http.request.uri == "/img/logo-edu.gif"
  - http contains "GET"
  - http contains "HTTP/1."
- 多个条件
  - and | or | && | ||
  - 且/或
