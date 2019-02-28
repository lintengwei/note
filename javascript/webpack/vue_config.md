# webpack 的 vue 配置

## 需要安装的 loaders

```bat
rem vue-loader 解析vue文件
rem vue-template-compiler 解析template模版
rem vue-style-loader 解析style
cnpm install --save-dev vue-loader vue-template-compiler vue-style-loader
```

## tip

1. 默认 import 的 vue 包是运行时构建，如果在开发环境中，需要做如下配置

```javascript
//  webpack.config.js中
//  vue默认导出的是vue.common.js包，其中不包含template编译模块
//  而vue.js中包含该模块
module.exports = {
  //  ...
  resolve: {
    vue: 'vue/dist/vue'
  }
}
```
