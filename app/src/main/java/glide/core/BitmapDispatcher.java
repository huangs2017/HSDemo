package glide.core;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import java.util.concurrent.BlockingQueue;
import glide.BitmapRequest;
import util.MyUtil;
import glide.cache.DoubleLruCache;

public class BitmapDispatcher extends Thread{

    BlockingQueue<BitmapRequest> requestQueue;
    DoubleLruCache doubleLruCache = DoubleLruCache.getInstance();
    Handler handler = new Handler(Looper.getMainLooper());

    public BitmapDispatcher(BlockingQueue<BitmapRequest> requestQueue){
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                BitmapRequest request = requestQueue.take();
                showLoadingImage(request);
                Bitmap bitmap = findBitmap(request);
                deliveryUIThread(request, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void showLoadingImage(BitmapRequest request){
        int resId = request.getLoadingResId();
        if (resId > 0){
            ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId); // UI线程
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest request) {
        //内存、硬盘、网络
        Bitmap bitmap = doubleLruCache.get(request);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = MyUtil.downloadImage(request.getUrl());
        if (bitmap != null) {
            doubleLruCache.put(request, bitmap);
        }
        return bitmap;
    }

    private void deliveryUIThread( BitmapRequest request, Bitmap bitmap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = request.getImageView();
                if (imageView.getTag().equals(request.getUrlMD5())) { // ListView滑动，防止界面错位
                    imageView.setImageBitmap(bitmap); // UI线程
                }
            }
        });
    }

}
