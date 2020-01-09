package hs.activity.widget2;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyButtonActivity extends AppCompatActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new MyButton(this);
        button.setText("xxx");
        setContentView(button, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
    
}
