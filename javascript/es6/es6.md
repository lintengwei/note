# es6

## generator

当作状态机来使用。

1. 定义状态生成器
   1. function \* funcName()
2. 调用 funcName 来返回状态机【stateFunc】
3. 调用状态机的【next】方法来返回当前状态
   1. 调用 next 会执行函数，知道遇到【yield】字段会停止执行，如果没有该字段，会执行到函数结束，并且返回函数的返回值，如果没有返回值，则返回 undefined

```javascript
function* stateManager() {
  yield 'start'
  yield 'loading'
  return 'end'
}

let stateFunc = stateManager()

stateFunc.next() //  return {value:'start',done:false}
stateFunc.next() //  return {value:'loading',done:false}
stateFunc.next() //  return {value:'end',done:true}
```

### Generator 构造器的方法

- next(value?:any)
  - next 方法可以带一个参数，重置上一次【yield】返回的值为【value】
- return(value?:any)
  - 结束状态机，并且把该值作为 value 返回，之后在继续调用 next 方法，返回{value:undefined,done:true}
- throw(exception:Error)
  - 像生成器抛出错误，会被 try-catch 捕获

### Generator 的 FAQ

1. 不能把 yield 的值赋值给一个变量吗？

不能要把 yield 返回的值赋值赋值给一个变量，这样操作不会生效。如果需要指定值，则需要使用【next】方法，并且传一个值进去作为上一个状态返回的值

```javascript
function* test() {
  let a = 2
  let b = yield a + 1
  let c = yield b + 3
  return c + 3
}

let ss = test()

console.log(ss.next())
console.log(ss.next(-2)) //  此时b=-2
console.log(ss.next(-5)) //  此时c=-5
console.log(ss.next())
```
