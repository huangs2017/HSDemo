package juc;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

import Util.Log;

// Phaser简单使用
public class Phaser_Test1 {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(2); // Phaser(2)代表注册的party数量, 不传入默认为0
        new Runner(phaser).start();
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        new Runner(phaser).start();
    }



    static class Runner extends Thread {

        private Phaser phaser;

        public Runner(Phaser phaser) {
            this.phaser = phaser;        // 多个线程必须持有同一个phaser
        }

        @Override
        public void run() {
            try {
                Log.i(this.getName() + " is ready1");      phaser.arriveAndAwaitAdvance();
                Log.i(this.getName() + " running...");     Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
                Log.i(this.getName() + " is ready2");      phaser.arriveAndAwaitAdvance();
                Log.i(this.getName() + " running...");     Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
                Log.i(this.getName() + " is ready3");      phaser.arriveAndAwaitAdvance();

                Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
                Log.i(this.getName() + " over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}