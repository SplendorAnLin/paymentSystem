package com.yl.payinterface.core.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;

/**
 * 通用工具类
 *
 * @author 聚合支付有限公司
 * @since 2017年08月29日
 * @version V1.0.0
 */
public class CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(CommonUtils.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("初始化 缓存中心地址 配置失败:", e);
        }
    }
	
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
    
//    public static Map<String, String> getCnaps(String bankName) {
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			String words = "";
//			words = "words=" + URLEncoder.encode(bankName, "UTF-8");
//			words += "&1=1";
//			String url = (String) prop.get("cachecenter.service.url")
//					+ "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
//			String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
//			JsonNode cnapsNodes = com.lefu.commons.utils.lang.JsonUtils.getInstance().readTree(resData);
//			for (JsonNode cnapsNode : cnapsNodes) {
//				map.put("cnapsCode", cnapsNode.get("code").getTextValue());
//				map.put("bankName", cnapsNode.get("name").getTextValue());
//                map.put("bankCode", cnapsNode.get("providerCode").getTextValue());
//				break;
//			}
//		} catch (Exception e) {
//			logger.error("获取联行信息异常:",e);
//		}
//		return map;
//	}

    /**
     * 卡识别
     *
     * @param accountNo 银行卡号
     * @return
     */
    public static Recognition recognition(String accountNo) {
        try {
            String cachecenterIin = (String) prop.get("cachecenter.service.url");
            StringBuffer url = new StringBuffer(cachecenterIin + "/iin/recognition.htm?cardNo=");
            url.append(accountNo);
            url.append(
                    "&fields=code&fields=length&fields=panLength&fields=providerCode&fields=cardType&fields=cardName&fields=agencyCode&fields=agencyName");
            JSONObject object = HttpUtil.getJsonObject(url.toString());
            if (object != null) {
                Recognition recognition = new Recognition();
                recognition.setAgencyCode(object.getString("agencyCode"));
                recognition.setAgencyName(object.getString("agencyName"));
                recognition.setCardName(object.getString("cardName"));
                recognition.setCardType(object.getString("cardType"));
                recognition.setCode(object.getString("code"));
                recognition.setProviderCode(object.getString("providerCode"));
                recognition.setLength(object.getString("length"));
                recognition.setPanLength(object.getString("panLength"));
                return recognition;
            }
        } catch (Exception e) {
            logger.info("卡号：{}，卡识别报错：{}", accountNo, e);
        }
        return null;
    }

    /**
     * 获取清分行信息
     *
     * @param providerCode 提供方编码
     * @return
     */
    public static List<Cnaps> cnapsCode(String providerCode) {
        List<Cnaps> list = new ArrayList<>();
        try {
            String cachecenterCnaps = (String) prop.get("cachecenter.service.url");
            StringBuffer url = new StringBuffer(cachecenterCnaps + "/cnaps/suggestSearch.htm?");
            url.append("word=&fields=clearingBankCode&fields=providerCode&providerCode=");
            url.append(providerCode);
            JSONArray array = HttpUtil.getJsonArray(url.toString());
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Cnaps cnaps = new Cnaps();
                    cnaps.setCode(object.getString("code"));
                    cnaps.setClearingBankCode(object.getString("clearingBankCode"));
                    cnaps.setProviderCode(object.getString("providerCode"));
                    list.add(cnaps);
                }
            }
        } catch (Exception e) {
            logger.info("获取清分行信息报错：{}", e);
        }
        return list;
    }
}