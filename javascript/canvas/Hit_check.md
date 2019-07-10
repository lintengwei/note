# 碰撞检测

## 规则图形检测

### 外接矩形判断

```javascript
const A= new Rect()
const B= new Rect()

/**
 * @param left 左边界
 * @param right 右边界
 * @param top 上边界
 * @param bottom 下边界
 * */
function check(){
  if(A.left>B.right||A.right<B.left||A.top<B.bottom||A.bottom>B.top){
    return false
  }
  return true
}
```

### 外接圆判断

```javascript
const A=new Circle()
const B=new Circle()

/**
 * @param radiusX 半径
 * @param centerPointX  中心点位置
 * @param lineLength  中心点距离
 * */
function check(){
  let radiusA=A.radius
  let radiusB=B.radius
  let pointA=A.centerPoint
  let pointB=B.centerPoint
  let lineLength=Math.sqrt(Math.pow(Math.abs(pointA.x-pointB.x),2)+Math.pow(Math.abs(pointA.y-pointB.y),2))
  if(lineLength>(radiusA+radiusB)){
    return false
  }
  return true
}
```

## 分轴离定法