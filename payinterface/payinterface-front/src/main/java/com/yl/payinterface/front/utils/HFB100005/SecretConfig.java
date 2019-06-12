package com.yl.payinterface.front.utils.HFB100005;

import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 证书路径配置信息
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class SecretConfig {
	private String publicKeyPath;
	private InputStream publicKeyIn;
	private String pulbicKeyInStr;
	private String privateKeyPath;
	private InputStream privateKeyIn;
	private String privateKeyInStr;
	private String keyPass;
	private boolean isStream = false;

	public SecretConfig(String publicKeyPath, String privateKeyPath, String keyPass, boolean isStream) {
		this.publicKeyPath = publicKeyPath;
		this.privateKeyPath = privateKeyPath;
		this.keyPass = keyPass;
	}

	public SecretConfig(InputStream publicKeyIn, InputStream privateKeyIn, String keyPass) {
		this.publicKeyIn = publicKeyIn;
		this.privateKeyIn = privateKeyIn;
		this.keyPass = keyPass;
		isStream = true;
	}

	public SecretConfig(String publicKeyInStr, String privateKeyInStr, String keyPass) {
		this.publicKeyIn = new ByteArrayInputStream(Base64.decode(publicKeyInStr));
		this.privateKeyIn = new ByteArrayInputStream(Base64.decode(privateKeyInStr));
		this.keyPass = keyPass;
		isStream = true;
	}

	public boolean isStream() {
		return isStream;
	}

	public String getPublicKeyPath() {
		return this.publicKeyPath;
	}

	public String getPrivateKeyPath() {
		return this.privateKeyPath;
	}

	public String getKeyPass() {
		return keyPass;
	}

	public InputStream getPublicKeyIn() {
		return publicKeyIn;
	}

	public InputStream getPrivateKeyIn() {
		return privateKeyIn;
	}

	public String getPulbicKeyInStr() {
		return pulbicKeyInStr;
	}

	public void setPulbicKeyInStr(String pulbicKeyInStr) {
		this.pulbicKeyInStr = pulbicKeyInStr;
	}

	public String getPrivateKeyInStr() {
		return privateKeyInStr;
	}

	public void setPrivateKeyInStr(String privateKeyInStr) {
		this.privateKeyInStr = privateKeyInStr;
	}
}