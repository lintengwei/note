# request 

[https://github.com/request/request](https://github.com/request/request)

## 基本用法

```javascript
const request=require('request')
const path=require('path')
const fs=require('fs')

//  流，当有响应的时候，会触发response事件
request.get('http://www.baidu.com').on('response',res=>{
  //  res 是全双工流
  //  res.statusCode
  //  res.headers['content-type']
  res.on('data',chunk=>{

  })
  res.on('end',()=>{

  })
}).pipe(fs.createWriteStream(path.resolve(__dirname,'baidu.html')))
.on('error',e=>{
  //  handler error
})
```

## 详解

### Form上传数据

类似于使用浏览器的【FormData】对象进行数据传输

```javascript
//  content-type:application/x-www-form-urlencoded
const request=require('request')

request.post('http://server.com/upload',{
  form:{
    name:'ltw',
    age:44
  }
})
//  or
request.post('http://server.com/upload').form({
  name:'ltw'
})

//  content-type:multipart/form-data
const formData={
  //  simple pairs
  name:'ltw',
  //  files
  //  类似于 <input type='file'  id='img' name='img' />
  img:fs.createReadStream(path.resolve(__dirname,'./images/a.png'))
  //  multipart files
  //  类似于 <input type='file' multipart id='imgs' name='imgs' />
  imgs:[
    fs.createReadStream(path.resolve(__dirname,'./images/a.png')),
    fs.createReadStream(path.resolve(__dirname,'./images/b.png'))
  ]
}
request.post('http://server.com/upload',{
  formData:formData
},(err,resp,bodu)=>{

})
```

### 基本认证

```javascript
const request=require('request')

request.get('http://server.com',{
  auth:{
    user|username:'ltw',
    pass|password:'123',
    sendImmediately:false
  }
})
```

### 添加头部信息

```javascript
const request=require('request')
const options={
  headers:{
    'Cache-Control':'no-store'
  }
}

request.get('http://server.com',options,(err,res,body)=>{

})
```

### TLS/SSL协议

```javascript
const fs = require('fs')
    , path = require('path')
    , certFile = path.resolve(__dirname, 'ssl/client.crt')
    , keyFile = path.resolve(__dirname, 'ssl/client.key')
    , caFile = path.resolve(__dirname, 'ssl/ca.cert.pem')
    , request = require('request');

const options = {
    url: 'https://api.some-server.com/',
    cert: fs.readFileSync(certFile),
    key: fs.readFileSync(keyFile),
    passphrase: 'password',
    ca: fs.readFileSync(caFile)
};

request.get(options);
```

## 选项

- url<String>
  - 请求的url，如果有baseUrl，会拼接在一起形成完成的请求路径
- baseUrl<String>
  - 基础的url
- method<String>
  - 请求的方法，默认是get
- headers<Object>
  - 请求携带的头部信息，默认为空
- qs<Object>
  - 查询参数
- qsParseOptions
  - 查询参数格式化模型
  - Object
    - seq
      - 键值对分隔符
    - eq
      - 键值分隔符
    - options
      - 参数
- qsStringifyOptions<Object>
- useQuerystring<Boolean>
- body<String|Buffer|ReadStream>
  - 如果设置了json格式，则必须是已经序列化的json
- form
  - 以content-type:application/x-www-form-urlencoded类型发送数据
- formData
  - 以content-type:application/multipart-form发送数据
- multipart
- preambleCRLF<Boolean>
  - 在载荷之前添加换行符
- postambleCRLF<Boolean>
  - 在载荷之后添加换行符
- json
  - 以json格式发送和接收数据，会自动解析
- jsonReviver
- jsonReplacer
- auth
  - 基本认证
- oauth
- hawk
- aws
- httpSignature
- followRedirect
- followAllRedirects
- followOriginalHttpMethod
- maxRedirects
- encoding
- gzip
- jar
- agent
  - http.Agent实例
- agentClass
  - 可以指定使用的Agent实现
- agentOptions
  - 传递给Agent的参数
- forever
- pool<Object>
- timeout
  - 超时时间ms
- localAddress
  - 本地地址
- proxy
- strictSSL
- tunnel
- proxyHeaderWhiteList
- proxyHeaderExclusiveList
- time<Boolean>
- har
- callback<Function>
  - 请求回调，可以放在optiosns里面，或者作为最后一个参数穿个request相关方法
  - params
    - error
    - response
    - body

