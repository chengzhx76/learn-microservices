package com.github.chengzhx76.netty4.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description
 * @Author admin
 * @Date 2020/3/11 20:18
 * @Version 3.0
 */
public class SimpleClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
