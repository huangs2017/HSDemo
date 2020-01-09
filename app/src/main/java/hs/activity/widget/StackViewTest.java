package hs.activity.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;

import androidx.appcompat.app.AppCompatActivity;

import hs.activity.R;

public class StackViewTest extends AppCompatActivity {

    StackView stackView;
    int[] imageIds = new int[] {R.drawable.chunv, R.drawable.sheshou, R.drawable.juxie,  R.drawable.shizi, R.drawable.jinniu};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stack_main);
        stackView = findViewById(R.id.mStackView);

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
                ImageView imageView = new ImageView(StackViewTest.this);
                imageView.setImageResource(imageIds[position]);
                return imageView;
            }
        };

        stackView.setAdapter(adapter);
    }

    public void prev(View view) {
        stackView.showPrevious();
    }

    public void next(View view) {
        stackView.showNext();
    }

}