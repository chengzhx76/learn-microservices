package com.github.chengzhx76.jdk.asyn;

import com.github.chengzhx76.jdk.util.ToolUtils;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/22 11:26
 * @Version 3.0
 */
public class FutureTest {

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            10, 10,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        System.out.println("task-pool-放入队列失败-阻塞失败");
                    }
                }
            });

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("before " + Thread.currentThread().getName());

        Future<String> future = threadPool.submit(() -> {
            System.out.println("after " + Thread.currentThread().getName());
            ToolUtils.await(10);
            return "f-hello word";
        });

        System.out.println(future.get());

        threadPool.execute(() -> System.out.println("tp-hello world"));


        FutureTask<String> futureTask = new FutureTask<>(() -> {
            ToolUtils.await(3);
            return "ft-hello world";
        });

        threadPool.submit(futureTask);


        System.out.println(futureTask.get());

        System.out.println("hello world");
    }

}
