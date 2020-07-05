package com.github.chengzhx76.netty4.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;
import java.net.*;

/**
 * @Description https://blog.csdn.net/fenglongmiao/article/details/79219959
 * @Author admin
 * @Date 2020/7/5 15:54
 * @Version 3.0
 */
public class UdpClient {
    static Channel channel = null;
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch)throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
//                                .addLast(new StringDecoder(Charset.forName("ASCII")))
//                                .addLast(new StringEncoder(Charset.forName("ASCII")))
                                .addLast(new UdpClientHandler());
                    }
                });

        channel = bootstrap.bind(1234).sync().channel();
        channel.closeFuture().await(1000);

        workerGroup.shutdownGracefully();
    }

    // 测试
    public void logPushSendTest() throws Exception{
        int i = 0;
        while( i < 10){
            UdpClientHandler.sendMessage("你好UdpServer", new InetSocketAddress("127.0.0.1", 888));
            i++;
        }
    }
    public static void main2(String args[]){

        byte[] buf = new byte[256];
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        buf= "hello".getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 9956);
        try {
            socket.send(packet);
            System.out.println(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
