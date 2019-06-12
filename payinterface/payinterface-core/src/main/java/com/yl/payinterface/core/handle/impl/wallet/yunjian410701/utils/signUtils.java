package com.yl.payinterface.core.handle.impl.wallet.yunjian410701.utils;

import com.yl.payinterface.core.utils.MD5Util;
import java.util.Map;

/**
 * 云尖 加密工具
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/22
 */
public class signUtils {

    public static String getSign(Map<String, Object> params, String key){
        String sign = "";
        StringBuffer sb = new StringBuffer();
        sb.append("version=" + params.get("version"));
        sb.append("&customerid=" + params.get("customerid"));
        sb.append("&total_fee=" + params.get("total_fee"));
        sb.append("&sdorderno=" + params.get("sdorderno"));
        sb.append("&notifyurl=" + params.get("notifyurl"));
        sb.append("&returnurl=" + params.get("returnurl"));
        sb.append("&" + key);
        sign = MD5Util.md5(sb.toString());
        return sign;
    }

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
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
        return s;
    }
}