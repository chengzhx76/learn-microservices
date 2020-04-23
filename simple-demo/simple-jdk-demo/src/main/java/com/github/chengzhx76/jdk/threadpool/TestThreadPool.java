package com.github.chengzhx76.jdk.threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    }

}
