- [mocha](#mocha)
  - [简单用法](#%E7%AE%80%E5%8D%95%E7%94%A8%E6%B3%95)
  - [接口](#%E6%8E%A5%E5%8F%A3)
  - [命令行参数](#%E5%91%BD%E4%BB%A4%E8%A1%8C%E5%8F%82%E6%95%B0)
    - [基本参数](#%E5%9F%BA%E6%9C%AC%E5%8F%82%E6%95%B0)
    - [配置参数](#%E9%85%8D%E7%BD%AE%E5%8F%82%E6%95%B0)
    - [文件参数](#%E6%96%87%E4%BB%B6%E5%8F%82%E6%95%B0)
    - [其他参数](#%E5%85%B6%E4%BB%96%E5%8F%82%E6%95%B0)
  - [注意点](#%E6%B3%A8%E6%84%8F%E7%82%B9)

# mocha

[https://mochajs.org/](https://mochajs.org/)
配合断言库使用来实现测试。可以使用三方的断言库。

- should
  - [docs](https://github.com/shouldjs/should.js)
- better-assert
  - [docs](https://github.com/visionmedia/better-assert)
- jest
  - [docs](https://github.com/facebook/jest#readme)
  - jest 内置的断言库
    - jest-extended
      - [docs](https://github.com/jest-community/jest-extended)
- unexpected
  - [docs](https://github.com/unexpectedjs/unexpected#readme)
- chai
  - [docs](http://chaijs.com)

## 简单用法

```javascript
//  创建test文件夹，创建index.js文件
const assert = require('assert')

//  同步调用
describe('Array', function() {
  describe('#indexOf', function() {
    it('应该返回-1，如果没有找到元素', function() {
      assert([1, 2, 3].indexOf(4), -1)
    })
  })
})

//  异步调用
descirbe('测试异步',()=>{
  if('异步测试不抛出错误',done=>{
    setTimeout(()=>{
      //  不抛出错误
      done()
      //  抛出错误
      done(new Error('error'))
    },1000)
  })
})

//  配合Promise使用
describe('test promise', function() {
  it('not throw error', function() {
    //  需要返回一个Promise
    //  并且返回Promise和done不能同时调用
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        reject('抛出错误')
      }, 1000)
    })
  })
})

//  配合await async使用
//  修改默认超时时间，默认是2000ms
function tt() {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve('done')
    }, 5000)
  })
}

describe('test async', function() {
  it('not throw error', async function() {
    let s = await tt()
    assert.deepStrictEqual(s, '33')
  })
})

//  如果使用箭头函数，不可以在回调内部通过this来访问mocha的上下文
describe('message',()=>{
  //  为一个空的Object
  console.log(this)
})
//  不使用箭头函数，可以访问this，是【Suite】的对象
describe('message',function(){
  //  this instanceOf Suite
  console.log(this)
})

//  钩子函数
//  如果想要有更好的显示错误信息，给钩子函数一个名称会是一个比较好的做法
//  钩子函数也支持异步调用，同it方法一样，会在回调的方法里面传递done参数，让测试者决定调用的时机来反馈钩子结束

//  可以创建根级的钩子函数，在每个文件的最外层声明函数，钩子函数也可以放在最外层？？
describe('utils', () => {


  it('test', () => {})

  it('test2', () => {})

  it('test3', () => {})

//  会在test，test2，test3测试开始之前执行
  before(() => {
    console.log('before all test')
  })

//  会在所有测试结束之后执行
  after(() => {
    console.log('after all test')
  })

//  会在每个测试开始之前执行
  beforeEach(() => {
    console.log('before each test')
  })

// 会在每个测试结束之后执行
  afterEach(() => {
    console.log('after each test')
  })
})

//  如果没有个it函数传递回调函数，那么这个测试会被标识为一个挂起状态【pending】
describe('utils',()=>{
  //  测试结果为 挂起
  it('#parse()')
})

//  可以通过【only】来设置测试哪些东西
//  可以用在 describe、it上面
describe('utils', () => {
  //  如果在某个describe里面有使用only，那么只有使用only的测试会被执行，其他会跳过,可以多次使用only
  describe.only('ttt', () => {
    it('test', () => {})
    it('test2', async () => {
      let res = await tt()
      assert.deepEqual(res, 'done')
    })
    it('test3', () => {})
  })

  describe('ffff', () => {
    it('test4', () => {})
  })


//  在it上使用
  describe('ttt', () => {
    it('test', () => {})

    //  只有这个会被执行，其他都不会被执行，包括下面的ffff同样不会执行！！！
    it.only('test2', async () => {
      let res = await tt()
      assert.deepEqual(res, 'done')
    })
    it('test3', () => {})
  })

  describe('ffff', () => {
    it('test4', () => {})
  })
})

//  通过使用【skip】来告诉mocha跳过哪些测试
//  当需要根据不用的环境来测试的时候可以通过选择【only】和【skip】来达到自定义测试项目的目的
describe('utils', () => {
  describe('ttt', () => {
    it('test', () => {})

    //  会跳过，最终为【pending】状态
    it.skip('test2', async () => {
      let res = await tt()
      assert.deepEqual(res, 'done')
    })

    //  会跳过，最终为【pending】状态
    it.skip('test3', () => {})
  })

  describe('ffff', () => {
    it('test4', () => {})
  })
})

//  当某个测试不通过的时候，可以通过【retires】设置重试的次数，钩子函数【beforeEach】和【afterEach】会多次调用
let a = 1
describe('utils', function() {
  this.retries(5)
  beforeEach(function() {
    //  总共调用10次，包括正常的2次和重复的3+5=8次
    console.log(a++ + 'ddddddddddddddddddd')
  })
  describe('test', function() {
    //  这个失败会重试五次，因为外层设置了5次
    it('ssss', function() {
      assert.deepEqual(0, 1)
    })
  })
  describe('test1', function() {
    //  这个只会重试三次
    it('fail', function() {
      this.retries(3)
      assert.deepEqual(0, 1)
    })
  })
})

//  测试的时长
//  mocha会显示每个测试的测试时长。默认的阀值是【75ms】，当时长小于二分之一的阀值是，表示测试是快速的，当大于二分之一小于阀值时，表示这是一个正常状态。当大于阀值时，表示这个测试是有问题的，耗时严重。
function tt() {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve('done')
    }, 1500)
  })
}
describe('utils', function() {
  describe('test', function() {
    //  这条测试时长为1500ms，严重超过75ms，因此被认为是慢的测试
    it('ssss', async function() {
      let s = await tt()
      assert.deepEqual(s, 'done')
    })
  })
  describe('test1', function() {
    //  这条是快的测试
    it('fail', function() {
      assert.deepEqual(1, 1)
    })
  })
})
```

```json
//  会寻找test文件夹的index.js文件
{
  "scripts": {
    "test": "mocha"
  }
}
```

## 接口

> describe(message:string,callback:Function)

描述方法。调用这个方法，如果内部有嵌套的调用，会生成一个层级的描述结构，例如测试一个工具类，类的方法等。外层放的是工具类的命名空间，内层描述的是具体的方法。
例如上面的例子，会生成如下层级结构

- Array
  - #indexOf
    - [true] 应该返回-1，如果没有找到元素的

> it(message:string,callback:Function(done:Function(done:Function)))

- 参数
  - message
    - 描述字符
  - callback
    - 测试函数
  - done
    - 对于异步调用的方法，可以通过调用 done 来通知 mocha 完成测试。==调用两次会报错==
    - 可以接收一个 falsy 的值，或者 Error 对象来反馈测试失败

也是描述方法。和 describe 的区别是什么？看文档是用在方法上，可能会捕获错误？

## 命令行参数

可以通过命令行传递参数和配置文件的方式来启动 Mocha。

如果是以配置文件的方式来启动 mocha，如果按照一下方式命名，mocha 会自动搜索，如果使用的是其他的名称，需要指定文件的位置。可以通过一下几种方式：

- javascript
  - .mocharc.js
- json
  - .mocharc.json
- yaml
  - .mocharc.yaml
- package.json
  - 创建【mocha】属性，并且通过 package 来传递 package.json 的路径

### 基本参数

> --timeout -t

设置测试超时时间

> --solw -s

设置测试时间的阀值。默认是 75ms

> --retries

全局设置测试失败的重试次数。默认不重试

### 配置参数

> --config

设置配置文件路径。mocha 可以通过配置文件来输出配置信息。同 webpack 一样。文件名是什么？

> --package

如果在 packagej.json 里面设置的配置，需要指定 package.json 的路径

### 文件参数

> --watch -w

开启观察者模式，当文件修改之后，会自动测试

### 其他参数

> --help -h
> --version -v

## 注意点

1. 只有当 describe 的回调函数中传入参数 done 才会被当作异步测试，异步测试如果没有调用 done 方法，会被挂起（【pending】状态），并且只能调用一次，多次调用会报错。
