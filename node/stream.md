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

## Writeable

数据可以写入的地方

### 属性

- writeable
- writeableHighWaterMark
- writeableLength

### 事件

#### close 事件

当流或其底层资源（比如文件描述符）被关闭时触发。 表明不会再触发其他事件，也不会再发生操作。
不是所有可写流都会触发 'close' 事件。

#### drain 事件

如果调用 write 方法返回 false 表示内部存储到达阀值补课再写，之后如果可写流重新可写的时候会触发 drain 事件

#### error 事件

当写入数据发生错误的时候会触发

#### finish

当主动调用 end 方法的时候会触发 finish 事件

#### pipe

当在可读流调用 pipe 方法的时候会触发 pipe 事件

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
