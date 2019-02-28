# CollapsingToolbarLayout布局

> 是继承自FrameLayout的，子布局遵循FrameLayout布局
> 与AppBarLayout组合的滚动布局（Recyclerview、NestedScrollView等）需要设置app:layout_behavior="@string/appbar_scrolling_view_behavior"（上面代码中NestedScrollView控件所设置的）。没有设置的话，AppBarLayout将不会响应滚动布局的滚动事件。
> CollapsingToolbarLayout和ScrollView一起使用会有滑动bug，注意要使用NestedScrollView来替代ScrollView。

## 基本属性

+ app:contentScrim
  + 设置折叠时工具栏布局的颜色
+ app:statusBarScrim
  + 设置折叠时状态栏的颜色,默认contentScrim是colorPrimary的色值，statusBarScrim是colorPrimaryDark的色值。这个后面会用到

## CollapsingToolbarLayout的子布局有3种折叠模式

> 包含toolbar使用

1. 使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示。即：
  + mCollapsingToolbarLayout.setTitle(" ");
2. 该变title的字体颜色：
  + 扩张时候的title颜色：mCollapsingToolbarLayout.setExpandedTitleColor();
  + 收缩后在Toolbar上显示时的title的颜色：mCollapsingToolbarLayout.setCollapsedTitleTextColor();
  + 这个颜色的过度变化其实CollapsingToolbarLayout已经帮我们做好，它会自动的过度，比如我们把收缩后的title颜色设为绿色

+ app:layout_collapseMode="off"
  + 这个是默认属性，布局将正常显示，没有折叠的行为。
+ app:layout_collapseMode="pin"
  + CollapsingToolbarLayout折叠后，此布局将固定在顶部。
+ app:layout_collapseMode="parallax"
  + CollapsingToolbarLayout折叠时，此布局也会有视差折叠效果，就是也可以滚动。
  + 当CollapsingToolbarLayout的子布局设置了parallax模式时，我们还可以通过app:layout_collapseParallaxMultiplier设置视差滚动因子，值为：0~1。

> layout_anchor 如何使用
```xml
<!--  app:layout_anchor="@id/app_bar"    -->
<!--  app:layout_anchorGravity="bottom|end"    -->
<!--  layout_anchorGravity属性会以中心点为基准定位 -->
 <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/fab_margin"
        app:layout_anchor="@id/app_bar"   
        app:layout_anchorGravity="bottom|end"
        app:srcCompat="@android:drawable/ic_dialog_email" />
```