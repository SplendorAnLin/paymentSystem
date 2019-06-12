package com.yl.chat.utils;

import java.util.ResourceBundle;

/**
 * 获取随机数工具类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public class ResourceUtils {

    private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");

    /**
     * 获取随机码的长度
     *
     * @return 随机码的长度
     */
    public static String getRandCodeLength() {
        return bundle.getString("randCodeLength");
    }

    /**
     * 获取随机码的类型
     *
     * @return 随机码的类型
     */
    public static String getRandCodeType() {
        return bundle.getString("randCodeType");
    }
}