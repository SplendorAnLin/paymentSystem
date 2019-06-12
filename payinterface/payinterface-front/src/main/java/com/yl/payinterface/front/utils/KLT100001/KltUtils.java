package com.yl.payinterface.front.utils.KLT100001;

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

    /**
     * 认证支付回调验签
     * @param data
     * @param key
     * @param sign
     * @return
     */
    public static boolean authPay(Map<String, String> data, String key) {
        boolean flag = false;
        StringBuffer buf = new StringBuffer();
        appendSignPara(buf, "merchantId", data.get("merchantId"));
        appendSignPara(buf, "version", data.get("version"));
        appendSignPara(buf, "language", data.get("language"));
        appendSignPara(buf, "signType", data.get("signType"));
        appendSignPara(buf, "payType", data.get("payType"));
        appendSignPara(buf, "issuerId", data.get("issuerId"));
        appendSignPara(buf, "mchtOrderId", data.get("mchtOrderId"));
        appendSignPara(buf, "orderNo", data.get("orderNo"));
        appendSignPara(buf, "orderDatetime", data.get("orderDatetime"));
        appendSignPara(buf, "orderAmount", data.get("orderAmount"));
        appendSignPara(buf, "payDatetime", data.get("payDatetime"));
        appendSignPara(buf, "ext1", data.get("ext1"));
        appendSignPara(buf, "ext2", data.get("ext2"));
        appendSignPara(buf, "payResult", data.get("payResult"));
        appendLastSignPara(buf, "key", key);
        String signMsg = buf.toString();
        signMsg = MD5Hex(signMsg);
        if (signMsg.equals(data.get("signMsg"))) {
            flag = true;
        }
        return flag;
    }
}