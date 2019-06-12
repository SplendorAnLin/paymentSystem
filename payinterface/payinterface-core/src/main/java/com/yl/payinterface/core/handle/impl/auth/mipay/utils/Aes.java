package com.yl.payinterface.core.handle.impl.auth.mipay.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class Aes {

	/*
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	public Aes() {
	}

	// 加密
	public static String encrypt(String sSrc, String sKey) throws Exception {
		if (null != sSrc && !"".equals(sSrc)) {
			if (null == sKey || "".equals(sKey.trim())) {
				sKey = "0102030405060708";
			}
			String ivParameter = "0102030405060708";
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return Base64Utils.encode(encrypted);// 此处使用BASE64做转码。 
		} else {
			return "";
		}

	}

	// 解密
	public static String decrypt(String sSrc, String sKey) throws Exception {
		try {
			if (null != sSrc && !"".equals(sSrc)) {
				if (null == sKey || "".equals(sKey.trim())) {
					sKey = "0102030405060708";
				}
				String ivParameter = "0102030405060708";
				byte[] raw = sKey.getBytes("ASCII");
				SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
				byte[] encrypted1 = Base64Utils.decode(sSrc);// 先用base64解密
				System.out.println(encrypted1.length);
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} else {
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
