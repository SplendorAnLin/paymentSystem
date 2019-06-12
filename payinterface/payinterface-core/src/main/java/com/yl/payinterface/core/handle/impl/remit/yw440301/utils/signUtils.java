package com.yl.payinterface.core.handle.impl.remit.yw440301.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 伊蚊签名工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/6
 */
public class signUtils {

    /**
     * Map 转 URL 并加密
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map, String key) throws Exception {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return sign(s, key, "UTF-8");
    }


    public static String sign(String text, String key, String charset) throws Exception {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }


    private static byte[] getContentBytes(String content, String charset) {
        if (charset != null && !"".equals(charset)) {
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException arg2) {
                throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        } else {
            return content.getBytes();
        }
    }
}