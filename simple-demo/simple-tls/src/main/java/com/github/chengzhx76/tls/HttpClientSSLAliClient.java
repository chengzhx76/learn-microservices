package com.github.chengzhx76.tls;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author: Cheng
 * @create: 2023-07-28
 **/
public class HttpClientSSLAliClient {

    private static final String PRIVATE_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\tls\\go\\clientKeyStore.p12";
    private static final String TRUST_FILE = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\tls\\go\\caKeyStore.jks";

    private static final String CLIENT_KEY_STORE_PASSWORD = "111111";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "111111";
    private static final String CLIENT_KEY_PASSWORD = "111111";


    private static PoolingHttpClientConnectionManager secureConnectionManager;
    private static HttpClientBuilder secureHttpBuilder = null;
    private final static int DEFAULT_MAX_CONNECTION = 5;
    private final static int DEFAULT_TIME_OUT = 1000 * 60;


    public static void main(String[] args) throws IOException {
        init();
        CloseableHttpResponse response = null;
        try {
            response = secureHttpBuilder.build().execute(httpUriRequest("https://localhost:8443", "GET", null));
            String data = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println(data);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
    }

    private static HttpUriRequest httpUriRequest(String url, String method, HttpEntity entity) {
        RequestBuilder requestBuilder = null;
        if ("POST".equals(method)) {
            requestBuilder = RequestBuilder.post();
            if (entity != null) {
                requestBuilder.setEntity(entity);
            }
        } else {
            requestBuilder = RequestBuilder.get();
        }

        requestBuilder.setUri(url);

        requestBuilder.addHeader("Content-Type", "application/json");
        requestBuilder.addHeader("Connection", "keep-alive");

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectionRequestTimeout(DEFAULT_TIME_OUT)
                .setSocketTimeout(DEFAULT_TIME_OUT)
                .setConnectTimeout(DEFAULT_TIME_OUT);

        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }


    private static void init() {

        try {
            KeyStore trustStore = KeyStore.getInstance("jks");
            InputStream trustStoreInput = Files.newInputStream(new File(TRUST_FILE).toPath());
            trustStore.load(trustStoreInput, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());

            KeyStore clientKeyStore = KeyStore.getInstance("pkcs12");
            InputStream clientKeyStoreInput = Files.newInputStream(new File(PRIVATE_FILE).toPath());
            clientKeyStore.load(clientKeyStoreInput, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(clientKeyStore, CLIENT_KEY_PASSWORD.toCharArray())
                    .setSecureRandom(new SecureRandom())
                    .build();

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"},
                    null,
                    new NoopHostnameVerifier()
            );
            ConnectionSocketFactory plainSocketFactory = new PlainConnectionSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainSocketFactory)
                    .register("https", sslSocketFactory)
                    .build();

            secureConnectionManager = new PoolingHttpClientConnectionManager(registry, null, null, null, 2, TimeUnit.MINUTES);
            secureConnectionManager.setMaxTotal(10);
            secureConnectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTION);
            secureHttpBuilder = HttpClients.custom().setConnectionManager(secureConnectionManager);
        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
    }

}
