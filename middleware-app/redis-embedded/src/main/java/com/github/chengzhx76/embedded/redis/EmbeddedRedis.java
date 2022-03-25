package com.github.chengzhx76.embedded.redis;

import redis.embedded.RedisServer;

/**
 * @author: Cheng
 * @create: 2022-03-25
 **/
public class EmbeddedRedis {
    public static void main(String[] args) {
        RedisServer redisServer = new RedisServer(6379);
        redisServer.start();

//        redisServer.stop();
    }
}
