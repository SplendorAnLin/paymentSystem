package com.yl.online.gateway.utils;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 二次支付回调
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/12
 */
public class NotifyUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(NotifyUtils.class);

    public static void send(Order order, PartnerRequest partnerRequest){
        Map<String, String> params = new LinkedHashMap<>();
        try {
            params.put("busstime", new SimpleDateFormat("yyyyMMddHHmmss").format(order.getCreateTime()));
            params.put("paytime", new SimpleDateFormat("yyyyMMddHHmmss").format(order.getSuccessPayTime()));
            params.put("apiCode", "YL-PAY");
            params.put("payType", order.getPayType().name());
            params.put("partner", order.getReceiver());
            params.put("outOrderId", partnerRequest.getOutOrderId());
            params.put("orderCode", order.getCode());
            params.put("partnerOrderStatus", order.getStatus().name());
            params.put("qingsuanStatus", order.getClearingStatus().name());
            params.put("amount", String.valueOf(order.getAmount()));
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("sign", MD5Util.md5(partnerRequest.getOutOrderId() + "ABbvYc3ESmv21DmLq0fh8ZlScnkhWY" + params.get("timestamp")));
            logger.info("二次支付回调参数：{}", params);
            String notifyResult = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://www.begame.cn/b2c/pay/hook", JsonUtils.toJsonString(params), true, "UTF-8", 10000);
            logger.info("回传处理结果：{}", notifyResult);
        } catch (Exception e) {
            logger.error("二次支付回调异常!异常信息:{}", e);
        }
    }
}