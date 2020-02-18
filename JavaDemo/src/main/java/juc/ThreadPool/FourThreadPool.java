package juc.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/*
    ThreadPoolExecutor extends AbstractExecutorService
    AbstractExecutorService implements ExecutorService
    ExecutorService extends Executor
*/

// 线程池分类：1.单例  2.定长  3.定时  4.缓存
public class FourThreadPool {

    public static void main(String[] args) {
        threadPool();
    }

    // 四类线程池底层都是这种
    public static void threadPool() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        pool.execute(new MyRunnable(1));

        pool.execute(new MyRunnable(2));
        pool.execute(new MyRunnable(3));
        pool.execute(new MyRunnable(4));

        pool.execute(new MyRunnable(5));

        pool.shutdown(); // 关闭线程池
    }


    // 单例
    public static void singleThreadPool() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i=1; i<=10; i++) {
            pool.execute(new MyRunnable(i));
        }
        pool.shutdown(); // 关闭线程池
    }

    // 定长
    public static void fixedThreadPool() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i=1; i<=10; i++) {
            pool.execute(new MyRunnable(i));
        }
        pool.shutdown(); // 关闭线程池
    }

    // 定时
    public static void scheduledThreadPool() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        for (int i=1; i<=10; i++) {
//            schedule(Runnable command, long delay, TimeUnit unit);
            pool.schedule(new MyRunnable(i), 3, TimeUnit.SECONDS);
        }
        pool.shutdown(); // 关闭线程池
    }

    // 缓存
    public static void cachedThreadPool() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i=1; i<=10; i++) {
            pool.execute(new MyRunnable(i));
        }
        pool.shutdown(); // 关闭线程池
    }


}
