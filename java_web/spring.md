# spring

最主要的功能是管理对象的创建社生命周期

## Bean 的基础

通过 xml 文件的配置来告知 spring 如何管理，其使用过程如下：

1. 在 xml 里面声明 bean 的限定类名和对应的唯一 id

```xml
<bean
  class="com.wholler.tk.bean.User"
  id="user"/>
```

2. 获取 xml 配置文件

```java
//  在获取bean之前需要获取xml配置的context实例，有三种方法可以获取
// 通过内置的ClassPathApplicationContext获取 ，xmlpath放在src目录下 测试使用
ApplicationContext context=new ClassPathApplicationContext(xmlPath);

//  getWebApplicationContext
ApplicationContext ap =WebApplicationUtils.getWebApplicationContext(servletContextParam);

//  自定义ApplicationContext，为什么推荐使用这个方法？
//  在xml里面配置 <bean id="springContextUtil" class="com.school.tool.SpringContextUtil" />
//  这样，在根据applicationContext初始化上下文时，会自动调用setApplicationContext()方法去获取ApplicationContext。
//  1.注解 2.实现ApplicationContextAware接口
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

}

ApplicationContext=SpringContextUtil.getApplicationContext();
```

### bean 的作用域

[作用域测试](https://blog.csdn.net/panhaigang123/article/details/79452064)

1. 单例
   1. singleton【默认】
   2. 整个 spring-ioc 容器返回一个 bean 实例
2. 原型
   1. prototype
   2. 每次从容器调用 bean 都是新的
3. 请求
   1. request
   2. 返回每个 http 请求的一个 bean 实例
   3. 每个请求都不一样，如果在同一个请求里面多次获取同一个 bean，则为统一对象
   4. 仅适用于 WebApplicationContext？？
4. 会话
   1. session
   2. 返回每个 http 会话的一个 bean 实例
   3. http 为无状态协议，如果设置 session 和客户端 cookie 协作可以追踪状态，如果 session 没过期，则在此期间内多次获取会是同一个 bean
5. 全局会话
   1. globalSession
   2. 返回全局 http 会话的一个 bean 实例
   3. 同上

## 【DI】依赖注入和【IOC】控制反转

## 自动装配 beans

## JDBC 的支持

## AOP 面向切面编程
