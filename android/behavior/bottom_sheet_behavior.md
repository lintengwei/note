# BottomSheetBehavior

## bottom sheet有以下5种状态

+ STATE_COLLAPSED： 默认的折叠状态， bottom sheets只在底部显示一部分布局。显示高度可以通过 app:behavior_peekHeight 设置（默认是0）
  + 需要在xml里面设置  app:behavior_peekHeight="0dp"才会隐藏
  + 当处于STATE_EXPANDED状态的时候，设置这个会缩减高度至-behavior --peekHeight的设置值

+ STATE_DRAGGING ： 过渡状态，此时用户正在向上或者向下拖动bottom sheet

+ STATE_SETTLING: 视图从脱离手指自由滑动到最终停下的这一小段时间

+ STATE_EXPANDED： bottom sheet 处于完全展开的状态：当bottom sheet的高度低于CoordinatorLayout容器时，整个bottom sheet都可见；或者CoordinatorLayout容器已经被bottom sheet填满。
  + 当处于hidden状态的时候，设置该状态会显示

+ STATE_HIDDEN ： 默认无此状态（可通过app:behavior_hideable 启用此状态），启用后用户将能通过向下滑动完全隐藏 bottom sheet

## 属性

```xml
<RelativeLayout
        android:id="@+id/bottomSheetLayout"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:background="@android:color/holo_orange_light"
        app:behavior_hideable="true"
        app:behavior_peekHeight="60dp"
        app:layout_behavior="@string/bottom_sheet_behavior">
</RelativeLayout>
<!-- 其中 -->
<!--behavior_hideable bottom sheet是否能完全隐藏 false不能 默认false  -->
<!--behavior_peekHeight 表示当为STATE_COLLAPSED（折叠）状态的时候bottom sheet残留的高度，默认为0  -->
<!-- 以上两个属性可以通过代码设置 -->
<!-- mBottomSheetBehavior.setHideable(true);-->
<!-- mBottomSheetBehavior.setPeekHeight(300);   -->
```

## BottomSheetDialogFragment 

```java
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_dialog_bottom_sheet, container, false);
        return v;
    }
 
}

//  useage
new CustomBottomSheetDialogFragment().show(getSupportFragmentManager(), "Dialog");
```