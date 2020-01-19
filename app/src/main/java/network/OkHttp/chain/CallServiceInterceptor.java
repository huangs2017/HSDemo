package network.OkHttp.chain;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import network.OkHttp.NetDataPipe;
import network.OkHttp.SocketConnect;
import network.OkHttp.bean.Request;
import network.OkHttp.bean.Response;

public class CallServiceInterceptor implements Interceptor {

    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("intercept", "通信拦截器....");
        NetDataPipe netDataPipe = chain.netDataPipe;
        SocketConnect socketConnect = chain.socketConnect;
        Request request = chain.call.request();
        InputStream is = socketConnect.call(netDataPipe, request);

        //HTTP/1.1 200 OK 空格隔开的响应状态
        String statusLine = netDataPipe.readLine(is);
        String[] status = statusLine.split(" ");
        int responseCode = Integer.valueOf(status[1]);

        Map<String, String> headers = netDataPipe.readHeaders(is);

        boolean isKeepAlive = false; // 是否保持连接
        if (headers.containsKey("Connection")) {
            isKeepAlive = headers.get("Connection").equalsIgnoreCase("Keep-Alive");
        }

        int contentLength = -1;
        if (headers.containsKey("Content-Length")) {
            contentLength = Integer.valueOf(headers.get("Content-Length"));
        }

        boolean isChunked = false; // 分块编码数据
        if (headers.containsKey("Transfer-Encoding")) {
            isChunked = headers.get("Transfer-Encoding").equalsIgnoreCase("chunked");
        }

        String body = null;
        if (contentLength > 0) {
            byte[] bytes = netDataPipe.readBytes(is, contentLength);
            body = new String(bytes);
        } else if (isChunked) {
            body = netDataPipe.readChunked(is);
        }

        socketConnect.updateLastUseTime();
        return new Response(responseCode, contentLength, headers, body, isKeepAlive);
    }

}
