# socket 客户端使用

## 基本使用

```javascript
const io = require('socket.io')
const socket = io('ws://localhost:9999') //  如果没有带路径，默认连接到命名空间【/】下，如果没有参数，默认是window.location
const socketAdmin = io('ws://localhost:9999/admin') //  如果带了路径，那么表示的是该客户端连接到服务器指定的命名空间，本例连接到namespace【/admin】
```

## io

- io(url?:String,options?:Object)
  - url
    - 连接的 url
  - options
    - forceNew<Boolean>
      - 是否重用已存在的连接，全局只有一个连接？
      - 如果在同一命名空间下，不带参数也会创建一个新的 socket
    - transports<Array>
      - 连接通道模式
        - websocket
          - 直接建立 socket 连接
        - polling
          - 默认先建立轮询的连接，之后才会转成稳定的 socket

```javascript
const socket = io() //  连接到window.location
const socket = io('ws://localhost:3000') //  连接到 localhost:3000服务下的命名空间【/】
const socket = io('ws://localhost:3000/nsp?token=FD631892ACB24') //  连接到 localhost:3000服务i下的命名空间【/nsp】,可以携带参数
```

### 参数

```javascript
//  client带查询参数
const socket = io('ws://localhost:3000/?token=892DF')
//  待查询选项？
const socket = io('ws://localhost:3000/', {
  query: {
    token: '892DF'
  }
})

//  server
const io = require('socket.io')()

//  中间件
io.use((socket, next) => {
  let token = socket.handshake.query.token
  if (auth(token)) {
    return next()
  }
  return next(new Error('auth error'))
})

io.on('connection', socket => {
  let token = socket.handshake.query.token
})
```

### 带自定义头【extraHeaders】

自定义的头部值会作用在【transport】为【polling】的时候，因为 websocket 不识别自定义的头部

```javascript
//  client
const socket = io({
  transportOptions: {
    polling: {
      extraHeaders: {
        fus: 'test'
      }
    }
  }
})

//  server
io.use((socket, next) => {
  const fus = socket.handShake.headers['fus']
})
```

## Manager

io 函数会隐式的创建管理器吗？管理器有什么作用？

- new Manager(url:String,options:?Object)
  - url
    - 连接地址
  - options
    - 附带参数
      - path
      - reconnection
      - reconnectionAttempts
      - reconnectionDelay
      - reconnectionDelayMax
      - timeout
      - autoConnect
      - query
      - parse

## Socket

### 事件

- connect
  - 成功连接服务器会触发事件
- connect_error
  - 跟事件【error】的区别是什么
- connect_timeout
  - 连接超时的时候出触发
- error
  - 连接错误会触发
- disconnect
  - 断开连接的时候触发
- reconnect
  - 成功重连触发
- reconnect_attempt
  - 每次尝试重连的时候会触发该事件
  - 回调函数接收一个参数，表示重连的次数
  - attemptNumber
- reconnecting
  - 在尝试重连的时候触发
- reconnect_error
  - 重连发生错误触发
- reconnect_failed
  - 重连失败触发，配合配置选项【reconnectionAttempts】一起使用
- ping
  - 当客户端发送心跳包的时候会触发该事件
- pong
  - 服务器对客户端 ping 的回应
  - 回调函数接收一个参数，表示发送 ping 之后到收到恢复等待的事件，单位 ms

### 属性

- id<Strinf>
  - 唯一识别码
- connected<Boolean>
  - 是否连接
- disconnected<Boolean>
  - 是否没连接

### 方法

- open()
  - 默认 socket 是自动连接的，配合配置参数的【autoConnect】一起使用，如果当新建的时候【autoClose】设置为【false】，那么需要手动开启需要调用【open】
- connect()
  - open 的别名
- send([...args][,ack])
- emit(eventName[,...args][,ack])
  - 触发服务器监听的事件
- on(eventName,callback<data>)
  - eventName
    - 监听的事件名称
  - callback
    - 事件触发的回调，参数为传输的数据
- compress(value:Boolean)
  - 是否压缩传输的数据，默认是 true
- ## binary(value)
- close()
- disconnect()
  - close()的别名
  - 可以通过调用【open】重新连接服务器

```javascript
//  emit
socket.emit('test', 'tobi', data => {
  //  ack的作用
  console.log(data) // data will be 'woot'
})

// server:
io.on('connection', socket => {
  socket.on('test', (name, fn) => {
    fn('woot')
  })
})
```
