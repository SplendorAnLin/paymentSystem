package com.yl.payinterface.front.utils.GHF100001;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 签名工具类
 *
 * @author xiaojie.zhang
 * @version V1.0.0
 * @since 2018/7/30
 */
public class SignUtils {

    /**
     * 签名校验
     *
     * @param params
     * @param key
     * @return
     */
    public static boolean checkSign(Map<String, String> params, String key){
        Map<String, String> signParams = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = params.entrySet();

        for (Map.Entry<String, String> entry : entries){
            if(!"sign".equals(entry.getKey())){
                signParams.put(entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : signParams.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue()) && !"sign".equalsIgnoreCase(entry.getKey())) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sb.append("key=").append(key);

        String sign = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        if (sign.equalsIgnoreCase(params.get("sign"))) {
            return true;
        }
        return false;
    }

    /**
     * 签名
     *
     * @param params
     * @param key
     * @return
     */
    public static boolean checkSignNotAnd(Map<String, String> params, String key) {
        return DigestUtils.md5DigestAsHex((buildParams(params) + key).getBytes()).equalsIgnoreCase(params.get("sign"));
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

        Map<String, String> signParams = new TreeMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()){
            if(!"sign".equals(entry.getKey())){
                signParams.put(entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<String, String> entry : signParams.entrySet()) {
            if(entry.getKey().indexOf("sign") > -1 || entry.getKey().indexOf("Sign") > -1){
                continue;
            }
            if(StringUtils.isBlank(entry.getValue())){
                continue;
            }
            stringBuffer.append(entry.getKey());
            stringBuffer.append("=");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("&");
        }
        stringBuffer = stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        return stringBuffer.toString();
    }
}
