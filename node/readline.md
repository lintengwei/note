- [readline](#readline)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
  - [方法](#%E6%96%B9%E6%B3%95)
    - [readline](#readline-1)
    - [interface 类](#interface-%E7%B1%BB)
      - [事件](#%E4%BA%8B%E4%BB%B6)
      - [方法](#%E6%96%B9%E6%B3%95-1)

# readline

## 基本使用

```javascript
const readline = require('readline')

const rl = readline.createInterface({
  //  指定要读取的输入流
  //  如果input指定的是文件，则会逐行读取文件的内容
  //  如果input指定的是process.stdin，则会监听控制台的输入信息
  input: stream.Readable,
  //  输出流
  //  是把readline.pormpt指定的字符串输出到指定的输出流中
  output: stream.Writeable,
  //  指定提示字符串
  prompt: string
})

rl.on('resume', () => {
  console.log('readline resume')
})

rl.on('pause', () => {
  console.log('pause')
})

rl.on('close', () => {
  console.log('close')
})

rl.on('line', line => {
  console.log('----------------')
  if (line === 'pause') {
    rl.pause()

    setTimeout(() => {
      rl.resume()
    }, 5000)
    return
  }
  if (line === 'exit') {
    rl.close()
    return
  }
  if (line === 'clear') {
    readline.clearLine()
  }
})
```

## 方法

### readline

- readline.clearLine

  - 参数
    - stream<stream.Writeable>
    - dir
      - -1 从光标左边开始
      - 1 从光标右边开始
      - 0 整行

- readline.clearScreenDown(stream)

- readline.cursorTo()

- readline.moveCursor(stream,dx,dy)

### interface 类

#### 事件

- close

当主动调用 rl.close()是会关闭 rl 对象，并且触发 close 事件

- line

每当读取输入流中一行的时候会触发 line 的时间，并且会把行信息作为参数传递给回调函数

- pause

当主动调用 rl.pause()的时候会暂停 rl 的读取，并且触发该事件

- resume

调用 rl.resume()会恢复 rl 的行为，并且触发 resume 事件

#### 方法

- rl.close()

- rl.pause()

- rl.prompt()

- rl.question(query,callback(answer))

- rl.resume()

- rl.write()
