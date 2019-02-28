# http

## http.request(opts)

```javascript
let opt={
  host:'www.baidu.com'，//请求域名
  hostname:'www.baidu.com',// 优先级高于host
  path:'/path',
  procotol:'http',   // default http
  port:'80',  // default 80
}
```

## http.createServer(callFunc(req,res))

```javascript {.line-numbers}
const http=require('http);
const server=http.createServer((req,res)=>{

})
server.listen(port)
```
