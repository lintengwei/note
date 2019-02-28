# web.xml 的配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
          xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         id="WebApp_ID" version="3.0">

    <!-- icon元素指出IDE和GUI工具用来表示Web应用的一个和两个图像文件的位置。 -->
    <icon></icon>

    <!-- display-name元素提供GUI工具可能会用来标记这个特定的Web应用的一个名称。 -->
    <display-name></display-name>

    <!-- description元素给出与此有关的说明性文本。 -->
    <description></description>

    <!-- context-param元素声明应用范围内的初始化参数 -->
    <!-- 全局范围内的参数 作用于所有的servlet -->
    <context-param></context-param>

    <!-- filter 过滤器元素将一个名字与一个实现javax.servlet.Filter接口的类相关联。 -->
    <filter></filter>

    <!-- filter-mapping 一旦命名了一个过滤器，就要利用filter-mapping元素把它与一个或多个servlet或JSP页面相关联。 -->
    <filter-mapping></filter-mapping>

    <!-- listener 对事件监听程序的支持，事件监听程序在建立、修改和删除会话或servlet环境时得到通知。Listener元素指出事件监听程序类。 -->
    <listener></listener>

    <!-- servlet 在向servlet或JSP页面制定初始化参数或定制URL时，必须首先命名servlet或JSP页面。Servlet元素就是用来完成此项任务的。 -->
    <servlet></servlet>

    <!-- servlet-mapping 服务器一般为servlet提供一个缺省的URL：http://host/webAppPrefix/servlet/ServletName。但是，常常会更改这个URL，以便servlet可以访问初始化参数或更容易地处理相对URL。在更改缺省URL时，使用servlet-mapping元素。 -->
    <servlet-mapping></servlet-mapping>

    <!-- session-config 如果某个会话在一定时间内未被访问，服务器可以抛弃它以节省内存。可通过使用HttpSession的setMaxInactiveInterval方法明确设置单个会话对象的超时值，或者可利用session-config元素制定缺省超时值。 -->
    <session-config></session-config>

    <!-- mime-mapping 如果Web应用具有想到特殊的文件，希望能保证给他们分配特定的MIME类型，则mime-mapping元素提供这种保证。 -->
    <mime-mapping></mime-mapping>

    <!-- welcome-file-list元素指示服务器在收到引用一个目录名而不是文件名的URL时，使用哪个文件。 -->
    <welcome-file-list></welcome-file-list>

    <!-- error-page元素使得在返回特定HTTP状态代码时，或者特定类型的异常被抛出时，能够制定将要显示的页面。 -->
    <error-page></error-page>

    <!-- resource-env-ref元素声明与资源相关的一个管理对象。 -->
    <resource-env-ref></resource-env-ref>

    <!-- resource-ref元素声明一个资源工厂使用的外部资源。 -->
    <resource-ref></resource-ref>

    <!-- security-constraint元素制定应该保护的URL。它与login-config元素联合使用 -->
    <security-constraint></security-constraint>

    <!-- 用login-config元素来指定服务器应该怎样给试图访问受保护页面的用户授权。它与sercurity-constraint元素联合使用。 -->
    <login-config></login-config>

    <!-- security-role元素给出安全角色的一个列表，这些角色将出现在servlet元素内的security-role-ref元素的role-name子元素中。分别地声明角色可使高级IDE处理安全信息更为容易。 -->
    <security-role></security-role>

    <!-- env-entry元素声明Web应用的环境项。 -->
    <env-entry></env-entry>

    <!-- ejb-ref元素声明一个EJB的主目录的引用。 -->
    <ejb-ref></ejb-ref>

    <!-- ejb-local-ref元素声明一个EJB的本地主目录的应用。 -->
    <ejb-local-ref></ejb-local-ref>

</web-app>
```

## servlet

> 定义一个 servlet

```xml
<servlet>
    <!-- 唯一名称 -->
    <servlet-name>ServletName</servlet-name>
    <!-- 对应的类 -->
    <servlet-class>FullyQualifiedName</servlet-class>
    <!-- 初始化一些servlet的参数 -->
    <init-param>
        <param-name>param1</param-name>
        <param-value>value1</param-value>
    </init-param>
    <init-param>
        <param-name>param2</param-name>
        <param-value>2</param-value>
    </init-param>
</servlet>
```

> servlet-mapping

```xml
<servlet-mapping>
    <!-- 定义的servlet名称 -->
    <servlet-name>ServletName</servlet-name>
    <!-- 匹配的路由 -->
    <!-- 要是注解也定义了会优先使用注解 ?-->
    <url-pattern>URL</url-pattern>
</servlet-mapping>
```

## filter 过滤器

> filter

```xml
<filter>
    <!-- 过滤器的名称 -->
    <filter-name>Reporter</filter-name>
    <!-- 过滤器对应的类 -->
    <filter-class>moresevlets.ReportFilter</filter-class>
</filter>  
```

> filter-mapping

```xml
<filter-mapping>
    <!-- filter 定义的过滤器 -->
    <filter-name>Reporter</filter-name>
    <!-- 过滤的路由 -->
    <url-pattern>/*</url-pattern>
</filter-mapping>  
```
