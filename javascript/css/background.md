# background 

## background-color

定义背景颜色

## background-image

定义背景图，可以定义多个用逗号分隔，最先定义的图位于最上层。

## background-clip

> border-box | content-box | padding-box | text

设置元素的背景（背景图片或颜色）是否延伸到边框下面。

## background-repeat

CSS 属性定义背景图像的重复方式。背景图像可以沿着水平轴，垂直轴，两个轴重复，或者根本不重复。。默认是repeat=== repeat repeat
默认是以background-position的位置来铺设图片的，如果

```css
.img{
  /* 定义x、y轴的是否重复 */
  background-repeat:x y;
  /* x y都重复 等价于 repeat repeat
     会裁剪图片
   */
  background-repeat:repeat ;
  background-repeat:repeat-x ;
  background-repeat:repeat-y ;

  /* background-position会被忽略
     不会裁剪图片，以图片尺寸来铺设背景，知道空间不足以容下完整的图片，则停止铺设，并且多余的空白空间会均匀的分布在图片之间
  */
  background-repeat:space ;

  /* 会压缩图片，当空间不足以容下图片的正常尺寸的时候，会通过拉伸或者压缩的方式来完成铺设（具体是拉升还是压缩看剩余空间的多少？）可能是大于50%时候是压缩，小于50%时候是拉伸 */
  background-repeat:round ;
  background-repeat:no-repeat ;
}
```

## background-position

设置背景图片的起始位置

<bg-position>#
where 
<bg-position> = [ [ left | center | right | top | bottom | <length-percentage> ] | [ left | center | right | <length-percentage> ] [ top | center | bottom | <length-percentage> ] | [ center | [ left | right ] <length-percentage>? ] && [ center | [ top | bottom ] <length-percentage>? ] ]

where 
<length-percentage> = <length> | <percentage>

```css
.bg{
  background: background-position/background-size; /* 可以简写 */
}
.bg{
  background-position:top center; /* 位于背景区顶部，中部 */
  background-position:top left; /* 位于背景区顶部，左部 */
  background-position:bottom left; /* 位于背景区底部，左部 */
  background-position:bottom 20px left 30px; /* 位于背景区底部距离20px，左部距离30px */
  background-position:bottom 20% left 30%; /* 位于背景区底部距离20%，左部距离30% ，可能计算方式不同？ */
}
```

## background-size

设置背景图片大小。图片可以保有其原有的尺寸，或者拉伸到新的尺寸，或者在保持其原有比例的同时缩放到元素的可用空间的尺寸。
可以使用多个数值来设置多个背景的尺寸，用逗号分隔

> cover | contain | x y

cover：缩放背景图以完全覆盖背景区，可能图片的某些部分会不可见
contain：缩放图片是图片在背景区完全可见，可能会使背景区部分留白（如果未设置backgroud-repeat）
x y：指定具体的数值来设置背景图片的大小
x% y%：指定百分比，指背景图相对背景区的百分比

## backgroud-origin

指定背景铺设的范围。默认值为padding-box

> content-box | padding-box | border-box

## background-attachment

> local

相对于内容固定

> scroll

相对于容器固定，一般默认是这个模式

> fixed

相对于视口固定，相当于以视口作为容器来设置背景！如果在其上设置其他属性，例如background-size等都是相对于视口设置！