package test.demo.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object MyViewModel : ViewModel() {
    val receiveStringData = MutableLiveData<String>()   // 接收到的字符串数据
    val sendStringData = MutableLiveData<String>()      // 要发送的字符串数据
    val stopByUser = MutableLiveData<Boolean>()         // 给服务断开连接的信号
    val status = MutableLiveData<ConnectStatus>()       // 服务状态
}