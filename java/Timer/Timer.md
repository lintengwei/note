# Timer 的使用

1.  每个 Timer 实例包含一个任务队列 TaskQueue<TimerTask>=[]
2.  每个 Timer 实例都有一个内置线程 TimerThread
3.  新建 Timer 对象，运行 TimerThread 的线程，并且等待任务队列的更新
4.  当任务的队列有更新时唤醒 TimerThread 线程
5.  每次队列更新，会把最快执行的任务排在队列的首位
6.  每次任务执行之后，都会进行重排序，把最快执行的任务排在首位

```java
// 4个构造方法
/**
 * 构建任务队列
 * @param isDaemon
 * */
public Timer()
public Timer(boolean isDaemon)
public Timer(String name)
public Timer(String name,boolean isDaemon)

/**
 * 添加TimerTask到TastkQueue
 * */
void	schedule(TimerTask task, Date time)
void	schedule(TimerTask task, Date firstTime, long period)
void	schedule(TimerTask task, long delay)
void	schedule(TimerTask task, long delay, long period)
void	scheduleAtFixedRate(TimerTask task, Date firstTime, long period)
void	scheduleAtFixedRate(TimerTask task, long delay, long period)
```

## TimerTask

```java
//  内置4个状态
// 生成实例 还没有被加进Timer的任务队列
static final int VIRGIN=0;
//  已经加进了任务队列 还没有执行
static final int SCHEDULED=1;
//  已经非循环任务-已经执行
static final int VIRGIN=2;
// 被取消
static final int VIRGIN=3;

//  实例变量
// 当前状态 初始状态为任务生成 没有被加进任务队列
int state=VIRGIN;
//下次任务的执行时间
long nextExecutionTime;
//任务的循环时间
long period;
```

## TaskQueue 任务队列

```java

/**
 * TimerThread的主循环
 * */
private void mainLoop() {
  while (true) {
        try {
            TimerTask task;
            boolean taskFired;
            synchronized(queue) {
                // Wait for queue to become non-empty
                while (queue.isEmpty() && newTasksMayBeScheduled)
                    queue.wait();
                if (queue.isEmpty())
                    break; // Queue is empty and will forever remain; die

                // Queue nonempty; look at first evt and do the right thing
                long currentTime, executionTime;
                task = queue.getMin();
                synchronized(task.lock) {
                    if (task.state == TimerTask.CANCELLED) {
                        queue.removeMin();
                        continue;  // No action required, poll queue again
                    }
                    currentTime = System.currentTimeMillis();
                    executionTime = task.nextExecutionTime;
                    if (taskFired = (executionTime<=currentTime)) {
                        if (task.period == 0) { // Non-repeating, remove
                            queue.removeMin();
                            task.state = TimerTask.EXECUTED;
                        } else { // Repeating task, reschedule
                            queue.rescheduleMin(
                              task.period<0 ? currentTime   - task.period
                                            : executionTime + task.period);
                        }
                    }
                }
                if (!taskFired) // Task hasn't yet fired; wait
                    queue.wait(executionTime - currentTime);
            }
            if (taskFired)  // Task fired; run it, holding no locks
                task.run();
        } catch(InterruptedException e) {
      }
    }
}

/**
 * 添加任务会执行这个方法
 * */
private void sched(TimerTask task, long time, long period) {
  if (time < 0)
  throw new IllegalArgumentException("Illegal execution time.");

  // Constrain value of period sufficiently to prevent numeric
  // overflow while still being effectively infinitely large.
  if (Math.abs(period) > (Long.MAX_VALUE >> 1))
    period >>= 1;

  synchronized(queue) {
    if (!thread.newTasksMayBeScheduled)
        throw new IllegalStateException("Timer already cancelled.");

    synchronized(task.lock) {
        if (task.state != TimerTask.VIRGIN)
            throw new IllegalStateException(
                "Task already scheduled or cancelled");
        task.nextExecutionTime = time;
        task.period = period;
        task.state = TimerTask.SCHEDULED;
    }

    queue.add(task);
    if (queue.getMin() == task)
        queue.notify();
  }
}

void add(TimerTask task) {
// Grow backing store if necessary
  if (size + 1 == queue.length)
    queue = Arrays.copyOf(queue, 2*queue.length);

  queue[++size] = task; //  从位置1开始存储
  fixUp(size);
}

/**
 * 每次新增任务都会进行比较，把最快执行的任务放在队列首位
 * 1<2<4<6
 * 1<3<5<7
 * */
private void fixUp(int k) {
  while (k > 1) {
    int j = k >> 1;
    if (queue[j].nextExecutionTime <= queue[k].nextExecutionTime)
        break;
    TimerTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
    k = j;
  }
}

/**
 * 重排序
 * 每次执行完一个任务都会对task进行重排序
 * 如果2>3并且1<2,1<3
 * 则新的顺序为
 * 3<5<7<1
 * 3<2<4<6
 * 3最为第一顺位执行
 * 或者3>2并且1<2,1<3
 * 2<4<6<1
 * 2<3<5<7
 * 不用常规排序，可以加快算法
 * */
private void fixDown(int k) {
  int j;
  while ((j = k << 1) <= size && j > 0) {
    if (j < size &&
        queue[j].nextExecutionTime > queue[j+1].nextExecutionTime)
        j++; // j indexes smallest kid
    if (queue[k].nextExecutionTime <= queue[j].nextExecutionTime)
        break;
    TimerTask tmp = queue[j];  queue[j] = queue[k]; queue[k] = tmp;
    k = j;
  }
}
```
