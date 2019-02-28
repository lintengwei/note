# touch

## events

### touchstart

### touchmove

### touchend

### touchcancel

## Touches

- changedTouches
  - 以这个对象计算touch相关
- touches
- targetTouches

> touch属性

+ clientX
  + 点相对于【视口】的水平坐标位置，不包含偏移量
+ clientY
  + 点相对于【视口】的垂直坐标位置，不包含偏移量
+ pageX
  + 同clientX，包含偏移
+ pageY
  + 同clientY，包含偏移
+ screenX
  + 点相对于【屏幕】的水平坐标位置
+ screenY
  + 点相对于【屏幕】的垂直坐标位置
+ target
+ identifier

## 判断移动方向

> 坐标
横坐标以下，顺时针方向0-180，逆时针方向0- -180
（-135，-45）-- 上边
（-45，45）-- 右边
（45，135）-- 下边
（135，180） and （-180，-135）-- 左边 


知识点：
[Math.tan函数](https://baike.baidu.com/item/Tan/14821216)
[Math.atan函数](https://baike.baidu.com/item/atan/10931293?fr=aladdin)
[Math.atan2函数](https://baike.baidu.com/item/atan2/10931300?fr=aladdin)

1. 滑动屏幕事件使用html5 的touchstart滑动开始事件和touchend滑动结束事件。
2. 方向的判断，以起点做平面坐标系，与终点连线做直线，直线与x正半轴计算角度；我们以45度角为方向分割线，如：只要滑动角度大于等于45度且小于135度，则判断它方向为向上滑。
3. 使用Math.atan2来计算起点与终点形成的直线角度。
4. 仔细对比标准坐标系与屏幕坐标系，我们发现，标准坐标系，上半轴为负值，要实现转换，只需要调换Y坐标起点与终于位置即可。

```javascript {.line-numbers}
const TOP = 1
const RIGHT = 2
const BOTTOM = 3
const LEFT = 4
const __NOT = 0
const MIN_TOUCH_DIS = 5

function GetSlideAngle(dx, dy) {
  return Math.atan2(dy, dx) * 180 / Math.PI;
}

//  根据起点和终点返回方向 1：向上，2：向下，3：向左，4：向右,0：未滑动 
function GetSlideDirection(startX, startY, endX, endY) {
  let dy = endY - startY;
  let dx = endX - startX;
  let result = MIN_TOUCH_DIS;
  //如果滑动距离太短 
  if (Math.abs(dx) < MIN_TOUCH_DIS && Math.abs(dy) < MIN_TOUCH_DIS) {
    return result;
  }
  let angle = GetSlideAngle(dx, dy);
  if (angle >= -45 && angle < 45) {
    result = RIGHT;
  } else if (angle >= 45 && angle < 135) {
    result = BOTTOM;
  } else if (angle >= -135 && angle < -45) {
    result = TOP;
  }
  else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
    result = LEFT;
  }
  return result;
}

//  滑动处理 
let startX, startY, currentTranslateX = 0, currentTranslateY = 0;
app.addEventListener('touchstart', function (ev) {
  startX = ev.touches[0].pageX - currentTranslateX;
  startY = ev.touches[0].pageY - currentTranslateY;
}, false);
app.addEventListener('touchmove', function (ev) {
  let endX, endY;
  endX = ev.changedTouches[0].pageX;
  endY = ev.changedTouches[0].pageY;
  let direction = GetSlideDirection(startX, startY, endX, endY);
  let dom = ev.target
  let x = currentTranslateX = (endX - startX)
  let y = currentTranslateY = (endY - startY)
  switch (direction) {
    case MIN_TOUCH_DIS:
      console.log("没滑动");
      break;
    case TOP:
      console.log("向上");
      dom.style.transform = `translateY(${y}px)`
      break;
    case RIGHT:
      console.log("向右");
      dom.style.transform = `translateX(${x}px)`
      break;
    case BOTTOM:
      console.log("向下");
      dom.style.transform = `translateY(${y}px)`
      break;
    case LEFT:
      console.log("向左");
      dom.style.transform = `translateX(${x}px)`
      break;
    default:
  }

})
app.addEventListener('touchend', function (ev) {
  let endX, endY;
  endX = ev.changedTouches[0].pageX;
  endY = ev.changedTouches[0].pageY;
  let direction = GetSlideDirection(startX, startY, endX, endY);
  switch (direction) {
    case MIN_TOUCH_DIS:
      console.log('touch end', "没滑动");
      break;
    case TOP:
      console.log('touch end', "向上");
      break;
    case RIGHT:
      console.log('touch end', "向右");
      break;
    case BOTTOM:
      console.log('touch end', "向下");
      break;
    case LEFT:
      console.log('touch end', "向左");
      break;
    default:
  }
}, false);

```