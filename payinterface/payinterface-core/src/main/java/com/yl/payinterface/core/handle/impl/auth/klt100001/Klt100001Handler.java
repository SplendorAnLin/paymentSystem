package com.yl.payinterface.core.handle.impl.auth.klt100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.auth.klt100001.utils.KltUtils;
import com.yl.payinterface.core.handler.AuthPayHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 开联通 认证支付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
@Service("klt100001Handler")
public class Klt100001Handler implements AuthPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Klt100001Handler.class);

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        Map<String, String> data = new HashMap<>();
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String productName = map.get("productName");
        String gateway = properties.getProperty("toPay");
        try {
            data.put("inputCharset", "1");
            data.put("pickupUrl", properties.getProperty("pickupUrl"));
            data.put("receiveUrl", properties.getProperty("receiveUrl"));
            data.put("version", "v1.0");
            data.put("language", "1");
            data.put("signType", "0");
            data.put("merchantId", properties.getProperty("merchantId"));
            data.put("orderNo", interfaceRequestID);
            data.put("orderAmount", amount);
            data.put("orderCurrency", "156");
            data.put("orderDatetime", dateTime);
            data.put("productName", productName);
            if ("AUTHPAY_KLT-100001".equals(map.get("interfaceCode"))) {
                data.put("payType", "99");
            } else if ("AUTHPAY_KLT-100002".equals(map.get("interfaceCode"))) {
                data.put("payType", "39");
            } else if ("AUTHPAY_KLT-100003".equals(map.get("interfaceCode"))) {
                data.put("payType", "40");
            }
            String signMsg = KltUtils.authPay(data, properties.getProperty("key"));
            data.put("signMsg", signMsg);
            data.put("gateway", gateway);
            data.put("url", properties.getProperty("payUrl"));
            data.put("interfaceRequestID", map.get("interfaceRequestID"));
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return data;
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