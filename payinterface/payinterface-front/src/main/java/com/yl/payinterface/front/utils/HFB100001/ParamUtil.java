package com.yl.payinterface.front.utils.HFB100001;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 参数构建工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class ParamUtil {

	private static final String SIGN_PARAM_SEPARATOR = "&";
	
	/**
	 * 解析request param到map中
	 * @param request
	 * @return
	 */
	public static TreeMap<String, String> getParamMap(HttpServletRequest request){
		TreeMap<String, String> transMap = new TreeMap<String, String>();
		Enumeration<String> enu = request.getParameterNames();
		String t = null;
		while(enu.hasMoreElements()){
			t = enu.nextElement();
			transMap.put(t, request.getParameter(t));
		}
		return transMap;
	}
	
	 /**
     * 解析queryString
     * @param queryString 请求参数字符串
     * @param enc		     编码
     * @return
     */
    public static Map<String, String> getParamsMap(String queryString, String enc) {
        Map<String, String> paramsMap = new TreeMap<String, String>();
        if (queryString != null && queryString.length() > 0) {
            int ampersandIndex, lastAmpersandIndex = 0, tmpIndex = 0;
            String subStr, param, value;
            do {
                ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
                if (ampersandIndex > 0) {
                    subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
                    lastAmpersandIndex = ampersandIndex;
                } else {
                    subStr = queryString.substring(lastAmpersandIndex);
                }
                tmpIndex = subStr.indexOf('=');
                param = subStr.substring(0,tmpIndex);
                value = subStr.substring(tmpIndex+1);
                try {
                    value = URLDecoder.decode(value, enc);
                } catch (UnsupportedEncodingException ignored) {
                }
                if(!"".equals(param))
                    paramsMap.put(param, value);
            } while (ampersandIndex > 0);
        }
        return paramsMap;
    }

	/**
     * 组织签名需要的交易数据
     * @param map
     * @return
     */
    public static String getSignMsg(Map map){
        StringBuffer sb = new StringBuffer();
        String key = "";
        Collection<String> keyset= map.keySet();
        List list = new ArrayList<String>(keyset);
        Collections.sort(list);
        for(Object s : list){
            key = s.toString();
            sb.append(key).append("=").append(map.get(key)).append(SIGN_PARAM_SEPARATOR);
        }
        if(sb.indexOf(SIGN_PARAM_SEPARATOR) > -1)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}