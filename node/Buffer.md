# Buffer

## methods

### 类方法

### 实例方法

- write(string[, offset[, length]][, encoding])
  - 写入字节到buffer
  - 如果没设置偏移量，会从0位开始写入
- buf.toJSON()
  -  当字符串化一个 Buffer 实例时，JSON.stringify() 会隐式地调用该函数。
  -  格式：：：{"type":"Buffer","data":[1,2,3,4,5]}
- buf.toString([encoding[, start[, end]]])

## string_decoder

### buf.toString(encoding)

全部字节按照编码方式转码成字符串

### decoder.write(buf)

会将多余的字节保存在内部的buffer，确保输出的字符是正确编码的，不会出现乱码的问题？