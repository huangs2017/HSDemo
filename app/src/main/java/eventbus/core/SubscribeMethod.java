package eventbus.core;

import java.lang.reflect.Method;

//EventBus注册方法信息
public class SubscribeMethod {

    private Method method;            // 注册方法
    private ThreadMode threadMode;    // 线程类型
    private Class<?> eventType;       // 参数类型

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public void setEventType(Class<?> eventType) {
        this.eventType = eventType;
    }
}
