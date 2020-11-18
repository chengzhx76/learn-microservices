package com.github.chengzhx76.demo.provider;

import com.github.chengzhx76.dubbo.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.concurrent.CountDownLatch;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/3/4
 */
public class ProviderApplication {
    public static void main(String[] args) throws Exception {
        if (isClassic(args)) {
            startWithExport();
        } else {
            startWithBootstrap();
        }
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void startWithBootstrap() {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ConfigCenterConfig configCenter = new ConfigCenterConfig();
        configCenter.setAddress("zookeeper://127.0.0.1:2181");
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-provider"))
//                .registry(new RegistryConfig("zookeeper://180.76.183.68:2181"))
//                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .configCenter(configCenter)
                .service(service)
                .start()
                .await();

//        https://github.com/apache/dubbo-samples/blob/master/java/dubbo-samples-metadata-report/dubbo-samples-metadata-report-configcenter/src/main/java/org/apache/dubbo/samples/metadatareport/configcenter/ZKTools.java
    }

    private static void startWithExport() throws InterruptedException {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setApplication(new ApplicationConfig("dubbo-demo-api-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://180.76.183.68:2181"));
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
