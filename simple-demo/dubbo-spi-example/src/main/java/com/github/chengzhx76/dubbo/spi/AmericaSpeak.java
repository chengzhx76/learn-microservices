package com.github.chengzhx76.dubbo.spi;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
public class AmericaSpeak implements Speak {

    @Override
    public void say() {
        System.out.println("hello world!");
    }
}
