package juc.ThreadPool.TT_ThreadPool;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TT_ThreadPoolExecutor implements TT_ExecutorService {

    int poolSize;
    volatile long keepAliveTime;
    volatile boolean allowCoreThreadTimeOut = false;

    BlockingQueue<Runnable> workQueue; //任务队列
    HashSet<Worker> workers = new HashSet<>(); //线程集合
    volatile TT_RejectedExecutionHandler handler;

    AtomicInteger ctl = new AtomicInteger(); //当前存活的线程数
    ReentrantLock mainLock = new ReentrantLock();
    volatile boolean isShutdown = false;
    volatile long completedTaskCount;


    public TT_ThreadPoolExecutor(int poolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue, TT_RejectedExecutionHandler handler) {
        this.poolSize = poolSize;
        this.keepAliveTime = keepAliveTime;
        if (keepAliveTime > 0) {
            allowCoreThreadTimeOut = true;
        }
        this.workQueue = workQueue;
        this.handler = handler;
    }

    @Override
    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("线程已关闭，不接受新任务");
        }
        int c = ctl.get();
        if (c < poolSize) {
            // 创建核心线程去处理任务
            if (addWorker(task, true)) ;  //----------------------
        } else if (workQueue.offer(task)) {

        } else {
            handler.rejected(task, this); // 拒绝策略
        }
    }

    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            isShutdown = true;
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt(); //
                    } catch (Exception e) {

                    } finally {
                        w.unlock();
                    }
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    public int getActiveThread() {
        return ctl.get();
    }

    @Override
    public Runnable getTask() {
        try {
            return allowCoreThreadTimeOut ? workQueue.poll(keepAliveTime, TimeUnit.SECONDS) : workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


//___________________________________________________________
    private boolean addWorker(Runnable r, boolean startNew) {
        if (startNew) {
            ctl.incrementAndGet();
        }
        boolean workerAdded = false;
        boolean workerStarted = false;

        Worker w = new Worker(r);
        Thread t = w.thread;
        if (t != null) {
            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (!isShutdown) {
                    if (t.isAlive()) // 检查线程是否已经处于运行状态，start方法不能重复调用执行
                        throw new IllegalThreadStateException();
                    workers.add(w);
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) {
                t.start(); //----------------------
                workerStarted = true;
            }
        }
        return workerStarted;
    }


    AtomicInteger atomic = new AtomicInteger();
    class Worker extends ReentrantLock implements Runnable {
        volatile long completedTask;
        final Thread thread;
        Runnable firstTask;

        public Worker(Runnable r) {
            this.firstTask = r;
            this.thread = new Thread(this, "thread-name- " + atomic.incrementAndGet());
        }

        public void run() {
            runWorker(this);
        }
    }

    private void runWorker(Worker worker) {
        Thread wt = Thread.currentThread();
        Runnable task = worker.firstTask;
        worker.firstTask = null;
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) { //----------------------
                worker.lock();
                if (isShutdown && !wt.isInterrupted()) {
                    wt.interrupt();
                }
                try {
                    task.run();
                } finally {
                    task = null;
                    worker.completedTask++; //当前线程完成的任务数
                    worker.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(worker, completedAbruptly);
        }
    }

    private void processWorkerExit(Worker worker, boolean completedAbruptly) {
        if (completedAbruptly)
            ctl.decrementAndGet();
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += worker.completedTask;
            workers.remove(worker);
        } finally {
            mainLock.unlock();
        }
        if (completedAbruptly && !workQueue.isEmpty()) {
            addWorker(null, false);
        }
    }

}
