package com.pay.sign.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public static String getMd5Str(String source) {

		byte b[] = getMd5Bytes(source);
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
//		System.out.println("result: " + buf.toString());// 32位的加密
//		System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		return buf.toString();
	}

	public static byte[] getMd5Bytes(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte b[] = md.digest();
			return b;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTimeKey(String milisec){
		return milisec.substring(6, 9)+milisec.substring(10,13);
	}
	
	public static void main(String args[]) {
		String str = String.valueOf(System.currentTimeMillis());
		System.out.println(str);
		System.out.println(getTimeKey(str));
	}
}
