package com.github.chengzhx76.jdk.thread;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description
 * @Author admin
 * @Date 2020/5/9 10:00
 * @Version 3.0
 */
public class Test {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        /*Thread thread = new Thread();
        thread.interrupted();
        thread.isInterrupted();*/

        new Test().test();

    }

    private void test() {
        Integer result = write(() -> {
            System.out.println("1111");
            return 111;
        });

        System.out.println("result-> " + result);

        write(() -> {
            await(2);
            System.out.println("2222");
        });
        write(() -> {
            System.out.println("3333");
        });
        write(() -> {
            System.out.println("4444");
        });
    }


    private void write(Runnable runnable) {


//        new Thread(runnable).start();

        write(
                () -> {
                    System.out.println("Runnable.b");
                    runnable.run();
                    System.out.println("Runnable.a");
                    return null;
                }
        );
    }

    private <V> V write(Callable<V> callable) {
        V value = null;
//        Lock writeLock = lock.writeLock();
        try {
//            writeLock.lock();
            System.out.println("Callable.b");
            value = callable.call();
            System.out.println("Callable.a");
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e.getCause());
        } finally {
//            writeLock.unlock();
        }
        return value;
    }


    void await(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
