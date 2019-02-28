# git 语法简要

[document](https://git-scm.com/book/zh/v2)

## git 配置文件

Git 自带一个 git config 的工具来帮助设置控制 Git 外观和行为的配置变量。 这些变量存储在三个不同的位置：

- /etc/gitconfig 文件: 包含系统上每一个用户及他们仓库的通用配置。 如果使用带有 --system 选项的 git config 时，它会从此文件读写配置变量。

- ~/.gitconfig 或 ~/.config/git/config 文件：只针对当前用户。 可以传递 --global 选项让 Git 读写此文件。

- 当前使用仓库的 Git 目录中的 config 文件（就是 .git/config）：针对该仓库。

每一个级别覆盖上一级别的配置，所以 .git/config 的配置变量会覆盖 /etc/gitconfig 中的配置变量。
==在 Windows 系统中，Git 会查找 $HOME 目录下（一般情况下是 C:\Users\$USER）的 .gitconfig 文件。 Git 同样也会寻找 /etc/gitconfig 文件，但只限于 MSys 的根目录下，即安装 Git 时所选的目标位置。==

在 windows 下各个文件的位置：

1. git config --system --list
   1. ds
2. git config --global --list
   1. C:\Users\<user>\.gitconfig
3. git config --local --list
   1. 在本地仓库的.git/config

## git 本地仓库

### 初始化本地仓库

```bat
rem 初始化一个本地git仓库
git init
rem 查看帮助 command 命令
git command --help
```

### 添加记录到暂存区

```bat
rem 添加单条记录记录
git add file
rem 添加全部修改的记录
git add *
```

### 提交记录到版本库

==此处操作版本库生成一个版本 id== 【commitid】

```bat
rem -m 为此次提交的记录信息
git commit -m 'the message'
rem  不需要add  直接提交修改
git commit -a -m
```

### 删除文件

```bat
rem 工作区中删除文件
rm file
rem 从版本库中删除文件 相当于 add 操作 ，操作同步更新到暂存区
git rm file

rem
rem  恢复删除的文件
rem
git checkout -- file

rem  如果先执行 rm file，后又执行 git rm file ,则需要两步才能回到之前的工作区
git reset HEAD -- file
git checkout -- file  //恢复到删除前的工作区
```

### 分支

```bat
rem 新建分支，并且切换到该分支
git checkout -b branchName
rem 上面的指令等同于下面两条指令
git branch branchName
git checkout branchName
rem 删除分支
git branch -d branchName
```

### 撤销文件的修改

```bat
rem
rem 撤销在工作区的修改，即在本地文件的修改
rem 只针对已经add过的文件，没有add过的文件是没有效果的
rem
git checkout -- file

rem
rem 撤销暂存区的修改，已经add，没有commit的
rem HEAD 表示的是最新的版本 HEAD~再上一个版本
rem
git reset HEAD -- file
```

### 查看状态和历史记录

```bat
rem 查看历史记录 即【commit】每次都会生成一个记录
git log
rem 查看当前工作区和暂存区的状态，尚未add，已经add，尚未commit都会有记录
git status
```

## 远程仓库

### 添加远程仓库

a. 在 github 上创建一个新的项目，生成一个项目地址

> 项目地址【https://github.com/ltw9527/git】

b. 在本地仓库下运行命令

```bat

rem  建立远程连接
rem  github.com:username/project.git
git remote add origin git@github.com:ltw9527/git.git

rem  推送本地服务到远程仓库
git push -u origin master
```

#### Permission denied (publickey).

> 这是因为 ssh 可以过期或者没有 ssh key，需要重新生成一个 ssh key 放在 github 账户上

1. 查找本地是否有 ssh 的文件
   1. id_rsa.pub 里面的字符拷贝到 github 里面的 setting 的 ssh 里面
2. 本地没有这个文件，需要新建一个

#### Threre is no tracking information for the current branch . Please specify which branch you want to merge with

> 这是因为需要指定本地仓库的分支，跟远程仓库项目分支的关系，如果我们需要本地的 master 对应远程的 master,指定了之后就可以使用了

```bat
git branch --set-upstream-to=origin/master master

rem  如果是别人的远程仓库，需要先pull，并且把merge的原因说上
git pull

rem  linux 操作指令
rem  编辑文件
rem vi fileName
rem i  insert
rem esc 失去编辑焦点
rem :w write 写入文件
rem :q exit 退出编辑
```

#### 当本地版本落后关联版本时候，好像不能提交，提示是需要拉去最新的版本下来

```bat
your branch is behind 'origin/master' ,
you should 'git pull' the newest.
```

#### 当本地版本落后关联版本，但是本地版本又做了修改，并且和关联不一致

```bat
error: Your local changes to the following files would be overwritten by merge:
        test.txt
Please, commit your changes or stash them before you can merge.
Aborting
```

> 针对上面的问题，需要先 commit 或者隐藏掉本地的修改，才可以 git pull 最新的 version。当 commit 之后，git pull，会自动的合并，并且本地的修改和关联库的修改都会体现

### unable to auto-detect email address

> 找到 config 文件加入

```bat
[user]
  email=xxx@163.com
  name=xxxx
```
