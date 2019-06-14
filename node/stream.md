- [stream](#stream)
  - [基本](#%E5%9F%BA%E6%9C%AC)
  - [Writeable](#writeable)
    - [属性](#%E5%B1%9E%E6%80%A7)
    - [事件](#%E4%BA%8B%E4%BB%B6)
      - [close 事件](#close-%E4%BA%8B%E4%BB%B6)
      - [drain 事件](#drain-%E4%BA%8B%E4%BB%B6)
      - [error 事件](#error-%E4%BA%8B%E4%BB%B6)
      - [finish](#finish)
      - [pipe](#pipe)
    - [方法](#%E6%96%B9%E6%B3%95)
      - [cork](#cork)
      - [destroy](#destroy)
      - [end](#end)
      - [write](#write)
      - [setDefaultEncoding](#setdefaultencoding)
      - [uncork](#uncork)
  - [Readable](#readable)
    - [事件](#%E4%BA%8B%E4%BB%B6-1)
      - [data](#data)
      - [readable](#readable)
  - [实现可读流和可写流](#%E5%AE%9E%E7%8E%B0%E5%8F%AF%E8%AF%BB%E6%B5%81%E5%92%8C%E5%8F%AF%E5%86%99%E6%B5%81)
    - [实现可写流](#%E5%AE%9E%E7%8E%B0%E5%8F%AF%E5%86%99%E6%B5%81)
    - [实现可读流](#%E5%AE%9E%E7%8E%B0%E5%8F%AF%E8%AF%BB%E6%B5%81)
    - [实现双工流](#%E5%AE%9E%E7%8E%B0%E5%8F%8C%E5%B7%A5%E6%B5%81)
    - [实现转换流](#%E5%AE%9E%E7%8E%B0%E8%BD%AC%E6%8D%A2%E6%B5%81)

# stream

## 基本

四种基本流类型：

1. Writeable
   1. 可写流
   2. 例如 fs.createWriteStream()
2. Readable
   1. 可读流
   2. 例如 fs.createReadStream()
3. Duplex
   1. 可读可写流
   2. net.socket
4. Transform
   1. 在读写过程中可以修改或者转换数据的 Duplex 流
   2. zlib.createDeflate()

可读流和可写流内部会存在一个缓冲区，用于存储数据，缓冲区的大小是根据生成流对象方法的参数【highWaterMark】来指定的，如果没有指定，默认是 64k，可以通过 writeabel.writeableBuffer 或 readable.readableBuffer 来获取。
对于可读流来说，当内部缓冲区存储的数据到达指定的阀值的时候，流会暂停从底层获取数据来填充内部缓冲区，直到流被消费后，会重新激活该行为。
对于可写流来说，当内部缓冲区到达指定的阀值的时候，再次调用 write 方法写入数据会返回 false 值，表示当前的内部存储区已经满了，不能在继续写入数据。
可读流和可写流默认的缓冲区大小都是 ==16kb==.其子类可以改写这个值

## Writeable

数据可以写入的地方

### 属性

- writeable
- writeableHighWaterMark
- writeableLength

### 事件

#### close 事件

当流或其底层资源（比如文件描述符）被关闭时触发。 表明不会再触发其他事件，也不会再发生操作。
==不是所有可写流都会触发 'close' 事件。==

#### drain 事件

如果调用 write 方法返回 false 表示内部存储到达阀值不可再写，之后如果可写流重新可写的时候会触发 drain 事件

#### error 事件

当写入数据发生错误的时候会触发

#### finish

调用 stream.end() 且缓冲数据都已传给底层系统之后触发。

#### pipe

当在可读流上调用 stream.pipe() 时触发。

```javascript
const fs = require('fs')

const rs = fs.createReadStream('a.txt')
const ws = fs.createWriteStream('b.txt')

ws.on('pipe', () => {})

rs.pipe(ws)
```

### 方法

#### cork

当写入大量小块数据到流时，内部缓冲可能失效，从而导致性能下降， writable.cork() 主要用于避免这种情况。 对于这种情况，实现了 writable.\_writev() 的流可以用更优的方式对写入的数据进行缓冲。

#### destroy

销毁流。

#### end

结束可写流。

#### write

写入可写流。

#### setDefaultEncoding

设置流编码形式。

#### uncork

将调用 stream.cork() 后缓冲的所有数据输出到目标。如果一个流上多次调用 writable.cork()，则必须调用同样次数的 writable.uncork() 才能输出缓冲的数据。

## Readable

### 事件

#### data

#### readable

两种读取模式:

- 流动模式(flowing)
  - ==数据自动从底层系统获取==,通过 EventEmitter 接口尽可能块的提供给应用程序
- 暂停模式(paused)
  - 显示的调用 strea.read() 方法读取数据

所有的可读流都开始于暂停模式,通过以下方法切换到流模式:

1. 添加'data'事件监听
2. 调用 stream.resume()
3. 调用 stream.pipe()

可读流切换回暂停流:

1. 如果没有孤傲到目标,调用 stream.pause()
2. 如果有管道目标,则通过调用 stream.pipe()方法移出所有管道目标.

添加 'readable' 事件句柄会使流自动停止流动，并通过 readable.read() 消费数据。 如果 'readable' 事件句柄被移除，且存在 'data' 事件句柄，则流会再次开始流动。

当流中有数据可读的时候会触发.初始化的时候会先从从底层读取低于 hightWaterMark 的数据来填充内部缓冲区.

1. 当消费者调用 read 方法之后,会在什么时候继续读取数据来填充呢?

当内部缓冲区的大小小于 highWaterMark 的值的时候,可读流的会从底层读取 highWaterMark 大小的字节来填充内部缓冲区,并且触发[readable]事件,直到下次内部缓冲区的大小小于 highWaterMark 的值的时候会再次读取,直到所有字节被消费.
又或者是当可读流的总字节数小于 highWaterMark 的值的时候,在第一次消费之后,底层也会去读取数据,虽然返回是空的,但是还是回触发[readable]事件?

## 实现可读流和可写流

### 实现可写流

必须实现的方法[_write(chunk,encoding,callback)],并且必须在最后面调用 callback

```javascript
const { Writable } = require('stream')

class TestWrieable extends Writable {
  _write(chunks, encoding, callback) {
    //  当调用之后,chunks会被置空
    //  读取writeableLength是0?
    let err = new Error('error')
    if (err) {
      //  调用callback,表示已经处理过chunks了,并且writeableLength会减去chunks的长度?
      //   调用callback之后会触发事件[drain],通知可以继续写数据到流.
      callback(err)
    } else {
      callback()
    }
  }

  //  实现这个方法,会在流调用 stream.end()的时候,发布[finish]事件
  _final(callback) {
    //  必须调用callback()才会触发事件
    callback()
  }
}
```

### 实现可读流

必须实现方法[_read(size)]

当 readable.\_read() 被调用时，如果从资源读取到数据，则需要开始使用 this.push(dataChunk) 推送数据到读取队列。 \_read() 应该持续从资源读取数据并推送数据，直到 readable.push() 返回 false。 若想再次调用 \_read() 方法，则需要恢复推送数据到队列。

一旦 readable.\_read() 被调用，它不会被再次调用，除非调用了 readable.push()。 这是为了确保 readable.\_read() 在同步执行时只会被调用一次。

```javascript
//  官方例子
const { Readable } = require('stream')

class Counter extends Readable {
  constructor(opt) {
    super(opt)
    this._max = 1000000
    this._index = 1
  }

  _read() {
    const i = this._index++
    if (i > this._max) this.push(null)
    else {
      const str = String(i)
      const buf = Buffer.from(str, 'ascii')
      //  每次读取会读取一个数字 buf的长度
      //  通过调用push(Buffer)来返回stream.read()调用的字节
      //  如果方法内没有调用push,则返回为null
      this.push(buf)
    }
  }
}

const counter = new Counter()
let num = 10
while (num--) {
  console.log(counter.read().toString()) //  输出1-99的数字
}
```

### 实现双工流

内置可读流和可写流是相互独立的.

### 实现转换流

继承自双工流,但是重写了 stream.\_write()和 stream.\_read()方法,并且可读流和可写流是相关联的.
