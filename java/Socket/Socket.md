# Socket 相关

## Socket 服务器

```java
// construtor
//  构建socket服务器
//  创建服务器之后就开始监听，需要调用accept方法获取连接的socket
/**
 * @param port 绑定的端口
 * @param backlog 队列
 * @param bindAddr 绑定地址
 * */
public ServerSocket();  //  需要后面通过bind方法绑定端口
public ServerSocket(int port);  //  默认是50个连接
public ServerSocket(int port,int backlog);
public ServerSocket(int port backlog,InetAddress bindAddr);

//  调用accept方法获取连接的socket，该方法会阻塞线程，只有当有连接的时候会执行
public Socket accept();
public void bind(SocketAddress endpoint);   //  给服务器绑定监听端口，如果之前绑定，会抛出错误
public void close();  //  关闭服务器
public InetAddress getInetAddress();  //  ip
public int getaLocalport(); //  获取监听的端口，如果没有绑定，返回-1
public SocketAddress getLocalSocketAddress();   //  ip:port
public int getReceiveBufferSize();
public boolean getReuseAddress();
public int getSoTimeout();  //  获取超时时长
protected void implAccept();
public boolean isBound(); //  获取服务器的绑定状态
public boolean isClose(); //  服务器是否关闭
public void setPerformancePrefreneces();
public void setReceiveBufferSize();
static void setSocketFactory(SocketImplFactory fac);
public void setSoTimeout(); //  设置超时时长
public String toString(); //  返回地址和端口
```
