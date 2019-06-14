# socket.io 的使用

```javascript
const http = require('http')
const httpServer = http.createServer()
const Server = require('socket.io')

//  把Server绑定到指定的http服务器
const io = new Server(httpServer)

io.on('connect',socket=>{
  //  监听命名空间  / 下面的新连接
})

const adminNsp=io.of('/admin)

adminNsp.on('connect',socket=>{
  //  监听  【/admin】  下面的新连接
})

httpServer.list(9999) //  开启http服务器
```

## Server

### 创建新的服务器

- new Server(httpServer[,options])
  - 把websocket挂载在现有的http服务器
- new Server(port[,options])
  - 创建新的websocket服务器
    - options
      - path
        - 命名空间
      - serverClient
      - adapter
      - origins
        - 允许的请求源，默认为【*】，支持所有
      - parser
      - 底层引擎的选项
      - pingTimeout
      - pintInterval
      - upgradeTimeout
      - maxHttpBufferSize
      - allowRequest
      - transports
      - allowUpgrades
      - perMessageDeflate
      - httpCompression
      - cookie
      - cookiePath
      - cookieHttpOnly
      - wsEngine

### 属性

- sockets
- engine.generateId

### 方法

- serveClient([value])
- path([value])
- adapter([value])
- origins([value])
- origins(fn)
- attach(httpServer[,options])
- attach(port[,options])
- listen(httpServer[,options])
- listen(port[,options])
- bind(engine)
- onconnection(socket)
- of(nsp)
- close(callback)

### 事件

- connect
  - 连接事件，所有连接的客户端都会在服务器触发这个事件，包括指定连接到某个命名空间下的

## Namespace

### 属性

- name
  - 命名空间的名称
- connected
  - 连接到此命名空间下的以 id 作为键，socket 作为值的对象
- adapter
  - 用于命名空间的适配器。当使用基于 redis 的适配器时很有用，因为它公开了跨集群管理套接字和房间的方法。

### 事件

- connect
  - 当有新的客户端连接到该命名空间下的时候会触发该事件

### 方法

- to(room)
  - 进入该空间下的某个房间
- in(room)
  - to 的别名
- emit(eventName[,...args])
  - 触发某个事件，连接到该空间下的 socket 都会收到
- clients(callback)
  - 获取连接到此命名空间的客户端 ID 列表（如果适用，跨所有节点）。也适用于房间，或者默认的命名空间
- use(fn)
  - 使用中间件

### 标识

- volatile
  -
- binary
- local

```javascript
//  创建命名空间
const nsp1 = io.of('/nsp1')

nsp1.on('connect', socket => {
  //  监听命名空间下的连接
})
```

## Socket

### 属性

- id
  - 连接的 socket 唯一识别码
- rooms
  - 已经加入的房间
- client
- conn
  - 对基础客户端传输连接（engine.io socket 对象）的引用。这允许访问 IO 传输层，该层仍然（大部分）抽象实际的 TCP/IP 套接字。
- request
  - 用于访问请求头，如 cookie 或用户代理
- handshake
  - 握手的头部
    - headers
    - time
    - address
    - xdomain
    - secure
    - issued
    - url
    - query

### 事件

- disconnect
  - 客户端断开连接事件
- error
  - 连接出现错误事件
- disconnecting
  - 当客户端将要断开连接（但尚未离开其房间）时激发。

### 标识

- broadcast
  - 给除了自己之外的所有的客户端发布事件，
- volatile
- binary
  - 指定发送的数据是否包含二进制数据

```javascript
//  cli
socket.on('join', id => {
  console.log(`${id} join`)
})

//  server
io.on('connect', socket => {
  //   通知自己之外的所有客户端，有新的用户加入
  socket.broadcast.emit('join', socket.id)
})
```

### 方法

- use(func<package,next>)
- send([...args][,ack])
  - emit 的别名
- emit(eventName[,...args][,ack])
- on(eventName,callback)
- once(eventName,callback)
- removeListener(eventName,listener)
- removeListenerAll([eventName])
- eventNames()
- join(room[,callback])
- join(rooms[,callback])
- to(room)
- in(room)
- compress(value)
- disconnect(close)

```javascript
//  使用中间件
io.on('connect', socket => {
  socket.use((packet, next) => {
    //  packet=== [eventName,param1,param2,...params]
    console.log(packet)
    return next()
  })
})
```

## 注意点

1. 【/】命名空间下的事件会在子命名空间之前触发？
2. 【/】命名空间下【emit】的事件会通知给所有连接的客户端，而在某个命名空间下调用【emit】则只会通知该命名空间下的 socket？
