# Vue 插件的使用

如何定义一个插件

1. 直接定义一个函数
2. 定义一个包含<install:Function>属性的对象。

```javascript
//  define plugin

//  导出Toast插件
export function Toast(Vue,options){

}

const Alert={
  install(Vue,options){

  }
}
//  导出Alert插件
export Alert

//  use
import Vue from 'vue'
import {Toast,Alert} from './plugins'

//  定义插件，可以传入多个插件
Vue.use(Toast,{
  //  options
},...args)
Vue.use(Alert,{
  //  options
},...args)
```

```javascript
function initUse(Vue) {
  Vue.use = function(plugin) {
    //  内置了一个缓冲组件的数组，已安装组件不会再次安装
    var installedPlugins =
      this._installedPlugins || (this._installedPlugins = [])
    if (installedPlugins.indexOf(plugin) > -1) {
      return this
    }

    // additional parameters
    var args = toArray(arguments, 1)
    args.unshift(this)
    if (typeof plugin.install === 'function') {
      plugin.install.apply(plugin, args)
    } else if (typeof plugin === 'function') {
      plugin.apply(null, args)
    }
    installedPlugins.push(plugin)
    return this
  }
}

function toArray(list, start) {
  start = start || 0
  var i = list.length - start
  var ret = new Array(i)
  while (i--) {
    ret[i] = list[i + start]
  }
  return ret
}
```
