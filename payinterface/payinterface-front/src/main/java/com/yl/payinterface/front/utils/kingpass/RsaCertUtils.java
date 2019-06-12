package com.yl.payinterface.front.utils.kingpass;

import com.yl.payinterface.front.utils.Base64Utils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * Rsa工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2016年9月16日
 */
public class RsaCertUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data          已加密数据
     * @param privateKeyStr 私钥路径
     * @param password      私钥密码
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKeyStr, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        InputStream in = new FileInputStream(privateKeyStr);
        ks.load(in, password.toCharArray());
        in.close();
        String keyAlias = null;
        Enumeration enumeration = ks.aliases();
        if (enumeration.hasMoreElements()) {
            keyAlias = (String) enumeration.nextElement();
        }
        PrivateKey privateK = (PrivateKey) ks.getKey(keyAlias, password.toCharArray());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data         签名数据
     * @param publicKeyStr 公钥路径
     * @param sign         数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String publicKeyStr, String sign)
            throws Exception {
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(new FileInputStream(publicKeyStr));
        PublicKey publicK = Cert.getPublicKey();
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicK);
        signature.update(data.getBytes());
        System.out.println(Base64Utils.encode(publicK.getEncoded()));
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(publicKey);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(bytes));
        PublicKey publicK = x509Certificate.getPublicKey();

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * 签名
     *
     * @param params
     * @param prkPath
     * @param password
     * @return
     * @throws Exception
     */
    public static String sign(Map<String, String> params, String prkPath, String password) throws Exception {
        String paramsStr;
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry entry : entrySet) {
            if ("bizContent".equals(entry.getKey())) {
                continue;
            }
            stringBuilder.append(entry.getValue()).append("|");
        }
        paramsStr = stringBuilder.toString();
        paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
        return RsaCertUtils.sign(paramsStr.getBytes(), prkPath, password);
    }

    /**
     * 验证签名
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static boolean verify(Map<String, String> params) throws Exception {
        Map<String, String> signParams = new TreeMap<>();
        signParams.put("mercOrderNo", params.get("mercOrderNo"));
        signParams.put("orderAmount", params.get("orderAmount"));
        signParams.put("orderStatus", params.get("orderStatus"));
        signParams.put("orderSuccessTime", params.get("orderSuccessTime"));
        signParams.put("payOrderNo", params.get("payOrderNo"));

        String paramsStr;
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = signParams.entrySet();
        for (Map.Entry entry : entrySet) {
            stringBuilder.append(entry.getValue()).append("|");
        }
        paramsStr = stringBuilder.toString();
        paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
        return RsaCertUtils.verify(paramsStr.getBytes(), params.get("cert"), params.get("sign"));
    }

    public static void main(String[] args) throws Exception {
        String str = "{\"txnAmt\":\"50000\",\"orderId\":\"TD20171209101088660452\",\"txnTime\":\"20171209153348\",\"frontUrl\":\"http://m.bank-pay.com:5555/payinterface-front/LDZQuickPay42010101D0FrtComplete/quickPayD0\",\"backUrl\":\"http://m.bank-pay.com:5555/payinterface-front/LDZQuickPay42010101D0Complete/quickPayD0\",\"accNo\":\"6222300499932815\",\"realName\":\"张晓杰\",\"identityNo\":\"130728199208250012\",\"phoneNo\":\"17600126400\",\"toBankName\":\"工商银行\",\"toAccNo\":\"6212260200041743341\",\"userFee\":\"300\",\"extFiled\":\"TD-20171209-101088660452\",\"toBankCode\":\"102100099996\",\"terminalMerCode\":\"\",\"terminalMerName\":\"\",\"terminalMerState\":\"\",\"terminalMerCity\":\"\",\"terminalMerAddress\":\"\",\"t0drawFee\":\"\",\"t0drawRate\":\"\",\"t1consFee\":\"\",\"t1consRate\":\"\",\"version\":\"0.1\"}";
        System.out.println(RsaCertUtils.sign(str.getBytes(), "/Users/zhangxiaojie/Documents/800001702700003.p12", "NlQwHM"));
    }
}
