# BOM 的历史历史对象

- go(int)
- back()
- forward()
- pushState(state,title,url)
- replaceState(state,title,url)

> 使用 history.pushState() 可以改变 referrer，它在用户发送 XMLHttpRequest 请求时在 HTTP 头部使用，改变 state 后创建的 XMLHttpRequest 对象的 referrer 都会被改变。因为 referrer 是标识创建 XMLHttpRequest 对象时 this 所代表的 window 对象中 document 的 URL。

历史记录入栈的时候，window 不会触发 popstate 事件，只有在出栈的时候才会触发，并且事件对象会带上入栈时候的 state 对象

```javascript
let history = window.history;
let state = {
  cnt: 1
};
//  注意 pushState() 绝对不会触发 hashchange 事件，即使新的URL与旧的URL仅哈希不同也是如此。
history.pushState(state, '', 'test.html?a=2');
//  用户点击后退或者调用history.back()方法
window.addEventListener('popstate', e => {
  //  state=e.state
  //  注意这里返回的state是当前history的state对象，如果这个对象不是pushState创建的，该对象为null
});
```

当 hash 改变的时候，window 对想会触发【hashchange】事件

```javascript
window.addEventListener('hashchange', function(e) {
  //  e.newUrl  to
  //  e.oldUrl  from
});
```
