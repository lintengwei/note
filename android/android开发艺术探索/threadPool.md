# threadPool线程池

> 频繁的创建好销毁线程是很消耗性能的，因此就出现了线程池的概念，java实现线程池的方式，主要是通过ThreadPoolExcutor类来实现的。

## ThreadPoolExcutor

### 构造方法
```java

//  其中四个构造方法，前三个内部实现都会调用第四个构造函数
public class ThreadPoolExecutor extends AbstractExecutorService {
    .....
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
        BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler);
    ...
}
```

### 参数详解

+ corePoolSize
  + 核心线程数。在创建线程池时，默认线程池是空的，没有任何的线程，当通过调用threadPoolExcutor.excute(Runnable)方法把任务加进去，如果核心线程数小于设定的值，就会开启一个新线程来执行任务，如果核心线程数已经满了，就会把任务加进任务队列，等待执行，如果队列满了，就会创建一个非核心线程来执行任务，非核心线程的存活跟其他参数相关，如果非核心线程也满了，就会阻塞。
  + prestartAllCoreThreads()或者prestartCoreThread()方法可以在初始化的时候直接创建线程
+ maximumPoolSize
  + 线程池的最大线程数，当活动的线程达到这个数值时候，后续的任务会阻塞，包括核心线程和非核心线程
+ keepAliveTime
  + 非核心线程闲置时的超时时间，超过这个时常，非核心线程会被系统回收
  + 如果执行threadPoolExcutor.allowCoreThreadTimeout(true)，表示这个时常对核心线程也是有效的，即全部都是非核心线程？
+ unit 
  + 在keepAliveTime中设置的单位
```java{.line-numbers}
TimeUnit.DAYS;               //天
TimeUnit.HOURS;             //小时
TimeUnit.MINUTES;           //分钟
TimeUnit.SECONDS;           //秒
TimeUnit.MILLISECONDS;      //毫秒
TimeUnit.MICROSECONDS;      //微妙
TimeUnit.NANOSECONDS;       //纳秒
```
+ workQueue
  + 线程中的任务队列，通过excute提交的任务都会存储在这边，如果没有满的话。
+ threadFactory
  + 线程工厂，为线程池创建新线程的功能。
+ handler
  + 不常用。当线程无法执行任务时候会被调用，可能是因为任务队列已满或者是无法成功完成任务，会调用handler的rejectedExcution方法来发布通知，默认抛出一个rejectedException，可选值：
    + CallerRunsPolicy
    + AboutPolicy-默认值
    + DiscardPolicy
    + DiscardOldestPolicy

### 常用规则

+ 如果线程池的核心线程未满，直接新建一个线程来执行任务
+ 如果线程池的核心线程已经满了，任务会被插入到任务队列里面
+ 如果无法插入到任务队列里面，表示任务队列已经满了，则会启动一个新的非核心线程来执行任务
+ 如果非核心线程也满了，则线程池会拒绝任务，threadPoolExcutor会调用rejectedExcutionHandler的rejectedExcution通知调用者。
+ 如果有空闲线程，队列任务开始执行，不管是核心线程还是没有被回收的非核心线程，都可以执行任务，非核心线程只有在空闲时间达到设定的值才会被回收，空闲即没有任务在运行，阻塞状态不算空闲，不会被回收

## 系统中已经存在的线程池

> FixedThreadPool , CacheThreadPool, ScheduledThreadPool, SingleThreadPool. 其中都是通过Excutors来新建一个线程池。

### FixedThreadPool

> 线程数量固定的线程池，当线程处于空闲状态时候，线程不会被回收。根据构造函数，可以知道，FixedThreadPool类所有的线程都是核心线程，就是不会被系统回收，并且没有时间限制，任务队列也没有容量限制

```java
//  只有一个参数，核心线程数
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    }

//  线程数和线程创建工厂
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),threadFactory);
    }
```