package com.github.chengzhx76.mybatis.cache.entity;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/5 17:29
 * @Version 3.0
 */
public class StudentEntity {
    // 学号
    private Integer id;
    // 姓名
    private String name;
    // 年龄
    private Integer age;
    // 班级
    private String className;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
