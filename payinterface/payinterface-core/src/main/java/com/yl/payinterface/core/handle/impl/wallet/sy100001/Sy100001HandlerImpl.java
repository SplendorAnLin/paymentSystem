package com.yl.payinterface.core.handle.impl.wallet.sy100001;

import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.TreeMap;

/**
 * 盛世银支付
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/9
 */
@Service("Sy100001Handler")
public class Sy100001HandlerImpl implements WalletPayHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(Sy100001HandlerImpl.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("return_type", "json");
            req.put("api_code", "34393032");
            req.put("is_type", "alipay");
            req.put("price", "2.00");
            req.put("order_id", "TD-" + System.currentTimeMillis());
            req.put("time", String.valueOf(System.currentTimeMillis()));
            req.put("mark", "apple");
            req.put("return_url", "https://kd.alblog.cn/front/callback");
            req.put("notify_url", "https://kd.alblog.cn/front/callback");
            req.put("sign", getUrlParamsByMap(req, "d57a2b05447e305a0f67ff0e5627cc86"));
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://47.75.72.219:12301/channel/Common/mail_interface", req, false, "UTF-8", 300000);
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        return null;
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
        logger.info("盛世银支付下单原文：{}", map);
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
        logger.info("盛世银支付签名原文：{}", s);
        return MD5Util.md5(s).toUpperCase();
    }
}
