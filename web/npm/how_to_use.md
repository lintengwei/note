# 如何使用

## 设置镜像

> npm config set registry http://registry.npmjs.org/  

## 关于npm安装依赖包

 如果package.json里面已经有dependence，则会按照dependence定义的规则下载，如果package.json里面没有依赖，则会安装最新版
```java
npm install <package>
```

### 全局安装

> 如果想在cli里面用命令行使用包，则需要全局安装

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

## 关于package.json

```java
npm init  自定义value
npm init --yes  默认value
```

### 字段说明

如果没有description字段，则会读取README.md第一行作为值，或者是README，可以让搜索功能找到我们的包

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

从node_modules文件夹移除
```java
npm uninstall <package>
```
从node_modules和package.json的dependence中移除，新版好像可以通过上述移除
```java
 npm uninstall --save lodash
```

## 创建node模块

## 发布和更新包

> 一个包的内容具体有哪些

+ a) a folder containing a program described by a package.json file
+ b) a gzipped tarball containing (a)
+ c) a url that resolves to (b)
+ d) a <name>@<version> that is published on the registry with (c)
+ e) a <name>@<tag> that points to (d)
+ f) a <name> that has a "latest" tag satisfying (e)
+ g) a git url that, when cloned, results in (a).

### 发布

本地测试是否可以打包
```java
npm install . -g
//  在其他工程里面测试，打开一个其他工程，然后在其他工程安装完成的包
cd pro2
npm install ../pro1
```
添加用户
```java
//  按提示输入
npm adduser
```
发布包403的问题
+ 因为最初的http://registry.npmjs.org这个源国内用户访问非常慢，所以一开始我们可能换成了国内淘宝的镜像：http://registry.npm.taobao.org。但是淘宝这个镜像没有开放上传的权限，因此会报这样的错误：
+ 必须在发布包内重新登录才可以发布
+ 注册止呕必须要验证邮箱才可以发布包

### 更新

