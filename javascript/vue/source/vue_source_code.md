- [Vue 简要概述](#Vue-%E7%AE%80%E8%A6%81%E6%A6%82%E8%BF%B0)
  - [加载 vue 框架会直接运行这几个初始化函数](#%E5%8A%A0%E8%BD%BD-vue-%E6%A1%86%E6%9E%B6%E4%BC%9A%E7%9B%B4%E6%8E%A5%E8%BF%90%E8%A1%8C%E8%BF%99%E5%87%A0%E4%B8%AA%E5%88%9D%E5%A7%8B%E5%8C%96%E5%87%BD%E6%95%B0)

# Vue 简要概述

## 加载 vue 框架会直接运行这几个初始化函数

```javascript
//  会执行下列几个函数，完成Vue的全局接口配置，和一些原型方法设置
initGlobalAPI(Vue)
initMixin(Vue)
stateMixin(Vue)
eventsMixin(Vue)
lifecycleMixin(Vue)
renderMixin(Vue)
```

> initGlobalAPI

配置全局 api。

```javascript
function initGlobalAPI(Vue) {
  // config
  var configDef = {}
  configDef.get = function() {
    return config
  }
  {
    configDef.set = function() {
      warn(
        'Do not replace the Vue.config object, set individual fields instead.'
      )
    }
  }
  Object.defineProperty(Vue, 'config', configDef)

  // exposed util methods.
  // NOTE: these are not considered part of the public API - avoid relying on
  // them unless you are aware of the risk.
  //  暴露一些Vue的工具方法
  Vue.util = {
    warn: warn,
    extend: extend,
    mergeOptions: mergeOptions,
    defineReactive: defineReactive$$1
  }

  //  全局的设置观测方法
  Vue.set = set
  Vue.delete = del
  Vue.nextTick = nextTick

  // 2.6 explicit observable API
  Vue.observable = function(obj) {
    observe(obj)
    return obj
  }

  //  创建Vue全局的components，filters，directives容器
  Vue.options = Object.create(null)
  ASSET_TYPES.forEach(function(type) {
    Vue.options[type + 's'] = Object.create(null)
  })
  //  定义directives，components，filters的合并策略
  ASSET_TYPES.forEach(function(type) {
    strats[type + 's'] = mergeAssets
  })

  // this is used to identify the "base" constructor to extend all plain-object
  // components with in Weex's multi-instance scenarios.
  Vue.options._base = Vue

  //  注册内置的组件到全局组件中
  extend(Vue.options.components, builtInComponents)

  //  初始化插件
  initUse(Vue)
  //  初始化混合
  initMixin$1(Vue)
  //  初始化集成
  initExtend(Vue)
  //  初始化全局的component，filter，directive的注册功能
  initAssetRegisters(Vue)
}

function initAssetRegisters(Vue) {
  /**
   * Create asset registration methods.
   */
  ASSET_TYPES.forEach(function(type) {
    Vue[type] = function(id, definition) {
      if (!definition) {
        return this.options[type + 's'][id]
      } else {
        /* istanbul ignore if */
        if (type === 'component') {
          validateComponentName(id)
        }
        if (type === 'component' && isPlainObject(definition)) {
          definition.name = definition.name || id
          definition = this.options._base.extend(definition)
        }
        if (type === 'directive' && typeof definition === 'function') {
          definition = { bind: definition, update: definition }
        }
        this.options[type + 's'][id] = definition
        return definition
      }
    }
  })
}
```

> initMixin

设置 Vue 的初始化方法

```javascript
function Vue(options) {
  if (!(this instanceof Vue)) {
    warn('Vue is a constructor and should be called with the `new` keyword')
  }

  //  每次创建一个Vue实例，都会调用_init方法来进行必要的初始化
  this._init(options)
}

function initMixin(Vue) {
  Vue.prototype._init = function(options) {
    var vm = this
    // a uid
    //  vue实例的标识符
    vm._uid = uid$3++

    var startTag, endTag
    /* istanbul ignore if */
    if (config.performance && mark) {
      startTag = 'vue-perf-start:' + vm._uid
      endTag = 'vue-perf-end:' + vm._uid
      mark(startTag)
    }

    // a flag to avoid this being observed
    vm._isVue = true //  标志位，用于防止vue被侦测？
    // merge options
    if (options && options._isComponent) {
      // optimize internal component instantiation
      // since dynamic options merging is pretty slow, and none of the
      // internal component options needs special treatment.
      //  如果是一个vue组件，
      initInternalComponent(vm, options)
    } else {
      //  混合父级的options到实例内
      vm.$options = mergeOptions(
        resolveConstructorOptions(vm.constructor), //  cm.constructor===Vue
        options || {},
        vm
      )
    }
    /* istanbul ignore else */
    {
      initProxy(vm)
    }
    // expose real self
    vm._self = vm
    initLifecycle(vm)
    initEvents(vm)
    initRender(vm)
    callHook(vm, 'beforeCreate')
    initInjections(vm) // resolve injections before data/props
    initState(vm)
    initProvide(vm) // resolve provide after data/props
    callHook(vm, 'created')

    /* istanbul ignore if */
    if (config.performance && mark) {
      vm._name = formatComponentName(vm, false)
      mark(endTag)
      measure('vue ' + vm._name + ' init', startTag, endTag)
    }

    if (vm.$options.el) {
      vm.$mount(vm.$options.el)
    }
  }
}
```

> stateMixin

```javascript
function stateMixin(Vue) {
  // flow somehow has problems with directly declared definition object
  // when using Object.defineProperty, so we have to procedurally build up
  // the object here.
  var dataDef = {}
  dataDef.get = function() {
    return this._data
  }
  var propsDef = {}
  propsDef.get = function() {
    return this._props
  }
  {
    dataDef.set = function() {
      warn(
        'Avoid replacing instance root $data. ' +
          'Use nested data properties instead.',
        this
      )
    }
    propsDef.set = function() {
      warn('$props is readonly.', this)
    }
  }
  Object.defineProperty(Vue.prototype, '$data', dataDef)
  Object.defineProperty(Vue.prototype, '$props', propsDef)

  //  注册实例的对象变更观测方法
  Vue.prototype.$set = set
  Vue.prototype.$delete = del

  //  注册实例的watch方法
  Vue.prototype.$watch = function(expOrFn, cb, options) {
    var vm = this
    if (isPlainObject(cb)) {
      return createWatcher(vm, expOrFn, cb, options)
    }
    options = options || {}
    options.user = true
    var watcher = new Watcher(vm, expOrFn, cb, options)
    if (options.immediate) {
      try {
        cb.call(vm, watcher.value)
      } catch (error) {
        handleError(
          error,
          vm,
          'callback for immediate watcher "' + watcher.expression + '"'
        )
      }
    }
    return function unwatchFn() {
      watcher.teardown()
    }
  }
}
```

> eventsMixin

定义实例的自定义事件。

1. 父子组件通讯是如何实现的？

在子组件【$emit】事件之后，会在vm._events里面去找对应的回调，那么当父组件【@eventName】的时候，会通过调用子组件实例的【$on】方法，向子组件的【vm.\_events】里面注入监听的回调

```javascript
function eventsMixin(Vue) {
  var hookRE = /^hook:/

  //  注册事件
  Vue.prototype.$on = function(event, fn) {
    var vm = this
    if (Array.isArray(event)) {
      for (var i = 0, l = event.length; i < l; i++) {
        vm.$on(event[i], fn)
      }
    } else {
      ;(vm._events[event] || (vm._events[event] = [])).push(fn)
      // optimize hook:event cost by using a boolean flag marked at registration
      // instead of a hash lookup
      if (hookRE.test(event)) {
        vm._hasHookEvent = true
      }
    }
    return vm
  }

  Vue.prototype.$once = function(event, fn) {
    var vm = this
    function on() {
      vm.$off(event, on)
      fn.apply(vm, arguments)
    }
    on.fn = fn
    vm.$on(event, on)
    return vm
  }

  Vue.prototype.$off = function(event, fn) {
    var vm = this
    // all
    if (!arguments.length) {
      vm._events = Object.create(null)
      return vm
    }
    // array of events
    if (Array.isArray(event)) {
      for (var i$1 = 0, l = event.length; i$1 < l; i$1++) {
        vm.$off(event[i$1], fn)
      }
      return vm
    }
    // specific event
    var cbs = vm._events[event]
    if (!cbs) {
      return vm
    }
    if (!fn) {
      vm._events[event] = null
      return vm
    }
    // specific handler
    var cb
    var i = cbs.length
    while (i--) {
      cb = cbs[i]
      if (cb === fn || cb.fn === fn) {
        cbs.splice(i, 1)
        break
      }
    }
    return vm
  }

  Vue.prototype.$emit = function(event) {
    var vm = this
    {
      var lowerCaseEvent = event.toLowerCase()
      if (lowerCaseEvent !== event && vm._events[lowerCaseEvent]) {
        tip(
          'Event "' +
            lowerCaseEvent +
            '" is emitted in component ' +
            formatComponentName(vm) +
            ' but the handler is registered for "' +
            event +
            '". ' +
            'Note that HTML attributes are case-insensitive and you cannot use ' +
            'v-on to listen to camelCase events when using in-DOM templates. ' +
            'You should probably use "' +
            hyphenate(event) +
            '" instead of "' +
            event +
            '".'
        )
      }
    }
    var cbs = vm._events[event]
    if (cbs) {
      cbs = cbs.length > 1 ? toArray(cbs) : cbs
      var args = toArray(arguments, 1)
      var info = 'event handler for "' + event + '"'
      for (var i = 0, l = cbs.length; i < l; i++) {
        invokeWithErrorHandling(cbs[i], vm, args, vm, info)
      }
    }
    return vm
  }
}
```
