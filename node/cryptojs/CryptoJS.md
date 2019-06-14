# CryptoJS

[https://cryptojs.gitbook.io/docs/](https://cryptojs.gitbook.io/docs/)

## 注意点

1. 所有输出默认的转换类型为使用【CryptoJS.enc.Hex】转换

## 支持的加密类型

### 哈希算法

- MD5
- SHA1
- SHA2
- SHA3
- RIPEMD160
- HMAC
- PBKDF2

### 加密算法

- AES
  - [http://www.cnblogs.com/luop/p/4334160.html](http://www.cnblogs.com/luop/p/4334160.html)
  - [https://blog.csdn.net/qq_28205153/article/details/55798628](https://blog.csdn.net/qq_28205153/article/details/55798628)
- DES
- 3DES/TripleDES
- Rabbit
- RC4/RC4Drop

### 支持的模式

[https://www.cnblogs.com/starwolf/p/3365834.html](https://www.cnblogs.com/starwolf/p/3365834.html)

- ECB
  - 不推荐使用
  - Electronic Codebook Book (ECB)
  - 电码本模式
  - 这种模式是将整个明文分成若干段相同的小段，然后对每一小段进行加密。
  - 不需要iv
- CBC
  - 推荐使用
  - 默认的模式
  - Cipher Block Chaining (CBC)
  - 密码分组链接模式
  - 这种模式是先将明文切分成若干小段，然后每一小段与初始块或者上一段的密文段进行异或运算后，再与密钥进行加密
  - 需要iv
- CFB
  - 不推荐使用
  - Cipher FeedBack (CFB)
  - 密码反馈模式
  - 需要iv
- OFB
  - Output FeedBack (OFB)
  - 输出反馈模式
  - 需要iv
- CTR
  - 推荐使用
  - Counter (CTR)
  - 计算器模式
  - 需要iv

### 支持的填充类型

- Pkcs7
  - 默认的类型
- Iso97971
- AnsiiX923
- Iso10126
- ZeroPadding
- NoPadding

### Encoder

String 和 WordArray 互相转换的编码工具。

- Base64
- Hex
- Latin1
- Utf8
- Utf16
- Utf16LE

```javascript
var words = CryptoJS.enc.Base64.parse("SGVsbG8sIFdvcmxkIQ==");
​
var base64 = CryptoJS.enc.Base64.stringify(words);
​
var words = CryptoJS.enc.Latin1.parse("Hello, World!");
​
var latin1 = CryptoJS.enc.Latin1.stringify(words);
​
var words = CryptoJS.enc.Hex.parse("48656c6c6f2c20576f726c6421");
​
var hex = CryptoJS.enc.Hex.stringify(words);
​
var words = CryptoJS.enc.Utf8.parse("𔭢");
​
var utf8 = CryptoJS.enc.Utf8.stringify(words);
​
var words = CryptoJS.enc.Utf16.parse("Hello, World!");
​
var utf16 = CryptoJS.enc.Utf16.stringify(words);
​
var words = CryptoJS.enc.Utf16LE.parse("Hello, World!");
​
var utf16 = CryptoJS.enc.Utf16LE.stringify(words);
```

## 关于输入和输出值

### hash 算法的输入和输出值

> 输入值

hash 算法接收的输入值可以是字符串或类型为【CryptoJS.lib.WordArray】，WordArray 是一个 32 位字的数组。如果输入值是一个字符串，则内部会自动使用【UTF-8】编码转换成 WordArray

```javascript
const CryptoJS = require('crypto-js')

const hash = CryptoJS.MD5('test')

//  等同于
const wordArray = CryptoJS.enc.Utf8.parse('test')
const hash1 = CryptoJS.MD5(wordArray)
```

> 输出值

通过调用 hash 算法得到的输出值还不是字符串，是 WordArray 对象。如果把输出值使用在需要转成字符串的上下文中，会自动使用【CryptoJS.enc.Hex】转换成【十六进制字符串】

```javascript
const hash = CryptoJS.MD5('test')

//  hash是WordArray对象
console.log(hash)
//  hex
console.log(`${hash}`)
//  相当于是调用了以下方法
const hex = hash.toString(CryptoJS.enc.Hex)
```

### 加密算法的输入和输出值

> 输入值

> 输出值

加密算法返回的值是一个 CipherParams 对象：

- ciphertext
  - 密文
  - WordArray 对象
  - 如果转换成字符串，如果没有指定【编码】，默认使用【Hex】
- key
- iv

## 例子

```javascript
//解密方法
function Decrypt(word) {
  let encryptedHexStr = CryptoJS.enc.Hex.parse(word)
  let srcs = CryptoJS.enc.Base64.stringify(encryptedHexStr) //  为什么需要加这行
  let decrypt = CryptoJS.AES.decrypt(srcs, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.NoPadding
  })
  let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8)
  return decryptedStr
}

//加密方法
function Encrypt(word) {
  let srcs = CryptoJS.enc.Utf8.parse(word)
  let encrypted = CryptoJS.AES.encrypt(srcs, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.NoPadding
  })
  return encrypted.ciphertext.toString().toUpperCase()
}
```
