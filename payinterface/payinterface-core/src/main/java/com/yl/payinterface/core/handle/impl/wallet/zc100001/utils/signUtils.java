package com.yl.payinterface.core.handle.impl.wallet.zc100001.utils;

import com.yl.payinterface.core.utils.MD5Util;
import java.util.Map;

/**
 * 加密工具
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/22
 */
public class signUtils {

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map, String key) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return MD5Util.md5(s + "&key=" + key).toUpperCase();
    }
}