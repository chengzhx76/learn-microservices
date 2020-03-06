package com.github.chengzhx76.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/18
 */
public class SimpleBeanDefintionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public SimpleBeanDefintionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        String sex = element.getAttribute("sex");
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        if (age != null && age.length() > 0) {
            beanDefinition.getPropertyValues().addPropertyValue("age", age);
        }
        if (sex != null && sex.length() > 0) {
            beanDefinition.getPropertyValues().addPropertyValue("sex", sex);
        }
        parserContext.getRegistry().registerBeanDefinition(name, beanDefinition);
        return beanDefinition;
    }
}
