# dns 解析模块

node 的 dns 解析模块。目前主要有两种解析方式，一种是直接调用本地主机进行解析，另一种是直接访问真实的 dns 服务器去异步解析。

解析方法：

- lookup(hostname[,options],callback)
  - 本地解析
  - callback
    - 参数
      - err
      - address
        - ip 地址
      - family
        - 4/6
- resolve(hostname[,rrtype],callback)
  - 底层方法，通过参数 rrtype 来识别是哪种解析，其他解析方法都是自他衍生出来的
- resolve4(hostname[,options],callback)
  - 解析 ipv4 地址
- resolve6(hostname[,options],callback)
  - 解析 ipv6 地址

```javascript
const dns = require('dns')
```
