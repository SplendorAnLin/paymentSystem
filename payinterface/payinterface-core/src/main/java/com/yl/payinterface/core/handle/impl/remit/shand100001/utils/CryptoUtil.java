package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * 签名处理工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class CryptoUtil {

    public static Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

    public CryptoUtil() {
    }

    public static byte[] digitalSign(byte[] plainBytes, PrivateKey privateKey, String signAlgorithm) throws Exception {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(privateKey);
            signature.update(plainBytes);
            byte[] signBytes = signature.sign();
            return signBytes;
        } catch (NoSuchAlgorithmException var5) {
            throw new Exception(String.format("数字签名时没有[%s]此类算法", signAlgorithm));
        } catch (InvalidKeyException var6) {
            throw new Exception("数字签名时私钥无效", var6);
        } catch (SignatureException var7) {
            throw new Exception("数字签名时出现异常", var7);
        }
    }

    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, PublicKey publicKey, String signAlgorithm) throws Exception {
        boolean isValid = false;

        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(publicKey);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException var6) {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm), var6);
        } catch (InvalidKeyException var7) {
            throw new Exception("验证数字签名时公钥无效", var7);
        } catch (SignatureException var8) {
            throw new Exception("验证数字签名时出现异常", var8);
        }
    }

    public static boolean verifyDigitalSign(byte[] plainBytes, byte[] signBytes, X509Certificate cert, String signAlgorithm) throws Exception {
        boolean isValid = false;

        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(cert);
            signature.update(plainBytes);
            isValid = signature.verify(signBytes);
            return isValid;
        } catch (NoSuchAlgorithmException var6) {
            throw new Exception(String.format("验证数字签名时没有[%s]此类算法", signAlgorithm));
        } catch (InvalidKeyException var7) {
            throw new Exception("验证数字签名时公钥无效", var7);
        } catch (SignatureException var8) {
            throw new Exception("验证数字签名时出现异常", var8);
        }
    }

    public static byte[] RSAEncrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if (plainBytes.length % encryptBlockSize != 0) {
            ++nBlock;
        }

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(1, publicKey);
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);

            for(int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }

                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException var13) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException var14) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException var15) {
            throw new Exception("无效密钥", var15);
        } catch (IllegalBlockSizeException var16) {
            throw new Exception("加密块大小不合法", var16);
        } catch (BadPaddingException var17) {
            throw new Exception("错误填充模式", var17);
        } catch (IOException var18) {
            throw new Exception("字节输出流异常", var18);
        }
    }

    public static byte[] RSADecrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int decryptBlockSize = keyByteSize - reserveSize;
        int nBlock = encryptedBytes.length / keyByteSize;

        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(2, privateKey);
            ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);

            for(int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }

                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                outbuf.write(decryptedBlock);
            }

            outbuf.flush();
            outbuf.close();
            return outbuf.toByteArray();
        } catch (NoSuchAlgorithmException var13) {
            throw new Exception(String.format("没有[%s]此类解密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException var14) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException var15) {
            throw new Exception("无效密钥", var15);
        } catch (IllegalBlockSizeException var16) {
            throw new Exception("解密块大小不合法", var16);
        } catch (BadPaddingException var17) {
            throw new Exception("错误填充模式", var17);
        } catch (IOException var18) {
            throw new Exception("字节输出流异常", var18);
        }
    }

    public static PublicKey toPublicKey(BigInteger exponent, BigInteger modulus) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modulus, exponent);
        PublicKey key = keyFactory.generatePublic(pubSpec);
        return key;
    }

    public static PrivateKey toPrivateKey(BigInteger exponent, BigInteger modulus) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec prispec = new RSAPrivateKeySpec(modulus, exponent);
        PrivateKey key = keyFactory.generatePrivate(prispec);
        return key;
    }

    public static byte[] AESEncrypt(byte[] plainBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws Exception {
        try {
            if (keyBytes.length % 8 == 0 && keyBytes.length >= 16 && keyBytes.length <= 32) {
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);
                SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
                if (StringUtils.trimToNull(IV) != null) {
                    IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                    cipher.init(1, secretKey, ivspec);
                } else {
                    cipher.init(1, secretKey);
                }

                byte[] encryptedBytes = cipher.doFinal(plainBytes);
                return encryptedBytes;
            } else {
                throw new Exception("AES密钥长度不合法");
            }
        } catch (NoSuchAlgorithmException var8) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException var9) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException var10) {
            throw new Exception("无效密钥", var10);
        } catch (InvalidAlgorithmParameterException var11) {
            throw new Exception("无效密钥参数", var11);
        } catch (BadPaddingException var12) {
            throw new Exception("错误填充模式", var12);
        } catch (IllegalBlockSizeException var13) {
            throw new Exception("加密块大小不合法", var13);
        }
    }

    public static byte[] AESDecrypt(byte[] encryptedBytes, byte[] keyBytes, String keyAlgorithm, String cipherAlgorithm, String IV) throws Exception {
        try {
            if (keyBytes.length % 8 == 0 && keyBytes.length >= 16 && keyBytes.length <= 32) {
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);
                SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
                if (IV != null && StringUtils.trimToNull(IV) != null) {
                    IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                    cipher.init(2, secretKey, ivspec);
                } else {
                    cipher.init(2, secretKey);
                }

                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                return decryptedBytes;
            } else {
                throw new Exception("AES密钥长度不合法");
            }
        } catch (NoSuchAlgorithmException var8) {
            throw new Exception(String.format("没有[%s]此类加密算法", cipherAlgorithm));
        } catch (NoSuchPaddingException var9) {
            throw new Exception(String.format("没有[%s]此类填充模式", cipherAlgorithm));
        } catch (InvalidKeyException var10) {
            throw new Exception("无效密钥", var10);
        } catch (InvalidAlgorithmParameterException var11) {
            throw new Exception("无效密钥参数", var11);
        } catch (BadPaddingException var12) {
            throw new Exception("错误填充模式", var12);
        } catch (IllegalBlockSizeException var13) {
            throw new Exception("解密块大小不合法", var13);
        }
    }

    public static byte[] hexString2ByteArr(String hexStr) {
        return (new BigInteger(hexStr, 16)).toByteArray();
    }

    public static final byte[] hexStrToBytes(String s) {
        byte[] bytes = new byte[s.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

    public static String bytes2string(byte[] bytes, int radix) {
        int size = 2;
        if (radix == 2) {
            size = 8;
        }

        StringBuilder sb = new StringBuilder(bytes.length * size);

        for(int i = 0; i < bytes.length; ++i) {
            int integer;
            for(integer = bytes[i]; integer < 0; integer += 256) {
                ;
            }

            String str = Integer.toString(integer, radix);
            sb.append(StringUtils.leftPad(str.toUpperCase(), size, "0"));
        }

        return sb.toString();
    }
}