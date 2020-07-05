package com.github.chengzhx76.netty4.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @Description
 * https://blog.csdn.net/IT_liuzhiyuan/article/details/90375453
 * https://stackoverflow.com/questions/37656161/netty-udp-server-bootstrap
 * @Author admin
 * @Date 2020/7/5 15:20
 * @Version 3.0
 */
public class UdpServer {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup acceptGroup = new NioEventLoopGroup();
        bootstrap.group(acceptGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new UdpServerHandler());
                    }
                });

        Channel channel = bootstrap.bind("127.0.0.1", 8888).sync().channel();
        channel.closeFuture().await();

        acceptGroup.shutdownGracefully();
    }

}
