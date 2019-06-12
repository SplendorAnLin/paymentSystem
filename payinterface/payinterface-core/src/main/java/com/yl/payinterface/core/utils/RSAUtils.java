package com.yl.payinterface.core.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Rsa工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class RSAUtils {  
	  
    /**
     * 加密算法RSA 
     */  
    public static final String KEY_ALGORITHM = "RSA";
      
    /**
     * 签名算法 
     */  
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
  
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
     * @param data 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     *  
     * @return 
     * @throws Exception
     */  
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);  
        signature.update(data);  
        return Base64Utils.encode(signature.sign());  
    }  
  
    /**
     * 校验数字签名
     * @param data 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @param sign 数字签名 
     *  
     * @return 
     * @throws Exception
     *  
     */  
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);  
        signature.update(data);  
        return signature.verify(Base64Utils.decode(sign));  
    }  
  
    /**
     * 私钥解密
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
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
     * @param encryptedData 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
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
     * @param data 源数据 
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
     * @param data 源数据 
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
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception
     */  
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());  
    }  
  
    public static void main(String[] args) throws Exception {
    	String puk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDfJg1TAJNe3F11pfgmG5lLbqZW/PN+i4PdgHPAoLd9vsFB9Rm0ahtWnCx8k3BYoiDZX0dn+CRXX1vikQ3AKAwfi9Aq6uwiSJJ65BEBN/DIfffECqKnDg/JlZd4QYxZJtLr8Z+NJwFppT5E8KJ8qNRRgUQSEWQ65Z0ZWJCaQZ4kQIDAQAB";
        String str = "{\"txnAmt\":\"50000\",\"orderId\":\"TD20171209101088660452\",\"txnTime\":\"20171209153348\",\"frontUrl\":\"http://m.bank-pay.com:5555/payinterface-front/LDZQuickPay42010101D0FrtComplete/quickPayD0\",\"backUrl\":\"http://m.bank-pay.com:5555/payinterface-front/LDZQuickPay42010101D0Complete/quickPayD0\",\"accNo\":\"6222300499932815\",\"realName\":\"张晓杰\",\"identityNo\":\"130728199208250012\",\"phoneNo\":\"17600126400\",\"toBankName\":\"工商银行\",\"toAccNo\":\"6212260200041743341\",\"userFee\":\"300\",\"extFiled\":\"TD-20171209-101088660452\",\"toBankCode\":\"102100099996\",\"terminalMerCode\":\"\",\"terminalMerName\":\"\",\"terminalMerState\":\"\",\"terminalMerCity\":\"\",\"terminalMerAddress\":\"\",\"t0drawFee\":\"\",\"t0drawRate\":\"\",\"t1consFee\":\"\",\"t1consRate\":\"\",\"version\":\"0.1\"}";
        System.out.println(Base64Utils.encode(encryptByPublicKey(str.getBytes(), puk)));
	}
}
