package com.yl.payinterface.core.handle.impl.wallet.zft100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付通 京东钱包
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/6
 */
@Service("zft100001Handler")
public class Zft100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Zft100001HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        req.put("service", "getJdH5Url");
        req.put("merchantNo", "CX0003924");
        req.put("bgUrl", "https://kd.alblog.cn/front/callback");
        req.put("version", "V2.0");
        req.put("payChannelCode", "CR_WAP");
        req.put("payChannelType", "1");
        req.put("orderNo", "TD" + System.currentTimeMillis());
        req.put("orderAmount", "200");
        req.put("curCode", "CNY");
        req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        req.put("orderSource", "2");
        req.put("signType", "1");
        req.put("sign", ZftSignUtils.getUrlParamsByMap(req, "c40893797ae24b9395a40d2b7da2391d"));
        try {
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://139.129.206.79:29606/PayApi/getJdH5Url", req, false, "UTF-8", 300000);
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
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
            req.put("service", "getJdH5Url");
            req.put("merchantNo", properties.getProperty("merchantNo"));
            req.put("bgUrl", properties.getProperty("notifyUrl"));
            req.put("version", "V2.0");
            req.put("payChannelCode", "CR_WAP");
            req.put("payChannelType", "1");
            req.put("orderNo", interfaceRequestID);
            req.put("orderAmount", amount);
            req.put("curCode", "CNY");
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("orderSource", "2");
            req.put("signType", "1");
            req.put("sign", ZftSignUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            logger.info("支付通下单报文：{}", req);
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            logger.info("支付通返回报文：{}", resPar);
            if ("10000".equals(resPar.get("dealCode"))) {
                resParams.put("code_url", resPar.get("payUrl"));
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resPar.get("payUrl"));
                resParams.put("interfaceInfoCode", interfaceRequestID);
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("merchantNo"));
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
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
}