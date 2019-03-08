# npm 命令行

[https://docs.npmjs.com/cli-documentation/](https://docs.npmjs.com/cli-documentation/)

## npm 控制

- npm install npm@latest -g
  - 更新 npm 版本
- npm -version
  - 查看当前版本
- npm root <-g>
  - 查看当前包管理位置
- npm prefix [-g]
  - 查看当前包管理的父目录

## 用户控制

- npm login
  - npm 用户登录
- npm logout
  - npm 用户登出
- npm whoami
  - 查看当前用户
- npm token
  - 查看当前用户的令牌

## 关于包信息

- npm home <package>
  - 打开包的 github 主页
- npm docs <package>
  - 打开包的文档
- npm bugs <package>
  - 打开包的 github 仓库的 issue 页面
- npm view <package>
  - 查看包的版本信息
- npm ls
  - 查看已安装的包
  - -prod
    - 查看 dependencies 包
  - -dev
    - 查看 dev-dependencies 包
  - -json
    - 以 json 格式输出
- npm search <package>
  - 搜索包

## 远程包管理

- npm publish --access=public
  - 发布包
  - 在删除包之后的 24 小时内，不允许在发布相同的包
  - 在发布包内的 72 小时，不允许删除包
- npm unpublish /f
  - /f
    - 强制从仓库中删除包
  - 删除包

## 本地包管理

- npm install --save|-S <package>
  - 默认本地安装
  - <-g> 全局安装
- npm uninstall --save <package>
  - 写在包
- npm update <-g> <pkg..>
  - 更新包
  - 如果指定了包名，则更新指定包，否则更新全部！
