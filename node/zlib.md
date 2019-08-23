# node 的压缩模块 zlib

是转换流的实现.
zlib 模块提供通过 Gzip 和 Deflate/Inflate 实现的压缩功能.zlib 是流对象且实现了 EventEmitter

## 术语

- zip
  - 泛指的是压缩算法
- gzip
  - 是GNUzip的缩写，数据格式
  - 也是使用defalte压缩算法实现？
- deflate
  - 一种无损压缩算法
- inflate
  - deflate的解压算法

## Gzip

- zlib.createGzip(Object?:options)
  - options
    - flush
      - number
    - finishFlush
      - number
    - chunkSize
      - number
    - windowBits
      - number
    - level
      - number
    - memLevel
      - number
    - strategy
      - number
    - dictionary
      - Buffer|DataView|Arraybuffer
    - info
      - boolean

例子:

```javascript
const zlib = require('zlib')
const fs = require('fs')

const rs = fs.createReadStream('a.txt')
const ws = fs.createWriteStream('a.txt.gz')
const gzip = zlib.createGzip()

//  读取a文件并且使用gzip压缩,把压缩的内容放到a.txt.gz
rs.pipe(gzip).pipe(ws)

const bs = fs.createReadStream('a.txt.gz')
const bws = fs.createWriteStream('b.txt')
const unGzip = zlib.createUnzip()

//  读取压缩文件的内容,把内容传给unGzip转换格式
//  再把转换的内容输出到b.txt.gz文件
bs.pipe(unGize).pipe(bws)
```

## Defalte

```javascript
const zlib = require('zlib')
const fs = require('fs')

const rs = fs.createReadStream('a.txt')
const ws = fs.createWriteStream('a.txt.gz')
const gzip = zlib.createDefalte()

//  读取a文件并且使用gzip压缩,把压缩的内容放到a.txt.gz
rs.pipe(gzip).pipe(ws)

const bs = fs.createReadStream('a.txt.gz')
const bws = fs.createWriteStream('b.txt')
const deflate = zlib.createUnzip()

//  读取压缩文件的内容,把内容传给unGzip转换格式
//  再把转换的内容输出到b.txt.gz文件
bs.pipe(deflate).pipe(bws)
```
