# popupWindow

## 构造函数

```java
//方法一：
public PopupWindow (Context context)
//方法二：
public PopupWindow(View contentView)
//方法三：
public PopupWindow(View contentView, int width, int height)
//方法四：
public PopupWindow(View contentView, int width, int height, boolean focusable)  

//  创建
View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popuplayout, null);
PopupWindwo popWnd = PopupWindow (context);
popWnd.setContentView(contentView);
popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);  
```

## 如何显示

```java
//相对某个控件的位置（正左下方），无偏移
showAsDropDown(View anchor)：
//相对某个控件的位置，有偏移;xoff表示x轴的偏移，正值表示向左，负值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
showAsDropDown(View anchor, int xoff, int yoff)：
//相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
showAtLocation(View parent, int gravity, int x, int y)：  
```

## 设置

> 则 PopUpWindow 本身可以看作一个类似于模态对话框的东西（但有区别），PopupWindow 弹出后，所有的触屏和物理按键都有 PopupWindows 处理。其他任何事件的响应都必须发生在 PopupWindow 消失之后， （home 等系统层面的事件除外）。比如这样一个 PopupWindow 出现的时候，按 back 键首先是让 PopupWindow 消失，第二次按才是退出 activity，准确的说是想退出 activity 你得首先让 PopupWindow 消失 。如果 PopupWindow 中有 Editor 的话，focusable 要为 true。而 setFocusable(false);
> 则 PopUpWindow 只是一个浮现在当前界面上的 view 而已，不影响当前界面的任何操作。是一个“没有存在感”的东西。一般情况下 setFocusable(true);

```java
public void dismiss()
//另外几个函数，这里不讲其意义，下篇细讲
public void setFocusable(boolean focusable)
public void setTouchable(boolean touchable)
public void setOutsideTouchable(boolean touchable)
public void setBackgroundDrawable(Drawable background)  

//  窗体外点击关闭
popupWindow.setFocusable(true);
popupWindow.setOutsideTouchable(true);
//  一下不设置不能dismiss
ColorDrawable dw = new ColorDrawable(Color.parseColor("#66000000"));
popupWindow.setBackgroundDrawable(dw);
```

## 动画

```java
//  设置动画
mPopWindow.setAnimationStyle(R.style.contextMenuAnim);
```

```xml
<style name="PopupAnimation" parent="android:Animation">  
  <item name="android:windowEnterAnimation">@anim/push_bottom_in</item>    //进场动画  
  <item name="android:windowExitAnimation">@anim/push_bottom_out</item>    //出场动画  
</style>  
```
