# Java 的资源访问控制

[入门](https://www.ibm.com/developerworks/cn/java/j-lo-javasecurity/)

[安全管理器](https://www.cnblogs.com/yiwangzhibujian/p/6207212.html)

[权限](https://www.cnblogs.com/Qspace/articles/2373197.html)

[policy 文档](https://docs.oracle.com/javase/1.5.0/docs/guide/security/PolicyFiles.html)

## 开启

-Djava.security.manager
-Djava.security.manager -Djava.security.policy=D:\Java\jdk1.8.0_05\jre\lib\security\java.policy

## FAQ

1. 能否在程序运行时候动态的修改权限？
2. 关于 doPrivileged 的使用和理解？

某些导入类没有执行权限，可以使用该方法让检查类不去验证
