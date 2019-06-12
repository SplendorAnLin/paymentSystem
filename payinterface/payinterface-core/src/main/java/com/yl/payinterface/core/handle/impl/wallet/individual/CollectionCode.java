package com.yl.payinterface.core.handle.impl.wallet.individual;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CollectionCodeLock;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 支付宝 个人收款码商户池
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
@Service("cc100001Handler")
public class CollectionCode implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(CollectionCode.class);

    @Resource
    private CollectionCodeLock collectionCodeLock;

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        try {
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
            String interfaceRequestID = map.get("interfaceRequestID");
            String amount = map.get("amount");
            AliPayCollectionCode aliPayCollectionCode = collectionCodeLock.getCode(amount);
            if (aliPayCollectionCode == null) {
                throw new RuntimeException("暂无可用个人码!");
            }
            Map<String, String> transMap = new HashMap<>();
            transMap.put("code_url", aliPayCollectionCode.getUrl());
            transMap.put("amount", amount);
            transMap.put("interfaceRequestID", interfaceRequestID);
            CacheUtils.setEx("ALIPAYCODE:" + interfaceRequestID, JsonUtils.toJsonString(transMap), 600);
            JSONObject cache = JsonUtils.toObject(JsonUtils.toJsonString(aliPayCollectionCode), new TypeReference<JSONObject>() {
            });
            cache.put("interfaceRequestID", interfaceRequestID);
            CacheUtils.setEx(aliPayCollectionCode.getAliPayAcc() + ":" + aliPayCollectionCode.getAmount() + getRemarks(aliPayCollectionCode.getRemarks()), cache.toString(), 600);
            resParams.put("code_url", properties.getProperty("qrPayUrl") + "?requestId=" + interfaceRequestID);
            resParams.put("code_img_url", resParams.get("code_url"));
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", aliPayCollectionCode.getId());
            resParams.put("interfaceOrderID", aliPayCollectionCode.getAliPayAcc() + " - "+ aliPayCollectionCode.getId());
            logger.info("接口请求号：{}，收款二维码信息：{}", interfaceRequestID, JsonUtils.toJsonString(aliPayCollectionCode));
        } catch (Exception e) {
            logger.error("个人码下单异常!异常信息：{}", e);
        }
        return resParams;
    }

    public static String getRemarks(String remarks) {
        if (StringUtils.isNotBlank(remarks)) {
            return remarks;
        } else {
            return "";
        }
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