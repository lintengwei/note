# vue-cli@3.0+ 的使用

## 全局安装

如果之前安装了 1.x 或者 2.x 版本的需要先卸载？

```bat
rem uninstall
cnpm uninstall -g vue-cli
yarn remove global vue-cli
rem npm install
cnpm install -g @vue/cli
rem yarn
yarn add global @vue/cli
```

## 创建和运行

```bat
vue create projectName
cd projectName
rem 运行开发模式
npm run dev/server
```

## css 相关

```bat
rem sass
cnpm install --save-dev sass-loader node-sass
rem less
cnpm install --save-dev less-loader less
rem stylus
cnpm install --save-dev stylus-loader stylus
```

## 静态资源的使用

可以定义外部静态资源

- <%= VALUE %> 用来做不转义插值；
- <%- VALUE %> 用来做 HTML 转义插值；
- <% expression %> 用来描述 JavaScript 流程控制。

## 环境变量相关

一般开发模式和构建模式对应的后台访问地址是不一致的，可以通过这个来设置
可以在根目录下设置四种文件，分别为：

- .env # 在所有的环境中被载入
- .env.local # 在所有的环境中被载入，但会被 git 忽略
- .env.[mode] # 只在指定的模式中被载入
- .env.[mode].local # 只在指定的模式中被载入，但会被 git 忽略

.env.development
.env.production

```javascript
//  访问
//  只有以  VUE_APP_开头的键才能在开发中使用
console.log(process.env.VUE_APP_xxx)

//  在 .env.development中
VUE_APP_URL = 'http://192.168.1.116/server'

//  在  .env.production
VUE_APP_URL = 'http://wwww.ltengwei.com/server'

//  判断当前的开发模式
NODE_ENV==='development' or NODE_ENV='production'
```
