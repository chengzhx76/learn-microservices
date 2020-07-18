package com.github.chengzhx76.netty4.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description
 * @Author admin
 * @Date 2020/7/18 17:18
 * @Version 3.0
 */
public class DnsServer {

    public static void main(String[] args) throws InterruptedException {

        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
//				    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer());

            ChannelFuture httpChannelFuture = serverBootstrap.bind(8089).sync();

            System.out.println("===http====> bind 8089 port");

            // =======================DNS=======================
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(httpChannelFuture.channel().eventLoop())
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new DnsServerInitializer());

            bootstrap.bind(53).sync();
            System.out.println("===dns====> bind 53 port");
            // =======================DNS=======================

            httpChannelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
