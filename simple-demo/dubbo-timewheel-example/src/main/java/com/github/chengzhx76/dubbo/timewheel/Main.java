package com.github.chengzhx76.dubbo.timewheel;

import org.apache.dubbo.common.timer.HashedWheelTimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author: Cheng
 * @create: 2021-11-29
 **/
public class Main {

    /*
    tickDuration
    每个方格代表的时间

    timeUnit
    tickDuration的时间单位

    ticksPerWheel
    就是轮子一共有多个格子，即要多少个tick才能走完这个wheel一圈。
     */

    static String pattern = "HH:mm:ss";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public static void main(String[] args) throws InterruptedException {
        //定义一个HashedWheelTimer，有16个格的轮子，每一秒走一个一个格子
        HashedWheelTimer timer = new HashedWheelTimer(2, TimeUnit.SECONDS, 16);
        timer.newTimeout(timeout -> System.out.println(formatter.format(LocalDateTime.now()) + "_1s delay"), 1, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> System.out.println(formatter.format(LocalDateTime.now()) + "_10s delay"), 10, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> System.out.println(formatter.format(LocalDateTime.now()) + "_11s delay"), 11, TimeUnit.SECONDS);
//        TimeUnit.SECONDS.sleep(20);
    }

}
