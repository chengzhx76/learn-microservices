package com.github.chengzhx76.dubbo.spi.activate;

import org.apache.dubbo.common.extension.Activate;

/**
 * @Description
 * @Author admin
 * @Date 2020/8/12 15:14
 * @Version 3.0
 */
@Activate(group = "provider", order = 2)
public class Test2Filter implements Filter {
    @Override
    public void doFilter(String test) {

    }
}
