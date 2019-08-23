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
  //  finally的返回值不会作为返回Promise的value，then的才会！！！！
})
```

## Promise对象上的方法

```javascript
/**
 * 方法接收一个可遍历的参数，例如数组（可以是任何元素，如果不是Promise对象，则会把该值作为Promise的返回值存入结果数组），返回一个Promise对象。当所有的Promise方法成功返回之后才会调用then方法（并且值是按照iterable的顺序存入数组的），当有一个Promise发生错误，则会立即调用catch方法。
 * */
Promise.all(iterable<any>)

/**
 * 当iterable参数里的任意一个子promise被成功或失败后，父promise马上也会用子promise的成功返回值或失败详情作为参数调用父promise绑定的相应句柄，并返回该promise对象
 * */
Promise.race(iterable<any>)

/**
 * 返回一个状态由给定value决定的Promise对象。如果该值是thenable(即，带有then方法的对象)，返回的Promise对象的最终状态由then方法执行决定；否则的话(该value为空，基本类型或 * 者不带then方法的对象),返回的Promise对象状态为fulfilled，并且将该value传递给对应的then方法。通常而言，如果你不知道一个值是否是Promise对象，使用Promise.resolve(value)* 来返回一个Promise对象,这样就能将该value以Promise对象形式使用。
 * */
Promise.resolve(value)

/**
 * 返回一个状态为失败的Promise对象，并将给定的失败信息传递给对应的处理方法
 * */
Promise.reject(resean)
```

## 原型方法

```javascript
/**
 * 可以定义两个方法，一个是resolve，一个是reject调用的，或者使用catch方法来捕获错误
 * then方法的返回值会作为Promise的value
 * */
Promise.prototype.then(onFulfilled, onRejected)

/**
 * Promise的错误处理方法
 * */
Promise.prototype.catch(onRejected)

/**
 * 最后都会调用的方法
 * */
Promise.prototype.finally(onFinally)
```