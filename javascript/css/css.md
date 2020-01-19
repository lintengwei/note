- [css](#css)
  - [基本概念](#%e5%9f%ba%e6%9c%ac%e6%a6%82%e5%bf%b5)
  - [定位](#%e5%ae%9a%e4%bd%8d)
  - [文本](#%e6%96%87%e6%9c%ac)
  - [媒体查询](#%e5%aa%92%e4%bd%93%e6%9f%a5%e8%af%a2)
  - [动画](#%e5%8a%a8%e7%94%bb)
  - [过渡](#%e8%bf%87%e6%b8%a1)
  - [效果](#%e6%95%88%e6%9e%9c)
    - [filter](#filter)
    - [box-shadow](#box-shadow)
    - [border-image](#border-image)
  - [格式化上下文](#%e6%a0%bc%e5%bc%8f%e5%8c%96%e4%b8%8a%e4%b8%8b%e6%96%87)
    - [BFC](#bfc)
    - [IFC](#ifc)
  - [特殊属性](#%e7%89%b9%e6%ae%8a%e5%b1%9e%e6%80%a7)
  - [FAQ](#faq)

# css

[css参考手册](http://css.cuishifeng.cn/index.html)
[css 规范](https://www.w3.org/Style/CSS/current-work)
[docs](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference)
[https://developer.mozilla.org/zh-CN/docs/Web/CSS](https://developer.mozilla.org/zh-CN/docs/Web/CSS)
[https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS](https://developer.mozilla.org/zh-CN/docs/Web/Guide/CSS)
[http://css.doyoe.com/](http://css.doyoe.com/)

## 基本概念

包含块

[docs](https://developer.mozilla.org/zh-CN/docs/Web/CSS/All_About_The_Containing_Block)。元素的尺寸及位置，常常会受它的包含块所影响。对于一些属性，例如 width, height, padding, margin，绝对定位元素的偏移值 （比如 position 被设置为 absolute 或 fixed），当我们对其赋予百分比值时，这些值的计算值，就是通过元素的包含块计算得来。大多数情况下，包含块就是这个元素最近的祖先块元素的内容区，但也不是总是这样。

> 如何确定一个包含块

确定一个元素的包含块的过程完全依赖于这个元素的 position 属性：

1. 如果 position 属性为 static 或 relative ，包含块就是由它的最近的祖先块元素（比如说 inline-block, block 或 list-item 元素）或格式化上下文(比如说 a table container, flex container, grid container, or the block container itself)的内容区的边缘组成的。
2. 如果 position 属性为 absolute ，包含块就是由它的最近的 position 的值不是 static （也就是值为 fixed, absolute, relative 或 sticky）的祖先元素的内边距区的边缘组成。
3. 如果 position 属性是 fixed，包含块就是由 viewport (in the case of continuous media) 或是组成的。

## 定位

当值为 absolute 绝对定位的时候，会脱离文档流，并且按照其之前的兄弟节点位置之后排布，并且层级都比正常文档流的高

```css
.nav {
  /* 吸附效果，当在可视区域内会随滚动，当要离开可视区域内时，会吸附在顶部 */
  position: sticky;
}
```

## 文本

```css

p{
  /**
   * overflow wrap css属性应用于内联元素，设置浏览器是否应在不可断字符串中插入换行符，以防止文本溢出其行框。
   * */
  word-wrap:normal|break-word;
  overflow-wrap:normal|break-word;

  /**
   * 定义当文本超出内容盒子的时候的换行方式
   * normal 正常模式，该模式下遇到空格符，当内容区不能容下空格符两边的文本的时候，会把后面的文本换行，如果某个文本（不存在空格）超过内容区不会换行
   * break-all  当超出内容区的时候换行。但是不会1以单词作为换行一句，而是最大限度的排布，所以对于英文而言，有可能会打断某个单词的
   * keep-all 分词对于中文，日文，韩文，当超过内容区的时候会换行，但是对于英文文本会按照normal方式
   * break-word 当超出内容区的时候换行。跟break-all唯一的不同就是该模式下会以单词作为换行的依据，不存在单个单词分在两行的情况
   * */
  word-break:normal|break-all|keep-all|break-word;


  /* 小写|大写|首字母大写 */
  text-transform: lowercase|uppercase|capitalize;
  /* 指定元素是否保留文本间的空格、换行；超出边界是否换行
  normal：
默认处理方式。会将序列的空格合并为一个，内部是否换行由换行规则决定，默认换行符不起作用。
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

block-format-context，块级格式化上下文。
如何生成一个BFC如下：

1. 根元素
2. 浮动元素
3. 绝对定位元素（absolute，fixed）
4. inline-block元素
5. table-cell元素
6. overflow不为visible的元素
7. 弹性元素，网格元素（display的值为flex，grid的元素）

BFC布局规则：

1. 内部的盒子会在垂直方向，一个个地放置；
2. 盒子垂直方向的距离由margin决定，属于同一个BFC的两个相邻Box的上下margin会发生重叠；
3. 每个元素的左边，与包含的盒子的左边相接触，即使存在浮动也是如此；
4. BFC的区域不会与float重叠；
5. BFC就是页面上的一个隔离的独立容器，容器里面的子元素不会影响到外面的元素，反之也如此；
6. 计算BFC的高度时，浮动元素也参与计算。

### IFC

inline-format-context，行级格式化上下文。规则如下：

1. 内部的盒子会在水平方向，一个个地放置；
2. IFC的高度，由里面最高盒子的高度决定？；
3. 当一行不够放置的时候会自动切换到下一行；

## 特殊属性

```css
/* 限制文本的行数，需要四个属性一起使用，ie下无效，其他都可以 */
p{
    width: 300px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
}
```

## FAQ

1. transform 为什么对于行内元素不起作用？

