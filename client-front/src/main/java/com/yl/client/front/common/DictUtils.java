package com.yl.client.front.common;

import com.yl.client.front.Constant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger log = LoggerFactory.getLogger(DictUtils.class);

    /**
     * 获取字典的类型所有集合
     *
     * @param DictType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<JSONObject> getDict(String DictType) {
        if (DictType != null && !DictType.isEmpty()) {
            return Constant.DICTS.get(DictType).getJSONArray("dictionaries");
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
        List<JSONObject> dictTypeList = getDict(dictType);
        if (dictTypeList != null && dictTypeList.size() > 0 && list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean exists = false;
                for (int j = 0; j < dictTypeList.size(); j++) {
                    if (dictTypeList.get(j).getString("key").replace(Constant.DICTIONARY, "").equals(list.get(i).get(name).toString())) {
                        list.get(i).put(name + diySuffix, dictTypeList.get(j).getString("value"));
                        exists = true;
                        break;
                    }
                }
                if (!exists) {//匹配不到值
                    list.get(i).put(name + diySuffix, "");
                    log.info("字典:{}匹配不到该词:{}", dictType, list.get(i).get(name).toString());
                }
            }
        } else {
            log.info("字典缺失:{}", dictType);
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
        List<JSONObject> dictTypeList = getDict(dictType);
        if (dictTypeList.size() > 0) {
            boolean exists = false;
            for (int j = 0; j < dictTypeList.size(); j++) {
                if (dictTypeList.get(j).getString("key").toString().replace(Constant.DICTIONARY, "").equals(info.get(name))) {
                    info.put(name + diySuffix, dictTypeList.get(j).getString("value"));
                    exists = true;
                }
            }
            if (!exists) {//匹配不到值
                info.put(name + diySuffix, "");
                log.info("字典:{}匹配不到该词:{}", dictType, info.get(name));
            }
        } else {
            log.info("字典缺失:{}", dictType);
        }
        return info;
    }

    public static String keyOfDict(String dictType, String name) {
        List<JSONObject> dictTypeList = getDict(dictType);
        if (dictTypeList.size() > 0) {
            for (int j = 0; j < dictTypeList.size(); j++) {
                if (name.equals(dictTypeList.get(j).getString("key").toString().replace(Constant.DICTIONARY, ""))) {
                    return dictTypeList.get(j).getString("value");
                }
            }
        }
        log.info("字典:{}匹配不到该词:{}", dictType, name);
        return "";
    }
}
