package hs.activity.widget2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

class OverDraw extends View {

    int imageWidth;
    int imageSpace;  // 两个相邻图片的左边缘之间的距离
    int imageLeft;   // 跟踪每张图片的左坐标
    ArrayList<Bitmap> bitmapList = new ArrayList<>();

    public OverDraw(Context context, int[] imageResIds, int imageWidth, int imageSpace) {
        super(context);
        this.imageWidth = imageWidth;
        this.imageSpace = imageSpace;
        for (int i = 0; i < imageResIds.length; i++) {
            Bitmap bitmap = getBitmap(imageResIds[i]);
            bitmapList.add(bitmap);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i;
        for (i = 0; i < bitmapList.size(); i++) {
            Bitmap bitmap = bitmapList.get(i);
            imageLeft = i * imageSpace;
            canvas.save();
            if (i < bitmapList.size() - 1) {
                // 指定绘制区域，避免过度绘制
                canvas.clipRect(imageLeft, 0, imageLeft + imageSpace, bitmap.getHeight());
            }
            canvas.drawBitmap(bitmap, imageLeft, 0, null); // 绘制最后一张图片不需要canvas.clipRect
            canvas.restore();
        }
    }


    //--------------------------------------------------------------------------
    public Bitmap getBitmap(int resId) {
        Bitmap bitmap = makeBitmap(getResources(), resId, imageWidth);
        return getScaledBitmap(bitmap, imageWidth);
    }

    // Create a bitmap from drawable resource.
    public Bitmap makeBitmap(Resources res, int resId, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth; // Get the raw width of image.
        int inSampleSize = 1;
        // Calculate the best inSampleSize.
        if (width > reqWidth) {
            final int halfWidth = width / 2;
            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // Return a bitmap to a specific width.
    private Bitmap getScaledBitmap(Bitmap bitmap, int width) {
        float scale = (float) width / bitmap.getWidth();
        int dstWidth = (int) (bitmap.getWidth() * scale);
        int dstHeight = (int) (bitmap.getHeight() * scale);
        return Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false);
    }
    //--------------------------------------------------------------------------

}