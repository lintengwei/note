# pm2 (Production Process Manager)

[https://pm2.io/doc/en/runtime/overview/](https://pm2.io/doc/en/runtime/overview/)

## cli

> pm2 start  app.js -i max

开启或者添加服务进程。参数 -i max 可以开启多进程模式 max为最大进程数

> pm2 restart app
> pm2 reload app

重启和重载服务

> pm2 stop app.js

关闭服务，参数可以是【id|name|all|json|stdin】 

+ id
  + pm2开启后生成的唯一id，可以通过 pm2 ls 命令查看进程id
+ name 
  + 文件名
+ all 
  + 关闭所有pm2的服务

> pm2 logs

查看服务日志（pm2的日志文件保存在【 ~/.pm2/logs/】）

> pm2 ls

查看pm2启动的进程

> pm2 monit

查看进程状态

> pm2 init

生成pm2的配置文件，具体信息查看ecosystem.config.js

## 根据配置文件启动项目

[全部配置参数](https://pm2.io/doc/en/runtime/reference/ecosystem-file/)

- pm2 init
  - ecosystem.config.js运行以上命令，会在当前目录生成一个配置文件【】
- pm2 start [path]
  - 会在当前目录中寻找文件【ecsystem.config.js】，吐过没有找到会报错，或者可以指定【path】来告诉pm2配置文件的位置，默认会启动所有配置文件中指定的项目！
  - 可选参数
    - --only appname
      - 值启动指定的某个项目，appname为config文件中定义的name
    - --env [production]
      - 通过该参数可以指定运行的环境，如果没有指定，则默认为developent，否则根据【env】后置的参数倆决定启用哪种环境
- pm2 restart [path]
  - 可选参数
    - --update-env
      - 强制更新运行环境

==一旦运行环境指定了之后，restart或者reload都不会改变其开始指定过的环境，除非指定了参数[--update-env]==

```javascript
module.exports = {
  apps : [{
    name: "app",  //  pm2 ls 显示的项目名
    script: "./app.js", //  项目的启动脚本位置
    
    //  指定运行环境 env_{environment-name}
    //  开发环境
    env: {
      NODE_ENV: "development",
    },
    //  生产环境
    env_production: {
      NODE_ENV: "production",
    }
  }]
}
```
