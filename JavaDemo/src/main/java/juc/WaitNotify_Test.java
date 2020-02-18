package juc;

import java.util.ArrayList;
import java.util.List;
import Util.Log;

/**
    基于wait/notify的线程通信
    wait/notify必须与synchronized一同使用
    wait释放锁、notify不释放锁
    notify只会通知一个wait中的线程，并把锁给他
 */
public class WaitNotify_Test {

    public static void main(String[] args) {

        final WaitNotify_Test demo = new WaitNotify_Test();

        new Thread(() -> demo.get(), "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> demo.put(), "t2").start();

    }


    //原子类
    private volatile List<String> list = new ArrayList<>();
    private Object lock = new Object();

    public void put() {
        synchronized (lock) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("A");
                Log.i("线程" + Thread.currentThread().getName() + "添加第" + i + "个元素");
                if (list.size() == 5) {
                    //数据准备好了，发出唤醒通知，但是不释放锁
                    lock.notify();
                    Log.i("发出通知...");
                }
            }
        }
    }

    public void get() {
        synchronized (lock) {
            try {
                Log.i("线程" + Thread.currentThread().getName() + "业务处理，发现有需要的数据没准备好，则发起等待");
                lock.wait(); //wait操作释放锁,否则其他线程无法进入put方法
                Log.i("线程" + Thread.currentThread().getName() + "被唤醒");
                for (String s : list) {
                    Log.i("线程" + Thread.currentThread().getName() + "获取元素:" + s);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
