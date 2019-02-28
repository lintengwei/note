# Thread线程相关

## 创建线程

+ 继承Thread 
+ 实现Runnable接口
+ 继承AsyncTask
  + 使用了线程池
  + 封装了Thread和Handler
  + 不适合特别耗时的任务
+ 继承IntentService

### AsyncTask

```java
//  声明
/**
 *都是泛型参数
 *@param params 给asyncTask的参数 
 *@param progress 返回给ui线程的进度
 *@param result  返回结果
 **/
public abstract class AsyncTask<Params, Progress, Result> 

//  核心方法

public class TestAsyncTask extends AsyncTask<Void,Integer,Integer> {

    private int len=10;


    public TestAsyncTask() {
        super();
    }

    /**
     * 必须实现的方法，在线程池中执行，执行异步任务，可以传入参数，通过publishProgress
     * 方法来传递任务进度，publishProgress通过调用onProgressUpdate方法使用
     * 此方法还需要返回计算结果给onPostExcute
     * @param voids 运行所需要的参数
     * @return  结果
     */
    @Override
    protected Integer doInBackground(Void... voids) {
        int total=0;
       for (int i=0;i<len;i++){
           total+=i;
           if (i==7){
               cancel(true);
           }
           publishProgress(i);
       }
       return total;
    }

    /**
     * 该方法在主线程中执行，在一部任务开始前调用，做准备工作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 在主线程中执行，在异步任务完成之后会被调用
     * 如何判断任务的结束与否
     * @param integer   doInBackground返回的值
     */
    @Override
    protected void onPostExecute(Integer integer) {
        Console.log(integer);
    }


    /**
     * 在主线程中执行，用户更新任务进度
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        Console.log(values[0]);
    }


    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        Console.log("cancel");
    }
}

> 使用过程

+ 主线程调用excute()方法，传进参数，开始执行，在doInBackground方法里面如果调用publishProgress()，猜测会放进队列，等待主线程的时间片，如果轮到主线程执行，则会调用onProgressUpdate来更新UI，当doInBackground方法执行完之后，返回一个值给postExecute()方法，该方法运行在主线程来更新ui，至此async方法结束。如果在async方法尚未结束之前，主动调用cancel()方法，则postExecute()方法不会执行，会调用onCancel()，asycnTask终止