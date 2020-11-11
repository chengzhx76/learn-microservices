package com.github.chengzhx76.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/11 18:06
 * @Version 3.0
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.github.chengzhx76.annotation.Dog", "com.github.chengzhx76.annotation.Sheep"};
    }
}
