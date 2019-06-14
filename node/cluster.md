# node 集群

默认情况下,node 引用是单进程单线程的,为了充分利用多核服务的有事,cluster 模块可以帮助我们创建多个进程,并且可以共享端口.工作进程由 child_process.fork()方法创建，因此它们可以使用 IPC 和父进程通信，从而使各进程交替处理连接服务。

例如服务器是 4 核 cpu 的,使用 cluster 模块可以创建一个主进程和四个工作进程,工作进程的调度由主进程负责.

```javascript
const cluster = require('cluster')
const cpus = require('os').cpus().length

//  判断是否是主进程
if (cluster.isMaster) {
  for (let i = 0; i < cpus; i++) {
    cluster.fork() //  开启一个新的node进程,以本文件作为入口
  }
  process.stdin.on('data', data => {
    //  通过监听控制台的输入来调度使用那个工作进程工作
    let s = data.slice(0, 1)
    s = parseInt(s.toString())
    let workers = cluster.workers
    if (s === 0) {
      workers[++s].send(data)
    } else if (s === 1) {
      workers[++s].send(data)
    } else if (s === 2) {
      workers[++s].send(data)
    } else if (s === 3) {
      workers[++s].send(data)
    } else {
      console.log('没有找到', s)
    }
  })
} else {
  //  工作进程执行入口
  console.log(`工作进程${cluster.worker.id}启动`)
  //  监听父进程发送的消息
  cluster.worker.on('message', data => {
    //  data={type:'Buffer',data}
  })
}
```

## 创建子进程

- cluster.fork([env])
  - 增加的进程环境变量
  - 返回
    - cluster.Worker

## 访问 Worker

```javascript
//  在主进程中访问
const cluster = require('cluster')
let workers = cluster.workers //  返回所有工作进程的数组

//  在工作进程中访问
let worker = cluster.worker //  在工作进程中访问返回,在主进程为undefined
```

## Worker

### 事件

- disconnect
