package com.github.chengzhx76.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description
 * 1.直接导入普通的 Java 类
 * 可以看到我们顺利的从 IOC 容器中获取到了 Circle 对象，证明我们在配置类中导入的 Circle 类，确实被声明为了一个 Bean。
 * @Author admin
 * @Date 2020/11/11 17:56
 * @Version 3.0
 */

/**
 * 1.直接导入普通的 Java 类
 * 可以看到我们顺利的从 IOC 容器中获取到了 Circle 对象，证明我们在配置类中导入的 Circle 类，确实被声明为了一个 Bean。
 * 2.配合自定义的 ImportSelector 使用
 * ImportSelector 是一个接口，该接口中只有一个 selectImports 方法，用于返回全类名数组。所以利用该特性我们可以给容器动态导入 N 个 Bean。
 * 3.配合 ImportBeanDefinitionRegistrar 使用
 * ImportBeanDefinitionRegistrar 也是一个接口，它可以手动注册bean到容器中，从而我们可以对类进行个性化的定制。(需要搭配 @Import 与 @Configuration 一起使用。）
 */
//@Import({Cat.class})
//@Import({Cat.class, MyImportSelector.class})
@Import({Cat.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
public class Config {

}
