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
  //   默认情况下，子进程的 stdin、 stdout 和 stderr 被重定向到 ChildProcess 对象上的相应 subprocess.stdin、subprocess.stdout 和 subprocess.stderr 流。
  //  通过监听流事件和子进程调用相关的方法可以实现父子进程的通讯
  //  process.stdout and process.stderr 与 Node.js 中其他 streams 在重要的方面有不同:
  //  console.log() 和 console.error() 内部分别是由它们实现的。
  console.log(data)
})
subProcess.stderr.on('data', data => {
  //  启动错误会调用
})
subProcess.stdin.on('data', data => {
  //  子进程的输入流
})

//  创建同步进程
const subProcess = spawnSync('node subProcess.js', { shell: true })
```

## 衍生子进程的方法

### 衍生异步进程

- spawn(command[,args][,options])
  - 建立 shell，在 shell 中运行命令,需要指定 shell 参数来开启!
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
      - 如果为 true,使用系统默认的 shell 新建一个 shell,在 shell 中执行 command,相当于在 shell 中执行命令.
    - windowsHide
      - 是否隐藏 shell 控制台,默认 false???
    - 返回
      - ChildProcess
- fork(modulePath[,args][,options])
  - modulePath
    - 子进程执行的模块名称
  - args
    - 参数名
  - options
    - 其余配置参数
  - 注意点
    - 不和父进程共享内存!!
    - 方法会衍生出一个 node 子进程，并且和父进程通过 ipc 通道进行连接，其他 spawn，exec 等方法不能通过 ipc 通道通信吗？
  - 返回
    - ChildProcess
- exec(command[,args][,options])
  - 建立 shell，在 shell 中运行命令,不需要使用 shell 参数来开启. 默认是开启 shell
  - 返回
    - ChildProcess
- execFile(modulePath[,args][,options])
  - 不建立 shell，直接运行命令？
  - 在 windows 下不能运行？
  - 返回
    - ChildProcess

```javascript
const child_process=require('child_process);
const path=require('path);
const subProcess=child_process.fork(path.resolve(__dirname,'./childProcess.js'));
subProcess.on('message',data=>{
  console.log('父进程收到子进程的数据',data)
})

//  父进程通过调用子进程实例向子进程发送数据
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
  - 返回
    - pid <number> 子进程的 pid。
    - output <Array> stdio 输出的结果数组。
    - stdout <Buffer> | <string> output[1] 的内容。
    - stderr <Buffer> | <string> output[2] 的内容。
    - status <number> 子进程的退出码。
    - signal <string> 用于终止子进程的信号。
    - error <Error> 如果子进程失败或超时的错误对象。
- execSync
  - 返回
    - <Buffer> | <string> 命令的 stdout。
- execFileSync
  - 返回
    - <Buffer> | <string> 命令的 stdout。
