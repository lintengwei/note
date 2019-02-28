# child_process

node 衍生子进程的包，其中 spawn，exec，execFile,fork 都属于创建异步进程，spawnSync,execSync,execFileSync 属于创建同步进程。调用穿件异步进程的方法，会返回一个子进程的描述对象，通过该对象可以和子进程通信。而同步进程会阻塞主进程的时间轮训，只有当子进程完成之后，返回子进程的处理结果等。其中 spawn,spawnSync 都是底层实现，其他几个方法都是他们的特定实例

```javascript
const { spawnSync, spawn } = require('child_process')

//  创建异步进程
const subProcess = spawn('node subProcess.js', { shell: true })

//  可以通过返回的对象去监听子进程的输入输出
//  其中 stdout，stdin，stderr都是流对象
//  stdout可读流，stdin可写流，stderr可读流
subProcess.stdout.on('data', data => {
  //  buffer
  console.log(data)
})
subProcess.stderr.on('data', data => {})
subProcess.stdin.on('data', data => {})

//  创建同步进程
const subProcess = spawnSync('node subProcess.js', { shell: true })
```

## 衍生子进程的方法

### 衍生异步进程

- sapwn(command[,args][,options])
  - 建立 shell，在 shell 中运行命令
  - command<string>
    - 要运行的命令
  - args<String[]>
    - 字符串参数列表
  - options
    - cwd
    - env
    - argv0
    - stdio
    - detached
    - uid
    - gid
    - shell
    - windowsHide
- fork(modulePath[,args][,options])
  - modulePath
    - 子进程执行的模块名称
  - args
    - 参数名
  - options
    - 其余配置参数
  - fork 方法会衍生出一个 node 子进程，并且和父进程通过 ipc 通道进行连接，其他 spawn，exec 等方法不能通过 ipc 通道通信吗？
- exec(command[,args][,options])
  - 建立 shell，在 shell 中运行命令
- execFile(modulePath[,args][,options])
  - 不建立 shell，直接运行命令？
  - 在 windows 下不能运行？

```javascript
const child_process=require('child_process);
const path=require('path);
const subProcess=child_process.fork(path.resolve(__dirname,'./childProcess.js'));
subProcess.on('message',data=>{
  console.log('父进程收到子进程的数据',data)
})

//  父进程向子进程发送数据
subProcess.send({
  data:134
})

//  childProcess.js
process.on('message',data=>{
  console.log('子进程收到父进程的数据',data)
})

//  子进程向父进程发送数据
process.send({
  data:456
})
```

### 衍生同步进程

- spawnSync
  - return
    - pid <number> 子进程的 pid。
    - output <Array> stdio 输出的结果数组。
    - stdout <Buffer> | <string> output[1] 的内容。
    - stderr <Buffer> | <string> output[2] 的内容。
    - status <number> 子进程的退出码。
    - signal <string> 用于终止子进程的信号。
    - error <Error> 如果子进程失败或超时的错误对象。
- execSync
  - <Buffer> | <string> 命令的 stdout。
- execFileSync
  - <Buffer> | <string> 命令的 stdout。
