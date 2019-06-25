# http 协议

## http 报文

### 报文格式

请求报文格式:

<method> <url> <version> /r/n
/r/n
<headers> /r/n
/r/n
<entity-body> /r/n

响应报文格式:

<version> <status> <reason>/r/n
/r/n
<headers> /r/n
/r/n
<entity-body> /r/n

## 首部

首部是报文中关于请求或者响应的详细的信息,可以分为:

- 通用首部
  - 请求和响应都可以包含
- 请求首部
  - 请求
- 响应首部
  - 响应
- 实体首部
  - 实体信息
- 扩展首部
  - 规范中没有定义的首部

### 通用首部

#### 通用信息首部

- Connection
  - 客户端和服务器连接的选项
- Date
  - 日期
- MIME-Version
- Trailer
  - 如果报文采用了分块编码传输
- Transfer-Encoding
  - 告知接收端为了保证可靠的传输,对报文采用那个了什么编码方式
- Update
  - 告知发送端可能要升级使用的协议
- Via
  - 显示报文经过的中间节点

#### 通用缓存首部

- Cache-Control
  - http/1.1
  - 缓存指示,使用的是相对时间
  - 例如
    - Cache-Control: Max-Age=484200
    - 表示自请求之后 484200 秒文档都是有效的
  - 相关
    - Expires
      - http/1.0+
      - 响应缓存首部
      - 指定文档的过期日期,在过期日期之前都使用缓存
- Pragma
  - 另一种随报文一起发送的指示,但是不专用于缓存??

### 请求首部

#### 请求的信息首部

- Client-Ip
  - 提供客户端 ip
- From
  - 提供客户端的 email 地址
- Host
  - 提供接收请求的服务端的主机名和端口
- Referer
  - 提供了包含当前请求 URI 的文档的 URL
- UA-Color
- UA-CPU
- UA-Disp
- UA-os
- UA-Pixels
- User-Agent
  - 将发起请求的客户端的应用程序名称告诉服务器

#### Accept 首部

用于告知服务器,客户端能接收的信息,如编码格式,字符集等

- Accept
  - 告诉服务器接收那些媒体文件
- Accept-Charset
  - 告诉服务器接收哪些字符集
- Accept-Encoding
  - 告诉服务器接收那些编码方式,实体的编码
- Accept-Language
  - 告诉服务器接收哪种语言

#### 条件首部

用于缓存的再验证

- Expect
  - 允许客户端列出请求所要求的呃服务器行为
- If-Match
  - 如果实体标记与文档当前的实体标记相匹配,就获取这份文档
- If-Modified-Since
  - 除非在指定的时间之后修改过文档,否则限制这个请求
- If-None-Match
  - 如果不匹配,就获取文档
- If-Range
  - 允许对文档的某个范围进行条件请求
- If-Unmodifyed-Since
- Range

#### 安全请求首部

- Cookie
- Cookie2
- Authorization
  - 包含了客户端的认证数据

#### 代理请求首部

- Max-Forward
- Proxy-Authorization
- Proxy-Connection

### 响应首部

#### 响应信息首部

- Age
- 从最初创建开始,响应的持续时间
- Public
  - 服务器为其资源支持的请求方法列表
- Server
  - 服务器应用程序的名称
- Title
  - html 文档标题

#### 协商首部

- Accept-Ranges
  - 对资源来说,服务器可以接收的范围类型
- Vary

#### 安全响应首部

- Proxy-Authorization
- Set-Cookie
- Set-Cookie2
- www-Authorization
  - 服务端对客户端的质询列表

### 实体首部

#### 实体信息首部

- Allow
  - 列出了可以对次实体执行的请求方法,一般是 options 请求?
- Location
  - 告知客户端实体实际位于何处,用于将接收端定向到资源的位置上去

#### 内容首部

- Content-Base
- Content-Encoding
  - 实体采用的编码
- Content-Language
  - 实体语言
- Content-Length
  - 实体长度
- Content-Location
  - 实体位置
- Content-MD5
  - 实体的 MD5 校验和
- Content-Range
- Content-Type

#### 实体缓存首部

- ETag
  - 以此实体先关的实体标记
- Expires
  - 实体不在有效,需要从源端再次获取的日期和时间
- Last-Modified
  - 实体最后一次修改的事件
