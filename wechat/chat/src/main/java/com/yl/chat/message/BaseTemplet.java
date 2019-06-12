package com.yl.chat.message;

import java.util.TreeMap;

/**
 * 模板消息内容和字体颜色
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年6月13日
 */
public class BaseTemplet {

    /**
     * 获取参数
     *
     * @param color 文字的颜色
     * @param value 文字的值
     * @return 获取已经封装好的 TreeMap 集合
     */
    public static TreeMap<String, String> ThempleItem(String value, String color) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("value", value);
        params.put("color", color);
        return params;
    }

    public static TreeMap<String, String> ThempleItem(String value) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("value", value);
        params.put("color", "#222");
        return params;
    }
}