# egg框架

[项目地址](https://eggjs.org/zh-cn/intro/)

## 数据库

> 安装egg-mysql插件

npm i --save egg-mysql

> 导入egg-mysql插件，/config/plugin.js

```javascript 
exports.mysql = {
  enable: true,
  package: 'egg-mysql',
};
```

> 配置egg-msyql插件，/config/config.{env}.js

```javascript
  config.mysql = {
    client: {
      // host
      host: '127.0.0.1',
      // 端口号
      port: '3306',
      // 用户名
      user: 'root',
      // 密码
      password: '123456',
      // 数据库名
      database: 'test',
    },
    // 是否加载到 app 上，默认开启
    app: true,
    // 是否加载到 agent 上，默认关闭
    agent: false,
  };
```

## 静态资源:::前后分离方式

内置了static插件，文件架构：

+ 文件放在 app/public 目录下
+ 访问文件 procotol://hostname:port/public/__filename.ext

## 后端渲染方式

内置了对egg-view模版引擎的支持：

## 安全模式

### csrf

```javascript

//  客户端
    function xhr() {
      return new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest()
        var reg = /csrfToken=(?=(.+));/
        var token;
        if (document.cookie) {
          token = reg.exec(document.cookie)[1]
        }
        xhr.onreadystatechange = function () {
          if (xhr.readyState == 4 && xhr.status === 200) {
            return resolve(xhr.responseText)
          }
        }
        xhr.open('post', 'http://localhost:8080/')
        xhr.setRequestHeader('Content-Type', 'json/application')
        if (token) {
          //  ajax需要加token，key可以修改
          xhr.setRequestHeader('x-csrf-token', token)
        }
        xhr.send();
      })
    }
```