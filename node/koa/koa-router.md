# koa-router

[https://github.com/alexmingoia/koa-router#readme](https://github.com/alexmingoia/koa-router#readme)

本质上还是定义中间件，通过next来链式调用中间件。koa-router的主要作用就是把路由导航到不同的中间件去做具体的处理。当重复定义多个相同的路由的时候，如果前面的中间件没有调用next来调用后面的中间件，那么后面的不会执行，就跟koa的中间件是一样的模式。

## 安装

```bat
cnpm install --save koa-router
yarn add koa-router
rem 路径编译是通过 path-to-regext包来实现的
cnpm install --save path-to-regexp
```

## 基本使用

```javascript
const Router = require('koa-router')
const Koa = require('koa')
//  路径编译
const pathToRegexp=require('path-to-regexp')
const app = new Koa()
const route = new Router({
  prefix: '/api' //  定义所有路由的前缀
})

/**
 * @param path String 路由
 * @param keys  Array 存放参数的数组
 * @param options Object  配置选项
 * @param options.sensitive Boolean 是否大小写敏感
 * @param options.strict Boolean  是否严格
 * @param options.end Boolean 是否从结尾开始匹配
 * @param options.start Boolean
 * @param options.delimiter String
 * @param options.endsWith 
 * @param options.whitelist
 * @return regexp = /^\/user\/([^\/]+?)\/?$/i
 * @return keys = [{ name: 'id', prefix: '/', delimiter: '/', optional: false, repeat: false, pattern: '[^\\/]+?' }] 
 * */
const regexp=pathToRegexp('/user/:id',keys,options)

//  定义路由
route.get('/', (ctx, next) => {
  //  ctx 请求的上下文
})
router.post('/', (ctx, next) => {})
router.put('/', (ctx, next) => {})
router.del('/', (ctx, next) => {})
router.patch('/', (ctx, next) => {})
router.del('/', (ctx, next) => {})

//  中间件
async function auth(ctx, next) {}
router.use(auth(),auth2()) //  作用于所有路由
router.use('/user/:id', auth()) //  值作用于/user/:id路由
router.use(['/user','/order'],auth())  //  用于多个路由的中间件

//  如果定义路由 /user/:id
//  可以对id参数进行校验
//  会把定义的中间件放进路由数组的中间件的合适位置
router.use('/user/:id', auth()) 
router.param('id',(id,ctx,next)=>{
  await next()
})

app.use(router.routes()).use(router.allowedMethods()) //  options的响应
app.listen(999)
```

## 源码

```javascript
//  定义路由
methods.forEach(function(method) {
  
  /**
   * @param name :?String 指定路由名称
   * @param path String  指定匹配路径
   * @param middleware...  指定中间件，可以指定多个
   * */
  Router.prototype[method] = function(name, path, middleware) {
    var middleware

    //  把多个中间件组成数组传递个route对象
    if (typeof path === 'string' || path instanceof RegExp) {
      middleware = Array.prototype.slice.call(arguments, 2)
    } else {
      middleware = Array.prototype.slice.call(arguments, 1)
      path = name
      name = null
    }

    this.register(path, [method], middleware, {
      name: name
    })

    return this
  }
})

//  注册路由
Router.prototype.register = function(path, methods, middleware, opts) {
  opts = opts || {}

  var router = this
  var stack = this.stack

  // support array of paths
  if (Array.isArray(path)) {
    path.forEach(function(p) {
      router.register.call(router, p, methods, middleware, opts)
    })

    return this
  }

  // create route
  //  新建一个路由对象
  /**
   * @param path 路由
   * @param methods 该路由支持的方法
   * @param middleware  该路由对应的控制器
   * @param options
   * @options options.end 
   * @options options.name   路由名称
   * @options options.sensitive
   * @options options.strict  严格模式
   * @options options.prefix  路由前缀  
   * @options options.ignoreCatures
   * */
  var route = new Layer(path, methods, middleware, {
    end: opts.end === false ? opts.end : true,
    name: opts.name,
    sensitive: opts.sensitive || this.opts.sensitive || false,
    strict: opts.strict || this.opts.strict || false,
    prefix: opts.prefix || this.opts.prefix || '',
    ignoreCaptures: opts.ignoreCaptures
  })

  if (this.opts.prefix) {
    route.setPrefix(this.opts.prefix)
  }

  // add parameter middleware
  Object.keys(this.params).forEach(function(param) {
    route.param(param, this.params[param])
  }, this)

  stack.push(route) //  存储所有定义的路由，重复定义的路由也会存储进去

  return route
}

//  路由中间件
Router.prototype.routes = Router.prototype.middleware = function() {
  var router = this

  //  方法返回路由派发器
  var dispatch = function dispatch(ctx, next) {
    debug('%s %s', ctx.method, ctx.path)

    //  获取当前请求的路劲
    var path = router.opts.routerPath || ctx.routerPath || ctx.path
    //  根据请求路径和请求方法获取匹配的路由
    var matched = router.match(path, ctx.method)
    var layerChain, layer, i

    //  ctx.matched是在哪里赋值的？
    //  有什么意思？
    if (ctx.matched) {
      ctx.matched.push.apply(ctx.matched, matched.path)
    } else {
      ctx.matched = matched.path
    }

    // 暴露router对象给context
    ctx.router = router

    //  如果没有匹配到合适的路由 ，直接调用下一个中间件
    if (!matched.route) return next()
    
    //  满足条件的route对象数组
    var matchedLayers = matched.pathAndMethod   

    //  
    var mostSpecificLayer = matchedLayers[matchedLayers.length - 1] 
    ctx._matchedRoute = mostSpecificLayer.path
    if (mostSpecificLayer.name) {
      ctx._matchedRouteName = mostSpecificLayer.name
    }
    
    //  因为每个路由可以设置多个中间件，按照定义的顺序依次调用
    //  所有会把满足条件的所有route对象定义的中间件组成调用链
    layerChain = matchedLayers.reduce(function(memo, layer) {
      memo.push(function(ctx, next) {
        ctx.captures = layer.captures(path, ctx.captures)
        ctx.params = layer.params(path, ctx.captures, ctx.params)
        ctx.routerName = layer.name
        return next()
      })
      return memo.concat(layer.stack)
    }, [])

    return compose(layerChain)(ctx, next)
  }

  dispatch.router = this

  return dispatch
}

/**
 * @param path  当前请求路径
 * @param method  当前请求方法
 * */
Router.prototype.match = function(path, method) {
  var layers = this.stack 
  var layer
  var matched = {
    path: [],
    pathAndMethod: [],
    route: false
  }

  //  遍历所有定义的route对象查找符合条件的route
  for (var len = layers.length, i = 0; i < len; i++) {
    layer = layers[i]

    debug('test %s %s', layer.path, layer.regexp)

    //  查看当前route对象是否符合要求
    if (layer.match(path)) {
      matched.path.push(layer)  //  满足条件，推进path数组

      //  任何数字按位取反之后 ~x==-(x+1)
      //  ~layer.methods.indexOf(method)  如果没找到匹配的，返回为0，找到匹配的返回值小于0
      //  如果方法数组为空，则默认允许所有方法
      //  如果请求方法匹配，则把匹配的route对象推进数组
      if (layer.methods.length === 0 || ~layer.methods.indexOf(method)) {
        matched.pathAndMethod.push(layer)
        if (layer.methods.length) matched.route = true
      }
    }
  }

  return matched
}

/**
 * 定义中间件
 * 如果没有指定路劲，则默认所有路由都使用该中间件
 * @param path String|Array 指定路由使用该中间件，可以是字符串表示单个路由，或者是数组表示多个路由
 * @param middleware... 可以传递多个中间件，会按照定义顺序依次执行
 * */
Router.prototype.use = function() {
  var router = this
  var middleware = Array.prototype.slice.call(arguments)
  var path

  // support array of paths
  if (Array.isArray(middleware[0]) && typeof middleware[0][0] === 'string') {
    middleware[0].forEach(function(p) {
      router.use.apply(router, [p].concat(middleware.slice(1)))
    })

    return this
  }

  var hasPath = typeof middleware[0] === 'string'
  if (hasPath) {
    path = middleware.shift()
  }
  
  middleware.forEach(function(m) {
    //  是哪里来的属性
    if (m.router) {
      m.router.stack.forEach(function(nestedLayer) {
        if (path) nestedLayer.setPrefix(path)
        if (router.opts.prefix) nestedLayer.setPrefix(router.opts.prefix)
        router.stack.push(nestedLayer)
      })

      if (router.params) {
        Object.keys(router.params).forEach(function(key) {
          m.router.param(key, router.params[key])
        })
      }
    } else {
      //  重新注册一个新的路由对象？
      router.register(path || '(.*)', [], m, {
        end: false,
        ignoreCaptures: !hasPath
      })
    }
  })

  return this
}

/**
 * @name 对路由的参数进行前置操作 router.js
 * @param param 参数名称
 * @param middleware  定义的操作方法
 * */
Router.prototype.param = function(param, middleware) {
  this.params[param] = middleware
  this.stack.forEach(function(route) {
    route.param(param, middleware)
  })
  return this
}

route.param('id',(id,ctx,next)=>{
  //  第一个参数为为对应的值
  //  ctx 请求上下文
  //  next 调用下一个
})

/**
 * @name route对象的param方法，用于对参数进行必要的操作
 * @param param 参数名称
 * @param fn  定义的操作函数
 * */
Layer.prototype.param = function(param, fn) {
  var stack = this.stack

  //  根据route对象的路径生成的参数集合
  //  [{ name: 'id', prefix: '/', delimiter: '/', optional: false, repeat: false, pattern: '[^\\/]+?' }]
  var params = this.paramNames  
  var middleware = function(ctx, next) {
    return fn.call(this, ctx.params[param], ctx, next)
  }
  middleware.param = param

  var names = params.map(function(p) {
    return p.name
  })

  var x = names.indexOf(param)
  if (x > -1) {
    // iterate through the stack, to figure out where to place the handler fn
    stack.some(function(fn, i) {
      // param handlers are always first, so when we find an fn w/o a param property, stop here
      // if the param handler at this part of the stack comes after the one we are adding, stop here
      if (!fn.param || names.indexOf(fn.param) > x) {
        // inject this param handler right before the current item
        stack.splice(i, 0, middleware)
        return true // then break the loop
      }
    })
  }

  return this
}
```