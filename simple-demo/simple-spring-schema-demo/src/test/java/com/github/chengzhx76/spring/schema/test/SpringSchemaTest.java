package com.github.chengzhx76.spring.schema.test;

import com.github.chengzhx76.spring.schema.bean.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Desc:
 * Author: 光灿
 * Date: 2019/5/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app.xml" })
public class SpringSchemaTest {

    @Autowired
    private People people;

    @Test
    public void test001() {
        String name = people.getName();
        System.out.println(name);
        int age = people.getAge();
        System.out.println(age);
        int sex = people.getSex();
        System.out.println(sex);
    }

}
