package com.yl.payinterface.core.handle.impl.remit.ht440301.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 辉腾代付签名工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/29
 */
public class signUtils {

    private static final Logger logger = LoggerFactory.getLogger(signUtils.class);

    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    public static String getSign(Map<String, String> map, String key) throws Exception {
        logger.info("辉腾代付下单原文：{}", map);
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        String s = sb.toString();
        s = s.substring(0, s.length() - 1);
        logger.info("签名原文：{}", s);
        return signByPrivate(s, readFile(key, "UTF-8"), "UTF-8");
    }


    public static String readFile(String filePath, String charSet) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        try {
            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            return new String(byteBuffer.array(), charSet);
        } finally {
            fileInputStream.close();
        }
    }

    public static String signByPrivate(String content, String privateKey, String input_charset) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        PrivateKey privateKeyInfo = getPrivateKey(privateKey);
        return signByPrivate(content, privateKeyInfo, input_charset);
    }

    public static String signByPrivate(String content, PrivateKey privateKey,
                                       String input_charset) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes(input_charset));
        return Base64.encode(signature.sign());
    }

    /**
     * 得到私钥
     *
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = buildPKCS8Key(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static byte[] buildPKCS8Key(String privateKey) throws IOException {
        if (privateKey.contains("-----BEGIN PRIVATE KEY-----")) {
            return Base64.decode(privateKey.replaceAll("-----\\w+ PRIVATE KEY-----", ""));
        } else if (privateKey.contains("-----BEGIN RSA PRIVATE KEY-----")) {
            final byte[] innerKey = Base64.decode(privateKey.replaceAll("-----\\w+ RSA PRIVATE KEY-----", ""));
            final byte[] result = new byte[innerKey.length + 26];
            System.arraycopy(Base64.decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKY="), 0, result, 0, 26);
            System.arraycopy(BigInteger.valueOf(result.length - 4).toByteArray(), 0, result, 2, 2);
            System.arraycopy(BigInteger.valueOf(innerKey.length).toByteArray(), 0, result, 24, 2);
            System.arraycopy(innerKey, 0, result, 26, innerKey.length);
            return result;
        } else {
            return Base64.decode(privateKey);
        }
    }
}