# costomize_view 自定义视图

## 构造函数

### context.obtainStyleAttributes(AttributeSet set,@StyleableRes int[] attrs)

* set
  * 我们的属性值
* attrs
  * 我们自定义的值

> 使用完 obtainStyleAttribute 之后需要调用 recycle()回收资源

## onMeasure(int widthMeasureSpec,int heightMeasureSpec)

### 参数

```java
int widthMode=MeasureSpec.getMode(widthMeasureSpec);  //  获取测量模式
int heightSize=MeasureSpec.getSize(widthMeasureSpec); //  获取测量值

//  高度同上
//  当layout_height/layout_width == MATCH_PARENT/WRAP_CONTENT;
//  默认的测量方式都是 MATCH_PARENT，因此自定义视图想要有 WRAP_CONTENT的属性值
//  需要复写onMeasure方法，如下
// 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth =200;
        int mHeight = 200;  //默认size的值

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
```

## onDraw 绘制方法

### 视图重绘的方法

* invalidate();
  * 这个方法会让视图重回，需要在主线程调用
* postInvalidate();
  * 可以发起重绘，可以在其他线程中调用；

### Paint 画笔

```java
Paint mPaint=new Paint();
mPaint.setStyle(Paint.Style.FILL);  // 设置填充 其他参考
mPaint.setColor(Colror.RED);  // 设置画笔颜色
mPaint.setTextSize(int p);  // 设置大小
```

### Canvas 画布

```java
Canvas canvas=new Canvas();

//  绘制文本
/**
 * @param text 需要绘制的文本
 * @param x    绘制的文本左边距离canvas左边的距离 mPaint.setTextAlign(Paint.Align.CENTER) 文本中心到canvas左边的距离
 * @param y    绘制的文本baselint距离canvas上边的距离
 * @param paint 绘制文本的画笔
 * */
canvas.drawText(String text,int x,int y,Paint paint);

//  获取文本宽度  有重载方法 -----------
Rect rect=new Rect();
getTextBounds(String text,int start,int end,Rect rect);
mPaint.getTextBounds(text,0,text.length(),mPaint);  
int textWidth=rect.width(); // 文本宽度
int textHeight=rect.height(); //  文本高度

//  绘制在view的中心
canvas.drawText(text,getMeasureWidth()/2-textWidth/2,getMeasureHeight()/2+textHeight()/2,mPaint);

//  baseline 居中 后面在看-------------------
FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();  
int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  
canvas.drawText(testString,getMeasuredWidth() / 2 - rect.width() / 2, baseline, mPaint);  

//  TypedValue类的使用
```

#### 绘制矩形

```java
Rect rect=new Rect();
canvas.drawRect(rect,mPaint); //  正常矩形
canvas.drawRoundRect(rect,rx,ry,mPaint);  //绘制圆角矩形
```
