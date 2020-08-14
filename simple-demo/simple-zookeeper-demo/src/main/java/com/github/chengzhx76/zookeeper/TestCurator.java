package com.github.chengzhx76.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
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
//                .connectString("localhost:2181")
                .connectString("180.76.183.68:2181")
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
        client.create().forPath("/cheng","/test data 2".getBytes());
        client.checkExists().forPath("/cheng");

    }

}
