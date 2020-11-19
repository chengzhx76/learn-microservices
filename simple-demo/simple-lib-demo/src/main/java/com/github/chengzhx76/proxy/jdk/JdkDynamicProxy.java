package com.github.chengzhx76.proxy.jdk;

import com.github.chengzhx76.proxy.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 16:47
 * @Version 3.0
 */
public class JdkDynamicProxy<T> {

    public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }
    public static <T> T newProxyInstance(InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{ Subject.class }, handler);
    }

}
