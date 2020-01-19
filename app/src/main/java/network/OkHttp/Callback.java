package network.OkHttp;

import network.OkHttp.bean.Response;

public interface Callback {
    void onFailure(Throwable throwable);

    void onResponse(Response response);
}
