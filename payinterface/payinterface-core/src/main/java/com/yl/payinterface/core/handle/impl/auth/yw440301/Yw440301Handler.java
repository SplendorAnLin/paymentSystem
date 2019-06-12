package com.yl.payinterface.core.handle.impl.auth.yw440301;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.auth.yw440301.utils.signUtils;
import com.yl.payinterface.core.handle.impl.remit.hs100001.utils.HttpUtil;
import com.yl.payinterface.core.handler.AuthPayHandler;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 伊蚊 快捷
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/4
 */
@Service("yw440301Handler")
public class Yw440301Handler implements AuthPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yw440301Handler.class);

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        Map<String, String> res = new HashMap<>();
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = map.get("amount");
        String productName = map.get("productName");
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "placeQuickPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", properties.getProperty("sysMerchNo"));
            req.put("outOrderNo", interfaceRequestID);
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("orderAmt", amount);
            req.put("orderTitle", productName);
            req.put("orderDetail", productName);
            req.put("frontUrl", properties.getProperty("frontUrl"));
            req.put("backUrl", properties.getProperty("backUrl"));
            req.put("settleCycle", "T1");
            req.put("selectFinaCode", "ICBC");
            req.put("tranAttr", "DEBIT");
            req.put("tranSubAttr", "FRONT");
            req.put("userId", map.get("ownerId"));
            req.put("clientIp", "192.168.0.1");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("signType", "MD5");
            logger.info("伊蚊快捷下单报文：{}", req);
            String resString = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("伊蚊快捷下单返回报文：{}", resString);
            Map<String, Object> resPar = JsonUtils.toObject(resString, new TypeReference<Map<String, Object>>() {
            });
            if ("0000".equals(resPar.get("retCode"))) {
                res.put("responseCode", "0000");
                res.put("orderNo", resPar.get("tranNo").toString());
                res.put("gateway", "htmlSubmit");
                res.put("interfaceRequestID", interfaceRequestID);
                res.put("html", resPar.get("autoSubmitForm").toString());
            }
        } catch (Exception e) {
            logger.error("伊蚊快捷异常!异常信息:{}", e);
        }
        return res;
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        return null;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}