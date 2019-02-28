# vue 动画系统

## 进入/离开

### 单元素和组件的过渡

> 使用场景。实际是通过动态的修改元素的 class 属性来实现过渡效果。实际的效果实现在 css 里面定义。

- 条件渲染 v-if
- 条件展示 v-show
- 动态组件
- 组件根节点

> 初次渲染过渡

默认初次渲染是不会有过渡效果

```html
<!-- 需要设置appear属性 默认为false -->
<!-- animation 设置 appear-active-class即可 -->
<transition
  appear
  appear-class="custom-appear-class"
  appear-to-class="custom-appear-to-class"
  (2.1.8+)
  appear-active-class="custom-appear-active-class"
>
  <!-- ... -->
</transition>
```

> 过渡的类名

==过渡效果完成之后所有类名都会移除，所有在过渡上的定义 css 效果都只是为过渡服务，过渡完成，恢复静态的 css 样式。动画同理==

1. v-enter：在元素被插入前生效。定义元素的初始状态，即从哪里，那种状态开始动画。
2. v-enter-active：在元素被插入前生效，在整个过渡期间都生效。定义过渡的效果、时间等。
3. v-enter-to :定义过渡的结束状态。在元素插入之后生效，此时 v-enter 移出。【2.1.8 实现】
4. v-leave：定义离开的初始状态
5. v-leave-active：作用在整个离开过渡阶段。
6. v-leave-to：定义离开的结束状态。

### 多元素过渡

对于原生标签，可以简单是使用 v-if，v-else 切换显示。如果两者使用的是相同的元素，则需要加上 key 以识别不同的 dom，否则默认的渲染模式会使用相同的元素，只是简单的更换现有 dom 节点的数据。

> 默认的多元素切换是进入和睿出同时进行的。
> mode='out-in'/'in-out'，分别是先退出后进入和先进入后退出

## 列表的过渡

几个特点：

1. 不同于 <transition>，它会以一个真实元素呈现：默认为一个 <span>。你也可以通过 tag 特性更换为其他元素。
2. 过渡模式不可用，因为我们不再相互切换特有的元素。
3. 内部元素 总是需要 提供唯一的 key 属性值。
4. 以 index 作为 key，只有最后一个元素有动画效果

```html
<transition-group name="translate" tag="ul">
  <li v-for="item in items" :key="item">{{item}}</li>
</transition-group>
<!--
  简单的使用，定义相关class，当列表数据变化的时候，相关的item会应用对应的class，在css中设置过渡效果即可。
-->
<!--
  以上的做法有一个缺陷，会有突变的效果。因为只是单纯的作用一个item（类似transition），其他item不做处理。
-->
<!--
  如果需要设置缓冲的效果，则可以使用v-move指令，该指令会根据节点的定位方式，动态的修改class来完成过渡效果
-->
<!--
  在节点插入和删除时候，会把其他受影响的节点动态的 v-move class来执行过渡，应该是会先计算插入或者删除的节点的大小后，来移动受影响的其他节点
-->
<style>
  .translate-enter-active,
  .translate-leave-active {
    transition: all 0.5s;
  }
  .translate-enter {
    transform: translateX(-20px);
    opacity: 0;
  }
  .translate-leave-to {
    transform: translateX(20px);
    opacity: 0;
  }
  .translate-move {
    transition: all 0.5s;
  }
  .translate-leave-active {
    position: absolute;
    width: 100%;
  }
</style>
```
