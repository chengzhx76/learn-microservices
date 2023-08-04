package com.github.chengzhx.aliyun.gm.test;

import cn.hutool.core.io.FileUtil;
import com.aliyun.gmsse.Record;
import com.aliyun.gmsse.SecurityParameters;
import com.aliyun.gmsse.handshake.ServerKeyExchange;
import com.aliyun.gmsse.record.Handshake;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author: Cheng
 * @create: 2023-08-04
 **/
public class TestSign {
    private static final String authFileKey = "D:\\golang\\src\\learn-microservices-go\\gmtls\\certs2\\client-gm-auth-key.pem";
    private static final String authFileCert = "D:\\golang\\src\\learn-microservices-go\\gmtls\\certs2\\client-gm-auth-cert.crt";

    public static final byte[] SRC_DATA = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    private SecurityParameters securityParameters = new SecurityParameters();

    @Test
    public void Sm2Sign() throws Exception {

        PrivateKey priKey = _loadPrivateKey(authFileKey);
        BCECPrivateKey signKey = (BCECPrivateKey) priKey;

        X509Certificate cert = _loadX509Certificate(authFileCert);
        BCECPublicKey publicKey = (BCECPublicKey) cert.getPublicKey();

        byte[] msgBytes = ByteUtils.fromHexString("0100002b010164ccd5314920ba1bd337bce9165e810463c1cd5bb26c7b1191eccb2ac1afe4eb000004e0130080010002000026010197f9b027365c8f0c7d46bc329deb7753cb476832772f6d2bce7acba45866b4f100e013000b0002d50002d20001663082016230820107a0030201020209008badaefb0a754820300a06082a811ccf5501837530133111300f06035504030c086d7963612e636f6d301e170d3233303732363039333731325a170d3337303430333039333731325a30123110300e06035504030c07756d662e636f6d3059301306072a8648ce3d020106082a811ccf5501822d034200040f48d135714a16e15e94f71e951634c37d6bcc47607538f3d009d812f9c6e8bf686f67a8f82fc489612d2420c9979d634c73b2de4613d94859fe1a29759dd803a345304330120603551d11040b30098207756d662e636f6d301d0603551d250416301406082b0601050507030206082b06010505070301300e0603551d0f0101ff0404030205a0300a06082a811ccf550183750349003046022100a094586ff4135972656ae8bedbadf3f46d30433743f420b1a34967d0c783d86b022100d190c8fb164e849c89a9888b8e3934c633a5a810d67e4ad7b52320ffdca9a1e30001663082016230820107a0030201020209008badaefb0a754821300a06082a811ccf5501837530133111300f06035504030c086d7963612e636f6d301e170d3233303732363039333731325a170d3337303430333039333731325a30123110300e06035504030c07756d662e636f6d3059301306072a8648ce3d020106082a811ccf5501822d03420004263968536c55339656f63e9e7d6684312947e8a5e1794dbad27789d00f9e41e7c6d2c70d2443dc9d3111d21b979193fdadc6a830b7a30618b2548a2937145f73a345304330120603551d11040b30098207756d662e636f6d301d0603551d250416301406082b0601050507030206082b06010505070301300e0603551d0f0101ff0404030205a0300a06082a811ccf550183750349003046022100dbe871260369f210f34142c34d6a3e05d5df0ebeef74dd361b271353536eec42022100c9b91d92c31a9ae6ab6fe7b93837208b94b56e8f388179574e64e2593f7b7bb00c000049004730450220103c3b7083aac863beb436d90acff135071074d24a263f7b40a35e74492eebf6022100ceac505f205f50abeb0781fe7cdad3d10939fa128a7df0a041de7c96875cfa890d00001c0201400017001530133111300f06035504030c086d7963612e636f6d0e0000000b0002cd0002ca0001623082015e30820105a0030201020209008badaefb0a754822300a06082a811ccf5501837530133111300f06035504030c086d7963612e636f6d301e170d3233303732363039333731325a170d3337303430333039333731325a30163114301206035504030c0b636c69656e74312e636f6d3059301306072a8648ce3d020106082a811ccf5501822d03420004cfb605245bc39c5c434bfb48094d7b6babd0630cbb5336bb8d8756ad0d05df0561485eaece3a04713b4c5318c30fb8768ff1bf166a72336b4973a3df853da903a33f303d30160603551d11040f300d820b636c69656e74312e636f6d30130603551d25040c300a06082b06010505070302300e0603551d0f0101ff0404030205a0300a06082a811ccf55018375034700304402203fb727be066a38b81488a0d016b5dd516c902af247350bf93cab2899fd33dc2d022077d75f7939b31dcd72aed08428fd1e7a8356a159039b6829f6f2c33c8f3c1dab0001623082015e30820105a0030201020209008badaefb0a754822300a06082a811ccf5501837530133111300f06035504030c086d7963612e636f6d301e170d3233303732363039333731325a170d3337303430333039333731325a30163114301206035504030c0b636c69656e74312e636f6d3059301306072a8648ce3d020106082a811ccf5501822d03420004cfb605245bc39c5c434bfb48094d7b6babd0630cbb5336bb8d8756ad0d05df0561485eaece3a04713b4c5318c30fb8768ff1bf166a72336b4973a3df853da903a33f303d30160603551d11040f300d820b636c69656e74312e636f6d30130603551d25040c300a06082b06010505070302300e0603551d0f0101ff0404030205a0300a06082a811ccf55018375034700304402203fb727be066a38b81488a0d016b5dd516c902af247350bf93cab2899fd33dc2d022077d75f7939b31dcd72aed08428fd1e7a8356a159039b6829f6f2c33c8f3c1dab1000009f009d30819a022100a5c6563c37b14472cd13b8046d45e800130616093486de9436d51d11eaf8543e022100ad390502d6ec9ec479d8a0f080c34edc31221e904ee2a608d6806b3f0aadf68304202807c70c9fad6719bdea7cd951c2c1f184e45fcc952d1fd2e1b4eaac1004bce804306008f24bb21f7f6a797ba0c597cc7a72c3f80ea48dd8fc1d9e7b18cc77dcf2e2323d7f9563171c1cfc594bb11345c490");

        /*Signature signature = Signature.getInstance("SM3withSM2", new BouncyCastleProvider());
        SM2ParameterSpec spec = new SM2ParameterSpec("1234567812345678".getBytes());
        signature.setParameter(spec);
        signature.initSign(signKey);
        signature.update(msgBytes);
        byte[] signBytes = signature.sign();

        System.out.println("SM2 sign result:\n" + ByteUtils.toHexString(signBytes));*/


        // --------------------------------------------------------------------------------------------------

        byte[] signHexToBytes = ByteUtils.fromHexString("304502207932f2254eac738b640e7cdecba799dc2e63cf44bbe711690c78612b23346f74022100e86853109b066c6d14f31112d8a95170d390a373c3a2b9b1c24321a53f823243");

        Signature s = Signature.getInstance("SM3withSM2", new BouncyCastleProvider());
        SM2ParameterSpec _spec = new SM2ParameterSpec("1234567812345678".getBytes());
        s.setParameter(_spec);
        s.initVerify(publicKey);
        s.update(msgBytes);
        boolean verify = s.verify(signHexToBytes);

        System.out.println("======verify=====> " + verify);

    }
    // ===================================================== gmhelper start ==============================================


