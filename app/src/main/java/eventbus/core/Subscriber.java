package eventbus.core;

import java.lang.reflect.Method;

//EventBus注册方法信息
public class Subscriber {

    private Method method;               // 注册方法
    private ThreadMode threadMode;       // 线程类型
    private Class<?> parameterType;      // 事件类型

    public Subscriber(Method method, ThreadMode threadMode, Class<?> parameterType) {
        this.method = method;
        this.threadMode = threadMode;
        this.parameterType = parameterType;
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

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }
}
