package com.yl.dpay.core.Utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class AESUtils {
	/**
	 * 加密
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encryptBase64(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return Base64Utils.encode(parseByte2HexStr(result).getBytes()); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String base64Decrypt(String content, String password) {
		try {
			byte[] byteContent = parseHexStr2Byte(new String(Base64Utils.decode(content)));
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return new String(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
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

	/**
	 * 加密
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt2(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		String s = "123 ewqe fdg蔡少芬的方式防守对方发 发    =-=12-= eqweqweqweqw123123撒谎大师级的卡号三等奖";
		String password = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAOJcfx+76Jf77vwanFoFTy28d9Z3"
				+ "HoDbWJpWCRDpWNOX9Q/9+P9AKOOXFBucAQddP4Z26x9AV76Fh4paAtKlNXZI4sHkb8WzACDOrNfP"
				+ "bcyrAdZtxZjtVwNMJgkL8NuTqZMS00d8j55Dm0Tgqq1iHw6i1ze6iH6MtwNgiKYuRcMtAgMBAAEC"
				+ "gYEAtkg0GrLwF8vJ5HvtYlSaMUNu4WalSrDYBdi4jkTKwgHBV3J2m93nz3Zuu9kBvxNpP3ft2Qmw"
				+ "Xv0aa+AWG9jsVeMTdSlfXwk29JKkbdK2pu++LmpVMd7yvjCVNNOjqrPCg0OnfqLal/gqI7n5muM1"
				+ "YUReYASFovyxF2CbN3OypSECQQD99fFcXnA6w76R7M335FdyKgb8+ymtpNzBpGlB1MrVDEquX9sw"
				+ "symbb96Q2uvwUy0UjhtnWfzNi8RopJV+VMV1AkEA5C3RmXl+IsKij4TfBzt89kP1LVtp6seten8x"
				+ "I5lsKQyX8wG+Wbf+Q9rWoYzq2giXVdCEseFbhp4+PMEp3P132QJBALKt/dk9YL0Xx9cnw2qsL5JV"
				+ "W4FWvWY83k0n/h6hNuxar5isPK2xAI8qUG+ed+4ot0kFCubWe3Wq5s+xLLUrs5kCQQDFkWqfcZOm"
				+ "s1If5FNcJsT/Uj4yxKkD8Vqi6wh4I3qEXHQgo8zWUDqnNA+NF7+tg6zNT9vlTRR7jf6jijkeMJWZ"
				+ "AkEAw6JBZ8tlqjKuBYWeEKH8rRAtpEYczF+zQ0QzHcqrpeu1SQpz7/mWK3WjUiTxZ2I+AQXw55wb" + "03h2CNHIm+wFzw==";
		// 加密
		System.out.println("加密前：" + s);
		String tt4 = encryptBase64(s, password);
		System.out.println(new String(tt4));

		// 解密
		String decryptResult = base64Decrypt(tt4, password);
		System.out.println("解密后：" + new String(decryptResult));
	}
}