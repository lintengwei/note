# 一些软件的安装

## java

+ [下载jdk并安装](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ 配置环境变量
  + 变量名：JAVA_HOME
    + 变量值：C:\Program Files (x86)\Java\jdk1.8.0_91        // 要根据自己的实际路径配置
  + 变量名：CLASSPATH
    + 变量值：.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;    //记得前面有个"."
  + 变量名：Path
    + 变量值：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;

## maven

+ [下载maven并安装](https://maven.apache.org/download.cgi)
+ 配置环境变量
  + M2_HOME
    + 新建系统变量M2_HOME，path填写maven安装所在目录
  + path中添加
    + ;%M2_HOME%/bin
  + 检查是否安装成功
    + mvn -v 显示版本号
+ 配置setting
  + 本地仓库 ->localRepository
  + 中央仓库 ->mirror 
    + url -> http://maven.aliyun.com/nexus/content/groups/public