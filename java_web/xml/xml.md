# xml 文档的解析

[详细解释](https://blog.csdn.net/qq_38724991/article/details/76131614)

## xmlns【xml 的命名空间】

> xmlns:namespace-prefix="namespaceURI"

- xmlns
  - 表示

由于 xml 可以自定义，任何人和机构都可以自己定义 xml 文件的标签，因此难免会和其他人的标签重复，xmlns 的作用就是我们给 xml 标签的一个声明，表示这个标签是干嘛用的。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- xmlns  默认的命名空间 如果使用此xsd定义的标签可以不加前缀 -->
<!-- xmlns:xsi  固定语法 -->
<!-- xsi:schemaLocation 固定语法，表示标签解析的位置，两两对应【namespace】 【xsd uri】 -->
<project
  xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>om.wholler</groupId>
	<artifactId>springMVC</artifactId>
	<packaging>war</packaging>
	<name />
	<version>0.0.1-SNAPSHOT</version>
	<description />
</project>
```

> xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

标准语法

> xsi:schemaLocation=" namespace1 xsd1 namespace2 xsd2 [...namespace xsd]"

声明命名空间标签的声明文档，便于解析器把对应的标签成功解析为我们希望的样子。能否使用本地文档
