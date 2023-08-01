package com.github.chengzhx76.dubbo.spi.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
@SPI("china")
public interface EarthPeople {

    void say();

    @Adaptive({"p", "e"})
//    @Adaptive
    void eat(URL url);

}
