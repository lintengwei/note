# 阶段任务

[摘自](https://blog.csdn.net/u010739551/article/details/51083004)
java 多线程技术提供了 Phaser 工具类，Phaser 表示“阶段器”，用来解决控制多个线程分阶段共同完成任务的情景问题。其作用相比 CountDownLatch 和 CyclicBarrier 更加灵活，例如有这样的一个题目：5 个学生一起参加考试，一共有三道题，要求所有学生到齐才能开始考试，全部同学都做完第一题，学生才能继续做第二题，全部学生做完了第二题，才能做第三题，所有学生都做完的第三题，考试才结束。分析这个题目：这是一个多线程（5 个学生）分阶段问题（考试考试、第一题做完、第二题做完、第三题做完），所以很适合用 Phaser 解决这个问题。

```java
/**
 * 覆盖这个方法，表示各个阶段需要做的事情
 * @param phase 阶段，从0开始计数
 * @registeredParties
 * @return 返回值 true表示完结，false表示未完结
 * */
boolean onAdvance(int phase, int registeredParties);

/**
 * 注册
 * 每注册一次表示成员数加1
 * 注册完成之后，每次调用	arriveAndAwaitAdvance()等相关方法，表示某个成员到达该阶段
 * */
register();

/**
 * 到达某阶段，一般实在工作线程调用，标识该线程完成到了某阶段
 * */
arrive();   //  每调用一次表示有成员到达该阶段，不等待其他人到达
arriveAndAwaitAdvance();  //  成员到达，等待其他人到达
arriveAndDeregister();    //  成员到达，并且注销，注销之后不会在参与到计数中，如果再次调用 arrive()等方法，会报错
```

```java
//  test
public class Test
{

   public static void main(String[] args) throws InterruptedException
   {
      Phaser phaser = new Work();
      Worker[] workers = new Worker[4];
      for (int i = 0; i < workers.length; i++)
      {
         workers[i] = new Worker(phaser , "worker" + i , i);
         phaser.register();
      }
      for (int i = 0; i < workers.length; i++)
      {
         workers[i].start();
      }
   }
}

class Worker extends Thread
{

   private Phaser phaser;

   private int num;

   public Worker(Phaser phaser, String name, int num)
   {
      this.phaser = phaser;
      setName(name);
      this.num = num;
   }

   @Override
   public void run()
   {
      try
      {
         work();
         phaser.arriveAndAwaitAdvance();
         work2();
         phaser.arriveAndAwaitAdvance();
         work3();
         phaser.arriveAndAwaitAdvance();
      }
      catch (InterruptedException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private void work() throws InterruptedException
   {
      doWork();
      System.err.println(Thread.currentThread().getName() + "::完成工作1");
   }

   private void work2() throws InterruptedException
   {
      doWork();
      System.err.println(Thread.currentThread().getName() + "::完成工作2");
   }

   private void work3() throws InterruptedException
   {
      doWork();
      System.err.println(Thread.currentThread().getName() + "::完成工作3");
   }

   private void doWork()
   {
      int sum = 0;
      for (int i = 0; i < 100000; i++)
      {
         sum += i;
      }
   }
}

class Work extends Phaser
{

   @Override
   protected boolean onAdvance(int phase, int registeredParties)
   {
      System.err.println("当前完成到任务:::" + (phase + 1));
      switch (phase)
      {
         case 0:
            System.err.println("完成任务1");
            return false;
         case 1:
            System.err.println("完成任务2");
            return false;
         case 2:
            System.err.println("完成任务3");
            return false;
         default:
            break;
      }
      return true;
   }
}
```
