package com.github.chengzhx.aliyun.gm.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import com.aliyun.gmsse.ProtocolVersion;
import com.aliyun.gmsse.crypto.Crypto;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: Cheng
 * @create: 2023-08-02
 **/
public class TestGm {

//    private static final String encFileKey = "D:/golang/src/learn-microservices-go/gmtls/certs2/server-gm-enc-key.pem";
//    private static final String encFileCert = "D:/golang/src/learn-microservices-go/gmtls/certs2/server-gm-enc-cert.crt";

    private static final String encFileKey = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\sm2\\go2\\server-gm-enc-key.pem";
    private static final String encFileCert = "D:\\idea-workspace\\learn-microservices\\simple-demo\\simple-tls\\src\\main\\resources\\sm2\\go2\\server-gm-enc-cert.crt";

//    private static final String encFileKey = "sm2/go2/server-gm-enc-cert.crt";
//    private static final String encFileCert = "sm2/go2/server-gm-enc-key.pem";

    SecureRandom random = new SecureRandom();
    public static void main(String[] args) throws Exception {
        new TestGm().pubKeyToHexStrEncrypt();
    }



    @Test
    public void getPlaintextHex() throws Exception {
        ProtocolVersion version = ProtocolVersion.NTLS_1_1;
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ba.write(version.getMajor());
        ba.write(version.getMinor());
        ba.write(random.generateSeed(46));
        byte[] plaintextByte = ba.toByteArray();

        String plaintextHex = HexUtil.encodeHexStr(plaintextByte);
        System.out.println("plaintextHex => " + plaintextHex);
    }


    // ===================================================== gmhelper end ==============================================


