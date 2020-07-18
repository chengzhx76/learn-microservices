package com.github.chengzhx76.netty.dns.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author admin
 * @Date 2020/7/18 18:34
 * @Version 3.0
 */
public class Test {


    public static void main(String[] args) throws UnknownHostException {
        String ip = "128.0.0.1";

        byte[] ipByte = ipToBytes(ip);
        for (byte b : ipByte) {
            System.out.println(b);
        }

        System.out.println(bytesToIp(ipByte));
    }

    public static byte[] ipToBytes(String ip) throws UnknownHostException {
        return InetAddress.getByName(ip).getAddress();
    }

    public static String bytesToIp(byte[] src) {
        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff)
                + "." + (src[3] & 0xff);
    }

}
