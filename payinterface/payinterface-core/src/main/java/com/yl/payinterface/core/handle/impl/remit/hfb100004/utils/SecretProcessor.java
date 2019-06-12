package com.yl.payinterface.core.handle.impl.remit.hfb100004.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * 证书加密工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class SecretProcessor {

	/**
	 * 加密类型
	 */
	private static String SignType = "SHA1withRSA";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final String RSA = "RSA/ECB/PKCS1Padding";

	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	/**
	 * 获取证书公钥
	 * 
	 * @param certPath
	 *            证书路径
	 * @return 公钥
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	protected static PublicKey getPublicKey(String certPath) throws KeyStoreException, NoSuchProviderException,
            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		// 加载证书
		// 读取证书文件的输入流
		InputStream certin = new FileInputStream(certPath);
		return getPublicKey(certin);
	}

	/**
	 * 获取证书公钥
	 * 
	 * @param certPath
	 *            证书路径
	 * @return 公钥
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	protected static PublicKey getPublicKey(InputStream certin) throws KeyStoreException, NoSuchProviderException,
            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
		// 证书格式为X509
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
		// 读取证书文件的输入流
		Certificate certificate = certificateFactory.generateCertificate(certin);
		certin.close();
		// 从证书中得到公钥
		PublicKey publicKey = certificate.getPublicKey();
		return publicKey;
	}

	/**
	 * 获取私钥
	 * 
	 * @param storePath
	 *            秘钥库路径
	 * @param storePass
	 *            秘钥库访问密码
	 * @param keyPass
	 *            秘钥
	 * @return 私钥
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	protected static PrivateKey getPrivateKey(String storePath, String keyPass)
			throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException,
            IOException, UnrecoverableKeyException {
		// 根据密钥库类型JKS得到密钥库实例
		InputStream in = new FileInputStream(storePath);
		return getPrivateKey(in, keyPass);
	}

	/**
	 * 获取私钥
	 * 
	 * @param storePath
	 *            秘钥库路径
	 * @param storePass
	 *            秘钥库访问密码
	 * @param keyPass
	 *            秘钥
	 * @return 私钥
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	protected static PrivateKey getPrivateKey(InputStream in, String keyPass)
			throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException,
            IOException, UnrecoverableKeyException {
		// 根据密钥库类型JKS得到密钥库实例
		// KeyStore keyStore = KeyStore.getInstance("JKS","SUN");
		KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
		keyStore.load(in, keyPass.toCharArray());
		in.close();
		// 根据alias从keystone中取出密钥对
		PrivateKey privateKey = null;
		Enumeration<?> enums = keyStore.aliases();
		while (enums.hasMoreElements()) {
			String keyAlias = (String) enums.nextElement();
			if (keyStore.isKeyEntry(keyAlias)) {
				privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());
			}
		}
		return privateKey;
	}

	/**
	 * 公钥加密
	 * 
	 * @param publicKey
	 *            公钥
	 * @param content
	 *            需加密内容
	 * @return 公钥加密之后的密文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 * @throws NoSuchProviderException
	 */
	protected static String encryptByPublickey(Key publicKey, String content)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException, NoSuchProviderException {
		String encryptContent = "";

		Cipher cipher = Cipher.getInstance(RSA, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] data = content.getBytes("utf-8");
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
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

		encryptContent = base64Encoder(encryptedData);
		return encryptContent;
	}

	/**
	 * 私钥解密
	 * 
	 * @param privateKey
	 *            私钥
	 * @param content
	 *            需解密内容
	 * @return 私钥解密之后的明文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchProviderException
	 */
	protected static String decryptByPrivateKey(PrivateKey privateKey, String content)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		String decryptContent = "";

		Cipher cipher = Cipher.getInstance(RSA, "BC");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// MAX_DECRYPT_BLOCK = cipher.getBlockSize();
		byte[] encryptedData = base64Decoder(content);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();

		decryptContent = new String(decryptedData, "UTF-8");
		return decryptContent;
	}

	/**
	 * 私钥生成签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param content
	 *            签名内容
	 * @return 签名
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws SignatureException
	 */
	protected static String sign(PrivateKey privateKey, String content)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		Signature signature = Signature.getInstance(SignType);
		signature.initSign(privateKey);
		signature.update(content.getBytes("UTF-8"));
		byte[] signByte = signature.sign();
		String sign = base64Encoder(signByte);
		return sign;
	}

	/**
	 * 公钥验签
	 * 
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名
	 * @param content
	 *            签名内容
	 * @return 验证结果
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	protected static boolean verify(PublicKey publicKey, String sign, String content)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		// 验签
		Signature signature = Signature.getInstance(SignType);
		signature.initVerify(publicKey);
		signature.update(content.getBytes("UTF-8"));
		boolean verified = signature.verify(base64Decoder(sign));
		return verified;
	}

	/**
	 * 返回base64解密之后的byte数组
	 * 
	 * @param content
	 *            base64密文
	 * @return
	 * @throws IOException
	 */
	private static byte[] base64Decoder(String content) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(content);
	}

	/**
	 * 返回base64加密之后的字符串
	 * 
	 * @param content
	 *            需要加密的byte数组
	 * @return
	 */
	private static String base64Encoder(byte[] content) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(content).replaceAll("\r", "").replaceAll("\n", "");
	}
}