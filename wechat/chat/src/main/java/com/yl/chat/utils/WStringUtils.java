package com.yl.chat.utils;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Andy
 */
public class WStringUtils {

    /**
     * 判断字符串是否为空!
     *
     * @param str 待判断的字符串
     * @return true 为空 false 不为空
     */
    public static boolean StringIsEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串列表是否为空 !
     *
     * @param args 待判断的字符列表
     * @return true 为空 false不为空
     */
    public static boolean StringItemOrEmpty(String... args) {
        boolean fig = true;
        if (args == null || args.length == 0) {
            for (String str : args) {
                fig &= !StringIsEmpty(str);
            }
        }
        return fig;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param params 待判断的字符串
     * @return true 是数字, false 不是数字
     */
    public static boolean isNumber(String params) {
        String reg = "[0-9]+";
        boolean result = Pattern.matches(reg, params);
        return result;
    }
}