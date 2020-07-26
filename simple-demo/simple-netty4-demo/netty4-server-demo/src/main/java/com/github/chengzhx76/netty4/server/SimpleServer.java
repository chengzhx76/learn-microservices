package com.github.chengzhx76.netty4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description
 * @Author admin
 * @Date 2020/3/10 18:41
 * @Version 3.0
 */
public class SimpleServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(8899).sync();

            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }

}
