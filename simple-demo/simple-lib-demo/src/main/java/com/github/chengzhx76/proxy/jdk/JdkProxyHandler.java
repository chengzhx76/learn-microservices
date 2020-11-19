package com.github.chengzhx76.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 16:27
 * @Version 3.0
 */
public class JdkProxyHandler implements InvocationHandler {

    // 代理对象
    private Object target;

    public JdkProxyHandler(Object target) {
        this.target = target;
    }

    // 代理方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("========>ProxyHandler.invoke");
        return method.invoke(target, args);
    }

}
