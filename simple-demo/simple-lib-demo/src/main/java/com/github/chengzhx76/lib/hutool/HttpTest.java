package com.github.chengzhx76.lib.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * Author: 光灿
 * Date: 2021/3/6
 */
public class HttpTest {

    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        //headers.put("accept", "max-age=0");
        //headers.put("accept-encoding", "gzip, deflate, br");
        //headers.put("accept-language", "zh-CN,zh;q=0.9");
        //headers.put("cache-control", "mobile.12306.cn");
        //headers.put("host", "mobile.12306.cn");
        //headers.put("connection", "keep-alive");
        //headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");

        String content = HttpRequest.get("https://mobile.12306.cn/weixin/wxcore/ysqcx?type=wxxcx").addHeaders(headers).execute().body();
        System.out.println(content);
    }

}
