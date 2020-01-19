package network.OkHttp.chain;

import android.util.Log;

import java.io.IOException;

import network.OkHttp.bean.Response;

public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("intercept", "重试拦截器....");
        IOException exception = null;
        for (int i = 0; i < 3; i++) { // 默认重试3次
            try {
                Response response = chain.proceed();
                return response;
            } catch (IOException e) {
                exception = e;
            }
        }
        throw exception;
    }
}
