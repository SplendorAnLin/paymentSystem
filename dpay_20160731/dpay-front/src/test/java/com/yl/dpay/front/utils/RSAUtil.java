package com.yl.dpay.front.utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil {

	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	public static final String KEY_ALGORTHM="RSA";//
	public static final String SIGNATURE_ALGORITHM="MD5withRSA";
	/**
	 * RSA公钥加密
	 */
	public static String RSAencryptByPublicKey(byte[] data,String publickey){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(publickey);//BASE64解密公钥
			X509EncodedKeySpec KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory key = KeyFactory.getInstance("RSA");
			RSAPublicKey publicKey = (RSAPublicKey)key.generatePublic(KeySpec);
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			int inputLen = data.length;  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段加密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
	                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
	            } else {  
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * MAX_ENCRYPT_BLOCK;  
	        }  
	        byte[] encryptedData = out.toByteArray();  
	        out.close();  
	        return new BASE64Encoder().encode(encryptedData);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * RSA私钥解密
	 */
	public static byte[] RSAdecryptByPrivateKey(String data,String privateKey){
		try {
			byte[] dataBytes = new BASE64Decoder().decodeBuffer(data);
			byte[] keyBytes =new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory key = KeyFactory.getInstance(KEY_ALGORTHM);
			RSAPrivateKey privatekey = (RSAPrivateKey) key.generatePrivate(KeySpec);
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privatekey);
			int inputLen = dataBytes.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段解密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
	                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);  
	            } else {  
	                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * MAX_DECRYPT_BLOCK;  
	        }  
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
	        return decryptedData; 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 用私钥对信息生成数字签名
	 */
	@SuppressWarnings("restriction")
	public static String sign(String data,String privateKey){
		try {
			byte[]dataBytes = new BASE64Decoder().decodeBuffer(data);
			byte[] keyBytes =new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory key = KeyFactory.getInstance(KEY_ALGORTHM);
			PrivateKey privateKey2 = key.generatePrivate(pkcs8EncodedKeySpec);
			//用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey2);
			signature.update(dataBytes);
			return new BASE64Encoder().encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 校验数字签名
	 */
	public static boolean verify(String data,String publicKey,String sign){
		try {
			byte[] dataBytes = new BASE64Decoder().decodeBuffer(data);
			byte[] keyBytes =new BASE64Decoder().decodeBuffer(publicKey);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory key = KeyFactory.getInstance(KEY_ALGORTHM);
			PublicKey publicKey2 = key.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicKey2);
			signature.update(dataBytes);
			
			return signature.verify(new BASE64Decoder().decodeBuffer(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 产生密钥对
	 */
	public static void getKeyPairs(){
		KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			//公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			System.out.println("公钥："+new BASE64Encoder().encode(publicKey.getEncoded()));
			System.out.println("-----------------------------------------------------------------------");
			//私钥
			RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
			System.out.println("私钥："+new BASE64Encoder().encode(privateKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getKeyPairs();
	}
}
