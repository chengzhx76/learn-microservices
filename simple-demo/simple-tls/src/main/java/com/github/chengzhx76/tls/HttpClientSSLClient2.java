package com.github.chengzhx76.tls;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;


/**
 * @author: Cheng
 * @create: 2023-07-28
 **/
public class HttpClientSSLClient2 {
    // 客户端证书路径，用了本地绝对路径，需要修改
    private final static String CLIENT_CERT_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\certs\\go\\client.p12";
    // 客户端证书密码
    private final static String CLIENT_PWD = "111111";
    // 信任库路径，即keytool生成的那个自定义名称的库文件
    private final static String TRUST_STRORE_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\certs\\go\\client.truststore";
    // 信任库密码，即keytool时的密码
    private final static String TRUST_STORE_PWD = "111111";


    public static void main(String[] args) {
        String strReturn1 = HttpClientSSLClient2.httpGet("https://localhost:8443");
        System.out.println(strReturn1);
    }

    /**
     * 获取HttpClient客户端
     */
    public static CloseableHttpClient getHttpClient() {
        SSLConnectionSocketFactory sslSocketFactory;
        try {
            sslSocketFactory = getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return HttpClientBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
    }

    /**
     * 创建SSLSocketFactory实例
     */
    private static SSLConnectionSocketFactory getSocketFactory() throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, KeyManagementException, UnrecoverableKeyException {
        // 初始化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        KeyStore keyStore = getKeyStore(CLIENT_CERT_FILE, CLIENT_PWD, "PKCS12");
        keyManagerFactory.init(keyStore, CLIENT_PWD.toCharArray());

        // 初始化信任库
        KeyStore trustKeyStore = getKeyStore(TRUST_STRORE_FILE, TRUST_STORE_PWD, "JKS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustKeyStore);

        // 加载协议
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        //return new SSLConnectionSocketFactory(sslContext);

        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }

    /**
     * 获取(密钥及证书)仓库
     *
     * @param keyStorePath 证书路径
     * @param password     证书密码
     * @param type         证书类型
     */
    private static KeyStore getKeyStore(String keyStorePath, String password, String type)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        // 获取证书
        FileInputStream inputStream = new FileInputStream(keyStorePath);
        // 秘钥仓库
        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(inputStream, password.toCharArray());
        inputStream.close();
        return keyStore;
    }

    public static String httpGet(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("Content-Type", "application/json;charset=UTF-8");
            get.setHeader("Accept", "application/json");


            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            resultMsg = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }


    /**
     * post请求发送json格式参数
     *
     * @param url
     * @param strBody
     * @return
     * @throws Exception
     */
    public static String httpPostByJson(String url, String strBody) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        String resultMsg = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("Accept", "application/json");

            StringEntity se = new StringEntity(strBody, StandardCharsets.UTF_8);
            se.setContentType("text/json");

            httpPost.setEntity(se);

            response = httpClient.execute(httpPost);
            HttpEntity entity;
            entity = response.getEntity();
            resultMsg = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultMsg;
    }


}
