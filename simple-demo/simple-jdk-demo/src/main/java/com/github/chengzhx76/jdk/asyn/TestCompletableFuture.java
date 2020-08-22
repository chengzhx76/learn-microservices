package com.github.chengzhx76.jdk.asyn;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/**
 * @Description
 * https://www.jianshu.com/p/6bac52527ca4
 * https://mp.weixin.qq.com/s?__biz=MzIwMzY1OTU1NQ==&mid=2247493364&idx=1&sn=5c6e84401232b7b92c815db685ccc5dc&chksm=96cea6b8a1b92fae247e4652183c6363663f03c8088fb881774db65de411a8bff036eeee9f64&mpshare=1&scene=24&srcid=0730Do0GSJK46nIsfFzMhp6M&sharer_sharetime=1596072228538&sharer_shareid=8b38221e975e7a9e6ef3c6ed6de0af5f#rd
 * @Author admin
 * @Date 2020/8/14 16:46
 * @Version 3.0
 */
public class TestCompletableFuture {
//    private static ExecutorService threadPool = Executors.newSingleThreadExecutor();

    protected final static AtomicInteger threadNum = new AtomicInteger(1);

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            1, 1,
            0L, TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    String name = "cheng-thread-" + threadNum.getAndIncrement();
                    Thread thread = new Thread(r, name);
//                    thread.setDaemon(true);
                    return thread;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        System.out.println("task-pool-放入队列失败-阻塞失败");
                    }
                }
            });

    public static void main(String[] args) {
        System.out.println("start===>" + LocalDateTime.now().toString());
        CompletableFuture<Boolean> startLM = startLm();

        // 拿到上一步的结果，不返回
        CompletableFuture<Void> tdLm = startLM.thenAccept(result -> {
            System.out.println("startLM-end===> " + result + " - " + LocalDateTime.now().toString());
            if (result) {
                CompletableFuture<String> createTD = null;
                try {
                    createTD = createTD();
                } catch (Exception e) {
                    System.err.println("throw createTD-> " + e.getMessage());
                    throw new ServiceException("throw ServiceException.createTD");
                }

                createTD.whenComplete((val, ex) -> {
                    if (ex != null) {
                        System.err.println("createTD-whenComplete-ex-> " + val + " - " + ex.getMessage());
                    } else {
                        System.out.println("createTD-whenComplete-ex-> " + val + " - ex is null");
                    }
                });

                CompletableFuture<String> installLM = installLM();

                installLM.whenComplete((val, ex) -> {
                    if (ex != null) {
                        System.err.println("installLM-whenComplete-ex-> " + val + " - " + ex.getMessage());
                    } else {
                        System.out.println("installLM-whenComplete-ex-> " + val + " - ex is null");
                    }
                });

                System.out.println("createTD-installLM-end===>" + LocalDateTime.now().toString());
                createTD.thenAcceptBoth(installLM, (__, ___) -> {
                        CompletableFuture<Void> instantiateTD = Instantiate();
                        instantiateTD.whenComplete((val, ex) -> {
                            if (ex != null) {
                                System.err.println("instantiateTD-whenComplete-ex-> " + val + " - " + ex.getMessage());
                            } else {
                                System.out.println("instantiateTD-whenComplete-ex-> " + val + " - ex is null");
                            }
                        });
                    })
                    .whenComplete((val, ex) -> {
                        if (ex != null) {
                            System.err.println("end-finish-whenComplete-ex-> " + val + " - " + ex.getMessage());
                        } else {
                            System.out.println("end-finish-whenComplete-ex-> " + val + " - ex is null");
                        }
                    });
            }
        });

        // 总结：在谁那抛出的异常谁处理，谁包裹的谁处理
        startLM.whenComplete((val, ex) -> {
            if (ex != null) {
            System.err.println("startLM-whenComplete-ex-> " + val + " - " + ex.getMessage());
            } else {
                System.out.println("startLM-whenComplete-ex-> " + val + " - ex is null");
            }
        });

        tdLm.whenComplete((val, ex) -> {
            if (ex != null) {
                System.err.println("tdLm-whenComplete-ex-> " + val + " - " + ex.getMessage());

                System.err.println("tdLm-whenComplete-ex-test1-> " + ex.getClass().getName());
                System.err.println("tdLm-whenComplete-ex-test2-> " + ex.getCause().getClass().getName());

            } else {
                System.out.println("tdLm-whenComplete-ex-> " + val + " - ex is null");
            }
        });

        /*startLM.exceptionally(ex -> {
            if (ex != null) {
                System.out.println("startLM-exceptionally-ex-> " + ex.getMessage());
            } else {
                System.out.println("startLM-exceptionally-ex-> ex is null");
            }
            return null;
        });*/
        /*tdLm.exceptionally(ex -> {
            if (ex != null) {
                System.out.println("tdLm-exceptionally-ex-> " + ex.getMessage());
            } else {
                System.out.println("tdLm-exceptionally-ex-> ex is null");
            }
            return null;
        });*/

        System.out.println("last===>" + LocalDateTime.now().toString());
//        await(100000);
    }

    private static CompletableFuture<Boolean> startLm() {
        if (1 == 1) {
//            throw new RuntimeException("startLm");
        }
        return CompletableFuture.supplyAsync(() -> {
            if (1 == 1) {
//                throw new RuntimeException("inner-startLm");
            }
            System.out.println("联盟启动 " + Thread.currentThread().getName());
            await(5);
            System.out.println("联盟启动-finish");
            return true;
        }, threadPool);
    }

    private static CompletableFuture<String> createTD() {
        if (1 == 1) {
            throw new RuntimeException("createTD");
        }
        return CompletableFuture.supplyAsync(() -> {
            if (1 == 1) {
//                throw new RuntimeException("inner-createTD");
            }
            System.out.println("创建通道 " + Thread.currentThread().getName());
            await(2);
            System.out.println("创建通道-finish");
            return "channelId";
        });
    }

    private static CompletableFuture<String> installLM() {
        if (1 == 1) {
//            throw new RuntimeException("installLM");
        }
        return CompletableFuture.supplyAsync(() -> {
            if (1 == 1) {
//                throw new RuntimeException("inner-installLM");
            }
            System.out.println("安装链码 " + Thread.currentThread().getName());
            await(3);
            System.out.println("安装链码-finish");
            return "ccId";
        });
    }

    private static CompletableFuture<Void> Instantiate() {
        if (1 == 1) {
//            throw new RuntimeException("Instantiate");
        }
        return CompletableFuture.runAsync(() -> {
            if (1 == 1) {
//                throw new RuntimeException("inner-Instantiate");
            }

            System.out.println("实例化 " + Thread.currentThread().getName());
            await(4);
            System.out.println("实例化-finish");
            System.out.println("end===>" + LocalDateTime.now().toString());
        });
    }

    /*private static BiConsumer<String, String> Instantiate() {
        if (1 == 1) {
//            throw new RuntimeException("Instantiate");
        }
        return (channelId, ccId) -> {

            if (1 == 1) {
                throw new RuntimeException("inner-Instantiate");
            }

            System.out.println("实例化 " + Thread.currentThread().getName());
            await(4);
            System.out.println("实例化-finish");
            System.out.println("end===>" + LocalDateTime.now().toString());
        };
    }*/

    private static BiConsumer<String, String> Instantiate = (channelId, ccId) -> {

        if (1 == 1) {
//            throw new RuntimeException();
        }

        System.out.println("实例化 " + Thread.currentThread().getName());
        await(7);
        System.out.println("实例化-finish");
        System.out.println("end===>" + LocalDateTime.now().toString());
    };


    private static void await(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
