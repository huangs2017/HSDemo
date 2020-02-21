package eventbus.core;

import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {

    static EventBus instance = new EventBus();
    Map<Object, List<Subscriber>> map;
    Handler handler;
    ExecutorService executorService; // 线程池

    public static EventBus getDefault() {
        return instance;
    }

    private EventBus() {
        this.map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    //register--------------------------------------------------------------------------------------
    public void register(Object object) {
        List<Subscriber> subscriberList = map.get(object);
        //如果已经注册，就不需要注册
        if (subscriberList == null) {
            subscriberList = getSubscribers(object);
            map.put(object, subscriberList);
        }
    }

    //遍历能够接收事件的方法
    private List<Subscriber> getSubscribers(Object object) {
        List<Subscriber> list = new ArrayList<>();
        Class<?> aClass = object.getClass();
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
            Subscriber subscriber = new Subscriber(method, threadMode, parameterTypes[0]);
            list.add(subscriber);
        }
        return list;
    }
    //register--------------------------------------------------------------------------------------


    //post==========================================================================================
    public void post(final Object event) {
        for (Object object : map.keySet()) {                // 拿到注册类object
            List<Subscriber> list = map.get(object);        // 获取注册类中所有添加注解的方法
            for (final Subscriber subscriber : list) {
                // 判断这个方法是否应该接收事件
                if (subscriber.getParameterType().isAssignableFrom(event.getClass())) {
                    postExecute(object, subscriber, event);
                }
            }
        }
    }

    public void postExecute (final Object object, final Subscriber subscriber, final Object event) {
        switch (subscriber.getThreadMode()) {
            case MAIN:
                // post方法在主线程
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    invoke(object, subscriber, event);
                } else {
                    // post方法在子线程
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invoke(object, subscriber, event);
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
                            invoke(object, subscriber, event);
                        }
                    });
                } else {
                    //post方法在子线程
                    invoke(object, subscriber, event);
                }
                break;
            case POSTING:
                break;
        }

    }

    private void invoke(Object object, Subscriber subscriber, Object event) {
        Method method = subscriber.getMethod();
        try {
            method.invoke(object, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //post==========================================================================================


    //取消注册
    public void unregister(Object object) {
        if (map.get(object) != null) {
            map.remove(object);
        }
    }

}