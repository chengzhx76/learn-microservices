package com.github.chengzhx76.dubbo.unit.test.common;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/20 9:47
 * @Version 3.0
 */
public interface I1 extends I0 {
    void setName(String name);

    void hello(String name);

    int showInt(int v);

    float getFloat();

    void setFloat(float f);
}
