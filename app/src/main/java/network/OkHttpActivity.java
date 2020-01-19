package network;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import hs.activity.R;
import network.OkHttp.Call;
import network.OkHttp.Callback;
import network.OkHttp.OkHttpClient;
import network.OkHttp.bean.Request;
import network.OkHttp.bean.RequestBody;
import network.OkHttp.bean.Response;

public class OkHttpActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpActivity";
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity);
        client = new OkHttpClient();
    }

    public void get(View view) {
        Request request = new Request.Builder()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Response response) {
                Log.e(TAG, "get响应体: " + response.getBody());

            }
        });
    }

    public void post(View view) {
        RequestBody body = new RequestBody()
                .add("city", "长沙")
                .add("key", "13cb58f5884f9749287abbead9c658f2");
        Request request = new Request.Builder()
                .post(body)
                .url("http://restapi.amap.com/v3/weather/weatherInfo")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Response response) {
                Log.e(TAG,"post响应体: " + response.getBody());
            }
        });
    }

}
