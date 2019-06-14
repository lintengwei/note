# websocket协议

[文档](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers)
web应用和服务器的长连接

## 请求头

> Upgrade

值为【Websocket】，扁食客户端希望建立websocket连接

> Connection

值为【Upgrade】表示客户端希望长连接

> Sec-WebSocket-Key

随机字符串，用于验证协议是否为websocket协议而非http协议

> Sec-WebSocket-Version

表示客户端使用的是什么版本的websocket

## 响应头

> Upgrade

同上

> Connection

同上

> Sec-WebSocket-Accept

根据请求头的Sec-Websocket-Key和特殊字符串【258EAFA5-E914-47DA-95CA-C5AB0DC85B11】拼接，然后使用hash函数【sha1】计算hash值，最后把hash值使用base64编码即为最终的结果，验证是否为Websocket协议

> Sec-WebSocket-Location

对应请求的Host头

## 如何建立WebSocket服务器

### 握手

客户端请求报文：

> GET /chat HTTP/1.1
> Host: example.com:8000
> Upgrade: websocket
> Connection: Upgrade
> Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
> Sec-WebSocket-Version: 13

服务器响应报文：

> HTTP/1.1 101 Switching Protocols
> Upgrade: websocket
> Connection: Upgrade
> Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=

```javascript
const http=require('http')
const crypto=require('crypto)

const server=http.createServer((req,res)=>{

})

//  监听协议升级事件
server.on('upgrade',(req,socket,head)=>{
  //  req 为请求信息
  //  socket 为底层socket
  //  head
  let key=req.headers['sec-websocket-key']
  let accept=createSecWebsocketAcceptStr(key)

  //  发送给客户端建立连接
  socket.write(`HTTP/1.1 101 Switching Protocols\r\n
                Upgrade:Websocket\r\n
                Connection:Upgrade\r\n
                Sec-Websocket-Accept:${accept}\r\n
                \r\n`)

  //  什么意思
  socket.pipe(socket)
})

server.listen(9999,()=>{
  console.log(`server is running port 9999`)
})

//  认证协议
function createSecWebsocketAcceptStr(key) {
  const str = '258EAFA5-E914-47DA-95CA-C5AB0DC85B11'
  const sha1 = crypto.createHash('sha1')
  sha1.update(key + str)
  return sha1.digest().toString('base64')
}

```


## 客户端

```javascript
const webSocket=new WebSocket(url)

//  状态，常量
WebSocket.CONNECTING =0 //  正在建立连接
WebSocket.OPEN = 1 //  已经建立连接
WebSocket.CLOSING = 2  //  正在关闭连接
WebSocket.CLOSED =3  //  已经关闭连接

//  实例变量
webSocket.readystate    //  当前的状态

//  实例方法
webSocket.onopen=function(){} //  连接成功之后调用
webSocket.onerror=function(error){}  //  发生错误调用
webSocket.onmessage=function(data){}  //  收到服务器消息调用
webSocket.onclose=function(){}  //  关闭时候电泳

/* 
一个数字状态码，它解释了连接关闭的原因。如果没有传这个参数，默认使用1005。
*/
webSocket.close([code[,reason]])
webSocket.send(data)
```

注意点：

- http协议版本必须大于1.1
- 请求方法必须是get