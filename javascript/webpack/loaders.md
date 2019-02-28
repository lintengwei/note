# loaders 加载器

从右到左解析模块的吗？

## css 相关

**建议将 style-loader 与 css-loader 结合使用**

### css-loader

解析\*.css 的@import(),url()，然后解析它们，配合 style-loader 使用会在 document 中插入 style 节点. 并且 style-loader 位置要在　 css-loader 之前???

```javascript
module.exports = {
  module: {
    rules: [
      {
        test: /\*.css$/,
        //  为什么要在ｃｓｓ－ｌｏａｄｅｒ之前
        use: ['style-loader', 'css-loader']
      }
    ]
  }
}
```

### style-loader

插入 style 节点到 document 中

### sass-loader

[sass|scss]的预编译器

```bat
rem install sass-loader usage in rules dependence on node-sass
npm install --save-dev node-sass sass-loder

rem install node-sass 全局安装
npm install -g node-sass
```

```javascript
module.exports = {
  //  ...
  module: {
    rules: [
      {
        test: /\.scss$/,
        //  从右往左解析
        use: ['style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.styl$/,
        use: [
          {
            loader: 'style-loader' // 将 JS 字符串生成为 style 节点
          },
          {
            loader: 'css-loader' // 将 CSS 转化成 CommonJS 模块
          },
          {
            loader: 'stylus-loader' // 将 tylus 编译成 CSS
          }
        ]
      }
    ]
  }
}
```

### styus-loader

[stylus]的预编译器

```bat
rem install stylus-loader  dependence on stylus
npm install --save-dev stylus-loader

rem install stylus
npm install -g stylus
```

##　文件相关

### file-loader

### raw-loader

利用 raw-loader 可以实现 html 的热更新。

```javascript
//  配置文件中
module.expors = {
  module: {
    rules: [
      {
        test: /\.html$/,
        use: 'raw-loader'
      }
    ]
  }
}

//  在入口文件引入html文件，让devServer监控html文件的改动
import '../test.html'
```
