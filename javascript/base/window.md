# window对象

window对象方法

## base-64

```javascript
//  从 String 对象中创建一个 base-64 编码的 ASCII 字符串，其中字符串中的每个字符都被视为一个二进制数据字节。
//   由于这个函数将每个字符视为二进制数据的字节，而不管实际组成字符的字节数是多少，所以如果任何字符的码位超出 0x00 ~ 0xFF 范围，则会引发 InvalidCharacterError 异常
//  可以使用 encodeURIComponent()转码
let encodedData = window.btoa("Hello, world");  //  编码
let decodedData = window.atob(encodedData);    // 解码
```

## url-encode

[docs](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/encodeURI)

- 保留字符
  - ; , / ? : @ & = + $
- 非转义字符
  - 字母 数字 - _ . ! ~ * ' ( )
- 数字符号
  - (#)

> encodeURI和decodeURIComponent的区别

encodeURI 会替换所有的字符，但不包括保留字符，非转义字符，数字符号
encodeURIComponent 转义除了非转义字符的所有字符


```javascript
window.encodeURI(str)
window.decodeURI(str)
window.encodeURIComponent(str)
window.decodeURIComponent(str)
```

