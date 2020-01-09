package handler;

public class Handler {

    final Looper looper;
    final MessageQueue mQueue;

    public Handler() {
        looper = Looper.myLooper();
        mQueue = looper.mQueue;
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

    public void handleMessage(Message msg) {

    }

}
