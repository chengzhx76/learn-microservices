package com.github.chengzhx76.embedded.zookeeper;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Desc: 嵌入式的 Zookeeper
 * https://github.com/weibocom/motan/blob/master/motan-registry-zookeeper/src/test/java/com/weibo/api/motan/registry/zookeeper/EmbeddedZookeeper.java
 * https://blog.csdn.net/Andy2019/article/details/73379978
 * https://github.com/cosu/zookeeperdemo/blob/0e95c42338ff0ce54cb24f7f0fd8448fb2f9ff72/src/main/java/ro/cosu/ZookeeperRunner.java
 * Author: 光灿
 * Date: 2020/4/11
 * ！！Dubbo 的用法
 * https://github.com/apache/dubbo/blob/master/dubbo-remoting/dubbo-remoting-zookeeper/src/test/java/org/apache/dubbo/remoting/zookeeper/curator/CuratorZookeeperClientTest.java
 *
 */
public class EmbeddedZookeeper {


    // https://www.journaldev.com/1069/threadpoolexecutor-java-thread-pool-example-executorservice
//    ThreadPoolExecutor executorPool = new ThreadPoolExecutor(1, 1, 10,
//            TimeUnit.SECONDS,
//            new ArrayBlockingQueue<>(1),
//            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


    // Apache ZooKeeper 服务启动源码解释
    // https://www.ibm.com/developerworks/cn/opensource/os-cn-zookeeper-code/index.html
    // cheng
    private void start() throws IOException, QuorumPeerConfig.ConfigException {

//        Files.createTempDirectory("");

        Properties properties = new Properties();
        InputStream stream = getClass().getResourceAsStream("/zoo.cfg");
        properties.load(stream);

        QuorumPeerConfig quorumConfig = new QuorumPeerConfig();
        quorumConfig.parseProperties(properties);
        stream.close();

        ZooKeeperServerMain zookeeperServer = new ZooKeeperServerMain();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.readFrom(quorumConfig);

        /*executorPool.execute(() -> {
            try {
                zookeeperServer.runFromConfig(serverConfig);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/


        zookeeperServer.runFromConfig(serverConfig);

        System.in.read();

    }

    public static void main(String[] args) throws IOException, QuorumPeerConfig.ConfigException {
        EmbeddedZookeeper zookeeperServer = new EmbeddedZookeeper();
        zookeeperServer.start();
    }

}
