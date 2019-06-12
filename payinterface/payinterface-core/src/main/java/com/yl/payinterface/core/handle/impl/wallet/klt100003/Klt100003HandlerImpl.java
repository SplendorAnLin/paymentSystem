package com.yl.payinterface.core.handle.impl.wallet.klt100003;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.auth.klt100001.utils.KltUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service("klt100003Handler")
public class Klt100003HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Klt100003HandlerImpl.class);

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String, String> data = new HashMap<>();
        Map<String,String> resParams = new HashMap<String, String>();
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String productName = map.get("productName");
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
            data.put("payType", "47");
            String signMsg = KltUtils.authPay(data, properties.getProperty("key"));
            data.put("signMsg", signMsg);
            data.put("gateway", "submit");
            data.put("url", properties.getProperty("payUrl"));
            data.put("interfaceRequestID", map.get("interfaceRequestID"));
            resParams.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("seller"));
            CacheUtils.setEx(interfaceRequestID, JsonUtils.toJsonString(data), 43200);
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