package com.yl.payinterface.core.handle.impl.quick.dianpayer.utils;



import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.utils.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Map;

public class SymmtricCryptoUtil {

    public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
                                        String algorithm, int mode) throws GeneralSecurityException {
        String fullAlg = algorithm + "/CBC/PKCS5Padding";
        byte[] iv = initIv(fullAlg);
        return doCrypto(text, keyData, iv, fullAlg, "CBC", "PKCS5Padding", mode);
    }


    public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
                                        String algorithm, String padding, int mode)
            throws GeneralSecurityException {
        String fullAlg = algorithm + "/CBC/" + padding;
        byte[] iv = initIv(fullAlg);
        return doCrypto(text, keyData, iv, fullAlg, "CBC", padding, mode);
    }


    public static byte[] symmtricCrypto(byte[] text, byte[] keyData,
                                        String algorithm, String workingMode, String padding, int mode)
            throws GeneralSecurityException {
        String fullAlg = algorithm + "/" + workingMode + "/" + padding;
        byte[] iv = null;
        if (StringUtils.equals(workingMode, "CBC")) {
            iv = initIv(fullAlg);
        }
        return doCrypto(text, keyData, iv, fullAlg, workingMode, padding, mode);
    }

    /**
     * 获取对称加解密输入流的方法。（/CBC/PKCS5Padding模式）
     *
     * @param file
     * @param keyData   密钥数据
     * @param algorithm 对称加密算法名称。KMI默认使用3DES算法，即“DESede”. 目前KMI接受的参数有: AES, Blowfish,
     *                  DESede
     * @param mode      加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 输入流
     * @throws IOException                    文件读取错误的时候抛出该异常。
     * @throws GeneralSecurityException 加解密失败时抛出该异常。
     */
    public static InputStream getInputStream(File file, byte[] keyData,
                                             String algorithm, int mode) throws IOException,
            GeneralSecurityException {
        return getInputStream(file, keyData, algorithm, "CBC", "PKCS5Padding",
                mode);
    }


    public static InputStream getInputStream(File file, byte[] keyData,
                                             String algorithm, String workingMode, String padding, int mode)
            throws IOException, GeneralSecurityException {
        String fullAlg = algorithm + "/CBC/PKCS5Padding";
        // 初始化输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 初始化cipher
        byte[] iv = initIv(fullAlg);
        Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
        return new CipherInputStream(fileInputStream, cipher);
    }


    public static OutputStream getOutputStream(File file, byte[] keyData,
                                               String algorithm, int mode) throws IOException,
            GeneralSecurityException {
        return getOutputStream(file, keyData, algorithm, "CBC", "PKCS5Padding",
                mode);
    }


    public static OutputStream getOutputStream(File file, byte[] keyData,
                                               String algorithm, String workingMode, String padding, int mode)
            throws IOException, GeneralSecurityException {
        String fullAlg = algorithm + "/CBC/PKCS5Padding";
        // 初始化输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 初始化cipher
        byte[] iv = initIv(fullAlg);
        Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
        return new CipherOutputStream(fileOutputStream, cipher);
    }


    public static byte[] doCrypto(byte[] text, byte[] keyData, byte[] iv,
                                  String fullAlg, String workingMode, String padding, int mode)
            throws GeneralSecurityException {
        if (!StringUtils.equals(workingMode, "CBC")
                && !StringUtils.equals(workingMode, "ECB")) {
            throw new GeneralSecurityException("错误的工作模式,目前KMI只支持CBC和ECB两种工作模式");
        }

        if (!StringUtils.equals(padding, "PKCS5Padding")
                && !StringUtils.equals(padding, "NoPadding")) {
            throw new GeneralSecurityException(
                    "错误的填充模式,目前KMI只支持PKCS5Padding和NoPadding两种工作模式");
        }

        if (mode != Cipher.ENCRYPT_MODE && mode != Cipher.DECRYPT_MODE) {
            throw new GeneralSecurityException(
                    "错误的加解密标识,目前KMI只支持Cipher.ENCRYPT_MODE和Cipher.DECRYPT_MODE");
        }

        Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
        return cipher.doFinal(text);
    }


    private static Cipher getCipher(byte[] keyData, byte[] iv, String fullAlg,
                                    String workingMode, int mode) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(fullAlg);
        SecretKey secretKey = new SecretKeySpec(keyData, StringUtils
                .substringBefore(fullAlg, "/"));

        if (StringUtils.equals(workingMode, "CBC")) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(mode, secretKey, ivSpec);
        } else {
            cipher.init(mode, secretKey);
        }
        return cipher;
    }


    private static byte[] initIv(String fullAlg)
            throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(fullAlg);
        int blockSize = cipher.getBlockSize();
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; ++i) {
            iv[i] = 0;
        }
        return iv;
    }


    private static byte[] IV = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};




    /**
     * 3des 加密后base64
     *
     * @param seed
     * @param data
     * @return
     * @throws GeneralSecurityException
     */
    public static String tEncryptBase64(String seed, String data)
            throws GeneralSecurityException {
        byte[] _data = data.getBytes();
        byte[] tmp = doCrypto(_data, seed.getBytes(), IV,
                "DESede/ECB/PKCS5Padding",
                "ECB", "PKCS5Padding",
                Cipher.ENCRYPT_MODE);
        return Base64Utils.encode(tmp);
    }

    /**
     * 解码
     *
     * @param seed
     * @param data
     * @return
     * @throws GeneralSecurityException
     */

    public static String tDecryptBase64(String seed, String data) throws GeneralSecurityException {
        byte[] _data = Base64Utils.decode(data);
        byte[] tmp = doCrypto(_data, seed.getBytes(), IV,
                "DESede/ECB/PKCS5Padding",
                "ECB", "PKCS5Padding",
                Cipher.DECRYPT_MODE);
        return new String(tmp);
    }


    public static String encryptBase64(String seed, String data)
            throws GeneralSecurityException {
        byte[] _data = data.getBytes();
        byte[] tmp = doCrypto(_data, seed.getBytes(), IV,
                "DES/ECB/PKCS5Padding",
                "ECB", "PKCS5Padding",
                Cipher.ENCRYPT_MODE);
        return Base64Utils.encode(tmp);
    }


    public static String decryptOrEncrypt(String data, String key,
                                     boolean isEnc, String charset) throws
            UnsupportedEncodingException, GeneralSecurityException {

        if (isEnc) {//加密
            byte[] bytes = data.getBytes(charset);
            byte[] tmp = doCrypto(bytes, key.getBytes(), IV,
                    "DESede/ECB/PKCS5Padding",
                    "ECB", "PKCS5Padding",
                    Cipher.ENCRYPT_MODE);
            return Base64Utils.encode(tmp);
        } else { //解密
            byte[] bytes = Base64Utils.decode(data);
            byte[] tmp = doCrypto(bytes, key.getBytes(), IV,
                    "DESede/ECB/PKCS5Padding",
                    "ECB", "PKCS5Padding",
                    Cipher.DECRYPT_MODE);
            return new String(tmp, charset);
        }


    }

    public static void main(String[] args) {
    	try {
    		String info = "result_json=ze4YguEWDmNvMTWPfkjLhuxeQrj2rFN/21beqC1UfCzZb/4Nsl9YWlD1IQzZZnA5dkJPPz1gITpuxgqrh9DqUa/RMMgkzMGUseiknkDOwgSf8+VDPhcEDsiwoB2lEbSbK6qrgZnpeKvWieRhQerPL1D9M2JDiYOB6ETBUK4CnTVOnbDRDhk9Rsdt84AAl+YTwXb46kokK7KRB4/2kENY3iWMRiCSY/q8IjEasTpscNlSjNevVBAqcVD9M2JDiYOBDqVKyuc0hfLQwDRUAXEuFxs7ThbU1clanclNwJQxLsMst9Pxwto3uo+sQqvF0kJj1uES3QZ5hJ0RrOPIrm58TOypTmC6sNV7pVhCOgYPm/N9MQIZnh4lexRFvGHBTvorPCbedt4+PI8CKGeQiARcuseBpvhzjI2ZQGS7ibp+RAzh71h/7pFS1/JfQUTtU2r+Z3qcIBWI5k1Sx4l8lfVlFsCDE8K94lNbIq2oDRh0ZRM6FPQ5abHzMKGCBH9KQMqOiUOTYfGst5HHvEe2LXfFn0AF7dc1y6sGF2Cg4IvXlM7Hs9rSiJ5l5jqaMoIMD/Q6btmmjxunlYEI3mX2f9uc9Q9rj90hIFeS6Hk6v7YN84nHusCaMkUQFIk82bRFOO1bRVOYG87+5prKFbH9Zpje3b+mY8epxyWtl5sRnWU9TbQLF1Occm8xbnRNTcReSl1ueAhkC/yeQ0AfI/ivapTPMA==";
			System.out.println("result_json");
			System.out.println(info.substring(12));
    		
			String tmp = SymmtricCryptoUtil.decryptOrEncrypt(info.substring(12), "tR6QWHcUkFT6LxMfeT9CTg0k", false, "utf-8");
			System.out.println(tmp);
			System.out.println(JsonUtils.toObject(tmp, new TypeReference<Map<String, Object>>() {}));
    	} catch (Exception e) {
			e.printStackTrace();
		} 
	}
    
}
