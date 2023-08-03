package com.github.chengzhx76.aliyun.gm;

import com.aliyun.gmsse.GMProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: Cheng
 * @create: 2023-08-01
 **/
public class Client2 {


    private static final String caCert = "sm2/chain-ca.crt";

    private static final String sigKey = "sm2/server_sign.key";
    private static final String sigCert = "sm2/server_sign.crt";

    private static final String encKey = "sm2/server_enc.key";
    private static final String encCert = "sm2/server_enc.crt";

    public static void main(String[] args) throws Exception {

        new Client2().testServer();
    }


//    @Test
    public void testServer() throws Exception {
        GMProvider provider = new GMProvider();
        SSLContext sc = SSLContext.getInstance("TLS", provider);


        KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
        ks.load(null, null);

        ks.setKeyEntry("sign", Helper.loadPrivateKey(sigKey), new char[0], new X509Certificate[] {
                Helper.loadCertificate(sigCert)
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

        URI uri = new URI("https://localhost:8443/");
        URL serverUrl = uri.toURL();
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setSSLSocketFactory(ssf);
        conn.setRequestMethod("GET");
//        Assert.assertEquals(200, conn.getResponseCode());
//        Assert.assertEquals("ECC-SM2-WITH-SM4-SM3", conn.getCipherSuite());

        // 读取服务器端返回的内容
        InputStream connInputStream = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(connInputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        StringBuilder buffer = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
//        Assert.assertEquals("<!DOCTYPE html>Hi.", buffer.toString());
        System.out.println("===============> " + buffer);
        connInputStream.close();
        // 中断服务线程
    }
}
