package com.yl.payinterface.core.handle.impl.remit.lucky100002.utils;

import java.security.MessageDigest;

/**
 * MD5 加密工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/10
 */
public class Md5Util {

	public static String md5(String key, String... args) throws Exception {
		StringBuffer text = new StringBuffer();
		for(String s : args)
		{
			if(s != null)
			{
				text.append(s);
			}
		}
		text.append(key);
		byte[] bytes = text.toString().getBytes("UTF-8");
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		bytes = messageDigest.digest();
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < bytes.length; i ++)
		{
			if((bytes[i] & 0xff) < 0x10)
			{
				result.append("0");
			}

			result.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return result.toString().toUpperCase();
	}
}