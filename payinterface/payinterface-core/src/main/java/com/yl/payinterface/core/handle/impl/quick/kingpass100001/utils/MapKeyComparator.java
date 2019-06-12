package com.yl.payinterface.core.handle.impl.quick.kingpass100001.utils;

import java.util.Comparator;

/**
 * 比较器
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/5
 */
class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}