package com.github.chengzhx76.netty4.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description
 * @Author admin
 * @Date 2020/7/5 15:56
 * @Version 3.0
 */
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        // TODO 不确定服务端是否有response 所以暂时先不用处理
        final ByteBuf buf = packet.content();
        int readableBytes = buf.readableBytes();
        byte[] content = new byte[readableBytes];
        buf.readBytes(content);
        String serverMessage = new String(content);
        System.out.println("reserveServerResponse is: "+serverMessage);
    }

    /**
     * 向服务器发送消息
     * @param msg 按规则拼接的消息串
     * @param inetSocketAddress 目标服务器地址
     */
    public static void sendMessage(final String msg, final InetSocketAddress inetSocketAddress){
        if(msg == null){
            throw new NullPointerException("msg is null");
        }
        // TODO 这一块的msg需要做处理 字符集转换和Bytebuf缓冲区
        senderInternal(datagramPacket(msg, inetSocketAddress));
    }

    /**
     * 发送数据包并监听结果
     * @param datagramPacket
     */
    public static void senderInternal(final DatagramPacket datagramPacket, List<Channel> channelList) {
        for (Channel channel : channelList) {
            if(channel != null){
                channel.writeAndFlush(datagramPacket).addListener(new GenericFutureListener<ChannelFuture>() {
                    @Override
                    public void operationComplete(ChannelFuture future)
                            throws Exception {
                        boolean success = future.isSuccess();
                        System.out.println("Sender datagramPacket result : "+success);
                    }
                });
            }
        }
    }

    /**
     * 组装数据包
     * @param msg 消息串
     * @param inetSocketAddress 服务器地址
     * @return DatagramPacket
     */
    private static DatagramPacket datagramPacket(String msg, InetSocketAddress inetSocketAddress){
        ByteBuf dataBuf = Unpooled.copiedBuffer(msg, Charset.forName("UTF-8"));
        DatagramPacket datagramPacket = new DatagramPacket(dataBuf, inetSocketAddress);
        return datagramPacket;
    }

    /**
     * 发送数据包服务器无返回结果
     * @param datagramPacket
     */
    private static void senderInternal(final DatagramPacket datagramPacket) {
        System.out.println("LogPushUdpClient.channel" + UdpClient.channel);
        if(UdpClient.channel != null){
            UdpClient.channel.writeAndFlush(datagramPacket).addListener(new GenericFutureListener<ChannelFuture>() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    boolean success = future.isSuccess();
                    System.out.println("Sender datagramPacket result : "+success);
                }
            });
        }else{
            throw new NullPointerException("channel is null");
        }
    }

}
