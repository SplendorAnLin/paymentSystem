package com.yl.payinterface.core.handle.impl.wallet.hs100001;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 花生QQ扫码
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/13
 */
@Service("walletHs100001Handler")
public class Hs100001HandlerImpl implements WalletPayHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(Hs100001HandlerImpl.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("version", "2.1");
            req.put("orgNo", "8180600384");
            req.put("custId", "18061100002006");
            req.put("custOrderNo", "TD-" + System.currentTimeMillis());
            req.put("tranType", "0906");
            req.put("payAmt", "101");
            req.put("backUrl", "https://kd.alblog.cn/front/callback");
            req.put("frontUrl", "https://kd.alblog.cn/front/callback");
            req.put("goodsName", "shopping");
            req.put("sign", getUrlParamsByMap(req, "39AB18F00AA43154DBFCE9CD2325A96A"));
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://121.196.196.167:8889/tran/cashier/pay.ac", req, false, "UTF-8", 300000);
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        String interfaceType = map.get("interfaceType");
        Map<String, String> req = new TreeMap<>();
        Map<String, String> resParams = new HashMap<>();
        try {
            req.put("version", "2.1");
            req.put("orgNo", properties.getProperty("orgNo"));
            req.put("custId", properties.getProperty("custId"));
            req.put("custOrderNo", interfaceRequestID);
            if (InterfaceType.JDH5.name().equals(interfaceType)) {
                req.put("tranType", "0906");
            } else if (InterfaceType.QQNATIVE.name().equals(interfaceType)) {
                req.put("tranType", "0702");
            }
            req.put("payAmt", amount);
            req.put("backUrl", properties.getProperty("backUrl"));
            req.put("goodsName", interfaceType);
            req.put("sign", getUrlParamsByMap(req, properties.getProperty("key")));
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("花生返回报文：{}", res);
            Map<String, String> resMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("01".equals(resMap.get("contentType"))) {
                resParams.put("code_url", resMap.get("busContent"));
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resMap.get("busContent"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("custId"));
            } else if ("03".equals(resMap.get("contentType"))) {
                resParams.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("custId"));
                CacheUtils.setEx(interfaceRequestID, resMap.get("busContent"), 43200);
            }
        } catch (Exception e) {
            logger.error("花生下单异常!异常信息:{}", e);
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
        logger.info("花生下单原文：{}", map);
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
        logger.info("签名原文：{}", s);
        return MD5Util.md5(s);
    }
}