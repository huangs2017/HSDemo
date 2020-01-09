package JavaDemo.ThreadPool;

import android.util.Log;

public class MyRunnable implements Runnable {

    int nov;

    public MyRunnable(int nov) {
        this.nov = nov;
    }

    @Override
    public void run() {
        Log.i("hunter", "执行当前任务的线程是: " + Thread.currentThread().getName());
        Log.i("hunter", "我是任务: " + nov);
    }

}
