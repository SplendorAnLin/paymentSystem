package com.yl.payinterface.core.handle.impl.remit.zft100002.utils;

import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

/**
 * 签名工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/6
 */
public class ZftSignUtils {

    /**
     * Map 转 URL 并加密
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map, String key) {
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
        return MD5Util.md5(s + key);
    }
}