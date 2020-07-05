package com.github.chengzhx76.jdk.asyn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;

/**
 * @Description
 * @Author admin
 * @Date 2020/5/26 9:58
 * @Version 3.0
 */
public class LearnCompletableFuture {


    public static void main(String[] args) {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Hello world")
                .thenApply(s -> s + " cheng")
                .thenApply(String::toUpperCase);

        System.out.println(f0.join());

        // 对于简单的并行任务，你可以通过“线程池+Future”的方案来解决；如果任务之间有聚合关系，无论是AND聚合还是OR聚合，
        // 都可以通过CompletableFuture来解决；而批量的并行任务，则可以通过CompletionService来解决。
//        CompletionService<String> service = new ExecutorCompletionService<String>();


    }

}
