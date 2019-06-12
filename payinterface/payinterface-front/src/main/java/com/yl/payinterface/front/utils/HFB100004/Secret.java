package com.yl.payinterface.front.utils.HFB100004;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 处理证书加密类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class Secret {
	private SecretConfig csc;
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public Secret(SecretConfig csc) throws Exception {
		this.setCsc(csc);
		try {
			if (csc.isStream()) {
				publicKey = SecretProcessor.getPublicKey(csc.getPublicKeyIn());
				privateKey = SecretProcessor.getPrivateKey(csc.getPrivateKeyIn(), csc.getKeyPass());
			} else {
				publicKey = SecretProcessor.getPublicKey(csc.getPublicKeyPath());
				privateKey = SecretProcessor.getPrivateKey(csc.getPrivateKeyPath(), csc.getKeyPass());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String encrypt(String content) throws Exception {
		return SecretProcessor.encryptByPublickey(publicKey, content);
	}

	public String decrypt(String content) throws Exception {
		return SecretProcessor.decryptByPrivateKey(privateKey, content);
	}

	public String sign(String content) throws Exception {
		return SecretProcessor.sign(privateKey, content);
	}

	public boolean verify(String content, String sign) throws Exception {
		return SecretProcessor.verify(publicKey, sign, content);
	}

	public SecretConfig getCsc() {
		return csc;
	}

	public void setCsc(SecretConfig csc) {
		this.csc = csc;
	}
}