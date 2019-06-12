package com.yl.payinterface.front.utils.AL100001;

import com.lefu.commons.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 爱农网关支付工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/7
 */
public class AlPayUtils {

    private static final Logger logger = LoggerFactory.getLogger(AlPayUtils.class);

    public static String getSign(Map<String, String> map, String key) throws Exception{
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        String source = getWebForm(map, false, true);
        return Base64.encode(new MessageDigestUtils("MD5").sign((source + key).getBytes("UTF-8")));
    }

    public static String getWebForm(Map<String, String> map, boolean isEncode, boolean flag) {
        if (null == map || map.keySet().size() == 0) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String str = (value != null ? value : "");
            if (flag) {
                if ("signMethod".equals(key) || "signature".equals(key)) {
                    continue;
                }
            }
            try {
                if (isEncode) {
                    str = URLEncoder.encode(str, "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("map转换异常", e);
            }
            url.append(entry.getKey()).append("=").append(str).append("&");
        }
        String strURL = url.toString();
        if ("&".equals("" + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return strURL;
    }

    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<>(0);
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
}