package test.demo.websocketservice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * WebSocketService与其它接口进行数据交换的ViewModel
 */
object MyViewModel : ViewModel() {
    /**
     * 接收到的字符串数据
     */
    val receiveStringData = MutableLiveData<String>()

    /**
     * 要发送的字符串数据
     */
    val sendStringData = MutableLiveData<String>()

    /**
     * 给服务断开连接的信号
     */
    val stopByUser = MutableLiveData<Boolean>()

    /**
     * 服务状态
     * 取值为null时，表示service没有启动
     */
    val status = MutableLiveData<ConnectStatus>()

}