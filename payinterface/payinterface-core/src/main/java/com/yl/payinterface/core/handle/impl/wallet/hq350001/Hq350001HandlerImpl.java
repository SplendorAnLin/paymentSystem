package com.yl.payinterface.core.handle.impl.wallet.hq350001;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.hq350001.utils.HuanQiuUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 环球支付宝H5
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/18
 */
@Service("hQ350001Handler")
public class Hq350001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hq350001HandlerImpl.class);

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        try {
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
            String interfaceRequestID = map.get("interfaceRequestID");
            String amount = map.get("amount");
            String productName = map.get("productName");
            Map<String, String> transMap = new HashMap<>();
            transMap.put("body", productName);
            transMap.put("notify_url", properties.getProperty("notifyUrl"));
            transMap.put("out_order_no", interfaceRequestID);
            transMap.put("partner", properties.getProperty("partner"));
            transMap.put("return_url", properties.getProperty("returnUrl"));
            transMap.put("subject", productName);
            transMap.put("total_fee", amount);
            transMap.put("user_seller", properties.getProperty("seller"));
            transMap.put("sign", HuanQiuUtils.getSign(transMap, properties.getProperty("key")));
            transMap.put("url", properties.getProperty("url"));
            resParams.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("seller"));
            CacheUtils.setEx(interfaceRequestID, JsonUtils.toJsonString(transMap), 43200);
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
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