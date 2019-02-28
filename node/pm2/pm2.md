# pm2 

## cli

> pm2 start  app.js -i max

开启或者添加服务进程。参数 -i max 可以开启多进程模式 max为最大进程数

> pm2 restart app
> pm2 reload app

关闭和重启服务

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