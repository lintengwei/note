- [Vue 如何实现数据侦测](#vue-%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0%E6%95%B0%E6%8D%AE%E4%BE%A6%E6%B5%8B)
  - [Dep](#dep)
  - [Watcher](#watcher)
  - [Observer](#observer)
  - [数组变异方法](#%E6%95%B0%E7%BB%84%E5%8F%98%E5%BC%82%E6%96%B9%E6%B3%95)
  - [更新异步队列](#%E6%9B%B4%E6%96%B0%E5%BC%82%E6%AD%A5%E9%98%9F%E5%88%97)
  - [虚拟节点](#%E8%99%9A%E6%8B%9F%E8%8A%82%E7%82%B9)
  - [FAQ](#faq)

# Vue 如何实现数据侦测

Vue 会为每一个根属性使用【defineReactive\$\$1】方法创建监听器，实现原理是使用 Object.defineProperty()重写 data 的 get 和 set 方法，当每次修改和获取对象的属性的时候，会拦截操作，来实现响应式属性。

具体实现如下：

1. 对于每个根属性，vue 会为其重新定义 get 和 set 方法，并且在内使用闭包创建了一个 Dep 对象，该对象有一个 subs 的属性，值为数组，用于存放依赖它的 Watchers；
2. 而对于其他需要依赖根属性的值，例如计算属性，vue 会为每一个属性创建一个 Watcher，并且使用 object.defineProperty 重新定义 get 和 set 方法，当第一次引用该属性的时候，会调用我们设置的函数作为这个属性个 get 方法，而我们在 get 内有引用根属性的值，所以会调用根属性的 get 方法；
3. 在根属性的 get 方法内有一句【dep.depend()】，会把我们前面提到的每个属性创建的 dep 添加进计算属性 Watcher 的【newDeps】数组里面，并且把属于计算属性的 Watcher 作为依赖推进 dep 的 subs 的数组里面；
4. 在将来的某个时刻重新赋值了该引用属性的值，会调用 set 方法来拦截操作，而 set 方法里面有一句【 dep.notify()】会通知所有在 Watchers，也就是在 subs 里面的 Watchers，即该根属性的依赖 Watchers，每个 Watchers 调用 update 方法来跟新自己存在的值；

## Dep

```javascript
var uid = 0

/**
 * A dep is an observable that can have multiple
 * directives subscribing to it.
 */
var Dep = function Dep() {
  this.id = uid++
  this.subs = [] // Watcher[]
}

Dep.prototype.addSub = function addSub(sub) {
  this.subs.push(sub)
}

Dep.prototype.removeSub = function removeSub(sub) {
  remove(this.subs, sub)
}

//  例如某个计算属性b，依赖原始值a，当第一次获取b的值的时候，会调用
Dep.prototype.depend = function depend() {
  if (Dep.target) {
    Dep.target.addDep(this)
  }
}

Dep.prototype.notify = function notify() {
  // stabilize the subscriber list first
  var subs = this.subs.slice()
  if (process.env.NODE_ENV !== 'production' && !config.async) {
    // subs aren't sorted in scheduler if not running async
    // we need to sort them now to make sure they fire in correct
    // order
    subs.sort(function(a, b) {
      return a.id - b.id
    })
  }
  for (var i = 0, l = subs.length; i < l; i++) {
    subs[i].update()
  }
}

// The current target watcher being evaluated.
// This is globally unique because only one watcher
// can be evaluated at a time.
//  全局唯一的，因为同一时间只有一个watcher会执行
//  Watcher队列
Dep.target = null
var targetStack = []

function pushTarget(target) {
  targetStack.push(target)
  Dep.target = target
}

function popTarget() {
  targetStack.pop()
  Dep.target = targetStack[targetStack.length - 1]
}
```

## Watcher

```javascript
var uid$2 = 0

/**
 * A watcher parses an expression, collects dependencies,
 * and fires callback when the expression value changes.
 * This is used for both the $watch() api and directives.
 */
var Watcher = function Watcher(vm, expOrFn, cb, options, isRenderWatcher) {
  this.vm = vm
  if (isRenderWatcher) {
    vm._watcher = this
  }
  vm._watchers.push(this)
  // options
  if (options) {
    this.deep = !!options.deep
    this.user = !!options.user
    this.lazy = !!options.lazy //  是否创建的时候就生成依赖，否则只有在调用的时候才会生成依赖
    this.sync = !!options.sync
    this.before = options.before
  } else {
    this.deep = this.user = this.lazy = this.sync = false
  }
  this.cb = cb
  this.id = ++uid$2 // uid for batching
  this.active = true

  //  如果是懒加载模式，一开始watch的值是脏值！
  this.dirty = this.lazy // for lazy watchers
  this.deps = []
  this.newDeps = []
  this.depIds = new _Set()
  this.newDepIds = new _Set()
  this.expression =
    process.env.NODE_ENV !== 'production' ? expOrFn.toString() : ''
  // parse expression for getter
  if (typeof expOrFn === 'function') {
    this.getter = expOrFn
  } else {
    this.getter = parsePath(expOrFn)
    if (!this.getter) {
      this.getter = noop
      process.env.NODE_ENV !== 'production' &&
        warn(
          'Failed watching path: "' +
            expOrFn +
            '" ' +
            'Watcher only accepts simple dot-delimited paths. ' +
            'For full control, use a function instead.',
          vm
        )
    }
  }
  this.value = this.lazy ? undefined : this.get()
}

/**
 * Evaluate the getter, and re-collect dependencies.
 */
Watcher.prototype.get = function get() {
  pushTarget(this)
  var value
  var vm = this.vm
  try {
    value = this.getter.call(vm, vm)
  } catch (e) {
    if (this.user) {
      handleError(e, vm, 'getter for watcher "' + this.expression + '"')
    } else {
      throw e
    }
  } finally {
    // "touch" every property so they are all tracked as
    // dependencies for deep watching
    if (this.deep) {
      traverse(value)
    }
    popTarget()
    this.cleanupDeps()
  }
  return value
}

/**
 * Add a dependency to this directive.
 */
Watcher.prototype.addDep = function addDep(dep) {
  var id = dep.id
  if (!this.newDepIds.has(id)) {
    this.newDepIds.add(id)
    this.newDeps.push(dep)
    if (!this.depIds.has(id)) {
      dep.addSub(this)
    }
  }
}

/**
 * Clean up for dependency collection.
 */
Watcher.prototype.cleanupDeps = function cleanupDeps() {
  var i = this.deps.length
  while (i--) {
    var dep = this.deps[i]
    if (!this.newDepIds.has(dep.id)) {
      dep.removeSub(this)
    }
  }
  var tmp = this.depIds
  this.depIds = this.newDepIds
  this.newDepIds = tmp
  this.newDepIds.clear()
  tmp = this.deps
  this.deps = this.newDeps
  this.newDeps = tmp
  this.newDeps.length = 0
}

/**
 * Subscriber interface.
 * Will be called when a dependency changes.
 */
Watcher.prototype.update = function update() {
  /* istanbul ignore else */
  //  更新策略
  //  懒惰模式，只有引用的时候才会去计算值，不引用不会去计算，Vue默认开启了懒惰模式
  if (this.lazy) {
    this.dirty = true
  } else if (this.sync) {
    //  如果设置sync为true，表示同步更新，update的时候单独执行ui的渲染？
    //  默认sync为false，可以增强性能
    this.run()
  } else {
    //  一个更新队列，那又是何时把队列里面的任务推出来执行呢？
    queueWatcher(this)
  }
}

/**
 * Scheduler job interface.
 * Will be called by the scheduler.
 */
Watcher.prototype.run = function run() {
  if (this.active) {
    var value = this.get()
    if (
      value !== this.value ||
      // Deep watchers and watchers on Object/Arrays should fire even
      // when the value is the same, because the value may
      // have mutated.
      isObject(value) ||
      this.deep
    ) {
      // set new value
      var oldValue = this.value
      this.value = value
      if (this.user) {
        try {
          this.cb.call(this.vm, value, oldValue)
        } catch (e) {
          handleError(
            e,
            this.vm,
            'callback for watcher "' + this.expression + '"'
          )
        }
      } else {
        this.cb.call(this.vm, value, oldValue)
      }
    }
  }
}

/**
 * Evaluate the value of the watcher.
 * This only gets called for lazy watchers.
 */
Watcher.prototype.evaluate = function evaluate() {
  this.value = this.get()
  this.dirty = false
}

/**
 * Depend on all deps collected by this watcher.
 */
Watcher.prototype.depend = function depend() {
  var i = this.deps.length
  while (i--) {
    this.deps[i].depend()
  }
}

/**
 * Remove self from all dependencies' subscriber list.
 */
Watcher.prototype.teardown = function teardown() {
  if (this.active) {
    // remove self from vm's watcher list
    // this is a somewhat expensive operation so we skip it
    // if the vm is being destroyed.
    if (!this.vm._isBeingDestroyed) {
      remove(this.vm._watchers, this)
    }
    var i = this.deps.length
    while (i--) {
      this.deps[i].removeSub(this)
    }
    this.active = false
  }
}
```

## Observer

```javascript
var Observer = function Observer(value) {
  this.value = value
  this.dep = new Dep()
  this.vmCount = 0
  def(value, '__ob__', this)
  if (Array.isArray(value)) {
    if (hasProto) {
      protoAugment(value, arrayMethods)
    } else {
      copyAugment(value, arrayMethods, arrayKeys)
    }
    this.observeArray(value)
  } else {
    this.walk(value)
  }
}

/**
 * Walk through all properties and convert them into
 * getter/setters. This method should only be called when
 * value type is Object.
 */
Observer.prototype.walk = function walk(obj) {
  var keys = Object.keys(obj)
  for (var i = 0; i < keys.length; i++) {
    defineReactive$$1(obj, keys[i])
  }
}

/**
 * Observe a list of Array items.
 */
Observer.prototype.observeArray = function observeArray(items) {
  for (var i = 0, l = items.length; i < l; i++) {
    observe(items[i])
  }
}

// helpers

/**
 * Augment a target Object or Array by intercepting
 * the prototype chain using __proto__
 */
function protoAugment(target, src) {
  /* eslint-disable no-proto */
  target.__proto__ = src
  /* eslint-enable no-proto */
}

/**
 * Augment a target Object or Array by defining
 * hidden properties.
 */
/* istanbul ignore next */
function copyAugment(target, src, keys) {
  for (var i = 0, l = keys.length; i < l; i++) {
    var key = keys[i]
    def(target, key, src[key])
  }
}

/**
 * Define a reactive property on an Object.
 */
function defineReactive$$1(obj, key, val, customSetter, shallow) {
  var dep = new Dep()

  var property = Object.getOwnPropertyDescriptor(obj, key)
  if (property && property.configurable === false) {
    return
  }

  // cater for pre-defined getter/setters
  var getter = property && property.get
  var setter = property && property.set
  if ((!getter || setter) && arguments.length === 2) {
    val = obj[key]
  }

  var childOb = !shallow && observe(val)
  Object.defineProperty(obj, key, {
    enumerable: true,
    configurable: true,
    get: function reactiveGetter() {
      var value = getter ? getter.call(obj) : val
      if (Dep.target) {
        dep.depend()
        if (childOb) {
          //  如果是value是一个Object，也会把依赖放进属性__ob__，用于当整个Object被替换的时候发起通知
          childOb.dep.depend()
          if (Array.isArray(value)) {
            dependArray(value)
          }
        }
      }
      return value
    },
    set: function reactiveSetter(newVal) {
      var value = getter ? getter.call(obj) : val
      /* eslint-disable no-self-compare */
      if (newVal === value || (newVal !== newVal && value !== value)) {
        return
      }
      /* eslint-enable no-self-compare */
      if (process.env.NODE_ENV !== 'production' && customSetter) {
        customSetter()
      }
      // #7981: for accessor properties without setter
      if (getter && !setter) {
        return
      }
      if (setter) {
        setter.call(obj, newVal)
      } else {
        val = newVal
      }
      childOb = !shallow && observe(newVal)
      dep.notify()
    }
  })
}
```

## 数组变异方法

```javascript
var arrayProto = Array.prototype
var arrayMethods = Object.create(arrayProto)

var methodsToPatch = [
  'push',
  'pop',
  'shift',
  'unshift',
  'splice',
  'sort',
  'reverse'
]

/**
 * Intercept mutating methods and emit events
 */
methodsToPatch.forEach(function(method) {
  // cache original method
  //  获取原始方法
  var original = arrayProto[method]
  def(arrayMethods, method, function mutator() {
    var args = [],
      len = arguments.length
    while (len--) args[len] = arguments[len]

    //  返回结果
    var result = original.apply(this, args)
    var ob = this.__ob__
    var inserted

    //  对于push unshift splice有可能插入新元素的操作，调用 Observer.prototype.observeArray 去定义侦测。当操作完成之后，囤估值所有依赖来来刷新数据
    switch (method) {
      case 'push':
      case 'unshift':
        inserted = args
        break
      case 'splice':
        inserted = args.slice(2)
        break
    }
    if (inserted) {
      ob.observeArray(inserted)
    }
    // notify change
    ob.dep.notify()
    return result
  })
})
```

## 更新异步队列

Vue 内部通过异步策略来延迟数据的更新，会把多次的更新操作缓冲到一起执行来提高效率

1. 通过函数【queueWatcher】来把 Watcher 推进队列，队列的会根据 Watcher 的 id 值来排序，排序靠前的会优先执行，通过设置变量【has[id]===true】来识别本次队列是否已经有某个 Watcher 来防止多次计算同一个 Watcher 的值。
2. 当某个 Watcher 被推入队列的时候，会立即把 Watcher 队列的执行函数作为回调推入【callbacks】的回调队列，同时设置变量【waiting】来标识当前队列已经作为回调放入【callbacks】队列，在的风带执行，在此之后且在回调执行前再次推入的 Watcher 都会处以同一个队列里面。只有当该回调被执行了，重置【waiting】的值之后，才可以把队列执行函数放进【callbacks】对调队列。
3. 而在【nextTick】内部，通过设置【pending】来开启异步函数的执行，一个时间段之后开启一个异步队列。只有当前的异步队列函数【flushCallbacks】执行完成之后才能开启另外一个异步任务。
4. 最后通过【Promise.resolve()】或 setTimeout 来实现异步更新。

```javascript
var isUsingMicroTask = false

var callbacks = []
var pending = false

function flushCallbacks() {
  pending = false
  var copies = callbacks.slice(0)
  callbacks.length = 0
  for (var i = 0; i < copies.length; i++) {
    copies[i]()
  }
}

// Here we have async deferring wrappers using microtasks.
// In 2.5 we used (macro) tasks (in combination with microtasks).
// However, it has subtle problems when state is changed right before repaint
// (e.g. #6813, out-in transitions).
// Also, using (macro) tasks in event handler would cause some weird behaviors
// that cannot be circumvented (e.g. #7109, #7153, #7546, #7834, #8109).
// So we now use microtasks everywhere, again.
// A major drawback of this tradeoff is that there are some scenarios
// where microtasks have too high a priority and fire in between supposedly
// sequential events (e.g. #4521, #6690, which have workarounds)
// or even between bubbling of the same event (#6566).
var timerFunc

// The nextTick behavior leverages the microtask queue, which can be accessed
// via either native Promise.then or MutationObserver.
// MutationObserver has wider support, however it is seriously bugged in
// UIWebView in iOS >= 9.3.3 when triggered in touch event handlers. It
// completely stops working after triggering a few times... so, if native
// Promise is available, we will use it:
/* istanbul ignore next, $flow-disable-line */
if (typeof Promise !== 'undefined' && isNative(Promise)) {
  var p = Promise.resolve()
  timerFunc = function() {
    p.then(flushCallbacks)
    // In problematic UIWebViews, Promise.then doesn't completely break, but
    // it can get stuck in a weird state where callbacks are pushed into the
    // microtask queue but the queue isn't being flushed, until the browser
    // needs to do some other work, e.g. handle a timer. Therefore we can
    // "force" the microtask queue to be flushed by adding an empty timer.
    if (isIOS) {
      setTimeout(noop)
    }
  }
  isUsingMicroTask = true
} else if (
  !isIE &&
  typeof MutationObserver !== 'undefined' &&
  (isNative(MutationObserver) ||
    // PhantomJS and iOS 7.x
    MutationObserver.toString() === '[object MutationObserverConstructor]')
) {
  // Use MutationObserver where native Promise is not available,
  // e.g. PhantomJS, iOS7, Android 4.4
  // (#6466 MutationObserver is unreliable in IE11)
  var counter = 1
  var observer = new MutationObserver(flushCallbacks)
  var textNode = document.createTextNode(String(counter))
  observer.observe(textNode, {
    characterData: true
  })
  timerFunc = function() {
    counter = (counter + 1) % 2
    textNode.data = String(counter)
  }
  isUsingMicroTask = true
} else if (typeof setImmediate !== 'undefined' && isNative(setImmediate)) {
  // Fallback to setImmediate.
  // Techinically it leverages the (macro) task queue,
  // but it is still a better choice than setTimeout.
  timerFunc = function() {
    setImmediate(flushCallbacks)
  }
} else {
  // Fallback to setTimeout.
  timerFunc = function() {
    setTimeout(flushCallbacks, 0)
  }
}

var MAX_UPDATE_COUNT = 100

var queue = []
var activatedChildren = []
var has = {}
var circular = {}
var waiting = false //  是否在等待执行
var flushing = false //   是否在执行队列的任务
var index = 0
/**
 * Push a watcher into the watcher queue.
 * Jobs with duplicate IDs will be skipped unless it's
 * pushed when the queue is being flushed.
 */

//  调用Watcher的update方法，可能会触发这个方法来把Watcher推进队列
function queueWatcher(watcher) {
  var id = watcher.id
  if (has[id] == null) {
    has[id] = true

    //  如果没在执行队列任务，则把watcher直接推入数组，后续执行的时候会进行排序
    //  否则如果是正在执行，会根据id值来把当前的Watcher放在一个合适的位置。
    //  作为单线程的端，正在执行还有可能放进新的Watcher吗？
    if (!flushing) {
      queue.push(watcher)
    } else {
      var i = queue.length - 1
      while (i > index && queue[i].id > watcher.id) {
        i--
      }
      queue.splice(i + 1, 0, watcher)
    }
    // queue the flush
    if (!waiting) {
      waiting = true

      if (!config.async) {
        flushSchedulerQueue()
        return
      }
      //  把队列执行函数放进异步更新队列里面，等待更新
      nextTick(flushSchedulerQueue)
    }
  }
}

function nextTick(cb, ctx) {
  var _resolve
  callbacks.push(function() {
    if (cb) {
      try {
        cb.call(ctx)
      } catch (e) {
        handleError(e, ctx, 'nextTick')
      }
    } else if (_resolve) {
      _resolve(ctx)
    }
  })
  if (!pending) {
    pending = true
    timerFunc()
  }
  // $flow-disable-line
  if (!cb && typeof Promise !== 'undefined') {
    return new Promise(function(resolve) {
      _resolve = resolve
    })
  }
}

Vue.prototype.$nextTick = function(fn) {
  return nextTick(fn, this)
}

/**
 * Flush both queues and run the watchers.
 */
function flushSchedulerQueue() {
  currentFlushTimestamp = getNow()
  flushing = true
  var watcher, id

  // Sort queue before flush.
  // This ensures that:
  // 1. Components are updated from parent to child. (because parent is always
  //    created before the child)
  // 2. A component's user watchers are run before its render watcher (because
  //    user watchers are created before the render watcher)
  // 3. If a component is destroyed during a parent component's watcher run,
  //    its watchers can be skipped.
  //  排序能确保一下几点：
  //  1. 父组件在子组件之前更新
  queue.sort(function(a, b) {
    return a.id - b.id
  })

  // do not cache length because more watchers might be pushed
  // as we run existing watchers
  for (index = 0; index < queue.length; index++) {
    watcher = queue[index]
    if (watcher.before) {
      watcher.before()
    }
    id = watcher.id
    has[id] = null
    watcher.run()
    // in dev build, check and stop circular updates.
    if (has[id] != null) {
      circular[id] = (circular[id] || 0) + 1
      if (circular[id] > MAX_UPDATE_COUNT) {
        warn(
          'You may have an infinite update loop ' +
            (watcher.user
              ? 'in watcher with expression "' + watcher.expression + '"'
              : 'in a component render function.'),
          watcher.vm
        )
        break
      }
    }
  }

  // keep copies of post queues before resetting state
  var activatedQueue = activatedChildren.slice()
  var updatedQueue = queue.slice()

  //  重置
  //  清空队列和一些标识【waiting】【flushing】
  resetSchedulerState()

  // call component updated and activated hooks
  callActivatedHooks(activatedQueue)
  callUpdatedHooks(updatedQueue)

  // devtool hook
  /* istanbul ignore if */
  if (devtools && config.devtools) {
    devtools.emit('flush')
  }
}

/**
 * Reset the scheduler's state.
 */
function resetSchedulerState() {
  index = queue.length = activatedChildren.length = 0
  has = {}
  {
    circular = {}
  }
  waiting = flushing = false
}

function callActivatedHooks(queue) {
  for (var i = 0; i < queue.length; i++) {
    queue[i]._inactive = true
    activateChildComponent(queue[i], true /* true */)
  }
}

function callUpdatedHooks(queue) {
  var i = queue.length
  while (i--) {
    var watcher = queue[i]
    var vm = watcher.vm
    if (vm._watcher === watcher && vm._isMounted && !vm._isDestroyed) {
      callHook(vm, 'updated')
    }
  }
}

function activateChildComponent(vm, direct) {
  if (direct) {
    vm._directInactive = false
    if (isInInactiveTree(vm)) {
      return
    }
  } else if (vm._directInactive) {
    return
  }
  if (vm._inactive || vm._inactive === null) {
    vm._inactive = false
    for (var i = 0; i < vm.$children.length; i++) {
      activateChildComponent(vm.$children[i])
    }
    callHook(vm, 'activated')
  }
}
```

## 虚拟节点

Vue 内部通过构建虚拟的 dom 树来存储节点信息，并且会为每个 Vue 实例分配一个 render Watcher 来侦测当数据变更的时候，生成新的虚拟 dom 树，并且通过和之前的虚拟树比较来检测变动，通过计算实现最小的 dom 变动来达到提高性能的目的。

```javascript
//  render Watcher函数，每次数据变更都会调用
updateComponent = function() {
  vm._update(vm._render(), hydrating)
}
//  render Watcher对象
new Watcher(
  vm,
  updateComponent,
  noop,
  {
    before: function before() {
      if (vm._isMounted && !vm._isDestroyed) {
        callHook(vm, 'beforeUpdate')
      }
    }
  },
  true /* isRenderWatcher */
)
```

## FAQ

1. <pre>__ob__</pre>是对整个对象的侦测？对于data根对象，会创建一个，并且其嵌套的每个值为Object的对象都会创建一个？

如果侦测是值是一个对象，除了会为每一个属性分配一个 Dep 之外，还会为对象创建一个<pre>**ob**</pre>属性，该属性指向一个【Observer】对象。每个属性里面的 Dep 是针对单个属性的改变，而对象的<pre>**ob**</pre>是用于监听对象值的修改，让某个 key 对象的值原先为 Object 的时候，如果把值变更为其他值，会触发【Observer】里面的 Dep 的 notify 方法，通知依赖于它的对象来实现数据的刷新。
