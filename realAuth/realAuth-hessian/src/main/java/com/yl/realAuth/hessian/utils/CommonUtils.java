package com.yl.realAuth.hessian.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证工具类
 * @author Shark
 * @since 2015年7月7日
 */
public class CommonUtils {

	/** 把字符串中间部分替换成*号 */
	public static String replaceWithStar(String str, int start, int end) {
		String startValue = str.substring(0, start);
		String middleValue = str.substring(start, end).replaceAll("\\d", "*");
		String endValue = str.substring(end);
		return startValue + middleValue + endValue;
	}

	/**
	 * 加密
	 * @param value 原文串
	 * @param start 起始位置
	 * @param end 终止位置
	 * @return 密文串
	 */
	public static String encrypt(String value) {
		String key = "908";
		return AESUtil.encryptToHex(value, key);
	}

	/**
	 * 解密
	 * @param value 密文串
	 * @return 原文串
	 */
	public static String deEncrypt(String value) {
		String key = "908";
		return AESUtil.decryptByHex(value, key);
	}

	/**
	 * 卡号加密
	 * @param cardNo 卡号
	 */
	public static Map<String, String> cardNoEncrypt(String cardNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bankCardNo", replaceWithStar(cardNo, 4, cardNo.length() - 4));
		map.put("bankCardNoEncrypt", encrypt(cardNo));
		return map;
	}

	/**
	 * 身份证加密
	 * @param certNo 身份证号
	 */
	public static Map<String, String> certNoEncrypt(String certNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("certNo", replaceWithStar(certNo, 6, 14));
		map.put("certNoEncrypt", encrypt(certNo));
		return map;
	}

	public static void main(String[] args) {

		System.out.println(deEncrypt("BF5A01C22EDC69FB6227E6A163332A107AF6AC3F5BB15E692E494CF24BFCE999"));
	}
}
