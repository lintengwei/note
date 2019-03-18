

- [babel 的使用](#babel-%E7%9A%84%E4%BD%BF%E7%94%A8)
  - [基本使用](#%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8)
    - [plugins](#plugins)
    - [presets](#presets)
  - [四种配置文件](#%E5%9B%9B%E7%A7%8D%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)
    - [属性](#%E5%B1%9E%E6%80%A7)

# babel 的使用

[https://babel.docschina.org/docs/en/](https://babel.docschina.org/docs/en/)
[使用细节](https://blog.csdn.net/maquealone/article/details/79575163)

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

### plugins

babel是一个编译器，通过babel可以把源码（可以使用某县浏览器尚未支持的新特性）转换成浏览器支持的语法的编译器。而插件就是我们告诉babel编译器该如何编译我们的源码。

### presets

因为javascript的版本一直在迭代，并且出了很多新的特性，如果自己手动的去配置插件，需要配置很多插件，并且可能会遗漏。所以babel官方出了一些预设的包，其中包含了各种插件，我们可根据我们代码的使用范围来需要配置预设的值，babel就会根据该值编译源码。

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

### 属性

版本关系

| 版本 | package | des          |
| ---- | ------- | ------------ |
| es6  | es2015  | 当前版本     |
| es7  | stage-0 | 下一个版本？ |

- presets
  - env
  - stage-0
    - 它包含 stage-1, stage-2 以及 stage-3 的所有功能，同时还另外支持如下两个功能插件：
      - transform-do-expressions
      - transform-function-bind
  - stage-1
    - 对该功能的正式建议。
    - 包含 stage-2 和 stage-3
    - plugins
      - transform-class-constructor-call
      - transform-class-properties
      - transform-decorators
      - transform-export-extensions
  - stage-2
    - 规范中的第一版。在这一点上，最终可能会将特性包含在标准中。
    - 包含 stage-3
    - plugins
      - syntax-trailing-function-commas
      - transform-object-reset-spread
  - stage-3
    - 该提案大部分已经完成，现在需要来自实现和用户的反馈才能进一步发展。
    - yarn add preset-stage-3
    - presets:['@bable/preset-stage-3']
    - 使用简短的路径 presets:['stage-3']
    - plugins
      - transform-async-to-generator
      - transform-exponentiation-operator
  - flow
  - react
    - yarn add @babel/preset-react
    - 包含插件
      - @babel/plugin-syntax-jsx
      - @babel/plugin-transform-react-jsx
      - @babel/plugin-transform-react-display-name
  - minify
  - typescript
    - yarn add @babel/preset-typescript
    - 包含插件
      - @babel/plugin-transform-typescript
- plugins
