package test.demo.service

// WebSocket连接的状态
enum class ConnectStatus {
    DISCONNECTED,   // 已断开连接
    CONNECTED,      // 已连接，通信正常
    CLOSING         // 正在关闭连接
}