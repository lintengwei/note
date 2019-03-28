# Vue options

Vue 构造器函数上的 options 参数包含了一些 Vue 内置的 components，directives，filters。当 new 一个新的实例的时候，会把这个 options 注入到每个实例里面。所以可以在每个实例里面直接使用，不需要再声明，全局组件也是这个原理？

```javascript
//  内置的keep-alive组件
var builtInComponents = {
  KeepAlive: KeepAlive
}

Vue.options = Object.create(null)
ASSET_TYPES.forEach(function(type) {
  Vue.options[type + 's'] = Object.create(null)
})

//  扩展
extend(Vue.options.components, builtInComponents)
// install platform runtime directives & components
extend(Vue.options.directives, platformDirectives)
extend(Vue.options.components, platformComponents)
// this is used to identify the "base" constructor to extend all plain-object
// components with in Weex's multi-instance scenarios.
Vue.options._base = Vue
```

## constructor options

构造函数内置的选项

### components

#### keep-alive

#### transition

### directives

#### v-model

#### v-show

### filters

## Vue instance options

Vue 实例的选项

### data

> Object|Function:Object

### computed

### watch

> Object:{key:string,value:Function|string|Object}

```javascript
const vm = new Vue({
  data() {
    return {
      a: 1,
      b: 2,
      c: 3,
      d: 4
    }
  },
  watch: {
    //  当定义字符串的时候，会需找methods里面的方法
    d: 'func1',

    //  可以使用数组定义多个回调，methods中的func1，func2
    a: ['func1', 'func2'],

    //  当定义一个Object的时候，handler为回调函数
    b: {
      lazy: false, //  懒加载模式，只有在使用的时候才会去计算值
      user: false, //
      deep: false,
      sync: false,
      before: false,
      handler: function(newValue) {}
    },

    //  可以直接定义一个函数作为回调
    c(newValue) {}
  },
  methods: {
    func1() {},
    func2() {}
  }
})
```

### methods

### props
