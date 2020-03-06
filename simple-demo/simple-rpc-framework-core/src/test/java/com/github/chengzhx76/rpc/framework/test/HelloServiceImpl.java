package com.github.chengzhx76.rpc.framework.test;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/26
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
