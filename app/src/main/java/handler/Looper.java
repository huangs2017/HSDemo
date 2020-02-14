package handler;

public final class Looper {

    static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
    MessageQueue mQueue;


    private Looper(){
        mQueue = new MessageQueue();
    }


    public static  void prepare(){
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper());
    }


    public static Looper myLooper(){
        return threadLocal.get();
     }


    public static void loop() {
        Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
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
