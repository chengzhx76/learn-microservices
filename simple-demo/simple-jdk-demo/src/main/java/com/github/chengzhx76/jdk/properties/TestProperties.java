package com.github.chengzhx76.jdk.properties;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @Description
 * @Author admin
 * @Date 2020/8/22 15:51
 * @Version 3.0
 */
public class TestProperties {

    static String str = "dubbo.registry.address=zookeeper://127.0.0.1:2181\n"
            + "dubbo.registry.simplified=true\n"
            + "dubbo.metadata-report.address=zookeeper://127.0.0.1:2181\n"
            + "dubbo.application.qos.port=33333";

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(str));

        System.out.println(properties);
    }

}
