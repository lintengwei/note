# npm

[npm Repositories](https://www.npmjs.com/)

## 参数

## npm 镜像

> npm install -g cnpm --registry=https://registry.npm.taobao.org
> npm 官方仓库 http://registry.npmjs.org

## npm 更新

> npm install npm@latest -g
> npm install npm@next -g

## 包管理

### 如何发布包含命名空间的包？

- 如果只是单个包在某个命名空间下
  - npm init --scope=username
- 如果发布包都在某个命名空间下
  - npm config set scope=username

### 包的版本规则

## cli

[https://blog.sigoden.com/npm-can-kao-shou-ce/](https://blog.sigoden.com/npm-can-kao-shou-ce/)

##

- npm init
  - 初始化一个 node 项目
    - 参数
      - --scope=username
        - 命名空间使用
      - --yes
        - 一切按照默认的配置生成 json 文件
- npm login
  - 登陆
  - username
  - password
  - email_address
- npm view packagename
  - 查看仓库是否有指定的包
- npm publish
  - 注册包到仓库
  - 删除之后，24 小时之内不能再次上传
- npm unpublish --force
  - 从仓库删除包
  - 一般 npm 仓库不允许删除，所以需要加--force 参数
- npm config
  - 查看当前配置文件
  - /? 查看帮助
  - npm config set key=value
    - 设置属性
  - npm config list -l
    - 查看所有配置项
- npm access
  - 设置已发布包的访问级别
