# css 基线

[https://www.zhangxinxu.com/wordpress/2015/08/css-deep-understand-vertical-align-and-line-height/](https://www.zhangxinxu.com/wordpress/2015/08/css-deep-understand-vertical-align-and-line-height/)
[https://blog.csdn.net/it_queen/article/details/54729949](https://blog.csdn.net/it_queen/article/details/54729949)

> 基线|底线|中线|顶线|行框顶|行框底

![https://img-blog.csdn.net/20170125141742563?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSVRfUXVlZW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast](https://img-blog.csdn.net/20170125141742563?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSVRfUXVlZW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

> 内容区

内容区是指底线和顶线包裹的区域，就是可见区域（行内元素display:inline;可以通过background-color属性显示出来），实际中不一定看得到，但确实存在。内容区的大小依据font-size的值和字数进行变化。可以通过设置background来显示内容区。

> 行内框

虚拟的框模型，每个inline元素都会生成一个行内框，由line-height决定，line-height越大，行内框越大。如果文本没被inline包裹，会自动生成匿名行内框，跟其他inline一样，只是所有属性都继承父元素的。

inline级别的元素的内容区：

> 行框

找到所有之内的行内框的最大值，其他的行内框根据设置的vertical-aligin进行布局，等完成布局之后，找到顶线最大的作为行框的顶部，底线最小的作为行框的底部？

行框高度的计算规则：

1. 会计算所有inline-level级别的元素的高度，对于可替换元素（例如img），inline-block元素，inline-table元素，高度为其外边框之间的高度，而对于inline-level的元素（span，a等），高度为其的line-height值
2. 而对于inline-level级别的元素，可以通过设置vertical-align来修改垂直方向的对齐方式
3. 经过计算各个inline-level级别的高度以及经过设置vertical-align的对齐方式之后，来布局行内元素，最后行框的顶部为行内框最高的那个，而行框的底部为行内框最低的那个，两者之差即为line-box的高度！

==CSS 2.1没有定义inline-level的内容区域是什么，因此不同的UA可以在不同的地方绘制背景和边界。==

> 基线

基线（base line）并不是汉字文字的下端沿，而是英文字母“x”的下端沿

> 底线

内容区的低部

> 中线

字符x的中间位置

> 顶线

内容区的顶部

> 行内框顶部

line-height的值减去内容区的高度除以2得到的值加到内容区的顶部，如果为正值，高于内容区，负值低于内容区

> 行内框底部

line-height的值减去内容区的高度除以2得到的值加到内容区的底部，如果为正值，低于内容区，负值高于内容区

> 行框顶

由所有行内框顶部的最高值决定

> 行框底

由所有行内框底部的最低值决定

> font-size

决定内容区的高度，当前并未指定如何绘制内容区的高度，因此各个浏览器实现不一致。假设顶线到底线的距离即为font-size的大小，而line-height的大小，决定了顶线到行内框顶部的距离（也是底线到行内框底部的距离），其值为（line-height大小减去font-size的大小），所以如果line-height的值大于font-size，会在两边留白，否则字体会显示错乱。

> line-height 

line-height CSS 属性用于设置多行元素的空间量，如多行文本的间距。对于块级元素，它指定元素行盒（line boxes）的最小高度。对于非替代的 inline 元素，它用于计算行盒（line box）的高度。

- normal
  - 取决于浏览器，各个浏览器实现不一致，基本为1.2
- 数字
  - 该属性的应用值是这个无单位数字<数字>乘以该元素的字体大小。计算值与指定值相同。大多数情况下，这是设置line-height的推荐方法，不会在继承时产生不确定的结果。
- 长度
  - 指定<长度>用于计算 line box 的高度。参考<长度>了解可使用的单位。以 em 为单位的值可能会产生不确定的结果（见下面的例子）
- 百分比
  - 与元素自身的字体大小有关。计算值是给定的百分比值乘以元素计算出的字体大小。百分比值可能会带来不确定的结果

> vertical-align


几个定义：

> 顶部

指的是line-height减去内容区的值除以2得到的值加到内容区的上端和下端，如果值为正数，则顶部高于内容区，底部低于内容区，否则相反（就是行内框的顶部）

> 父元素

指的是该元素的直接父元素

可选值及其说明：

- 相对父元素的值
  - baseline
    - 使元素的基线与父元素的【基线】对齐，如果元素没有基线，则元素的地步和父元素的基线对齐
    - 如何确定父元素的基线
  - sub
    - 使元素的基线与父元素的【下标】对齐
  - super
    - 使元素的基线与父元素的【上标】对齐
  - text-bottom
    - 是元素的【底线】与父元素的【内容区顶部】对齐
  - text-top
    - 是元素的【顶线】与父元素的【内容区顶部】对齐
  - middle
    - 是元素的中线与父元素基线加上父元素x-height的高度对齐
  - length
    - 使元素的基线对齐到父元素基线之上的给定高度，可以为负数
  - percentage
    - 使元素的基线对齐到父元素的基线之上的给定百分比，该百分比是line-height属性的百分比。可以是负数
- 相对行框的值
  - top
    - 使元素及其后代元素的顶部（行内框顶部）与行框的顶部对齐。
  - bottom
    - 使元素及其后代元素的底部（行内框底部）与行框的底部对齐。
- 表格单元格的值
  - baseline
  - top
  - middle
  - bottom

几个问题：

1. 父元素的基线是有最高的行内框决定的？

是

2. 调整行内框最大值的vertical-align，会影响父元素的内容区的定位？如果设置vertical-align:bottom，则该行内框内容区底部会和父元素的内容区底部重合，其他子元素依赖父元素的内容区进行定位？

父元素的内容区会根据行内框最大的元素来进行定位
 

##  注意点

1. 如果父元素设置的line-height为数字，则子元素会继承父元素的line-height值，并且根据自己的font-size来计算line-height。如果父元素设置的line-height为具体的值，则子元素会继承具体的值，不会再次计算line-height

## FAQ

1. 内容区和行内框的区别是什么

内容区是根据font-size来定高度的，而行内框是根据line-height来定高度的