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

```bat
rem install
npm install --save-dev clean-webpack-plugin
```

> Path

```javascript
;[
  'dist', // removes 'dist' folder
  'build/*.*', // removes all files in 'build' folder
  'web/*.js' // removes all JavaScript files in 'web' folder
]
```

> options 可选参数

- allowExternal
  - 允许插件清理 webpack 根目录之外的文件夹。

## mini-css-extract-plugin

把 css 打包成一个单独的包.适用于 webpack@4.0.0。[doc](https://github.com/shama/stylus-loader)

## ExtractTextWebpackPlugin

把 css 打包成一个单独的包. webpack@3.0.0。 对于 webpack@4.0.0+，使用【mini-css-extract-plugin】

```bat
rem install
npm install --save-dev extract-text-webpack-plugin
```

```javascript
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const extract = new ExtractTextPlugin({
  filename: 'common.[chunkhash].css'
})
module.exports = {
  //  ...
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
