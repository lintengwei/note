# source_code

## 加载 vue 框架会直接运行这几个初始化函数

```javascript
//  会执行下列几个函数，完成Vue的全局接口配置，和一些原型方法设置
initGlobalAPI(Vue);
initMixin(Vue);
stateMixin(Vue);
eventsMixin(Vue);
lifecycleMixin(Vue);
renderMixin(Vue);

//  原型方法 _私有方法
Vue.prototype = {
  constructor:Vue,
  FunctionalRenderContext:function(){},
  _init: function(options) {}, //  实例初始化会调用这个方法

  /* 事件相关 */
  $on: function(event, fn) {},
  $once: function(event, fn) {},
  $off: function([event[,fn]]) {},
  $emit: function(event,...args) {},

  /* 侦测 */
  $watch: function(expOrFn,cb,options) {},

  /* 钩子 */
  _update: function(vnode,hydrating) {},
  $forceUpdate: function() {},
  $destory: function() {},

  /* 渲染 */
  _render: function() {},
  $nextTick: function(fn) {}
};

//  Vue

/**
 * 设置响应式data，主要依赖三个类
 * Watcher,Dep,Observe
 * */
/**
 * @param vm vue实例
 * @param expOrFn 需要监听的表达式或者计算属性函数，如 this.a,function(){this.a+1}
 * @param cb 变动回调函数
 * @param options 附加参数 deep=true/false 是否深度观测，immediate=true/false,是否马上观测
 * */
function Watch(vm,expOrFn,cb,options,isRenderWatcher){}

/**
 * Watcher存储类
 * 是一个可以有多个的可观察对象指令订阅它。
 * @api addSub 添加Watcher
 * @api removeSub 移出Watcher
 * @api notify 通知所有Watcher
 * @api depend
 * */
  var uid = 0;

  /**
   * A dep is an observable that can have multiple
   * directives subscribing to it.
   */
  function remove(arr, item) {
    if (arr.length) {
      var index = arr.indexOf(item);
      if (index > -1) {
        return arr.splice(index, 1);
      }
    }
  }

   // 正在评估的当前目标观察者。
   // 这是全局唯一的，因为可能只有一个
   // 观察者随时被评估。
  Dep.target = null;
  var targetStack = [];

  function pushTarget(_target) {
    if (Dep.target) {
      targetStack.push(Dep.target);
    }
    Dep.target = _target;
  }

  function popTarget() {
    Dep.target = targetStack.pop();
  }

  var Dep = function Dep() {
    this.id = uid++;
    this.subs = [];
  };

  Dep.prototype.addSub = function addSub(sub) {
    this.subs.push(sub);
  };

  Dep.prototype.removeSub = function removeSub(sub) {
    remove(this.subs, sub);
  };

  Dep.prototype.depend = function depend() {
    if (Dep.target) {
      Dep.target.addDep(this);
    }
  };

  Dep.prototype.notify = function notify() {
    // stabilize the subscriber list first
    var subs = this.subs.slice();
    for (var i = 0, l = subs.length; i < l; i++) {
      subs[i].update();
    }
  };

/**
 * 对象/数组设置响应式实现类
 * */
function Observe(){}
```

## 构造函数的属性

每个实例都会混合该配置对象，位置/core/config.js.

## 初始化过程

1. 初始化一些配置
2. 初始化事件监听，如果父组件有对子组件监听，在这里完成初始化
3. 初始化渲染函数
4. 执行周期钩子【beforeCreate】
5. 初始化 inject，为什么不是在 provide 之前？
6. 初始化 props 、methods 、 state、computed、watch，props 在 state 之前，因此 state 可以依赖于 props
7. 初始化 provide
8. vue 初始化完成，执行周期钩子【created】
9. 调用原型方法【\$mount】，执行周期钩子【beforeMount】
10. 此时 vue 会获取【el】内部的 html 文本、或者【template】属性的值，优先级【render>template>el】，最终都会转换为 render 函数。
11. 调用 render 函数完成解析，返回的 html 挂载在相应的 dom 下，此时 dom 已经渲染在屏幕，执行周期钩子【mounted】

## 注意点

```javascript
/**
 * 源码中定义了两个这个方法，第一个是挂载，第二个是解析，应该不是同一个人写的。先把第一个$mout赋值给一个变量，再重新定义解析方法。实际的挂载方法是 mountComponent
 * */
Vue.prototype.$mount = function() {
  //  ...
}

function mountComponent(vm, el, hydrating) {
  // ...
}
```
