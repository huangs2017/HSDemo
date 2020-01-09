package glide;

import android.content.Context;
import android.widget.ImageView;
import java.lang.ref.SoftReference;

import glide.core.RequestManager;
import util.MyUtil;

public class BitmapRequest {

    private String url;
    private SoftReference<ImageView> imageView;
    private String urlMD5;
    private int loadingResId;   // 正在等待的图片
    private Context context;

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest loading(int loadingResId) {
        this.loadingResId = loadingResId;
        return this;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMD5 = MyUtil.stringToMD5(url);
        return this;
    }
    public void into(ImageView imageView) {
        this.imageView = new SoftReference<> (imageView) ;
        imageView.setTag (urlMD5) ; //////////
        RequestManager.getInstance().addBitmapRequest(this); //-----------
    }




    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
