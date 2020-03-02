package component.Broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

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


    }

}