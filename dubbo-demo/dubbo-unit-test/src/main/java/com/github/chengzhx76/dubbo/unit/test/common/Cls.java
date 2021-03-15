package com.github.chengzhx76.dubbo.unit.test.common;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/20 10:00
 * @Version 3.0
 */
public class Cls {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class InnerCls {
        private Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    public class StaticInnerCls {
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
