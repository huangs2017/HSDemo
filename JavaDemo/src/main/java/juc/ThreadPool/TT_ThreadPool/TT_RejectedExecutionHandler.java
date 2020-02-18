package juc.ThreadPool.TT_ThreadPool;

import Util.Log;

public class TT_RejectedExecutionHandler {

    public void rejected(Runnable task, TT_ThreadPoolExecutor executor){
        Log.i("任务已经满了");
        throw new RuntimeException("任务已经满了");
    }

}
