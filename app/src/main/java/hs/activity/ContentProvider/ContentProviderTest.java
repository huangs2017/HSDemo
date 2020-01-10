package hs.activity.ContentProvider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import hs.activity.R;

public class ContentProviderTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void bind (View view) {
        Uri uri = Uri.parse("content://com.hs.provider/query");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String money = cursor.getString(2);
                System.out.println("name: " + name + "---money:" + money);
            }
        }
    }

}