    @Test
    public void gmhelperSm2Sign() throws Exception {
        PrivateKey priKey = _loadPrivateKey(authFileKey);
        BCECPrivateKey bcecPriKey = (BCECPrivateKey) priKey;

        X509Certificate cert = _loadX509Certificate(authFileCert);
        BCECPublicKey bcecPubKey = (BCECPublicKey) cert.getPublicKey();

//        System.out.println("SM2 msg:\n" + ByteUtils.toHexString(SRC_DATA));

//        byte[] msgBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] msgBytes = ByteUtils.fromHexString("29609fb61f34fe9b207716c7d56cf05200a7d411a2d7c0e739af72b5db0104f7");


//        byte[] sign = sign(bcecPriKey, msgBytes);
//        System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));

        // --------------------------------------------------------------------------------------------------
        byte[] signHexToBytes = ByteUtils.fromHexString("304502207932f2254eac738b640e7cdecba799dc2e63cf44bbe711690c78612b23346f74022100e86853109b066c6d14f31112d8a95170d390a373c3a2b9b1c24321a53f823243");


        boolean verify = verify(bcecPubKey, msgBytes, signHexToBytes);
        System.out.println("======verify=====> " + verify);
    }

    public static byte[] sign(PrivateKey priKey, byte[] srcData) throws CryptoException {
        BCECPrivateKey ecPriKey = (BCECPrivateKey) priKey;
        ECPrivateKeyParameters priKeyParameters = convertPrivateKeyToParameters(ecPriKey);
        SM2Signer signer = new SM2Signer();
        ParametersWithRandom pwr = new ParametersWithRandom(priKeyParameters, new SecureRandom());
        signer.init(true, pwr);
        signer.update(srcData, 0, srcData.length);
        return signer.generateSignature();
    }

    public static boolean verify(PublicKey pubKey, byte[] srcData, byte[] sign) {
        BCECPublicKey ecPubKey = (BCECPublicKey) pubKey;
        ECPublicKeyParameters pubKeyParameters = convertPublicKeyToParameters(ecPubKey);
        SM2Signer signer = new SM2Signer();
        signer.init(false, pubKeyParameters);
        signer.update(srcData, 0, srcData.length);
        return signer.verifySignature(sign);
    }

    /*public static byte[] sign(BCECPrivateKey priKey, byte[] srcData) throws CryptoException {
        byte[] withId = null;

        ECPrivateKeyParameters priKeyParameters = convertPrivateKeyToParameters(priKey);

        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ParametersWithRandom pwr = new ParametersWithRandom(priKeyParameters, new SecureRandom());
        if (withId != null) {
            param = new ParametersWithID(pwr, withId);
        } else {
            param = pwr;
        }
        signer.init(true, param);
        signer.update(srcData, 0, srcData.length);
        return signer.generateSignature();
    }*/

    /*public static boolean verify(BCECPublicKey pubKey, byte[] srcData, byte[] sign) {
        byte[] withId = null;
        ECPublicKeyParameters pubKeyParameters = convertPublicKeyToParameters(pubKey);
        SM2Signer signer = new SM2Signer();
        CipherParameters param;
        if (withId != null) {
            param = new ParametersWithID(pubKeyParameters, withId);
        } else {
            param = pubKeyParameters;
        }
        signer.init(false, param);
        signer.update(srcData, 0, srcData.length);
        return signer.verifySignature(sign);
    }*/


    public static ECPrivateKeyParameters convertPrivateKeyToParameters(BCECPrivateKey ecPriKey) {
        ECParameterSpec parameterSpec = ecPriKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPrivateKeyParameters(ecPriKey.getD(), domainParameters);
    }

    public static ECPublicKeyParameters convertPublicKeyToParameters(BCECPublicKey ecPubKey) {
        ECParameterSpec parameterSpec = ecPubKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPublicKeyParameters(ecPubKey.getQ(), domainParameters);
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
}









