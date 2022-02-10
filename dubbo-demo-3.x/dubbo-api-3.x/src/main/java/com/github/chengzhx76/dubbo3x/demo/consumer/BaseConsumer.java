package com.github.chengzhx76.dubbo3x.demo.consumer;

import com.github.chengzhx76.dubbo3x.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.EchoService;

/**
 * @author: Cheng
 * @create: 2022-02-10
 **/
public class BaseConsumer {
    public static void main(String[] args) {
        runWithRefer();

    }

    private static void runWithRefer() {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        reference.setInterface(DemoService.class);
        // http://dubbo.apache.org/zh/docs/v2.7/user/examples/reference-config-cache/
//        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
//        DemoService service = cache.get(reference);
        DemoService service = reference.get();
        String message = service.sayHi("dubbo");

        EchoService echoService = (EchoService) service;
        Object echo = echoService.$echo("hello dubbo");
        System.out.println(echo);

        System.out.println(message);

    }
}
