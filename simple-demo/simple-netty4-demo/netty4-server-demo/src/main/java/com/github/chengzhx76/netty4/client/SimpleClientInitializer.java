package com.github.chengzhx76.netty4.client;

import com.github.chengzhx76.netty4.server.SimpleServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description
 * @Author admin
 * @Date 2020/3/10 18:56
 * @Version 3.0
 */
public class SimpleClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new SimpleServerHandler());
    }
}
