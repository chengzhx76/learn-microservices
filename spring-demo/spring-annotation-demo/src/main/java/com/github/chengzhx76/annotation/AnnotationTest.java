package com.github.chengzhx76.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/11 17:59
 * @Version 3.0
 */
public class AnnotationTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Cat cat = context.getBean(Cat.class);
        cat.sayHi();
        Dog dog = context.getBean(Dog.class);
        dog.sayHi();
        Sheep sheep = context.getBean(Sheep.class);
        sheep.sayHi();
        Duck duck = context.getBean("duck", Duck.class);
        duck.sayHi();
        People people = context.getBean(People.class);
        people.sayHi();
    }
}
