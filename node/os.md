# os

## endianness

返回计算机二进制编译环境的字节顺序.'BE'为大端,'LE'为小端.主要是针对多字节的写入顺序.单字节不需要考虑.
小端字节顺序,低位写在低地址位,高位写在高地址为.大端正好相反

```javascript
const os = require('os')

const end = os.endianness()

const uInt16 = new uInt16Array()
uInt16[0] = 5000

//  16进制表示为 1388 13在高位  88在低位
const buf = Buffer.from(uInt16)

// 如果当前的字节顺序是 'LE',则返回 <Buffer 88 13> 读取是从低位开始读的
// 如果当前的字节顺序是 'BE',则返回 <Buffer 13 88> 读取是从低位开始读的
```
