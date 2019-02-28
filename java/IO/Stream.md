# io 的字节流

- InputStream
  - FileInputStream
  - FilterInputStream
    - 装饰者模式，以其他输入流作为参数，适配输入数据
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
    - 装饰者模式，以其他输入流作为参数，适配输出数据
    - BufferedOutputStream
    - DataOutputStream
    - PrintStream
  - ObjectOutputStream
  - PipedOutStream
  - ByteArrayOutputStream

## InputStream

```java
//  一次读取buf长度的字节填充buf，如果不满则0补位.如果buf长度小于可读的长度
//  循环读取的换会覆盖上一次的内容！
public int read(byte[] buf);
public int read(byte[],int offset,int length);
//  一次读取一个字节的字节，结束返回-1
public int read();
```

## OutputStream

```java
public void write(int b);   //  一次写入一个字节的数据
public void write(byte[] b);  //
public void write(byte[] b,int offset,int length);  //  把b的字节写入到内部缓冲区
```

> FileInputSteam/FileOutputStream

```java
//  constructor
//  把文件作为数据流读取数据
/**
 * @param file 文件
 * @param fdObj 文件描述符
 * @param pathName 文件所在路径
 * */
public FileInputStream(File file);
public FileInputStream(FileDescriptor fdObj);
public FileInputStream(String pathName);

//  constructor
//  把数据写入到输出流文件
//  如果文件不存在会创建文件，如果目录不存在会抛出 FileNotFoundException错误
/**
 * @param append 是否已追加的模式写入
 * */
public FileOutputStream(String pathName);
public FileOutputStream(String pathName,boolean append);
public FileOutputStream(File file);
public FileOutputStream(File file,boolean append);
public FileOutputStream(FileDescriptor fdObj);
```

> ByteArrayInputStream/ByteArrayOutputStream

```java
// constructor
// 这两个类有啥软用？？？？？
//  临时存放数据？
/**
 * @param b 把该字节数组拷贝到ByteArrayInputStream的内部缓冲区
 * @param offset 拷贝的偏移
 * @param length 拷贝的长度
 * */
public ByteArrayInputStream(byte[] b);
public ByteArrayInputStream(byte[] b,int offset,int length);

/**
 * @param size 初始化内部缓冲区的大小，随着写入内部缓冲区会自动增长
 * */
public ByteArrayOutputStream();
public ByteArrayOutputStream(int size);

public byte[] toByteArray();  //  读取输出流的可读数据
public String toString();   //  把内部缓冲区转换为字符串，使用的是默认的编码方式
```

> PipedInputStream/PipedOutputStream

管道流

```java
// constructor
//  输入和输出连接之后，写入输出流的数据都可以在写入流里面读取到
/**
 * 输入流构造函数
 * @param pipeSize 管道大小
 * @param pos 连接的输出流
 * */
public PipedInputStream();
public PipedInputStream(int pipeSize);
public PipedInputStream(PipedOutputStream pos);
public PipedInputStream(PipedOutputStream pos,int pipeSize);

//  连接输出流
public void connect(PipedOutputStream pos);

/**
 * 输出流构造函数
 * @param pis 连接的输入流
 * */
public PipedOutputStream();
public PipedOutputStream(PipedInputStream pis);

//  连接输入流
public void connect(PipedInputStream pis);

PipedInputStream pis=new PipedInputStream();
PipedOutputStream pos=new PipedOutputStream();
pis.connect(pos);
String name="ltw";
pos.write(pos.getBytes());
byte[] b=new byte[100];
pis.read(b);
System.out.println(new String(b,0,b.length));   //  ltw
```

> ObjectInputStream/ObjectOutputStream

```java
//  constructor
//  把java的数据镀锡i昂写入到流中，装饰者模式，需要和其他流配合使用
//  对象类型的数据结构需要实现Serializeble接口
//  读取顺序和写入顺序需要一致，否者会出现异常，或者和结果不一致
/**
 * */
protected ObjectInputStream();
public ObjectInputStream(InputStream in);

protected ObjectOutputStream();
public ObjectOutputStream(OutputStream out);
```

> SequenceInputStream

```java
//  constructor
//  输入流的逻辑串联
/**
 * @param s1 输入流1
 * @param s2 输入流2
 * @param e 输入流的集合
 * */
public SequenceInputStream(Enumeration<? extends InputStream> e);
public SequenceInputStream(InputStream s1,InputStream s2);

String name = "ltw";
String sex = "南门";
SequenceInputStream seq = new SequenceInputStream(new ByteArrayInputStream(name.getBytes()),
new ByteArrayInputStream(sex.getBytes()));
FileOutputStream out = new FileOutputStream("C://Users//wholler//Desktop//a.txt");
int b;
while ((b = seq.read()) > -1) {
	out.write(b);
}
out.close();
```
