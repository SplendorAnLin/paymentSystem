package com.yl.payinterface.core.handle.impl.quick.kingpass100001.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 九派工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/2
 */
public class KingPassUtils {

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    public static String mapToStr(Map<String, String> map) {
        String result = "";

        Map.Entry entry;
        for (Iterator var2 = map.entrySet().iterator(); var2.hasNext(); result = result + (String) entry.getKey() + "=" + (String) entry.getValue() + "&") {
            entry = (Map.Entry) var2.next();
        }

        return result.substring(0, result.length() - 1);
    }

    public static Map<String, String> strToMap(String res) {
        Map<String, String> map = new TreeMap<>();
        String[] ans = res.split("&");
        int var4 = ans.length;

        for (String s : ans) {
            map.put(s.split("=")[0], s.split("=")[1]);
        }

        return map;
    }
}
