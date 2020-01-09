package JavaDemo.ThreadPool.TT_ThreadPool;

import android.util.Log;

public class TT_RejectedExecutionHandler {

    public void rejected(Runnable task, TT_ThreadPoolExecutor executor){
        Log.i("hunter", "任务已经满了");
        throw new RuntimeException("任务已经满了");
    }

}
