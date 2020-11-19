package com.github.chengzhx76.proxy.javassist;

import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * javassist使用全解析
 * https://www.cnblogs.com/rickiyang/p/11336268.html
 * @Author admin
 * @Date 2020/11/19 16:47
 * @Version 3.0
 */
public class JavassistDynamicProxy {

    public static <T> T newProxyInstance(Class<T> cls, MethodHandler methodHandler) throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[] { cls });
        Class<?> proxyCls = proxyFactory.createClass();
        T proxy = (T) proxyCls.newInstance();
        ((ProxyObject) proxy).setHandler(methodHandler);
        return proxy;
    }

    public static <T> T  newProxyInstanceBytecode(Class<T> proxyCls) throws Exception {
        ClassPool classPool  = new ClassPool(true);
        CtClass cls = classPool.makeClass(proxyCls.getName() + "$JavassistProxy");
        cls.addInterface(classPool.get(proxyCls.getName()));
        cls.addConstructor(CtNewConstructor.defaultConstructor(cls));
        cls.addMethod(CtNewMethod.make("public String sayHello(String name) { System.out.println(\"====>\" + $1); return name; }", cls));
        Class<?> pc = cls.toClass();
        return (T) pc.newInstance();
    }


}