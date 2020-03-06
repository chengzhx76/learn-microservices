package com.github.chengzhx76.dubbo.demo;

import java.util.concurrent.CompletableFuture;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/3/4
 */
public interface DemoService {
    String sayHello(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}
