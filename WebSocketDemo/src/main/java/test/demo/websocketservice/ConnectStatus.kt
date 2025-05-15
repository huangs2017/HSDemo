package test.demo.websocketservice

/**
 * WebSocketService连接的状态
 */
enum class ConnectStatus {

    /**
     * 已断开连接
     * 刚启动或网络错误以及重连尝试间的状态
     */
    DISCONNECTED,

    /**
     * 已经连接，通信正常
     */
    CONNECTED,

    /**
     * 正在关闭连接
     */
    CLOSING
}