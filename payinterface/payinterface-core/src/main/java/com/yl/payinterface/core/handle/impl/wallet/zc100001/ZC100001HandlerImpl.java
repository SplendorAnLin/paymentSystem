package com.yl.payinterface.core.handle.impl.wallet.zc100001;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.zc100001.utils.signUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 中辰支付宝
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/17
 */
@Service("ZC100001Handler")
public class ZC100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(ZC100001HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, Object> req = new TreeMap<>();
        req.put("version", "V2.0");
        req.put("merchantId", "9962987698");
        req.put("goodsName", "测试");
        req.put("busType", "06");
        req.put("orderId", "TD-" + System.currentTimeMillis());
        req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        req.put("orderAmt", "10.00");
        req.put("returnUrl", "https://kd.alblog.cn/front/callback");
        req.put("notifyUrl", "https://kd.alblog.cn/front/callback");
        req.put("signType", "MD5");
//        req.put("sign", signUtils.getSign(req, "1dc79f548e5f4e8ba29b8bbda5629f02"));
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String, Object> req = new TreeMap<>();
        Map<String,String> resParams = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = map.get("amount");
        String productName = map.get("productName");
        try {
            req.put("version", "V2.0");
            req.put("merchantId", properties.getProperty("merchantId"));
            req.put("goodsName", productName);
            if ("ZF100001-ALIPAY_H5".equals(map.get("interfaceInfoCode"))) {
                req.put("busType", "06");
            } else {
                req.put("busType", "04");
            }
            req.put("orderId", interfaceRequestID);
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("orderAmt", amount);
            req.put("returnUrl", properties.getProperty("returnUrl"));
            req.put("notifyUrl", properties.getProperty("notifyUrl"));
            req.put("signType", "MD5");
            req.put("sign", signUtils.getUrlParamsByMap(req, "1dc79f548e5f4e8ba29b8bbda5629f02"));
            req.put("url", properties.getProperty("payUrl"));
            logger.info("中辰支付宝请求报文：{}", req);
            resParams.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("merchantId"));
            CacheUtils.setEx(interfaceRequestID, JsonUtils.toJsonString(req), 43200);
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