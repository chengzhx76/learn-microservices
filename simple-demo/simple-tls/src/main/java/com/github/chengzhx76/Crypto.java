package com.github.chengzhx76;

import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Crypto {
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

    private static byte[] join(byte[] a, byte[] b) {
        byte[] out = new byte[a.length + b.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }

    private static void hmacHash(byte[] secret, byte[] seed, byte[] output)
            throws InvalidKeyException, NoSuchAlgorithmException, ShortBufferException, IllegalStateException {
        KeyParameter keyParameter = new KeyParameter(secret);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);

        byte[] a = seed;

        int macSize = mac.getMacSize();

        byte[] b1 = new byte[macSize];
        byte[] b2 = new byte[macSize];

        int pos = 0;
        while (pos < output.length) {
            mac.update(a, 0, a.length);
            mac.doFinal(b1, 0);
            a = b1;
            mac.update(a, 0, a.length);
            mac.update(seed, 0, seed.length);
            mac.doFinal(b2, 0);
            System.arraycopy(b2, 0, output, pos, Math.min(macSize, output.length - pos));
            pos += macSize;
        }
    }

    /**
     * PRF实现
     * 
     * @throws IOException
     * @throws IllegalStateException
     * @throws ShortBufferException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] prf(byte[] secret, byte[] label, byte[] seed, int length) throws IOException,
            InvalidKeyException, NoSuchAlgorithmException, ShortBufferException, IllegalStateException {
        byte[] labelSeed = join(label, seed);
        byte[] result = new byte[length];
        hmacHash(secret, labelSeed, result);
        return result;
    }

    public static byte[] hash(byte[] bytes) {
        Digest digest = new SM3Digest();
        byte[] output = new byte[digest.getDigestSize()];
        digest.update(bytes, 0, bytes.length);
        digest.doFinal(output, 0);
        return output;
    }
}
