package com.github.chengzhx76.jdk.base;

/**
 * Desc:
 * Author: 光灿
 * Date: 2020/4/23
 */
public class TestMove {

    /*
        java中有三种移位运算符

        <<      :     左移运算符，num << n,相当于num乘以2的n次方

        >>      :     右移运算符，num >> n,相当于num除以2的n次方

        >>>    :     无符号右移，忽略符号位，空位都以0补齐

        对于正数而言，>>和>>>没区别。

        java运算符 与（&）、非（~）、或（|）、异或（^）
        1.位异或运算（^）

        运算规则是：两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1。

        2、位与运算符（&） 5&2 = 1 相当于取模操作 比 % 快

        运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。

        3、位或运算符（|）

        运算规则：两个数都转为二进制，然后从高位开始比较，两个数只要有一个为1则为1，否则就为0。

        4、位非运算符（~）

        运算规则：如果位为0，结果是1，如果位为1，结果是0.
     */

    /*
        例子：https://icode9.com/content-1-1201260.html
        负数的二进制是按正数的二进制的反码后补码表示的：
        1.先取到正数的二进制
        2.在进行取反
        3.再进行补码（取反后+1）
     */

    /*
        |=、&=、^=

        例子：https://www.cnblogs.com/qubaba/p/11558127.html
     */

    /*
        开源软件中关于2的算法

        例子：https://zhuanlan.zhihu.com/p/354468257
     */

    public static void main(String[] args) {
        /*int i = 10;
        show(i);
        show(-i);*/

        System.out.println(17 & 15);
    }



    public static void right() {
        int aValue = 10;
        show(aValue);
        int leftMoveValue1 =aValue >>1; //右边移1位
        show(leftMoveValue1);
        int leftMoveValue2 =aValue >>2; //右移2位
        show(leftMoveValue2);
        int leftMoveValue3 =aValue >>3;//右移3位
        show(leftMoveValue3);
    }
    public static void show(int number) {
//        System.out.println(number);
        System.out.println(formatString(Integer.toBinaryString(number), 32, "0"));
    }

    public static String formatString(String str, int len, String holder) {
        StringBuilder totalHolder = new StringBuilder();
        if (str.length() < len) {
            for (int i = 0; i < len - str.length(); i++) {
                totalHolder.append(holder);
            }
        }
        return String.format("%s%s", totalHolder, str);
    }

}
