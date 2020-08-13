package com.github.chengzhx76.demo.configcenter;

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.metadata.report.identifier.KeyTypeEnum;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static org.apache.dubbo.common.constants.CommonConstants.PATH_SEPARATOR;

/**
 * @Description
 * https://github.com/apache/dubbo-samples/blob/master/java/dubbo-samples-metadata-report/dubbo-samples-metadata-report-configcenter/src/main/java/org/apache/dubbo/samples/metadatareport/configcenter/ZKTools.java
 * @Author admin
 * @Date 2020/8/13 20:03
 * @Version 3.0
 */
public class ZKTools {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static CuratorFramework client;

    static {
        initClient();
    }

    public static void main(String[] args) throws Exception {
        generateDubboProperties();
    }

    public static void generateDubboProperties() {
        generateDubboPropertiesForGlobal();
        generateDubboPropertiesForProvider();
        generateDubboPropertiesForConsumer();
    }

    private static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static void generateDubboPropertiesForGlobal() {
        String str = "dubbo.registry.address=zookeeper://" + zookeeperHost + ":2181\n" +
                "dubbo.metadata-report.address=zookeeper://" + zookeeperHost + ":2181\n" +
                "#global config for consumer\n" +
                "dubbo.consumer.timeout=6000\n" +
                "#global config for provider\n" +
                "dubbo.protocol.port=20831\n" +
                "dubbo.provider.id.timeout=5000";

        System.out.println(str);

        try {
            String path = "/dubbo/config/dubbo/dubbo.properties";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateDubboPropertiesForConsumer() {
        String str = "dubbo.consumer.timeout=6666";

        System.out.println(str);

        try {
            String path = "/dubbo/config/metadatareport-configcenter-consumer/dubbo.properties";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateDubboPropertiesForProvider() {
        String str = "dubbo.protocol.threadpool=fixed\n" +
                "dubbo.protocol.threads=100";

        System.out.println(str);

        try {
            String path = "/dubbo/config/metadatareport-configcenter-provider/dubbo.properties";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setData(String path, String data) throws Exception {
        client.setData().forPath(path, data.getBytes());
    }

    private static String pathToKey(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        return path.replace("/dubbo/config/", "").replaceAll("/", ".");
    }

    private static void removeInterfaceData(Class interfaceClazz) {
        try {
            client.delete().forPath("/dubbo/" + interfaceClazz.getName().replaceAll("\\.", "/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMetadata(String root, String serviceInterface, String version, String group,
                                     String side, String app) throws Exception {
        String path = getNodePath(root, new MetadataIdentifier(serviceInterface, version, group, side, app));
        return new String(client.getData().forPath(path));
    }

    public static String getNodePath(String root, MetadataIdentifier metadataIdentifier) {
        return toRootDir(root) + metadataIdentifier.getUniqueKey(KeyTypeEnum.PATH);
    }

    private static String toRootDir(String root) {
        if (root.equals(PATH_SEPARATOR)) {
            return root;
        }
        return root + PATH_SEPARATOR;
    }

    private static void removeGlobalConfig() {
        try {
            client.delete().forPath("/dubbo/config/dubbo/dubbo.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
