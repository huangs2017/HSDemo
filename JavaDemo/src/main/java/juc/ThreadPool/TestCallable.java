package juc.ThreadPool;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


/*
    class FutureTask<V> implements RunnableFuture<V>
    interface RunnableFuture<V> extends Runnable, Future<V>
*/
public class TestCallable {

    public static void main(String[] args) throws Exception {
        method1();
    }


    // callable线程实现方式1
    public static void method1() throws Exception {
        MyCallable callable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        String s = futureTask.get(); // 阻塞
    }

    // callable线程实现方式2
    public static void method2() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        ArrayList<Future> list = new ArrayList();
        for (int i=1; i<=100; i++) {
//          pool.execute(new MyRunnable()); // 真正开始执行
            Future<String> future = pool.submit(new MyCallable());
            list.add(future);
        }
        //  三个三个一起取
        for (Future future : list) {
            String s = (String) future.get(); // 真正开始执行
        }
        pool.shutdown(); // 关闭线程池
    }

}
