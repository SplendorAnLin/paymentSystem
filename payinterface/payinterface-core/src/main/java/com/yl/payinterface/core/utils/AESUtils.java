package com.yl.payinterface.core.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class AESUtils {
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES";
	/**
	 * 加密
	 *
	 * @param data
	 *            待加密内容
	 * @param key
	 *            加密秘钥
	 * @return 十六进制字符串
	 */
	public static String encrypt(String data, String key) {
		if (key.length() < 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		} else if (key.length() > 16) {
			key = key.substring(0, 16);
		}
		try {
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
			byte[] byteContent = data.getBytes(CHAR_ENCODING);
			cipher.init(Cipher.ENCRYPT_MODE, genKey(key));// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return new String(Base64.encodeBase64(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            待解密内容(十六进制字符串)
	 * @param key
	 *            加密秘钥
	 * @return
	 */
	public static String decrypt(String data, String key) {
		if (key.length() < 16) {
			throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
		} else if (key.length() > 16) {
			key = key.substring(0, 16);
		}
		try {
			byte[] decryptFrom = Base64.decodeBase64(data);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, genKey(key));// 初始化
			byte[] result = cipher.doFinal(decryptFrom);
			return new String(result, "utf-8"); // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建加密解密密钥
	 *
	 * @param key
	 *            加密解密密钥
	 * @return
	 */
	private static SecretKeySpec genKey(String key) {
		SecretKeySpec secretKey;
		try {
			secretKey = new SecretKeySpec(key.getBytes(CHAR_ENCODING), AES_ALGORITHM);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, AES_ALGORITHM);
			return seckey;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("genKey fail!", e);
		}
	}
}