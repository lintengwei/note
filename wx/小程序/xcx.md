# 小程序

## 生命周期

小程序架构是逻辑层和视图层分开实现，两个线程，逻辑层负责业务逻辑，视图层负责显示，当逻辑层数据变更并且需要视图实时更新页面，则需要通过逻辑层通知视图层来实现界面的数据更新【setData】方法

### 逻辑层

1. start
  1. 逻辑层线程创建页面对象，页面对象【Page】初始化，完成数据的初始化工作
  2. 创建完成之后，接连调用生命周期函数【onLoad】和【onShow】
2. created
  1. 页面对象创建完成，数据初始化完成，调用【wait】方法进入等待，等待视图层线程通知，并且把初始数据发送给视图层完成首次渲染。
  2. 当数据发送完毕之后，会再次调用【wait】进入等待。当视图层完成渲染，页面展示在前台之后，视图层发送通知，逻辑层调用周期函数【onReady】，此时视图层进入活动状态
3. 当需要更新参数时候，调用【setData】通知视图层更新视图

### 视图层

1. start
  1. 初始化视图层，完成视图层的初始化工作
2. inited
  1. 初始化完成，视图线程通知逻辑层，等待逻辑层发送初始渲染数据，
3. get data
  1. 收到初始化数据，完成首次渲染，此时页面展示在前台？
4. ready
  1. 渲染完毕，调用【wait】等待逻辑层通知视图更新。

## 自定义导航栏

1. app.json里面配置window参数的【navigationStyle】属性，默认为【default】，设置为【custom】，则顶部的标题栏会小时，需要自定义标题栏
  1. 如何确定标题栏的高度？
  2. 宽度如果超出会怎么样？
  3. 会不会出现其他的一些问题？

## 自定义组件

### 自定义扩展组件【基础库2.2.3开始支持】

自定义组件的扩展其实就是提供了修改自定义组件定义段的能力。

```javascript
//  调用内置函数 Behavior
//  definitionFilter 是一个函数，在被调用时会注入两个参数，第一个参数是使用该 behavior 的 component/behavior 的定义对象，第二个参数是该 behavior 所使用的 behavior 的 definitionFilter 函数列表。
module.exports=Behavior({
  definitionFilter(defField){
    defField.data.a=1
    defField.data.b=2
  }
})

//  在组件Component定义behavior字段。注意该字段只在Component内有效
Component({
  behavoid:[require('behavoir.js')],
  ready(){

  }
})

//  构建
```


### behavior

预先定义通用的数据或者方法或者声明周期函数，然后通过Page的behavior属性添加进去

合并规则：

1. 如果有同名的属性或方法，组件本身的属性或方法会覆盖 behavior 中的属性或方法，如果引用了多个 behavior ，在定义段中靠后 behavior 中的属性或方法会覆盖靠前的属性或方法；
2. 如果有同名的数据字段，如果数据是对象类型，会进行对象合并，如果是非对象类型则会进行相互覆盖；
3. 生命周期函数不会相互覆盖，而是在对应触发时机被逐个调用。如果同一个 behavior 被一个组件多次引用，它定义的生命周期函数只会被执行一次。

```javascript
Page({
  behaviors:[require('./behavior1.js')]
})
```