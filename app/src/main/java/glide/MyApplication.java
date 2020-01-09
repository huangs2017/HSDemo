package glide;

import android.app.Application;
import glide.cache.DoubleLruCache;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DoubleLruCache.getInstance(this); // 缓存初始化
    }

    public static MyApplication getInstance() {
        return instance;
    }

}