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
