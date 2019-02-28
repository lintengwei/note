# 如何使用 ssh 协议和 github 绑定

[https://help.github.com/en/articles/connecting-to-github-with-ssh](https://help.github.com/en/articles/connecting-to-github-with-ssh)

## 查看本地是否有 ssh 记录

```bat
rem 打开git shell
rem 输入一下command 会列出本地已存在的ssh记录
rem 如果是window一般在用户所属文件目录下面的.ssh文件夹下面
ls -al ~/.ssh
```

## 新建 ssh 文件

1. 打开 Git Bash 客户端
2. ssh-keygen -t rsa -b 4096 -C "253357669@qq.com"

## 测试是否能连接到 github

```bat
rem 打开 Git Bash
ssh -T git@github.com
```
