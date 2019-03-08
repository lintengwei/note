# babel 的使用

[https://babel.docschina.org/docs/en/](https://babel.docschina.org/docs/en/)

## 基本使用

```bat
rem 安装包
rem @babel/cli babel的编译工具
rem @babel/core babel的核心功能
rem @babel/preset-env 转换语法功能
yarn add @babel/cli @babel/core @babel/preset-env -D
rem @babel/polyfill 实现目标环境中缺少的功能
yarn add @babel/polyfill
```

babel.config.js：

```javascript
module.exports = {
  presets: ['@babel/env']
}
```

package.json 文件：

```javascript
{
  script:{
    //  受用babel编译src目录下的js文件，并且把编译过的文件存储在dist目录下
    'build':'./node_modules/.bin/babel src --out-dir dist'
  }
}
```

## 四种配置文件

1. babel.config.js

```javascript
module.exports = function(api) {
  api.cache(true)
  const presets = []
  const plugins = []
  return {
    presets,
    plugins
  }
}
```

2. .babelrc

```json
{
  "presets":[...],
  "plugins":[...]
}
```

3. .babelrc.js

```javascript
const presets=[...]
const plugins=[...]
module.exports={
  presets,
  plugins
}
```

1. package.json

```json
{
  "name": "my-package",
  "version": "1.0.0",
  "babel": {
    "presets": [ ... ],
    "plugins": [ ... ],
  }
}
```
