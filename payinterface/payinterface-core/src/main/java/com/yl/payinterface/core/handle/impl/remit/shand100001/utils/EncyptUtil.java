package com.yl.payinterface.core.handle.impl.remit.shand100001.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 杉德加密工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/26
 */
public class EncyptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncyptUtil.class);
    private String publicKeyPath;
    private String privateKeyPath;
    private String keyPassword;

    public EncyptUtil(String publicKeyPath, String privateKeyPath, String keyPassword) {
        this.publicKeyPath = publicKeyPath;
        this.privateKeyPath = privateKeyPath;
        this.keyPassword = keyPassword;
    }

//    public EncyptUtil() {
//        this.publicKeyPath = DynamicPropertyHelper.getStringProperty("dsfp.encrypt.publickey", "").get();
//        this.privateKeyPath = DynamicPropertyHelper.getStringProperty("dsfp.encrypt.privatekey", "").get();
//        this.keyPassword = DynamicPropertyHelper.getStringProperty("dsfp.encrypt.password", "").get();
//    }

    public EncyptUtil() {
        this.publicKeyPath = "/home/cer/sand/sand.cer";
        this.privateKeyPath = "/home/cer/sand/sand.pfx";
        this.keyPassword = "123456";
    }

    public List<NameValuePair> genEncryptData(String merchId, String transCode, String data) throws Exception {
        if (merchId != null && transCode != null && data != null) {
            List<NameValuePair> formparams = new ArrayList();
            formparams.add(new BasicNameValuePair("merId", merchId));
            formparams.add(new BasicNameValuePair("transCode", transCode));
            try {
                CertUtil.init(this.publicKeyPath, this.privateKeyPath, this.keyPassword);
                byte[] plainBytes = data.getBytes("UTF-8");
                String aesKey = utils.getRandomStringByLength(16);
                byte[] aesKeyBytes = aesKey.getBytes("UTF-8");
                String encryptData = new String(Base64.encodeBase64(CryptoUtil.AESEncrypt(plainBytes, aesKeyBytes, "AES", "AES/ECB/PKCS5Padding", (String)null)), "UTF-8");
                String sign = new String(Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes, CertUtil.getPrivateKey(), "SHA1WithRSA")), "UTF-8");
                String encryptKey = new String(Base64.encodeBase64(CryptoUtil.RSAEncrypt(aesKeyBytes, CertUtil.getPublicKey(), 2048, 11, "RSA/ECB/PKCS1Padding")), "UTF-8");
                formparams.add(new BasicNameValuePair("encryptData", encryptData));
                formparams.add(new BasicNameValuePair("encryptKey", encryptKey));
                formparams.add(new BasicNameValuePair("sign", sign));
                logger.info("encryptData:{}", encryptData);
                logger.info("encryptKey:{}", encryptKey);
                logger.info("sign:{}", sign);
                return formparams;
            } catch (Exception var11) {
                var11.printStackTrace();
                throw var11;
            }
        } else {
            logger.error("merchId or transCode or data is null");
            return null;
        }
    }

    public String decryptRetData(String data) throws Exception {
        Map<String, String> responseMap = convertResultStringToMap(data);
        String retEncryptKey = (String)responseMap.get("encryptKey");
        String retEncryptData = (String)responseMap.get("encryptData");
        String retSign = (String)responseMap.get("sign");
        logger.info("retEncryptKey:{}", retEncryptKey);
        logger.info("retEncryptData:{}", retEncryptData);
        logger.info("retSign:{}", retSign);
        byte[] decodeBase64KeyBytes = Base64.decodeBase64(retEncryptKey.getBytes("UTF-8"));
        byte[] merchantAESKeyBytes = CryptoUtil.RSADecrypt(decodeBase64KeyBytes, CertUtil.getPrivateKey(), 2048, 11, "RSA/ECB/PKCS1Padding");
        byte[] decodeBase64DataBytes = Base64.decodeBase64(retEncryptData.getBytes("UTF-8"));
        byte[] retDataBytes = CryptoUtil.AESDecrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", (String)null);
        logger.info("retData:{}", new String(retDataBytes, "UTF-8"));
        byte[] signBytes = Base64.decodeBase64(retSign.getBytes("UTF-8"));
        boolean isValid = CryptoUtil.verifyDigitalSign(retDataBytes, signBytes, CertUtil.getPublicKey(), "SHA1WithRSA");
        if (!isValid) {
            logger.error("报文验签不通过");
            throw new Exception("报文验签不通过");
        } else {
            logger.info("报文验签通过");
            String ret = new String(retDataBytes, "UTF-8");
            return ret;
        }
    }

    private static Map<String, String> convertResultStringToMap(String result) {
        Map<String, String> map = null;
        if (StringUtils.isNotBlank(result)) {
            if (result.startsWith("\"") && result.endsWith("\"")) {
                result = result.substring(1, result.length() - 1);
            }
            map = utils.convertResultStringToMap(result);
        }
        return map;
    }
}