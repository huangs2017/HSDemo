package hs.activity;

import android.app.IntentService;
import android.content.Intent;
import util.Log;

public class IntentService_Test extends IntentService {


    public IntentService_Test() {
        super("IntentService123"); // 线程名
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("Thread--> " + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("startId-------->" + startId);
        Log.i("Thread--> " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }
}

/*
    IntentService的优点
        第一，省去了手动开线程的麻烦；
        第二，不用手动停止Service；


    startId：代表启动服务的次数，从1开始。

    Context.stopService和Service.stopSelf()     会立即停止service。
    Service.stopSelf(int startId)               startId跟最后启动该service时生成的startId相等时才会停止service。

    IntentService中维护着一个消息队列，要想结束IntentService必须等到消息队列没有消息了。
*/
