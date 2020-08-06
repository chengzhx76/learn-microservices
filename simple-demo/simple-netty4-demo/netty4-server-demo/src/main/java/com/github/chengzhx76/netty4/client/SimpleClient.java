package com.github.chengzhx76.netty4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description
 * @Author admin
 * @Date 2020/3/11 20:27
 * @Version 3.0
 */
public class SimpleClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();

//        DefaultPromise

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleClientInitializer());

            ChannelFuture future = bootstrap.connect("127.0.0.1", 8899);
            //future.addListener(new ChannelFutureListener() {
            //    @Override
            //    public void operationComplete(ChannelFuture future) throws Exception {
            //        if (future.isSuccess()) {
            //            ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
            //            ChannelFuture writeChannel = future.channel().writeAndFlush(buffer);
            //        } else {
            //            Throwable cause = future.cause();
            //            cause.printStackTrace();
            //        }
            //    }
            //});

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                future.channel().writeAndFlush(reader.readLine());
            }

            //future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
