# axios使用

```bat
rem install
cnpm install --save axios
rem yarn
yarn add axios
```

```javascript
const axios=require('axios').default

//  method get
axios.get('http://www.baidu.com',{
  //  options
}).then(res=>{
  //  response
  let response={
    data,   //  响应携带的主体
    status, //  响应的状态码
    statusText, //  响应的状态说明
    headers,  //  响应的头部信息
    config  //  请求的选项信息
  }
}).catch(e=>{
  //  catch error
})
```

## 配置选项

```javascript
const options={
  url:'/',  //  请求的地址
  method:'get', //  请求的方法
  baseURL:'http://www.baidu.com/',  //  请求的根，会和url拼接成完整的请求地址
  transformRequest(data,headers){
    //  可以根据需要对请求的数据进行转换

    return String|Buffer|ArrayBuffer|FormData|Stream
  },
  transformResponse(data,headers){
    //  对返回的数据进行转换
    return data
  },
  headers:{
    //  请求的头部
  },
  params:{
    //  查询参数
  },
  data:{
    //  载荷参数
  },  
  paramsSerializer(params){
    //  序列化请求参数
  },
  timeout:3000, //  请求超时时间，超过该值，axios会抛出请求超时的错误
  withCredentials:false,  //  是否携带cookie等信息？
  auth:{    //  基本认证
    username:'',
    password:''
  },
  responseType:'json',  //  返回的主体格式
  responseEncoding:'utf8',  //  返回的主体编码
  xsrfCookieName:'',  //  
  xsrfHeaderName:'',  //  
  onUploadProgress(progressEvent){
    //  
  },
  onDownloadProgress(progressEvent){
    //  
  },
  maxContentLength:2000,  //  响应主体最大的字节数
  validateStatus(status){
    //  对响应的状态是抛出错误还是返回做出判断，默认的如下
    return status>=200&&status<300 
  },  
  maxRedirects:4, //  最大的重定向数
  socketPath:null,  //  
  httpAgent:null,
  httosAgent:null,
  proxy:{
    //  代理服务配置
    host:'127.0.0.1',
    port:9999,
    auth:{
      username:'',
      password:''
    }
  },
  //  用于取消请求的令牌
  cancelToken:new CancelToken(cancel=>{

  })
}
```

## 实例方法

request为基本方法，所有其他实例方法都是通过request来实现的

> request<options>
> get<url,options>
> post<url,data,options>
> delete
> head
> options
> put
> patch
> getUri

## 其他使用

1. 可以配置前置和后置中间件

```javascript
const axios=require('axios').default

//  前置
axios.interceptors.request.use(function resolveHandler(config){

  return config //  必须要返回config给下一个pormise
},function rejectHandler(e){
  Promise.reject(e)
})

//  后置
axios.interceptors.response.use(function resolveHandler(response){

  return response
},function rejectHandler(e){
  return Promise.reject(e)
})
```

1. 取消请求

```javascript
const axios=require('axios').default
const CancelToken=axios.CancelToken
const source=CancelToken.source() //  生成一个特定的请求源包含唯一的token

axios.get('http://www.baidu.com',{
  cancelToken:source.token
}).then(res=>{

}).catch(e=>{
  //  当cancel方法被调用的时候，会抛出错误，并且携带取消原因
})

source.cancel('cancel reason')  //  取消请求，并且指定取消原因
```