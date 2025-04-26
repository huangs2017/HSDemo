package handler;

public final class Looper {

    static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
    MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static void prepare() {
        threadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return threadLocal.get();
    }

    public static void loop() {
        Looper me = myLooper();
        MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next();
            if (msg == null) { //----------------
                continue;
            }
            msg.target.dispatchMessage(msg);
        }
    }

}