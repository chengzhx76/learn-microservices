package com.github.chengzhx76.proxy;

import com.github.chengzhx76.proxy.javassist.JavassistDynamicProxy;
import com.github.chengzhx76.proxy.javassist.JavassistProxyHandler;
import com.github.chengzhx76.proxy.jdk.JdkDynamicProxy;
import com.github.chengzhx76.proxy.jdk.JdkProxyHandler;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 16:35
 * @Version 3.0
 */
public class DynamicProxyTest {

    public static void main(String[] args) throws Exception {
        testJavassistProxy();

    }

    public static void testJdkProxy() {
        Subject subject = new RealSubject();
        Subject proxy1 = JdkDynamicProxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new JdkProxyHandler(subject));
        Subject proxy2 = JdkDynamicProxy.newProxyInstance(new JdkProxyHandler(subject));

        System.out.println(subject.sayHello("subject"));
        System.out.println(proxy1.sayHello("proxy1"));
        System.out.println(proxy2.sayHello("proxy2"));
    }

    public static void testJavassistProxy() throws Exception {
        Subject subject = new RealSubject();
        Subject proxy1 = JavassistDynamicProxy.newProxyInstance(Subject.class, new JavassistProxyHandler(subject));

        Subject proxy2 = JavassistDynamicProxy.newProxyInstanceBytecode(Subject.class);

        System.out.println(proxy1.sayHello("proxy1"));
        System.out.println(proxy2.sayHello("proxy2"));
    }


}
