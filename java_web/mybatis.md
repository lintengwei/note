# mybatis 使用

[api 文档](http://www.mybatis.org/mybatis-3/zh/index.html)

## 基本使用

```xml
<!-- mytabis-config.xml -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeAliases>
		<package name="com.wholler.dk.model" />
	</typeAliases>
  <properties resource="com/wholle/tk/config/mybatis/db-config.properties">
  </properties>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${driver}" />
				<property name="url" value="${url}" />
				<property name="username" value="{username}" />
				<property name="password" value="{password}" />
			</dataSource>
		</environment>
	</environments>
	<mappers>
		<mapper resource="org/mybatis/map/UserMapper.xml" />
	</mappers>
</configuration>

<!-- map org/mybatis/map/UserMapper.xml -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.map.UserMapper">
  <select id="selectUser" resultType="User">
    select * from user where id = #{id}
  </select>
</mapper>
```

```properties
# oracle
driver=oracle.jdbc.driver.OracleDriver
url=jdbc:oracle:thin:@192.168.1.99:1521:ORCL
username=ltw
password=123456
```

```java
//usage
String resource="com/wholle/tk/config/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
SqlSession sqlSession = sqlSessionFactory.openSession();
UserPO user=(UserPO) sqlSession.selectOne("org.mybatis.map.UserMapper",26);
```

## mybatis-config 配置文件

> 配置项

- configuration 配置
  - properties 属性
  - settings 设置
  - typeAliases 类型别名
  - typeHandlers 类型处理器
  - objectFactory 对象工厂
  - plugins 插件
  - environments 环境
  - environment 环境变量
  - transactionManager 事务管理器
  - dataSource 数据源
  - databaseIdProvider 数据库厂商标识
  - mappers 映射器

## mappers 配置文件详解

> 配置项

- mappers
  - namespace
    - 这个 mapper 的命名空间，全局 mappers 唯一
  - select
  - insert
  - update
  - delete
  - sql
    - 可重用语句
  - resultMap
    - 高级元素，描述结果集和对象的映射关系。很叼，不好形容
  - cache
    - 该命名空间的缓存配置
  - cache-ref
    - 其他命名空间的缓存配置
