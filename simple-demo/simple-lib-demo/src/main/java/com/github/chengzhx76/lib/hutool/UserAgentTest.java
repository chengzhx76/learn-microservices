package com.github.chengzhx76.lib.hutool;

import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.net.NetUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/18 10:14
 * @Version 3.0
 */
public class UserAgentTest {

    public static void main(String[] args) {

        UserAgent agent = UserAgentUtil.parse("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
        System.out.println(agent);

        System.out.println(NetUtil.hideIpPart("127.0.0.1"));

    }


}
