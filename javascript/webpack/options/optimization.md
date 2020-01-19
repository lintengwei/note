# 优化选项

webpack内置的一些优化选项

```javascript
module.exports={
  optimization:{
    //  是否开启压缩代码的功能，生产模式默认开启。可以使用内置的压缩插件，或者在属性【optimization.minimizer】指定
    minimize:Boolean,
    //  覆写内置的压缩插件（可以配置参数）
    minimizer:Array(TerserPlugin)|Function(complier),

  }
}
```