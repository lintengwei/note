# vue

- [生命周期](#生命周期)
- [循环](#循环)
- [插槽](#插槽)

## 全局配置

### 自定义选项合并策略

重新定义 Vue 继承链的重名选项的覆盖规则

```javascript
Vue.options._my_option = 16
Vue.config.optionMergeStrategies._my_option = function(parent, child, vm) {
  return child + 1 + parent
}

const Profile = Vue.extend({
  _my_option: 1
})

// Profile.options._my_option = 2

const vm = new Profile({
  _my_option: 22
})

//  vm.$options._my_option=23
```

## 生命周期

```javascript {.line-numbers}
const component = {
  beforeCreate() {
    //  初始化事件和生命周期钩子
  },
  created() {
    //  data和props初始化完成，此时可以对数据进行操作，data在props之后初始化
    //  如果props传输进来的数据后不在跟父组件关联，可以初始化一个本地变量
    //  如果需要转换数据额结构，则可以生成一个计算属性
  },
  beforeMount() {
    //  组件挂载至dom树之前调用
  },
  mounted() {
    //  组件已经挂载在dom树，此时可以访问dom节点
  },
  beforeUpdate() {
    //  当数据改变导致组件更新之前调用
  },
  updated() {
    //  组件更新完成之后调用
  },
  beforeDestroy() {
    //  卸载组件调用，v-if、切换路由等
  },
  destroyed() {
    //  组件卸载完成
  }
}
```

## 数据侦测

```javascript
/**
 * @param expOrFn 点表达式或者计算函数==> data(){return {b:{c:1}}} this.a.b/function(){return this.a.b+1}
 * @param cb 当值干煸时候的回调函数
 * @param options 附加参数 deep/immediate
 * */
vm.$watch(expOrfn, cb, options)

component = {
  computed: {
    key1: 'methods', //  methods定义的方法
    key2: {
      //  如果是对象，需要定义handler方法
      handler() {},
      deep: true, //  附加参数
      immediate: true
    },
    key3: [] //  如果是数组 [Object]==key2 [String]==key1
  }
}
```

## 循环

```html {.line-numbers}
<!-- Array -->
<li v-for="(item,index) in list"></li>
<!-- Object -->
<li v-for="(value,key,index) in object"></li>
```

> 数组方法

```javascript
// 变异方法，调用以下方法会同步更新视图
push()
pop()
shift()
unshift()
splice()
sort()
reverse()

//  非变异方法 slice concat filter map 返回一个新数组，可以使之替换旧的数据实现数据更新
//  你可能认为这将导致 Vue 丢弃现有 DOM 并重新渲染整个列表。幸运的是，事实并非如此。Vue 为了使得 DOM 元素得到最大范围的重用而实现了一些智能的、启发式的方法，所以用一个含有相同元素的数组去替换原来的数组是非常高效的操作。
example1.items = example1.items.filter(function(item) {
  return item.message.match(/Foo/)
})

//  更新
//  当你利用索引直接设置一个项时，例如：vm.items[indexOfItem] = newValue
//  当你修改数组的长度时，例如：vm.items.length = newLength
//  数组没有检查数据变更的方法，无法发布订阅通知

// Vue.set
Vue.set(vm.items, indexOfItem, newValue)
vm.items.splice(indexOfItem, 1, newValue)
vm.$set(vm.items, indexOfItem, newValue)
vm.$set(vm.obj, key, value)

//  清空Array
vm.items.splice(newLength)
```

> key

==2.2.0+ 的版本里，当在组件中使用 v-for 时，key 现在是必须的。==

## 插槽

### 作用域插槽

> 当子组件需要根据数据来渲染多种 ui 的时，并且需要复用该组件的时候使用。配合【slot】使用。

==在 2.5.0+，slot-scope 不再限制在 <template> 元素上使用，而可以用在插槽内的任何元素或组件上。==

```html
<!-- 定义一个todoList组件，默认情况下值显示text字段 -->
<!--
  现有status字段，需要根据status字段来显示其他不一致的内容，可以使用作用于插槽
-->
<template>
  <ul class="todo-list">
    <li v-for="(item,index) in todoList" @click="complete(index)" :key="index">
      <!-- 定义父组件接收的变量名为【todo】 -->
      <slot :todo="item"> {{item.text}} </slot>
    </li>
  </ul>
</template>
<!-- 父组件 -->
<template>
  <todo-list>
    <!-- 父组件来自由组合需要展示的内容 -->
    <!-- 定义子组件在父组件中的命名控件为【scope】 -->
    <!-- scope.todo===todo===子组件的item -->
    <!-- 结构语法 slot-scope="{ todo }" -->
    <template slot-scope="scope">
      <span v-if="scope.todo.isRight">+</span>{{scope.todo.text}}
    </template>
  </todo-list>
</template>
```
