package eventbus.core;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {

    static EventBus instance = new EventBus();
    Map<Object, List<SubscribeMethod>> cacheMap;
    Handler handler;
    ExecutorService executorService; // 线程池

    public static EventBus getDefault() {
        return instance;
    }

    private EventBus() {
        this.cacheMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    //register--------------------------------------------------------------------------------------
    public void register(Object subscriber) {
        List<SubscribeMethod> subscribeMethodList = cacheMap.get(subscriber);
        //如果已经注册，就不需要注册
        if (subscribeMethodList == null) {
            subscribeMethodList = getSubscribeMethods(subscriber);
            cacheMap.put(subscriber, subscribeMethodList);
        }
    }

    //遍历能够接收事件的方法
    private List<SubscribeMethod> getSubscribeMethods(Object subscriber) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> aClass = subscriber.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation == null) {
                continue;
            }

            //检测这个方法是否合格
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException("EventBus接收消息的方法参数只能有一个");
            }

            //符合要求
            ThreadMode threadMode = annotation.threadMode();
            SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, parameterTypes[0]);
            list.add(subscribeMethod);
        }
        return list;
    }
    //register--------------------------------------------------------------------------------------


    //post==========================================================================================
    public void post(final Object obj) {
        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();                // 拿到注册类
            List<SubscribeMethod> list = cacheMap.get(next);    // 获取类中所有添加注解的方法
            for (final SubscribeMethod subscribeMethod : list) {
                // 判断这个方法是否应该接收事件
                if (subscribeMethod.getEventType().isAssignableFrom(obj.getClass())) {
                    postExecute (subscribeMethod, next, obj);
                }
            }
        }
    }

    public void postExecute (final SubscribeMethod subscribeMethod, final Object next, final Object obj) {
        switch (subscribeMethod.getThreadMode()) {
            case MAIN:
                // post方法在主线程
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    invoke(subscribeMethod, next, obj);
                } else {
                    // post方法在子线程
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invoke(subscribeMethod, next, obj);
                        }
                    });
                }
                break;
            case ASYNC: // 接收方法在子线程
                // post方法在主线程
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            invoke(subscribeMethod, next, obj);
                        }
                    });
                } else {
                    //post方法在子线程
                    invoke(subscribeMethod, next, obj);
                }
                break;
            case POSTING:
                break;
        }

    }

    private void invoke(SubscribeMethod subscribeMethod, Object next, Object obj) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(next, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //post==========================================================================================


    //取消注册
    public void unregister(Object subscriber) {
        if (cacheMap.get(subscriber) != null) {
            cacheMap.remove(subscriber);
        }
    }

}