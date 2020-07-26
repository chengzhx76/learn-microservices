package com.github.chengzhx76.consumer;

import com.github.chengzhx76.dubbo.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/3/5
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //if (isClassic(args)) {
            runWithRefer();
        //} else {
        //    runWithBootstrap();
        //}
    }

    private static boolean isClassic(String[] args) {
        return args.length > 0 && "classic".equalsIgnoreCase(args[0]);
    }

    private static void runWithBootstrap() {
        //ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        //reference.setInterface(DemoService.class);
        //reference.setTimeout(10000);
//        reference.setGeneric("true"); // 声明为泛化接口 http://dubbo.apache.org/zh-cn/docs/user/demos/generic-reference.html
//        reference.setAsync(true);

        // 方法级别控制 http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-method.html
//        List<MethodConfig> methods = new ArrayList<>();
//        MethodConfig method = new MethodConfig();
//        method.setAsync(true); // 默认引用 reference 的
//        methods.add(method);
//
//        reference.setMethods(methods);

        //DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        //bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
        //        .registry(new RegistryConfig("zookeeper://180.76.183.68:2181"))
        //        .reference(reference)
        //        .start();
        //
        //DemoService demoService = ReferenceConfigCache.getCache().get(reference);

        /* ---------------------- sync invoke ----------------------- */
        //logger.info("begin-->");
        //String message = demoService.sayHello("dubbo");
        //logger.info("end-->");
        //System.out.println(message);

        /* ---------------------- RpcContext async invoke ------------ */

//        // 此调用会立即返回null
//        String result = demoService.sayHello("dubbo");
//        System.out.println(result);
//
//        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
//        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
//        // 为Future添加回调
//        helloFuture.whenComplete((retValue, exception) -> {
//            if (exception == null) {
//                System.out.println(retValue);
//            } else {
//                exception.printStackTrace();
//            }
//        });
//
//        // or
//        CompletableFuture<String> resultFuture = RpcContext.getContext().asyncCall(() -> demoService.sayHello("oneway call request1"));
//
//        try {
//            result = resultFuture.get();
//            System.out.println(result);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }

        /* ---------------------- async invoke ----------------------- */
//        CompletableFuture<String> future = demoService.sayHelloAsync("async call request");
//        try {
//            result = future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println(result);
//        // 增加回调 callback
//        future.whenComplete((v, t) -> {
//            if (t != null) {
//                t.printStackTrace();
//            } else {
//                System.out.println("Response: " + v);
//            }
//        });
//        // 早于结果输出
//        System.out.println("Executed before response return.");

        /* ---------------------- generic invoke ---------------------- */
//        GenericService genericService = (GenericService) demoService;
//        Object genericInvokeResult = genericService.$invoke("sayHello", new String[] {
//                String.class.getName()
//                },
//                new Object[] { "dubbo generic invoke" });
//        System.out.println(genericInvokeResult);
    }

    private static void runWithRefer() {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        reference.setInterface(DemoService.class);
        DemoService service = reference.get();
        String message = service.sayHello("dubbo");
        System.out.println(message);
    }

}
