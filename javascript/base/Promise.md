# Promise

一个 Promise有以下几种状态:

+ pending: 初始状态，既不是成功，也不是失败状态。
+ fulfilled: 意味着操作成功完成。
+ rejected: 意味着操作失败。

![show_text](./static/1536649783(1).jpg)

## constructor

```javascript
let a=new Promise((resolve,reject)=>{
  resolve('test')
})
let b=a.then(res=>{

  //  因为 Promise.prototype.then 和  Promise.prototype.catch 方法返回promise 对象， 所以它们可以被链式调用。
  //  返回一个promise 如果没有catch 会抛出异常
  //  可以在最后一个promise对象在捕获错误，之前的promise会抛出给上层处理
  //  如果没有给定返回值 则默认返回undefined 下一个 Promise.then((res)=>) res==='undefined'
}).catch(err=>{

}).finally(_=>{

})
```