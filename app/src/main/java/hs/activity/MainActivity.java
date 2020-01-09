package hs.activity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void bind (View view) {
        Intent intent = new Intent(this, RemoteService.class);
        startService(intent);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }

//        Intent intent = new Intent();
//        intent.setClassName("food.activity", "food.activity.LoginActivity");
//        startActivity(intent);
    }

}




/*
    进程的生命周期：
        如果内存不足，Android可能会决定终止一个进程。在此进程中运行着的应用程序组件也会因此被销毁。
        Android系统根据进程内运行的组件及这些组件的状态，决定进程的优先级

        1. 前台进程
            正处于Activity onResume()状态，运行着正与用户交互的Activity。
            正处于bind服务交互的状态
            运行着"前台"服务——服务以startForeground()方式被调用。
        2. 可见进程
           相当于Activity执行了onPause方法，界面上按钮不可以被点击，失去焦点。
            运行着被可见activity绑定的服务。
        3. 服务进程
            运行着由startService()方法启动的服务。
        4. 后台进程
            Activity执行了onStop方法，界面不可见但activity并没有销毁
        5. 空进程
            没有运行任何组件（四大组件）的进程，保留这个进程是为了缓存需要


    Android 四大组件都运行在主线程中
*/


/*
    混合方式开启服务
    需求：既想让服务在后台长期运行，又想调用服务里的方法
    1. 调用startService开启服务，保证服务在后台长期运行
    2. 调用bindService获取IBinder的实现类
    3. 调用unbindService解绑服务
    4. 调用stopService
*/
