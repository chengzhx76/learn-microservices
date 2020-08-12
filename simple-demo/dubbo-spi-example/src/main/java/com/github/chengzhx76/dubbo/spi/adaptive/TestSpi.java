package com.github.chengzhx76.dubbo.spi.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * Desc: http://dubbo.apache.org/zh-cn/blog/introduction-to-dubbo-spi.html
 * https://www.jianshu.com/p/bc523348f519
 * Author: 光灿
 * Date: 2020/6/26
 */
public class TestSpi {
    // 1.@Adaptive注解用在类上代表实现一个装饰类，通过 `getAdaptiveExtension()`方法直接获取
    // 2.@Adaptive注解用在扩展接口的方法上。表示该方法是一个自适应方法。Dubbo在为扩展点生成自适应实例时，
    //   如果方法有@Adaptive注解，会为该方法生成对应的代码。方法内部会根据方法的参数，来决定使用哪个扩展。
    //   如：@Adaptive({"p", "e"}) "p" 和 "e" 是 url 中的 key， 如果没指定则使用接口名 两个单词的转换成 "." EarthPeople=>earth.people
    //   如过 url 没指定 则直接获取 @SPI("china")中的china 和调用`getDefaultExtension()`效果一样，所以说url里的权重高些
    // 3.在类上有@Adaptive注解 在 url 中指定的就无效了
    // 结论：类上的@Adaptive > 方法上的@Adaptive > @SPI 注解

    /**
     * 1. 在类上加上@Adaptive注解的类，是最为明确的创建对应类型Adaptive类。所以他优先级最高。
     * 2. @SPI注解中的value是默认值，如果通过URL获取不到关于取哪个类作为Adaptive类的话，就使用这个默认值，当然如果URL中可以获取到，就用URL中的。
     * 3. 可以再方法上增加@Adaptive注解，注解中的value与链接中的参数的key一致，链接中的key对应的value就是spi中的name,获取相应的实现类。
     * @param args
     */

    public static void main(String[] args) {
//        Speak speak = ExtensionLoader.getExtensionLoader(Speak.class).getDefaultExtension();
        EarthPeople people = ExtensionLoader.getExtensionLoader(EarthPeople.class).getAdaptiveExtension();
//        people.say();

//        URL url = URL.valueOf("dubbo://127.0.0.1:20880?earth.people=america");
        URL url = URL.valueOf("dubbo://127.0.0.1:20880?e=america");

        people.eat(url);
    }

}
