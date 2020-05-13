package com.github.chengzhx76.jdk.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/27 20:33
 * @Version 3.0
 */
public class Test {
    /*
        重入锁实现可重入性原理或机制是：每一个锁关联一个线程持有者和计数器，
        当计数器为 0 时表示该锁没有被任何线程持有，那么任何线程都可能获得该
        锁而调用相应的方法；当某一线程请求成功后，JVM会记下锁的持有线程，并
        且将计数器置为 1；此时其它线程请求该锁，则必须等待；而该持有锁的线程
        如果再次请求这个锁，就可以再次拿到这个锁，同时计数器会递增；当线程退
        出同步代码块时，计数器会递减，如果计数器为 0，则释放该锁。
     */
    private final Lock rtl = new ReentrantLock();

    int value;

    public int get() {
        rtl.lock();
        try {
            return value;
        } finally {
            rtl.unlock();
        }
    }

    public void addOne() {

        rtl.lock();
        try {
            value = 1 + get();
        } finally {
            rtl.unlock();
        }

    }

    // ==================== synchronized =============

    public synchronized int synGet() {
        return value;
    }

    public synchronized void synAddOne() {
        value = 1 + synGet();

    }


    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
//        test.addOne();
        test.synAddOne();


        Lock lock = new ReentrantLock();

        lock.lockInterruptibly();


    }

}
