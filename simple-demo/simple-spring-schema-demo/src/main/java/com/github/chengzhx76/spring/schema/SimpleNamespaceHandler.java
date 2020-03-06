package com.github.chengzhx76.spring.schema;

import com.github.chengzhx76.spring.schema.bean.People;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Desc:NamespaceHandler用于解析我们自定义名字空间下的所有元素，目前我们要解析上面的my:car元素。 SimpleNamespaceHandler 里面只有3个方法：
 * init() 会在NamespaceHandler初始化的时候被调用。
 * BeanDefinition parse(Element, ParserContext) - 当Spring遇到一个顶层元素的时候被调用。
 * BeanDefinitionHolder decorate(Node, BeanDefinitionHolder, ParserContext) - 当Spring遇到一个属性或嵌套元素的时候调用.
 * Author: 光灿
 * Date: 2019/5/18
 */
public class SimpleNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        // "people"对应于xsd文件里面的<xsd:element name="people">
        // 这里的意思是告诉spring使用UserBeanDefinitionParser解析器解析user这个元素
        registerBeanDefinitionParser("people", new SimpleBeanDefintionParser(People.class));
    }
}
