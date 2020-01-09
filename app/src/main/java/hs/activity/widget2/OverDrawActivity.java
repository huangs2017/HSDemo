package hs.activity.widget2;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import hs.activity.R;

// 演示解决OverDraw
public class OverDrawActivity extends AppCompatActivity {

    int image_width = 420;
    int image_spacing = image_width / 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_txt);

        int[] imageIds = {R.mipmap.overdraw1, R.mipmap.overdraw2, R.mipmap.overdraw3};
        OverDraw droidCardView = new OverDraw(this, imageIds, image_width, image_spacing);
        droidCardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout layout = findViewById(R.id.layout_over_draw);
        layout.addView(droidCardView);
    }

}

/*
避免过度绘制方法：
    1. 去掉多余背景色,减少复杂shape使用
    2. 避免层级叠加
    3. 自定义View使用clipRect屏蔽被遮盖View绘制
*/
