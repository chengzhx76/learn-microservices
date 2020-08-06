package com.github.chengzhx76.jdk.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description
 * @Author admin
 * @Date 2020/8/4 14:38
 * @Version 3.0
 */
public class TestNio {

    public static void main(String[] args) throws Exception {
//        read();
        write();
    }


    private static void read() throws Exception {
        URL url = TestNio.class.getClassLoader().getResource("nio.txt");
        FileInputStream stream = new FileInputStream(url.getPath());
        FileChannel channel = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) b);
        }
        channel.close();
        stream.close();
    }

    private static void write() throws Exception {
//        URL url = TestNio.class.getClassLoader().getResource("nio.txt");
        FileOutputStream stream = new FileOutputStream("nio_write.txt");
        FileChannel channel = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] text = "hello world ~~".getBytes();

        for (byte b : text) {
            buffer.put(b);
        }
        buffer.flip();

        channel.write(buffer);

        channel.close();
        stream.close();
    }
}
