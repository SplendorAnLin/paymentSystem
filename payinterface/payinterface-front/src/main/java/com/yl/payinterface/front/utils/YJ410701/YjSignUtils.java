package com.yl.payinterface.front.utils.YJ410701;

import com.yl.payinterface.front.utils.MD5Util;
import java.util.Map;

/**
 * 云尖 签名验证工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/22
 */
public class YjSignUtils {

    /**
     * 验证异步通知签名
     * @param key
     * @param sign
     * @param params
     * @return
     */
    public static boolean checkSign(String key, String sign, Map<String, String> params){
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append("customerid=" + params.get("customerid"));
        sb.append("&status=" + params.get("status"));
        sb.append("&sdpayno=" + params.get("sdpayno"));
        sb.append("&sdorderno=" + params.get("sdorderno"));
        sb.append("&total_fee=" + params.get("total_fee"));
        sb.append("&paytype=" + params.get("paytype"));
        sb.append("&" + key);
        if (sign.equals(MD5Util.md5(sb.toString()))) {
            flag = true;
        }
        return flag;
    }
}