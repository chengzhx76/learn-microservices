package com.github.chengzhx76.dubbo3x.demo.provider;

import com.github.chengzhx76.dubbo3x.demo.DemoService;

/**
 * @author: Cheng
 * @create: 2022-02-10
 **/
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
