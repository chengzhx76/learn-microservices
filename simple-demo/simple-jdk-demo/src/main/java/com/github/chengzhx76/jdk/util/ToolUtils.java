package com.github.chengzhx76.jdk.util;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/22 11:30
 * @Version 3.0
 */
public class ToolUtils {
    public static void await(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
