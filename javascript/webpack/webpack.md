# webpack 使用

[document](https://webpack.docschina.org/guides/)

## 基本属性

```javascript
module.exports = {
  entry: [string|Array<string>|{[entryChunkName: string]: string|Array<string>}],
  output:Object<{filename:'',path:''}>
};
```

> entry. 如果传入一个字符串或字符串数组，chunk 会被命名为 main

- String
  - 当只有一个入口文件时候定义为 String
  - 只会打包成一个ｂｕｎｄｌｅ文件
- Array
  - 当你向 entry 传入一个数组时会发生什么？向 entry 属性传入「文件路径(file path)数组」将创建“多个主入口(multi-main entry)”。在你想要多个依赖文件一起注入，并且将它们的依赖导向(graph)到一个“chunk”时，传入数组的方式就很有用。
  - 把数组的每个入口都打包成一个ｂｕｎｄｌｅ文件
- Object
  - 这是应用程序中定义入口的最可扩展的方式。“可扩展的 webpack 配置”是指，可重用并且可以与其他配置组合使用。这是一种流行的技术，用于将关注点(concern)从环境(environment)、构建目标(build target)、运行时(runtime)中分离。然后使用专门的工具（如 webpack-merge）将它们合并。
  - 每个入口都是一个ｂｕｎｄｌｅ文件
  - 如果是ＳＰＡ项目，一个Ａｐｐ入口和一个依赖入口
  - 如果是多文件项目，每个ｈｔｍｌ对应一个脚本文件

> output

即使可以存在多个入口起点，但只指定一个输出配置!!!

- filename
  - 用于输出文件的名称
  - [name]
    - 如果是 entry 为[String|Array<String>]，name 为 main
    - 如果是 Object,name 为 key
  - [hash]
    - 基于构建的 hash
  - [chunkhash]
    - 基于内容的 hash。用该字段可以生成缓存更新文件
- path
  - 目标输出目录 path 的绝对路径。

```javascript
const path = require('path')
module.exports = {
  entry: '',
  output: {
    filename: '[name].bundle.js', //  name　对应entry object key
    path: path.resolve(__dirname, '/dist')
  }
}
```

> module

其他文件的解析

```javascript
module.exports = {
  module: {
    rules: [
      {
        test:[String|Regexp]
        use: [
          'style-loader',
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1
            }
          },
          {
            loader: 'less-loader',
            options: {
              noIeCompat: true
            }
          }
        ]
      }
    ]
  }
};
```

==tip==

1. 模块的解析规则是从右到左解析的，所以类似 css 的预处理器，需要放在最右边，最先解析

## 开发环境的配置需求

### source-map

[options](https://webpack.docschina.org/configuration/devtool).由于很多源码文件经过编译之后会合成一个文件，因此如果在调试过程中出现错误，难以定位到程序的错误位置。因此配置 source-map 可以便于定位错误位置。

```javascript
module.exports = {
  devtool: 'inline-source-map'
}
```

### 观察模式

[options](https://webpack.docschina.org/configuration/dev-server).修改源码文件之后，浏览器同步更新的功能。

- webpack's Watch Mode
- webpack-dev-server（推荐使用）
- webpack-dev-middleware

```bat
rem install
npm install --save-dev webpack-dev-server
```

```javascript
//  webpack.config.js 配置
//  配合webpack.HotModuleReplacementPlugin使用
//  package.json文件　--参数    --open --config configPath
module.exports = {
  devServer: {
    contentBase: '../dist',
    port: 8080, //  端口
    hot: true, //  热加载
    open: true //   是否打开浏览器
  }
}

//  package.json 配置
json = {
  dev: 'webpack-dev-server --config ./config/webpack.dev.conf.js'
}
```

### 热模块替换

模块热替换(Hot Module Replacement 或 HMR)是 webpack 提供的最有用的功能之一。它允许在运行时更新各种模块，而无需进行完全刷新

```javascript
const webpack=require('webpack)
module.exports = {
  plugins: [new webpack.HotModuleReplacementPlugin()]
}
```

## 生产环境配置

### 提取公共文件

当多个入口有相同的引用的时候，webpack 的默认行为是每个入口文件都会把依赖打包，这对于服务来说是多余的。通过配置相关参数可以修改这种默认行为。
[options](https://webpack.docschina.org/plugins/split-chunks-plugin/).　【split-chunks-plugin】应该也是插件行为的，为何不在 plugins 里面进行配置。

> 为何只能分离 node_module 内置的模块，自定义的模块如何分离？

```javascript
//  新增配置项可以修改默认行为
module.exports = {
  //  ...
  optimization: {
    splitChunks: {
      chunks: 'async', //  async initial all
      minSize: 30000, //  文件大小至少为多少才会分离，默认３０kb。优先级大于【minChunks】
      minChunks: 1, // 至少被几个模块依赖才会进行分离
      maxAsyncRequests: 5,
      maxInitialRequests: 3,
      automaticNameDelimiter: '~',
      name: true, //  是否自动生成文件名？retrun true,自动生成。如果为字符串则以该字符串为文件名
      cacheGroups: {
        //  把大模块分离成更小的模块
        //  默认只会把node_module里面的模块进行分离。
        //  如果需要把其他自定义的模块进行分离如何操作？？？
        vendors: {
          test: /[\\/]node_modules[\\/]/,
          priority: -10,
          name: 'lib'
        },
        utils: {
          minChunks: 2,
          test: /[\\/]utils[\\/]/, //  把utils下面的文件统一打包到utils.[chunkhash].js文件里
          priority: -20,
          reuseExistingChunk: true,
          name: 'utils'
        }
      }
    }
  }
}
```

## 脚本

```bat
rem 构建语法  为什么是 npx 语法
npm webpack --config configPath
rem in package.json
dev : webpack --config webpack.dev.conf.js rem 开发环境的配置文件
build : webpack --config webpack.prod.conf.js rem 生产环境的配置文件
```
