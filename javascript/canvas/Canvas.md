# canvas 的使用

[MDN 关于 canvas 的教程](https://developer.mozilla.org/zh-CN/docs/Web/API/Canvas_API/Tutorial/Basic_usage)

## 注意！！！！

> 除了fillRect，strokeRect方法，其他任何绘制路径的方法，包括线，贝赛尔曲线，圆弧等都需要调用beginPath来清除之前的路径！！！！！！！！

> 除了颜色可以用来设置fillStyle或者strokeStyle之外，其他的比如渐变和图片也可以用来设置

```javascript
const canvas=document.getElementById('canvas')
const ctx=canvas.getContext('2d')

//  颜色
ctx.fillStyle='red'
//  渐变
const linearGradient=ctx.createLinearGradient()
ctx.addColorStop(ratio,color) //  添加节点颜色，可以无限多个
ctx.fillStyle=linearGradient
//  图片
const pattern=ctx.createPattern(image,type)
ctx.fillStyle=pattern
```

## 基本使用

```html
<canvas id="canvas" width="150" height="150" />
```

```javascript
//  获取canvas元素
const canvas = document.getElementById('canvas')
//  获取渲染上下文
//  接收一个参数  2d|
//  之后通过ctx上下文对象来实现对canvas的操作
const ctx = canvas.getContext('2d')

// ctx的属性
//  CanvasRenderingContext2D
ctx={
fillStyle: "#000000", //  填充颜色
filter: "none", //  
font: "10px sans-serif",  //  字体
globalAlpha: 1, //  透明度
globalCompositeOperation: "source-over",  //  设置图层合并模式
imageSmoothingEnabled: true,
imageSmoothingQuality: "low",
lineCap: "butt",  //  
lineDashOffset: 0,
lineJoin: "miter",  
lineWidth: 1, //  宽度
miterLimit: 10, //  
shadowBlur: 0,
shadowColor: "rgba(0, 0, 0, 0)",
shadowOffsetX: 0,
shadowOffsetY: 0,
strokeStyle: "#ff0000", //  边框颜色
textAlign: "start", //  字体对齐方式
textBaseline: "alphabetic", //  字体基线
}
```

## 基本对象

### CanvasRenderingContext2D

[docu](https://developer.mozilla.org/zh-CN/docs/Web/API/CanvasRenderingContext2D)

### ImageData

[docu](https://developer.mozilla.org/zh-CN/docs/Web/API/ImageData)

## CanvasGradient 

[docu](https://developer.mozilla.org/zh-CN/docs/Web/API/CanvasGradient)

## 基本概念

### 栅格

html 中定义的 canvas 标签表示 canvas 的有效区域，其中坐上角的坐标为（0，0），一个像素表示一个网格单元

## 绘制基本形状

### 绘制矩形

```javascript
//  可用方法
//  其中x，y分别表示到原点的距离
//  绘制填充矩形，会绘制一个填充颜色的矩形
ctx.fillRect(x, y, width, height)
//  绘制一个边框矩形，边框带颜色
ctx.strokeRect(x, y, width, height)
//  清除矩形区域，清除指定范围内的任何已经绘制的东西？
ctx.clearRect(x, y, width, height)

//  example
function draw() {
  var canvas = document.getElementById('canvas')
  if (canvas.getContext) {
    var ctx = canvas.getContext('2d')

    ctx.fillRect(25, 25, 100, 100)
    ctx.clearRect(45, 45, 60, 60)
    ctx.strokeRect(50, 50, 50, 50)
  }
}
```

### 绘制路径

> 不同于SVG，HTML中的元素canvas只支持一种原生的图形绘制：矩形。所有其他的图形的绘制都至少需要生成一条路径。不过，我们拥有众多路径生成的方法让复杂图形的绘制成为了可能，所以其他图形最后都需要调用fill或者stroke来描边或者填充路径

```javascript
/**
 * 首先，你需要创建路径起始点。
 * 然后你使用画图命令去画出路径。
 * 之后你把路径封闭。
 * 一旦路径生成，你就能通过描边或填充路径区域来渲染图形。
 * */

//  新建一条路径，生成之后，图形绘制命令被指向到路径上的生成路径
//  绘制路径的初始化操作，之后可以调用绘制路径的方法
//  生成路径的第一步叫做beginPath()。本质上，路径是由很多子路径构成，这些子路径都是在一个列表中，所有的子路径（线、弧形、等等）构成图形。而每次这个方法调用之后，列表清空重置，然后我们就可以重新绘制新的图形。
ctx.beginPath()
//  闭合路径之后图形绘制命令又重新指向到上下文中
ctx.closePath()
//  通过线条来绘制图形轮廓
ctx.stroke()
//  通过填充路径的内容区域生成实心的图形
//  自动调用closePath方法
ctx.fill()
//  移动画笔
//  beginPath之后需要首先调用该方法，来指定绘制的起始点
ctx.moveTo(x, y)
//  绘制直线
ctx.lineTo(x, y)

function draw() {
  var canvas = document.getElementById('canvas')
  if (canvas.getContext) {
    var ctx = canvas.getContext('2d')

    // 填充三角形
    ctx.beginPath() //  初始化路径
    ctx.moveTo(25, 25) //  画笔移动到（25，25）坐标
    ctx.lineTo(105, 25) //  连接（25，25）-（105，25）
    ctx.lineTo(25, 105) //  连接（105，25）-（25，105）
    ctx.fill() //  填充内容区域

    // 描边三角形
    ctx.beginPath()
    ctx.moveTo(125, 125) //  画笔移动到（125，125）坐标
    ctx.lineTo(125, 45) //  连接（125，125）-（125，45）
    ctx.lineTo(45, 125) //  连接（125，45）-（45，125）
    ctx.closePath() //  闭合图形，会让七点和重点连接
    ctx.stroke() //
  }
}
```

### 绘制圆弧

> 当完成形状的绘制之后，要调用ctx.fill()或者ctx.stroke()给图形描边或者填充。绘制圆弧也需要调用ctx.beginPath()??

> arc()函数中表示角的单位是弧度，不是角度。角度与弧度的js表达式:弧度=(Math.PI/180)*角度。

```javascript
//  画一个以（x,y）为圆心的以radius为半径的圆弧（圆），从startAngle开始到endAngle结束，按照anticlockwise给定的方向（默认为顺时针）来生成。
//  ctx.arc(0,0,25,0,Math.PI*2) 绘制一个完整的园
ctx.arc(x, y, startAngle, endAngle, anticlockwise)
//  根据给定的控制点和半径画一段圆弧，再以直线连接两个控制点。
ctx.arc(x1, y1, x2, y2, radius)
```

### 二次贝塞尔曲线

```javascript
//  绘制二次贝塞尔曲线，cp1x,cp1y为一个控制点，x,y为结束点。
//  默认以当前画笔所在位置为起始点，调用moveTo可以移动起始点
quadraticCurveTo(cp1x, cp1y, x, y)
//  绘制三次贝塞尔曲线，cp1x,cp1y为控制点一，cp2x,cp2y为控制点二，x,y为结束点。
bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
```

#### 贝赛尔曲线详解

[https://www.jianshu.com/p/0c9b4b681724](https://www.jianshu.com/p/0c9b4b681724)

- 控制点
  - 是起点和终点之间的点？

二阶贝赛尔曲线，三个顶点ABC，在AB上找出D，在BC上找出E，满足AD/AB=BE/BC ，然后连接DE，在DE上找出点F，满足DF/DE=AD/AB=BE/BC，找出所有的F点，然后连接起来就是二阶贝赛尔曲线，其他更高阶同理，最后降成二阶处理即可。每个转折点成为控制点，还包括起点，结束点

## 颜色和样式

```javascript
//  设置填充颜色
ctx.fillStyle = color
//  设置轮廓颜色
ctx.strokeStyle = color
//  设置透明度，全局有效
ctx.globalAlpha=0.0~1.0
//  线形
ctx.lineWidth=value // 线条宽度，默认是1.0
ctx.lineCap=type  //  设置线条末端样式  butt（默认）|round|square
ctx.lineJoin=type //  设置线条和线条间接合处的样式  round|belbel|miter（默认）
ctx.miterLimint=value //  限制当两条线相交时交接处最大长度；所谓交接处长度（斜接长度）是指线条交接处内角顶点到外角顶点的长度。
ctx.getLineDash() //  返回一个包含当前虚线样式，长度为非负偶数的数组。
ctx.setLineDash(segments?:Array<Number>) //  设置当前虚线样式。一组描述线段和间距的数组
ctx.lineDashOffset=value  //  设置虚线样式的起始偏移量。

//  设置渐变
/**
 * @name 构建线性的渐变
 * @param x1,y1 起点坐标
 * @param x2,y2 终点坐标
 * */
const gradient=ctx.createLinearGradient(x1,y1,x2,y2)
//  前三个定义一个以 (x1,y1) 为原点，半径为 r1 的圆，后三个参数则定义另一个以 (x2,y2) 为原点，半径为 r2 的圆。
const gradient=ctx.createRadialGradient(x1,y1,r1,x2,y2,r2)
/**
 * @name 设置中间节点，可以添加多个
 * @position 0~1 表示节点的相对位置
 * @color 表示颜色
 * */
gradient.addColorStop(position,color)
//  把生成的渐变应用到context中
ctx.fillStyle=gradient

/**
 * @name
 * @param image
 * @param type
 * */
ctx.createPattern(image,type)
```

## 绘制文本

```javascript
/**
 * @name  填充文本
 * @param text 绘制的文本
 * @param x 开始的x坐标
 * @param y 开始的y坐标，文字底部算起？
 * @param maxWidth  绘制的最大宽度，超过会截断
 * */
ctx.fillText(text,x,y[,maxWidth])
ctx.strokeText(text,x,y[,maxWidth])

//  设置字体样式
//  设置字体大小 默认的是 10px sans-serif
ctx.font=value
//  设置字体对齐方式 start|end|left|right|center
ctx.textAlign=value
//  设置基线对齐方式 top|bottom|hanging|middle|alghabetic(默认)|ideographic
ctx.textBaseline=value
//  文本方向 ltr|rtl|inherit
ctx.direction=value

//  测量文本宽度
//  return {width:value<Number>}
ctx.measureText(text)
```

## 使用图片

图片源：

1. HTMLImageElement
   1. 这些图片是由 Image()函数构造出来的，或者任何的<img>元素
2. HTMLVideoElement
   1. 用一个 HTML 的 <video>元素作为你的图片源，可以从视频中抓取当前帧作为一个图像
3. HTMLCanvasElement
   1. 可以使用另一个 <canvas> 元素作为你的图片源。
4. ImageBitmap
   1. 这是一个高性能的位图，可以低延迟地绘制，它可以从上述的所有源以及其它几种源中生成。

```javascript
const image = new Image()
image.src = 'http://'
image.onload = function() {
  /**
   * @name  绘制图片
   * @param image 图片源
   * @param x x坐标
   * @param y y坐标
   * */
  ctx.drawImage(image, x, y)
}

//  包含缩放参数的
//  width 描绘宽度，height描绘高度
ctx.drawImage(image, x, y, width, height)

/**
 * @name  切片图片，之后只有在裁剪范围内会的图案才会绘制
 * @param sx  source x 源图片的起始位置
 * @param sy  原图片的起始位置
 * @param swidth 宽度
 * @param sHeight 高度
 * @param dx 目标位置的起始位置
 * @param dWidth  目标位置的宽度
 * */
ctx.beginPath()
ctx.clip(image, sx, sy, sWidth, sHeight, dx, dy, dWidth, dHeight)

/**
 * @name 循环图片
 * @param image 图片实例
 * @param type 类型 repeat，repeat-x，repeat-y 和 no-repeat
 * @param     依次表示，横竖循环，x轴循环，y轴循环，不循环
 * */
const pattern=ctx.createPattern(image,type)
ctx.fillStyle=pattern
ctx.fillRect(0,0,canvas.width,canvas.height)  
```

## 像素操作

ImageData 对象：

width:图片宽度，单位 px
height：图片高度，单位 px
data：Uint8ClampedArray 类型的以为数组

data 属性返回一个 Uint8ClampedArray，它可以被使用作为查看初始像素数据。每个像素用 4 个 1bytes 值(按照红，绿，蓝和透明值的顺序; 这就是"RGBA"格式) 来代表。每个颜色值部份用 0 至 255 来代表。每个部份被分配到一个在数组内连续的索引，左上角像素的红色部份在数组的索引 0 位置。像素从左到右被处理，然后往下，遍历整个数组。
Uint8ClampedArray 包含高度 × 宽度 × 4 bytes 数据，索引值从 0 到(高度 × 宽度 ×4)-1。
透明度也是以255表示，所以需要除以255来计算具体的透明度

```javascript
//  创建一个空的imageData，预设的颜色为【透明黑】，即所有元素都是0，可以通过手动赋值来修改颜色值
const imageData=ctx.createImageData(width,height)
//  得到颜色
const imageData1=ctx.getImageData(left,top,width,height)
//  根据行、列读取某像素点的R/G/B/A值的公式：
imageData.data[((行数-1)*imageData.width + (列数-1))*4 - 1 + 1/2/3/4];
//  写入像素到canvas
//  dx,dy表示写入的位置
ctx.putImageData(imageData,dx,dy)

//  保存图片
//  保存的图片信息可以用于document里面的img元素，或者通过有download属性的超链接下载到本地
canvas.toDataURL(ImageMimeType) //  存储图片的类型 image/png
canvas.toDataURL('image/jpeg', quality) // 创建一个JPG图片。你可以有选择地提供从0到1的品质量，1表示最好品质，0基本不被辨析但有比较小的文件大小。
canvas.toBlob(callback, type, encoderOptions) // 这个创建了一个在画布中的代表图片的Blob对像。
```

注意点:

1. 跨源的图片不能读取像素信息？
2. 新建的ImageData原始的数据都是0，即透明黑，需要通过手动赋值或者读取其他ImageData数据

## 变形

变形是一种更强大的方法，可以将原点移动到另一点、对网格进行旋转和缩放。

```javascript
/**
 * 一个绘画状态包括：
 * 1.当前应用的变形（即移动，旋转和缩放，见下）
 * 2.strokeStyle, fillStyle, globalAlpha, lineWidth, lineCap, lineJoin, miterLimit, shadowOffsetX, shadowOffsetY, shadowBlur, shadowColor, globalCompositeOperation 的值
 * 3.当前的裁切路径（clipping path）
 * */
//  保存当前状态，入栈
ctx.save()
//  恢复上一个保存的状态，出栈
ctx.restore()

//  将坐标移动到（x，y）坐标
ctx.translate(x,y)

//  这个方法只接受一个参数：旋转的角度(angle)，它是顺时针方向的，以弧度为单位的值。
ctx.rotate(deg)

//  scale  方法可以缩放画布的水平和垂直的单位。两个参数都是实数，可以为负数，x 为水平缩放因子，y 为垂直缩放因子，如果比1小，会比缩放图形， 如果比1大会放大图形。默认值为1， 为实际大小。
//  默认情况下，canvas 的 1 个单位为 1 个像素。举例说，如果我们设置缩放因子是 0.5，1 个单位就变成对应 0.5 个像素，这样绘制出来的形状就会是原先的一半。同理，设置为 2.0 时，1 个单位就对应变成了 2 像素，绘制的结果就是图形放大了 2 倍。
ctx.scale(x, y)

//  m11：水平方向的缩放
// m12：水平方向的倾斜偏移
// m21：竖直方向的倾斜偏移
// m22：竖直方向的缩放
// dx：水平方向的移动
// dy：竖直方向的移动
ctx.transform(m11, m12, m21, m22, dx, dy)
//  重置当前变形为单位矩阵，它和调用以下语句是一样的：
//  ctx.setTransform(1, 0, 0, 1, 0, 0);
ctx.resetTransform()
```

## 合成与裁剪

```javascript
//  设置图层合并模式  可选的值
ctx.globalCompositeOperation=type 
```
