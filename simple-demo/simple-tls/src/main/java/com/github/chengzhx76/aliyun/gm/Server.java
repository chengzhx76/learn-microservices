package com.github.chengzhx76.aliyun.gm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import javax.net.ssl.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.aliyun.gmsse.GMProvider;

/**
 * @author: Cheng
 * @create: 2023-08-01
 **/
public class Server {

    private static final String caCert = "sm2/go/ca-gm-cert.crt";

    private static final String signKey = "sm2/go/server-gm-sign-key.pem";
    private static final String signCert = "sm2/go/server-gm-sign-cert.crt";

    private static final String encKey = "sm2/go/server-gm-enc-key.pem";
    private static final String encCert = "sm2/go/server-gm-enc-cert.crt";

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        SSLServerSocket ss = server.buildServerSocket();
        Runnable runner = runServer(ss);
        Thread thread = new Thread(runner, "server");
        thread.start();
        Thread.sleep(100000);
    }

    public static Runnable runServer(final SSLServerSocket ss) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    SSLSocket socket = (SSLSocket) ss.accept();

                    // get path to class file from header
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    do {
                        line = in.readLine();
                        System.out.println(line);
                    } while ((line.length() != 0) &&
                            (line.charAt(0) != '\r') && (line.charAt(0) != '\n'));

                    PrintWriter ps = new PrintWriter(socket.getOutputStream(), false);
                    String content = "<!DOCTYPE html>\r\n" + "Hi.\r\n";
                    int contentLength = content.getBytes().length;
                    ps.print("HTTP/1.1 200 OK\r\n");
                    ps.print("Content-Type: text/html\r\n");
                    ps.print("Connection: close\r\n");
                    ps.print("Content-Length:" + contentLength + "\r\n");
                    ps.print("\r\n");
                    ps.print(content);
                    ps.flush();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public SSLServerSocket buildServerSocket() throws Exception {
        GMProvider provider = new GMProvider();
        SSLContext sc = SSLContext.getInstance("TLS", provider);

        KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
        ks.load(null, null);

        ks.setKeyEntry("sign", Helper.loadPrivateKey(signKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(signCert)
        });
        ks.setKeyEntry("enc", Helper.loadPrivateKey(encKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(encCert)
        });

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, new char[0]);

        X509Certificate cert = Helper.loadCertificate(caCert);
        ks.setCertificateEntry("ca", cert);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509", provider);
        tmf.init(ks);

        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLServerSocketFactory ssf = sc.getServerSocketFactory();
        SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(8443);
        ss.setNeedClientAuth(true);
        // ss.setEnabledProtocols(new String[] { "TLSv1.2" });
        return ss;
    }

}
