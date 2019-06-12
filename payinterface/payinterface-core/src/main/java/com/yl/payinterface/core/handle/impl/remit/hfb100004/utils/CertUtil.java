package com.yl.payinterface.core.handle.impl.remit.hfb100004.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * 证书加载功能类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class CertUtil {

    private static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

	private String keyStorePath;
	private InputStream keyStoreIn;
	private String cerPath;
	private InputStream cerIn;
	private String keyPass;
	private Secret secret;
	private boolean existPk = false;
	private String publicKeyContent;
	private String privateKeyContent;

	private String initType = "0";
	private static CertUtil certUtil = new CertUtil();

	public static CertUtil getInstance() {
		return certUtil;
	}

	private CertUtil() {
		this.initType = "0";
	}

	/**
	 * 初始化参数
	 */
	private void init(String keyStorePath, String cerPath, String keyPass) {
		try {
			SecretConfig csc = null;
			int type = Integer.valueOf(initType);
			switch (type) {
			case 0:
				keyStoreIn = getBasePathAsStream(keyStorePath);
				cerIn = getBasePathAsStream(cerPath);
				csc = cerIn != null && keyStoreIn != null ? new SecretConfig(cerIn, keyStoreIn, keyPass)
						: new SecretConfig(cerPath, keyStorePath, keyPass, false);
				existPk = true;
				break;
			case 1:
				keyStoreIn = getBasePathAsStream(keyStorePath);
				cerIn = getBasePathAsStream(cerPath);
				csc = cerIn != null && keyStoreIn != null ? new SecretConfig(cerIn, keyStoreIn, keyPass)
						: new SecretConfig(cerPath, keyStorePath, keyPass, false);
				existPk = true;
				break;
			case 2:
				if (null == publicKeyContent || "".equals(publicKeyContent.trim()) || null == privateKeyContent
						|| "".equals(privateKeyContent) || null == keyPass || "".equals(keyPass)) {
					throw new Exception("证书配置参数不能为空");
				}
				csc = new SecretConfig(publicKeyContent, privateKeyContent, keyPass);
				existPk = true;
				break;
			default:
                logger.error("请选择正确的初始化方式");
				break;
			}
			if (null == csc) {
				throw new Exception("证书加密初始化失败");
			}
			secret = new Secret(csc);
		} catch (Exception e) {
            logger.error("初始化加载证书处理失败," + e);
		}
	}

	URL locateFromClasspath(String resourceName) {
		URL url = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader != null) {
			url = loader.getResource(resourceName);

			if (url != null) {
                logger.error("Loading configuration from the context classpath (" + resourceName + ",url=" + url + ")");
			}
		}

		if (url == null) {
			url = ClassLoader.getSystemResource(resourceName);
			if (url != null) {
                logger.error("Loading configuration from the system classpath (" + resourceName + ")");
			}
		}
		return url;
	}

	InputStream getBasePathAsStream(String resourcesName) {
		return this.getClass().getClassLoader().getResourceAsStream(resourcesName);
	}

	String getBasePath(URL url) {
		if (url == null) {
			return null; // file:\
		}
		String s = url.toString();
		try {
			File file = new File(url.toURI());
			s = file.exists() ? file.getAbsolutePath() : null;
		} catch (URISyntaxException e) {

		}
		return s;
	}

	/**
	 * 签名
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String sign(String data, String keyStorePath, String cerPath, String keyPass) throws Exception {
		init(keyStorePath, cerPath, keyPass);
		return secret.sign(data);
	}

	/**
	 * 验签
	 * 
	 * @param data
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public boolean verify(String data, String sign, String keyStorePath, String cerPath, String keyPass) throws Exception {
		init(keyStorePath, cerPath, keyPass);
		sign = sign.replaceAll(" ", "+");
		return secret.verify(data, sign);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String data, String keyStorePath, String cerPath, String keyPass) throws Exception {
		init(keyStorePath, cerPath, keyPass);
		return secret.encrypt(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String data, String keyStorePath, String cerPath, String keyPass) throws Exception {
		init(keyStorePath, cerPath, keyPass);
		data = data.replaceAll(" ", "+");
		return secret.decrypt(data);
	}


	public String getPublicKeyContent() {
		return publicKeyContent;
	}

	public String getPrivateKeyContent() {
		return privateKeyContent;
	}
	
	public String getKeyPass() {
		return keyPass;
	}

	public CertUtil setCfgFileName(String cfgFileName) {
		this.initType = "0";
		return certUtil;
	}

	public CertUtil setProperties(Properties properties) {
		this.initType = "1";
		return certUtil;
	}

	public CertUtil setPublicKeyContent(String publicKeyContent, String privateKeyContent, String keyPass) {
		this.publicKeyContent = publicKeyContent;
		this.privateKeyContent = privateKeyContent;
		this.keyPass = keyPass;
		this.initType = "2";
		return certUtil;
	}
}