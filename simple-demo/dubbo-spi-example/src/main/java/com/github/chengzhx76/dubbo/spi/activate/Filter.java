package com.github.chengzhx76.dubbo.spi.activate;

import org.apache.dubbo.common.extension.SPI;

/**
 * @Description
 * @Author admin
 * @Date 2020/8/12 15:13
 * @Version 3.0
 */
@SPI
public interface Filter {

    void doFilter(String test);
}
