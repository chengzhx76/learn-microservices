package com.github.chengzhx76.tls;


/***********************************************************************************************************************
 *
 * 1)生成服务端私钥
 * keytool -genkey -alias serverkey -keystore kserver.keystore
 * 2)根据私钥,到处服务端证书
 * keytool -exoport -alias serverkey -keystore kserver.keystore -file server.crt
 * 3)把证书加入到客户端受信任的keystore中
 * keytool -import -alias serverkey -file server.crt -keystore tclient.keystore
 *
 **********************************************************************************************************************/


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * @author: Cheng
 * @create: 2023-07-28
 **/
public class SSLServer {
    private static final String PRIVATE_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\certs\\kserver.keystore";
    private static final String TRUST_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\certs\\tserver.keystore";

    private static final int DEFAULT_PORT = 7777;
    private static final String SERVER_KEY_STORE_PASSWORD = "111111";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "111111";
    private SSLServerSocket serverSocket;
    /**
     * 启动程序
     *
     * @param args
     */
    public static void main(String[] args) {
        SSLServer server = new SSLServer();
        server.init();
        server.start();
    }
    /**
     *
     * 听SSL Server Socket
     * 由于该程序不是演示Socket监听，所以简单采用单线程形式，并且仅仅接受客户端的消息，并且返回客户端指定消息
     *
     */
    public void start() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);
                byte[] buffer = new byte[20];
                bis.read(buffer);
                System.out.println(new String(buffer));
                bos.write("Server Echo".getBytes());
                bos.flush();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    /**
     * ssl连接的重点:
     * 初始化SSLServerSocket
     * 导入服务端私钥KeyStore，导入服务端受信任的KeyStore(客户端的证书)
     */
    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");
            ks.load(Files.newInputStream(Paths.get(PRIVATE_FILE)), SERVER_KEY_STORE_PASSWORD.toCharArray());
            tks.load(Files.newInputStream(Paths.get(TRUST_FILE)), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
            serverSocket.setNeedClientAuth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
