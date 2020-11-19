package com.github.chengzhx76.proxy;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 16:25
 * @Version 3.0
 */
public class RealSubject implements Subject {
    @Override
    public String sayHello(String name) {
        System.out.println("====> " + name);
        return name;
    }
}
