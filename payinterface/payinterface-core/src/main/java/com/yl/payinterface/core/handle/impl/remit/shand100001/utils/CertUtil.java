package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 证书工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class CertUtil {

    private static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

    private static final ConcurrentHashMap<String, Object> keys = new ConcurrentHashMap();

    public CertUtil() {
    }

    public static void init(String publicKeyPath, String privateKeyPath, String keyPassword) throws Exception {
        initPulbicKey(publicKeyPath);
        initPrivateKey(privateKeyPath, keyPassword);
    }

    public static PublicKey getPublicKey() {
        return (PublicKey)keys.get("public_key");
    }

    public static PrivateKey getPrivateKey() {
        return (PrivateKey)keys.get("private_key");
    }

    private static void initPulbicKey(String publicKeyPath) throws Exception {
        String classpathKey = "classpath:";
        if (publicKeyPath != null) {
            try {
                InputStream inputStream = null;
                if (publicKeyPath.startsWith(classpathKey)) {
                    inputStream = CryptoUtil.class.getClassLoader().getResourceAsStream(publicKeyPath.substring(classpathKey.length()));
                } else {
                    inputStream = new FileInputStream(publicKeyPath);
                }
                PublicKey publicKey = getPublicKey((InputStream)inputStream);
                keys.put("public_key", publicKey);
            } catch (Exception var4) {
                logger.error("无法加载银行公钥[{}]", new Object[]{publicKeyPath});
                logger.error(var4.getMessage(), var4);
                throw var4;
            }
        }
    }

    private static void initPrivateKey(String privateKeyPath, String keyPassword) throws Exception {
        String classpathKey = "classpath:";

        try {
            InputStream inputStream = null;
            if (privateKeyPath.startsWith(classpathKey)) {
                inputStream = CryptoUtil.class.getClassLoader().getResourceAsStream(privateKeyPath.substring(classpathKey.length()));
            } else {
                inputStream = new FileInputStream(privateKeyPath);
            }
            PrivateKey privateKey = getPrivateKey((InputStream)inputStream, keyPassword);
            keys.put("private_key", privateKey);
        } catch (Exception var5) {
            logger.error("无法加载本地私银[{}]", new Object[]{privateKeyPath});
            logger.error(var5.getMessage(), var5);
            throw var5;
        }
    }

    public static PublicKey getPublicKey(InputStream inputStream) throws Exception {
        PublicKey var5;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate oCert = (X509Certificate)cf.generateCertificate(inputStream);
            PublicKey publicKey = oCert.getPublicKey();
            var5 = publicKey;
        } catch (Exception var12) {
            throw new Exception("读取公钥异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var11) {

            }

        }
        return var5;
    }

    public static PrivateKey getPrivateKey(InputStream inputStream, String password) throws Exception {
        PrivateKey var8;
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            char[] nPassword;
            if (password != null && !password.trim().equals("")) {
                nPassword = password.toCharArray();
            } else {
                nPassword = null;
            }
            ks.load(inputStream, nPassword);
            Enumeration<String> enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String)enumas.nextElement();
            }
            PrivateKey privateKey = (PrivateKey)ks.getKey(keyAlias, nPassword);
            var8 = privateKey;
        } catch (FileNotFoundException var17) {
            throw new Exception("私钥路径文件不存在");
        } catch (IOException var18) {
            throw new Exception("读取私钥异常");
        } catch (NoSuchAlgorithmException var19) {
            throw new Exception("生成私钥对象异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException var16) {

            }

        }
        return var8;
    }
}