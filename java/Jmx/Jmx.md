# Jmx

## note

1. 定义一个接口，set 和 get 方法不会公布
2. 接口名称必须以<?>MBean 结尾，例如 UserMBean，则实现类 User
3. 实现类必须是<?>，否则不会通过编译
4. 命名:[?]:[name=?]

```java
public interface UserMBean{
  public void setName(String name);
  public String getName();
}

public class User implements UserMBean{
  private String name;
  public User(String name){
    this.name=name;
  }
  public void setName(String name){
    this.name=name;
  }
  public String getName(){
    return this.name;
  }
}

public Test{
  public static void main(String[] args){
    MBeanServer mbs=ManagementFactory.getPaltformMBeanServer();
    User user=new User("ltw");
    ObjectName oname=new Object(user.getName+":name=ltw");
    user.register(user,oname);
  }
}
```
