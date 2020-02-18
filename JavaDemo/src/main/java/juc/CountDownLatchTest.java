package juc;

import java.util.concurrent.CountDownLatch;
import Util.Log;

public class CountDownLatchTest {

    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("t1 等待 countDown = 0");
                    countDownLatch.await();
                    Log.i("t1 stop");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();


        try {
            Thread.sleep(1000);
            countDownLatch.countDown();
            Log.i("countDown = 1");

            Thread.sleep(1000);
            countDownLatch.countDown();
            Log.i("countDown = 0");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
