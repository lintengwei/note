# java 常用工具类

> maven 仓库地址 http://mvnrepository.com/

## Apache Commons Lang

[Apache Commons Lang](http://mvnrepository.com/artifact/org.apache.commons/commons-lang3)
[api document](http://commons.apache.org/proper/commons-lang/javadocs/api-3.7/index.html)

```xml
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.7</version>
</dependency>
```

1.  ArrayUtils – 用于对数组的操作，如添加、查找、删除、子数组、倒序、元素类型转换等；

2.  BitField – 用于操作位元，提供了一些方便而安全的方法；

3.  BooleanUtils – 用于操作和转换 boolean 或者 Boolean 及相应的数组；

4.  CharEncoding – 包含了 Java 环境支持的字符编码，提供是否支持某种编码的判断；

5.  CharRange – 用于设定字符范围并做相应检查；

6.  CharSet – 用于设定一组字符作为范围并做相应检查；

7.  CharSetUtils – 用于操作 CharSet；

8.  CharUtils – 用于操作 char 值和 Character 对象；

9.  ClassUtils – 用于对 Java 类的操作，不使用反射；

10. ObjectUtils – 用于操作 Java 对象，提供 null 安全的访问和其他一些功能；

11. RandomStringUtils – 用于生成随机的字符串；

12. SerializationUtils – 用于处理对象序列化，提供比一般 Java 序列化更高级的处理能力；

13. StringEscapeUtils – 用于正确处理转义字符，产生正确的 Java、JavaScript、HTML、XML 和 SQL 代码；

14. StringUtils – 处理 String 的核心类，提供了相当多的功能；

15. SystemUtils – 在 java.lang.System 基础上提供更方便的访问，如用户路径、Java 版本、时区、操作系统等判断；

16. Validate – 提供验证的操作，有点类似 assert 断言；

17. WordUtils – 用于处理单词大小写、换行等。

## 加密解密

### MD5

### BASE64

### ASE

## token 生成-客户端令牌

## xml 解析

[一些分析](https://www.cnblogs.com/java-class/p/6901910.html)
Jdom/Dom4j/Xstream... 基于底层解析方式重新组织封装的开源类库，简洁明了的 API，稳定高效的运行表现。
Dom4j 基于 JAXP 解析方式，性能优异、功能强大、极易使用的优秀框架。想了解底层解析方式请翻看：浅谈 Java XML 底层解析方式
Jdom 你细看内部代码，本质也是基于 JAXP 但包结构被重新组织， API 大量使用了 Collections 类，在性能上被 dm4j 压了好几个档次。
Xstream 基于 xmlpull 的 OXMapping 技术，更加倾向于将 XML 解析后映射为 Java 世界中的对象，等会在代码中会看的很清楚。
