# CoordinatorLayout布局使用

## 注意点

1. CoordinatorLayout继承自viewgroup,但是使用类似于framLayout,有层次结构,后面的布局会覆盖在前面的布局之上,但跟behavior属性也有很大关系,的app:layout_behavior属性,只有CoordinatorLayout的直接子布局才能响应,所以不要做徒劳无功的事

2. CoordinatorLayout+AppBarLayout+CollapsingToolbarLayout结合起来才能产生这么神奇的效果,不要想当然的光拿着CoordinatorLayout就要玩出来(刚接触的时候我也有这种想法),累死你

2. AppBarLayout:继承自lineLayout,使用时其他属性随lineLayou,已经响应了CoordinatorLayout的layout_behavior属性,所以他能搞出那么多特效来

2. AppBarLayout的直接子控件可以设置的属性:layout_scrollFlags 
    + .scroll|exitUntilCollapsed如果AppBarLayout的直接子控件设置该属性,该子控件可以滚动,向上滚动NestedScrollView出父布局(一般为CoordinatorLayout)时,会折叠到顶端,向下滚动时NestedScrollView必须滚动到最上面的时候才能拉出该布局 
    + .scroll|enterAlways:只要向下滚动该布局就会显示出来,只要向上滑动该布局就会向上收缩 
    + .scroll|enterAlwaysCollapsed:向下滚动NestedScrollView到最底端时该布局才会显示出来 
    +. 如果不设置改属性,则改布局不能滑动

5. CollapsingToolbarLayout,字面意思是折叠的toolbar,它确实是起到折叠作用的,可以把自己的自布局折叠 继承自framLayout,所以它的直接子类可以设置layout_gravity来控制显示的位置,它的直接子布局可以使用的属性:app:layout_collapseMode(折叠模式):可取的值如下: 
    + .pin:在滑动过程中,此自布局会固定在它所在的位置不动,直到CollapsingToolbarLayout全部折叠或者全部展开 
    +  .parallax:视察效果,在滑动过程中,不管上滑还是下滑都会有视察效果,不知道什么事视察效果自己看gif图(layout_collapseParallaxMultiplier视差因子 0~1之间取值,当设置了parallax时可以配合这个属性使用,调节自己想要的视差效果) 
    +  .不设置:跟随NestedScrollView的滑动一起滑动,NestedScrollView滑动多少距离他就会跟着走多少距离

6. toobar最好是放在CollapsingToolbarLayout,也不是没有其他用法,但是在这套系统中一般只能放在CollapsingToolbarLayout里面,才能达到好的效果,这里toolbar同时设置layout_gravity和app:layout_collapseMode时有一些比较复杂的情况.不一一作介绍,因为一般我们只会把toolbar放在最上面(不用设置layout_gravity属性,默认的),并且设置app:layout_collapseMode为pin,让他固定在最顶端,有兴趣的自己试试其他情况,

7. 告你一个惊天大幂幂:只要CollapsingToolbarLayout里面包含有toolbar那么CollapsingToolbarLayout的折叠后高度就是toolbar的高度,相当于CollapsingToolbarLayout设置了minHeight属性,

8. 再告诉你一个惊天大咪咪:CollapsingToolbarLayout折叠到最顶端时,它就是老大,会处于最上层,包括toolbar在内,所有的布局都会被他盖住,显示不出来.

9. CollapsingToolbarLayout 自己的属性 说明,不是直接子布局可用的,是自己可以用的属性 
contentScrim折叠后的颜色也是展开时的渐变颜色,效果超赞. 
title标题,如果设置在折叠时会动画的显示到折叠后的部分上面,拉开时会展开,很好玩的 
expandedTitleMargin当title文字展开时文字的margin,当然还有marginTop等属性,脑补吧 
app:collapsedTitleTextAppearance=”@style/Text”折叠时title的样式里面可以定义字体大小颜色等 
app:collapsedTitleTextAppearance=”@style/Text1”折叠时title的样式里面可以定义字体大小颜色等 
当然还有一些,自己试试吧,现在的这些已经完全够用了

10. 还有最后一个问题:怎么实现固定表头,这个也简单,写一个布局放在CollapsingToolbarLayout之后,AppBarLayout之内即可,xml文件中自己找找看吧.你要是问如果放在CollapsingToolbarLayout之前,AppBarLayout之内会怎么样?这样折叠布局就不起作用了.不会折叠了.

##  需要配合AppBarLayout使用

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/ac_con"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <ImageView
            app:layout_scrollFlags="scroll"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            android:background="@mipmap/ic_launcher"/>
        <android.support.design.widget.TabLayout
            android:id="@+id/tab"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </android.support.design.widget.AppBarLayout>

    <android.support.v4.widget.NestedScrollView
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </android.support.v4.widget.NestedScrollView>
</android.support.design.widget.CoordinatorLayout>
```

### 属性说明

#### 滚动控件的属性

+  app:layout_behavior="@string/appbar_scrolling_view_behavior"
  +  声明滚动控件的滚动行为

#### 滚动变化的控件属性

+ app:layout_scrollFlags="scroll"
  + scroll
    + 所有想滚动出屏幕的view都需要设置这个flag，没有设置这个flag的view将被固定在屏幕顶部
  + snap
    + 配合scroll使用 -snap|scroll
    + 要么该控件全部显示，要么全部隐藏
  + entersAlways
    + 配合scroll一起使用 scroll|enterAlways
    + 上拉隐藏，下拉总是先显示该控件
  + enterAlwaysCollapsed
    + enterAlways的附加属性，配合scroll，enterAlways和layout_height,minHeight一起使用 - scroll|enterAlways|enterAlwaysCollapsed
    + 下拉时先触发该控件的滚动，直至完成behevior，接着滚动控件开始执行滚动
    + 上拉时候先执行该控件的滚动行为，直至到达最小高度-minHeight。接着在执行滚动控件的滚动行为，当滚动控件滚动到顶部时候，在接着执行该控件没有滚动完的部分，直至整个页面到达顶部
  + exitUntilCollapsed
    + 配合scroll一起使用 -scroll|exitUntilCollapsed和layout_height,minHeight一起使用
    + 下拉的时候，该控件先执行滚动，直到高度达到minHeight的值，接着执行滚动控件的滚动动作
    + 上拉时候，先执行滚动控件的滚动，直到滚动到达最顶部，接着执行该控件的滚动行为，直到到达页面顶部

#### 控件的属性 layout_behavior

```java

//  Behavior<T> T 也就是观察者View
public class EasyBehavior extends CoordinatorLayout.Behavior<TextView> {

    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //  代表寻找被观察的view
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {

        //  如果一个coordinatorLayout 有两个button的话，textview会有两个观察者
        return dependency instanceof Button;
    }

    //  当被观察的状态发横 改变，这个方法会被调用，可以在这里处理观察的view的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
```
> 当scrollView里面嵌套listView列表的时候，会把滚动事件消费，导致coordinatorLayout不能派发事件