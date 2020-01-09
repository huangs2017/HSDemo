package hs.activity.Broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.os.MessageQueue;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import hs.activity.R;

public class BroadcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


    public void bind (View view) {
        Intent intent = new Intent("com.hs.sendRice");
//      Android9.0发送自定义静态广播必须是定向广播,否则收不到:
        intent.setPackage("food.activity");
//      intent.putExtra();
        // 发送有序广播
        sendOrderedBroadcast(intent, null, new FinalReceiver(), null, 1, "习大大给每个村民发了1000斤大米", null);

    /*
        1.有序广播
            类似中央发送文件，按照一定的优先级进行接收
           （1）可以被终止
           （2）数据可以被修改

        2.无序广播（简单）
            类似新闻联播
           （1）不可以被终止
           （2）数据不可以被修改

        特殊广播接收者（必须动态注册）
           操作特别频繁的广播事件，比如屏幕的锁屏和解锁、电池电量的变化
           代码registerReceiver()的广播，在（activity/service）销毁时需要unregisterReceiver();
    */


    /*
        receiverPermission  接收的权限
        resultReceiver      最终的receiver
        initialCode         初始码
        initialData         初始化数据

        sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver,
            Handler scheduler, int initialCode, String initialData, Bundle initialExtras);
    */
    }

}