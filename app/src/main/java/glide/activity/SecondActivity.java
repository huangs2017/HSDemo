package glide.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import glide.Glide;
import hs.activity.R;

public class SecondActivity extends FragmentActivity {

    LinearLayout scroll_line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_test_activity);
        scroll_line = findViewById(R.id.scroll_line);
    }

    public void single(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        scroll_line.addView(imageView);
        String url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3950876957,1467991853&fm=26&gp=0.jpg";
        //设置占位图片
        Glide.with(this).load(url).loading(R.mipmap.ic_launcher).into(imageView);
    }

    public void more(View view) {
        for (int i = 1; i <= 10; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            scroll_line.addView(imageView);
            String url = null;
            switch (i) {
                case 1:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067611298&di=269629fabab510d868bf17c89ffb166e&imgtype=0&src=http%3A%2F%2Fimg.tupianzj.com%2Fuploads%2Fallimg%2F180201%2F9-1P201145015516.jpg";
                    break;
                case 2:
                    url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=934222697,3928396438&fm=11&gp=0.jpg";
                    break;
                case 3:
                    url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3967114459,4266717778&fm=26&gp=0.jpg";
                    break;
                case 4:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067733308&di=be001c108955c8f2bb862950b7e02d81&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201601%2F15%2F20160115151155_FeuKM.jpeg";
                    break;
                case 5:
                    url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559067855055&di=071a5963bea36f3bb84460d981c5ff28&imgtype=0&src=http%3A%2F%2Fi3.sinaimg.cn%2Fent%2F2014%2F0521%2FU2520P28DT20140521152117.jpg";
                    break;
                case 6:
                    url = "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1449634080,114330435&fm=27&gp=0.jpg";
                    break;
                case 7:
                    url = "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2742398684,1626866478&fm=26&gp=0.jpg";
                    break;
                case 8:
                    url = "https://ss3.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3251808624,792931401&fm=26&gp=0.jpg";
                    break;
                case 9:
                    url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1432937052,3680173361&fm=26&gp=0.jpg";
                    break;
                case 10:
                    url = "https://ss0.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3436777124,1783395554&fm=26&gp=0.jpg";
                    break;
            }
            //设置占位图片
            Glide.with(this).load(url).loading(R.mipmap.ic_launcher).into(imageView);
        }
    }

}