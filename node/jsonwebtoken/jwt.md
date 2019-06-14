# jsonwebtoken使用

[git depository](https://github.com/auth0/node-jsonwebtoken)

## 概念

[http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html](http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)

### 组成

- Header（头部）
- Payload（载荷）
- Signature（签名）

Header.Payload.Signature，拼接的字符串颁发给客户端。

> Header

json对象，描述jwt的元数据，用于验证的时候使用，使用的时候用Base64编码。具体包括有：

- alg
  - 签名使用的算法
- typ
  - 表示token的类型，jwt令牌统一写成JWT

> Payload

json对象，实际传输的数据，使用的时候用Base64编码。其中包括jwt规范中的几个字段之外，我们还可以添加自定义字段，用于后续的认证工作。其中jwt规定了7个必须字段：

- iss（issuer）
  - 签发人
- exp（expiration time）
  - 过期时间
- sub（subject）
  - 主题
- aud（audience）
  - 受众
- nbf（not before）
  - 生效时间
- iat（issued at）
  - 签发时间
- jti（jwt id）
  - 编号

> Signature

Header.Payload 合并的字符串的签名。

### 注意点

1. 因为生成的token并没有进行任何的加密处理，因此传输的数据可以通过Base64解码，相当于是明文传输，可以考虑使用https进行传输
2. 不同于session，jsonwebtoken使用的是客户端存储，而后通过请求携带token，服务器完成验证的方式。所以，token一旦颁发，没有办法使其过期，除非服务端有额外的逻辑处理。

## 安装

```bat
rem yarn
yarn add jsonwebtoken
rem npm
cnpm install --save jsonwebtoken
```

## 使用

> 创建

当创建token的时候，可以选择参数。其中，expiresIn，notBefore，audience，issuer，subject没有默认值，并且这些值可以通过在payload指定exp，nbf，aud，iss，sub，但是不能同时指定。值的定义参考上文。

- jwt.sign(payload,secret[,options,callback])
  - payload
    - 需要传输的数据
  - secret
    - 加密密钥
  - options
    - 加密选项
      - algorithm
        - 加密算法
      - expiresIn
        - 过期时间，相对时间
      - notBefore
      - audience
      - issuer
      - jwtid
      - subject
      - noTimestamp
      - header
      - keyid
      - mutatePayload
  - callback
  - return
    - 如果提供了callback，token会作为参数传给回调，异步
    - 如果没有callback，则同步返回生成token

```javascript
const jwt=require('jsonwebtoken')

const token=jwt.sign(payload,secretKey[,options,callback])
```

> 验证

- jwt.verify(token,secret[,options,callback])
  - token
    - 颁发的token
  - secret
    - 签名的密钥
  - options
    - algorithm
      - 签名算法
    - audience
    - complete
      - boolean 
      - 是否返回完整的，包含header，payload，signature
    - issuer
    - ignoreExpiration
      - boolean
      - 如果为true，不验证时效性
    - subject
    - clockTolerance
    - maxAge
    - clockTimestamp
    - nonce
  - callback
    - 如果提供了callback，则会异步的抛出错误或者返回payload
    - 如果没有提供callback，则会同步的返回payoad或者抛出错误

```javascript
const jwt=require('jsonwebtoken')

jwt.verify(token,secret)
```

> 解码

- jwt.decode(token[,options])
  - token
    - 颁发的token
  - options
    - json
      - boolean
      - 强制使用JSON.parse解码payload
    - complete
      - boolean
      - 是否返回解码的payload和header

> 可能抛出的错误

- TokenExpiredError
  - 如果token过期会抛出错误
- JsonWebTokenError
  - 当生成token的我时候，参数不正确会抛出错误
- NotBeforeError
  - 如果当前时间早于NBF声明，则抛出错误。

> 如何刷新token


> 支持的算法

- HS256
  - HMAC using SHA-256 hash algorithm
- HS384
  - HMAC using SHA-384 hash algorithm
- HS512
  - HMAC using SHA-512 hash algorithm
- RS256
  - RSASSA-PKCS1-v1_5 using SHA-256 hash algorithm
- RS384
  - RSASSA-PKCS1-v1_5 using SHA-384 hash algorithm
- RS512
  - RSASSA-PKCS1-v1_5 using SHA-512 hash algorithm
- PS256
  - RSASSA-PSS using SHA-256 hash algorithm (only node ^6.12.0 OR >=8.0.0)
- PS384
  - RSASSA-PSS using SHA-384 hash algorithm (only node ^6.12.0 OR >=8.0.0)
- PS512
  - RSASSA-PSS using SHA-512 hash algorithm (only node ^6.12.0 OR >=8.0.0)
- ES256
  - ECDSA using P-256 curve and SHA-256 hash algorithm
- ES384
  - ECDSA using P-384 curve and SHA-256 hash algorithm
- ES512
  - ECDSA using P-512 curve and SHA-256 hash algorithm
- none