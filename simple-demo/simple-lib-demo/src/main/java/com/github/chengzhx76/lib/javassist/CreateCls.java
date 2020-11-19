package com.github.chengzhx76.lib.javassist;

import javassist.*;

/**
 * @Description
 * javassist使用全解析
 * https://www.cnblogs.com/rickiyang/p/11336268.html
 * @Author admin
 * @Date 2020/11/19 9:54
 * @Version 3.0
 */
public class CreateCls {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass stringType = pool.get("java.lang.String");
//        CtClass integerType = pool.get("java.lang.Integer");
        CtClass intType = pool.get("int");

        // 1.创建类
        CtClass cls = pool.makeClass("com.github.chengzhx76.javassist.dynamic.People");
        // 2.创建字段
        CtField nameField = new CtField(stringType, "name", cls);
        CtField ageField = new CtField(intType, "age", cls);
        CtField ctField = CtField.make("private String weight=\"3kg\";", cls);

        // 3.设置字段访问属性
        nameField.setModifiers(Modifier.PRIVATE);
        ageField.setModifiers(Modifier.PRIVATE);
        // 4.给字段设置默认值
        cls.addField(nameField, CtField.Initializer.constant("cheng"));
        cls.addField(ageField, CtField.Initializer.constant(18));
        cls.addField(ctField);
        // 5.设置get、set方法
        cls.addMethod(CtNewMethod.getter("getName", nameField));
        cls.addMethod(CtNewMethod.setter("setName", nameField));
        cls.addMethod(CtNewMethod.getter("getAge", ageField));
        cls.addMethod(CtNewMethod.setter("setAge", ageField));

        // 6.添加无参构造
        CtConstructor constructor = new CtConstructor(new CtClass[]{}, cls);
        constructor.setBody("{ name = \"by cheng\";}");
        cls.addConstructor(constructor);
        // 7.添加有参的构造方法
        constructor = new CtConstructor(new CtClass[]{ stringType }, cls);
        // $0=this / $1,$2,$3... 代表方法参数
        constructor.setBody("{$0.name = $1;}");
        cls.addConstructor(constructor);
        // 8.创建一个名字为 printName 方法，无参数，无返回值，输出值
        CtMethod printNameMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cls);
        printNameMethod.setModifiers(Modifier.PUBLIC);
        printNameMethod.setBody("{ System.out.println(name); }");
        cls.addMethod(printNameMethod);

        cls.writeFile("E:\\idea-workspace\\learn-microservices\\simple-demo\\simple-lib-demo\\src\\main\\java\\");
    }

}

/*
package com.github.chengzhx76.javassist;

import java.io.PrintStream;

public class dynamic
{
  private String weight = "3kg";
  private String name = "cheng";
  private int age = 18;

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public int getAge()
  {
    return this.age;
  }

  public void setAge(int paramInt)
  {
    this.age = paramInt;
  }

  public dynamic()
  {
    this.name = "by cheng";
  }

  public dynamic(String paramString)
  {
    this.name = paramString;
  }

  public void printName()
  {
    System.out.println(this.name);
  }
}
 */