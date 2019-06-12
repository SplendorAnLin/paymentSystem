package com.yl.online.gateway.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
@SuppressWarnings("restriction")
public class Base64Utils {

	/**
	 * Description: BASE64加密
	 * @param str
	 * @return
	 */
	public static String encode(byte[] str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str);
	}
	
	/**
	 * Description: BASE64加密
	 * @param str
	 * @return
	 */
	public static String encodeFilter(byte[] str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str).replaceAll("[\r|\n|\t]", "");
	}

	/**
	 * Description: BASE64解密
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str) {
		byte[] src = null;
		try {
			if (str == null || "".equals(str)) {
				return null;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			src = decoder.decodeBuffer(str);
		} catch (IOException e) {
			return null;
		}
		return src;
	}
	
	public static void main(String[] args) {
		String s = "123asdasdasd洒家打开啦江苏道路喀什假大空拉萨啊实打实大苏打实打实大苏打撒旦撒大苏打撒大苏打实打实大苏打实打实大大实打实的的接口";
		System.out.println(encodeFilter(s.getBytes()));
		System.out.println(encode(s.getBytes()));
	}
}
