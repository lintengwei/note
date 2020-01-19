# loaders 加载器

从右到左解析模块的吗？

## css 相关

**建议将 style-loader 与 css-loader 结合使用**

### postcss-loader

主要是使用autoprefixed的功能？

功能：

1. 给css属性加浏览器前缀，使用的是包[autoprefixer](https://github.com/postcss/autoprefixer)，[browserlist](https://github.com/browserslist/browserslist)
2. 可以使用新的css功能，postcss会编译成满足当前条件的css
3. css报错功能

如何使用：

1. 在webpack中配置postcss-loader，注意需要在style-loader、css-loader之后，在其他编译器之前（如sass-loader、stylus-loader等），可以传递参数
2. 在目录下设置配置文件 postcss.config.js

```javascript
//  webpack.config.js
//  npm install --save-dev css-loader style-loader postcss-loader stylus-loader stylus
module.exports={
  module:{
    rules:[
      {
        test:/\.styl$/,
        use:['css-loader',
          {
            loader:'postcss-loader',
            options:{
              config:{
                path:'./postcss.config.js'
              }
            }
          },
          'stylus-loader'
        ]
      }
    ]
  }
}

//  postcss.config.js
//  使用插件，可能需要独立下载
//  autoprefixer 需要独立配置s
module.exports={
  plugins:[
    require('autoprefixer')
  ],

}

//  npm install --save-dev  autoprefixer
//  需要配置浏览器适配列表
//  1. 在根目录下定义文件 .browserslistrc
//  2. 在package.json里面定义
//  package.json
"browserslist": [
    "defaults",
    "not IE 11",
    "not IE_Mob 11",
    "maintained node versions",
]

//  .browserslistrc
defaults
not IE 11
not IE_Mob 11
maintained node versions
```

### css-loader

解析\*.css 的@import(),url()，然后解析它们，配合 style-loader 使用会在 document 中插入 style 节点. 并且 style-loader 位置要在　 css-loader 之前???

```javascript
module.exports = {
  module: {
    rules: [
      {
        test: /\*.css$/,
        //  为什么要在 css-loader 之前
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

### stylus-loader

[stylus]的预编译器

```bat
rem install stylus-loader  dependence on stylus
npm install --save-dev stylus-loader

rem install stylus
npm install -g stylus
```

##　文件相关

### file-loader

把文件生成到输出文件夹，返回生成路径

### url-loader

功能同 file-loader ，当文件大小小于某个阀值的时候，会把文件内容输出成 base64 的内容

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
