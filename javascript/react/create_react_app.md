# create_react_app

(create_react_app 文档)[https://facebook.github.io/create-react-app/docs/folder-structure]

## 环境修改

1. 端口，默认端口为 3000
1. 设置环境变量 PORT=8070

## install chrome-react-devtool

[安装过程](https://blog.csdn.net/wp_boom/article/details/79011177)

1. 克隆 git 包
1. git clone https://github.com/facebook/react-devtools.git
1. 安装依赖
1. npm install
1. 构建
1. npm run build:extension:chrome

## install redux-devtool

[官方地址](https://github.com/zalmoxisus/redux-devtools-extension)

1. git clone https://github.com/zalmoxisus/redux-devtools-extension
2. npm i && npm run build:extension
3. 加载扩展包 ./build/
4. usage
5. 在创建 store 时候需要添加本地依赖
6. createStore(reducers, window.**REDUX_DEVTOOLS_EXTENSION** && window.**REDUX_DEVTOOLS_EXTENSION**())

## 兼容包

> 目前只包含如下兼容包，如果需要其他运行时支持包，则需要另外导入

- Object.assign() via object-assign.
- Promise via promise.
- fetch() via whatwg-fetch.

## 修改 spa 的 title

[react-helmet.git](https://github.com/nfl/react-helmet)

1. npm install --save react-helmet
2. usage
3. import Helmet from 'react-helmet'
4. in render function
   1. <Helmet><title>your title</title><Helmet/>

## 分包加载

[如何使用？](https://serverless-stack.com/chapters/code-splitting-in-create-react-app.html)

```javascript
//  npm install --save react-loadabel
//  defined the loading component
import Loadable from 'react-loadable';
import Loading from '../pages/loading';

const LoadableComponent = new Loadable({
  loader: import('../pages/component'),
  loading: Loading //  required
});
```

## 构建应用

> 是否生成 sourcemap？

设置环境变量 GENERATE_SOURCEMAP =false

> 设置路径？

设置环境变量 PUBLIC_URL=./，会根据这个来生成 html 文件
