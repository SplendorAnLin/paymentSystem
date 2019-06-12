package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import com.lefu.commons.utils.web.HttpClientUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class utils {

    private static final Logger logger = LoggerFactory.getLogger(utils.class);
    
    public static String sendPost(String url, String merchId, String transCode, String data){
        try {
            Map<String, String> formparams = new HashMap<>();
            formparams.put("merId", merchId);
            formparams.put("transCode", transCode);
            byte[] plainBytes = data.getBytes("UTF-8");
            byte[] aesKeyBytes = utils.getRandomStringByLength(16).getBytes("UTF-8");
            String encryptData = new String(Base64.encodeBase64(CryptoUtil.AESEncrypt(plainBytes, aesKeyBytes, "AES", "AES/ECB/PKCS5Padding", (String)null)), "UTF-8");
            String sign = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, CertUtil.getPrivateKey(), "SHA1WithRSA")), "UTF-8");
            String encryptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(aesKeyBytes, CertUtil.getPublicKey(), 2048, 11, "RSA/ECB/PKCS1Padding")), "UTF-8");
            formparams.put("encryptData", encryptData);
            formparams.put("encryptKey", encryptKey);
            formparams.put("sign", sign);
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, url, formparams, true, "UTF-8", 30000);
            logger.info("返回报文：{}", res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return null;
    }

    public static String getRandomStringByLength(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static Map<String, String> convertResultStringToMap(String result) {
        Map map = null;
        try {
            if (StringUtils.isNotBlank(result)) {
                if (result.startsWith("{") && result.endsWith("}")) {
                    result = result.substring(1, result.length() - 1);
                }
                map = parseQString(result);
            }
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;
        char openName = 0;
        if (len > 0) {
            for(int i = 0; i < len; ++i) {
                char curChar = str.charAt(i);
                if (isKey) {
                    if (curChar == '=') {
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }
                    } else {
                        if (curChar == '{') {
                            isOpen = true;
                            openName = 125;
                        }

                        if (curChar == '[') {
                            isOpen = true;
                            openName = 93;
                        }
                    }
                    if (curChar == '&' && !isOpen) {
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }
            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) throws UnsupportedEncodingException {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }

            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }

            map.put(key, temp.toString());
        }

    }
}