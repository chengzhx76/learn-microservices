package com.github.chengzhx76.tls;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * @author: Cheng
 * @create: 2023-07-28
 **/
public class SSLClient {

    private static final String PRIVATE_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\tls\\kclient.keystore";
    private static final String TRUST_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\tls\\tclient.keystore";

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7777;
    private static final String CLIENT_KEY_STORE_PASSWORD = "111111";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "111111";
    private SSLSocket sslSocket;
    /**
     * 启动客户端程序
     *
     * @param args
     */
    public static void main(String[] args) {
        SSLClient client = new SSLClient();
        client.init();
        client.process();
    }
    /**
     * 通过ssl socket与服务端进行连接,并且发送一个消息
     */
    public void process() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);
            bos.write("Client Message".getBytes());
            bos.flush();
            byte[] buffer = new byte[20];
            bis.read(buffer);
            System.out.println(new String(buffer));
            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    /**
     *
     * ssl连接的重点:
     * 初始化SSLSocket
     * 导入客户端私钥KeyStore，导入客户端受信任的KeyStore(服务端的证书)
     *
     */
    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");
            ks.load(Files.newInputStream(Paths.get(PRIVATE_FILE)), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(Files.newInputStream(Paths.get(TRUST_FILE)), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
