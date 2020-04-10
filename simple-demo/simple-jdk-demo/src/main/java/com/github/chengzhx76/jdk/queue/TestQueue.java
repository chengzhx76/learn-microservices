package com.github.chengzhx76.jdk.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/10 11:19
 * @Version 3.0
 */
public class TestQueue {

    static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        queue.add("1");
    }

}
