package com.github.chengzhx76.dubbo.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
@SPI("china")
public interface Speak {

    void say();

}
