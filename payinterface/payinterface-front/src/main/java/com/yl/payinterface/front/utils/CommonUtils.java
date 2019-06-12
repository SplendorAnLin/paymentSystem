package com.yl.payinterface.front.utils;

import com.lefu.commons.utils.lang.StringUtils;

import java.util.*;

/**
 * 通用工具类
 *
 * @author 聚合支付有限公司
 * @since 2017年08月29日
 * @version V1.0.0
 */
public class CommonUtils {

    public static String convertMap2Str(Map params){
        ArrayList<String> paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);
        Iterator<String> iterator = paramNames.iterator();
        StringBuffer signSource = new StringBuffer(50);
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                if (iterator.hasNext()) signSource.append("&");
            }
        }
        return signSource.toString();
    }

    public static Map<String, String> kvStr2Map(String str){
        String [] tmp;
        String [] strTmp;
        Map<String, String> params = new HashMap<>();
        if(StringUtils.notBlank(str)){
            if(str.indexOf("&") > -1){
                tmp = str.split("&");
                for (String s : tmp){
                    if (s.indexOf("=") > -1){
                        strTmp = s.split("=");
                        params.put(strTmp[0], strTmp.length == 1 ? "" : strTmp[1]);
                    }
                }
            }else if (str.indexOf("=") > -1){
                tmp = str.split("=");
                params.put(tmp[0], tmp.length == 1 ? "" : tmp[1]);
            }
        }
        return params;
    }
}
