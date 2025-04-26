package handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import handler.IdleHandlerDemo.IdleHandlerTest;
import handler.IdleHandlerDemo.Task;
import hs.activity.R;

public class HandlerTest extends AppCompatActivity {

    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void bind(View view) {
        Looper.prepare(); ////
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.e("testLog", "出参--> " + msg.obj.toString());
            }
        };
        sendMessage();
        Looper.loop(); ////
    }

    void sendMessage() {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                public void run() {
                    Message msg = new Message();
                    String result = Thread.currentThread().getName();
                    Log.e("testLog", "入参--> " + result);
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }.start();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        IdleHandlerTest idleHandlerTest = new IdleHandlerTest();
        idleHandlerTest.addTask(new Task())
                .addTask(new Task())
                .start();
    }

}