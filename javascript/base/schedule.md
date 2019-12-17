# 定时策略

可以用来执行定时任务或者是制作动画特效

## setTimeout

执行一次的定时器

```javascript
setTimeout(()=>{

},timeout)
```

## setInterval

重复执行的定时器

```javascript
setInterval(()=>{

},timeout)
```

## requestAnimationFram

执行动画的功能函数

```javascript
//  例子 某个元素左右移动200px

let dom=document.getElementById('dom')
let start=null

//  步进函数，作为参数传给requestAnimationFrame
function step(timestamp){
  if(!start) start=timestamp
  let progress=timestamp-start
  dom.style.left=Math.min(progress/5,200)+'px'

  //  如果时间小于2s，则再次调用执行，指到时间满足
  //  200*10==2000
  //  总的执行时间除以步进值，等于设置的最终值
  if(progress<1000){
    requestAnimationFrame(step)
  }
}

let id=requestAnimationFram(step)

//  可以取消动画
cancelRequestAnimation(id)
```