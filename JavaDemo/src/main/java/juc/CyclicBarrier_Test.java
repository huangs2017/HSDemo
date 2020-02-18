package juc;

import java.util.concurrent.CyclicBarrier;

import Util.Log;

public class CyclicBarrier_Test {

    public static void main(String[] args) throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(3);

        new Thread(new Worker(barrier, "worker1")).start();
        new Thread(new Worker(barrier, "worker2")).start();

        Log.i("................");
        barrier.await();
        Log.i("所有的线程都工作完毕了, main线程继续执行!");
    }



    static class Worker implements Runnable {
        private CyclicBarrier barrier; // 允许一组线程互相等待，直到到达某个公共屏障点
        private String name;

        public Worker(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Log.i(name + "运行完毕!");
                barrier.await(); // await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}



