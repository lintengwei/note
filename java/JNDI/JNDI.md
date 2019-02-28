# JNDI

[详解](https://blog.csdn.net/u010430304/article/details/54601302/)

[参考](https://www.jb51.net/article/81695.htm)

JNDI 是 Java Naming and Directory Interface（JAVA 命名和目录接口）

JNDI 诞生的理由似乎很简单。随着分布式应用的发展，远程访问对象访问成为常用的方法。虽然说通过 Socket 等编程手段仍然可实现远程通信，但按照模式的理论来说，仍是有其局限性的。RMI 技术，RMI-IIOP 技术的产生，使远程 对象的查找成为了技术焦点。JNDI 技术就应运而生。JNDI 技术产生后，就可方便的查找远程或是本地对象。

JNDI 架构提供了一组标准的独立于命名系统的 API，这些 API 构建在与命名系统有关的驱动之上。这一层有助于将应用与实际数据源分离，因此不管应用访问的是 LDAP、RMI、DNS、还是其他的目录服务。换句话说，JNDI 独立于目录服务的具体实现，只要有目录的服务提供接口（或驱动），就可以使用目录。

关于 JNDI 要注意的重要一点是，它提供了应用编程接口(application programming interface，API)和服务提供者接口(service provider interface，SPI)。这一点的真正含义是，要让应用与命名服务或目录服务交互，必须有这个服务的 JNDI 服务提供者，这正是 JNDI SPI 发挥作用的地方。服务提供者基本上是一组类，这些类为各种具体的命名和目录服务实现了 JNDI 接口—很像 JDBC 驱动为各种具体的数据库系统实现了 JDBC 接口一样。作为一个应用开发者，我们不必操心 JNDI SPI 的具体实现。只需要确认要使用的每一个命名或目录服务都有服务提供者。

1. Javax.naming：包含了访问命名服务的类和接口。例如，它定义了 Context 接口，这是命名服务执行查询的入口。
2. Javax.naming.directory：对命名包的扩充，提供了访问目录服务的类和接口。例如，它为属性增加了新的类，提供了表示目录上下文的 DirContext 接口，定义了检查和更新目录对象的属性的方法。
3. Javax.naming.event：提供了对访问命名和目录服务时的事件通知的支持。例如，定义了 NamingEvent 类，这个类用来表示命名/目录服务产生的事件，定义了侦听 NamingEvents 的 NamingListener 接口。
4. Javax.naming.ldap：这个包提供了对 LDAP 版本 3 扩充的操作和控制的支持，通用包 javax.naming.directory 没有包含这些操作和控制。
5. Javax.naming.spi：这个包提供了一个方法，通过 javax.naming 和有关包动态增加对访问命名和目录服务的支持。这个包是为有兴趣创建服务提供者的开发者提供的。

JNDI 可访问的现有的目录及服务有：

DNS、XNam 、Novell 目录服务、LDAP(Lightweight Directory Access Protocol 轻型目录访问协议)、 CORBA 对象服务、文件系统、Windows XP/2000/NT/Me/9x 的注册表、RMI、DSML v1&v2、NIS。

JNDI 优点：

包含了大量的命名和目录服务，使用通用接口来访问不同种类的服务；可以同时连接到多个命名或目录服务上；建立起逻辑关联，允许把名称同 Java 对象或资源关联起来，而不必指导对象或资源的物理 ID。

常用的 JNDI 操作：

void bind(String sName,Object object);――绑定：把名称同对象关联的过程
void rebind(String sName,Object object);――重新绑定：用来把对象同一个已经存在的名称重新绑定
void unbind(String sName);――释放：用来把对象从目录中释放出来
void lookup(String sName,Object object);――查找：返回目录总的一个对象
void rename(String sOldName,String sNewName);――重命名：用来修改对象名称绑定的名称
NamingEnumeration listBinding(String sName);――清单：返回绑定在特定上下文中对象的清单列表
NamingEnumeration list(String sName);

```java

```
