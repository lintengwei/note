# Buffer

## 方法

### 类方法

#### 分配 Buffer

- Buffer.from(string p1,string p2)
  - p1 待编码的字符
  - p2 编码格式,可选,默认是 utf8
    - ascii
    - utf8
    - hex
      - 每两位作为一个字节编码,如果不满足十六进制会终端编码,只返回前面的编码部分
- Buffer.from([int,int,int])
- Buffer.from(ArrayBuffer buf)
- Buffer.alloc(size)
- Buffer.allocUnsafe(size)

### 工具方法

- Buffer.byteLength(string p1,string encoding)
  - 获取字符指定编码的字节数
  - encoding 指定编码,默认 utf8
- Buffer.compare(Buffer buf1,Buffer buf2)
- Buffer.concat(Array<Buffer> list,int len)
  - list Buffer 数组
  - len 合并的总长度,如果 list 的长度大于 len,会截取

### 实例方法

- write(string[, offset[, length]][, encoding])
  - 写入字节到 buffer
  - 如果没设置偏移量，会从 0 位开始写入
- buf.toJSON()
  - 当字符串化一个 Buffer 实例时，JSON.stringify() 会隐式地调用该函数。
  - 格式：：：{"type":"Buffer","data":[1,2,3,4,5]}
- buf.toString([encoding[, start[, end]]])

## string_decoder

### buf.toString(encoding)

全部字节按照编码方式转码成字符串

### decoder.write(buf)

会将多余的字节保存在内部的 buffer，确保输出的字符是正确编码的，不会出现乱码的问题？
