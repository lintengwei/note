- [svg](#svg)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
  - [svg元素通用属性](#svg%E5%85%83%E7%B4%A0%E9%80%9A%E7%94%A8%E5%B1%9E%E6%80%A7)
  - [基本形状](#%E5%9F%BA%E6%9C%AC%E5%BD%A2%E7%8A%B6)
    - [路径](#%E8%B7%AF%E5%BE%84)
  - [定义引用](#%E5%AE%9A%E4%B9%89%E5%BC%95%E7%94%A8)
  - [动画元素](#%E5%8A%A8%E7%94%BB%E5%85%83%E7%B4%A0)
  - [文本](#%E6%96%87%E6%9C%AC)
  - [裁剪和遮罩](#%E8%A3%81%E5%89%AA%E5%92%8C%E9%81%AE%E7%BD%A9)
  - [注意点](#%E6%B3%A8%E6%84%8F%E7%82%B9)

# svg

[svg教程](https://developer.mozilla.org/zh-CN/docs/Web/SVG/Tutorial)
[svg元素](https://developer.mozilla.org/zh-CN/docs/Web/SVG/Element)
[svg元素属性](https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute)

## 基本使用

1. 从svg根元素开始：
  - 应舍弃来自 (X)HTML的doctype声明，因为基于SVG的DTD验证导致的问题比它能解决的问题更多。
  - 属性version和属性baseProfile属性是必不可少的，供其它类型的验证方式确定SVG版本。
  - 作为XML的一种方言，SVG必须正确的绑定命名空间 （在xmlns属性中绑定）。 请阅读命名空间速成 页面获取更多信息。
2. 绘制一个完全覆盖图像区域的矩形 <rect/>，把背景颜色设为红色。
3. 一个半径80px的绿色圆圈<circle/>绘制在红色矩形的正中央 （向右偏移150px，向下偏移100px）。
4. 绘制文字“SVG”。文字被填充为白色， 通过设置居中的锚点把文字定位到期望的位置：在这种情况下，中心点应该对应于绿色圆圈的中点。还可以精细调整字体大小和垂直位置，确保最后的样式是美观的。

```xml
<svg version="1.1"
     baseProfile="full"
     width="300" height="200"
     xmlns="http://www.w3.org/2000/svg">
  <!-- 绘制一个矩形，填充颜色为红色 -->
  <rect width="100%" height="100%" fill="red" />
  <!-- 绘制一个圆形，填充颜色为绿色 -->
  <circle cx="150" cy="100" r="80" fill="green" />
  <!-- 绘制文本，起点为（150，125），字体为60px，白色 -->
  <text x="150" y="125" font-size="60" text-anchor="middle" fill="white">SVG</text>
</svg>
```

## svg元素通用属性

该部分描述了svg元素的部分通用属性的说明和使用

## 基本形状

```xml
<svg>
  <!-- 矩形 -->
  <rect width='' height='' x='' y='' fill='color' stroke='color' ></rect>
  <!-- 圆形 -->
  <circle cx='' cy='' r='radius' stroke='' fill='' ></circle>
  <!-- 椭圆 -->
  <ellipse cx='' cy='' rx='x半径' ry='y半径'></ellipse>
  <!-- 线条 -->
  <line x1='' y1='' x2='' y2=''></line>
  <!-- 折线 -->
  <!-- points为点的左边，每两个一组，连成线 -->
  <polyline points='0,0,10,20,50,30' ></polyline>
  <!-- 多边形 -->
  <!-- 点集数列。每个数字用空白符、逗号、终止命令或者换行符分隔开。每个点必须包含2个数字，一个是x坐标，一个是y坐标。所以点列表 (0,0), (1,1) 和(2,2)可以写成这样：“0 0, 1 1, 2 2”。路径绘制完后闭合图形，所以最终的直线将从位置(2,2)连接到位置(0,0)。 -->
  <polygon points='50 160, 55 180, 70 180, 60 190, 65 205, 50 195, 35 205, 40 190, 30 180, 45 180'></polygon>
  <!-- 路径 -->
  <path d='M 20 230 Q 40 205, 50 230 T 90 230'></path>
</svg>
```

### 路径

注意点：

1. 默认是会填充的，并且填充颜色为黑色，需要手动设置[fill='transparent']
2. L相对于svg坐标的绝对位置，l相对于当前点的位置

[api](https://developer.mozilla.org/zh-CN/docs/Web/SVG/Tutorial/Paths)。path元素的形状是通过属性d定义的，属性d的值是一个“命令+参数”的序列，我们将讲解这些可用的命令，并且展示一些示例。
[svg动画](https://juejin.im/entry/58b7bae944d904006ac9c4af)

指令：

- M x y = moveto（移动到）
- L x y= lineto（点到点的连接）
- H x= horizontal lineto（水平连接到点）
- V y= vertical lineto（垂直连接到点）
- C x1 y1 ,x2 y2,x y= curveto（三次贝塞尔曲线）
  - 两个控制点和终点
- S x2 y2, x y = smooth curveto（三次贝塞尔曲线的对称写法）
  - S命令可以用来创建与前面一样的贝塞尔曲线，但是，如果S命令跟在一个C或S命令后面，则它的第一个控制点会被假设成前一个命令曲线的第二个控制点的中心对称点。如果S命令单独使用，前面没有C或S命令，那当前点将作为第一个控制点。下面是S命令的语法示例，图中左侧红色标记的点对应的控制点即为蓝色标记点。
- Q x1 y1, x y  = quadratic Belzier curve（二次贝塞尔曲线）
  - 一个控制点和终点
- T x y = smooth quadratic Belzier curveto（二次贝塞尔曲线的对称写法）
  - 和之前一样，快捷命令T会通过前一个控制点，推断出一个新的控制点。这意味着，在你的第一个控制点后面，可以只定义终点，就创建出一个相当复杂的曲线。需要注意的是，T命令前面必须是一个Q命令，或者是另一个T命令，才能达到这种效果。如果T单独使用，那么控制点就会被认为和终点是同一个点，所以画出来的将是一条直线。
- A rx ry x-axis-rotation large-arc-flag sweep-flag x y = elliptical Arc（圆弧）
  - x半径
  - y半径
  - 圆弧旋转情况（默认是水平放置，正数表示顺时针旋转）
  - 决定弧线是大于还是小于180度，0表示小角度弧，1表示大角度弧
  - 表示弧线的方向，0表示从起点到终点沿逆时针画弧，1表示从起点到终点沿顺时针画弧
  - 圆弧终点的坐标
- Z = closepath（闭合曲线）
  - 默认不闭合，会填充

```javascript
const path=document.getElementById('path')

path.getTotalLength() //  获取路径的总长度
```

## 定义引用

```xml
<svg>
<!-- 定义一个模版 -->
<symbol id="sym01" viewBox="0 0 150 110">
  <circle cx="50" cy="50" r="40" stroke-width="8" stroke="red" fill="red"/>
  <circle cx="90" cy="60" r="40" stroke-width="8" stroke="green" fill="white"/>
</symbol>

<!-- 引用之前定义的模版 -->
<use xlink:href="#sym01"
     x="0" y="0" width="100" height="50"/>
<use xlink:href="#sym01"
     x="0" y="50" width="75" height="38"/>
<use xlink:href="#sym01"
     x="0" y="100" width="50" height="25"/>
</svg>
```

## 动画元素

```xml
<svg>
<defs>
  <!-- 定义路径 -->
  <path d='' id='path1'></path>
</defs>
<text>
  <textpath startOffset='30'>
    <!-- 1.通过在元素内部通过设置animate标签来定义动画 -->
    <!-- 分别为 属性名，属性类型，开始值，结束值，持续时间，重复次数 -->
    <animate
      attributeName=''
      attrubuteType=''
      from=''
      to=''
      dur='10s'
      repeatCount=''
    ></animate>
  </textpath>
</text>
<circle>
  <!-- animateMotion元素导致引用的元素沿着运动【路径path】移动 -->
  <!-- 定义动画持续时间和执行次数 -->
  <!-- 只能定义path吗？ -->
  <animateMotion dur='6s' repeatCount='indefinite'>
    <!-- 路径 -->
    <mpath xlink:href='#path1'></mpath>
  </animateMotion>
</circle>
</svg>
```

## 文本

文本的一个至关重要的部分是它显示的字体。SVG提供了一些属性，类似于它们的CSS同行，用来激活文本选区。下列每个属性可以被设置为一个SVG属性或者成为一个CSS声明：font-family、font-style、font-weight、font-variant、font-stretch、font-size、font-size-adjust、kerning、letter-spacing、word-spacing和text-decoration。

```xml
<text id='txt'>
  <tspan font-weight="bold" fill="red">This is bold and red</tspan>
</text>
```

```javascript
const txt=document.getElementById('txt')

txt.getComputedTextLength() //  获取文本长度
```

## 裁剪和遮罩

```xml
<!-- 裁剪 -->
<svg version='1.1' xmlns='http://www.w3.org/2000/svg'>
  <defs>
    <clipPath id='clip'>
      <rect x='0' y='0' width='100' height='100'></rect>
    </clipPath>
  </defs>
  <!-- 只有在矩形[0,0,100,100]里面的部分才会绘制出来 -->
  <circle x='100' y='100' r='50' clip-path='url(#clip)' ></circle>
<svg>

<!-- 遮罩 -->
<svg
  id="svg"
  version="1.1"
  width="200"
  height="200"
  xmlns="http://www.w3.org/2000/svg"
>
  <defs>
    <linearGradient id="Gradient">
      <stop offset="0" stop-color="white" stop-opacity="0" />
      <stop offset="1" stop-color="white" stop-opacity="1" />
    </linearGradient>
    <mask id="Mask">
      <rect x="0" y="0" width="200" height="200" fill="url(#Gradient)" />
    </mask>
  </defs>

  <rect x="0" y="0" width="200" height="200" fill="green" />
  <rect x="0" y="0" width="200" height="200" fill="red" mask="url(#Mask)" />
</svg>

```

## 注意点

1. 所有的SVG元素的初始display值都是inline。