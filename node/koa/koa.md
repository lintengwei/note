# koa

支持ES2015以及更高的版本，如async语法。node版本7.6之后才实现async方法，之前的版本需要安装babel

## 安装

```bat
rem install
cnpm install --save koa
rem yarn
yarn add koa
```

## 简单使用

```javascript
const Koa=require('koa')
const app=new Koa()

//  app根据定义的顺序依次执行中间件方法，会先执行先定义的中间件，然后等待后续的中间件执行完返回来继续执行先前的中间件。
app.use(async (ctx,next)=>{
  let starttime=Date.now()  //  请求开始时间
  await next()
  let endtime=Date.now()  //  请求结束时间
  ctx.set('response-time',endtime-starttime)
})

//  middleware
app.use(async (ctx,next)=>{
  ctx.body='hello world'
})

app.listen(999,()=>{
  console.log(`server is listenning on port ${999}`)
})
//  和以下方式等价
const http=require('http')
const Koa=require('koa')
const app=new Koa()
http.createServer(app.callback()).listen(999)
```

## 实例方法

```javascript
const Koa=require('koa')
const app=new Koa()

//  返回用于处理请求的回调函数  http.createServer(app.callback())
app.callback()

//  koa中间件 ，必须为async函数
//  参数为
app.use(asyncFunction)
app.use(async (ctx,next)=>{
  //  ctx为当前请求生成的上下文
  //  next为调用下一个中间件的方法，是一个异步函数
  await next()
})
```

## 实例属性

```javascript
const Koa=require('koa')
const app=new Koa()

//  设置cookie的密钥
//  这些被传递给 KeyGrip，但是你也可以传递你自己的 KeyGrip 实例。
koa.keys
koa.keys=['secret1','secret1']

//  是ctx的原型，可以用过在其上设置属性来定义全局使用的方法
app.context
app.context.utils=utils
```

## Context

```javascript
koa.use(async (ctx,next)=>{

  ctx.req   //  node的request对象
  ctx.res   //  node的response对象
  ctx.request //  koa定义的request对象
  ctx.response  //  koa定义的response对象
  ctx.state //  推荐的命名空间，用于传递中间件和前端视图
  ctx.app //  app实例的引用
  ctx.app.emit(eventName)  //  application实现了EventEmitter，可以全局发出事件
  ctx.cookies.get(name[,options]) //  获取指定的cookie，options的可选参数是【signed】
  /**
   * options==
   * maxAge
   * signed
   * expires
   * path
   * domain
   * secure
   * httpOnly
   * overwrite
   * */
  ctx.cookies.set(name,value[,options]) //  设置cookies
  ctx.throw([status][,msg][,properties])  //  抛出一个status默认为500的错误

  ctx.assert(value[,status][,msg][,properties]) //  
  ctx.response  //  通过设置该值为false，可以禁止koa处理请求，自定义处理方式

})
```

## Request

```javascript
const request=ctx.reqeuest

request.header  //  请求的头部信息
request.header={}  //  设置头部信息
request.headers //  header的别名
request.headers={}
request.method  //  请求方法
request.length  //  content-length
request.url //  请求url
request.originalUrl //  
request.origin  //  域名
request.href  //  完整的url
request.path
request.querystring //  根据？获取原始查询字符串
request.search  //  querystring别名
request.hostname  //  
request.type  //  content-type
request.charest //  context-type:text/plain;charest=utf8
request.query //  查询字符串生成的对象
//  检查请求缓存是否新鲜
request.fresh 
request.ip  //  ip
request.subdomains  
request.is(types...)
```

## Response

```javascript
const response=ctx.response

response.header //  响应请求头
response.socket //  请求的套接字
response.status //  设置响应状态
response.message  //  设置状态信息
response.length //  content-length
//  设置返回的数据主体
//  string|buffer|Stream|Object|Array|null
//  如果置为string，则content-type默认为text/plain|text/html
//  如果值为Buffer content-type为application/octet-stream
//  如果值为Stream  content-type为application/octet-stream
//  值为Object  content-type为application.json
response.body=  
response.get(field)   //  获取指定的头部
response.set(field,value) //  设置头部信息
response.append(field,value)  //  追加头部
response.set({
  field1:value1,
  field2:value2
})
response.remove(field)  //  移出指定的头部信息
response.type //  content-type
response.redirect(url[,alt])  //  执行302重定向
response.lastmodified= //  设置 last-modified
response.etag=  //  设置etag
response.vary(field)  
response.flushHeaders() //  刷新设置的表头，并开始主体
```

## 源码

```javascript
function compose (middleware) {
  if (!Array.isArray(middleware)) throw new TypeError('Middleware stack must be an array!')
  for (const fn of middleware) {
    if (typeof fn !== 'function') throw new TypeError('Middleware must be composed of functions!')
  }

  /**
   * @param {Object} context
   * @return {Promise}
   * @api public
   */

  return function (context, next) {
    // last called middleware #
    let index = -1
    return dispatch(0)  //  首先取出第一个中间件
    //  定义取出中间件的函数  i表示第几个中间件
    function dispatch (i) {
      if (i <= index) return Promise.reject(new Error('next() called multiple times'))
      index = i
      let fn = middleware[i]
      if (i === middleware.length) fn = next
      if (!fn) return Promise.resolve()
      try {
        //  把运行下一个中间件的函数作为参数传给当前中间件
        //  把中间件的返回值通过Promise.resolve(returnValue)返回给上一级
        //  上一个通过 let ss=await next() 获取下一级的返回值
        return Promise.resolve(fn(context, dispatch.bind(null, i + 1)));  
      } catch (err) {
        return Promise.reject(err)
      }
    }
  }
}
  /**
   * Return a request handler callback
   * for node's native http server.
   *
   * @return {Function}
   * @api public
   */

  callback() {
    //  middleware数组，按照定义的顺寻依次存入
    //  每次调用都会把middleware的第一个中间件提出来，并且把ctx，next作为参数传入
    //  其中调用next函数，会执行下一个中间件，知道所有中间件执行完，会调用response方法来响应客户端
    const fn = compose(this.middleware);

    if (!this.listenerCount('error')) this.on('error', this.onerror);

    const handleRequest = (req, res) => {
      const ctx = this.createContext(req, res);
      return this.handleRequest(ctx, fn);
    };

    return handleRequest;
  }

  /**
   * Handle request in callback.
   *
   * @api private
   */

  handleRequest(ctx, fnMiddleware) {
    const res = ctx.res;
    res.statusCode = 404;
    const onerror = err => ctx.onerror(err);
    const handleResponse = () => respond(ctx);
    onFinished(res, onerror);
    return fnMiddleware(ctx).then(handleResponse).catch(onerror);
  }
```