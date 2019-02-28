# web 的请求过程

## DNS 解析

### 解析过程

* 首先在浏览器缓存中查看是否有对应的 dns 缓存
* 如果 1 中没有则当本机的 dns 缓存中查找，在 windows 下的 c:\Window\system32\dirvers\etc\hosts
* 如果本地没有找到 dns 缓存，则去我们的服务提供商提供的 dns 域名解析服务器查找-LDNS
* 如果 spa 服务商 dns 域名服务器也没有查到，回到全球顶级的 dns 服务器——Root Server （.com .cn .org）
  * 为什么要到到这边去请求？
* 而后根据 Root Service 返回的主域名解析器（gTLD Server）中过去查询。是国际顶级域名服务器
* LDNS 会从主域名请求返回的 Name Server 中去查询，一般就是我们购买的域名的代理商，可以有好几级代理。因此可能这边需要多次解析。
* 然后 Name Server 会返回我们对应的域名和 ip 的映射关系，返回给 LDNS，LDNS 会缓存这个域名，并且的返回给请求用户。

### 常见的域名解析方式

* A 记录
  * 就是我们直接设置域名和 ip 地址的映射关系，然后保存到我们的域名服务商的数据库里，个人理解。
* MX 记录
  * Mail Exchange。将某个域名下的邮箱服务器解析到指定的 ip 地址中
* CNAME 记录
  * 域名的别名。只想另外一个域名，相当于是外号一样
* NS 记录
  * 为某个域名制定 DNS 服务器。可能是当我们的域名解析请求道了 Name Server 阶段的时候，他不去查询自己的映射记录，而是去请求我们制定的 DNS 服务器查询结果。

## CDN【Content Delivery Network】【内容分发网络】
