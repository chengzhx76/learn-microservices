package com.github.chengzhx76.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/11 18:11
 * @Version 3.0
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition peopleBean = new RootBeanDefinition(People.class);
        registry.registerBeanDefinition("people", peopleBean);
        RootBeanDefinition duckBean = new RootBeanDefinition(Duck.class);
        registry.registerBeanDefinition("duck", duckBean);
    }
}
