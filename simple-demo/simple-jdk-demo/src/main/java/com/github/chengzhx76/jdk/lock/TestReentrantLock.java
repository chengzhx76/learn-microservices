package com.github.chengzhx76.jdk.lock;

import com.github.chengzhx76.jdk.util.ToolUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/14 13:11
 * @Version 3.0
 */
public class TestReentrantLock {

    final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        TestReentrantLock test = new TestReentrantLock();
        new Thread(test::testLock, "thread A").start();
        new Thread(test::testLock, "thread B").start();
        new Thread(test::testLock, "thread C").start();
        new Thread(test::testLock, "thread D").start();
        new Thread(test::testLock, "thread E").start();
        new Thread(test::testLock, "thread F").start();
    }

    void testLock() {
        try {
            System.out.println(Thread.currentThread().getName() + " 开始获取锁");
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 获取锁");
            ToolUtils.await(1);
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放锁");
            lock.unlock();
        }
    }

}
