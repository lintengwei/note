# jdbc 数据库的连接

> 步骤

1.  加载驱动类
2.  连接数据库

```java
//  加载驱动
Class.forName("Database Driver Class")
//  地址格式   JDBC URL: jdbc:netezza://<host>:<port>/<database_name>
String url="Database Url"
//  连接
Connection cn=DriverManager.getConnection(url,user,psw);
```

## JDBC 连接 DB2

Class.forName("Com.ibm.db2.jdbc.net.DB2Driver");  
String url="jdbc:db2://dburl:port/DBname"  
cn = DriverManager.getConnection( url, sUsr, sPwd );

## JDBC 连接 Microsoft SQLServer(microsoft)

Class.forName( "com.microsoft.jdbc.sqlserver.SQLServerDriver" );  
cn = DriverManager.getConnection( "jdbc:microsoft:sqlserver://localhost:1433;databaseName=master", sUsr, sPwd );

## JDBC 连接 Sybase(jconn2.jar)

Class.forName( "com.sybase.jdbc2.jdbc.SybDriver" );  
cn = DriverManager.getConnection( "jdbc:sybase:Tds:localhost:2638", sUsr, sPwd );

## JDBC 连接 MySQL(mm.mysql-3.0.2-bin.jar)

Class.forName( "org.gjt.mm.mysql.Driver" );  
cn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/myDatabaseName", sUsr, sPwd );

## JDBC 连接 PostgreSQL(pgjdbc2.jar)

Class.forName( "org.postgresql.Driver" );  
cn = DriverManager.getConnection( "jdbc:postgresql://localhost/myDatabaseName", sUsr, sPwd );

## JDBC 连接 Oracle(classes12.jar)

Class.forName( "oracle.jdbc.driver.OracleDriver" );  
cn = DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:ORCL", sUsr, sPwd );

## JDBC 连接 ODBC

Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );  
Connection cn = DriverManager.getConnection( "jdbc:odbc:" + sDsn, sUsr, sPwd );
