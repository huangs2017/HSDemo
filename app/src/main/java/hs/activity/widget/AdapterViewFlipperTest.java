package hs.activity.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hs.activity.R;

public class AdapterViewFlipperTest extends AppCompatActivity {

    int[] imageIds = new int[] {R.drawable.chunv, R.drawable.sheshou, R.drawable.juxie, R.drawable.shizi, R.drawable.jinniu};
    AdapterViewFlipper flipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper_activity);
        flipper = findViewById(R.id.flipper);
        flipper.setAdapter(adapter);
    }

    public void prev(View source) {
        flipper.showPrevious();
        flipper.stopFlipping();
    }

    public void next(View source) {
        flipper.showNext();
        flipper.stopFlipping();
    }

    public void auto(View source) {
        flipper.startFlipping();
    }


    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(AdapterViewFlipperTest.this);
            imageView.setImageResource(imageIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            return imageView;
        }
    };

}
