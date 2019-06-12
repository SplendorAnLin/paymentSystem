package com.yl.payinterface.core.handle.impl.quick.unionPay.utils;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

public class UnionPay120001EncryptionDES {
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	static String key = "0EDC06E6466B";

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] byteArr2HexStr <br>
	 * [描述] 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程 <br>
	 * [参数] 需要转换的byte数组 <br>
	 * [返回] 转换后的字符串 <br>
	 * [时间] 2015-6-16 上午10:37:03 <br>
	 ********************************************************* .<br>
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] hexStr2ByteArr <br>
	 * [描述] 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[]
	 * arrB)互为可逆的转换过程 <br>
	 * [参数] 需要转换的字符串 <br>
	 * [返回] 转换后的byte数组 <br>
	 * [时间] 2015-6-16 上午10:39:34 <br>
	 ********************************************************* .<br>
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [描述] 指定密钥构造方法 <br>
	 ********************************************************* .<br>
	 */
	public UnionPay120001EncryptionDES(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] encrypt <br>
	 * [描述] 加密字节数组 <br>
	 * [参数] 需加密的字节数组 <br>
	 * [返回] 加密后的字节数组 <br>
	 * [时间] 2015-6-16 上午10:40:56 <br>
	 ********************************************************* .<br>
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] encrypt <br>
	 * [描述] 加密字符串 <br>
	 * [参数] 需加密的字符串 <br>
	 * [返回] 加密后的字符串 <br>
	 * [时间] 2015-6-16 上午10:42:07 <br>
	 ********************************************************* .<br>
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] decrypt <br>
	 * [描述] 解密字节数组 <br>
	 * [参数] 需解密的字节数组 <br>
	 * [返回] 解密后的字节数组 <br>
	 * [时间] 2015-6-16 上午10:41:24 <br>
	 ********************************************************* .<br>
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] decrypt <br>
	 * [描述] 解密字符串 <br>
	 * [参数] 需解密的字符串 <br>
	 * [返回] 解密后的字符串 <br>
	 * [时间] 2015-6-16 上午10:36:01 <br>
	 ********************************************************* .<br>
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 
	 ********************************************************* .<br>
	 * [方法] getKey <br>
	 * [描述] 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位 <br>
	 * [参数] 构成该字符串的字节数组 <br>
	 * [返回] 生成的密钥 <br>
	 * [时间] 2015-6-16 上午10:32:20 <br>
	 ********************************************************* .<br>
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}
}