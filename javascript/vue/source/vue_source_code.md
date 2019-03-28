# source_code

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

## Vue 的初始化

1. 调用方法 Vue.prototype.\_init 方法进行初始化

```javascript
//  初始化组件的父子关系，以及设置一些实例属性
initLifecycle(vm)

//  初始化父子通讯的事件
initEvents(vm)

//  初始化渲染
initRender(vm)

//  调用钩子函数
callHook(vm, 'beforeCreate')

//  初始化注入
initInjections(vm) // resolve injections before data/props

//  初始化状态 data,props,watch,computed,methods等
initState(vm)

//  初始化提供者
initProvide(vm) // resolve provide after data/props

//  完成之后调用钩子【created】，表示vue初始化工作完成，可以进行挂载操作
callHook(vm, 'created')

//  把vue实例挂载在dom中
vm.$mount(vm.$options.el)
```

## 关于渲染

可以使用三种方式来渲染 vue，定义一个 render 函数，使用 template 字符串定义 html 文本，使用 el 节点告诉 vue 去获取里面的内容。其中优先级关系：
==render>template>el==

> render

> template

> el

```javascript
var mount = Vue.prototype.$mount

//  第一次会调用这个方法来解析html模版，然后才会调用下面的mount方法
Vue.prototype.$mount = function(el, hydrating) {
  el = el && query(el)

  /* istanbul ignore if */
  if (el === document.body || el === document.documentElement) {
    process.env.NODE_ENV !== 'production' &&
      warn(
        'Do not mount Vue to <html> or <body> - mount to normal elements instead.'
      )
    return this
  }

  var options = this.$options
  // resolve template/el and convert to render function
  if (!options.render) {
    var template = options.template
    if (template) {
      if (typeof template === 'string') {
        if (template.charAt(0) === '#') {
          template = idToTemplate(template)
          /* istanbul ignore if */
          if (process.env.NODE_ENV !== 'production' && !template) {
            warn(
              'Template element not found or is empty: ' + options.template,
              this
            )
          }
        }
      } else if (template.nodeType) {
        template = template.innerHTML
      } else {
        if (process.env.NODE_ENV !== 'production') {
          warn('invalid template option:' + template, this)
        }
        return this
      }
    } else if (el) {
      template = getOuterHTML(el)
    }
    if (template) {
      /* istanbul ignore if */
      if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
        mark('compile')
      }

      var ref = compileToFunctions(
        template,
        {
          outputSourceRange: process.env.NODE_ENV !== 'production',
          shouldDecodeNewlines: shouldDecodeNewlines,
          shouldDecodeNewlinesForHref: shouldDecodeNewlinesForHref,
          delimiters: options.delimiters, //  插值标识
          comments: options.comments //  是否保留注释
        },
        this
      )
      var render = ref.render
      var staticRenderFns = ref.staticRenderFns
      options.render = render
      options.staticRenderFns = staticRenderFns

      /* istanbul ignore if */
      if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
        mark('compile end')
        measure('vue ' + this._name + ' compile', 'compile', 'compile end')
      }
    }
  }
  return mount.call(this, el, hydrating)
}

// public mount method
//  上面方法之后调用
Vue.prototype.$mount = function(el, hydrating) {
  el = el && inBrowser ? query(el) : undefined
  return mountComponent(this, el, hydrating)
}

function mountComponent(vm, el, hydrating) {
  vm.$el = el
  if (!vm.$options.render) {
    vm.$options.render = createEmptyVNode
    if (process.env.NODE_ENV !== 'production') {
      /* istanbul ignore if */
      if (
        (vm.$options.template && vm.$options.template.charAt(0) !== '#') ||
        vm.$options.el ||
        el
      ) {
        warn(
          'You are using the runtime-only build of Vue where the template ' +
            'compiler is not available. Either pre-compile the templates into ' +
            'render functions, or use the compiler-included build.',
          vm
        )
      } else {
        warn(
          'Failed to mount component: template or render function not defined.',
          vm
        )
      }
    }
  }
  callHook(vm, 'beforeMount')

  var updateComponent
  /* istanbul ignore if */
  if (process.env.NODE_ENV !== 'production' && config.performance && mark) {
    updateComponent = function() {
      var name = vm._name
      var id = vm._uid
      var startTag = 'vue-perf-start:' + id
      var endTag = 'vue-perf-end:' + id

      mark(startTag)
      var vnode = vm._render()
      mark(endTag)
      measure('vue ' + name + ' render', startTag, endTag)

      mark(startTag)
      vm._update(vnode, hydrating)
      mark(endTag)
      measure('vue ' + name + ' patch', startTag, endTag)
    }
  } else {
    updateComponent = function() {
      vm._update(vm._render(), hydrating)
    }
  }

  // we set this to vm._watcher inside the watcher's constructor
  // since the watcher's initial patch may call $forceUpdate (e.g. inside child
  // component's mounted hook), which relies on vm._watcher being already defined
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
  hydrating = false

  // manually mounted instance, call mounted on self
  // mounted is called for render-created child components in its inserted hook
  if (vm.$vnode == null) {
    vm._isMounted = true
    callHook(vm, 'mounted')
  }
  return vm
}

Vue.prototype._render = function() {
  var vm = this
  var ref = vm.$options
  var render = ref.render
  var _parentVnode = ref._parentVnode

  if (_parentVnode) {
    vm.$scopedSlots = normalizeScopedSlots(
      _parentVnode.data.scopedSlots,
      vm.$slots,
      vm.$scopedSlots
    )
  }

  // set parent vnode. this allows render functions to have access
  // to the data on the placeholder node.
  vm.$vnode = _parentVnode
  // render self
  var vnode
  try {
    // There's no need to maintain a stack becaues all render fns are called
    // separately from one another. Nested component's render fns are called
    // when parent component is patched.
    currentRenderingInstance = vm
    vnode = render.call(vm._renderProxy, vm.$createElement)
  } catch (e) {
    handleError(e, vm, 'render')
    // return error render result,
    // or previous vnode to prevent render error causing blank component
    /* istanbul ignore else */
    if (process.env.NODE_ENV !== 'production' && vm.$options.renderError) {
      try {
        vnode = vm.$options.renderError.call(
          vm._renderProxy,
          vm.$createElement,
          e
        )
      } catch (e) {
        handleError(e, vm, 'renderError')
        vnode = vm._vnode
      }
    } else {
      vnode = vm._vnode
    }
  } finally {
    currentRenderingInstance = null
  }
  // if the returned array contains only a single node, allow it
  if (Array.isArray(vnode) && vnode.length === 1) {
    vnode = vnode[0]
  }
  // return empty vnode in case the render function errored out
  if (!(vnode instanceof VNode)) {
    if (process.env.NODE_ENV !== 'production' && Array.isArray(vnode)) {
      warn(
        'Multiple root nodes returned from render function. Render function ' +
          'should return a single root node.',
        vm
      )
    }
    vnode = createEmptyVNode()
  }
  // set parent
  vnode.parent = _parentVnode
  return vnode
}

Vue.prototype._update = function(vnode, hydrating) {
  var vm = this
  var prevEl = vm.$el
  var prevVnode = vm._vnode
  var restoreActiveInstance = setActiveInstance(vm)
  vm._vnode = vnode
  // Vue.prototype.__patch__ is injected in entry points
  // based on the rendering backend used.
  if (!prevVnode) {
    // initial render
    vm.$el = vm.__patch__(vm.$el, vnode, hydrating, false /* removeOnly */)
  } else {
    // updates
    vm.$el = vm.__patch__(prevVnode, vnode)
  }
  restoreActiveInstance()
  // update __vue__ reference
  if (prevEl) {
    prevEl.__vue__ = null
  }
  if (vm.$el) {
    vm.$el.__vue__ = vm
  }
  // if parent is an HOC, update its $el as well
  if (vm.$vnode && vm.$parent && vm.$vnode === vm.$parent._vnode) {
    vm.$parent.$el = vm.$el
  }
  // updated hook is called by the scheduler to ensure that children are
  // updated in a parent's updated hook.
}
```
