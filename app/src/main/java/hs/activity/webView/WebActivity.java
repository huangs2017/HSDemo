package hs.activity.webView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import hs.activity.R;

public class WebActivity extends Activity {

    private Button button;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        button = findViewById(R.id.button);
        webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/htmlDemo.html");

        //Android调用JS
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                webView.loadUrl("javascript:callJavaScriptMethod()");
            }
        });

        //JS调用Android
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface //没有这一行，JS调用Android会失败
            public void callJavaMethod() {
                Toast.makeText(getApplicationContext(), "JS调用Android成功", Toast.LENGTH_LONG).show();
            }
        }, "injectedObject");
    }

}
