# java IO

> 结构

- IO 流
  - 字符流
    - Reader
      - BufferedReader
      - InputStreamReader
        - FileReader
      - StringReader
      - PipedReader
      - ByteArrayReader
      - FilterReader
        - PushbackReader
    - Writer
      - BufferedWriter
      - OutputStreamWriter
        - FileWriter
      - StringWriter
      - PipedWriter
      - CharArrayWriter
      - FilterWriter
  - 字节流
    - InputStream
      - FileInputStream
      - FilterInputStream
        - BufferedInputStream
        - DataInputStream
        - PushbackInputStream
      - ObjectInputStream
      - PipedInputStream
      - SequenceInputStream
      - ByteArrayInputStream
    - OutputStream
      - FileOutputStream
      - FilterOutputStream
        - BufferedOutputStream
          - DataOutputStream
          - PrintStream
      - ObjectOutputStream
      - PipedOutStream
      - ByteArrayOutputStream

## FileReader

使用 read()方法可以读取字符，一次只能读取一个字符，返回的是一个对应编码的十进制（Editor 设置编码方式）。如何动态的设置编码方式？

```java
//  读取字符，该无参数方法返回读取的字符的对应的十进制的值，需要强制转码(char) int;
int read();
//  获取文件的编码方式
String getEncoding();
```

## FileWriter

创建字符输出流类对象和已存在的文件相关联。文件不存在的话，并创建。

```java
//  写入字符串。当执行完此方法后，字符数据还并没有写入到目的文件中去。此时字符数据会保存在缓冲区中。
void write(String str)
//刷新该流中的缓冲。将缓冲区中的字符数据保存到目的文件中去。
void flush()
//关闭此流。在关闭前会先刷新此流的缓冲区。在关闭后，再写入或者刷新的话，会抛IOException异常。
void close()
```
