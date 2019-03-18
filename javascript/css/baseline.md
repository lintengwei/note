# css 基线

[https://blog.csdn.net/it_queen/article/details/54729949](https://blog.csdn.net/it_queen/article/details/54729949)

> 基线|底线|中线|顶线|行框顶|行框底

基线（base line）并不是汉字文字的下端沿，而是英文字母“x”的下端沿

![https://img-blog.csdn.net/20170125141742563?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSVRfUXVlZW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast](https://img-blog.csdn.net/20170125141742563?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvSVRfUXVlZW4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

> 内容区

内容区是指底线和顶线包裹的区域（行内元素 display:inline;可以通过 background-color 属性显示出来），实际中不一定看得到，但确实存在。
==内容区的大小依据 font-size 的值和字数进行变化。==

> 行高|行距

行高（line-height）：包括内容区与以内容区为基础【对称】拓展的空白区域，我们称之为行高。一般情况下，也可以认为是相邻文本行基线间的距离。

行距：指相邻文本间上一个文本行基线和下一个文本行顶线之间的距离。当然，我更愿意认为是（上文本行行高-内容区高度）/2+（下文本行行高-内容区高度）/2。

> 行框

行框（line box），同行内框类似的概念，行框是指本行的一个虚拟的矩形框，也是浏览器渲染模式中的一个概念。行框高度等于本行内所有元素中行内框最大的值。
==以行高值最大的行内框为【基准】，其他行内框采用自己的对齐方式向【基准】对齐，最终计算行框的高度==

> 行内框

行内框是一个浏览器渲染模型中的概念，无法显示出来，但是它又确实存在。==它的高度就是行高指定的高度。==

如何有效的设置对其方式？

- vertical-align
  - baseline 基线对其 默认方式
  - text-bottom 当前盒子的底线和父级【内容区】底线对其
  - text-top 当前盒子的顶线和父级【内容区】顶线对其
  - middle 把当前盒的垂直中心和父级盒的基线加上父级的半 x-height 对齐
  - bottom 当前盒子的底部与【行框】底部对其
  - top 当前盒子的顶部与【行框】顶部对其
