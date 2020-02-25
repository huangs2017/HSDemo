package 笔试题;

import Util.Log;

// 死锁
public class DeadLock {

    public static void main(String[] args) {

        final DeadLock demo = new DeadLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.execute1();
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.execute2();
            }
        }, "t2").start();

    }



    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void execute1() {
        synchronized (lock1) {
            Log.i("线程" + Thread.currentThread().getName() + "获得lock1执行execute1开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                Log.i("线程" + Thread.currentThread().getName() + "获得lock2执行execute1开始");
            }
        }
    }

    public void execute2() {
        synchronized (lock2) {
            Log.i("线程" + Thread.currentThread().getName() + "获得lock2执行execute2开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                Log.i("线程" + Thread.currentThread().getName() + "获得lock1执行execute2开始");
            }
        }
    }


}
