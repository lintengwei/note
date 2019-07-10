# node 事件循环

[https://nodejs.org/en/docs/guides/event-loop-timers-and-nexttick/](https://nodejs.org/en/docs/guides/event-loop-timers-and-nexttick/)
[https://www.jianshu.com/p/deedcbf68880](https://www.jianshu.com/p/deedcbf68880)
[https://juejin.im/post/5cf25a19f265da1bba58ec43](https://juejin.im/post/5cf25a19f265da1bba58ec43)

   ┌───────────────────────┐
┌─>│        timers         │<————— 执行 setTimeout()、setInterval() 的回调
│  └──────────┬────────────┘
|             |<-- 执行所有 Next Tick Queue 以及 MicroTask Queue 的回调
│  ┌──────────┴────────────┐
│  │     pending callbacks │<————— 执行由上一个 Tick 延迟下来的 I/O 回调（待完善，可忽略）
│  └──────────┬────────────┘
|             |<-- 执行所有 Next Tick Queue 以及 MicroTask Queue 的回调
│  ┌──────────┴────────────┐
│  │     idle, prepare     │<————— 内部调用（可忽略）
│  └──────────┬────────────┘     
|             |<-- 执行所有 Next Tick Queue 以及 MicroTask Queue 的回调
|             |                   ┌───────────────┐
│  ┌──────────┴────────────┐      │   incoming:   │ - (执行几乎所有的回调，除了 close callbacks 以及 timers 调度的回调和 setImmediate() 调度的回调，在恰当的时机将会阻塞在此阶段)
│  │         poll          │<─────┤  connections, │ 
│  └──────────┬────────────┘      │   data, etc.  │ 
│             |                   |               | 
|             |                   └───────────────┘
|             |<-- 执行所有 Next Tick Queue 以及 MicroTask Queue 的回调
|  ┌──────────┴────────────┐      
│  │        check          │<————— setImmediate() 的回调将会在这个阶段执行
│  └──────────┬────────────┘
|             |<-- 执行所有 Next Tick Queue 以及 MicroTask Queue 的回调
│  ┌──────────┴────────────┐
└──┤    close callbacks    │<————— socket.on('close', ...)

其实图中已经画的很明白：
setTimeout/setInterval 属于 timers 类型；
setImmediate 属于 check 类型；
socket 的 close 事件属于 close callbacks 类型；
其他 MacroTask 都属于 poll 类型。
process.nextTick 本质上属于 MicroTask，但是它先于所有其他 MicroTask 执行；
所有 MicroTask 的执行时机，是不同类型 MacroTask 切换的时候。
idle/prepare 仅供内部调用，我们可以忽略。
pending callbacks 不太常见，我们也可以忽略。
所以我们可以按照浏览器的经验得出一个结论：

先执行所有类型为 timers 的 MacroTask，然后执行所有的 MicroTask（注意 NextTick 要优先哦）；
进入 poll 阶段，执行几乎所有 MacroTask，然后执行所有的 MicroTask；
再执行所有类型为 check 的 MacroTask，然后执行所有的 MicroTask；
再执行所有类型为 close callbacks 的 MacroTask，然后执行所有的 MicroTask；
至此，完成一个 Tick，回到 timers 阶段；
……
如此反复，无穷无尽……

> 关于Promise

await 表达式会暂停当前 async function 的执行，等待 Promise 处理完成。若 Promise 正常处理(fulfilled)，其回调的resolve函数参数作为 await 表达式的值，继续执行 async function。
若 Promise 处理异常(rejected)，await 表达式会把 Promise 的异常原因抛出。
另外，如果 await 操作符后的表达式的值不是一个 Promise，则返回该值本身。

当异步函数里面调用await的时候，会先停止执行异步函数之后的代码。返回上一级继续执行同步代码块，当同步代码块执行完之后才会继续执行await后续的代码块。


```javascript
setTimeout(() => {
  console.log('t1')
}, 0)

Promise.resolve(2).then(res => console.log(res))
process.nextTick(() => console.log('next'))

// output
//  next
//  2
//  t1
//  结论是否在开始事件循环之前也会把当前的【microtask】执行


async function async1() {
  //  2. 同步输出
  //  调用异步函数，返回Promise为一个microtask，遵循任务规则
  //  调用await之后，会等待异步任务返回才会继续执行之后的代码
  console.log('async1 start') 
  await async2()  
  console.log('async1 end')
}
async function async2() {
   //  3.同步输出，返回Promise
  console.log('async2')
}
//  1.同步代码
console.log('script start') 
setTimeout(function() {
  // 9. setTimeout1，timers层
  console.log('setTimeout0')  
}, 0) 
setTimeout(function() {
  // 10. setTimeout2，timers层
  console.log('setTimeout3')  
}, 3)
 // 11. check层
setImmediate(() => console.log('setImmediate'))

//  7. 本轮循环执行，位置在Promise之前所有输出会在promise3之前
process.nextTick(() => console.log('nextTick')) 
//  2.执行异步任务
async1()  
new Promise(function(resolve) {
  //  4.同步代码
  console.log('promise1') 
  resolve() 
   //  5.同步带i啊
  console.log('promise2')
}).then(function() {
   // 8. Promise
  console.log('promise3')  
})
//  6.同步代码块结束，开始事件循环，在事件循环之前和各个层级之间都会执行nextTick和microTask
console.log('script end') 

/* *
//  output
script start
async1 start
async2
promise1
promise2
script end
nextTick
promise3
async1 end
setTimeout0
setTimeout3
setImmediate
*/

```


# FAQ

1. 什么时候推退出当前层？

如果当前层的任务队列为空则事件循环移动到下一层，否则是不是会有时间等待阀值或者最大任务数量的限制？