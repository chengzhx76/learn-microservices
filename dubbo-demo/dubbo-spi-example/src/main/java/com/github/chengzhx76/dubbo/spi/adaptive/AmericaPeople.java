package com.github.chengzhx76.dubbo.spi.adaptive;

import org.apache.dubbo.common.URL;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
public class AmericaPeople implements EarthPeople {

    @Override
    public void say() {
        System.out.println("hello world!");
    }

    @Override
    public void eat(URL url) {
        System.out.println("牛排");
    }
}
