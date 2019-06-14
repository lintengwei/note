- [Vue 的 全局 api](#vue-%E7%9A%84-%E5%85%A8%E5%B1%80-api)
  - [extend](#extend)
  - [filter|component|directive](#filtercomponentdirective)
    - [定义一个全局 filter](#%E5%AE%9A%E4%B9%89%E4%B8%80%E4%B8%AA%E5%85%A8%E5%B1%80-filter)
    - [定义一个全局 component](#%E5%AE%9A%E4%B9%89%E4%B8%80%E4%B8%AA%E5%85%A8%E5%B1%80-component)
    - [定义一个全局 directive](#%E5%AE%9A%E4%B9%89%E4%B8%80%E4%B8%AA%E5%85%A8%E5%B1%80-directive)
  - [源码](#%E6%BA%90%E7%A0%81)

# Vue 的 全局 api

## extend

```javascript
//  初始化extend
function initExtend(Vue) {
  /**
   * Each instance constructor, including Vue, has a unique
   * cid. This enables us to create wrapped "child
   * constructors" for prototypal inheritance and cache them.
   */
  Vue.cid = 0
  var cid = 1 //闭包值 ，每集成一个子类，自增

  /**
   * Class inheritance
   */
  Vue.extend = function(extendOptions) {
    extendOptions = extendOptions || {}
    var Super = this
    var SuperId = Super.cid
    var cachedCtors = extendOptions._Ctor || (extendOptions._Ctor = {})
    if (cachedCtors[SuperId]) {
      return cachedCtors[SuperId]
    }

    var name = extendOptions.name || Super.options.name
    if (name) {
      validateComponentName(name)
    }

    var Sub = function VueComponent(options) {
      this._init(options)
    }
    Sub.prototype = Object.create(Super.prototype)
    Sub.prototype.constructor = Sub
    Sub.cid = cid++
    Sub.options = mergeOptions(Super.options, extendOptions)
    Sub['super'] = Super

    // For props and computed properties, we define the proxy getters on
    // the Vue instances at extension time, on the extended prototype. This
    // avoids Object.defineProperty calls for each instance created.
    if (Sub.options.props) {
      initProps$1(Sub)
    }
    if (Sub.options.computed) {
      initComputed$1(Sub)
    }

    // allow further extension/mixin/plugin usage
    Sub.extend = Super.extend
    Sub.mixin = Super.mixin
    Sub.use = Super.use

    // create asset registers, so extended classes
    // can have their private assets too.
    ASSET_TYPES.forEach(function(type) {
      Sub[type] = Super[type]
    })
    // enable recursive self-lookup
    if (name) {
      Sub.options.components[name] = Sub
    }

    // keep a reference to the super options at extension time.
    // later at instantiation we can check if Super's options have
    // been updated.
    Sub.superOptions = Super.options
    Sub.extendOptions = extendOptions
    Sub.sealedOptions = extend({}, Sub.options)

    // cache constructor
    cachedCtors[SuperId] = Sub
    return Sub
  }
}
```

## filter|component|directive

### 定义一个全局 filter

```javascript
import Vue from 'vue'

Vue.filter('format', v => {
  return v
})
```

### 定义一个全局 component

```javascript
import Vue from 'vue'

Vue.component('tm-button', {
  //  Vue options
})
```

### 定义一个全局 directive

```javascript
import Vue from 'vue'

Vue.directive('focus', {
  bind(el) {},
  update(el) {}
})
```

## 源码

```javascript
var ASSET_TYPES = ['component', 'directive', 'filter']

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
          //  定义全局组件内部也是用过extend来实现的
          definition = this.options._base.extend(definition)
        }
        if (type === 'directive' && typeof definition === 'function') {
          //  如果definition是一个函数，则Vue默认会设置【bind】和【update】作为钩子
          definition = { bind: definition, update: definition }
        }
        //  注册到全局的components，directives，filters里面
        this.options[type + 's'][id] = definition
        return definition
      }
    }
  })
}

function validateComponentName(name) {
  if (
    !new RegExp('^[a-zA-Z][\\-\\.0-9_' + unicodeRegExp.source + ']*$').test(
      name
    )
  ) {
    warn(
      'Invalid component name: "' +
        name +
        '". Component names ' +
        'should conform to valid custom element name in html5 specification.'
    )
  }
  if (isBuiltInTag(name) || config.isReservedTag(name)) {
    warn(
      'Do not use built-in or reserved HTML elements as component ' +
        'id: ' +
        name
    )
  }
}
```
