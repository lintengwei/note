# eclipse 常见的问题

## 项目类型

1. EJB Project 企业 Java Bean 规范的项目
   1. Enter JavaBean [详解](https://blog.csdn.net/u011225629/article/details/49847643)
2. Enterprise Application Project 企业应用项目
3. Dynamic Web Project javaWeb 项目
   1. 动态 web 项目，会把 webContent 里面的内容拷贝到 tomcat 下
4. Application Client Project 应用程序客户端项目
5. Connector Project 连接器项目
6. WebService Project 当你是做分布式系统时，也就是你做的只是一个服务，对外发布也只是一个服务的时候，需要建立 Web Services Project(不理解可以先研究下 webservices)
7. Java Project C/S 架构的项目
8. Web Fragment Project 是 Servlet3.0 的支持的新特性，简单理解就是将一个 web 工程分成多个部分进行开发调试
9. Report Web Project 是 web 版 但是侧重于报表的
10. Session Bean（EJB 3.x）：建议 EJB 3.x 版本规范的 Java Bean
11. Message-Driver Bean（EJB 3.x）：建立基于 EJB 3.x 规范的消息,驱动 Java Bean
12. Example：基于模板建立文件

## J2EE，J2SE，J2ME 三者有什么不同？

J2EE，J2SE，J2ME 是 Sun 公司的 Java 多个版本,就像 Windows XP 还有专业版和家庭版是一样的。
J2SE：Java 2 Platform Standard Edition 标准版，用于桌面应用，也是 J2EE 的基础。
J2EE：Java 2 Platform Enterprise Edition 企业版，用于企业应用，支持分布式部署。
J2ME：Java 2 Platform Micro Edition 移动版用于小型设备，是 J2SE 的一个子集。

Java2 平台包括企业版（J2EE）、标准版（J2SE）和微缩版（J2ME）三个版本，功能上也是从左到右变小。：
Standard Edition(标准版) J2SE 包含那些构成 Java 语言核心的类。
比如：数据库连接、接口定义、输入/输出、网络编程
Enterprise Edition(企业版) J2EE 包含 J2SE 中的类，并且还包含用于开发企业级应用的类。
比如：EJB、servlet、JSP、XML、事务控制
Micro Edition(微缩版) J2ME 包含 J2SE 中一部分类，用于消费类电子产品的软件开发。
比如：呼机、智能卡、手机、PDA、机顶盒

他们的范围是：J2SE 包含于 J2EE 中，J2ME 包含了 J2SE 的核心类，但新添加了一些专有类

应用场合，API 的覆盖范围各不相同。
笼统的讲，可以这样理解：
J2SE 是基础；
压缩一点，再增加一些 CLDC 等方面的特性就是 J2ME；
扩充一点，再增加一些 EJB 等企业应用方面的特性就是 J2EE。

## 配置 svn

从软件内安装：

1. [https://blog.csdn.net/qq_16946803/article/details/82148222](https://blog.csdn.net/qq_16946803/article/details/82148222)
2. http://subclipse.tigris.org/update_1.12.x

从软件外安装：

1. [https://blog.csdn.net/qq_39529566/article/details/81511910](https://blog.csdn.net/qq_39529566/article/details/81511910)

## 配置 tomcat

tomcat 的配置步骤

- preferences->service->runtime-envioronments->add tomcat-service
- 404 error
  - [解决](https://blog.csdn.net/huaibaowang/article/details/52900855)
  - 默认的访问路径是 eclipse 的 webapps 文件
  - 右击 service，switch location
  - 更改 eslipse 里面 service 的路劲映射
  - 修改完之后恢复 tomcat 里面的文件，重启服务器

## FAQ

### source not found

当查看源码的时候，出现 source 的问题。可能是反编译的问题，参考网上的方法如下：

方法 1：

1. help->eclipse MarketPlace
2. 搜索关键字[DeComplier]
3. 安装最多人装的，配置 file 关联

方法 2：

- 下载 jad
  - http://varaneckas.com/jad/
- 下载 jadeclipse 插件
  - http://sourceforge.net/projects/jadclipse/
  - 将下载的插件放在 eclipse 的 plugins 目录下
- 关联 jad 反编译器
  - 下载完关联插件之后，需要配置关联
    - preferences->java->javaClipse
      - 这里设置 jad.exe 所在目录
- 关联源码反编译插件
  - preferences->general->editor->file associatior
    - 设置.class 的默认反编译为 jad
    - 设置.class without source 默认反编译为 jad
- 按照报名显示层级结构
  - window > Navigation > Show View Menu > Package Presentation > Hierachial.

### 关于 Creation of element failed(myEclipse)错误

可能是没有新建 xml 文件

### 变量名称自动加类型的解决方法

> 先找到相关的插件： window -> show view -> plug-ins

找到插件 org.eclipse.jface.text,右键点击,选择 import as Source Project,导入完成后,在你的 workspace 就可以看到这个 project 了

> 修改代码

在 src/org/eclipse/jface/text/contentassist/CompletionProposalPopup.java 文件中,找到这样一行代码
char[] triggers = t.getTriggerCharacter();
if(contains(triggers,key))
在那行 if 判断里面,eclipse 会判断 key(就是你按下的键)是否在 triggers 中,如果是,那就触发下面的第一行提示上屏的代码.所以我们要做的就是把空格和=号排除就可以了:
if(key != '=' && key != 0x20 &&contains(triggers,key)){
.........
}

> .把修改好的 org.eclipse.jface.text 导出

右键点击你的 workspace 里的 org.eclipse.jface.text,选择 export-->Deployable plugins and
fragments, next,destination 选择
archive file，然后 finish.你就可以在 zip 文件里看到生成好的 jar ,用它替换掉 eclipse/plugins 里面的同名 jar 包,就可以了。

### eclipse 默认编码方式是 GBK，修改编码方式 utf-8

- preferences->workspace-text file encoding

### could not create jvm

- 可能是 jdk 的版本和 eclipse 不支持，需要查看官方的通告

### maven 项目报错

> 没有 web.xml 文件

```xml
<!-- 创建web.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<web-app
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	id="WebApp_ID"
	version="3.0">
</web-app>
<!-- 在pom.xml文件里面加入 -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.6</version>
				<configuration>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>
		</plugins>
	</build>
<!--项目右键 update maven -->
```

### Eclipse Maven 编译错误 Dynamic Web Module 3.0 requires Java 1.6 or newer 解决方案

这是由于你的 Maven 编译级别是 jdk1.5 或以下，而你导入了 jdk1.6 以上的依赖包。解决办法：使用 maven-compiler-plugin 将 maven 编译级别改为 jdk1.6 以上,update maven：

```xml
<build>
    <plugins>
        <!-- define the project compile level -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>2.3.2</version>
            <configuration>
                <source>1.7</source>
                <target>1.7</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Cannot detect Web Project version. Please specify version of Web Project through Maven project property <webVersion>.

需要指定 war 的 maven 编译包

```xml
<build>
		<plugins>
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.7</source>
					<target>1.7</target>
				</configuration>
			</plugin>
			<plugin>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.4</version>
				<configuration>
					<version>3.0</version>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>
			</plugin>

		</plugins>
	</build>
```

### java.lang.UnsupportedClassVersionError: org/apache/commons/logging/LogFactoryService : Unsupported major.minor version 52.0 (unable to load class org.apache.commons.logging.LogFactoryService)

[https://www.cnblogs.com/pangxiansheng/p/5426905.html](https://www.cnblogs.com/pangxiansheng/p/5426905.html).

- build path 的 JDK 版本是你开发的时候编译器需要使用到的，就是你在 eclipse 中开发代码，给你提示报错的，编译的过程；
- java compiler compliance level 中配置的编译版本号，这个编译版本号的作用是，你这个项目将来开发完毕之后，要放到服务器上运行，那个服务器上 JDK 的运行版本。

在 eclipse 中进行开发的时候，build path 中 JDK 进行类库的编译（就是你使用类在不在这个 JDK 中),java compiler compliance level 是对这个项目语法的编译（就是你的项目中语法的正确与否），在开发的过程中，这两个地方是都起作用的。所以说，build path 和 java complier compliance level 和服务器配置的 JDK 保持一致，就不会出现任何问题的。

stanford parser 和 jdk 版本对应关系
Java SE 11 = 55,
Java SE 10 = 54,
Java SE 9 = 53,
Java SE 8 = 52,
Java SE 7 = 51,
Java SE 6.0 = 50,
Java SE 5.0 = 49,
JDK 1.4 = 48,
JDK 1.3 = 47,
JDK 1.2 = 46,
JDK 1.1 = 45

### eclipse 中的.project 和 .classpath 文件的具体作用

[https://blog.csdn.net/greensure/article/details/77113215](https://blog.csdn.net/greensure/article/details/77113215)
