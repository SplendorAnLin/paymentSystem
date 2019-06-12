package com.yl.payinterface.core.handle.impl.wallet.ft320000;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.handle.impl.wallet.cncb330000.MD5;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.HttpUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.*;

/**
 * @author AnLin
 * @author zk [kai.zhang@bank-pay.com]
 * @Descriptions
 * @Date 2018年08月01日 11:08
 */
@Service("ft320000Handler")
public class FT320000HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(FT320000HandlerImpl.class);

    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        Map<String, String> tradeConfigs = new HashMap<>();
//        tradeConfigs.put("customer_no", "882312502725113");
//        tradeConfigs.put("key", "CB0DAA2541CCD7BB3C0FEB85DCFEAA70");
//        tradeConfigs.put("bank_url", "http://120.27.194.146:8081/H5PayOnline/groupH5");
//        tradeConfigs.put("notify_url", "http://m.bank-pay.com:9966/groupH5");
//        map.put("tradeConfigs", JsonUtils.toJsonString(tradeConfigs));
//        map.put("amount", "1");
//        map.put("interfaceRequestID", "test-" + System.currentTimeMillis());
//
//        FT320000HandlerImpl gx100000Handler = new FT320000HandlerImpl();
//        gx100000Handler.pay(map);

        String msg="{\"amount\":\"10.0\",\"respCode\":\"0000\",\"orderNo\":\"TD20180803100243835517\",\"status\":\"SUCCESS\",\"respMsg\":\"交易成功\",\"merchantNo\":\"882312502725113\",\"signature\":\"32DE81A508CDBAF6D60409F28B6462AB\"}";
        HttpUtil.sendPost("http://pay.feiyijj.com/payinterface-front/ftNotify/completePay",JsonUtils.toObject(msg,Map.class));
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String, String> resParams = new HashMap<>();

        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = map.get("amount");
        Map<String, String> transMap = new HashMap<>();
        //992支付宝 1004微信 993财付通 2097网银 2098支付宝 2099微信
        transMap.put("subject", "order");
        transMap.put("amNumber", properties.getProperty("customer_no"));
        transMap.put("amount", amount);
        transMap.put("notifyUrl", properties.getProperty("notify_url"));
        transMap.put("orderNo", interfaceRequestID);

        ArrayList<String> paramNames = new ArrayList<>(transMap.keySet());
        Collections.sort(paramNames);
        // 组织待签名信息
        StringBuilder signSource = new StringBuilder();
        for (String paramName : paramNames) {
            signSource.append(paramName + "=" + transMap.get(paramName) + "&");
        }
        String signature = MD5.MD5Encode(signSource.toString().substring(0, signSource.length() - 1) + properties.getProperty("key"));
        transMap.put("tradeType", "yH5Pay");
        transMap.put("signature", signature);
        String resStr;
        Map<String, String> jsonParams;
        try {
            logger.info("付腾 支付宝H5下单:[{}], 请求报文:[{}]", map.get("interfaceRequestID"), transMap);
            resStr = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("bank_url"), transMap,
                    true, "UTF-8", 60000);
            resStr = URLDecoder.decode(resStr).replace("\\u003", "=");
            logger.info("付腾 支付宝H5下单:[{}], 响应报文:[{}]", map.get("interfaceRequestID"), URLDecoder.decode(resStr));
            jsonParams = JsonUtils.toObject(resStr, new TypeReference<Map>() {
            });
            if ("0000".equals(jsonParams.get("respCode"))) {
                resParams.put("code_url", jsonParams.get("qrCodeURL"));
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + jsonParams.get("qrCodeURL"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
                resParams.put("gateway", "native");
            }
        } catch (Exception e) {
            logger.error("下单失败！异常信息:{}", e);
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
