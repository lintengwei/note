- [node 的包](#node-%E7%9A%84%E5%8C%85)
  - [服务开发](#%E6%9C%8D%E5%8A%A1%E5%BC%80%E5%8F%91)
  - [用于命令行](#%E7%94%A8%E4%BA%8E%E5%91%BD%E4%BB%A4%E8%A1%8C)
  - [包使用](#%E5%8C%85%E4%BD%BF%E7%94%A8)
    - [chalk](#chalk)

# node 的包

## 服务开发

- express
  - 搭建服务的简易框架
  - http://expressjs.com/
- koa
  - express 的替代
  - https://github.com/koajs/koa#readme
- egg
  - node 企业级的后台服务，集成了很多服务
  - https://github.com/eggjs/egg

## 用于命令行

- chalk
  - 样式化文本，彩色输出
  - [github](https://github.com/chalk/chalk#readme)
  - [使用简介](#chalk)
- clear
  - 清空屏幕
  - [github](https://github.com/bahamas10/node-clear#readme)
- clui
  - 绘制命令行中的表格，加载指示等
  - [github](https://github.com/nathanpeck/clui#readme)
- figlet
  - 生成字符串图案
  - [github](https://github.com/patorjk/figlet.js#readme)
- inquirer
  - 创建交互式的命令行界面
  - [github](https://github.com/SBoudrias/Inquirer.js#readme)
- minimist
  - 解析参数
  - [github](https://github.com/substack/minimist)
- configstore
  - 加载和保存配置
  - [github](https://github.com/yeoman/configstore#readme)

## 包使用

### chalk

设置控制台输出的颜色，可以设置前景色和背景色

```bat
rem install
yarn add chalk
```

```javascript
const chalk = require('chakl')
//  把msg字符串设置为蓝色
const msg = chalk.blue('msg')
//  输出背景色为blue的string
const mmsg = chalk.bgBlue('msg')
//  把msg输出在屏幕，并且颜色为蓝色
console.log(msg)
```
