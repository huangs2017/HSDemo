package hs.activity.widget2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatButton;

import hs.activity.R;

// Android可以自定义一个View，用它的Canvas
public class MyButton extends AppCompatButton {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;

    {
        paint = new Paint();
        path = new Path();
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
    }

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            System.out.println(attrs.getAttributeName(i) + "  " + attrs.getAttributeValue(i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);

        canvas.drawText("HelloWorld", 0, 20, paint);

        path.moveTo(300, 0);
        path.lineTo(300, 50);
        path.lineTo(200, 50);
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawTextOnPath("abcdefg", path, 0, 0, paint);

        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(bitmap, new Rect(0, 0, 48, 20), new Rect(100, 0, 148, 20), null);

//		Rect rect = new Rect(0, 0, 300, 30);
        RectF rectF = new RectF(0, 100, 300.0f, 150);
        canvas.drawRect(rectF, paint);
        canvas.drawOval(rectF, paint);

        canvas.drawRect(0, 200, 100, 250, paint);
        canvas.drawLine(0, 200, 100, 250, paint);
        canvas.drawCircle(50, 225, 20, paint);
    }

}
