package com.yl.payinterface.core.handle.impl.wallet.hq350001.utils;

import com.yl.payinterface.core.utils.MD5Util;
import java.util.Map;

/**
 * 环球支付工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/18
 */
public class HuanQiuUtils {

    /**
     * 获取MD5签名
     *
     * @param transMap
     * @return
     */
    public static String getSign(Map<String, String> transMap, String key) {
        StringBuffer arg = new StringBuffer();
        arg.append("body=" + transMap.get("body") + "&");
        arg.append("notify_url=" + transMap.get("notify_url") + "&");
        arg.append("out_order_no=" + transMap.get("out_order_no") + "&");
        arg.append("partner=" + transMap.get("partner") + "&");
        arg.append("return_url=" + transMap.get("return_url") + "&");
        arg.append("subject=" + transMap.get("subject") + "&");
        arg.append("total_fee=" + transMap.get("total_fee") + "&");
        arg.append("user_seller=" + transMap.get("user_seller"));
        return MD5Util.md5(arg.toString() + key);
    }
}