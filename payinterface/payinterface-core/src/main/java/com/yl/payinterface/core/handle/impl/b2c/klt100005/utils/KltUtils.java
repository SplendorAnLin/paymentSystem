package com.yl.payinterface.core.handle.impl.b2c.klt100005.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;

/**
 * 开联通工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class KltUtils {

    private static final Logger logger = LoggerFactory.getLogger(KltUtils.class);

    /**
     * 对数据做MD5摘要
     *
     * @param aData 源数据
     * @return 摘要结果
     * @throws SecurityException
     */
    public static String MD5Hex(String aData) throws SecurityException {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = bytes2HexString(md.digest(aData.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("MD5运算失败{}", e);
        }
        return resultString;
    }

    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 追加参数
     *
     * @param buf
     * @param key
     * @param value
     */
    public static void appendSignPara(StringBuffer buf, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            buf.append(key).append('=').append(value).append('&');
        }
    }

    /**
     * 末尾参数
     * @param buf
     * @param key
     * @param value
     */
    public static void appendLastSignPara(StringBuffer buf, String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            buf.append(key).append('=').append(value);
        }
    }

    public static String authPay(Map<String, String> data, String key) {
        String signMsg = "";
        StringBuffer buf = new StringBuffer();
        appendSignPara(buf, "inputCharset", data.get("inputCharset"));
        appendSignPara(buf, "pickupUrl", data.get("pickupUrl"));
        appendSignPara(buf, "receiveUrl", data.get("receiveUrl"));
        appendSignPara(buf, "version", data.get("version"));
        appendSignPara(buf, "language", data.get("language"));
        appendSignPara(buf, "signType", data.get("signType"));
        appendSignPara(buf, "merchantId", data.get("merchantId"));
        appendSignPara(buf, "orderNo", data.get("orderNo"));
        appendSignPara(buf, "orderAmount", data.get("orderAmount"));
        appendSignPara(buf, "orderCurrency", data.get("orderCurrency"));
        appendSignPara(buf, "orderDatetime", data.get("orderDatetime"));
        appendSignPara(buf, "productName", data.get("productName"));
        appendSignPara(buf, "payType", data.get("payType"));
        appendLastSignPara(buf, "key", key);
        signMsg = buf.toString();
        logger.info("开联通 认证支付签名原串：{}", signMsg);
        signMsg = MD5Hex(signMsg);
        return signMsg;
    }
}