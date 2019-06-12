package com.yl.online.gateway.utils.kingpassUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Shark
 * @Description
 * @Date 2018/5/30 17:45
 */
public class KingPassUtils {

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
