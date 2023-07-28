


D:\software\jdk1.8.0_92\bin\keytool -importcert -alias vp0ca -file txRootCA.crt -storetype jks -keystore caKeyStore.keystore

## .keystore转.jks

### 先转成.p12
D:\software\jdk1.8.0_92\bin\keytool -importkeystore -srckeystore caKeyStore.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore caKeyStore.p12

### 后把.p12转成.jks
D:\software\jdk1.8.0_92\bin\keytool -v -importkeystore -srckeystore caKeyStore.p12 -srcstoretype PKCS12 -destkeystore caKeyStore.jks -deststoretype JKS


openssl pkcs12 -export -in vp0.crt -out clientKeyStore.p12 -inkey vp0.key -name vp0



==============================================================

1）生成服务端私钥，并且导入到服务端KeyStore文件中
keytool -genkey -alias serverkey -keystore kserver.keystore


2）根据私钥，导出服务端证书
keytool -export -alias serverkey -keystore kserver.keystore -file server.crt


3）将服务端证书，导入到客户端的Trust KeyStore中
keytool -import -alias serverkey -file server.crt -keystore tclient.keystore


1）keytool -genkey -alias clientkey -keystore kclient.keystore
2）keytool -export -alias clientkey -keystore kclient.keystore -file client.crt
3）keytool -import -alias clientkey -file client.crt -keystore tserver.keystore



https://blog.csdn.net/qq_29427355/article/details/86611170

Java-HttpClient通过证书实现SSL双向认证（客户端）

https://blog.csdn.net/cl617287/article/details/125543195



keytool -keystore client.truststore -keypass 111111 -storepass 111111 -alias client -import -trustcacerts -file ca.crt

keytool -importkeystore -srckeystore client.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore client.p12




keytool -importcert -alias vp0ca -file ca.crt -storetype jks -keystore caKeyStore.keystore

## .keystore转.jks

### 先转成.p12
keytool -importkeystore -srckeystore caKeyStore.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore caKeyStore.p12

### 后把.p12转成.jks
keytool -v -importkeystore -srckeystore caKeyStore.p12 -srcstoretype PKCS12 -destkeystore caKeyStore.jks -deststoretype JKS


openssl pkcs12 -export -in server.crt -out clientKeyStore.p12 -inkey server.key -name server