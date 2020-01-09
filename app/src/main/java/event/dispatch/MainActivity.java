package event.dispatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import hs.activity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void bind(View view) {
        Boss1 LiLei = new Boss1("李雷");
        Boss2 HanMeiMei = new Boss2("韩梅梅");
        Boss3 Tom = new Boss3("Tom");

        LiLei.setSuperior(HanMeiMei);
        HanMeiMei.setSuperior(Tom);

        Request hunter = new Request();
        hunter.setRequestNumber(100);
        LiLei.requestDispatch(hunter);
    }

}
