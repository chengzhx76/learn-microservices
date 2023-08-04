package com.github.chengzhx76.aliyun.gm;

import com.aliyun.gmsse.GMProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author: Cheng
 * @create: 2023-08-01
 **/
public class GoClient {
//    private static final String caCert = "sm2/go/ca-gm-cert.crt";
    private static final String caCert = "D:\\golang\\src\\learn-microservices-go\\gmtls\\certs2\\ca-gm-cert.crt";

    private static final String signKey = "D:\\golang\\src\\learn-microservices-go\\gmtls\\certs2\\client-gm-auth-key.pem";
    private static final String signCert = "D:\\golang\\src\\learn-microservices-go\\gmtls\\certs2\\client-gm-auth-cert.crt";

//    private static final String signKey = "sm2/go/client-gm-auth-key.pem";
//    private static final String signCert = "sm2/go/client-gm-auth-cert.crt";

//    private static final String encKey = "sm2/go/client-gm-auth2-key.pem";
//    private static final String encCert = "sm2/go/client-gm-auth2-cert.crt";
    private static final String encKey = signKey;
    private static final String encCert = signCert;

    /*private static final String signKey = "sm2/go/server-gm-sign-key.pem";
    private static final String signCert = "sm2/go/server-gm-sign-cert.crt";

    private static final String encKey = "sm2/go/server-gm-enc-key.pem";
    private static final String encCert = "sm2/go/server-gm-enc-cert.crt";*/

    /*private static final String signKey = "sm2/server_sign.key";
    private static final String signCert = "sm2/server_sign.crt";

    private static final String encKey = "sm2/server_enc.key";
    private static final String encCert = "sm2/server_enc.crt";*/

    public static void main(String[] args) throws Exception {
        GoClient client = new GoClient();
        client.reqGoServer();

    }


    public void reqGoServer() throws Exception {

        GMProvider provider = new GMProvider();
        SSLContext sc = SSLContext.getInstance("TLS", provider);


        KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
        ks.load(null, null);

        /*ks.setKeyEntry("auth", Helper.loadPrivateKey(signKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(signCert)
        });*/
        ks.setKeyEntry("sign", Helper.loadPrivateKey(signKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(signCert)
        });
        ks.setKeyEntry("enc", Helper.loadPrivateKey(encKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(encCert)
        });

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, new char[0]);

        ks.setCertificateEntry("gmca", Helper.loadCertificate(caCert));

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509", provider);
        tmf.init(ks);

        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLSocketFactory ssf = sc.getSocketFactory();

        URI uri = new URI("https://umf.com:50052/");
//        URI uri = new URI("https://umf.com:8443/");
//        URI uri = new URI("https://localhost:8443/");
        URL serverUrl = uri.toURL();
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setSSLSocketFactory(ssf);
        conn.setRequestMethod("GET");


        // 读取服务器端返回的内容
        InputStream connInputStream = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(connInputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        StringBuilder buffer = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        System.out.println("=======Body========> " + buffer);
        System.out.println("======ECC-SM2-WITH-SM4-SM3=========> " + conn.getCipherSuite());
        connInputStream.close();
    }

}
