package com.github.chengzhx76.jdk.threadlocal;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author admin
 * @Date 2020/12/15 11:14
 * @Version 3.0
 */
public class TestInheritableThreadLocal {

    static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1,
            0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {

        threadLocal.set("test main thread");

        new Thread(() -> System.out.println("child thread --> " + threadLocal.get())).start();

        threadPool.execute(() -> System.out.println("pool thread --> " + threadLocal.get()));

    }



}
