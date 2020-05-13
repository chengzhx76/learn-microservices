package com.github.chengzhx76.jdk.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author admin
 * @Date 2020/5/9 9:30
 * @Version 3.0
 */
public class TestCondition {

    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public static void main(String[] args) {

    }

}
