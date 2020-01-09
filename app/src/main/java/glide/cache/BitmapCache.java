package glide.cache;

import android.graphics.Bitmap;
import glide.BitmapRequest;

public interface BitmapCache {
    //存入内存
    void put(BitmapRequest request, Bitmap bitmap);

    // 读取缓存的图片
    Bitmap get(BitmapRequest request);

    // 清除缓存的图片
    void remove(BitmapRequest request);

    // 清除所属的activity 的bitmap
    void remove(int activityCode);
}
