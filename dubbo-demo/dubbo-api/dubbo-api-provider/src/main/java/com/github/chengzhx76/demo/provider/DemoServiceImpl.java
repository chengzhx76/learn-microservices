package com.github.chengzhx76.demo.provider;

import com.github.chengzhx76.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/3/4
 */
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("begin-->");
        await();
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        logger.info("end-->");
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();

//        final AsyncContext asyncContext = RpcContext.startAsync();
//        new Thread(() -> {
//            // 如果要使用上下文，则必须要放在第一句执行
//            asyncContext.signalContextSwitch();
//            await();
//            // 写回响应
//            asyncContext.write("Hello " + name + ", response from provider.");
//        }).start();
//        return null;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {

        RpcContext savedContext = RpcContext.getContext();

        return CompletableFuture.supplyAsync(() -> {
            System.out.println(savedContext.getAttachment("consumer-key1"));
            await();
            return "async response from provider.";
        });
    }

    private void await() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
