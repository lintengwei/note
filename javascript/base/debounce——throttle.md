# 函数的防抖和节流

防抖与节流函数是一种最常用的 高频触发优化方式，能对性能有较大的帮助

## 防抖

将多次高频操作优化为只在最后一次执行，通常使用的场景是：用户输入，只需再输入完成后做一次输入校验即可。

```javascript
/**
 * 防抖函数，保证函数在在多频触发的时候只会执行一次
 * @param {Function} fn 执行函数
 * @param {Number} wait  执行时间
 * @param {Boolean} immediate  第一次是否立即执行
 */
function debounce(fn, wait, immediate) {
  let timer
  return function() {
    let args = arguments
    let context = this
    if (immediate && !timer) {
      fn.apply(context, args)
    }
    timer && clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(context, args)
    }, wait)
  }
}
```

## 节流

将多次高频操作优化为只在最后一次执行，通常使用的场景是：用户输入，只需再输入完成后做一次输入校验即可。

```javascript
/**
 * 节流函数，保证函数在指定时间间隔内只会执行一次
 * @param {Function} fn 执行函数
 * @param {Number} wait  执行时间
 * @param {Boolean} immediate  第一次是否立即执行
 */
function throttle(fn, wait, immediate) {
  let timer = undefined
  let callNow = immediate
  return function() {
    let args = arguments
    let context = this

    if (callNow) {
      fn.apply(context, args)
      callNow = false
    }

    if (!timer) {
      timer = setTimeout(() => {
        fn.apply(context, args)
        timer = undefined
      }, wait)
    }
  }
}
```