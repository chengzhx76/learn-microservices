package com.github.chengzhx76.netty4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description
 * @Author admin
 * @Date 2020/3/11 20:27
 * @Version 3.0
 */
public class SimpleClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleClientInitializer());

            ChannelFuture future = bootstrap.connect("127.0.0.1", 8899);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
