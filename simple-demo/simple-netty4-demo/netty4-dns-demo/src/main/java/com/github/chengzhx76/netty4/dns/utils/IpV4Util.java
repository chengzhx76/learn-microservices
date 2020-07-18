package com.github.chengzhx76.netty4.dns.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author admin
 * @Date 2020/7/18 18:43
 * @Version 3.0
 */
public class IpV4Util {

    /**
     * 将ip转为int
     * @param ip
     * @return int可能为负数
     * @throws UnknownHostException
     */
    public static int ipToInt(String ip) throws UnknownHostException {
        byte[] addr = ipToBytes(ip);
        //reference  java.net.Inet4Address.Inet4Address
        int address  = addr[3] & 0xFF;
        address |= ((addr[2] << 8) & 0xFF00);
        address |= ((addr[1] << 16) & 0xFF0000);
        address |= ((addr[0] << 24) & 0xFF000000);
        return address;
    }

    /**
     * 将ip转为int
     * @param ip
     * @return xxx.xxx.xxx.xxx
     */
    public static String intToIp(int ip) {
        byte[] addr = new byte[4];
        addr[0] = (byte) ((ip >>> 24) & 0xFF);
        addr[1] = (byte) ((ip >>> 16) & 0xFF);
        addr[2] = (byte) ((ip >>> 8) & 0xFF);
        addr[3] = (byte) (ip & 0xFF);
        return bytesToIp(addr);
    }

    /**
     * 将byte数组转为ip字符串
     * @param src
     * @return xxx.xxx.xxx.xxx
     */
    public static String bytesToIp(byte[] src) {
        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff)
                + "." + (src[3] & 0xff);
    }

    /**
     * 将ip字符串转为byte数组,注意:ip不可以是域名,否则会进行域名解析
     * @param ip
     * @return byte[]
     * @throws UnknownHostException
     */
    public static byte[] ipToBytes(String ip) throws UnknownHostException {
        return InetAddress.getByName(ip).getAddress();
    }

    public static void main(String[] args) throws UnknownHostException {
        String ip = "255.255.255.123";
        int ipI = ipToInt(ip);
        System.out.println(ip+"->"+ipI);
        System.out.println(ipI+"->"+intToIp(ipI));
        byte[] bytes = ipToBytes(ip);
        System.out.println(ip+"->bytes.length="+bytes.length);
        System.out.println("bytes->"+bytesToIp(bytes));
    }

}
