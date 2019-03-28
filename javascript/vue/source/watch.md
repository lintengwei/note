- [Vue 如何实现数据侦测](#vue-%E5%A6%82%E4%BD%95%E5%AE%9E%E7%8E%B0%E6%95%B0%E6%8D%AE%E4%BE%A6%E6%B5%8B)
  - [Dep](#dep)
  - [Watcher](#watcher)
  - [Observer](#observer)
  - [数组变异方法](#%E6%95%B0%E7%BB%84%E5%8F%98%E5%BC%82%E6%96%B9%E6%B3%95)
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

## FAQ

1. <pre>__ob__</pre>是对整个对象的侦测？对于data根对象，会创建一个，并且其嵌套的每个值为Object的对象都会创建一个？

如果侦测是值是一个对象，除了会为每一个属性分配一个 Dep 之外，还会为对象创建一个<pre>**ob**</pre>属性，该属性指向一个【Observer】对象。每个属性里面的 Dep 是针对单个属性的改变，而对象的<pre>**ob**</pre>是用于监听对象值的修改，让某个 key 对象的值原先为 Object 的时候，如果把值变更为其他值，会触发【Observer】里面的 Dep，通知依赖于它的数据来实现数据的刷新。
