package com.yl.payinterface.core.handle.impl.wallet.zh100001.utils;

import com.alibaba.fastjson.JSONObject;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * 众横QT工具类
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/9
 */
public class Zh100001Utils {
    
    private static final Logger logger = LoggerFactory.getLogger(Zh100001Utils.class);

    public static String getUrlParamsByMap(Map<String, String> map, String key) {
        logger.info("众横QT下单原文：{}", map);
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        String s = sb.toString().substring(0, sb.toString().length() - 1);
        s += key;
        logger.info("众横QT签名原文：{}", s);
        return MD5Util.md5(s).toUpperCase();
    }

    /**
     * 发送HTTP POST
     * @param map
     * @param url
     * @param code
     * @return
     */
    public static String httpSend(Map<String, String> map, String url, String code){
        try{
            CloseableHttpResponse response;
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("X-QF-SIGN", map.get("sign"));
            headerMap.put("X-QF-APPCODE", code);
            map.remove("sign");
            List<NameValuePair> params = new ArrayList<>(map.size());
            if (!map.isEmpty()) {
                for (Map.Entry<String, String> parameter : map.entrySet()) {
                    params.add(new BasicNameValuePair(parameter.getKey(), parameter
                            .getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            if (!headerMap.isEmpty()) {
                for (Map.Entry<String, String> vo : headerMap.entrySet()) {
                    httpPost.setHeader(vo.getKey(), vo.getValue());
                }
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (response != null && response.getEntity() != null) {
                    JSONObject resultObject = JSONObject.parseObject(StringEscapeUtils
                            .unescapeJava(EntityUtils.toString(response.getEntity(),
                                    "UTF-8")));
                    logger.info("众横QT下单返回报文：{}", resultObject.toString());
                    if (resultObject.getString("respcd").equals("0000")) {
                        return resultObject.toString();
                    }
                }
            }
        } catch(Exception e){
            logger.error("异常信息：{}", e);
        }
        return null;
    }
}