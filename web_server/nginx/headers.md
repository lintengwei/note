# 响应头和请求头

## 响应头

## 缓存相关

- ETag
  - 实体标记，配合 request-header 头部的 If-Match 一起使用来达到缓存的目的
  - 具体过程
    - 服务器返回客户端请求的文件，并且生成一个 token 赋值给 Etag，设置在响应头部一起返回给浏览器，浏览器缓存该文件以及 Etag，并且在下一次请求该文件的时候，会把 Etag 的值赋值给【If-None-Match】字段发送给服务器，服务器根据比对这两个值的一致性，来判断是否发送一个新的文件还是返回 304 状态码给客户端。
    - 怎么生成？ETag 的值保存在哪里？每次修改文件是不是会生成一个 ETag 来刷新上一次保存的值？
      - 是根据文件内容来生成？不保存 ETag 的值？而是每次请求过来的同时，再去计算一次当前请求文档的 ETag 的值，然后比对。【If-None-Match】false，返回 304，继续使用客户端缓存，true，返回 200，服务器发送最新的文件。
  - 如果同时设置了 Cache-Control:max-age;;;expires 等，有会使用哪个规则？
    - 同时使用。都检查完毕且满足必要的规则，才会做出响应
- Last-Modified
  - 请求的文档的最后修改时间。如果是动态内容有是什么？
  - 配合【If-Modified-Since】一起使用
- Expires【不推荐使用】
  - 响应失效的日期和时间
  - 服务器给请求的文件加上时间，告知客户端，在这个时间之前都不需要再次请求服务器，而是直接使用本地文档？

### 【Cache-Control】如何有效的控制缓存

服务器可以通过集中方式来指定文档过期之前可以将其缓存多长时间，按照优先级递减的顺序，可以：

- 附加一个 Cache-Control:no-store 首部到响应中去；
  - 禁止缓存该响应实体，每次请求都会发送整个请求文档
- 附加一个 Cache-Control:no-cache 首部到响应中去；
  - 可以缓存响应实体，当时在在验证之前不能提供给客户端使用
- 附加一个 Cache-Control:must-revalidate 首部到响应中去；
- 附加一个 Cache-Control:max-age=3600 首部到响应中去；
  - 可以缓存，但是在 3600s 之后，会失效？？？？
  - 将其值设置为 0，可以每次请求返回的都是最新的文件
- 附加一个 Expires 日期首部到响应中去；
- 不附加过期信息，让缓存确定自己的过期日期？？？

## 请求头

> 文件相关

- Accept
  - 客户端接受的文件类型
- Accept-Encoding
  - 客户端接受的压缩类型
  - gzip deflate
- Accept-Language
  - 客户端接受的语言
  - zh-CN,zh;q=0.9
- Accept-Charest
  - 浏览器能接受的编码类型
- Content-Type
  - 传输给服务器的文档类型，服务器根据这个字段来完成传输数据的解析
  - text/html;application/json;application/xml;application/x-www-form-urlencoded

> 缓存相关

- Cache-Control

- If-Match
  - 如果在响应头中有返回 ETag 字段，在下次请求中可以通过【If-Match】【If-None-Match】头部携带该字段来决定是否获取文档。
- If-None-Match
  - 同上
- If-Modified-Since
  - 如果响应头中有【Last-Modified】字段，客户端会缓存文件和头部信息。当喜下次再次请求该文件的时候，会附带头部信息【If-Modified-Since】字段，并且值为【Last-Modified】。服务器根据该字段来比对请求文件的最后修改时间，如果已知，则返回 304，否则返回 200
- If-Unmodified-Since
  - 同上
- ## If-Range

> 浏览器相关

- User-Agent

> 域名相关

- Host
- Referer

> 安全先关

- Authorization
- Coookie
- Cookie2
