package com.github.chengzhx76.dubbo.unit.test.common;

import org.apache.dubbo.common.bytecode.Wrapper;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/20 9:38
 * @Version 3.0
 */
public class WrapperTest {
    public static void main(String[] args) throws Exception {
        test01();
    }

    public static void test01() throws Exception {
        Wrapper w = Wrapper.getWrapper(I1.class);
//        Wrapper w = Wrapper.getWrapper(I0.class);
        String[] ns = w.getDeclaredMethodNames();
        for (String n : ns) {
            System.out.println(n);
        }
        ns = w.getMethodNames();
        for (String n : ns) {
            System.out.println(n);
        }

        Object obj = new Impl1();
        System.out.println(w.getPropertyValue(obj, "name"));

        w.setPropertyValue(obj, "name", "changed");
        System.out.println(w.getPropertyValue(obj, "name"));

        w.invokeMethod(obj, "hello", new Class<?>[]{String.class}, new Object[]{"qianlei"});
    }

    public static class Impl1 implements I1 {
        private String name = "you name";

        private float fv = 0;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void hello(String name) {
            System.out.println("hello " + name);
        }

        public int showInt(int v) {
            return v;
        }

        public float getFloat() {
            return fv;
        }

        public void setFloat(float f) {
            fv = f;
        }
    }
}

/*
org.apache.dubbo.common.bytecode.Wrapper0

public static String[] pns;
public static Map pts;
public static String[] mns;
public static String[] dmns;
public static Class[] mts0;

public String[] getPropertyNames(){ return pns; }
public boolean hasProperty(String n){ return pts.containsKey($1); }
public Class getPropertyType(String n){ return (Class)pts.get($1); }
public String[] getMethodNames(){ return mns; }
public String[] getDeclaredMethodNames(){ return dmns; }

c1:
public void setPropertyValue(Object o, String n, Object v){
  com.github.chengzhx76.dubbo.unit.test.common.I0 w;
  try{
    w = ((com.github.chengzhx76.dubbo.unit.test.common.I0)$1);
  }catch(Throwable e){
    throw new IllegalArgumentException(e);
  }
  throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \""+$2+"\" field or setter method in class com.github.chengzhx76.dubbo.unit.test.common.I0.");
}

c2:
public Object getPropertyValue(Object o, String n){
  com.github.chengzhx76.dubbo.unit.test.common.I0 w;
  try{
    w = ((com.github.chengzhx76.dubbo.unit.test.common.I0)$1);
  }catch(Throwable e){
    throw new IllegalArgumentException(e);
  }
  if($2.equals("name") ){
    return ($w)w.getName();
  }
  throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \""+$2+"\" field or setter method in class com.github.chengzhx76.dubbo.unit.test.common.I0.");
}


c3:
public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException{
  com.github.chengzhx76.dubbo.unit.test.common.I0 w;
  try{
    w = ((com.github.chengzhx76.dubbo.unit.test.common.I0)$1);
  }catch(Throwable e){
    throw new IllegalArgumentException(e);
  }
  try{
    if("getName".equals($2) && $3.length == 0) {
      return ($w)w.getName();
    }
  } catch(Throwable e) {
    throw new java.lang.reflect.InvocationTargetException(e);
  }
  throw new org.apache.dubbo.common.bytecode.NoSuchMethodException("Not found method \""+$2+"\" in class com.github.chengzhx76.dubbo.unit.test.common.I0.");
}
 */