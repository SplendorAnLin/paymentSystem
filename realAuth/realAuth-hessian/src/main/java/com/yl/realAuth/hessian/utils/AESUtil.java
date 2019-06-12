package com.yl.realAuth.hessian.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具类
 * @version 2015年6月18日 下午1:52:52
 * @author rui.wang
 */
public class AESUtil {
	// public static Logger logger = LoggerFactory.getLogger(AESUtil.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String AES_KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @return 十六进制字符串
	 */
	public static String encryptToHex(String content, String password) {
		try {
			return parseByte2HexStr(encrypt(content.getBytes(DEFAULT_CHARSET), password.getBytes(DEFAULT_CHARSET)));
		} catch (UnsupportedEncodingException e) {
			// logger.error(null, e);
		}
		return null;
	}

	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @return
	 */
	public static byte[] encrypt(byte[] content, byte[] password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password);
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {
			// logger.error(null, e);
		}
		return null;
	}

	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static String decryptByHex(String content, String password) {
		try {
			return new String(decrypt(parseHexStr2Byte(content), password.getBytes(DEFAULT_CHARSET)), DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			// logger.error(null, e);
		}
		return null;
	}

	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, byte[] password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(AES_KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password);
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (Exception e) {
			// logger.error(null, e);
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		if (buf == null) return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}