    private static final X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static final ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(),
            x9ECParameters.getG(), x9ECParameters.getN());

    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_H = CURVE.getCofactor();
    public final static BigInteger SM2_ECC_GX = new BigInteger(
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY = new BigInteger(
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public static final ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    public static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT,
            SM2_ECC_N, SM2_ECC_H);

    @Test
    public void gmhelperEncodeAndDecrypt() {
        String plaintextHex = "01014d1ed402d6d88e128744bbf5480254971fb4237264dee532fc10e47f9a8fc2641556084f68f9d92204d7bce62ff6";
        try {

            X509Certificate cert = _loadX509Certificate(encFileCert);
            BCECPublicKey pubKey = (BCECPublicKey) cert.getPublicKey();

            byte[] hexBytes = HexUtil.decodeHex(plaintextHex);
            byte[] encryptedBytes = encrypt(pubKey, hexBytes);

            String encryptHex = HexUtil.encodeHexStr(encryptedBytes);
            System.out.println("gmhelper.pubkey.encrypt => " + encryptHex);

            PrivateKey priKey = _loadPrivateKey(encFileKey);
            BCECPrivateKey bcecPriKey = (BCECPrivateKey) priKey;

            byte[] decryptedBytes = decrypt(bcecPriKey, encryptedBytes);

            String decryptedHex = HexUtil.encodeHexStr(decryptedBytes);
            System.out.println("decrypted => " + decryptedHex);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] encrypt(BCECPublicKey pubKey, byte[] preMasterSecret) throws IOException, InvalidCipherTextException {
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pubKey.getQ(), ecDomainParameters);
        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        ParametersWithRandom pwr = new ParametersWithRandom(publicKeyParameters, new SecureRandom());
        engine.init(true, pwr);
        byte[] cipher = engine.processBlock(preMasterSecret, 0, preMasterSecret.length);

        int curveLength = (DOMAIN_PARAMS.getCurve().getFieldSize() + 7) / 8;

        byte[] c1x = new byte[curveLength];
        byte[] c1y = new byte[curveLength];
        byte[] c2 = new byte[cipher.length - c1x.length - c1y.length - 1 - 32];
        byte[] c3 = new byte[32];

        int startPos = 1;
        System.arraycopy(cipher, startPos, c1x, 0, c1x.length);
        startPos += c1x.length;
        System.arraycopy(cipher, startPos, c1y, 0, c1y.length);
        startPos += c1y.length;
        System.arraycopy(cipher, startPos, c3, 0, c3.length);
        startPos += c3.length;
        System.arraycopy(cipher, startPos, c2, 0, c2.length);

        ASN1Encodable[] arr = new ASN1Encodable[4];
        // c1x,c1y的第一个bit可能为1，这个时候要确保他们表示的大数一定是正数，所以new BigInteger符号强制设为正。
        arr[0] = new ASN1Integer(new BigInteger(1, c1x));
        arr[1] = new ASN1Integer(new BigInteger(1, c1y));
        arr[2] = new DEROctetString(c3);
        arr[3] = new DEROctetString(c2);
        DERSequence ds = new DERSequence(arr);
        return ds.getEncoded(ASN1Encoding.DER);
    }

    public static byte[] decrypt(BCECPrivateKey bcecPriKey, byte[] encryptedPreMasterSecret) throws InvalidCipherTextException {

        ASN1Sequence as = DERSequence.getInstance(encryptedPreMasterSecret);
        byte[] c1x = ((ASN1Integer) as.getObjectAt(0)).getValue().toByteArray();
        byte[] c1y = ((ASN1Integer) as.getObjectAt(1)).getValue().toByteArray();
        // c1x，c1y可能因为大正数的补0规则在第一个有效字节前面插了一个(byte)0，变成33个字节，在这里要修正回32个字节去
        c1x = fixToCurveLengthBytes(c1x);
        c1y = fixToCurveLengthBytes(c1y);
        byte[] c3 = ((DEROctetString) as.getObjectAt(2)).getOctets();
        byte[] c2 = ((DEROctetString) as.getObjectAt(3)).getOctets();

        int pos = 0;
        byte[] cipherText = new byte[1 + c1x.length + c1y.length + c2.length + c3.length];
        final byte uncompressedFlag = 0x04;
        cipherText[0] = uncompressedFlag;
        pos += 1;
        System.arraycopy(c1x, 0, cipherText, pos, c1x.length);
        pos += c1x.length;
        System.arraycopy(c1y, 0, cipherText, pos, c1y.length);
        pos += c1y.length;
        System.arraycopy(c3, 0, cipherText, pos, c3.length);
        pos += c3.length;
        System.arraycopy(c2, 0, cipherText, pos, c2.length);

        ECPrivateKeyParameters priKeyParameters = new ECPrivateKeyParameters(bcecPriKey.getS(), ecDomainParameters);
        SM2Engine engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        engine.init(false, priKeyParameters);
        return engine.processBlock(cipherText, 0, cipherText.length);
    }

    private static byte[] fixToCurveLengthBytes(byte[] src) {
        int curveLen = (DOMAIN_PARAMS.getCurve().getFieldSize() + 7) / 8;
        if (src.length == curveLen) {
            return src;
        }
        byte[] result = new byte[curveLen];
        if (src.length > curveLen) {
            System.arraycopy(src, src.length - result.length, result, 0, result.length);
        } else {
            System.arraycopy(src, 0, result, result.length - src.length, src.length);
        }
        return result;
    }

    // ===================================================== gmhelper end ==================================================


    // 公钥对象转字符串
    public void pubKeyToString() throws Exception{
        X509Certificate cert = _loadX509Certificate(encFileCert);
        BCECPublicKey pubKey = (BCECPublicKey) cert.getPublicKey();
        byte[] pubKeyBytes = pubKey.getEncoded();
        System.out.println(Base64.getEncoder().encodeToString(pubKeyBytes));
    }

    // 公钥加密字符串
    @Test
    public void pubKeyToHexStrEncrypt() throws Exception{
        String plaintextHex = "01011f44b351576f0c67f8fbee833833c1d082c3a7cd207275dd355a2e174fbc226b8d970934eb9c5443eef59de1ad0e";
        byte[] hexBytes = HexUtil.decodeHex(plaintextHex);

        X509Certificate cert = _loadX509Certificate(encFileCert);

        BCECPublicKey pubKey = (BCECPublicKey) cert.getPublicKey();
        byte[] pubKeyBytes = pubKey.getEncoded();
        System.out.println("pubKey => " + Base64.getEncoder().encodeToString(pubKeyBytes));

        byte[] encryptedPreMasterSecret = Crypto.encrypt(pubKey, hexBytes);
        String encryptHex = HexUtil.encodeHexStr(encryptedPreMasterSecret);
        System.out.println("encrypted => " + encryptHex);
    }

    // 公钥加密字符串
    @Test
    public void priKeyToHexStrDecrypt() throws Exception{
        String plaintextHex = "010177f9368e67b0d0ed8e72642f00d0af448184f02515ee2afd6e54d73dbfae76bc291dbfa7f881fb0d3594c2ceabcb";
        String encryptHex = "3081980220061152e0c928188b4d400bfd5a0e809f8d12f6077ebdcb6bf337f11f766ea9b40220c8ab13461fb593b0b41108e96e07a2a14d915f495335b3fc8597577e72f2516a042062847c312361846c8ec20463f2b34010babb9755e7d9ee85f7d245492d45698f04309adbcd6b8cf4253a1cadd2ff283dbd5e7f759874ae7f070f2475d26a09c5d1d57d1d91a0feb70409d2bdd64473065e00";
        byte[] encryptBytes = HexUtil.decodeHex(encryptHex);

        PrivateKey priKey = _loadPrivateKey(encFileKey);


        byte[] priKeyBytes = priKey.getEncoded();
        System.out.println("priKey => " + Base64.getEncoder().encodeToString(priKeyBytes));

        byte[] decryptBytes = Crypto.decrypt((BCECPrivateKey)priKey, encryptBytes);
        String decryptHex = HexUtil.encodeHexStr(decryptBytes);
        System.out.println("decrypted => " + decryptHex);
    }


    // 加解密
    @Test
    public void encryptAndDecrypt() throws Exception{
        X509Certificate cert = _loadX509Certificate(encFileCert);
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ba.write(1);
        ba.write(1);
        ba.write(random.generateSeed(46));
        byte[] preMasterSecret = ba.toByteArray();
        String hexPreMasterSecret = HexUtil.encodeHexStr(preMasterSecret);
        System.out.println("hexPreMasterSecret => " + hexPreMasterSecret);
//        Assert.assertEquals(48, preMasterSecret.length);

        byte[] encryptedPreMasterSecret = Crypto.encrypt((BCECPublicKey)cert.getPublicKey(), preMasterSecret);
        String hexEncryptedPreMasterSecret = HexUtil.encodeHexStr(encryptedPreMasterSecret);
        System.out.println("hexEncryptedPreMasterSecret => " + hexEncryptedPreMasterSecret);
//        Assert.assertEquals(155, encryptedPreMasterSecret.length);


        PrivateKey key = _loadPrivateKey(encFileKey);
        byte[] decryptedPreMasterSecret = Crypto.decrypt((BCECPrivateKey)key, encryptedPreMasterSecret);
        String hexDecryptedPreMasterSecret = HexUtil.encodeHexStr(decryptedPreMasterSecret);
        System.out.println("hexDecryptedPreMasterSecret => " + hexDecryptedPreMasterSecret);
//        Assert.assertArrayEquals(preMasterSecret, decryptedPreMasterSecret);
    }



    @Test
    public void priFileToPriStr() throws Exception{
        PrivateKey priKey = _loadPrivateKey(encFileKey);
        byte[] priKeyBytes = priKey.getEncoded();
        System.out.println("priKey => " + Base64.getEncoder().encodeToString(priKeyBytes));
    }

    @Test
    public void base64Read() throws Exception{
        String base64 = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEJjloU2xVM5ZW9j6efWaEMSlH6KXheU260neJ0A+eQefG0scNJEPcnTER0huXkZP9rcaoMLejBhiyVIopNxRfcw==";
        cn.hutool.core.codec.Base64.decode(base64);
    }












    public X509Certificate _loadX509Certificate(String path) throws CertificateException, IOException {
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        InputStream is = FileUtil.getInputStream(path);
        BouncyCastleProvider bc = new BouncyCastleProvider();
        CertificateFactory cf = CertificateFactory.getInstance("X.509", bc);
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        is.close();
        return cert;
    }

    public PrivateKey _loadPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        InputStream is = FileUtil.getInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            if (line.startsWith("-")){
                continue;
            }
            sb.append(line).append("\n");
        }
        String ecKey = sb.toString().replaceAll("/r/n|/r|/n", "");
//        Base64.Decoder base64Decoder = Base64.getDecoder();
//        byte[] keyByte = base64Decoder.decode(ecKey.getBytes(StandardCharsets.UTF_8));
        byte[] keyByte = cn.hutool.core.codec.Base64.decode(ecKey);
        PKCS8EncodedKeySpec eks2 = new PKCS8EncodedKeySpec(keyByte);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
        PrivateKey privateKey = keyFactory.generatePrivate(eks2);
        return privateKey;
    }


    public void testStr() throws Exception{
        byte[] bs = random.generateSeed(46);
        String hex = HexUtil.encodeHexStr(bs);
        System.out.println(hex);
    }

}
