# webpack 插件

## HtmlWebpackPlugin

[document](https://webpack.docschina.org/plugins/html-webpack-plugin)

[options](https://github.com/jantimon/html-webpack-plugin#options)

```bat
rem install
npm install --save-dev html-webpack-plugin
```

> options 　可选参数

- template
  - 生成模版路径。插件会已这个模版为基础生成新的 html 文件.
- filename
  - 生成的文件名，可以带有路径
  - tip
    - 路径是相对于生成的文件的目录
    - 如果生成的文件目录为 /dist，且 filename 设置为 '../html/filename.html'
      - 最终目录为 /dist/html/filename.html
- chunks
  - 可以为模版指定导入的文件
  - 如 index.html 只需要导入 index.js 文件，则 chunks=['index']

==tip==

1. 每个对象生成一个 html 模版，如果是多文件项目，则需要生成对应的 HtmlWebpackPlugin 对象

## CleanWebpackPlugin

[options](https://github.com/johnagan/clean-webpack-plugin)
默认会清除 webpackOptions.output 的 path 下的文件，可以手动配置

```bat
rem install
npm install --save-dev clean-webpack-plugin
```

> Path

```javascript
```

## mini-css-extract-plugin|optimize-css-assets-webpack-plugin

把 css 打包成一个单独的包.适用于 webpack@4.0.0。[doc](https://github.com/shama/stylus-loader)
【optimize-css-assets-webpack-plugin】用于压缩 css 文件，webpack4+需要手动设置

注意点：

1. 会把某个入口引入的所有 css 打包进同一个 css 文件？如何分离公共的 css 包？如何把分离的文件嵌入到 html 文件？分离的公共文件不会注入到 html 文件如何处理？

配置【optimizaiton.splitChunks】来分离公共的文件

```bat
rem install
yarn add --dev mini-css-extract-plugin
yarn add --dev optimize-css-assets-webpack-plugin
```

```javascript
const CssMiniExtractPlugin = require('mini-css-extract-plugin')
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin')
module.exports = {
  //  ...
  optimization: {
    minimizer: [
      //  压缩js
      // new UglifyJsPlugin({
      //   cache: true,
      //   parallel: true,
      //   sourceMap: true // set to true if you want JS source maps
      // }),
      new OptimizeCSSAssetsPlugin({})
    ]
  },
  module: {
    rules: [
      {
        test: /\.css/,
        use: [
          {
            loader: CssMiniExtractPlugin.loader,
            options: {}
          },
          'css-loader'
        ]
      }
    ]
  },
  plugins: [
    new CssMiniExtractPlugin({
      filename: '[name].[chunkhash].css'
    })
  ]
}
```

## ExtractTextWebpackPlugin【弃用，使用上面的】

把 css 打包成一个单独的包. webpack@3.0.0。 对于 webpack@4.0.0+，使用【mini-css-extract-plugin】。不需要同 style-loader 共用

```bat
rem install
npm install --save-dev extract-text-webpack-plugin
```

```javascript
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const extract = new ExtractTextPlugin({
  //  输出的文件名 类似 output
  filename: 'common.[chunkhash].css'
})
module.exports = {
  //  ...
}
```

## split-chunks-plugin

[https://webpack.js.org/plugins/split-chunks-plugin/#splitchunkscachegroups](https://webpack.js.org/plugins/split-chunks-plugin/#splitchunkscachegroups)
把公共部分抽离出来单独打包成一个文件，已经合并到 webpack 的标准配置中。
配置参数是【optimization.splitChunks:<Object>】

==cacheGroups 可以重写所有 splitChunks 的属性==
==设置 cacheGroups 之后，webpack 会改变加载方式==

```javascript
module.exports = {
  //  ...
  optimization: {
    splitChunks: {
      //  多个文件的分隔符，默认~，当分离的包包含多个的时候使用
      automaticNameDelimiter: '~',
      //  指定哪一些模块需要被优化
      chunks: 'all|async|initial',
      //  按需加载是最大的并行请求数目
      maxAsyncRequests: 3,
      //  入口点耳朵最大请求数量
      maxInitialRequests: 3,
      //  需要共享几次才会单独拆份出来
      minChunks: 3,
      //  要生成块的最小大小（字节）
      minSize: 200000,
      maxSize: 200,
      //  拆分的模块名称
      //  true | function (module, chunks, cacheGroupKey) | string
      name: 'lib',
      //  缓存组可以集成或者覆盖splitChunks设置的所有属性。但是【test】，【priorty】，【reuseExistingChunk】只能在这里设置，可以通过设置cacheGroups.default=false 来禁用缓存组
      cacheGroups: {
        // 优先级  某个模块可能会被多个缓存组检测到，设置该属性的缓存组值越大，包就会打包进这里
        priority: 10,
        //  一下两个为缓存组
        vendors: {
          //  如果当前块包含已经从主包中分离出来的模块，那么它将被重用，而不是生成新的模块。这可能会影响块的结果文件名。
          reuseExistingChunks: true,
          //  控制此缓存组选择的模块。省略它将选择所有模块。它可以匹配绝对模块资源路径或块名称。匹配块名称时，将选择块中的所有模块。
          test: /\.css/,
          //  允许仅当文件名是初始块时重写该文件名。output.filename中可用的所有占位符也在此处可用。
          filename: '[name].common.css'
        },
        libs: {}
      }
    }
  }
}
```

## webpack.DllPlugin 插件|webpack.DllReferencePlugin 插件

打包第三方或者项目中固定的文件到单独的 js 文件中，到后期项目打包的时候不需要再次打包，只要引用即可，缩短打包构建的时间。

DllPlugin 插件的参数：

- context
  - 根据该字段来查找 manifest.json 的模块？
- name
  - required
  - 暴露的 DLL 函数名
- path
  - required
  - manifest.json 的存放位置

DllReferencePlugin 插件的参数：

- context
  - required
  - 上下文环境
- manifest
  - required
  - 一个包含 name 和 content 的 Object，就是 DllPlugin 的输出文件
- content
- name
- scope
- sourceTye

```javascript
const webpack=require('webpack')
const path=require('path')

//  webpack.config.dll.js 打包动态依赖包的webpack配置
module.exports={
  //  ...
  entry:{
    verdors:['vue','lodash','moment']
  },
  output:{
    filename:'[name].[chunkhash].js',
    path:path.resolve(__dirname,'dll'),
    library:'vendorsDll'
  },
  plugins:[
    new webpack.DllPlugin({
      name:'vendorsDll',
      path:path.resolve(__dirname,'dll','manifest.json')
    })
  ]
}

//  package.json
{
  "scripts":{
    "build:dll":"webpack --config ./webpack.config.dll.js",
    "build":"webpack --config ./webpack.config.js"
  }
}

//  bat
//  执行 npm run  build:dll 会在dll目录下生成依赖的js文件和manifest.json依赖信息文件

//  webpack.confit.js
module.exports={
  //  ...
  plugins:[
    new webpack.DllReferencePlugin({
      context:__dirname,
      manifest:require(path.resolve(__dirname,'dll','manifest.json'))
    })
  ]
}

//  bat
//  npm run build
//  然后在html文件导入之前生成的动态js文件，一定要放在最前面
```

## webpack-manifest-plugin

在 output 根目录下生成一个 manifest.json 映射文件。

```bat
rem install
 yarn add --dev webpack-manifest-plugin
```

```javascript
const ManifestPlugin = require('webpack-manifest-plugin')
module.exports = {
  //  ...
  plugins: [new ManifestPlugin()]
}
```
