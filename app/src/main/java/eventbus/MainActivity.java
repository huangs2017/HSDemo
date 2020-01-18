package eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import eventbus.core.EventBus;
import eventbus.core.Subscribe;
import eventbus.core.ThreadMode;
import hs.activity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EventBus.getDefault().register(this);
    }

    // 跳转到SecondActivity
    public void bind(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(Friend friend) {
        Toast.makeText(this, friend.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
