- [内置对象](#%e5%86%85%e7%bd%ae%e5%af%b9%e8%b1%a1)
  - [Object](#object)
    - [属性描述符](#%e5%b1%9e%e6%80%a7%e6%8f%8f%e8%bf%b0%e7%ac%a6)
    - [instanceof and isPropertyOf](#instanceof-and-ispropertyof)
  - [Regexp](#regexp)
  - [Function](#function)
  - [Array](#array)
  - [反射](#%e5%8f%8d%e5%b0%84)
    - [Proxy 对象](#proxy-%e5%af%b9%e8%b1%a1)
    - [Reflect 对象](#reflect-%e5%af%b9%e8%b1%a1)
  - [EventTarget](#eventtarget)
  - [闭包](#%e9%97%ad%e5%8c%85)
  - [Map,Set,WeakMap,WeakSet](#mapsetweakmapweakset)
  - [注意点](#%e6%b3%a8%e6%84%8f%e7%82%b9)

# 内置对象

## Object

### 属性描述符

如果是对象字面量创建的对象 writable configurable enumberable 三个属性都是 true
如果是 Object.defainedProperty(obj,property,{
value,
[writable default false][cinfigurable default false]

[enumberable default false]
})

数据描述符和存取描述符均具有以下可选键值：

> configurable
> 当且仅当该属性的 configurable 为 true 时，该属性描述符才能够被改变，同时该属性也能从对应的对象上被删除。默认为 false。

> enumerable
> 当且仅当该属性的 enumerable 为 true 时，该属性才能够出现在对象的枚举属性中。默认为 false。

> Writable 属性
> 当且仅当该属性的 Writable 为 true 时，该属性才能够出现在对象的枚举属性中。默认为 false

数据描述符同时具有以下可选键值：

> value
> 该属性对应的值。可以是任何有效的 JavaScript 值（数值，对象，函数等）。默认为 undefined。
> writable
> 当且仅当该属性的 writable 为 true 时，value 才能被赋值运算符改变。默认为 false。
> 存取描述符同时具有以下可选键值：

> get
> 一个给属性提供 getter 的方法，如果没有 getter 则为 undefined。当访问该属性时，该方法会被执行，方法执行时没有参数传入，但是会传入 this 对象（由于继承关系，这里的 this 并不一定是定义该属性的对象）。
> 默认为 undefined。
> set
> 一个给属性提供 setter 的方法，如果没有 setter 则为 undefined。当属性值修改时，触发执行该方法。该方法将接受唯一参数，即该属性新的参数值。
> 默认为 undefined。

```javascript {.line-number}

/**
 * 对象拷贝
 * 只是线拷贝，如果对象的属性也是一个对象，则两个对象的属性指向同一个对象
 * */
Object.assign(target,source1[,source2])
```

### instanceof and isPropertyOf

isPrototypeOf() 与 instanceof 运算符不同。在表达式 "object instanceof AFunction"中，object 的原型链是针对 AFunction.prototype 进行检查的，而不是针对 AFunction 本身。

```javascript
let a = []
Array.prototype.isPrototype(a) // true
a instanceof Array // true
```

## Regexp

- (?:y)
  - 匹配 y 但是不捕获 y，会消耗掉匹配
- x(?=y)
  - 只有当 x 紧跟 y 的时候匹配 x，但是不会消耗 y
- x(?!y)
  - 只有 x 后面不是 y 的时候才会匹配 x，但是不会消耗 y

## Function

- call(thisArg[, arg1[, arg2[, ...]]])
  - 立即执行
- apply(thisArg[,...args])
  - 立即执行
- bind(thisArg[, arg1[, arg2[, ...]]])
  - 返回函数，调用执行

```javascript
//  去除数组元素的首尾空格
//  调用call方法，并且把call的this上下文指向trim
function get(arr) {
  return arr.map(Function.prototype.call, String.prototype.trim)
}
function get(arr) {
  return arr.map(function(...args) {
    return String.prototype.trim.call(...args)
  })
}
function get(arr) {
  return arr.map(String.prototype.trim.call)
}

// return 'A'
Function.prototype.call.call(String.prototype.toUpperCase, 'a')
```

## Array

- Array.from(arg)
  - arg 类数组，可迭代对象，返回新的数组
  - Array.from('abc') => ['a','b','c']
  - 'abc'.split('') => ['a','b','c']
- Array.isArray(arg)
  - 判断是否是数组
  - arg instanceof Array
- Array.of(element0[, element1[, ...[, elementN]]])
  - 创建新的数组
  - Array.of(4) => [4]
  - new Array(4) => [undefined,undefined,undefined,undefined]
- Array.prototype.reduce(func(lastValue,ele,index,arrayThis){},initValue)
  - 从左往右迭代数组，可以设置一个初始值，返回 lastValue

```javascript
//  嵌套数组展开
function expandArray(arr) {
  return arr.reduce((last, ele, i) => {
    return last.concat(Array.isArray(ele) ? expandArray(ele) : ele)
  }, [])
}

function expandArray(arr) {
  return arr.reduce(function(last, ele, index) {
    if (Array.isArray(ele)) {
      ele = expandArray(ele)
    }
    return last.concat(ele)
  }, [])
}
```

## 反射

### Proxy 对象

实例方法：

- apply
  - 拦截目标函数的调用
  - 参数
    - target
      - 目标对象
    - thisArg
      - 被调用时的 this 上下文
      - 指的是 proxy 实例被调用时候的上下文环境
    - argumentsList
      - 被调用时的参数列表
- construct
  - 拦截 new 操作
- deleteProperty
  - 拦截 delete 操作
  - 参数
    - target
    - key
- get
  - 拦截获取值操作
  - 参数
    - target
    - key
    - receiver
      - proxy 或者子类
- set
  - 拦截设置操作
    - 参数
      - target
      - key
      - value
- getOwnPropertyDescriptor
- getPrototypeOf
- has
- isEWxtensible
- ownKeys
- preventExtensions
- setPrototypeOf

```javascript
//  apply
```

### Reflect 对象

同将原来在 Object 上部分静态方法，放在 Reflect 上，便于维护。
都是静态方法，在命名控件 Reflect 下，调用参照 Proxy

## EventTarget

- addEventListener(type:string,handler:Function(event?:Event),Object?:{once?:Boolean,capture?:Boolean,passive?:Boolean})
  - 添加事件侦听
    - type
      - 事件类型
    - handler
      - 事件触发回调
    - Object
      - 事件描述符
        - once
          - 表示该事件只会触发一次，触发之后移出
        - capture
          - 表示 listener 会在该类型的事件捕获阶段传播到该 EventTarget 时触发。
          - 事件会通过传递的方式已知传到 dom 树的底层，如果底层没有添加对应的事件，并且高层又有对应的事件，那么该事件会在冒泡的对应的 node 的时候触发，如果把这个参数设置为【true】，那么事件会在底层之前拦截此事件，就是说和原来的触发方向相反。
        - passive
          - 设置为 true 时，表示 listener 永远不会调用 preventDefault()。如果 listener 仍然调用了这个函数，客户端将会忽略它并抛出一个控制台警告。
- removeEventListener(type:string,handler?:Function)
  - 移出时间侦听
    - type
      - 事件类型
    - handler
      - 可选参数，如果没有这个参数，表示移出指定事件的所有侦听器，否则只移出指定的 handler
- dispatchEvent(type:string)
  - 触发事件侦听
    - type
      - 事件类型

## 闭包

函数定义的局部变量被函数之外的对象引用，就会生成闭包

```javascript
//  example1
function tt(i){
  return ()=>console.log(++i)
}

let aa=tt(2)  //  生成i的闭包

// example2
function Test(i) {
  //  因为参数i被原型方法test引用，所有会生成一个闭包，而原型方法被所有实例共享
  //  当创建最后一个实例的时候复写了该方法，此时闭包的i=3，所以所有实例输出的都是3
  Test.prototype.test = () => {
    console.log(i)
  }
}

let list = [1, 2, 3].map(ele => {
  return new Test(ele)
})

//  输出结果都是3
list.forEach(ele => {
  ele.test()
})
```

## Map,Set,WeakMap,WeakSet

Map的实现是通过两个内置数组来存放键（可以是任意值）和值的（根据存入的顺序依次放入数组），当赋值和取出的时候都需要遍历整个数组来查找，时间复杂度为O(n)，并且是强引用。所以当键或者值没有在其他地方引用的时候，垃圾回收不会处理Map中的元素，可能会造成内存泄漏。

WeakMap区别于Map的主要是键（只能是对象）和值都是弱引用，当其他地方没有引用的时候，垃圾回收会处理过期的对象，因此存在不稳定，所以键值不能遍历？

## 注意点

1. 关于Array的方法

```javascript
//  Array.prototype.sort(func:Function)
//  sort方法如果没有传递参数，则会把所有数组元素先转换为字符串之后根据unicode编码的位置来排序的
//  如果传递了参数，则会根据函数的返回值来确定元素的顺序，
//  func(a)-func(b)<0  a在b之前
//  func(a)-func(b)=0  ab位置不变
//  func(a)-func(b)>0  a在b之后
let a=[9,81,5]
a.sort()  //  [5,81,9]  为传递参数，按照系统默认的
a.sort((a,b)=>a-b) // [5,9,81]
```

2. 全局方法

```javascript
//  parseInt(input,radix)，input输入值，radix进制2-36，默认为10
//  如果被解析参数的第一个字符无法被转化成数值类型，则返回 NaN
//  在基数为 undefined，或者基数为 0 或者没有指定的情况下，JavaScript 作如下处理：
//  1.如果字符串 string 以"0x"或者"0X"开头, 则基数是16 (16进制).
//  2.如果字符串 string 以"0"开头, 基数是8（八进制）或者10（十进制），那么具体是哪个基数由实现环境决定。ECMAScript 5 规定使用10，但是并不是所有的浏览器都遵循这个规定。因此，永远都要明确给出radix参数的值。
//  3.如果字符串 string 以其它任何值开头，则基数是10 (十进制)。
```