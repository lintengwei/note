# java 反射

[详解](https://blog.csdn.net/sinat_38259539/article/details/71799078)

[api 接口中文](https://www.jianshu.com/p/9be58ee20dee)

[docu](https://docs.oracle.com/javase/8/docs/api/index.html)

## 什么是反射?

反射就是把 java 类中的各种成分映射成一个个的 Java 对象。解剖对象，获取对象的【任意】属性和方法，包括私有的！

## 为什么需要反射?

## 反射可以做什么?

## 反射的优点和缺点有哪些?

有点：

1. 利用反射可以灵活的配置多变的类。
   2. 通过配置文件保存需要改变的类，以及需要调用的操作
   3. 而后通过反射动态的生成多变的对象，实现类的灵活配置

缺点：

1. 增加了虚拟机的内存消耗?

## 反射的实现

```java {.line-numbers}
//  构造函数
public Constructor getConstructor([...args]);   //  获取构造函数，参数为类对象  Teacher(String name);  Constructor c=clazz.getConstructor(String.class);
public Constructor getDeclareConstructor([...args]);    //  获取包括私有的构造函数
public Constructor[] getConstructors();   //  获取公有的构造函数
public Constructor[] getDeclareConstructor();   //  获取全部的构造函数
/*  Constructor的方法   */
public Object newInstance();  //  利用反射生成一个对象 ; Class clazz=Class.forName("Teacher"); Object=clazz.newInstance(null);  //  无参构造函数

//  字段
public Field getField(String name);   //  获取指定的字段
public Field getDeclareField(String name);  //  可以获取私有
public Field[] getFields();   //  获取所有的字段
public Field[] getDeclareFields();  //  获取所有字段
/*  Field 的方法*/
public Object get();
public int getInt();
public double getDouble();
public void set(Object instance[,...args]);   //  设置值

//  方法
public Method getMethod(String name);   //  获取指定的public方法
public Method getDeclareMethod(String name);    //  获取包括私有的指定方法
public Method[] getMethods();   //  获取所有公有的方法，包括继承的
public Method[] getDeclareMethods();    //  获取类自身定义的方法，包括私有的，不包括继承的
/* Method类的方法 */
public void invoke(Object arg1[,...args]);  //  执行该方法，第一个参数为实例，其他为方法的参数. 如果有方法 add(int a,int b);  Method method=clazz.getMethod('add',int.class,int.class);  如果不带参数，则会去找没有参数的add方法
public Parameter[] getParameter();  //  获取参数方法类型
public Class[] getParamterTypes();  //  获取参数方法类型

//  Method,Field,Constructor类的权限控制方法
public void setAccess(boolean accessAble);    //  是否允许访问私有方法，私有属性.只有打开该属性，才允许访问私有
```

```java
//  获取Class对象的三种方法
//  假设现在有类 Teacher
Class c1=Teacher.class;

Teacher t1=new Teacher();
Class c2=t1.getClass();

//  可能抛出找不到类的错误
try{
  Class c3=Class.forName("com.lt.Teacher");
}catch(ClassNotFoundException){

}

//  因为方法区中只有一个class对象，只是获取的途径不同，但是返回的都是同一个对象
System.print.out(c1==c2==c3);
```
