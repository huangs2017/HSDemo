package component.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AccountProvider extends ContentProvider {


    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static int querySuccess = 1;
    private static int insertSuccess = 2;
    private MyOpenHelper myOpenHelper;

    static {
        // authority和在清单文件里定义的一致
        // uri--> content://com.hs.provider/query

//      uriMatcher.addURI(String authority, String path, int code)
        uriMatcher.addURI("com.hs.provider", "query", querySuccess);
        uriMatcher.addURI("com.hs.provider", "insert", insertSuccess);
    }

    @Override
    public boolean onCreate() {
        myOpenHelper = new MyOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        if (code == querySuccess) {
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        } else {
            throw new IllegalArgumentException("路径不匹配");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
