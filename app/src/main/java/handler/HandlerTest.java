package handler;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import handler.IdleHandlerDemo.IdleHandlerTest;
import handler.IdleHandlerDemo.Task;
import hs.activity.R;

public class HandlerTest extends AppCompatActivity {

	Button btn;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		btn = findViewById(R.id.button1);

		btn.setOnClickListener(v -> {
			Looper.prepare(); /////////////////////////////
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Log.i("testLog", "出参--> " + msg.obj.toString());
				};
			};

			for (int i=0; i<5; i++) {
				new Thread() {
					public void run() {
						Message msg = new Message();
						String result = Thread.currentThread().getName();
						Log.i("testLog", "入参--> " + result);
						msg.what = 1;
						msg.obj = result;
						handler.sendMessage(msg);
					}
				}.start();
			}
			Looper.loop(); /////////////////////////////
		});

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