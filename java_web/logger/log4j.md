# log4j 基本用法

## 注意

1.  如果没有设置配置文件，会使用默认的配置文件，具体查看官网
2.

## maven 导入包

```xml
<!-- 使用aliyun镜像 -->
<repositories>
  <repository>
      <id>aliyun</id>
      <name>aliyun</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
  </repository>
</repositories>

<dependencies>
    <dependency>  
        <groupId>org.apache.logging.log4j</groupId>  
        <artifactId>log4j-api</artifactId>  
        <version>${log4j.version}</version>  
    </dependency>  
    <dependency>  
        <groupId>org.apache.logging.log4j</groupId>  
        <artifactId>log4j-core</artifactId>  
        <version>${log4j.version}</version>  
    </dependency>  
</dependencies>
```
