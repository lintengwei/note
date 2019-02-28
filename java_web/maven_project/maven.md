# maven 使用

## 下载安装

### 下载

[【maven 下载地址】http://maven.apache.org/download.cgi#](http://maven.apache.org/download.cgi#)

[【依赖包下载地址】http://mvnrepository.com/](http://mvnrepository.com/)

### 安装

加压下载的文件，之后：

- 配置系统环境变量
  - M2_HOME -> 安装目录（解压的目录）
- 添加 path 变量
  - ;%M2_HOME%/bin
- 打开 cmd 窗口，检测是否安装成功
  - mvn -v

## 基本使用

### 配置文件

```xml
<!-- 本地仓库 -->
<localRepository>D:\m2repository</localRepository>

<!-- 镜像 -->
<!-- 阿里云 -->
<mirrors>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
</mirrors>
```

### maven 项目

> 需要自己新建一个 xml 文件，配置文件在隔壁

## 仓库的概念

### 远程仓库

### 本地仓库
