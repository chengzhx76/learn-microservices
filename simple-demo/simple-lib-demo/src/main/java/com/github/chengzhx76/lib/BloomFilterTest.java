package com.github.chengzhx76.lib;

import cn.hutool.bloomfilter.BitMapBloomFilter;

/**
 * @Description
 * @Author admin
 * @Date 2020/10/30 13:12
 * @Version 3.0
 */
public class BloomFilterTest {
    public static void main(String[] args) {

        // 初始化
        BitMapBloomFilter filter = new BitMapBloomFilter(10);
        filter.add("123");
        filter.add("abc");
        filter.add("ddd");

        // 查找
        filter.contains("abc");

    }
}
