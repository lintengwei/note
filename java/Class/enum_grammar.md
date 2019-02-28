# enum_grammar

> 当我们需要定义很多静态同类型的静态变量的时候，可以使用enum来替换实现
> 典型的就是时间，周一到周六之类的

## 静态变量定义

```java
//  静态变量定义
public final static int MON=0;
public final static int TUE=1;
public final static int WEN=2;
public final static int THU=3;
public final static int FRI=4;
public final static int SAT=5;
public final static int SUN=6;

```

## 枚举定义

```java
//  枚举定义
public enum Weekday{
  MON,TUE,WEN,THU,FRI,SAT,SUN
}
//  使用
Weekday mon=Weekday.MON;
//  方法
Weekday.values(); //Weekday[]数组实例
Weekday.valueOf(String);  //返回对应的名称
mon.name(); //获取名称  MON
mon.ordinal();  //获取在定义中的序号，从0开始，此处等于4

```

### 枚举扩展

#### 定义字段和方法

```java
public enum Weekday{

  //  定义必须放在第一行
  MON(0,"mon"),TUE(1,"mon"),WEN(2,"mon"),THU(3,"mon"),FRI(4,"mon"),SAT(5,"mon"),SUN(6,"mon"); //分好不可少

  // 定义字段 作用域和包一样
  private int index;
  private String name;

  private Weekday(int index,String name){
    this.index=index;
    this.name=name;
  }

  //  定义方法
  public int getIndex(){
    return index;
  }

  public void setIndex(int index){
    this.index=index;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name=name;
  }
}
```

#### 编译

经过编译，编译器给枚举类型新增了两个静态方法，一个是values(),一个是valueOf()。Enum本身也有一个valueOf()方法，但是该方法有两个参数，而我们继承的只有一个参数。并且由于values()是由编译器插入到我们的枚举中的，因此向上转型之后，我们将不能使用values()方法。

+ 编译器会把我们定义的枚举编译为final类，所谓我们不能使用继承，编译过程：

```java
enum Person{
  LTW,CSN;
}

// TO

final class Person extends Enum{

  public final static Person LTW;
  public final static Person CSN;
  public final static Person[] values();
  public static Person valueOf(java.util.String);
  static{

  }
}
```

#### 由于enum内部实际上是继承了Enum类，所以不能在继承其他类，使用接口

> 运用：随机选取工具类---取至 java编程思想

```java
public class Enums{

  //  定义一个随机取值的rand
  private static Random rand=new Random(50);

  public static <T extends Enum<T>> T random(Class<T> clazz){
    T[] ts=clazz.getEnumConstants();
    return random(ts);
  }

  private static <T> random(T[] ts){
    return ts[rand.nextInt(ts.length)];
  }
}
```

#### EnumSet 标志替换

> s书上说是可以快速的切换标志位

```java

//  比如现在我有一排灯8个 我需要记录哪些是凉的哪些是暗的，并且可以快速变换
//  可能的做法
//  1 表示亮 0表示暗,当需要切换的时候，只需要把对应的位置bit换位即可
//  语义不清，如果是放在一排还好，但是如果分布不均，就不好记录
long status=10110011;

//  假设现在有5盏灯，分别位于1，2，4，7，9楼，现在需要记录开关情况

//  之前的bit运算
long status=10011;  //分别代表不同层的状态  ，此时不好记录到底哪个表示那层
enum Light{
  FL1,FL2,FL4,FL7,FL9
}

EnumSet<Light> lights=EnumSet.noneOf(Light.class);  //empty set
lights.add(FL1);  //1亮
lights.remove(FL1); //1暗

//  只需要把bit为1的状态添加进来，

```