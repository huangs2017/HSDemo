package hs.activity.widget2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import hs.activity.R;
import hs.activity.widget2.MyView;

//渲染测试
public class MyViewActivity extends AppCompatActivity {

	private Shader[] shaders = new Shader[5];
	private int[] colors;
	MyView myView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shader_activity);
		myView = findViewById(R.id.my_view);
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		colors = new int[] { Color.RED, Color.GREEN, Color.BLUE };
		shaders[0] = new BitmapShader(bm, TileMode.REPEAT, TileMode.MIRROR);
		shaders[1] = new LinearGradient(0, 0, 100, 100, colors, null, TileMode.REPEAT);
		shaders[2] = new RadialGradient(100, 100, 80, colors, null, TileMode.REPEAT);
		shaders[3] = new SweepGradient(160, 160, colors, null);
		shaders[4] = new ComposeShader(shaders[1], shaders[2], PorterDuff.Mode.DARKEN);
	}

	public void onClick(View source) {
		switch (source.getId()) {
		case R.id.bn1:
			myView.paint.setShader(shaders[0]);
			break;
		case R.id.bn2:
			myView.paint.setShader(shaders[1]);
			break;
		case R.id.bn3:
			myView.paint.setShader(shaders[2]);
			break;
		case R.id.bn4:
			myView.paint.setShader(shaders[3]);
			break;
		case R.id.bn5:
			myView.paint.setShader(shaders[4]);
			break;
		}
		myView.invalidate();
	}

}