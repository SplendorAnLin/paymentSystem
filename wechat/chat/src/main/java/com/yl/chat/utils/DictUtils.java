package com.yl.chat.utils;

import com.lefu.commons.cache.bean.Dictionary;
import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.yl.chat.Constant;
import java.util.List;
import java.util.Map;

/**
 * 字典匹配
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年5月31日
 */
public class DictUtils {

    /**
     * 获取字典的类型所有集合
     *
     * @param DictType
     * @return
     */
    public static DictionaryType getDict(String DictType) {
        if (DictType != null && !DictType.isEmpty()) {
            return DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + DictType);
        }
        return null;
    }

    /**
     * 集合匹配字典
     *
     * @param list     转换的集合
     * @param dictType 字典的类型
     * @param name     匹配的字段
     * @return
     */
    public static List<Map<String, Object>> listOfDict(List<Map<String, Object>> list, String dictType, String name) {
        return listOfDict(list, dictType, name, "_CN");
    }

    /**
     * 集合匹配字典
     *
     * @param list      转换的集合
     * @param dictType  字典的类型
     * @param name      匹配的字段
     * @param diySuffix 自定义后缀
     * @return
     */
    public static List<Map<String, Object>> listOfDict(List<Map<String, Object>> list, String dictType, String name, String diySuffix) {
        List<Dictionary> dictTypeList = getDict(dictType).getDictionaries();
        if (dictTypeList.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean exists = false;
                for (int j = 0; j < dictTypeList.size(); j++) {
                    if (list.get(i).get(name).equals(dictTypeList.get(j).getKey().toString().replace(Constant.DICTIONARY, ""))) {
                        list.get(i).put(name + diySuffix, dictTypeList.get(j).getValue());
                        exists = true;
                    }
                }
                if (!exists) {//匹配不到值
                    list.get(i).put(name + diySuffix, null);
                }
            }
        }
        return list;
    }

    /**
     * @param info
     * @param dictType 字典的类型
     * @param name     匹配的字段
     * @return
     */
    public static Map<String, Object> mapOfDict(Map<String, Object> info, String dictType, String name) {
        return mapOfDict(info, dictType, name, "_CN");
    }

    /**
     * @param info
     * @param dictType  字典的类型
     * @param name      匹配的字段
     * @param diySuffix 自定义后缀
     * @return
     */
    public static Map<String, Object> mapOfDict(Map<String, Object> info, String dictType, String name, String diySuffix) {
        List<Dictionary> dictTypeList = getDict(dictType).getDictionaries();
        if (dictTypeList.size() > 0) {
            boolean exists = false;
            for (int j = 0; j < dictTypeList.size(); j++) {
                if (info.get(name).equals(dictTypeList.get(j).getKey().toString().replace(Constant.DICTIONARY, ""))) {
                    info.put(name + diySuffix, dictTypeList.get(j).getValue());
                    exists = true;
                }
            }
            if (!exists) {//匹配不到值
                info.put(name + diySuffix, null);
            }
        }
        return info;
    }
}