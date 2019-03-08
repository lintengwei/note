# yarn 命令行

[https://yarn.bootcss.com/docs/cli/](https://yarn.bootcss.com/docs/cli/)

## 用户信息

- yarn login
  - 登录
- yarn logout
  - 登出

## 本地包管理

- yarn add <package...> <global>
  - 安装包
  - --dev/-D
    - 安装在 devDependencies 里面
  - --peer/-P
    - peerDependencies
  - --optional/-O
    - optionalDependencies
- yarn remove <package>
  - 移出包
- yarn upgrade <package>
  - 更新包
- yarn list [--depth][--pattern]
  - 列出当前安装的包
- yarn version
  - 设置包的版本
- yarn versions
  - 查看当前系统版本信息
- yarn why <package>
  - 查看包为何安装
- yarn install
  - 安装所有包

## 远程包管理

- yarn publish --access <public|restricted>
  - 发布包
- yarn unpublish

## 配置信息

- yarn config
  - get <key>
    - 获取某个 key 的值
  - set <key> <value> <-g|--global>
    - 设置
  - delete <key>
    - 删除
  - list
    - 列表
