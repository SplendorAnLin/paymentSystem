package com.yl.payinterface.core.handle.impl.wallet.ghf100001.util;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;

import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 *
 * @author xiaojie.zhang
 * @version V1.0.0
 * @since 2018/7/25
 */
public class SignUtils {

    /**
     * 签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String sign(Map<String, String> params, String key) {
        String paramStr = buildParams(params) + "&key=" + key;
        return DigestUtils.md5DigestAsHex((paramStr).getBytes());
    }

    /**
     * 签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String signNotAnd(Map<String, String> params, String key) {
        return DigestUtils.md5DigestAsHex((buildParams(params) + key).getBytes());
    }

    /**
     * 构建k，v参数
     *
     * @param params
     * @return
     */
    public static String buildParams(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer(100);
        Set<Map.Entry<String, String>> entrys = params.entrySet();
        for (Map.Entry<String, String> entry : entrys) {
            stringBuffer.append(entry.getKey());
            stringBuffer.append("=");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("&");
        }
        stringBuffer = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        return stringBuffer.toString();
    }

    /**
     * 构建xml
     *
     * @param params
     * @return
     */
    public static String buildXml(Map<String, String> params) {
        StringBuffer sb = new StringBuffer(128);
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            sb.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue()).append("</")
                    .append(StringUtils.isBlank(entry.getKey()) ? "" : entry.getKey())
                    .append(">");
        }
        return sb.toString();
    }
}
