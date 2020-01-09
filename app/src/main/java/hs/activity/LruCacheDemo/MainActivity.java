package hs.activity.LruCacheDemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import hs.activity.R;

public class MainActivity extends Activity {

    private ImageMemoryCache memoryCache;
    private ImageFileCache fileCache;
    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lru_cache_activity);
        memoryCache = new ImageMemoryCache(this);
        fileCache = new ImageFileCache();
        imageView = findViewById(R.id.imageView1);
        Bitmap b = getBitmap("http://f.hiphotos.baidu.com/a.jpg");
        imageView.setImageBitmap(b);
    }

    public Bitmap getBitmap(String url) {
        Bitmap result = memoryCache.getBitmapFromCache(url); // 从内存缓存中获取图片
        if (result == null) {
            result = fileCache.getImage(url); // 文件缓存中获取
            if (result == null) {
//                result = downloadBitmap(url); // 从网络获取
                if (result != null) {
                    fileCache.saveBitmap(result, url);
                    memoryCache.addBitmapToCache(url, result); // mLruCache.put(url, bitmap)
                }
            } else {
                memoryCache.addBitmapToCache(url, result);
            } // 添加到内存缓存
        }
        return result;
    }

}
