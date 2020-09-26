package com.github.chengzhx76.jdk.loader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author admin
 * @Date 2020/9/26 11:30
 * @Version 3.0
 */
public class HowswapLoadTest {
    static ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {

        scheduled.scheduleAtFixedRate(() -> {
            try {
                // 每次都创建出一个新的类加载器
                CustomizeClassLoader hotSwapClsLoader = new CustomizeClassLoader(
                        "E:\\idea-workspace\\learn-microservices\\zloader",
//                        new String[]{"Foo.class"}
                        new String[]{"simple-jdk-other-1.0-SNAPSHOT.jar"}
                        );
//                Class cls = hotSwapClsLoader.loadClass("Foo");
                Class<?> cls = Class.forName("Foo", true, hotSwapClsLoader);
                Object foo = cls.newInstance();
//                Method method = foo.getClass().getMethod("sayHello", new Class<?>[]{});
                Method method = foo.getClass().getMethod("sayHi", new Class<?>[]{});
                method.invoke(foo, new Object[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("======================> " + LocalDateTime.now());
        }, 0, 5, TimeUnit.SECONDS);

    }
}
