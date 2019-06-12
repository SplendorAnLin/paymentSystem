package com.yl.payinterface.core.handle.impl.remit.klt100004.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class Rsa {

	static{
		   Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * 
	 * 载入公钥
	 * @param publicKeyCer
	 * @return
	 * @throws FileNotFoundException
	 * @throws CertificateException
	 */
	public static PublicKey loadPublicKey(String publicKeyCer) throws FileNotFoundException, CertificateException {
		InputStream inStream = new FileInputStream(publicKeyCer);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		PublicKey publicKey = cert.getPublicKey();
		return publicKey;
	}

	/**
	 *  载入私钥
	 * @param privateKeyPfx
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyPfx,String password) throws Exception{
		// Create a keystore object
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		
		char[] nPassword = (char[]) null;
		if ((password == null) || (password.trim().equals("")))
			nPassword = (char[]) null;
		else {
			nPassword = password.toCharArray();
		}
		// Load the file into the keystore
		keyStore.load(new FileInputStream(privateKeyPfx), nPassword);
		String aliaesName = "";
		Enumeration enumer = keyStore.aliases();
		while (enumer.hasMoreElements()) {
			aliaesName = (String) enumer.nextElement();
			if (keyStore.isKeyEntry(aliaesName)){
				return (PrivateKey) (keyStore.getKey(aliaesName, nPassword));
			}
		}
		throw new Exception("没有找到匹配私钥:"+privateKeyPfx);
		
	}
	
	/**
	 * RSA公钥加密
	 * @param input
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static  byte[] encrypt(byte[] input,PublicKey publicKey) throws Exception{
		Cipher cipher = null;
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey); // 用公钥pubKey初始化此Cipher
		return cipher.doFinal(input);                 //加密
	}
	
	/**
	 * RSA私钥解密
	 * @param input
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static  byte[] decrypt(byte[] input, PrivateKey privateKey)  throws Exception{
		Cipher cipher = null;
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey); // 用私钥priKey初始化此cipher
		return cipher.doFinal(input); 
	}
	
	/**
	 * 产生SHA1WithRSA 签名
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static  byte[] signSha1WithRsa(byte[] plainText,PrivateKey privateKey) throws Exception {
		return  sign(plainText, SignatureMethod.SHA1WithRSA,privateKey);
	}
	
	/**
	 * 验证SHA1WithRSA 签名信息
	 * @param signature
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static  boolean verfySha1WithRsa(byte[] signature,byte[] plainText,PublicKey publicKey) throws Exception {
		return verfy(signature, plainText, SignatureMethod.SHA1WithRSA,publicKey);
	}
	
	/**
	 * 产生MD5WithRSA签名信息
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static  byte[] signMd5WithRsa(byte[] plainText,PrivateKey privateKey) throws Exception {
		return sign(plainText, SignatureMethod.MD5WithRSA,privateKey);
	}
	
	/**
	 * 验证MD5WithRSA签名信息
	 * @param signature
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static  boolean verfyMd5WithRsa(byte[] signature,byte[] plainText,PublicKey publicKey) throws Exception {
		return verfy(signature, plainText, SignatureMethod.MD5WithRSA,publicKey);
	}
	
	/**
	 *  产生RSA签名信息（指定签名方法）
	 * @param plainText
	 * @param signatureMethod
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static  byte[] sign(byte[] plainText,String signatureMethod ,PrivateKey privateKey) throws Exception {
		Signature sig = Signature.getInstance(signatureMethod,new BouncyCastleProvider());
		sig.initSign(privateKey);  //用此私钥初始化此签名对象Signature
		sig.update(plainText);          
		return sig.sign();         //签名
	}
	
	/**
	 * 验证RSA签名信息（指定签名方法）
	 * @param signature
	 * @param plainText
	 * @param signatureMethod
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static  boolean verfy(byte[] signature,byte[] plainText,String signatureMethod,PublicKey publicKey) throws Exception{
		Signature sig = Signature.getInstance(signatureMethod);
		sig.initVerify(publicKey);  // 用公钥初始化此用于Signature对象。
		sig.update(plainText);           
		return sig.verify(signature);     //签名验证 
	}
}

class SignatureMethod {
	 public static String MD5WithRSA = "MD5WithRSA";
    public static String SHA1WithRSA = "SHA1WithRSA";
    
}
