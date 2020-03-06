package com.github.chengzhx76.spring.schema.bean;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/18
 */
public class People {

    private String name;
    private int age;
    private int sex;

    public String getName() {
        return name;
    }

    public People setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public People setAge(int age) {
        this.age = age;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public People setSex(int sex) {
        this.sex = sex;
        return this;
    }
}
