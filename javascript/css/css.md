- [css](#css)
  - [基本概念](#%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5)
  - [定位](#%E5%AE%9A%E4%BD%8D)
  - [文本](#%E6%96%87%E6%9C%AC)
  - [媒体查询](#%E5%AA%92%E4%BD%93%E6%9F%A5%E8%AF%A2)
  - [动画](#%E5%8A%A8%E7%94%BB)
  - [过渡](#%E8%BF%87%E6%B8%A1)
  - [效果](#%E6%95%88%E6%9E%9C)
    - [filter](#filter)
    - [box-shadow](#box-shadow)
    - [border-image](#border-image)
  - [格式化上下文](#%E6%A0%BC%E5%BC%8F%E5%8C%96%E4%B8%8A%E4%B8%8B%E6%96%87)
    - [BFC](#bfc)
    - [IFC](#ifc)
    - [FAQ](#faq)

# css

[css 规范](https://www.w3.org/Style/CSS/#specs)
[https://developer.mozilla.org/zh-CN/docs/Web/CSS](https://developer.mozilla.org/zh-CN/docs/Web/CSS)
[https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS](https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS)
[http://css.doyoe.com/](http://css.doyoe.com/)

## 基本概念

包含块

[docs](https://developer.mozilla.org/zh-CN/docs/Web/CSS/All_About_The_Containing_Block)。元素的尺寸及位置，常常会受它的包含块所影响。对于一些属性，例如 width, height, padding, margin，绝对定位元素的偏移值 （比如 position 被设置为 absolute 或 fixed），当我们对其赋予百分比值时，这些值的计算值，就是通过元素的包含块计算得来。

> 如何确定一个包含块

1. 如果 position 属性为 static 或 relative ，包含块就是由它的最近的祖先块元素（比如说 inline-block, block 或 list-item 元素）或格式化上下文(比如说 a table container, flex container, grid container, or the block container itself)的内容区的边缘组成的。
2. 如果 position 属性为 absolute ，包含块就是由它的最近的 position 的值不是 static （也就是值为 fixed, absolute, relative 或 sticky）的祖先元素的内边距区的边缘组成。
3. 如果 position 属性是 fixed，包含块就是由 viewport (in the case of continuous media) 或是组成的。
4. 如果 position 属性是 absolute 或 fixed，包含块也可能是由满足以下条件的最近父级元素的内边距区的边缘组成的：

## 定位

当值为 absolute 绝对定位的时候，会脱离文档流，并且按照其之前的兄弟节点位置之后排布，并且层级都比正常文档流的高

```css
.nav {
  /* 吸附效果，当在可视区域内会随滚动，当要离开可视区域内时，会吸附在顶部 */
  position: sticky;
}
```

## 文本

1. 当指定盒子模型的长度的时候，如果内容超出盒子不会换行？

```css
.div {
  word-wrap: break-word;
}
```

1. 每个盒子模型都有一个字体基线，子元素的字体会按照这个几线来调整子元素的位置，如果是多行的话会按照最后一行来对齐？

```css
p {
  /* 小写|大写|首字母大写 */
  text-transform: lowercase|uppercase|capitalize;
  /* 指定元素是否保留文本间的空格、换行；超出边界是否换行
  normal：
默认处理方式。会将序列的空格合并为一个，内部是否换行由换行规则决定。
pre：
原封不动的保留你输入时的状态，空格、换行都会保留，并且当文字超出边界时不换行。等同 pre 元素效果
nowrap：
与normal值一致，不同的是会强制所有文本在同一行内显示。
pre-wrap：
与pre值一致，不同的是文字超出边界时将自动换行。
pre-line：
与normal值一致，但是会保留文本输入时的换行。
  * */
  white-space: normal|pre|nowrap|pre-wrap|pre-line;
}
```

## 媒体查询

[https://developer.mozilla.org/zh-CN/docs/Web/CSS/@media](https://developer.mozilla.org/zh-CN/docs/Web/CSS/@media)
[https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS/Media_queries](https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS/Media_queries)

语法：@media [media_query_list[,media_query_list]]

<pre>
media_query_list: <media_query> [, <media_query> ]*
media_query: [[only | not]? <media_type> [ and <expression> ]*]
  | <expression> [ and <expression> ]*
expression: ( <media_feature> [: <value>]? )
media_type: all | aural | braille | handheld | print |
  projection | screen | tty | tv | embossed
media_feature: width | min-width | max-width
  | height | min-height | max-height
  | device-width | min-device-width | max-device-width
  | device-height | min-device-height | max-device-height
  | aspect-ratio | min-aspect-ratio | max-aspect-ratio
  | device-aspect-ratio | min-device-aspect-ratio | max-device-aspect-ratio
  | color | min-color | max-color
  | color-index | min-color-index | max-color-index
  | monochrome | min-monochrome | max-monochrome
  | resolution | min-resolution | max-resolution
  | scan | grid
</pre>

```css
/* 没有媒体类型 默认是不是screen */
@media (min-width: 700px) and (orientation: landscape) {
  ...;
}
@media tv and (min-width: 700px) and (orientation: landscape) {
  ...;
}
@media screen and (device-aspect-ratio: 16/9),
  screen and (device-aspect-ratio: 16/10) {
  ...;
}
@import url(example.css) screen and (min-width: 800px);
@import url(example.css) screen and (width: 800px), (color);
```

==note==

1. width/height 是文档窗口的可见区域，而 device-width 是针对整个屏幕的可见区域

## 动画

## 过渡

3D 变换

> transform-style: preserve-3d;

设置 3D 转换

> perspective

设置视距，就是观察者离 z 轴的距离，距离越远越小，越近越大

> perspective-origin

设置观察者的角度，在 xy 平面的位置，结合 perspective 最终确定观察者的位置

> backface-visibility

设置背部是否可见，【visible】【hidden】

## 效果

### filter

[https://developer.mozilla.org/zh-CN/docs/Web/CSS/filter](https://developer.mozilla.org/zh-CN/docs/Web/CSS/filter)。
filter CSS 属性将模糊或颜色偏移等图形效果应用于元素。滤镜通常用于调整图像，背景和边框的渲染。

```css
/*blur() 个图像设置模糊 值越大越模糊 接受长度 不接受百分比*/
img {
  filter: blur(10px);
}
/* brightness() 给图片应用一种线性乘法，使其看起来更亮或更暗 0-1/0-100%  1和100%表示无变化 0为全黑*/
img {
  filter: brightness(0.5) / brightness(50%);
}
/* contrast() 调整图片对比度 1/100%无变化 越大颜色越重*/
img {
  filter: contrast(0.5|50%|2.6);
}
/* drop-shadow(x y radius color) 给图片设置一个阴影效果 */
img {
  filter: drop-shadow(10px 10px 10px red);
}
/* 其他 */
img {
  filter: contrast(175%) brightness(3%) sepia(100%) saturate(200%) opacity(50%) invert(
      100%
    )
    hue-rotate(90deg) grayscale(100%);
}
```

### box-shadow

### border-image

## 格式化上下文

### BFC

### IFC

### FAQ

1. transform 为什么对于行内元素不起作用？
