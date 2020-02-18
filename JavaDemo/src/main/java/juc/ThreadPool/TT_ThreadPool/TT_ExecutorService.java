package juc.ThreadPool.TT_ThreadPool;

public interface TT_ExecutorService {
    void execute(Runnable task);
    void shutdown();
    int getActiveThread();
    Runnable getTask();
}
