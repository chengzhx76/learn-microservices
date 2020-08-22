package com.github.chengzhx76.dubbo.spi.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @Description
 * https://www.jianshu.com/p/bc523348f519
 * @Author admin
 * @Date 2020/8/12 15:21
 * @Version 3.0
 */
public class TestActivate {
    public static void main(String[] args) {
        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);

        URL url = new URL("", "", 3);
        url = url.addParameter("filterValue", "test5");
        List<Filter> list = extensionLoader.getActivateExtension(url, "", "provider");
        System.out.println(list);

        list = extensionLoader.getActivateExtension(url,"", "consumer");
        System.out.println(list);

        list = extensionLoader.getActivateExtension(url, "filterValue", "consumer");
        System.out.println(list);
    }
}
