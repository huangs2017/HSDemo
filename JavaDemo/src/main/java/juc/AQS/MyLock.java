package juc.AQS;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

// 不可重入 公平 排他锁
public class MyLock {

    private volatile int state = 0;
    private Thread lockHolder;
    private final ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<>();


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }


    public boolean acquire() {
        Thread current = Thread.currentThread();
        int state = getState();
        if (state == 0) { //表示当前线程可以加锁
            ConcurrentLinkedQueue<Thread> q = this.queue;
            if (  (q.size() == 0 || current == queue.peek()) && compareAndSwapState(0, 1)  ) {
                setLockHolder(current);
                return true;
            }
        }
        return false;
    }


    // 加锁
    public void lock() {
        // T1加锁成功
        if (acquire()) {
            return;
        }
        //加锁失败
        Thread current = Thread.currentThread();
        queue.add(current);

        //自旋，直到加锁成功
        for (; ;) {
            if ((queue.peek() == current) && acquire()) {// 拿队列的第一个线程
                queue.poll();//线程被唤醒，拿到锁，从队列移除
                return;
            }
            //T2 T3执行
            LockSupport.park(); //-----------
        }
    }

    // 解锁
    public void unlock() {
        Thread current = Thread. currentThread() ;
        if (current != lockHolder) {
            throw new RuntimeException ("couldn't release lock") ;
        }
        int c = getState() ;
        if (compareAndSwapState (1, 0)) { // T1释放锁
                setLockHolder (null) ;
            Thread thread = queue.peek() ;
            if (thread != null) {
                LockSupport.unpark(thread) ; //-----------
            }
        }
    }


    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error();
        }
    }

    public final boolean compareAndSwapState(int except, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, except, update);
    }


}
