package handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue2 {

    Message[] items;
    int putIndex = 0;
    int takeIndex = 0;
    int count = 0;

    Lock lock;
    Condition notEmpty;
    Condition notFull;

    public MessageQueue2() {
        this.items = new Message[50]; // 限定空间大小，节省内存
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }


    // 入队 （生产者）
    public void enqueueMessage(Message msg) {
        try {
            lock.lock();
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            items[putIndex] = msg;
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;

            notEmpty.signalAll(); // 唤醒所有
        } finally {
            lock.unlock();
        }
    }


    // 出队 （消费者）
    public Message next() {
        Message msg;
        try {
            lock.lock();
            while (count == 0) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            msg = items[takeIndex];
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;

            notFull.signalAll();
        } finally {
            lock.unlock();
        }

        return msg;
    }


}