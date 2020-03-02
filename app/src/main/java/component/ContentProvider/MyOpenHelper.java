package component.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context ctx) {
        super(ctx, "Account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db. execSQL("create table info(_id integer primary key autoincrement, name varchar(20), money varchar(20))");
        db.execSQL("insert into info(name, money) values(?, ?)", new String[]{"张三","5000"});
        db.execSQL("insert into info(name, money) values(?, ?)", new String[]{"李四", "3000"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
