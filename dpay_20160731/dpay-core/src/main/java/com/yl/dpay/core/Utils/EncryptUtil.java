package com.yl.dpay.core.Utils;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.AESUtils;

/**
 * Encrypt工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class EncryptUtil {
	public static String password = "504178";
	public static String getStars(String plainText) {

		return replaceWithStar(plainText, 6, 4);
	}

	public static String getEncrpty(String plainText) {
		return AESUtils.encryptToHex(plainText, password);
	}

	public static String getDeEncrpty(String encrptyText) {
		return AESUtils.decryptByHex(encrptyText, password);
	}

	/** 把字符串中间部分替换成*号 */
	private static String replaceWithStar(String str, int start, int end) {
		// 长度不足，则直接原文返回
		if (StringUtils.isBlank(str) || str.length() <= (start + end)) {
			return str;
		}
		try {
			String startValue = str.substring(0, start);
			String middleValue = StringUtils.repeat("*", str.substring(start, str.length() - end).length());
			String endValue = str.substring(str.length() - end);
			return startValue + middleValue + endValue;
		} catch (Exception e) {
			return str;
		}
	}

	public static void main(String[] args) {
		System.out.println(EncryptUtil.getEncrpty("123456"));
		System.out.println(EncryptUtil.getDeEncrpty(EncryptUtil.getEncrpty("123456")));
	}

}
