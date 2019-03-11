- [前端模块化规范](#%E5%89%8D%E7%AB%AF%E6%A8%A1%E5%9D%97%E5%8C%96%E8%A7%84%E8%8C%83)
  - [commonjs](#commonjs)
  - [amd](#amd)
  - [cmd](#cmd)
  - [standard](#standard)

# 前端模块化规范

[https://www.cnblogs.com/chenguangliang/p/5856701.html](https://www.cnblogs.com/chenguangliang/p/5856701.html)

## commonjs

模块的【同步】加载。主要是用在服务端模块加载。在 commonjs 规范中：

1. 一个文件就是一个模块，拥有独立的作用于
2. 通过 require 加载
3. 通过 exports 和 module.exports 暴露接口和变量

> 加载过程

1. 读取并且执行指定的 js 文件，然后查看该文件时候有通过 exports 或者 module.exports 导出接口，如果没有发现，则报错；
2. 模块内的 exports：为了方便，node 为每个模块提供一个 exports 变量，其指向 module.exports，相当于在模块头部加了这句话：var exports = module.exports，在对外输出时，可以给 exports 对象添加方法，PS：不能直接赋值（因为这样就切断了 exports 和 module.exports 的联系）;

```javascript
//  modlue  func.sj
exports.add=function(a,b){
  return a+b
}
exports.sub=(a,b){
  return a-b
}
//  app.js
//  同步加载，只有当模块加载完成之后才能执行模块暴露的方法
const func=require('func.js')
func.add(2,3)
```

## amd

由于 commonjs 的同步加载机制不适用于浏览器。因为同步加载机制只有当模块加载完成才能执行后续操作，假设某个页面有好几个模块需要加载，就必须串行请求模块，并且需要串行的加载执行，这样会导致浏览器响应缓慢的问题。因此 amd 便产生了，其允许并行的加载不同的模块，等分模块加载完成之后执行回调，并且把模块作为参数传给回调函数，之后便可以在回调中执行需要的操作。

## cmd

## standard

浏览器的标准实现。静态模块。export 与 import 都有一个重要的限制，那就是它们必须被用在其他语句或表达式的外部，而不能使用在 if 等代码块内部。原因之一是模块语法需要让 JS 能静态判断需要导出什么，正因为此，你只能在模块的顶级作用域使用 export 与 import。
