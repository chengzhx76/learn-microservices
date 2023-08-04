


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

=======================================================================
https://blog.51cto.com/remotedev/5750136

keytool -genkey -alias tomcathttps -keypass 123456 -keyalg RSA -keysize 2048 -validity 365 -keystore  metrox.p12

-alias : 证书别名

-keypass:证书密码

-keyalg:证书算法

-keysize:证书容量

-validity:证书有效期天数

-keysote: 证书保存名

-genkey:生成证书文件

=======================================================================




keytool -importcert -alias vp0ca -file ca.crt -storetype jks -keystore caKeyStore.keystore // 生成 ca 受信

## .keystore转.jks

### 先转成.p12
keytool -importkeystore -srckeystore caKeyStore.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore caKeyStore.p12

### 后把.p12转成.jks
keytool -v -importkeystore -srckeystore caKeyStore.p12 -srcstoretype PKCS12 -destkeystore caKeyStore.jks -deststoretype JKS


openssl pkcs12 -export -in server.crt -out clientKeyStore.p12 -inkey server.key -name server


=========================================================================================
gm-java

keytool -importcert -alias umfca -file ca-gm-cert.crt -storetype jks -keystore caKeyStore.keystore // 生成 ca 受信

## .keystore转.jks

### 先转成.p12
keytool -importkeystore -srckeystore caKeyStore.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore caKeyStore.p12

### 后把.p12转成.jks
keytool -v -importkeystore -srckeystore caKeyStore.p12 -srcstoretype PKCS12 -destkeystore caKeyStore.jks -deststoretype JKS


openssl pkcs12 -export -in server.crt -out clientKeyStore.p12 -inkey server.key -name server


=========================================================================================

=========================================================================================
gm-java-test


openssl genpkey -algorithm EC -pkeyopt ec_paramgen_curve:sm2p256v1 -out ca-gm-key.pem
openssl req -x509 -new -nodes -key ca-gm-key.pem -subj "/CN=myca.com" -days 5000 -out ca-gm-cert.crt

keytool -importcert -alias umfca -file ca-gm-cert.crt -storetype jks -keystore caKeyStore.keystore


openssl genrsa -out ca.key 2048
openssl req -new -x509 -days 3650 -key ca.key -out ca.crt


keytool -importcert -alias umfca -file ca.crt -storetype jks -keystore caKeyStore.keystore // 生成 ca 受信

## .keystore转.jks

### 先转成.p12
keytool -importkeystore -srckeystore caKeyStore.keystore -srcstoretype JKS -deststoretype PKCS12 -destkeystore caKeyStore.p12

### 后把.p12转成.jks
keytool -v -importkeystore -srckeystore caKeyStore.p12 -srcstoretype PKCS12 -destkeystore caKeyStore.jks -deststoretype JKS


openssl pkcs12 -export -in server.crt -out clientKeyStore.p12 -inkey server.key -name server


=========================================================================================

https://developer.aliyun.com/article/867410


https://github.com/guanzhi/GmSSL/issues/370

## 转换java密钥库错误
https://github.com/guanzhi/GmSSL/issues/370

## 如何在支持GM的情况下兼容常规SSL
https://github.com/liuguly/gm-hr/issues/1

## JAVA 支持gmssl生成的证书加载吗
https://github.com/guanzhi/GmSSL/issues/1080

## java 读取证书，生成的.p12文件转换jks时提示密钥不正确
https://github.com/guanzhi/GmSSL/issues/1285

## 国密SSL VPN握手抓包分析
https://www.jianshu.com/p/5435803d2ae7

## 国密加密SM2生成公私钥对
https://blog.51cto.com/u_16177403/6806510

## JAVA对GO生成的签名,验证失败
https://github.com/tjfoc/gmsm/issues/165

## SSL协商过程
https://www.cnblogs.com/lujiango/p/15936116.html
