package com.github.chengzhx76.dubbo.spi;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
//@Adaptive
public class ChinaSpeak implements Speak {

    @Override
    public void say() {
        System.out.println("你好！");
    }
}
