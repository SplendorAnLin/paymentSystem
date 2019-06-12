package com.yl.dpay.front.common;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64加密工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
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
}
