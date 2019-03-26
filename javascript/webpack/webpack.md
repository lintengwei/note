- [webpack 使用](#webpack-%E4%BD%BF%E7%94%A8)
  - [疑问](#%E7%96%91%E9%97%AE)
  - [基本属性](#%E5%9F%BA%E6%9C%AC%E5%B1%9E%E6%80%A7)
  - [开发环境的配置需求](#%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E7%9A%84%E9%85%8D%E7%BD%AE%E9%9C%80%E6%B1%82)
    - [source-map](#source-map)
    - [观察模式](#%E8%A7%82%E5%AF%9F%E6%A8%A1%E5%BC%8F)
    - [热模块替换](#%E7%83%AD%E6%A8%A1%E5%9D%97%E6%9B%BF%E6%8D%A2)
  - [生产环境配置](#%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE)
    - [提取公共文件](#%E6%8F%90%E5%8F%96%E5%85%AC%E5%85%B1%E6%96%87%E4%BB%B6)
  - [懒加载](#%E6%87%92%E5%8A%A0%E8%BD%BD)
  - [脚本](#%E8%84%9A%E6%9C%AC)
  - [番外](#%E7%95%AA%E5%A4%96)
    - [关于package.json中module字段的说明](#%E5%85%B3%E4%BA%8Epackagejson%E4%B8%ADmodule%E5%AD%97%E6%AE%B5%E7%9A%84%E8%AF%B4%E6%98%8E)

# webpack 使用

[document](https://webpack.docschina.org/guides/)

```json
//  安装的插件
{
  "devDependencies": {
    "clean-webpack-plugin": "^2.0.1",
    "html-webpack-plugin": "^3.2.0",
    "webpack": "^4.29.6",
    "webpack-cli": "^3.3.0",
    "webpack-manifest-plugin": "^2.0.4"
  }
}
```

## 疑问

1. webpack的数据流向是怎么样的，是先流向loaders还是plugins又或者是其他？

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
- library
  - 配合libraryTarget使用，表示输出包的名称
- libraryTarget
  - 配置如何暴露library，有四种方式
    - 暴露为一个变量
      - 如果设置属性 library=MyLibrary，且未设置libraryTarget的值，则使用默认值'var'，将模块赋值给MyLibrary，在模块内导出的方法，变量等，都在MyLibrary为接口调用
    - 通过在对象上赋值暴露
    - 模块定义系统
    - 其他targets

```javascript
//  libraryTarget=='var'
//  在html的script的脚本中，将暴露一个MyLibrary的全局变量
//  在node环境中如何使用？
module.exports={
  //  ...
  output:{
    library:'MyLibrary'
  }
}


```

> module

其他文件的解析

==tip==

1. 模块的解析规则是从右到左解析的，所以类似 css 的预处理器，需要放在最右边，最先解析

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

2. Rule.resource|Rule.issuer 如何使用？

Rule.test、Rule.include、Rule.exclude都是从resource衍生出来的。使用resource可以更加粒度的控制匹配模式。而issuer则是当resource匹配的时候的文件的导入源。可以动态生成【use】属性？

```javascript
//  index.js
import 'index.css'
import 'test.css'

//  webpack.config.js
module.exports={
  //  ...
  module:{
    rules:[
      {
        use:['css-loader'],
        resource(path){
          //  ../index.js
          //  return  true/false
        },
        issuer(path){
          //  ../index.css  ../test.css
          // 设置匹配模式
          //  return true/false
          if(/\.css$/.test(path)){
            return true
          }
          return false
        }
      }
    ]
  }
}
```

> devServer

配置本地开发环境.
webpack-dev-server 在编译之后不会写入到任何输出文件。而是将 bundle 文件保留在内存中，然后将它们 serve 到 server 中，就好像它们是挂载在 server 根路径上的真实文件一样。如果你的页面希望在其他不同路径中找到 bundle 文件，则可以通过 dev server 配置中的 publicPath 选项进行修改。
会在内存中创建一个虚拟目录，模拟让生产环境中的输出位置。
如果配置了 contentBase，当出现重名的时候，会优先使用内存中的文件

例如：
当前目录结构：

- /
  - src
    - index.js
  - public
    - index.html

```javascript
const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')
module.exports = {
  entry: {
    index: path.resolve(__dirname, 'src/index.js')
  },
  devServer: {
    port: 9999,
    hot: true,
    proxy: {
      //  发送到 /api的请求服务会被转发到 3000端口的本地服务
      '/api': 'http://localhost:3000'
    }
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'public/index.html'),
      //  'html/index.html' http://localhost:9999/html/
      filename: 'index.html' //  http://localhost:9999/ 能访问到
    })
  ]
}
```

如果按以上配置，当运行开发环境的时候，会生成虚拟目录，如下：

- /
  - index.html
  - output.js

```javascript
module.exports = {
  devServer: {
    //  告诉服务器从哪个目录中提供内容。只有在你想要提供静态文件时才需要
    //  加载html等静态文件，如果是动态文件则指定 publicPath？
    contentBase: String | Boolean|Array<String>,
    //  当静态文件修改的时候是否刷新页面
    watchContentBase: Boolean,
    //  是否自动打开浏览器
    open:Boolean,
    //   在响应中添加指定头部
    headers:Object,
    //  在响应的时候是否启用压缩
    compress:Boolean,
    //  输入包的访问路径，默认为 /
    publicPath:String|Array<String>,
    //  服务监听的端口
    port: Number,
    //  是否开启热更新
    hot: Boolean,

    hotOnly: Boolean,
    //  配置代理服务器
    proxy:Object
  }
}
```

> resolve

```javascript
module.exports = {
  resolve: {
    //  指定解析的别名
    alias: Object,
    //  指定当导入文件未包含扩展名时候的匹配扩展名，默认['.js','.json']
    extensions: Array,
    //  指定导入时候强制扩展名 默认false
    enforceExtension: Boolean,
    //  指定导入包是按照package.json的哪个字段导入，
    //  例如:['browser','module','main']
    //  会优先搜索package.json中时候有 browser字段，module字段，main字段，优先使用最先匹配的字段
    mainFields: Array,
    //  如果未找到 mainFileds指定的字段搜索到文件，则按照mainFiles文件搜索
    mainFiles: Array,
    aliasField: Object,
    plugins: Array
  }
}

module.exports = {
  resolve: {
    alias: {
      //  import utils from '$utils/utils' => import utils from 'src/utils/utils'
      $utils: path.resolve(__dirname, 'src/utils/')
    }
  }
}
```

> externals

不会打包指定的模块。例如通过cdn来引用脚本的时候，可以通过设置这个属性来避免webpack打包模。

```javascript
module.exports = {
  externals: {
    lodash: {
      commonjs: 'lodash',
      commonjs2: 'lodash',
      amd: 'lodash',
      root: '_'
    }
  }
}

//  example
//  in index.js
import _ from 'lodash'

//  webpack.config.js
module.exports={
  //  ...
  externals:{
    //  因为在index文件中导入的是 lodash ，所以key是lodash
    //  在html的cdn中lodash是以【_】暴露在window下面的，所以是 _
    lodash:'_'
  }
}

//  in html
<script src='http://www.cdn.com/lodash.min.js'></script>
<script src='http://www.app.com/app.min.js'></script>
```

> optimization

优化性能。默认webpack只会在生产环境下压缩js代码，使用的压缩插件是【terser-webpack-plugin】，通过属性minimize的值来确定是否开启压缩，当mode为【production】默认开启。
如果需要压缩css或者其他的文件需要额外指定压缩插件【css-mini-extract-plugin】，并且把压缩插件至于optimization的minimizer数组下.

```javascript
module.exports = {
  //  ...
  optimization: {
    //  是否开启js压缩 生产环境默认开启
    minimize:Boolean,
    //  指定压缩插件
    //  js 系统默认的是【terser-webpack-plugin】无需配置
    //  css 【css-mini-extract-plugin】
    minimizer:Array,
    //  生成运行时依赖加载 runtime.js
    runtimeChunk:{
      name:'runtime'
    },
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

> target string | function (compiler)

> webpack 的 target 属性，不要和 output.libraryTarget 属性混淆。有关 output 属性的更多信息，请查看我们的 指南。

webpack 能够为多种环境或 target 构建编译。如果指定编译目标，webpack 会按照指定的编译目标环境来编译文件。

当前支持的编译目标环境：

| 选项              | 描述                                                                                           |
| ----------------- | ---------------------------------------------------------------------------------------------- |
| async-node        | 编译为类 Node.js 环境可用（使用 fs 和 vm 异步加载分块）                                        |
| electron-main     | 编译为electron主进程                                                                           |
| electron-renderer | 编译为 Electron 渲染进程                                                                       |
| node              | 编译为类 Node.js 环境可用（使用 Node.js require 加载 chunk）                                   |
| node-webkit       | 编译为 Webkit 可用，并且使用 jsonp 去加载分块。支持 Node.js 内置模块和 nw.gui 导入（实验性质） |
| web               | 编译为类浏览器环境里可用（默认                                                                 |
| webworker         | 编译成一个 WebWorker                                                                           |


```javascript
module.exports = {
  //  ....
}
```

> stats

配置webpack打包过程中的控制台显示信息。系统提供预设的可选值：

| 值            | 描述                          |
| ------------- | ----------------------------- |
| 'errors-only' | 只有发生错误输出              |
| 'minimal'     | 只有发生错误输出/有新编译输出 |
| 'none'        | 没有输出                      |
| 'normal'      | 标准输出                      |
| 'verbose'     | 全部输出                      |

或则根据需求配置

```javascript
module.exports={
   stats: {
    // 未定义选项时，stats 选项的备用值(fallback value)（优先级高于 webpack 本地默认值）
    all: undefined,

    // 添加资源信息
    // 静态资源列表
    assets: true,

    // 对资源按指定的字段进行排序
    // 你可以使用 `!field` 来反转排序。
    // Some possible values: 'id' (default), 'name', 'size', 'chunks', 'failed', 'issuer'
    // For a complete list of fields see the bottom of the page
    assetsSort: "field",

    // 添加构建日期和构建时间信息
    builtAt: true,

    // 添加缓存（但未构建）模块的信息
    cached: true,

    // 显示缓存的资源（将其设置为 `false` 则仅显示输出的文件）
    cachedAssets: true,

    // 添加 children 信息
    children: true,

    // 添加 chunk 信息（设置为 `false` 能允许较少的冗长输出）
    chunks: true,

    // 添加 namedChunkGroups 信息
    chunkGroups: true,

    // 将构建模块信息添加到 chunk 信息
    chunkModules: true,

    // 添加 chunk 和 chunk merge 来源的信息
    chunkOrigins: true,

    // 按指定的字段，对 chunk 进行排序
    // 你可以使用 `!field` 来反转排序。默认是按照 `id` 排序。
    // Some other possible values: 'name', 'size', 'chunks', 'failed', 'issuer'
    // For a complete list of fields see the bottom of the page
    chunksSort: "field",

    // 用于缩短 request 的上下文目录
    context: "../src/",

    // `webpack --colors` 等同于
    colors: false,

    // 显示每个模块到入口起点的距离(distance)
    depth: false,

    // 通过对应的 bundle 显示入口起点
    entrypoints: false,

    // 添加 --env information
    env: false,

    // 添加错误信息
    errors: true,

    // 添加错误的详细信息（就像解析日志一样）
    errorDetails: true,

    // 将资源显示在 stats 中的情况排除
    // 这可以通过 String, RegExp, 获取 assetName 的函数来实现
    // 并返回一个布尔值或如下所述的数组。
    excludeAssets: "filter" | /filter/ | (assetName) => true | false |
      ["filter"] | [/filter/] | [(assetName) => true|false],

    // 将模块显示在 stats 中的情况排除
    // 这可以通过 String, RegExp, 获取 moduleSource 的函数来实现
    // 并返回一个布尔值或如下所述的数组。
    excludeModules: "filter" | /filter/ | (moduleSource) => true | false |
      ["filter"] | [/filter/] | [(moduleSource) => true|false],

    // 查看 excludeModules
    exclude: "filter" | /filter/ | (moduleSource) => true | false |
          ["filter"] | [/filter/] | [(moduleSource) => true|false],

    // 添加 compilation 的哈希值
    hash: true,

    // 设置要显示的模块的最大数量
    maxModules: 15,

    // 添加构建模块信息
    modules: true,

    // 按指定的字段，对模块进行排序
    // 你可以使用 `!field` 来反转排序。默认是按照 `id` 排序。
    // Some other possible values: 'name', 'size', 'chunks', 'failed', 'issuer'
    // For a complete list of fields see the bottom of the page
    modulesSort: "field",

    // 显示警告/错误的依赖和来源（从 webpack 2.5.0 开始）
    moduleTrace: true,

    // 当文件大小超过 `performance.maxAssetSize` 时显示性能提示
    performance: true,

    // 显示模块的导出
    providedExports: false,

    // 添加 public path 的信息
    publicPath: true,

    // 添加模块被引入的原因
    reasons: true,

    // 添加模块的源码
    source: false,

    // 添加时间信息
    timings: true,

    // 显示哪个模块导出被用到
    usedExports: false,

    // 添加 webpack 版本信息
    version: true,

    // 添加警告
    warnings: true,

    // 过滤警告显示（从 webpack 2.4.0 开始），
    // 可以是 String, Regexp, 一个获取 warning 的函数
    // 并返回一个布尔值或上述组合的数组。第一个匹配到的为胜(First match wins.)。
    warningsFilter: "filter" | /filter/ | ["filter", /filter/] | (warning) => true|false
  }
}
```

> performance

这些选项控制入口和资源的文件限制

```javascript
module.exports={
  //  ...
  performance:{
    //  default 250000  byte
    maxEntrypointSize:400000,
    maxAssetsSize:250000,
    //  过滤哪些模块需要限制，然后发出警告
    assetFilter(assetFilename){
      return assetFilname.endsWith('.js')
    }
  }
}
```

> devtool

源码映射文件。此选项控制是否生成，以及如何生成 source map。

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

- splitChunks.chunks
  - all
  - async
  - initial

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

## 懒加载

当某些模块是通过用户交互之后才使用的，可以使用webpack的模块懒加载功能。

```javascript
//  index.js
let button=document.getElementById('button')
button.addEventListener('click',e=>{
  //  es6语法 返回一个Promise  如何设置模块名？？
  import('./test.js').then(module=>{
    let a=module.default
    a.test()
  })

  //  require语法 .第三个参数是模块命名，如果不设置，webpack会设置为id，不便于阅读
    require.ensure(['./test.js'],function(require){

    },'test')
})
```

## 脚本

```bat
rem 构建语法  为什么是 npx 语法
npm webpack --config configPath
rem in package.json
dev : webpack --config webpack.dev.conf.js rem 开发环境的配置文件
build : webpack --config webpack.prod.conf.js rem 生产环境的配置文件
```

## 番外

### 关于package.json中module字段的说明

[https://juejin.im/entry/5a99ed5c6fb9a028cd448d6a](https://juejin.im/entry/5a99ed5c6fb9a028cd448d6a)
[https://segmentfault.com/a/1190000014286439](https://segmentfault.com/a/1190000014286439)
