package hs.activity.LruCacheDemo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class ImageMemoryCache {
    /**
     * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。
     * 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
     */
    private static LruCache<String, Bitmap> mLruCache;  //硬引用缓存
    private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache;  //软引用缓存

    public ImageMemoryCache(Context context) {

        //可用的内存 m(兆)
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = am.getMemoryClass();  //???
//    	System.out.println(memClass);

        int hard_cache_size = 1024 * 1024 * memClass / 4;  //设置硬引用缓存容量，为系统可用内存的1/4
        mLruCache = new LruCache<String, Bitmap>(hard_cache_size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != null)
                    // 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
                    mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
            }
        };

        final int soft_chche_size = 15;  //软引用缓存容量
        mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(soft_chche_size, 0.75f, true) {
            private static final long serialVersionUID = 6040103833179403725L;
            @Override
            protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
                if (size() > soft_chche_size) {
                    return true;
                }
                return false;
            }
        };

    }

    //添加图片到缓存
    public void addBitmapToCache(String url, Bitmap bitmap) {
        synchronized (mLruCache) {
            mLruCache.put(url, bitmap);
        }
    }

    // 从缓存中获取图片
    public Bitmap getBitmapFromCache(String url) {
        Bitmap bitmap;
        //先从硬引用缓存中获取
        synchronized (mLruCache) {
            bitmap = mLruCache.get(url);
            if (bitmap != null) {
                //如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(url);
                mLruCache.put(url, bitmap);
                return bitmap;
            }
        }
        //如果硬引用缓存中找不到，到软引用缓存中找
        synchronized (mSoftCache) {
            SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
            if (bitmapReference != null) {
                mSoftCache.remove(url);
                bitmap = bitmapReference.get();
                if (bitmap != null) {
                    mLruCache.put(url, bitmap); //将图片移回硬缓存
                    return bitmap;
                }
            }
        }
        return null;  //凑格式
    }


    public void clearCache() {
        mSoftCache.clear();
    }

}