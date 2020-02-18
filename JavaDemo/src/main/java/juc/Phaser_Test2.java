package juc;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

import Util.Log;

// Phaser中onAdvance方法的使用: 到达每个阶段执行
public class Phaser_Test2 {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new MyPhaser(2, 3);
        Log.i("需要参与者数量：" + phaser.getRegisteredParties());
        new Runner(phaser).start();
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        new Runner(phaser).start();
    }



    static class MyPhaser extends Phaser {

        private int totalPhaseNum;        //总计阶段数量

        public MyPhaser(int parties, int totalPhaseNum) {
            super(parties);
            this.totalPhaseNum = totalPhaseNum;
        }

//       到达每个阶段执行
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            System.out.println("phase " + phase + " is over, registeredParties is " + registeredParties);
            //如果已经到达了最后一个阶段，或者参与者为0，则结束
            return (totalPhaseNum - 1) == phase || registeredParties == 0;
        }
    }



    static class Runner extends Thread {

        private Phaser phaser;

        public Runner(Phaser phaser) {
            this.phaser = phaser;
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
