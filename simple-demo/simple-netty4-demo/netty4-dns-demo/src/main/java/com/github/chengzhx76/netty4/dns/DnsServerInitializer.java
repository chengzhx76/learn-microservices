package com.github.chengzhx76.netty4.dns;

import com.github.chengzhx76.netty4.dns.handler.DnsServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.dns.DatagramDnsQueryDecoder;
import io.netty.handler.codec.dns.DatagramDnsResponseEncoder;

/**
 * @Description
 * @Author admin
 * @Date 2020/7/18 17:26
 * @Version 3.0
 */
public class DnsServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DatagramDnsQueryDecoder());
        pipeline.addLast(new DatagramDnsResponseEncoder());
        pipeline.addLast(new DnsServerHandler());
    }
}
