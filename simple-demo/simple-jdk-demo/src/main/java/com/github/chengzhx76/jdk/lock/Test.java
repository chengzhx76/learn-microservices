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


    public static void main(String[] args) {
        Test test = new Test();
        test.addOne();
    }

}
