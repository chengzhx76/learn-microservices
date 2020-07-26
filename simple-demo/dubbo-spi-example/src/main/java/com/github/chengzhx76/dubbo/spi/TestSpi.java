package com.github.chengzhx76.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/6/26
 */
public class TestSpi {


    public static void main(String[] args) {
        //Speak speak = ExtensionLoader.getExtensionLoader(Speak.class).getDefaultExtension();
        Speak speak = ExtensionLoader.getExtensionLoader(Speak.class).getAdaptiveExtension();
        speak.say();
    }

}
