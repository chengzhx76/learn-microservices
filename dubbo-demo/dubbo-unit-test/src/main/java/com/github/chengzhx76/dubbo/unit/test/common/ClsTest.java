package com.github.chengzhx76.dubbo.unit.test.common;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/20 9:57
 * @Version 3.0
 */
public class ClsTest {
    public static void main(String[] args) {
//        System.out.println(MyEnum.A.getClass());
//        System.out.println(MyEnum.A.getDeclaringClass());
        System.out.println(new Cls().getClass());
        System.out.println(new Cls().getClass().getDeclaringClass());
    }
}
