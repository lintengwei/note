# debug调试包

```bat
rem install
cnpm install --save debug
rem yarn
yarna add debug
```

```javascript
//  socket.js
//  通过传递一个名称来控制显示语法
//  debug会把传递的参数作为控制开关
//  通过设置环境变量来控制显示哪些debug模块
//  set debug=* 显示全部
//  set debug=socket  只显示socket模块的调试信息
const debug=rquire('debug')('socket:')
const debug2=require('debug')('http:')

//  output socket:test
debug('test')
//  output http:test
debug2('test')

//  set debug=http: 只会输出htto:标识
//  set debug=* 输出全部
```