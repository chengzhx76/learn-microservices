package com.github.chengzhx76.dubbo.spi.adaptive;

import org.apache.dubbo.common.URL;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
public class ChinaPeople implements EarthPeople {

    @Override
    public void say() {
        System.out.println("你好！");
    }

    @Override
    public void eat(URL url) {
        System.out.println("馒头");
    }
}
