# 浏览器处理二进制数据

## ArrayBuffer

二进制数据缓冲区，不能直接操作，只能通过DataView来间接操作

```javascript
//  创建一个容量为8byte的缓冲区
let buffer= new ArrayBuffer(9)
//  把缓冲区派发给操作对象
let view   = new DataView(buffer);

//  对象方法
ArrayBuffer.isView(arg)  // 判断是否是ArrayBuffer
ArrayBuffer.transfer(oldBuffer) // 返回一个新的 ArrayBuffer 对象，其内容取自 oldBuffer 中的数据，并且根据 newByteLength 的大小对数据进行截取或补 0 

//  实例
ArrayBuffer.prototype.byteLength  //  长度
ArrayBuffer.prototype.slice(start,end)  //  截取部分
```

## DataView