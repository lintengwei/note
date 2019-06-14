# Vue 选项解释

## config

> optionMergeStrategies<Object|Function>

自定义选项的合并策略。就是除了 Vue 设定的选项之外，开发添加的而外属性，冲突合并的策略。可以接受一个函数表示所有的属性都是用同一种合并策略，如果接收单个对象，则键表示需要合并的属性，而值表示针对该属性如何合并。自定义的属性会设置在实例的【\$options】对象内。

==注意点==

1. 会一直追溯到 Vue 的 options 进行合并
2. 只能合并最外层的属性

```javascript
//  自定义合并策略
Vue.config.optionMergeStrategies[name] = function(parentValue, childValue, vm) {
  //  策略内容
}
//  Vue对其可配置选项都设置了合并策略，可以复用现有的策略
//  如果没有找到指定属性的合并策略，则使用Vue默认的合并策略
//  如果子组件中的属性不存在，则使用父组件中的值
var defaultStrat = function(parentVal, childVal) {
  return childVal === undefined ? parentVal : childVal
}
```
