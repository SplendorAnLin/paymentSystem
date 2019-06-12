package com.yl.payinterface.core.handle.impl.auth.futeng100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.auth.futeng100001.utils.MD5Util;
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 付腾支付接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年11月16日
 */
@Service("fuTeng100001Handler")
public class FuTeng100001Handler implements AuthPayHandler {

    private Logger logger = LoggerFactory.getLogger(FuTeng100001Handler.class);

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.setProperty("merId", "882451202890495");
//        properties.setProperty("key", "EAEB8C1BFD31DDA00BBC6D5A9ABACF2C");
//        properties.setProperty("payUrl", "http://app.jsztzf.com:8081/WapPayInterface/order.action");
//        properties.setProperty("notifyUrl", "http://127.0.0.1/123/");
//        Map<String, String> map = new HashMap<>();
//        map.put("tradeConfigs", JsonUtils.toJsonString(properties));
//        map.put("amount", "2000");
//        map.put("productName", "测试");
//        map.put("interfaceRequestId", CodeBuilder.getOrderIdByUUId());
//        System.out.println(new FuTeng100001Handler().authPay(map));
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        // 获取交易配置
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        Map<String, String> res = null;
        try {
            String sign = MD5Util.MD5Encode(properties.getProperty("merId") + properties.getProperty("key"), "UTF-8");
            StringBuffer url = new StringBuffer(properties.getProperty("payUrl"));
            url.append("?money=");
            url.append((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
            url.append("&remark=");
            url.append(java.net.URLEncoder.encode(map.get("productName"), "UTF-8"));
            url.append("&mercNo=");
            url.append(properties.getProperty("merId"));
            url.append("&subOderNo=");
            url.append(map.get("interfaceRequestID"));
            url.append("&backUrl=");
            url.append(properties.getProperty("notifyUrl"));
            url.append("&signCode=");
            url.append(sign);
            logger.info("付腾认证支付 请求地址：[{}]", url.toString());
            String result = HttpUtils.sendReq(url.toString(), "", "POST");
            logger.info("付腾认证支付 请求地址：[{}],响应报文：[{}]", url.toString(), result);
            Map<String, String> resData = JsonUtils.toObject(result, new TypeReference<Map<String, String>>() {
            });
            res = new HashMap<>();
            if (resData.get("resultCode").equals("0000")) {
                res.put("responseCode", resData.get("resultCode"));
                res.put("orderNo", resData.get("orderNo"));
                res.put("gateway", "htmlSubmit");
                res.put("interfaceRequestID", map.get("interfaceRequestID"));
                res.put("html", resData.get("html"));
                return res;
            } else {
                throw new BusinessRuntimeException("付腾认证支付 下单异常 : " + String.valueOf(resData.get("resultCode")));
            }
        } catch (Exception e) {
            logger.error("付腾认证支付接口下单异常！[{}]", e);
        }
        return res;
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        // TODO Auto-generated method stub
        return null;
    }
}