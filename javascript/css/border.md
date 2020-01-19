# border

## border-image

组合属性，以image作为边框的样式，会覆盖默认的border-style。需要定义border属性才会生效？

> border-image-width

设置边框的宽高【必须设置】。

```css
.border-image-width{
  border-image-width:10px;  // all
  border-image-width:10px 20px ;  //  top/bottom left/right
  border-image-width:10px 20px 30px ; top left/right bottom
  border-image-width:10px 20px 30px 40px; top right bottom left
}
```

> border-image-outset


> border-image-source

定义作为边框的图片资源【必须设置】

> border-image-slice

【必须设置】最重要的定义[https://developer.mozilla.org/zh-CN/docs/Web/CSS/border-image-slice](https://developer.mozilla.org/zh-CN/docs/Web/CSS/border-image-slice)！

通过border-image-source 引用边框图片后，border-image-slice属性会将图片分割为9个区域：四个角，四个边（edges）以及中心区域。四条切片线，从它们各自的侧面设置给定距离，控制区域的大小。

|--|--|--|
| 1   | 5  | 2   |
| 8   | 9  | 6   |
| 4   | 7  | 3   |

- 区域 1-4 为角区域（corner region）。 每一个都用一次来形成最终边界图像的角点。（Each one is used a single time to form the corners of the final border image.）
- 区域 5-8 边区域（edge region）。在最终的边框图像中重复，缩放或修改它们以匹配元素的尺寸。（These are repeated, scaled, or otherwise modified in the final border image to match the dimensions of the element.）
- 区域 9 为中心区域（ middle region）。它在默认情况下会被丢弃，但如果设置了关键字fill，则会将其用作背景图像。（It is discarded by default, but is used like a background image if the keyword fill is set.）

```css
/* 所有的边 */
border-image-slice: 30%; 

/* 垂直方向 | 水平方向 */
border-image-slice: 10% 30%;

/* 顶部 | 水平方向 | 底部 */
border-image-slice: 30 30% 45;

/* 上 右 下 左 */
border-image-slice: 7 12 14 5; 

/* 使用fill（fill可以放在任意位置） */
border-image-slice: 10% fill 7 12;
```

> border-image-repeat

定义边框的平铺方式，可以为一个值或者两个值，一个值定义所有，两个值分别定义水平和垂直方向

stretch
拉伸图片以填充边框。
repeat
平铺图片以填充边框。
round
平铺图像。当不能整数次平铺时，根据情况放大或缩小图像。
space
平铺图像 。当不能整数次平铺时，会用空白间隙填充在图像周围（不会放大或缩小图像）

```css
.image{
  border-image-repeat:stretch; /* 拉伸图片 */
  border-image-repeat:repeat; /* 重复图片 */
  border-image-repeat:round; /* 重复图片，当空间不足以容下的时候，会压缩或者拉伸 */
  border-image-repeat:space;  /*  */
  border-image-repeat:space round;  /*  */
}
```