package JavaDemo.ThreadPool.TT_ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import JavaDemo.ThreadPool.MyRunnable;

public class TestThreadPool {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(5);
        TT_RejectedExecutionHandler rejectedExecutionHandler = new TT_RejectedExecutionHandler();
        TT_ThreadPoolExecutor pool = new TT_ThreadPoolExecutor(2, 0, workQueue, rejectedExecutionHandler);

        pool.execute(new MyRunnable(1));
        pool.execute(new MyRunnable(2));
        pool.execute(new MyRunnable(3));
        pool.execute(new MyRunnable(4));
        pool.execute(new MyRunnable(5));
        pool.shutdown(); // 关闭线程池
    }

}
