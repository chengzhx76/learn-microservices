package com.github.chengzhx76.dubbo3x.demo.provider;

import com.github.chengzhx76.dubbo3x.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Cheng
 * @create: 2022-02-10
 **/
public class BaseProvider {
   public static void main(String[] args) {
         /*ServiceConfig<DemoService> service = new ServiceConfig<>();
            service.setApplication(new ApplicationConfig("first-dubbo-provider"));
            service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
            service.setInterface(DemoService.class);
            service.setRef(new DemoServiceImpl());
            service.export();

            System.out.println("dubbo service started");
            new CountDownLatch(1).await();*/
       startWithBootstrap();
    }


    private static void startWithBootstrap() {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        List<ConfigCenterConfig> configCenters = new ArrayList<>();
        ConfigCenterConfig zkConfigCenter = new ConfigCenterConfig();
        zkConfigCenter.setAddress("zookeeper://127.0.0.1:2181");
        configCenters.add(zkConfigCenter);
        /*ConfigCenterConfig zk2ConfigCenter = new ConfigCenterConfig();
        zk2ConfigCenter.setAddress("zookeeper://127.0.0.2:2182");
        configCenters.add(zk2ConfigCenter);*/
        bootstrap.application(new ApplicationConfig("dubbo3.x-demo-api-provider"))
//                .registry(new RegistryConfig("zookeeper://180.76.183.68:2181"))
//                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .configCenters(configCenters)
                .service(service)
                .start()
                .await();

//        https://github.com/apache/dubbo-samples/blob/master/java/dubbo-samples-metadata-report/dubbo-samples-metadata-report-configcenter/src/main/java/org/apache/dubbo/samples/metadatareport/configcenter/ZKTools.java
    }

}
