package com.github.chengzhx76.proxy.javassist;

import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 17:32
 * @Version 3.0
 */
public class JavassistProxyHandler implements MethodHandler {
    final Object target;

    public JavassistProxyHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        System.out.println("======>JavassistProxyHandler.invoke");
        return method.invoke(target, args);
    }
}
