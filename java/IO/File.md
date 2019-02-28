# File

```java
//  创建文件
File file=new File(pathname);
file.createNewFilw();

//  创建文件夹
File file=new File(directoryPath)
//  directoryPath 参数目录必须是存在的 不抛出错误
file.mkdir();
//  directoryPath任何层级的都可以生成，即使不存在
file.mkdirs();

/**
 * 创建临时文件，会社社
 * @param prefix 前缀
 * @param subbix 后缀
 * @param directory 指定存放临时文件的目录
 * */
File.createTempFile(String prefix,String suffix)
File.createTempFile(String prefix,String suffix,File directory)
```
