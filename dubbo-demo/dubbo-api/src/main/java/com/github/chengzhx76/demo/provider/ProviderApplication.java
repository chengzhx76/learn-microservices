package com.github.chengzhx76.demo.provider;

import com.github.chengzhx76.dubbo.demo.DemoService;
import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.metadata.report.MetadataReport;
import org.apache.dubbo.metadata.store.zookeeper.ZookeeperMetadataReport;

import java.util.ArrayList;
import java.util.List;
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
        List<ConfigCenterConfig> configCenters = new ArrayList<>();
        ConfigCenterConfig zkConfigCenter = new ConfigCenterConfig();
        zkConfigCenter.setAddress("zookeeper://127.0.0.1:2181");
        configCenters.add(zkConfigCenter);
        /*ConfigCenterConfig zk2ConfigCenter = new ConfigCenterConfig();
        zk2ConfigCenter.setAddress("zookeeper://127.0.0.2:2182");
        configCenters.add(zk2ConfigCenter);*/


        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        registryConfig.setSimplified(true);

        MetadataReportConfig metadataReportConfig = new MetadataReportConfig("zookeeper://127.0.0.1:2181");

        bootstrap.application(new ApplicationConfig("dubbo-demo-api-provider"))
//                .registry(new RegistryConfig("zookeeper://180.76.183.68:2181"))
                .registry(registryConfig)
                .configCenters(configCenters)
                .metadataReport(metadataReportConfig)
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
