package network.OkHttp.chain;

import java.io.IOException;

import network.OkHttp.bean.Response;

public interface Interceptor {

    Response intercept(InterceptorChain chain) throws IOException;
}
