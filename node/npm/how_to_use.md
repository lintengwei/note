# 如何使用

[http://javascript.ruanyifeng.com/nodejs/npm.html](http://javascript.ruanyifeng.com/nodejs/npm.html)

## 设置镜像

> npm config set registry http://registry.npmjs.org/

## 关于 npm 安装依赖包

如果 package.json 里面已经有 dependence，则会按照 dependence 定义的规则下载，如果 package.json 里面没有依赖，则会安装最新版

```java
npm install <package>
```

### 全局安装

> 如果想在 cli 里面用命令行使用包，则需要全局安装

```java
npm install -g <package>
//  删除全局
npm uninstall -g <package>
//  寻找可以更新的包
npm outdated -g --depth=0
// update globle package
npm update -g <package>
//  更新全部
npm update -g
```

## 关于 package.json

```java
npm init  自定义value
npm init --yes  默认value
```

### 字段说明

如果没有 description 字段，则会读取 README.md 第一行作为值，或者是 README，可以让搜索功能找到我们的包

```java{.line-numbers}
name: the current directory name
version: always 1.0.0
description: info from the readme, or an empty string ""
main: always index.js
scripts: by default creates an empty test script
keywords: empty
author: empty
license: ISC
bugs: info from the current directory, if present
homepage: info from the current directory, if present
```

To add an entry to your package.json's dependencies:

```java
npm install <package_name> --save
```

To add an entry to your package.json's devDependencies:

```java
npm install <package_name> --save-dev
```

## 删除

从 node_modules 文件夹移除

```java
npm uninstall <package>
```

从 node_modules 和 package.json 的 dependence 中移除，新版好像可以通过上述移除

```java
 npm uninstall --save lodash
```

## 创建 node 模块

## 发布和更新包

> 一个包的内容具体有哪些

- a) a folder containing a program described by a package.json file
- b) a gzipped tarball containing (a)
- c) a url that resolves to (b)
- d) a <name>@<version> that is published on the registry with (c)
- e) a <name>@<tag> that points to (d)
- f) a <name> that has a "latest" tag satisfying (e)
- g) a git url that, when cloned, results in (a).

### 发布

本地测试是否可以打包

```bat
npm install . -g
rem  在其他工程里面测试，打开一个其他工程，然后在其他工程安装完成的包
cd pro2
npm install ../pro1
```

添加用户

```bat
rem  按提示输入
npm adduser
```

发布包 403 的问题

- 因为最初的http://registry.npmjs.org这个源国内用户访问非常慢，所以一开始我们可能换成了国内淘宝的镜像：http://registry.npm.taobao.org。但是淘宝这个镜像没有开放上传的权限，因此会报这样的错误：
- 必须在发布包内重新登录才可以发布
- 注册必须要验证邮箱才可以发布包

### 更新
