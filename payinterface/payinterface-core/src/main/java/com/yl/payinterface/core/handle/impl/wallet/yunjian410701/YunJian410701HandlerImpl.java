package com.yl.payinterface.core.handle.impl.wallet.yunjian410701;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.yunjian410701.utils.signUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 云尖 支付宝H5
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/22
 */
@Service("yunJian410701Handler")
public class YunJian410701HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(YunJian410701HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("version", "1.0");
        params.put("customerid", "10878");
        params.put("sdorderno", "20180522101527183628");
        params.put("total_fee", "2.00");
        params.put("paytype", "alipaywap");
        params.put("bankcode", "");
        params.put("notifyurl", "http://pay.feiyijj.com/payinterface-front/yj410701Notify/completeTrade");
        params.put("returnurl", "http://pay.feiyijj.com/payinterface-front/yj410701Notify/completeGatewayPayPage");
        params.put("remark", "测试");
        params.put("sign", signUtils.getSign(params, "fd2847d549c899c0cdfe2388b11e7ea866068445"));
        System.out.println(signUtils.getUrlParamsByMap(params));
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(Double.valueOf("1.0")));
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String, Object> params = new HashMap<>();
        Map<String,String> resParams = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        DecimalFormat df = new DecimalFormat("#.00");
        String amount = df.format(Double.valueOf(map.get("amount")));
        String productName = map.get("productName");
        try {
            params.put("version", "1.0");
            params.put("customerid", properties.getProperty("customerId"));
            params.put("sdorderno", interfaceRequestID);
            params.put("total_fee", amount);
            params.put("paytype", "alipaywap");
            params.put("bankcode", "");
            params.put("notifyurl", properties.getProperty("notifyurl"));
            params.put("returnurl", properties.getProperty("returnurl"));
            params.put("remark", productName);
            params.put("sign", signUtils.getSign(params, properties.getProperty("key")));
            params.put("url", properties.getProperty("payUrl"));
            resParams.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("customerId"));
            CacheUtils.setEx(interfaceRequestID, JsonUtils.toJsonString(params), 43200);
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