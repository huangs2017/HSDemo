package glide.core;

import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import glide.BitmapRequest;

public class RequestManager {

    private static RequestManager instance;
    private BitmapDispatcher[] dispatchers;
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    private RequestManager(){
        start();
    }

    public void addBitmapRequest (BitmapRequest request) {
        if (!requestQueue.contains(request)) {
            requestQueue.add(request) ;
        }else {
            Log.i("hunter", "任务已存在，不用再次添加。");
        }
    }


    private void start(){
        int threadCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher [threadCount] ;
        for (int i = 0; i < dispatchers.length; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher (requestQueue) ;
            bitmapDispatcher.start() ;
            dispatchers[i] = bitmapDispatcher;
        }
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }

            }
        }
        return instance;
    }




}