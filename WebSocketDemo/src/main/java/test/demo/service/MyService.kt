package test.demo.service

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * 通过ViewModel进行发送/接收数据的传递
 */
class MyService : LifecycleService() {

    private val viewModel = MyViewModel
    private lateinit var webSocket: WebSocket

    private lateinit var serverUrl: String  // 服务器地址
    private val TAG = "MyTest"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serverUrl = intent?.getStringExtra("serverAddress")!! // 获得服务器地址
        connectToServer()

        viewModel.sendStringData.observe(this) { content ->
            webSocket.send(content)
        }

//        断开重连后，代码有点问题
        viewModel.stopByUser.observe(this) {
            if (it) {
                webSocket.close(1000, "disconnect by user") // 1000: 主动关闭状态码（正常关闭）
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 连接到服务器
     * 同时重置用户手动停止状态
     */
    private fun connectToServer() {
        if (viewModel.stopByUser.value == true) {
            viewModel.stopByUser.postValue(false)
        }

        val okHttpBuilder = OkHttpClient.Builder()
        val requestUrl = Request.Builder().url(serverUrl)
        webSocket = okHttpBuilder.build().newWebSocket(requestUrl.build(), MyWebSocketListener())
    }


    inner class MyWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            viewModel.status.postValue(ConnectStatus.CONNECTED)
            Log.e(TAG, "connected")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.d("MyTest", "receive-------------------$text")
            viewModel.receiveStringData.postValue(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            viewModel.status.postValue(ConnectStatus.DISCONNECTED)
            Log.e(TAG, "onFailure")
            t.printStackTrace()
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            viewModel.status.postValue(ConnectStatus.CLOSING)
            Log.e(TAG, "closing, because [$code]$reason ")
            if (viewModel.stopByUser.value == true) {
                stopSelf()
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            viewModel.status.postValue(ConnectStatus.DISCONNECTED)
            Log.e(TAG, "closed, because [$code]$reason ")
            if (viewModel.stopByUser.value == true) {
                webSocket.cancel()
                stopSelf()
            }
        }
    }

}