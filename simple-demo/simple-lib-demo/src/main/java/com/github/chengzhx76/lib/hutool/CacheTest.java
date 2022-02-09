package com.github.chengzhx76.lib.hutool;

import cn.hutool.cache.impl.LFUCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/18 10:14
 * @Version 3.0
 */
public class CacheTest {

    public static void main(String[] args) {

        LFUCache<String, String> cache = new LFUCache<>(16);

        Map<String, String> map = new HashMap<>();
//        map.containsKey()

    }


}
