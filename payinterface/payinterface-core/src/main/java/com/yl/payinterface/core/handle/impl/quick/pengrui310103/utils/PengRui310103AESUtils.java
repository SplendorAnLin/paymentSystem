package com.yl.payinterface.core.handle.impl.quick.pengrui310103.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 全时优服 AES加密工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/2/28
 */
public class PengRui310103AESUtils {

    public static Logger logger = LoggerFactory.getLogger(PengRui310103AESUtils.class);

    private static final String cipherAlgorithm = "AES/CBC/PKCS5Padding";

    private static final String keyAlgorithm = "AES";

    private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }


    /**
     * 使用Base64解密算法解密字符串 return
     */
    public static byte [] decode(String encodeStr) {
        byte[] b = encodeStr.getBytes(StandardCharsets.UTF_8);
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        b = base64.decode(b);
        return b;
    }

    /**
     * 解密
     * @param encodeStr
     * @param keyText
     * @return
     */
    public static String decode(byte [] encodeStr, String keyText) {
        return AESDecrypt(encodeStr,keyText);
    }

    @Deprecated
    private static String  AESDecrypt(byte [] encodeStr, String keyText) {
        byte[] bytes = AESDecrypt(encodeStr, getUTF8Bytes(keyText), keyAlgorithm, cipherAlgorithm,
                keyText);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * AES解密
     *
     * @param encryptedBytes
     *            密文字节数组，不经base64编码
     * @param keyBytes
     *            密钥字节数组
     * @param keyAlgorithm
     *            密钥算法
     * @param cipherAlgorithm
     *            加解密算法
     * @param IV
     *            随机向量
     * @return 解密后字节数组
     * @throws RuntimeException
     */
    @Deprecated
    private static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm,
                                     String cipherAlgorithm, String IV) {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new RuntimeException("AES密钥长度不合法");
            }
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (IV != null) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return decryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("没有[%s]此类加密算法", cipherAlgorithm),e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm),e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("无效密钥参数",e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("错误填充模式",e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("解密块大小不合法",e);
        }
    }

    /**
     * AES 加密
     *
     * @param plainText
     * @param keyText
     * @return
     */
    public static byte[] encode(String plainText, String keyText){
        try {
            return AESEncrypt(plainText, keyText);
        } catch (Exception e){

        }
        return null;
    }

    @Deprecated
    private static byte[] AESEncrypt(String plainText, String keyText) throws Exception {
        byte[] bytes = AESEncrypt(getUTF8Bytes(plainText), getUTF8Bytes(keyText), keyAlgorithm, cipherAlgorithm,
                keyText);
        return bytes;
    }

    /**
     * AES解密
     *
     * @param plainBytes      密文字节数组，不经base64编码
     * @param keyBytes        密钥字节数组
     * @param keyAlgorithm    密钥算法
     * @param cipherAlgorithm 加解密算法
     * @param IV              随机向量
     * @return 解密后字节数组
     * @throws RuntimeException
     */
    @Deprecated
    private static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm,
                                     String IV) {
        try {
            // AES密钥长度为128bit、192bit、256bit，默认为128bit
            if (keyBytes.length % 8 != 0 || keyBytes.length < 16 || keyBytes.length > 32) {
                throw new RuntimeException("AES密钥长度不合法");
            }

            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if (null != IV) {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            byte[] encryptedBytes = cipher.doFinal(plainBytes);
            return encryptedBytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("没有[%s]此类加密算法", cipherAlgorithm),e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(String.format("没有[%s]此类填充模式", cipherAlgorithm),e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("无效密钥参数",e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("错误填充模式",e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("加密块大小不合法",e);
        }
    }
}
