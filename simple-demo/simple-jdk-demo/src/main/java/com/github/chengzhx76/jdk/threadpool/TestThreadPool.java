package com.github.chengzhx76.jdk.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/4/23
 */
public class TestThreadPool {

    // 状态在高位存储，int 32 二进制是32位 前三位用来保存状态 后29位用来表示线程数 最大可用线程数为 2^29-1
    // https://www.jianshu.com/p/d2729853c4da
    ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
            0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
//        base();
        testScheduled();
    }

    private static void base() {
//        System.out.println(Integer.toBinaryString(Integer.SIZE));
//        System.out.println(Integer.toBinaryString(COUNT_BITS));
//        System.out.println(Integer.toBinaryString(1));
//        System.out.println(Integer.toBinaryString(1 << 3));
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(ctlOf(RUNNING, 0)));
    }

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0)); // 这个值前三位表示运行状态 后29位表示线程数 如：111_00000000000000000000000000000
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }


    private static void testScheduled() {
        final ScheduledExecutorService retryExecutor =
                Executors.newScheduledThreadPool(0, Executors.defaultThreadFactory());
        final AtomicInteger retryCounter = new AtomicInteger(0);

        ScheduledFuture retryScheduledFuture = retryExecutor.scheduleWithFixedDelay(() -> {
            System.out.println("i-> " + retryCounter.incrementAndGet());
            if (retryCounter.get() == 10) {
//                retryScheduledFuture.cancel(false);
                retryExecutor.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

}

