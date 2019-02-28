# activity

## 透明主题

```xml
    <style name="TransparentTheme" parent="@android:style/Theme.Translucent.NoTitleBar">
        <item name="android:windowBackground">@color/color_transparent</item>
        <item name="android:windowAnimationStyle">@null</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowNoTitle">true</item> <!-- 无标题 -->
        <item name="android:windowContentOverlay">@null</item>
    </style>
    <!-- useage -->
 <activity
            android:name=".wxapi.WXPayEntryActivity"
            android:exported="true"
            android:screenOrientation="portrait" />
```

## WindowSoftInputMode 属性详解

这个属性就是来设置窗口软键盘的交互模式的。

【1】stateUnspecified：软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置

> 当设置属性为 stateUnspecified 的时候，系统是默认不弹出软键盘的，但是当有获得焦点的输入框的界面有滚动的需求的时候，会自动弹出软键盘。至于为什么非要强调要获取焦点的输入框，这是因为，如果不是输入框获取焦点，软键盘也是不会自动弹出的，让界面不自动弹出软键盘的其中一个解决方案，就是在 xml 文件中，设置一个非输入框控件获取焦点，从而阻止键盘弹出。

【2】stateUnchanged：当这个 activity 出现时，软键盘将一直保持在上一个 activity 里的状态，无论是隐藏还是显示

> 当前界面的软键盘状态，取决于上一个界面的软键盘状态。

【3】stateHidden：用户选择 activity 时，软键盘总是被隐藏

> 如果我们设置了这个属性，那么键盘状态一定是隐藏的，不管上个界面什么状态，也不管当前界面有没有输入的需求，反正就是不显示。因此，我们可以设置这个属性，来控制软键盘不自动的弹出

【4】stateAlwaysHidden：当该 Activity 主窗口获取焦点时，软键盘也总是被隐藏的

【5】stateVisible：软键盘通常是可见的,设置为这个属性，可以将软键盘召唤出来，即使在界面上没有输入框的情况下也可以强制召唤出来。

> 那么让我跳转到 B 的时候，再回到 A 的时候看看键盘还在不在？经过测试，发现如果 A 设置了 stateVisible，跳转到 B,B 中键盘任然出现，再按返回键回到 A 的时候，A 中键盘不会出现了

【6】stateAlwaysVisible：用户选择 activity 时，软键盘总是显示的状态

> 跟 5 中唯一不同就是，如果 A 设置了 stateAlwaysVisible，跳转到 B,B 中键盘任然出现，再按返回键回到 A 的时候，A 中键盘还会出现。

【7】adjustUnspecified：默认设置，通常由系统自行决定是隐藏还是显示

> 系统会调整整个布局整体的位置，使输入的部分始终可见。因此底部的布局被顶上去了，“标题栏”不会被顶上去，“确定”按钮还是可见的。

【8】adjustResize：该 Activity 总是调整屏幕的大小以便留出软键盘的空间

【9】adjustPan：当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分
