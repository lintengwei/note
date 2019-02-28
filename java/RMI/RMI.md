# RMI

RMI(remote method onvocation，远程方法调用)。

简单实用：

```java
//  rmi服务器
//  需要开启rmiregister程序，在/bin目录下，默认监听的端口是1099，可以修改

//  实现操作类接口 服务器和客户端都需要使用，并且需要实现【serializable】接口
class User implements Serializable{

  public final static long serialVersionUID=1L;
  private String name;
  private int age;

  public User(String name,int age){
    this.name=name;
    this.age=age;
  }

  //  ... getters setters
}

//  操作类需要实现remote接口，服务器和客户端都需要使用, 并且每个方法都需要抛出remoteException
public interface Opreate{
  public String getName() throws RemoteException;
  public User getUser() throws RemoteException;
}

//  实现类
public class OperateImpl extends UnicastRemoteObject implements Operate{
  public final static long serialVersionUID=1L;
  //  ...
}

//  服务器
public class App{

  public static void main(String[] args) throws RemoteException{

    Operate operate=new OpreateImpl();
    Naming.rebind("rmi://localhost:1099/getuser",operate)
    System.out.println("rmi server is listening...");
  }
}

//  客户端

import com.wholler.lk.User;
import com.wholler.lk.Operate;

public class Client{

  public static void main(String[] args){

    try{
      Operate operate=(Operate) Naming.look("rmi://localhost:1099/getuser");
    }catch(NotBoundException e){

    }catch(MalformedURLException e){

    }catch(RemoteException e){

    }
  }
}
```
