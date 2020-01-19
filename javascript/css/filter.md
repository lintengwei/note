# filter

[https://www.cnblogs.com/sweeeper/p/10938334.html](https://www.cnblogs.com/sweeeper/p/10938334.html)
各个滤镜可以组合使用，效果跟组合的顺序相关！如何相关？

```css
img{
  
  /* 
    给图像设置高斯模糊效果，接收长度值，值越大模糊效果越明显，0值为原始图
  */
  filter:blur(20px);

  /*
    是图像更暗或者更亮，小于1图像变暗，大于1图像变亮，0为全黑
  */
  filter:brightness(x>0);

  /*
    设置图像对比度，1为正常，小于1图像变暗，大于1图像色度变深，0为灰色
  */
  filter:contrast(x>0);

  /*
    将图像转变为灰度图，0为正常 ，0~1 值越大灰度值越大，大于1图像全灰，负值图像正常
  */
  filter:grayscale(x);

  /*
    设置图像饱和度 ，1正常，0灰色，大于1，值越大饱和度越高
  */
  filter:saturate(x);
  
}
```