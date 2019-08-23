# async_hook使用

[https://www.jianshu.com/p/4a568dac41ed](https://www.jianshu.com/p/4a568dac41ed)

注意点：

1. 任何异步的操作都会触发钩子函数
   1. fs的异步，console.log等方法都会触发，每个异步操作触发一次钩子函数

```javascript
const fs = require('fs')
const async_hook = require('async_hooks')

let instance = async_hook.createHook({
  init(asyncId, type, triggerAsyncId) {
    fs.writeFileSync(1, `init-${asyncId}---${type}\n`)
  },
  before(asyncId) {
    fs.writeFileSync(1, `before-${asyncId}\n`)
  },
  after(asyncId) {
    fs.writeFileSync(1, `after-${asyncId} \n`)
  },
  destroy(asyncId) {
    fs.writeFileSync(1, `destroy-${asyncId}\n`)
  }
})

instance.enable()

fs.open('a.txt', 'r', null, fd => {
  fs.writeFileSync(1, `callbackcallbackcallbackcallback---${fd}\n`)
})

```