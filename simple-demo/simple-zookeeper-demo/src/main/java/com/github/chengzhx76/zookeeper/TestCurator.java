package com.github.chengzhx76.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/30
 */
public class TestCurator {

    public static void main(String[] args) throws Exception {
        testCreate();
    }

    private static void testCreate() throws Exception {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
//                .connectString("180.76.183.68:2181")
                .retryPolicy(new RetryNTimes(1, 1000))
                .connectionTimeoutMs(1000);

        CuratorFramework client  = builder.build();

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                System.out.println("---------->" + state);
                if (state == ConnectionState.LOST) {
                } else if (state == ConnectionState.CONNECTED) {
                } else if (state == ConnectionState.RECONNECTED) {
                }
            }
        });
        client.start();



        //创建永久节点
//        client.create().forPath("/cheng","/test data 2".getBytes());
//        client.checkExists().forPath("/cheng");


//        String path = "/cheng/test/dubbo.properties";
        String path = "/dubbo/config/dubbo/dubbo.properties";
        /*String str = "dubbo.registry.address=zookeeper://127.0.0.1:2181\n"
                + "dubbo.registry.simplified=true\n"
                + "dubbo.metadata-report.address=zookeeper://127.0.0.1:2181\n"
                + "dubbo.application.qos.port=33333";

        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().forPath(path);
        }
        client.setData().forPath(path, str.getBytes());*/

        System.out.println("================get==================");
        String data = new String(client.getData().forPath(path));
        System.out.println(data);

//        System.out.println("===============deleted===================");
//        client.delete().forPath(path);

    }

}
