package hs.activity.TTRecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import hs.activity.R;

public class MainAct extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);
        recyclerView = findViewById(R.id.table);
        recyclerView.setAdapter(new MyAdapter());







    }



    class MyAdapter implements RecyclerView.Adapter {

        private static final String TAG = "MainAct";

        @Override
        public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
            convertView = MainAct.this.getLayoutInflater().inflate(R.layout.item_table, parent, false);
            TextView textView = convertView.findViewById(R.id.text1);
            textView.setText("RecycleView Item " + position);
            Log.i(TAG, "onCreateViewHolder: " + convertView.hashCode());
            return convertView;
        }

        @Override
        public View onBinderViewHolder(int position, View convertView, ViewGroup parent) {
            TextView textView = convertView.findViewById(R.id.text1);
            textView.setText("RecycleView Item " + position);
            Log.i(TAG, "onBinderViewHolder: " + convertView.hashCode());
            return convertView;
        }

        @Override
        public int getItemViewType(int row) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public int getHeight(int index) {
            return 100;
        }

    }


}



