# transition & animation

[https://3dtransforms.desandro.com/](https://3dtransforms.desandro.com/)

## notice

> transform-origin

1. 转换操作，会把坐标原点一起转换，之后的操作是在转换后的坐标原点基础之上进行的，当transform伴随着多个属性的时候需要注意。
2. 所有变换都是基于原点的，rotate2d旋转基点为坐标原点，rotate3d则是各个坐标轴，scale在3d中也是各个坐标轴
3. 对于y轴 ，参数正值为顺着轴的正方向顺时针选择，负值为逆时针旋转；对于x轴则是相反的！

```css
{
  /*
    2d转换定义转换的基点，3d可以定义z轴
    默认是center center
    可以使用百分比，0为元素最左边/最上边，100%为元素最右边/最下边
    x轴可以定义：left center right x%
    y轴可以定义：top center bottom x%
    如果使用rotate则两轴交点为基点！
    分别定义x，y的基点坐标，例如 transform-origin:left top;
    表示x轴基点的在最左边那条线，y轴的基点在最上边的那条线，两线交点即为坐标原点，以该坐标绘制坐标轴即为图形变化轴？？？？

    如果有位移的转换，需要先进行位移，因为先做其他操作例如旋转，会把坐标轴一起旋转，之后进行位移是按照旋转的坐标轴进行的！？
  */
  transform-origin:center center 0; 

  /*
    定义内部元素的展示方式，默认为【flat】平面展示，【preserve-3d】3d展示
  */
  transform-style:preserve-3d;  
}
```

## transition 2d

> translateX(x),translateY(y),translate(x,y)

正值为x轴正方向/y轴负方向，负值为x轴负方向/y轴正方向

> scale(x,y),scaleX(),scale(x,y)

> rotate(deg)

正值为顺时针旋转，负值为逆时针选择

> skewX(x),skewY(y),skew(x,y)

## transition 3d

> translateZ(z),translate3d(x,y,z)

z轴正值为向开开发者方向，负值为远离开发者方向

> scale3d(x,y,z),scaleZ(z)

> rotateX(x),rotateY(y),rotateX(z),rotate3d(x,y,z)

rotate3d(x,y,z)中z只能是0或者1？0表示隐藏？

> perspective(n)

## animation