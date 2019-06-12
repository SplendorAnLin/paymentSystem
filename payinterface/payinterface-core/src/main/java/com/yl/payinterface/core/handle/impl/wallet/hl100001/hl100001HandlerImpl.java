package com.yl.payinterface.core.handle.impl.wallet.hl100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 汇利
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/10
 */
@Service("hl100001Handler")
public class hl100001HandlerImpl implements WalletPayHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(hl100001HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        req.put("mch_id", "510111006");
        req.put("pay_type", "301");
        req.put("out_trade_no", "TD-" + System.currentTimeMillis());
        req.put("total_fee", "1000");
        req.put("notify_url", "https://kd.alblog.cn/front/callback");
        req.put("ip", "106.120.28.121");
        req.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        req.put("sign", getUrlParamsByMap(req, "ft1at2y6r4o2mb4a9hh0heawujl8qxyf"));
        try {
            System.out.println(HttpClientUtils.send(HttpClientUtils.Method.POST, "http://pay.nta434.cn/lh_pay/pay", req, false, "UTF-8", 300000));
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("mch_id", properties.getProperty("mch_id"));
            req.put("pay_type", "301");
            req.put("out_trade_no", interfaceRequestID);
            req.put("total_fee", amount);
            req.put("notify_url", properties.getProperty("notify_url"));
            req.put("ip", map.get("ClientIp"));
            req.put("nonce_str", String.valueOf(System.currentTimeMillis()));
            req.put("sign", getUrlParamsByMap(req, properties.getProperty("key")));
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("汇利下单返回报文:{}", res);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("200".equals(resPar.get("status"))){
                resParams.put("code_url", resPar.get("pay_url"));
                resParams.put("code_img_url", resPar.get("pay_url"));
                resParams.put("interfaceInfoCode", resPar.get("order_id"));
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("mch_id"));
            }
        } catch (Exception e) {
            logger.error("汇利下单异常!异常信息：{}", e);
        }
        return resParams;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }

    public static String getUrlParamsByMap(Map<String, String> map, String key) {
        logger.info("汇利下单原文：{}", map);
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
        sb.append("key=" + key);
        String s = sb.toString();
        logger.info("汇利签名原文：{}", s);
        return MD5Util.md5(s).toUpperCase();
    }
}