# lodashapi

## Lang

- \_.isError(value)
  - 判断 value 的值是否是错误对象

## Array

- \_.chunk(array,[size=1])
  - 把数组分割成更小的数组，小数组长度由 size 决定
- \_.compact(array)
  - 过滤掉数组中 falsey 的值 . false,0,'',null,undefined,NaN
- \_.concat(array,[...values])
  - 合并数组，所有参数的第一层都会展开
- \_.findIndex(array,[values])
  - 查找某个元素

## Util

- \_.attempt(func,[args])
  - 尝试调用函数，返回函数返回值或者错误对象

## Function

- \_.after(n,func)
  - 返回一个函数，当这个函数被调用超过或者等于 n 次后，之后每次调用都会执行回调函数 func
- \_.before(n,func)
  - 返回一个函数，当这个函数调用次数小于 n 的时候，会执行回调函数 func，之后再次调用不会执行 func
- \_.bind(func,thisArg,...args)
  - 给函数 func 绑定一个 this 的上下文，并且把[...args]作为参数传给 func
  - 可以使用 [_] 作为参数占位符，方便参数的多变性
- \_.curry(func,[n=func.arguments.length])
  - 返回一个函数，并且参数长度为 n，如果为没有指定 n，则长度为 func 的参数长度
  - 当调用的时候传递的参数长度为 f,且 [f<n] ，则返回一个函数，参数长度为[n-f]，依次类推，知道满足参数长度为 func 需要的参数的时候才会执行
