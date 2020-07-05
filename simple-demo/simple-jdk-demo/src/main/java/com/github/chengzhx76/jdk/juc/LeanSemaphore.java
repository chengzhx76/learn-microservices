package com.github.chengzhx76.jdk.juc;

import java.time.LocalTime;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @Description
 * @Author admin
 * @Date 2020/5/13 9:46
 * @Version 3.0
 */
public class LeanSemaphore {

    public static void main(String[] args) throws InterruptedException {
        Obj[] obj = {new Obj(), new Obj(), new Obj(), new Obj(), new Obj()};

        ObjPool<Obj, String> pool = new ObjPool<>(obj);

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(() -> {
                String result = pool.exec(Obj::task);
                System.out.println(result);
            });
            thread.start();
        }

    }


}

class ObjPool<T, R> {
    final List<T> pool;

    //
    final Semaphore sem;

    ObjPool(T[] ts) {
        pool = new Vector<>();
        for (int i = 0; i<ts.length; i++) { // 不同对象
            pool.add(ts[i]);
        }
        sem = new Semaphore(ts.length);
    }

    // 利用对象池的对象，调用func
    R exec(Function<T, R> function) {
        T t = null;
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }
    }

}

class Obj {
    String task() {
        try {
            TimeUnit.SECONDS.sleep(3);
            return LocalTime.now().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}