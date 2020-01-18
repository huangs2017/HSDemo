package eventbus;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import eventbus.core.EventBus;
import hs.activity.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    // 发送消息
    public void bind(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                EventBus.getDefault().post(new Friend("Alan", 18));
//            }
//        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
