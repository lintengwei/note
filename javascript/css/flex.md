
# flex 布局

[https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Flexible_Box_Layout/Aligning_Items_in_a_Flex_Container](https://developer.mozilla.org/zh-CN/docs/Web/CSS/CSS_Flexible_Box_Layout/Aligning_Items_in_a_Flex_Container)

## 基本概念

## flex-box 属性

### flex-direction

子元素排布方向

### flex-wrap

子元素超出容器时候的换行规则

### justify-content

控制 flex-item 在主轴上的布局方式

### align-items

控制 flex-item 在交叉轴上的布局方式.默认是【stretch】适应 flexbox 的高度，flexbox 的高度由 flex-item 的最大值决定.

### align-content

CSS 的 align-content 属性设置了浏览器如何沿着伸缩盒子容器（flexbox container）的横轴和网格容器（Grid Container）的主轴在内容项之间和周围分配空间。
该属性对单行弹性盒子模型无效。（即：带有 flex-wrap: nowrap）。

## flex-item 属性

疑问：

1. 为了计算出有多少可用空间能布局于 flex 子元素, 浏览器必须知道这个 item 有多大才能开始. 它是如何解决没有应用于绝对单位的宽度和高度的 flex 子元素?

两个概念【min-content】和【max-content】用于设置元素的大小

【min-content】
元素内容会尽可能的自动换行，变得尽可能的小并且不会移出容器
【max-content】
跟【min-content】正好想法，会尽可能大，不会自动换行，如果容器太窄会溢出

### flex-basis

flex-basis 属性在任何空间分配发生之前初始化 flex 子元素的尺寸.是否是用于计算剩余空间的。

如果你的flex子元素 为自动调整大小， 则auto 会解析为其内容的大小.  此时你所熟知的min-content和max-content大小会变得有用,  flexbox 会将flex子元素的 max-content 大小作为 flex-basis. 下面的例子可以证明这一点。解释了为什么内置的文本不会自动换行，因为max-content作为内容区，所有不会自动换行

> flex-basis:auto

为该属性的默认值。如果对 item 有设置具体的尺寸，那么 flex-basis 的值为具体的值，如果没有设置具体的尺寸，那么会根据 item 的实际内容宽度来设置。如果子项的大小加起来小于容器的大小。那么之后设置 flex-grow 的值可以让 item 分剩余的控件。如果没有剩余空间，这个值无意义？

> flex-basis:0

如果 flex-basis 设置为 0 表示 flexbox 收回所有 item 占据的空间，并且根据【flex-grow】和【flex-shrink】来进行空间的分配。但是如果某个子项的实际大小超出了 flexbox 分配的空间，还是会按照实际的大小进行布局。除非设置了【overflow】属性?

```html
<div class="box">
  <div>1</div>
  <div>2</div>
  <div>
    33333333333333333333333333333333333333333333333333333333333333333333333333333333
  </div>
</div>
```

```css
/* 如果按照如下设置，收回item的宽度，所有item均分，但是最后一个item明显均分的内容不够，所以会按照内容大小布局 */
/* 但是当我们设置哦【overflow】来控制最后一个item超出内容的样式之后，所有item会均分空间 */
.box {
  display: flex;
  width: 200px;
}
.box > div {
  flex: 1 1 0;
}
.box > div:last-of-type {
  overflow: hidden;
}
```

### flex-grow

根据设置的值来分配剩余空间【positive-space】来填充 flexbox 的大小。
如果所有 item 的值加起来小于 flexbox 的值，则会分局【flex-grow】设置的值来分配空间

### flex-shrink

根据设置的值来缩小 flex-item 的空间【negative-space】以适应 flexbox 的大小。
如果所有的 item 加起来大于 flexbox 的值，那么设置这个值会把超过的空间分布到各个 item

### align-self

控制 flex-item 自己在交叉轴上的布局方式，这个设置会覆盖【align-items】设置的值
